export default (request, response) => {
    const xhr = require('xhr')
    var comedores = new Array();
    let bodyString = request.body;
    // El mensaje que envia DIALOGFLOW es un pedazo de string que se puede parsear a JSON
    // Es mucho mas fácil tratarlo como JSON
    // Solo hace falta fijarse en el ejemplo
    // Lo que usamos son los parameters, NOTA: los alias tambien se pasan por si queremosusarlos
    var entradaDialogFlow = JSON.parse(bodyString);
    console.log("Entrada DialogFlow: "+entradaDialogFlow);
    //var pais=entradaDialogFlow['queryResult']['parameters'].pais;
    //var capital="Madrid";

    var respuesta="SOCORRO";

    function getMenus(subtabla,i){
        // Miramos cuantos menus en ese dia
        let n_menus = subtabla.match(/Menú [0-9]+/g);
        let devolver = new Array();
		  // Si no hay menús es que está cerrado
		if(n_menus.lenght > 0){
            for(i=0; i < n_menus.length; i++){
                let diseccion;
                let menu_completo = n_menus[i] + ": "
                if (i < n_menus.length-1)
                    diseccion = subtabla.substring(subtabla.indexOf(n_menus[i]), subtabla.indexOf(n_menus[i+1]) ) 
                else
                    diseccion = subtabla.substring(subtabla.indexOf(n_menus[i]) )
                // Obtenemos numero de platos de un menu (primero, segundo, tercero...)
            let n_comidas = diseccion.match(/leftalign\">[a-zA-Zá-źÁ-Ź ]* </g);
                // Obtenemos la descripcion de cada plato
                let n_descripcion_comida = diseccion.match(/<strong>[a-zA-Zá-źÁ-Ź0-9,ñ\(\)\{\}\[\]\-\_ ]*<\/strong>/g);
                
                // Unimos todo
                for(let j in n_comidas){
                    menu_completo += n_comidas[j].substring(n_comidas[j].indexOf('>')+1,n_comidas[j].indexOf('<')) + " " +  n_descripcion_comida[j].substring(n_descripcion_comida[j].indexOf('>')+1,n_descripcion_comida[j].indexOf('</'))
                    if ( j < n_comidas.length-2)
                        menu_completo += ", "
                    if ( j == n_comidas.length-2)
                        menu_completo += " y de "
                }

                // Metemos uno de los menus
                devolver.push(menu_completo)
            }
        } else {
            devolver = 'CERRADO';
        }
        return devolver;
    }

    return xhr.fetch("https://scu.ugr.es/pages/menu/comedor").then(function (response) {
        // The API call was successful!
        return response.text();
    }).then(function (html) {
        // This is the HTML from our response as a text string
        let first_table_start = html.indexOf('<table');
        let first_table_end = html.indexOf('</table');
        let second_table_start = html.indexOf('<table',first_table_start+1);
        let second_table_end = html.indexOf('</table',first_table_end+1);
        let tablas = [html.substring(first_table_start,first_table_end), html.substring(second_table_start,second_table_end)];
        //console.log(tablas);
        for(let h in tablas){
            // Buscamos que dias hay menú
            let aux = tablas[h].match(/<strong>(L|M|J|V|S)+[a-zA-Zá-źÁ-Ź0-9 ]*,[a-zA-Zá-źÁ-Ź0-9 ]*<\/strong>/g);
            let menus = new Array();
            let dias = new Array();
            for(let i in aux){
                // Limpiamos la basura, solo queremos dia de la semana y fecha
                dias.push( aux[i].substring(aux[i].indexOf('>')+1,aux[i].indexOf('/')-1));
            }
            let algo = 0
            // Para cada dia obtenemos su menu
            for(let i=0; i < aux.length; i++){
                if(i < aux.length-1)
                    menus.push(getMenus(tablas[h].substring(tablas[h].indexOf(aux[i]), tablas[h].indexOf(aux[i+1])),algo));
                else
                    menus.push(getMenus(tablas[h].substring(tablas[h].indexOf(aux[i])),algo));
            }

            // Concatenamos el dia con su menu, para tener todo junto
            for(let i=0; i < aux.length; i=i+1){
                menus[i].unshift(dias[i])
            }

            // Metemos el menu de un comedor en el array de comedores
            comedores.push(menus);
            

        }
        //console.log(comedores);
        // NICO:
        // ~Aqui he obtenido todos los menus, sinceramente creo que el proceso se puede acortar (dependiendo de que nos pasen)
        // ~Por ejemplo como tengo los dias ordenados, solo extrago el menu de ese dia (es variar el for)
        // ~Con los menus habria que trastear la funcion getMenus, o quedarnos con algo de lo que devuelve
        // ~En fin que solo falta hacer retoques a la busqueda y tal, lo dificil esta hecho
        respuesta = comedores[0][0][1];
        console.log(respuesta)
        response.status = 200;

        // Set the headers the way you like
        response.headers['X-Custom-Header'] = 'CustomHeaderValue';
        return response.send({ fulfillmentText: respuesta });

    }).catch(function (err) {
        // There was an error
        console.warn('Something went wrong.', err);
        return response.send({ fulfillmentText: respuesta });
    });
};

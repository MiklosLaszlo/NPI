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
    var diaDialogFlow=entradaDialogFlow['queryResult']['parameters'].dia.toUpperCase();
    var menuDialogFlow=entradaDialogFlow['queryResult']['parameters'].menu;
    var comedorDialogFlow=entradaDialogFlow['queryResult']['parameters'].comedor;
    var controlDias = new Date();
    var diaHoy = controlDias.getDay();
    

    var respuesta="";

    function getMenus(subtabla,i){
        // Miramos cuantos menus en ese dia
        let n_menus = subtabla.match(/Menú [0-9]+/g);
        let devolver = new Array();
		// Si no hay menús es que está cerrado
		if(n_menus != null){
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
            devolver.push('CERRADO');
        }
        return devolver;
    }

    function dia2number(dia){
        switch(dia){
            case 'LUNES':
                return 0;
                break;
            case 'MARTES':
                return 1;
                break;
            case 'MIÉRCOLES':
                return 2;
                break;
            case 'JUEVES':
                return 3;
                break;
            case 'VIERNES':
                return 4;
                break;
            case 'SÁBADO':
                return 5;
                break;
            case 'DOMINGO':
                return 6;
                break;
            default:
                return -1;
                break;
        }
    }

    var diaPedido = dia2number(diaDialogFlow)

    return xhr.fetch("https://scu.ugr.es/pages/menu/comedor").then(function (response) {
        // The API call was successful!
        return response.text();
    }).then(function (html) {
        // This is the HTML from our response as a text string
        let first_table_start = body.indexOf('id="__doku_menu_semanal_comedores_fuentenueva_aynadamar_y_cartuja_-carlos_v"');
        let inter_table = body.indexOf('id="__doku_menu_comedor_pts"');
        let second_table_end = body.indexOf('<!-- SECTION "Menú Comedor (PTS)" [7536-] -->');
        let tablas = [body.substring(first_table_start,inter_table), body.substring(inter_table,second_table_end)];
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
        
        console.log(comedores)
        console.log(respuesta)
        response.status = 200;

        if(comedorDialogFlow == 'PTS'){
            for(let i in comedores[0]){
                let respondido = false
                if(diaDialogFlow == comedores[0][i][0].substring(0,comedores[0][i][0].indexOf(','))){
                    if(respondido){
                        if(diaHoy != diaPedido){
                            respondido = false
                        }
                    }
                    if(!respondido){
                        for(let j = 1; j < comedores[0][i].length; j++){
                            if(menuDialogFlow == j){
                                respuesta = comedores[0][i][j]
                            }
                            if(comedores[0][i][j] == 'CERRADO'){
                                respuesta = 'Ese dia el comedor está cerrado'
                            }
                        }
                        respondido = true
                    }
                }
            }
            if(respuesta == ""){
                respuesta = "Ese dia el comedor no está abierto"
            }
        }
        else{
            for(let i in comedores[1]){
                let respondido = false
                if(diaDialogFlow == comedores[1][i][0].substring(0,comedores[1][i][0].indexOf(','))){
                    if(respondido){
                        if(diaHoy != diaPedido){
                            respondido = false
                        }
                    }
                    if(!respondido){
                        for(let j = 1; j < comedores[1][i].length; j++){
                            if(menuDialogFlow == j){
                                respuesta = comedores[1][i][j]
                            }
                            if(comedores[0][i][j] == 'CERRADO'){
                                respuesta = 'Ese dia el comedor está cerrado'
                            }
                        }
                        respondido = true
                    }
                }
            }

            if(respuesta == ""){
                respuesta = "Ese dia el comedor no está abierto"
            }
        }

        // Set the headers the way you like
        response.headers['X-Custom-Header'] = 'CustomHeaderValue';
        return response.send({ fulfillmentText: respuesta });

    }).catch(function (err) {
        // There was an error
        console.warn('Something went wrong.', err);
        return response.send({ fulfillmentText: respuesta });
    });
};

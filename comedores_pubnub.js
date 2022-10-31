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
    var diaDialogFlow=entradaDialogFlow['queryResult']['parameters'].dia;
    var menuDialogFlow=entradaDialogFlow['queryResult']['parameters'].menu;
    var comedorDialogFlow=entradaDialogFlow['queryResult']['parameters'].comedor;
    var controlDias = new Date();
    var diaTratado = new Date(diaDialogFlow);

    console.log(entradaDialogFlow['queryResult']['parameters']);

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

    function month2number(mes){
        switch(mes){
            case 'ENERO':
                return 0;
            case 'FEBRERO':
                return 1;
            case 'MARZO':
                return 2;
            case 'ABRIL':
                return 3;
            case 'MAYO':
                return 4;
            case 'JUNIO':
                return 5;
            case 'JULIO':
                return 6;
            case 'AGOSTO':
                return 7;
            case 'SEPTIEMBRE':
                return 8;
            case 'OCTUBRE':
                return 9;
            case 'NOVIEMBRE':
                return 10;
            case 'DICIEMBRE':
                return 11;                
        }
    }

    return xhr.fetch("https://scu.ugr.es/pages/menu/comedor").then(function (response) {
        // The API call was successful!
        return response.text();
    }).then(function (html) {
        // This is the HTML from our response as a text string
        let first_table_start = html.indexOf('id="__doku_menu_semanal_comedores_fuentenueva_aynadamar_y_cartuja_-carlos_v"');
        let inter_table = html.indexOf('id="__doku_menu_comedor_pts"');
        let second_table_end = html.indexOf('<!-- SECTION "Menú Comedor (PTS)" [7536-] -->');
        let tablas = [html.substring(first_table_start,inter_table), html.substring(inter_table,second_table_end)];
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
        response.status = 200;

        if(comedorDialogFlow != 'PTS'){
            for(let i in comedores[0]){
                let numeros = comedores[0][i][0].match(/[0-9]+/g);
                let mes = comedores[0][i][0].match(/(ENERO|FEBRERO|MARZO|ABRIL|MAYO|JUNIO|JULIO|AGOSTO|SEPTIEMBRE|OCTUBRE|NOVIEMBRE|DICIEMBRE)/g);
                console.log(numeros[0])
                console.log(numeros[1])
                console.log(mes[0])
                console.log(diaTratado.getDate())
                console.log(diaTratado.getFullYear())
                console.log(diaTratado.getMonth())
                if( (numeros[0] == diaTratado.getDate() ) && (numeros[1] == diaTratado.getFullYear()) && (month2number(mes[0]) == diaTratado.getMonth() )) {
                    for(let j = 1; j < comedores[0][i].length; j++){
                        if(menuDialogFlow == j){
                            respuesta = comedores[0][i][j]
                        }
                        if(comedores[0][i][j] == 'CERRADO'){
                            respuesta = 'Ese dia el comedor está cerrado'
                        }
                    }
                }
            }
        }
        else{
            for(let i in comedores[1]){
                let numeros = comedores[1][i][0].match(/[0-9]+/g);
                let mes = comedores[1][i][0].match(/(ENERO|FEBRERO|MARZO|ABRIL|MAYO|JUNIO|JULIO|AGOSTO|SEPTIEMBRE|OCTUBRE|NOVIEMBRE|DICIEMBRE)/g);
                if( (numeros[0] == diaTratado.getDate() ) && (numeros[1] == diaTratado.getFullYear()) && (month2number(mes[0]) == diaTratado.getMonth() )) {
                    for(let j = 1; j < comedores[1][i].length; j++){
                        if(menuDialogFlow == j){
                            respuesta = comedores[1][i][j]
                        }
                        if(comedores[1][i][j] == 'CERRADO'){
                            respuesta = 'Ese dia el comedor está cerrado'
                        }
                    }
                }
            }

            
        }

        if(respuesta == ""){
            if(diaTratado.getDay() != 6)
                respuesta = "No hay info para ese dia"
            else
                respuesta = "Ese dia el comedor esta cerrado"
        }

        // Set the headers the way you like
        response.headers['X-Custom-Header'] = 'CustomHeaderValue';
        return response.send({ fulfillmentText: respuesta });

    }).catch(function (err) {
        // There was an error
        console.warn('Ha habido un error al buscar comedores.', err);
        return response.send({ fulfillmentText: respuesta });
    });
};

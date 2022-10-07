<![CDATA[
	function greetings(idioma) {
    var f = new Date();
    var horas = f.getHours();
    var manana = 0;
    var tarde = 0;
    var noche = 1;
    
    if (horas > 6 ){
                noche = 0;
                manana = 1;
        if(horas > 13){
                    manana = 0;
                    tarde = 1;
            if(horas > 19){
                        tarde = 0;
                            noche = 1;
                    }    
                }
    }
    var salida = "";
    switch(idioma){
        case 0:
            if(manana){
                salida = "Buenos dias";
            }
            if(tarde){
                salida = "Buenas tardes";
            }  
            if(noche){
                salida = "Buenas noches";
            }
           
            break;
        default:
            if(manana){
                salida = "Good mornings";
            }
            if(tarde){
                salida = "Good afternoon";
            }  
            if(noche){
                salida = "Good evening";
            }
            break;
    }
    return  salida;
}
]]>
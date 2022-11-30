
#define MAX_TS 1000
typedef int bool;

#define true 1
#define false 0

typedef enum {marca,funcion,variable,parametro_formal} TipoEntrada;
typedef enum {bool,entero,real,caracter,lista,desconocido} TipoDato;

typedef struct  entradaTS{
    TipoEntrada entrada;      // Indica el tipo de entrada
    char *nombre; 
    char *valor;            // Contendra los caracteres que forman el identificador
    TipoDato dato_referencia; // En caso de que entrada sea funcion,variable
                              // o parametro formal indica el tipo de dato al que hace referencia
    unsigned int parametros;           //Si tipoDato  es funcion indica el numero de parametros   
};
//Definimos la TS como un array multidimensional de entradas
entradaTS  TS[MAX_TS];

//Variable para saber por donde estamos de la pila
long int TOPE=0;

#define YYSTYPE entradaTS // Cada simbolo que se lea tendra estructura de tipo entradaTS


//Insertar elemento en la pila
void insertar(entradaTS e){
    if(debug)
        printf("Inserto %s %s \n", toStringEntrada(e.entrada), e.nombre);
    if(TOPE==MAX_TS){
        printf("ERROR: Se ha alcanzado el tamaño maximo de la pila \n");
        exit(-1);
    }   
    else{
        TS[TOPE].entrada=e.entrada;
        TS[TOPE].nombre=e.nombre;
        TS[TOPE].valor=e.valor;
        TS[TOPE].dato_referencia=e.dato_referencia;
        TS[TOPE].parametros=e.parametros;
        TOPE++;
    }
}

//Vaciar la pila
void vaciar(){
    if(debug)
        printf("La pila ha sido vaciada \n");
    TOPE=0;    
}

//Sacar el ultimo elemento de la pila
void sacar_utltimo(){
    if(debug)
        printf("Sacando el ultimo elemento de la pila \n");
    if(TOPE > 0)    
        TOPE--;
}

//Introducir en la pila una marca de inicio de bloque
void InsertarMarca(){
    if(debug)
        printf("Insertando marca de inicio de bloque \n");
    if(TOPE==MAX_TS){
        printf("ERROR: La pila esta llena \n");
        exit(-1);
    }    
    TS[TOPE].entrada=marca;
    TOPE++;
}

//Eliminamos todos los elementos hasta encontrar una marca de inicio de Bloque
void EliminarBloque(){
    if(debug)
        printf("Se procede a eliminar todos los elementos del bloque \n")
    bool es_marca=false;
    for (int i=TOPE-1;i>0 && !es_marca;i--){
        if(TS[i].entrada==marca){
            TOPE =i;
            es_marca=true;
        }
    }
    if(!es_marca)
        vaciar();
}




//Metodo auxiliar para mostrar la entrada
char *toStringEntrada(TipoEntrada e){
    switch (e){
    case  e==marca:
        return "Marca BLoque";
    case e==funcion:
        return "Funcion ";
    case e==variable:
        return "variable ";
    case e==parametro_formal:
        return "parametro_formal ";                
    default:
        return " ";
    }
}

char *toStringTipoDato(TipoDato dato){
    if(dato==entero) return "entero";
    if(dato==bool) return "bool";
    if(dato==real) return "real";
    if(dato==caracter) return "caracter";
    if(dato==lista) return "lista";
    return "";
}


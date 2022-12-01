#include "stdio.h"
#include "stdlib.h"

#define MAX_TS 1000

typedef int booleano;
//Si debug es 1 obtendremos informacion sobre lo que estamos haciendo
int debug=0;

#define true 1
#define false 0

typedef enum {marca,funcion,variable,parametro_formal} TipoEntrada;
typedef enum {booleano,entero,real,caracter,lista,desconocido} TipoDato;

typedef struct  entradaTS{
    TipoEntrada entrada;      // Indica el tipo de entrada
    char *nombre; 
    char *valor;              // Contendra los caracteres que forman el identificador
    TipoDato dato_referencia; // En caso de que entrada sea funcion,variable
                              // o parametro formal indica el tipo de dato al que hace referencia
    TipoDato dato_lista;      //tipo de datos que contiene la lista                    
    unsigned int parametros;  //Si tipoDato  es funcion indica el numero de parametros   
};
//Definimos la TS como un array multidimensional de entradas
entradaTS  TS[MAX_TS];

//Variable para saber por donde estamos de la pila
long int TOPE=0;

#define YYSTYPE entradaTS // Cada simbolo que se lea tendra estructura de tipo entradaTS


//Insertar elemento en la pila
void push(entradaTS e){
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
        TS[TOPE].dato_lista=e.dato_lista;
        TS[TOPE].parametros=e.parametros;
        TOPE++;
    }
}
//Metodo para saber si la pila esta vacia
bool isEmpty(){
    return (TOPE==0);
}

//Vaciar la pila
void clear(){
    if(debug)
        printf("La pila ha sido vaciada \n");
    TOPE=0;    
}

//Sacar el ultimo elemento de la pila
entradaTS pop(){
    if(debug)
        printf("Sacando el ultimo elemento de la pila \n");
    if(TOPE > 0) {
        entradaTS aux=TS[TOPE];   
        TOPE--;
        return aux;
    } 
    else{
        printf("Error: No se ha podido eliminar el ultimo elemento de la pila \n");
        exit(-1);
    }   
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
        printf("Se procede a eliminar todos los elementos del bloque \n");
    bool es_marca=false;
    for (int i=TOPE-1;i>0 && !es_marca;i--){
        if(TS[i].entrada==marca){
            TOPE =i;
            es_marca=true;
        }
    }
    if(!es_marca)
        clear();
}

//Funcion para buscar si un identificador existe dentro de su bloque, 
//si la encuentra devuelve la posicion util, si no devuelve -1

// identificador es el nombre de una variable o de una funcion
int  search_identificador(char * nom){
    if(debug) printf("Se procede a buscar si una variable esta dentro del mismo bloque")
    entradaTS aux=TS[TOPE];
    int i=TOPE-1;

    if(strlen(nombre ==0)){
        printf("Error: Se ha introducido una cadena vacia");
        exit(-1);
    }
    //Mientras que no encontremos la marca de inicio de bloque
    while(aux.entrada!=marca){
        if(strcmp(MAX_TS[i].nombre,nom)==0 && ((MAX_TS[i].entrada==variable) || (MAX_TS[i].entrada==funcion)) )
            return i;
        i--;    
    }
    return -1;
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//Metodo auxiliar para mostrar la entrada
char *toStringEntrada(TipoEntrada e){
    if(e==marca) return "marca";
    if(e==funcion) return "funcion";
    if(e==funcion) return "variable";
    if(e==parametro_formal) return "parametro_formal";
    return " ";
}

char *toStringTipoDato(TipoDato dato){
    if(dato==entero) return "entero";
    if(dato==booleano) return "booleano";
    if(dato==real) return "real";
    if(dato==caracter) return "caracter";
    if(dato==lista) return "lista";
    return "";
}


void printTS(){
    
    printf("------------------------------------\n");
    for(int pos = 0; pos <= TOPE-1;++pos){
        toStringEntrada(TS[pos].entrada);
        toStringTipoDato(TS[pos].dato_referencia);
    }
    printf("------------------------------------\n");
}


void ErrorOperarTipos(entradaTS dato1,entradaTS dato2){
    //En caso de que ambas variables tengan un tipo asignado
    if(dato1.dato_referencia!=desconocido && dato2.dato_referencia!=desconocido) {
        if(dato1.dato_referencia==lista)
            printf(" Error semantico : No se puede operar los tipos %s de %s y %s \n ", toStringTipoDato(dato1.dato_referencia), toStringTipoDato(dato1.dato_lista), toStringTipoDato(dato2.dato_referencia));
        else if( dato2.dato_referencia==lista)    
            printf(" Error semantico : No se puede operar los tipos %s  y %s de %s \n ", toStringTipoDato(dato1.dato_referencia), toStringTipoDato(dato2.dato_referencia), toStringTipoDato(dato2.dato_lista));
        else 
            printf("Error semantico : No se pueden operar los tipos %s y %s " , toStringTipoDato(dato1.dato_referencia),toStringTipoDato(dato2.dato_referencia));    
    }
}


void ErrorDeclaradaEnBLoque(entradaTS dato){
    if(dato.dato_referencia!=desconocido)
        printf(" Error semantico : La %s %s ya ha sido declarada en este bloque \n" , toStringTipoDato(dato.dato_referencia) , dato.nombre);
}

void ErrorNoDeclarada(entradaTS dato){
    if(dato!=desconocido){
        printf("Error semantico : La %s %s no ha sido declarada \n" , toStringTipoDato(dato.dato_referencia) , dato.nombre);
    }
}


void ErrorNombreParametros(entradaTS dato){
    if(dato!=desconocido)
        printf("Error semantico: Hay mas de un parametro con el mismo nombre %s \n" , dato.nombre);
}

void ErrorEnAsignacion(entradaTS dato1, entradaTS dato2){
    if(dato1.dato_referencia!=desconocido && dato2.dato_referencia!=desconocido)
        printf("Error semantico: Los tipos que hay en la asignacion %s y %s no coinciden \n" , toStringTipoDato(dato1.dato_referencia),toStringTipoDato(dato2.dato_referencia));
}

void ErrorTipoInternoLista(entradaTS dato1,entradaTS dato2){
    if (dato1.dato_referencia !=desconocido && dato2.dato_referencia !=desconocido)
        printf("Error semantico: Los tipos %s  y  %s no coinciden \n", toStringTipoDato(dato1.dato_lista),toStringTipoDato(dato2.dato_lista) );
}
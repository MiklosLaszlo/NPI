#include <stdbool.h>
#include <string.h>
#include <stdio.h>
#include <stdlib.h>

#define BG_COLOR_PURPLE     "\x1B[45m"
#define RESET_COLOR    "\x1b[0m"

#define MAX_TS 1000

//Si debug es 1 obtendremos informacion sobre lo que estamos haciendo
int debug=1;

#define true 1
#define false 0

#ifndef ENUM
#define ENUM
	typedef enum {marca,funcion,variable,parametro_formal,indefinido,operador} TipoEntrada;
	typedef enum {booleano,entero,real,caracter,lista,desconocido} TipoDato;
	typedef enum {
		negacion,sostenido,interrogacion,menos,
		equal, not_equal,
		and, or, xor,
		masmas,
		mas,
		entre, por,
		less, greater, less_eq, greater_eq,arroba, menosmenos, porcentaje,doblepor} TipoOperador;

	typedef struct  entradaTS{
   TipoEntrada entrada=indefinido;      // Indica el tipo de entrada
   char nombre[100]; 
   // char valor[50];              // Contendra los caracteres que forman el identificador
   TipoDato dato_referencia=desconocido; // En caso de que entrada sea funcion,variable
                             // o parametro formal indica el tipo de dato al que hace referencia
   TipoDato dato_lista;      //tipo de datos que contiene la lista                    
   unsigned int n_parametros;  //Si tipoDato  es funcion indica el numero de parametros 
   TipoOperador tipo_operador; // En caso de ser operador, qué operador es  
};
#endif 

//Definimos la TS como un array multidimensional de entradas
struct entradaTS  TS[MAX_TS];

//Variable para saber por donde estamos de la pila
long int TOPE=0;

//Metodo auxiliar para mostrar la entrada
char* toStringEntrada(TipoEntrada e){
    if(e==marca) return "marca";
    if(e==funcion) return "funcion";
    if(e==funcion) return "variable";
    if(e==parametro_formal) return "parametro_formal";
    return " ";
}

char* toStringTipoDato(TipoDato dato){
    if(dato==entero) return "entero";
    if(dato==booleano) return "booleano";
    if(dato==real) return "real";
    if(dato==caracter) return "caracter";
    if(dato==lista) return "lista";
    return "";
}
////////////////////////


//Insertar elemento en la pila
void push(struct entradaTS e){
    if(debug)
        printf("Inserto %s %s %s %s %i\n", toStringEntrada(e.entrada), e.nombre, toStringTipoDato(e.dato_referencia), toStringTipoDato(e.dato_lista),e.n_parametros);
    if(TOPE==MAX_TS){
        printf(BG_COLOR_PURPLE "ERROR:" RESET_COLOR"En línea %i. Se ha alcanzado el tamaño maximo de la pila.\n",yylineno);
        exit(-1);
    }   
    else{
        TS[TOPE].entrada=e.entrada;
        strcpy(TS[TOPE].nombre,e.nombre);
        TS[TOPE].dato_referencia=e.dato_referencia;
        TS[TOPE].dato_lista=e.dato_lista;
        TS[TOPE].n_parametros=e.n_parametros;
        TOPE++;
    }
}

//Insertar elemento en la pila
void push2(struct entradaTS e, TipoEntrada ent){
    if(debug)
        printf("Inserto %s %s %s %s %i\n", toStringEntrada(ent), e.nombre, toStringTipoDato(e.dato_referencia), toStringTipoDato(e.dato_lista),e.n_parametros);
    if(TOPE==MAX_TS){
        printf(BG_COLOR_PURPLE "ERROR:" RESET_COLOR"En línea %i.Se ha alcanzado el tamaño maximo de la pila \n",yylineno);
        exit(-1);
    }   
    else{
        TS[TOPE].entrada=ent;
        strcpy(TS[TOPE].nombre,e.nombre);
        TS[TOPE].dato_referencia=e.dato_referencia;
        TS[TOPE].dato_lista=e.dato_lista;
        TS[TOPE].n_parametros=e.n_parametros;
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
//copia e2 en e1
void copiaStruct(struct entradaTS *e1,struct entradaTS e2){
    (*e1).dato_lista = e2.dato_lista;
    (*e1).dato_referencia = e2.dato_referencia;
    (*e1).entrada = e2.entrada;
    (*e1).n_parametros = e2.n_parametros;
    (*e1).tipo_operador = e2.tipo_operador;
    strcpy((*e1).nombre,e2.nombre);
}
////////////////////////

//devuelve el argumento nº <arg> de la funcion <nombre>
struct entradaTS getArg(char* nombre,int arg){
    if(strlen(nombre)!=0){
        int index = -1;
        bool encontrado=false;
        struct entradaTS tmp;
        
        for(int i = TOPE-1; 0 <= i && !encontrado;--i){
            if(strcmp(nombre,TS[i].nombre)==0 && TS[i].entrada == funcion){
                index = i;
                encontrado = true;
            }
        }

        if(index > 0 && TS[index].n_parametros >= arg){
            index = index-TS[index].n_parametros + arg - 1;
            
            if(0 <= index && index < TOPE)
                copiaStruct(&tmp,TS[index]);
        }
        return tmp;
    }
}

//Sacar el ultimo elemento de la pila
struct entradaTS pop(){
    if(debug)
        printf("Sacando el ultimo elemento de la pila \n");
    if(TOPE > 0) {
        struct entradaTS aux=TS[TOPE];   
        TOPE--;
        return aux;
    } 
    else{
        printf(BG_COLOR_PURPLE "ERROR:" RESET_COLOR"En línea %i. No se ha podido eliminar el ultimo elemento de la pila \n");
        exit(-1);
    }   
}

//Introducir en la pila una marca de inicio de bloque
void InsertarMarca(){
    if(debug)
        printf("Insertando marca de inicio de bloque \n");
    if(TOPE==MAX_TS){
        printf(BG_COLOR_PURPLE "ERROR:" RESET_COLOR"En línea %i. La pila esta llena \n",yylineno);
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
struct entradaTS  search_identificador_marca(char * nom){
    if(debug) printf("Se procede a buscar si una variable esta dentro del mismo bloque \n");
    struct entradaTS aux=TS[TOPE];
    int i=TOPE-1;

    if(strlen(nom)==0){
        printf(BG_COLOR_PURPLE "ERROR:" RESET_COLOR"En línea %i. Se ha introducido una cadena vacia \n",yylineno);
    }
    //Mientras que no encontremos la marca de inicio de bloque
    // NICO: Ahora mismo hace:lee toda la pila hasta encontrar el nombre mientras sea una funcion o una variable.
    //       si no lo encuentra devuelve -1 
    while(aux.entrada!=marca && (i > -1)){

        if(strcmp(TS[i].nombre,nom)==0 && ((TS[i].entrada==variable) || (TS[i].entrada==funcion)) )
            return TS[i];
        aux.entrada=TS[i].entrada;    
        i--;    
    }
    return TS[0];
}

struct entradaTS  search_identificador_pila(char * nom){
    if(debug) printf("Se procede a buscar si una variable esta dentro del mismo bloque \n");
    struct entradaTS aux=TS[TOPE];
    int i=TOPE-1;

    if(strlen(nom)==0){
        printf(BG_COLOR_PURPLE "ERROR:" RESET_COLOR"En línea %i. Se ha introducido una cadena vacia \n",yylineno);
    }
    //Mientras que no encontremos la marca de inicio de bloque
    // NICO: Ahora mismo hace:lee toda la pila hasta encontrar el nombre mientras sea una funcion o una variable.
    //       si no lo encuentra devuelve -1 
    while((i > -1)){

        if(strcmp(TS[i].nombre,nom)==0 && ((TS[i].entrada==variable) || (TS[i].entrada==funcion)) )
            return TS[i];
        aux.entrada=TS[i].entrada;    
        i--;    
    }
    return TS[0];
}


bool Es_mismoTipo(struct entradaTS dato1, struct entradaTS dato2 ){
    if(dato1.dato_referencia!=desconocido && dato2.dato_referencia!=desconocido){
        if(dato1.dato_referencia==dato2.dato_referencia==lista)
            if(dato1.dato_lista==dato2.dato_lista)
                return true;
        if(dato1.dato_referencia==dato2.dato_referencia)
            return true;
    }         
    return false;
}

TipoDato AceptaOperadorBinarioAritmetico(struct entradaTS dato1,struct entradaTS dato2){
    if(Es_mismoTipo(dato1,dato2)){
        if( dato1.dato_referencia== entero )
            return entero;
        if( dato1.dato_referencia== real )
            return real;
    }   
    return desconocido;    
}




/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


void printTS(){
    
    printf("------------------------------------\n");
    for(int pos = 0; pos <= TOPE-1;++pos){
        toStringEntrada(TS[pos].entrada);
        toStringTipoDato(TS[pos].dato_referencia);
    }
    printf("------------------------------------\n");
}


void ErrorOperarTipos(struct entradaTS dato1,struct entradaTS dato2){
    //En caso de que ambas variables tengan un tipo asignado
    if(dato1.dato_referencia!=desconocido && dato2.dato_referencia!=desconocido) {
        if(dato1.dato_referencia==lista)
            printf(BG_COLOR_PURPLE "Error semantico :" RESET_COLOR"En línea %i. No se puede operar los tipos %s de %s y %s \n ", yylineno,toStringTipoDato(dato1.dato_referencia), toStringTipoDato(dato1.dato_lista), toStringTipoDato(dato2.dato_referencia));
        else if( dato2.dato_referencia==lista)    
            printf(BG_COLOR_PURPLE " Error semantico :" RESET_COLOR"En línea %i. No se puede operar los tipos %s  y %s de %s \n ",yylineno ,toStringTipoDato(dato1.dato_referencia), toStringTipoDato(dato2.dato_referencia), toStringTipoDato(dato2.dato_lista));
        else 
            printf(BG_COLOR_PURPLE "Error semantico :" RESET_COLOR"En línea %i. No se pueden operar los tipos %s y %s \n" ,yylineno, toStringTipoDato(dato1.dato_referencia),toStringTipoDato(dato2.dato_referencia));    
    }
}

void ErrorDeclaradaEnBLoque(struct entradaTS dato){
    if(dato.dato_referencia!=desconocido)
        printf(BG_COLOR_PURPLE "Error semantico :" RESET_COLOR"En línea %i. La %s %s ya ha sido declarada en este bloque \n",yylineno , toStringTipoDato(dato.dato_referencia) , dato.nombre);
}

void ErrorNoDeclarada(struct entradaTS dato){
    if(dato.dato_referencia!=desconocido){
        printf(BG_COLOR_PURPLE "Error semantico :" RESET_COLOR"En línea %i. La %s %s no ha sido declarada \n" ,yylineno, toStringTipoDato(dato.dato_referencia) , dato.nombre);
    }
}


void ErrorNombreParametros(struct entradaTS dato){
    if(dato.dato_referencia!=desconocido)
        printf(BG_COLOR_PURPLE "Error semantico :" RESET_COLOR"En línea %i. Hay mas de un parametro con el mismo nombre %s \n",yylineno , dato.nombre);
}

void ErrorEnAsignacion(struct entradaTS dato1, struct entradaTS dato2){
    if(dato1.dato_referencia!=desconocido && dato2.dato_referencia!=desconocido)
        printf(BG_COLOR_PURPLE "Error semantico :" RESET_COLOR"En línea %i. Los tipos que hay en la asignacion %s y %s no coinciden \n",yylineno , toStringTipoDato(dato1.dato_referencia),toStringTipoDato(dato2.dato_referencia));
}

void ErrorTipoInternoLista(TipoDato dato1,TipoDato dato2){
    if (dato1 !=desconocido && dato2 !=desconocido)
        printf(BG_COLOR_PURPLE "Error semantico :" RESET_COLOR"En línea %i. Los tipos %s  y  %s no coinciden \n",yylineno, toStringTipoDato(dato1),toStringTipoDato(dato2) );
}

////

void comprueba_exp_logica(struct entradaTS dato){
	if(dato.dato_referencia != booleano)
		printf(BG_COLOR_PURPLE "Error semantico :" RESET_COLOR"En línea %i. Se esperaba una expresión lógica y se tiene %s \n",yylineno, toStringTipoDato(dato.dato_referencia) );
}


struct entradaTS operador_ternario(struct entradaTS dato1, struct entradaTS dato2, struct entradaTS dato3){
	struct entradaTS salida;

	salida.dato_referencia = lista;
	salida.dato_lista = desconocido;

	if( dato1.dato_referencia == lista ){
		if( dato1.dato_lista == dato2.dato_referencia){
			if(dato3.dato_referencia == entero){
				salida.dato_referencia = lista;
				salida.dato_lista = dato2.dato_lista;
			}
			else
				printf(BG_COLOR_PURPLE "Error semantico :" RESET_COLOR"En línea %i. La posición debe ser un entero, se tiene %s \n",yylineno, toStringTipoDato(dato3.dato_referencia));
		} 
		else
			printf(BG_COLOR_PURPLE "Error semantico :" RESET_COLOR"En línea %i. El elemento a insertar debe ser del tipo de la lista. Tipo lista: %s, tipo elemento: %s \n",yylineno, toStringTipoDato(dato1.dato_lista), toStringTipoDato(dato2.dato_referencia));
	}
	else
		printf(BG_COLOR_PURPLE "Error semantico :" RESET_COLOR"En línea %i. Se esperaba una lista y se tiene %s \n",yylineno, toStringTipoDato(dato1.dato_referencia));

	return salida;
}

struct entradaTS operador_binario_logico(struct entradaTS operador, struct entradaTS dato1, struct entradaTS dato2 ){
	// Se entiende que el operador es binario less, less_eq, greater,greater_eq,  equal, not_equal

	struct entradaTS salida;
	salida.dato_referencia = indefinido;

	if (dato1.dato_referencia == booleano && dato2.dato_referencia == booleano){
		salida.dato_referencia = booleano;
	}
	else 
		printf(BG_COLOR_PURPLE "Error semantico :" RESET_COLOR"En línea %i. El operador solo acepta tipos booleanos. Tipo dato1: %s, Tipo dato2: %s \n",yylineno, toStringTipoDato(dato1.dato_referencia), toStringTipoDato(dato2.dato_referencia));

	return salida;
}

struct entradaTS operador_binario_aritmetico(struct entradaTS dato1,struct entradaTS dato2){

	struct entradaTS salida;
	salida.entrada = indefinido;
	salida.dato_referencia = desconocido;

	bool lista_1 = dato1.dato_referencia == lista;
	bool lista_2 = dato2.dato_referencia == lista;

	if(dato1.dato_referencia == real && dato2.dato_referencia == real && !lista_1 && !lista_2){
		salida.dato_referencia = real;
	}
	else if(dato1.dato_referencia == entero && dato2.dato_referencia == entero && !lista_1 && !lista_2){
		salida.dato_referencia = entero;
	}
	else{
		printf("Error semántico: Se esperaban dos reales o dos enteros. Se tienen los tipos %s y %s \n",
		 toStringTipoDato(dato1.dato_referencia),
		 toStringTipoDato(dato2.dato_referencia));
	}

	if(lista_1){
		if(dato1.dato_lista == real && dato2.dato_referencia == real){
			salida.dato_referencia = lista;
			salida.dato_lista = real;
		} 
		else if(dato1.dato_lista == entero && dato2.dato_referencia == entero){
			salida.dato_referencia = lista;
			salida.dato_lista = entero;
		}
		else{
			printf("Error semántico: Una lista de %s debe operarse con un %s. Se tiene %s \n",
				toStringTipoDato(dato1.dato_lista),
				toStringTipoDato(dato1.dato_lista),
				toStringTipoDato(dato2.dato_referencia));
		}
	}

	if(lista_2){
		if(dato1.dato_lista == real && dato2.dato_lista == real){
			salida.dato_referencia = lista;
			salida.dato_lista = real;
		}
		else if(dato1.dato_lista == entero && dato2.dato_lista == entero){
			salida.dato_referencia = lista;
			salida.dato_lista = entero;
		}
		else {
			printf("Error semántico: Una lista de %s debe operarse con un %s. Se tiene %s \n",
				toStringTipoDato(dato2.dato_lista),
				toStringTipoDato(dato2.dato_lista),
				toStringTipoDato(dato1.dato_referencia));
		}
	}  
	return salida;
}

struct entradaTS operador_binario_lista(struct entradaTS operador, struct entradaTS dato1, struct entradaTS dato2){
    struct entradaTS salida;
    salida.dato_referencia=indefinido;
    
        //Caso l@x
        if(dato1.dato_referencia==lista && dato2.dato_referencia==entero && operador.tipo_operador==arroba)
            salida.dato_referencia=entero;
        //Caso l--x    
        else if(dato1.dato_referencia==lista && dato2.dato_referencia==entero && operador.tipo_operador==menosmenos)
            salida.dato_referencia=lista;
        //Caso l%x    
        else if(dato1.dato_referencia==lista && dato2.dato_referencia==entero && operador.tipo_operador==porcentaje)  
            salida.dato_referencia=lista;  
        //Caso l**l
        else if(dato1.dato_referencia==lista && dato2.dato_referencia==lista && operador.tipo_operador==doblepor)
            salida.dato_referencia=lista;
        //Caso l +x  , l - x , l/x,l*x
        else if((dato1.dato_referencia==lista && dato1.dato_lista==dato2.dato_referencia) && (dato2.dato_referencia==entero || dato2.dato_referencia==real))
            salida.dato_referencia=lista;
        //Caso x +l y x * l
        else if((dato1.dato_referencia==entero || dato1.dato_referencia==real) && (dato2.dato_referencia==lista && dato2.dato_lista==dato1.dato_referencia))
            salida.dato_referencia=dato1.dato_referencia;
        else
            printf(
					"Error semantico: se esperaba una lista y un real o entero o viceversa pero se obtuvo %s y %s \n",
					toStringTipoDato(dato1.dato_referencia),
					toStringTipoDato(dato2.dato_referencia));    
        return salida;
}

struct entradaTS operador_binario(struct entradaTS operador, struct entradaTS dato1, struct entradaTS dato2) {
	struct entradaTS salida;
    switch (operador.tipo_operador)
	{
	case equal:
	case not_equal:
	case and:
	case or:
	case xor:
	case less:
	case greater:
	case less_eq:
	case greater_eq:
		return operador_binario_logico(operador, dato1, dato2);
		break;
	
	case mas:
	case menos:
	case por:
	case entre:
		return operador_binario_aritmetico( dato1, dato2);
		break;

	case arroba:
	case menosmenos:
	case porcentaje:
	case doblepor:
		return operador_binario_lista(operador, dato1, dato2);
		break;

	default:
		
		salida.dato_referencia = desconocido;
		salida.entrada = indefinido;

		break;
	}
}

struct entradaTS operador_unario(struct entradaTS dato,struct entradaTS operador ){
    struct entradaTS coso;
    switch(operador.tipo_operador){
        case negacion:
            if(dato.dato_referencia != booleano){
                printf(BG_COLOR_PURPLE "Error semantico :" RESET_COLOR"En línea %i. El primer operando no es un booleano \n",yylineno);
            }
            else{
                coso.dato_referencia=booleano;
            }

        break;
        case sostenido:
            if (dato.dato_referencia != lista){
                printf(BG_COLOR_PURPLE "Error semantico :" RESET_COLOR"En línea %i. El primer operando no es una lista \n",yylineno);
            }
            else
                coso.dato_referencia=entero;
        break;
        
        case interrogacion:
            if (dato.dato_referencia != lista){
                printf(BG_COLOR_PURPLE "Error semantico :" RESET_COLOR"En línea %i. El primer operando no es una lista \n",yylineno);
            }
            else
                coso.dato_referencia=dato.dato_lista;
        break;
        
        case menos:
            if(dato.dato_referencia==entero || dato.dato_referencia==real)
                coso.dato_referencia=entero;
            else
                printf(BG_COLOR_PURPLE "Error semantico :" RESET_COLOR"En línea %i. El dato no es entero o real \n",yylineno);
        break;

        default:
            coso.dato_referencia = desconocido;  
        break;
    };
    return coso;
}

//compara dos struct de tipo entradaTS
bool esMismo(struct entradaTS e1,struct entradaTS e2){
    return ((e1.dato_lista == e2.dato_lista) && (e1.dato_referencia == e2.dato_referencia) && (e1.entrada == e2.entrada) && (e1.n_parametros == e2.n_parametros));
}

//Search parametros, devuelve la posición en la pila del parametro
//Si no existe devuelve -1
struct entradaTS search_parametro(char * nom, struct entradaTS param){
    struct  entradaTS dev;
    int p=-1;
    bool encontrado=false;
    if(strlen(nom)==0){
        printf(BG_COLOR_PURPLE "Error semantico :" RESET_COLOR"En línea %i. Se ha introducido una cadena vacía \n",yylineno);
    }
    int pos_funcion = pos_identificador(nom);
    int n_arg = TS[pos_funcion].n_parametros;

    for(int i=1;i<=n_arg && !encontrado;++i){
        if(esMismo(TS[pos_funcion-i],param)){
            p=pos_funcion-i;
            encontrado = true;
            return TS[pos_funcion-i];
        }
    }
    return dev;
}

//Funcion para buscar si un identificador existe dentro de su bloque, 
//si la encuentra devuelve la posicion util, si no devuelve -1

// identificador es el nombre de una variable o de una funcion
int  pos_identificador(char * nom){
    if(debug) printf("Se procede a buscar si una variable esta dentro del mismo bloque \n");
    struct entradaTS aux=TS[TOPE];
    int i=TOPE-1;

    if(strlen(nom)==0){
        printf("Error: Se ha introducido una cadena vacia \n");
    }
    //Mientras que no encontremos la marca de inicio de bloque
    // NICO: Ahora mismo hace:lee toda la pila hasta encontrar el nombre mientras sea una funcion o una variable.
    //       si no lo encuentra devuelve -1 
    while(aux.entrada!=marca && (i > -1)){
        if(strcmp(TS[i].nombre,nom)==0 && ((TS[i].entrada==variable) || (TS[i].entrada==funcion)) )
            return i;
        i--;
    }
    return -1;
}

bool comprobar_for_pascal(struct entradaTS identificador, struct entradaTS dato1, struct entradaTS dato2){
	bool correcto = true;
	TipoDato tipo_variable = search_identificador_pila(identificador.nombre).dato_referencia;
	correcto &= tipo_variable == entero;
	if(correcto){
		correcto &= dato1.dato_referencia == entero;
		if(correcto){
			correcto &= dato2.dato_referencia == entero;
			if(!correcto) 
				printf("Línea %d. Error semántico: el final debe ser un entero. Se tiene %s\n",yylineno, toStringTipoDato(dato2.dato_referencia));
		}
		else
			printf("Línea %d. Error semántico: se esperaba una asignación a entero. Se tiene %s\n",yylineno, toStringTipoDato(dato1.dato_referencia));
	}
	else{
		printf("Línea %d. Error semántico: se esperaba una variable ya declarada de tipo entero. Se tiene %s\n",yylineno, toStringTipoDato(tipo_variable));
	}

	return correcto;
}

bool comprobar_asignacion(struct entradaTS identificador, struct entradaTS dato1){
	bool correcto = true;
	TipoDato tipo_variable = search_identificador_pila(identificador.nombre).dato_referencia;
	correcto &= tipo_variable != desconocido;
	if(correcto){
		correcto &= dato1.dato_referencia == tipo_variable;
			if(!correcto) 
				printf("Línea %d. Error semántico: Los tipos deben coincidir. Se tiene tipo variable: %s, tipo dato: %s\n",yylineno, toStringTipoDato(tipo_variable),toStringTipoDato(dato1.dato_referencia));
		
	}
	else{
		printf("Línea %d. Error semántico: la variable debe estar declarada. Se tiene %s\n",yylineno, toStringTipoDato(tipo_variable));
	}

	return correcto;
}
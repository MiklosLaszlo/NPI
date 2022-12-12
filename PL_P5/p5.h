#include <stdbool.h>
#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include "p5_TS.h"

extern int yylineno;

#define BG_COLOR_PURPLE	"\x1B[45m"
#define RESET_COLOR		"\x1b[0m"

#define MAX_TS 1000

#define true 1
#define false 0

//Si debug es 1 obtendremos informacion sobre lo que estamos haciendo
int debug=0;

//Definimos la TS como un array multidimensional de entradas
struct entradaTS  TS[MAX_TS];

//Variable para saber por donde estamos de la pila
long int TOPE=0;

// Que no se puede inicializar por defecto un struct en c

//Metodo auxiliar para mostrar la entrada
char* toStringEntrada(TipoEntrada e){
   if(e==marca) return "marca";
   if(e==funcion) return "funcion";
   if(e==funcion) return "variable";
   if(e==parametro_formal) return "parametro_formal";
   if(e==indefinido) return "indefinido";
   if(e==operador) return "operador";
	if(e==control) return "control";
   return " ";
}

char* toStringTipoDato(TipoDato dato){
   if(dato==entero) return "entero";
   if(dato==booleano) return "booleano";
   if(dato==real) return "real";
   if(dato==caracter) return "caracter";
   if(dato==lista) return "lista";
   if(dato==desconocido) return "desconocido";
   return "";
}
////////////////////////

//copia e2 en e1
void copiaStruct(struct entradaTS *e1,struct entradaTS e2){
   // para el analizador semántico
   (*e1).dato_lista = e2.dato_lista;
   (*e1).dato_referencia = e2.dato_referencia;
   (*e1).entrada = e2.entrada;
   (*e1).n_parametros = e2.n_parametros;
   (*e1).tipo_operador = e2.tipo_operador;
   strcpy((*e1).nombre,e2.nombre);

   // para la traducción
   (*e1).puntero_lista = e2.puntero_lista;
   strcpy((*e1).nombre_traductor,e2.nombre_traductor);

	strcpy(e1->descriptor_control.Entrada, e2.descriptor_control.Entrada);
	strcpy(e1->descriptor_control.Salida, e2.descriptor_control.Salida);
	strcpy(e1->descriptor_control.Else, e2.descriptor_control.Else);
	strcpy(e1->descriptor_control.NombreVar, e2.descriptor_control.NombreVar);
}

#pragma region Semantica

//Insertar elemento en la pila
void push(struct entradaTS e){
   if(debug)
      printf("Inserto %s %s %s %s %i\n", toStringEntrada(e.entrada), e.nombre, toStringTipoDato(e.dato_referencia), toStringTipoDato(e.dato_lista),e.n_parametros);
   if(TOPE==MAX_TS){
      printf(BG_COLOR_PURPLE "ERROR:" RESET_COLOR"En línea %i. Se ha alcanzado el tamaño maximo de la pila.\n",yylineno);
      exit(-1);
   }   
   else{
      copiaStruct(&TS[TOPE],e);
      TOPE++;
   }
   //if(debug)
      //printf("Tamaño actual de la pila %i\n",TOPE);
}

//Insertar elemento en la pila
void push2(struct entradaTS e, TipoEntrada ent){
   if(debug)
      printf("Inserto %s %s %s %s %i. Posicion %i\n", toStringEntrada(ent), e.nombre, toStringTipoDato(e.dato_referencia), toStringTipoDato(e.dato_lista),e.n_parametros, TOPE);
   if(TOPE==MAX_TS){
      printf(BG_COLOR_PURPLE "ERROR:" RESET_COLOR"En línea %i.Se ha alcanzado el tamaño maximo de la pila \n",yylineno);
      exit(-1);
   }   
   else{
      copiaStruct(&TS[TOPE],e);
      TS[TOPE].entrada=ent;
      TOPE++;
   }
   //if(debug)
      //printf("Tamaño actual de la pila %i\n",TOPE);
   //printTS();
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
         
         if(0 <= index && index < TOPE){
            
            copiaStruct(&tmp,TS[index]);
         }
      }
      if(debug)
         printf("Uso %s ,%s ,%s ,%s ,%i.\n", toStringEntrada(tmp.entrada), tmp.nombre, toStringTipoDato(tmp.dato_referencia), toStringTipoDato(tmp.dato_lista),tmp.n_parametros);
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
   //if(debug)
      //printf("Tamaño actual de la pila %i\n",TOPE);
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
   //if(debug)
      //printf("Tamaño actual de la pila %i\n",TOPE);
}

//Funcion para buscar si un identificador existe dentro de su bloque, 
//si la encuentra devuelve la posicion util, si no devuelve -1

// identificador es el nombre de una variable o de una funcion
struct entradaTS  search_identificador_marca(char * nom){
   int i=TOPE-1;

   if(strlen(nom)==0){
      printf(BG_COLOR_PURPLE "ERROR:" RESET_COLOR"En línea %i. Se ha introducido una cadena vacia \n",yylineno);
   }
   //Mientras que no encontremos la marca de inicio de bloque
   // NICO: Ahora mismo hace:lee toda la pila hasta encontrar el nombre mientras sea una funcion o una variable.
   //      si no lo encuentra devuelve -1 
   while(TS[i].entrada!=marca && (i > -1)){

      if(strcmp(TS[i].nombre,nom)==0 && ((TS[i].entrada==variable) || (TS[i].entrada==funcion)  || (TS[i].entrada==parametro_formal)) ){
         if(debug)
            printf("Uso %s, %s, %s, %s, %i,%s.\n", toStringEntrada(TS[i].entrada), TS[i].nombre, toStringTipoDato(TS[i].dato_referencia), toStringTipoDato(TS[i].dato_lista),TS[i].n_parametros,TS[i].nombre_traductor);
         return TS[i];
      }   
      i--;   
   }
   return TS[0];
}

struct entradaTS  search_identificador_pila(char * nom){
   struct entradaTS aux=TS[TOPE];
   int i=TOPE-1;

   if(strlen(nom)==0){
      printf(BG_COLOR_PURPLE "ERROR:" RESET_COLOR"En línea %i. Se ha introducido una cadena vacia \n",yylineno);
   }
   //Mientras que no encontremos la marca de inicio de bloque
   // NICO: Ahora mismo hace:lee toda la pila hasta encontrar el nombre mientras sea una funcion o una variable.
   //      si no lo encuentra devuelve -1 
   while((i > -1)){

      if(strcmp(TS[i].nombre,nom)==0 && ((TS[i].entrada==variable) || (TS[i].entrada==funcion) || (TS[i].entrada==parametro_formal)) ){
         if(debug)
            printf("Uso %s, %s, %s, %s, %i, %s.\n", toStringEntrada(TS[i].entrada), TS[i].nombre, toStringTipoDato(TS[i].dato_referencia), toStringTipoDato(TS[i].dato_lista),TS[i].n_parametros,TS[i].nombre_traductor);
         return TS[i];
      }
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
   for(int i = 0; i <= TOPE-1;i++){
      printf("Posicion %i, %s, %s, %s, %s, %i, %s.\n", i,toStringEntrada(TS[i].entrada), TS[i].nombre, toStringTipoDato(TS[i].dato_referencia), toStringTipoDato(TS[i].dato_lista),TS[i].n_parametros, TS[i].nombre_traductor);
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
      printf(BG_COLOR_PURPLE "Error semantico :" RESET_COLOR"En línea %i. %s no está declarado \n" ,yylineno, dato.nombre);
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

bool comprobar_for_pascal(struct entradaTS identificador, struct entradaTS dato1, struct entradaTS dato2){
	bool correcto = true;
	TipoDato tipo_variable = search_identificador_pila(identificador.nombre).dato_referencia;
	correcto &= tipo_variable == entero;
	if(correcto){
		correcto &= dato1.dato_referencia == entero;
		if(correcto){
			correcto &= dato2.dato_referencia == entero;
			if(!correcto) 
				printf(BG_COLOR_PURPLE "Error semantico." RESET_COLOR"Línea %d. El final debe ser un entero. Se tiene %s\n",yylineno, toStringTipoDato(dato2.dato_referencia));
		}
		else
			printf(BG_COLOR_PURPLE "Error semantico." RESET_COLOR"Línea %d.Se esperaba una asignación a entero. Se tiene %s\n",yylineno, toStringTipoDato(dato1.dato_referencia));
	}
	else{
		printf(BG_COLOR_PURPLE "Error semantico." RESET_COLOR"Línea %d.Se esperaba una variable ya declarada de tipo entero. Se tiene %s\n",yylineno, toStringTipoDato(tipo_variable));
	}

	return correcto;
}

bool comprobar_asignacion(struct entradaTS identificador, struct entradaTS dato1){
	bool correcto = true;
   struct entradaTS entradaAux;
	copiaStruct(&entradaAux,search_identificador_pila(identificador.nombre));
   TipoDato tipo_variable = entradaAux.dato_referencia;
   if(entradaAux.entrada != parametro_formal && entradaAux.entrada!= variable){
      printf(BG_COLOR_PURPLE "Error semantico." RESET_COLOR "Línea %d. %s no ha sido declarada\n",yylineno, identificador.nombre);
      return false;
   }
   else{
      correcto &= tipo_variable != desconocido;
      if(correcto){
         correcto &= dato1.dato_referencia == tipo_variable;
            if(!correcto) 
               printf(BG_COLOR_PURPLE "Error semantico." RESET_COLOR "Línea %d. Los tipos deben coincidir. Se tiene tipo variable: %s, tipo dato: %s\n",yylineno, toStringTipoDato(tipo_variable),toStringTipoDato(dato1.dato_referencia));
         
      }
      else{
         printf(BG_COLOR_PURPLE "Error semantico." RESET_COLOR "Línea %d. La variable debe estar declarada. Se tiene %s\n",yylineno, toStringTipoDato(tipo_variable));
      }
   }

	return correcto;
}

bool igualdad(struct entradaTS dato1, struct entradaTS dato2){
	return ((dato1.dato_referencia == dato2.dato_referencia) && (dato1.dato_referencia != lista)) || ( dato1.dato_referencia == lista && dato2.dato_referencia == lista && dato1.dato_lista == dato2.dato_lista); 
}

void comprueba_exp_logica(struct entradaTS dato){
	if(dato.dato_referencia != booleano)
		printf(BG_COLOR_PURPLE "Error semantico:" RESET_COLOR" En línea %i. Se esperaba una expresión lógica y se tiene %s \n",yylineno, toStringTipoDato(dato.dato_referencia) );
}


struct entradaTS operador_ternario(struct entradaTS dato1, struct entradaTS dato2, struct entradaTS dato3){
	struct entradaTS salida;

	salida.dato_referencia = lista;
	salida.dato_lista = desconocido;

	if( dato1.dato_referencia == lista ){
		if( dato1.dato_lista == dato2.dato_referencia){
			if(dato3.dato_referencia == entero){
				salida.dato_referencia = lista;
				salida.dato_lista = dato2.dato_referencia;
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

struct entradaTS operador_binario_aritmetico(struct entradaTS operador,struct entradaTS dato1,struct entradaTS dato2){
	struct entradaTS salida;
	salida.entrada = indefinido;
	salida.dato_referencia = desconocido;

	bool lista_1 = dato1.dato_referencia == lista;
	bool lista_2 = dato2.dato_referencia == lista;

	if(dato1.dato_referencia == real && dato2.dato_referencia == real){
		salida.dato_referencia = real;
	}
	else if(dato1.dato_referencia == entero && dato2.dato_referencia == entero){
		salida.dato_referencia = entero;
	}
	else if (!lista_1 && !lista_2){
		printf(BG_COLOR_PURPLE "Error semantico :" RESET_COLOR"Linea: %i.Se esperaban dos reales o dos enteros. Se tienen los tipos %s y %s \n", yylineno,
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
			printf(BG_COLOR_PURPLE "Error semantico :" RESET_COLOR"Linea: %i.Una lista de %s debe operarse con un %s. Se tiene %s \n", yylineno,
				toStringTipoDato(dato1.dato_lista),
				toStringTipoDato(dato1.dato_lista),
				toStringTipoDato(dato2.dato_referencia));
		}
	}

	if(lista_2){
		if(dato1.dato_referencia == real && dato2.dato_lista == real){
         if(operador.tipo_operador != entre && operador.tipo_operador != menos){
            salida.dato_referencia = lista;
            salida.dato_lista = real;
         }
         else{
            printf(BG_COLOR_PURPLE "Error semantico :" RESET_COLOR"Linea: %i.Una lista de %s. No puede dividir o restar %s \n", yylineno,
				toStringTipoDato(dato2.dato_lista),
				toStringTipoDato(dato2.dato_lista),
				toStringTipoDato(dato1.dato_referencia));
         }
		}
		else if(dato1.dato_referencia == entero && dato2.dato_lista == entero){
         if(operador.tipo_operador != entre && operador.tipo_operador != menos){
            salida.dato_referencia = lista;
            salida.dato_lista = entero;
         }
         else{
            printf(BG_COLOR_PURPLE "Error semantico :" RESET_COLOR"Linea: %i.Una lista de %s de %s. No puede dividir o restar %s \n", yylineno,
				toStringTipoDato(dato2.dato_lista),
				toStringTipoDato(dato2.dato_lista),
				toStringTipoDato(dato1.dato_referencia));
		      }
      }
		else {
			printf(BG_COLOR_PURPLE "Error semantico :" RESET_COLOR"Linea: %i.Una lista de %s debe operarse con un %s. Se tiene %s \n", yylineno,
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
					BG_COLOR_PURPLE "Error semantico :" RESET_COLOR"Linea: %i. se esperaba una lista y un real o entero o viceversa pero se obtuvo %s y %s \n", yylineno,
					toStringTipoDato(dato1.dato_referencia),
					toStringTipoDato(dato2.dato_referencia));   
      return salida;
}

	// Se entiende que el operador es binario less, less_eq, greater,greater_eq,  equal, not_equal
struct entradaTS operador_binario_logico_aritmetico(struct entradaTS operador, struct entradaTS dato1, struct entradaTS dato2 ){
	struct entradaTS salida;
	salida.dato_referencia = indefinido;
   if(dato1.dato_referencia == dato2.dato_referencia){
      if(dato1.dato_referencia==entero || dato1.dato_referencia==real)
         salida.dato_referencia=booleano;
      else
         printf(BG_COLOR_PURPLE "Error semantico :" RESET_COLOR"En línea %i. Solo se pueden comparar entero-entero y real-real",yylineno, toStringTipoDato(dato1.dato_referencia), toStringTipoDato(dato2.dato_referencia));
   }
	
	else 
		 printf(BG_COLOR_PURPLE "Error semantico :" RESET_COLOR"En línea %i. Solo se pueden comparar entero-entero y real-real",yylineno, toStringTipoDato(dato1.dato_referencia), toStringTipoDato(dato2.dato_referencia));
	return salida;
}

struct entradaTS operador_binario_igualdades(struct entradaTS operador, struct entradaTS dato1, struct entradaTS dato2 ){
	struct entradaTS salida;
	salida.dato_referencia = desconocido;
   if(igualdad(dato1,dato2)){
      salida.dato_referencia=booleano;
      
   }
	
	else {
		 printf(BG_COLOR_PURPLE "Error semantico :" RESET_COLOR"En línea %i.Los tipos de datos no son iguales para equal or not_equal. Dato tipo 1:%s Dato tipo 2: %s. Si son listas son de subtipos distintos",yylineno, toStringTipoDato(dato1.dato_referencia), toStringTipoDato(dato2.dato_referencia));
   }
   return salida;
}

struct entradaTS operador_binario(struct entradaTS operador, struct entradaTS dato1, struct entradaTS dato2) {
	struct entradaTS salida;
   switch (operador.tipo_operador)
	{
	case equal:
	case not_equal:
      return operador_binario_igualdades(operador, dato1, dato2);
      break;
	case and:
	case or:
	case xor:
      return operador_binario_logico(operador, dato1, dato2);
		break;
	case less:
	case greater:
	case less_eq:
	case greater_eq:
		return operador_binario_logico_aritmetico(operador, dato1, dato2);
		break;
	
	case mas:
	case menos:
	case por:
	case entre:
		return operador_binario_aritmetico( operador,dato1, dato2);
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
            printf(BG_COLOR_PURPLE "Error semantico :" RESET_COLOR"En línea %i. La variable %s no es una lista\n",yylineno, dato.nombre);
         }
         else
            coso.dato_referencia=entero;
      break;
      
      case interrogacion:
         if (dato.dato_referencia != lista){
            printf(BG_COLOR_PURPLE "Error semantico :" RESET_COLOR"En línea %i. La variable %s no es una lista\n",yylineno, dato.nombre);
         }
         else{
            coso.dato_referencia=dato.dato_lista;
         }
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
bool search_parametro(char * nom){
   struct  entradaTS dev;
   if(strlen(nom)==0){
      printf(BG_COLOR_PURPLE "Error semantico :" RESET_COLOR"En línea %i. Se ha introducido una cadena vacía \n",yylineno);
   }
   int i=TOPE-1;
   while(TS[i].entrada==parametro_formal){
      if(strcmp(TS[i].nombre,nom)==0 ){
         printf(BG_COLOR_PURPLE "Error semantico :" RESET_COLOR"En línea %i. Dos parametros con el mismo nombre \n",yylineno);
         return true;
      }
      i--;
   }
   return false;
}

bool search_parametros_funcion_declardo(char *param_nom){
   struct entradaTS dev;
   int j=TOPE-1;
   while(TS[j].entrada!=funcion && j>0){
      j--;
      
   }
   j--;
   if(j!=0){
      
      while(TS[j].entrada==parametro_formal){
         
         if(strcmp(TS[j].nombre,param_nom)==0)
            return true;
         j--;
      }
   }
   return false;
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
   //      si no lo encuentra devuelve -1 
   while(aux.entrada==marca && (i > -1)){
      if(strcmp(TS[i].nombre,nom)==0 && ((TS[i].entrada==variable) || (TS[i].entrada==funcion)) )
         return i;
      i--;
   }
   return -1;
}

#pragma endregion

#pragma region Traduccion

// Variables para la traduccion
int contadorVar = 0; // Para las variables de nuestro lenguaje
int contadorTem = 0; // Para las expresiones de nuestro lenguaje
int contadorEtiqueta = 0; // Contador de las etiquetas para los goto
const int tamInt2Char = 11; // Con cuantos caracteres puede representar un entero incluyendo el caracter '/0'
char nombreVar[5] = "var\0"; // Para las variables traducidas
char nombreTem[5] = "tem\0"; // Para las expresiones traducidas
char nombreEtiqueta[10] = "etiqueta\0"; // Indica nombre de ka  etiqueta

char nombreDev[200] = "\0"; // Variable temporal para guardar los nombres a devolver
char txsalida[200] = "";	// Para escribir en ficheros

int profundidad_bloque = 0;	// podría usarse para tabular. Lo uso para saber si son variables del main o de un bloque


// Ficheros a usar
FILE *fmain;
FILE *fvariables;
FILE *ffunciones;

//// FUNCIONES PARA LA GENERACIÓN DE CODIGO INTERMEDIO
// Funciones que generan los nombres
char* creaNombreTraduccion(TipoEntrada e){
   char auxInt2Char[tamInt2Char];
   char auxChar[4] = "\0";
   int auxNum;
   strcpy(nombreDev,"\0");
   switch(e){
      case variable:
         contadorVar++;
         auxNum = contadorVar;
         strcpy(auxChar,nombreVar);
         break;
      default:
         contadorTem++;
         auxNum = contadorTem;
         strcpy(auxChar,nombreTem);
         break;
   }
   sprintf(auxInt2Char,"%i",auxNum);
   strcat(nombreDev,auxChar);
   strcat(nombreDev,auxInt2Char);
   return nombreDev;
}

char* creaEtiquetaDevolver(){
   char auxInt2Char[tamInt2Char];
   strcpy(nombreDev,"\0");
   contadorEtiqueta++;
   sprintf(auxInt2Char,"%i",contadorEtiqueta);
   strcat(nombreDev,nombreEtiqueta);
   strcat(nombreDev,auxInt2Char);
   return nombreDev;
}

// Abre los ficheros correspondientes
// Modifico esto porque dec_var no se modifica
void generaFich(){

   // Creo los ficheros a usar
   fmain = fopen("generated.c","w");
   // fvariables = fopen("dec_var","w");
   ffunciones = fopen("dec_fun","w");

   // Añado que se puedan usar booleanos en todos
   fputs("#include <stdbool.h>\n",fmain);
   // fputs("#include <stdbool.h>\n",fvariables);
   fputs("#include <stdbool.h>\n",ffunciones);

   // Añado al archivo c lo que va a necesitar, que seria los printf, scanf, archivo de variables y de funciones
	 fputs("#include <stdio.h>\n#include \"dec_var\"\n#include \"dec_fun\"\n\n\nint main()",fmain);
   // Añado al archivo de funciones printf, scanf y las variables
   fputs("#include <stdio.h>\n#include \"dec_var\"\n",ffunciones);
}

// Cierra los ficheros
void closeFich(){
   fclose(fmain);
   // fclose(fvariables);
   fclose(ffunciones);
}

/* void writeTabs(FILE * f){
	for (int i = 0; i < profundidad_bloque; i++)
		fputs("\t", f);
} */

FILE* dondeEscriboExpresiones(int funcion_actual){
   if(funcion_actual<0){
      fputs("\t",fmain);
      return fmain;
   }
   else{
      for(int i=-1; i<funcion_actual;i++){
         fputs("\t",ffunciones);
      }
      return ffunciones;
   }
}

void normalizoTipoDato(char* tipoNormalizado, TipoDato dato_referencia){
   switch(dato_referencia){
      case entero:
         strcpy(tipoNormalizado,"int \0");
         break;
      case real:
         strcpy(tipoNormalizado,"float \0");
         break;
      case booleano:
         strcpy(tipoNormalizado,"bool \0");
         break;
      case caracter:
         strcpy(tipoNormalizado,"char \0");
         break;
      case lista:
         // Meter algo por aqui???
         break;
   }
}

// Escribe en el fichero de variables la variable de tipo dato_referencia y si es preciso dato_lista con nombre s
// Tambien escribe el nombre de los parametros (por nested funcions)
// si el parametro i es menor o igual a -1 significa que estoy en main, así que declaro las variables como globales (en su archivo)
// en otro caso entoy en las funciones y se declaran de forma estandar
void writeVarFile(int funcion_actual){
   FILE *file;
   if(funcion_actual<0) {
      file = fmain;
		// Si en Main, no es un while o bloque de otra estructura
		if(profundidad_bloque == 1)
			fseek(file, (-1) * sizeof("\nint main(){\n") * sizeof(char) + 1, SEEK_CUR);
   }
   else{
      file = ffunciones;
   }
   for(int i=-1; i<funcion_actual;i++){
      fputs("\t",file);
   }
   char auxDatoReferencia[20] = "\0";
   //char auxDatoLista[20] = "\0";
   normalizoTipoDato(&auxDatoReferencia,TS[TOPE-1].dato_referencia);
   
   // Supongo que el tipo de dato siempre es correcto
   if(TS[TOPE-1].dato_referencia!=lista){
      fputs(auxDatoReferencia,file);
      fputs(TS[TOPE-1].nombre_traductor,file);
      fputs(";\n",file);
   }
   else {
      ;
   }
	if(funcion_actual <= 0 && profundidad_bloque == 1)
		fputs("\nint main(){\n", file);

}

// Escribe en el fichero de funciones la declaracion de la funcion (recien insertada)
void writeFunctionFile(){
   char auxDatoReferencia[20] = "\0";
   //char auxDatoLista[20] = "\0";
   normalizoTipoDato(&auxDatoReferencia,TS[TOPE-1].dato_referencia);

   if(TS[TOPE-1].dato_referencia!=lista){
      fputs(auxDatoReferencia,ffunciones);
      fputs(TS[TOPE-1].nombre_traductor,ffunciones);
      fputs("(",ffunciones);
      int i = TOPE-1-TS[TOPE-1].n_parametros;
      while(i!=(TOPE-1)){
         switch(TS[i].dato_referencia){
         case entero:
            strcpy(auxDatoReferencia,"int \0");
            break;
         case real:
            strcpy(auxDatoReferencia,"float \0");
            break;
         case booleano:
            strcpy(auxDatoReferencia,"bool \0");
            break;
         case caracter:
            strcpy(auxDatoReferencia,"char \0");
            break;
         case lista:
            // Meter algo por aqui???
            break;
         }
         if(TS[i].dato_referencia!=lista){
            fputs(auxDatoReferencia,ffunciones);
            fputs(" ",ffunciones);
            fputs(TS[i].nombre,ffunciones);
         }
         else{
            ;// COSAS CON LISTAS
         }
         i++;

         if(i!=(TOPE-1))
            fputs(", ",ffunciones);
         else
            fputs(")",ffunciones);
      }
   }
   else {
      ;
   }
}

// Escribe el inicio de un bloque, si el parametro funcion_actual es menor o igual a -1 significa que estoy en main
// si es mayor estoy en el bloque de la funcion con nombre en la pila funcion_actual
void writeStartBlock(int funcion_actual){
   profundidad_bloque++;
   if(funcion_actual<0)
      fputs("{\n",fmain);
   else{
      fputs("{\n",ffunciones);
   }
   
}

// Escribe el final de un bloque, igual que los anteriores si el parametro es menor o igual a -1 estoy en el main
// en otro caso escribo en una función
void writeEndBlock(int funcion_actual){
   profundidad_bloque--;
   if(funcion_actual<0)
      fputs("}\n",fmain);
   else
      fputs("}\n",ffunciones);

}

// Escribe el resultado de una expresion cuando es un identificador
void writeExpresionIdentificador(struct entradaTS pasoExpresion,  struct entradaTS identificador,int funcion_actual){
   FILE *file = dondeEscriboExpresiones(funcion_actual);
   char auxDatoReferencia[20] = "\0";
   //char auxDatoLista[20] = "\0";
   normalizoTipoDato(&auxDatoReferencia,pasoExpresion.dato_referencia);
   if(pasoExpresion.dato_referencia != lista){
      fputs(auxDatoReferencia,file);
      fputs(pasoExpresion.nombre_traductor,file);
      fputs(" = ",file);
      fputs(identificador.nombre_traductor,file);
      fputs(";\n",file);
   }
   else{
      ;
   }
}

// Escrive el resultado de una expresion cuando es un literal
void writeExpresionLiteral(struct entradaTS pasoLiteral ,int funcion_actual){
   FILE *file = dondeEscriboExpresiones(funcion_actual);
   char auxDatoReferencia[20] = "\0";
   //char auxDatoLista[20] = "\0";
   normalizoTipoDato(&auxDatoReferencia,pasoLiteral.dato_referencia);
   if(pasoLiteral.dato_referencia != lista){
      fputs(auxDatoReferencia,file);
      fputs(pasoLiteral.nombre_traductor,file);
      fputs(" = ",file);
      fputs(pasoLiteral.nombre,file);
      fputs(";\n",file);
   }
   else{
      ;
   }

}

// Escribe el resultado de una expresion cuando es una funcion
// Se ha optado por usar una array de char tri dimensional que por cada función anidada (maximo 20) recuerda el nombre de su parametro
// cuando se se calcula la expresion de este (permito hasta 20 parametros en una funcion, sino peta).
// Finalmente el nombre es menos de tamaño 100 maximo.
void writeExpresionFuncion(struct entradaTS pasoExpresion, struct entradaTS funcion,char nombre_parametros[20][100],int funcion_actual){
   FILE *file = dondeEscriboExpresiones(funcion_actual);
   char auxDatoReferencia[20] = "\0";
   //char auxDatoLista[20] = "\0";w
   normalizoTipoDato(&auxDatoReferencia,pasoExpresion.dato_referencia);
   if(pasoExpresion.dato_referencia != lista){
      fputs(auxDatoReferencia,file);
      fputs(pasoExpresion.nombre_traductor,file);
      fputs(" = ",file);
      fputs(funcion.nombre_traductor,file);
      fputs("(",file);
      int i=0;
      
      while (i < funcion.n_parametros){
         fputs(nombre_parametros[i],file);
         i++;
         if(i!=funcion.n_parametros)
            fputs(",",file);
      }
      fputs(");\n",file);
   }
   // Si es lista, probablemente se pueda copiar uno a uno
   else{
      ;
   }
}

/* No es necesaria, copiaStruct copia el nombre del traductor tambien
// Escribe el resultado de una expresion evaluada entre parentesis (va a ser una igualdad rapida...)
void writeExpresionParentesis(struct entradaTS pasoExpresion, struct entradaTS expresionPartentesis){
   FILE *file = dondeEscriboExpresiones(funcion_actual);
   char auxDatoReferencia[20] = "\0";
   //char auxDatoLista[20] = "\0";w
   normalizoTipoDato(&auxDatoReferencia,pasoExpresion.dato_referencia);
   if(pasoExpresion.dato_referencia != lista){
      fputs(auxDatoReferencia,file);
      fputs(pasoExpresion.nombre_traductor,file);
      fputs(" = ",file);
      fputs(expresionPartentesis.nombre_traductor,file);
      fputs(";\n",file);
   }
   // Si es lista, probablemente se pueda copiar uno a uno
   else{
      ;
   }
}
*/

// Escribe el resultado de una expresion unaria
// Faltan las listas
void writeExpresionUnaria(struct entradaTS pasoExpresion, TipoOperador operador, struct entradaTS operando, int funcion_actual){
   FILE *file = dondeEscriboExpresiones(funcion_actual);
   char auxDatoReferencia[20] = "\0";
   //char auxDatoLista[20] = "\0";w
   normalizoTipoDato(&auxDatoReferencia,pasoExpresion.dato_referencia);
   fputs(auxDatoReferencia,file);
   fputs(pasoExpresion.nombre_traductor,file);
   fputs(" = ",file);
   switch(operador){
      case negacion:
         fputs("!",file);
      break;
      case sostenido:
         // Rellenar para lista
      break;
      
      case interrogacion:
         // Rellenar para lista
      break;
      
      case menos:
         fputs("-",file);
      break;
   };
   fputs(operando.nombre_traductor,file);
   fputs(";\n",file);
}

void writeExpresionBinaria(struct entradaTS pasoExpresion, struct entradaTS operando1, TipoOperador operador, struct entradaTS operando2, int funcion_actual){
    FILE *file = dondeEscriboExpresiones(funcion_actual);
    char auxDatoReferencia[20] = "\0";
    //char auxDatoLista[20] = "\0";
    normalizoTipoDato(&auxDatoReferencia,pasoExpresion.dato_referencia);
    // El operador xor va algo más a su rollo
    fputs(auxDatoReferencia,file);
    fputs(pasoExpresion.nombre_traductor,file);
    fputs(" = ",file);
    switch(operador){
        // Operadores booleanos
        case and:
            fputs(operando1.nombre_traductor,file);
            fputs(" && ",file);
            fputs(operando2.nombre_traductor,file);
        break;
        case or:
            fputs(operando1.nombre_traductor,file);
            fputs(" || ",file);
            fputs(operando2.nombre_traductor,file);
        break;
            
        case xor:
            // Esto es a nivel de bits, que sea lógico no tiene sentido
            // Vale si trato los booleanos como bits, en fin, matenme
            fputs(operando1.nombre_traductor,file);
            fputs(" ^ ",file);
            fputs(operando2.nombre_traductor,file);
            

        break;
        
        // Operadores de igualdad
        case equal:
            fputs(operando1.nombre_traductor,file);
            fputs(" == ",file);
            fputs(operando2.nombre_traductor,file);
        break;

      case not_equal:
         fputs(operando1.nombre_traductor,file);
         fputs(" != ",file);
         fputs(operando2.nombre_traductor,file);
      break;

      // Operadores de comparación
      case less:
         fputs(operando1.nombre_traductor,file);
         fputs(" < ",file);
         fputs(operando2.nombre_traductor,file);
         break;
      case greater:
         fputs(operando1.nombre_traductor,file);
         fputs(" > ",file);
         fputs(operando2.nombre_traductor,file);
         break;
      case less_eq:
         fputs(operando1.nombre_traductor,file);
         fputs(" <= ",file);
         fputs(operando2.nombre_traductor,file);
         break;
      case greater_eq:
         fputs(operando1.nombre_traductor,file);
         fputs(" >= ",file);
         fputs(operando2.nombre_traductor,file);
         break;

      // Operadores aritmeticos
      case mas:
         if(operando1.dato_referencia == operando2.dato_referencia){
            fputs(operando1.nombre_traductor,file);
            fputs(" + ",file);
            fputs(operando2.nombre_traductor,file);
         }
         else{
            ; // Seria el caso de una lista y un número si no hay errores semánticos
         }
         break;
      case entre:
         if(operando1.dato_referencia == operando2.dato_referencia){
            fputs(operando1.nombre_traductor,file);
            fputs(" / ",file);
            fputs(operando2.nombre_traductor,file);
         }
         else{
            ; // Seria el caso de una lista y un número si no hay errores semánticos
         }
         break;
      case por:
         if(operando1.dato_referencia == operando2.dato_referencia){
            fputs(operando1.nombre_traductor,file);
            fputs(" * ",file);
            fputs(operando2.nombre_traductor,file);
         }
         else{
            ; // Seria el caso de una lista y un número si no hay errores semánticos
         }
         break;
      case menos:
         if(operando1.dato_referencia == operando2.dato_referencia){
            fputs(operando1.nombre_traductor,file);
            fputs(" - ",file);
            fputs(operando2.nombre_traductor,file);
         }
         else{
            ; // Seria el caso de una lista y un número si no hay errores semánticos
         }
         break;

      // Operadores propiamente de listas
      case arroba:
         break;
      case menosmenos:
         break;
      case porcentaje:
         break;
      case doblepor:
         break;

   };
   fputs(";\n",file);
}

void getTypeforI_O(char* copiado, TipoDato dato_referencia){
   switch(dato_referencia){
      case entero:
         strcpy(copiado,"%i");
         break;
      case real:
         strcpy(copiado,"%f");
         break;
      case booleano:
         // No tiene sentido el leer un booleano, pero se puede ver como un entero a la hora de mostrar por pantallas
         strcpy(copiado,"%i");
         break;
      case caracter:
         strcpy(copiado,"%c");
         break;
      case lista:
         // Cosas para las listas
         break;
   }
}

void writeSentenciaEntrada(struct entradaTS expresionSalida, int funcion_actual){
   FILE *file = dondeEscriboExpresiones(funcion_actual);
   char auxDatoReferencia[20] = "\0";
   //char auxDatoLista[20] = "\0";
   getTypeforI_O(&auxDatoReferencia,expresionSalida.dato_referencia);
   switch(expresionSalida.dato_referencia){
      case entero:
      case real:
      case caracter:
         fputs("scanf(\"",file);
         fputs(auxDatoReferencia,file);
         fputs("\", &",file);
         fputs(expresionSalida.nombre_traductor,file);
         fputs(");\n",file);
         break;
      case booleano:
         fputs("int ",file);
         char auxNombre[100];
         strcpy(auxNombre,creaNombreTraduccion(indefinido));
         fputs(auxNombre,file);
         fputs(";\n",file);
         dondeEscriboExpresiones(funcion_actual); // Meto esto porque mete un tabulado de forma automatico y eso esta nice
         fputs("scanf(\"%d\", &",file);
         fputs(auxNombre,file);
         fputs(");\n",file);
         dondeEscriboExpresiones(funcion_actual); // Meto esto porque mete un tabulado de forma automatico y eso esta nice
         fputs(expresionSalida.nombre_traductor,file);
         fputs(" = ",file);
         fputs(auxNombre,file);
         fputs(";\n",file);
         // No tiene mucho sentido leer un booleano pero bueno, leo con un entero y luego igualo a ver que pasa
         break;
      case lista:
         // Hacer algo con la lista
         break;
   }

}

void writeSentenciaSalida(struct entradaTS expresionSalida, int funcion_actual){
   FILE *file = dondeEscriboExpresiones(funcion_actual);
   char auxDatoReferencia[20] = "\0";
   //char auxDatoLista[20] = "\0";
   getTypeforI_O(&auxDatoReferencia,expresionSalida.dato_referencia);
   switch(expresionSalida.dato_referencia){
      case entero:
      case real:
      case caracter:
      case booleano:
         fputs("printf(\"",file);
         fputs(auxDatoReferencia,file);
         fputs("\", ",file);
         fputs(expresionSalida.nombre_traductor,file);
         fputs(");\n",file);
         break;
      case lista:
         // Hacer algo con la lista
         break;
   }
}

// Hacer
void writeSentenciaReturn(struct entradaTS expresionReturn, int funcion_actual){
   FILE *file = dondeEscriboExpresiones(funcion_actual);
   char auxDatoReferencia[20] = "\0";
   if(expresionReturn.dato_referencia != lista){
      fputs("return ",file);
      fputs(expresionReturn.nombre_traductor, file);
      fputs(";\n",file);
   }
   else{
      // Hacer listas
   }
}

// Hacer
void writeSentenciaAsignacion(struct entradaTS expresion1, struct entradaTS expresion2, int funcion_actual){
   FILE *file = dondeEscriboExpresiones(funcion_actual);
   char auxDatoReferencia[20] = "\0";
   if(expresion1.dato_referencia != lista){
      fputs(expresion1.nombre_traductor,file);
      fputs(" = ", file);
      fputs(expresion2.nombre_traductor,file);
      fputs(";\n",file);
   }
}

// Hacer for, while e if

void escribe(char* s, int funcion_actual){
	if(funcion_actual < 0)
		fputs(s, fmain);
	else
		fputs(s, ffunciones);
}

struct entradaTS buscaAnteriorControl(){
	bool encontrado = false;
	struct entradaTS e;
	copiaStruct(&e, initialize);

	for(int i = TOPE - 1; i > 0 && !encontrado; i--){
		encontrado = TS[i].entrada == control;
		if(encontrado)
			copiaStruct(&e, TS[i]);
	}

	return e;
}

struct entradaTS actualizaBuscaControl(char * v){
	bool encontrado = false;
	struct entradaTS e;
	copiaStruct(&e, initialize);

	for(int i = TOPE - 1; i > 0 && !encontrado; i--){
		encontrado = TS[i].entrada == control;
		if(encontrado){
			strcpy(TS[i].descriptor_control.NombreVar, v);
			copiaStruct(&e, TS[i]);
		}
	}
	return e;
}

struct entradaTS generaEtiquetaControl(char * var){
	struct entradaTS condicional;

	copiaStruct(&condicional, initialize);
	condicional.entrada = control;

	strcpy(condicional.descriptor_control.Entrada, creaEtiquetaDevolver());
	strcpy(condicional.descriptor_control.Salida, creaEtiquetaDevolver());
	strcpy(condicional.descriptor_control.Else, creaEtiquetaDevolver());
	strcpy(condicional.descriptor_control.NombreVar, var);

	return condicional;
}

void empiezaSentenciaIf(int funcion_actual){
	struct entradaTS condicional;

	copiaStruct(&condicional, generaEtiquetaControl(""));

	sprintf(txsalida, "\n{\t// Comienza la traduccion del if\n");
	escribe(txsalida, funcion_actual);

	push(condicional);
}

void writeCondicion(char * variable, int func_actual){
	struct entradaTS condicional = actualizaBuscaControl(variable);

	sprintf(txsalida, "\n\tif (!%s) goto %s; \t// Ir al Else del if\n", condicional.descriptor_control.NombreVar, condicional.descriptor_control.Else);
	escribe(txsalida, func_actual);
}

void writeSaltoSalida(int funcion_actual){
	struct entradaTS e = buscaAnteriorControl();

	sprintf(txsalida, "\tgoto %s; \t// Ir a la salida del if\n", e.descriptor_control.Salida);
	escribe(txsalida, funcion_actual);
}

void writeEtiquedaEntrada(int funcion_actual){
	struct entradaTS e = buscaAnteriorControl();

	sprintf(txsalida, "\n%s: \t// Etiqueta Entrada del if\n", e.descriptor_control.Entrada);
	escribe(txsalida, funcion_actual);
}

void writeEtiquetaElse(int funcion_actual){
	struct entradaTS e = buscaAnteriorControl();

	sprintf(txsalida, "\n%s: \t// Etiqueta Else del if\n", e.descriptor_control.Else);
	escribe(txsalida, funcion_actual);
}

void writeEtiquetaSalida(int funcion_actual){
	struct entradaTS e = buscaAnteriorControl();

	sprintf(txsalida, "\n}\t// Fin del if\n%s: \t// Etiqueta de Salida del if\n", e.descriptor_control.Salida);
	escribe(txsalida, funcion_actual);

	pop();
}


void empiezaSentenciaWhile(int funcion_actual){
	struct entradaTS condicional;

	copiaStruct(&condicional, generaEtiquetaControl(""));

	sprintf(txsalida, "\n{ \t// Comienza la traduccion del while\n%s: \t// Etiqueta de entrada al while\n", condicional.descriptor_control.Entrada);
	escribe(txsalida, funcion_actual);

	push(condicional);
}

void writeCondicionSalida(char* variable, int f_actual){
	struct entradaTS condicional = actualizaBuscaControl(variable);

	sprintf(txsalida, "\n\tif (!%s) goto %s; \t// Ir a la salida del while\n", condicional.descriptor_control.NombreVar, condicional.descriptor_control.Salida);
	escribe(txsalida, f_actual);
}

void writeCierreWhile(int f_actual){
	struct entradaTS condicional = buscaAnteriorControl();

	sprintf(txsalida, "\n\tgoto %s; \t// Ir a la entrada del while\n}\t//Fin while\n%s:\t// Etiqueta de salida del while\n", condicional.descriptor_control.Entrada, condicional.descriptor_control.Salida);
	escribe(txsalida, f_actual);

	pop();
}


void empiezaSentenciaFor(int f_actual){
	struct entradaTS condicional;
	copiaStruct(&condicional, generaEtiquetaControl(""));

	sprintf(txsalida, "\n{\t// Comienza la traduccion del for\n");
	escribe(txsalida, f_actual);

	push(condicional);
}

void writeAsignacionInicial(char* nombre, struct entradaTS exp, int f_actual){
	struct entradaTS condicional = actualizaBuscaControl(search_identificador_pila(nombre).nombre_traductor);

	sprintf(txsalida, "\t// Sentencia de asignacion inicial del for\n");
	escribe(txsalida, f_actual);
	writeSentenciaAsignacion(search_identificador_pila(nombre),exp,f_actual);
	sprintf(txsalida, "\n%s: \t// Etiqueta entrada for\n\t// Expresion final del for\n", condicional.descriptor_control.Entrada);
	escribe(txsalida, f_actual);
}

void writeCondicionalFor(char* final, int f_actual){
	struct entradaTS condicional = buscaAnteriorControl();

	char* tmp = creaNombreTraduccion(indefinido);
	
	sprintf(txsalida, "\tbool %s = %s <= %s ; \t// Variable de control del for\n", tmp, condicional.descriptor_control.NombreVar, final);
	escribe(txsalida, f_actual);

	sprintf(txsalida, "\tif( !%s ) goto %s ; \t// Ir a salida del for\n", tmp, condicional.descriptor_control.Salida);
	escribe(txsalida, f_actual);
}

void writeCierreFor(int f_actual){
	struct entradaTS condicional = buscaAnteriorControl();

	sprintf(txsalida, "\t%s = %s + 1 ; \t// Incremento del for\n", condicional.descriptor_control.NombreVar, condicional.descriptor_control.NombreVar);
	escribe(txsalida, f_actual);
	sprintf(txsalida, "\tgoto %s ; \t// Salto entrada del for\n", condicional.descriptor_control.Entrada);
	escribe(txsalida, f_actual);
	sprintf(txsalida, "}\t// Fin del for\n%s: \t// Etiqueta Salida del for\n", condicional.descriptor_control.Salida);
	escribe(txsalida, f_actual);

	pop();
}


#pragma endregion
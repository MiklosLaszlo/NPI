%{
#define YYSTYPE struct entradaTS
#include "lex.yy.c"
#include "p5.h"
#define RESET_COLOR    "\x1b[0m"
#define BG_COLOR_YELLOW     "\x1B[43m"
#define BG_COLOR_PURPLE     "\x1B[45m"
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
   TipoEntrada entrada;      // Indica el tipo de entrada

   char nombre[100];                 // Contendra los caracteres que forman el identificador en nuestro lenguaje
   char nombre_traductor[100];       // Contendra los caracteres que forman el identificador al traducirlo a C
   
   TipoDato dato_referencia; // En caso de que entrada sea funcion,variable
                             // o parametro formal indica el tipo de dato al que hace referencia
   TipoDato dato_lista;      //tipo de datos que contiene la lista                    
   unsigned int n_parametros;  //Si tipoDato  es funcion indica el numero de parametros o el tamaño de la lista
   unsigned int puntero_lista; // Puntero que señala la posición en la lista
   
   TipoOperador tipo_operador; // En caso de ser operador, qué operador es  
};

	const struct entradaTS initialize = {.entrada = indefinido, .dato_referencia=desconocido, .dato_lista=desconocido, .n_parametros=0, .puntero_lista=0};
#endif

void yyerror( char *msg ){
	//fprintf(stderr, BG_COLOR_YELLOW "YY Error sintáctico"  RESET_COLOR " Línea %d, No se esperaba el lexema \'%s\'\n" ,yylineno, yytext);
}
void explicacion_error_sintactico( char * msg ) {
	fprintf(stderr, BG_COLOR_YELLOW "Error sintáctico"  RESET_COLOR " Línea %d, No se esperaba \'%s\'. %s\n" ,yylineno, yytext, msg);
}
void explicacion_error_semantico( char * msg ) {
	fprintf(stderr, BG_COLOR_PURPLE "Error semántico"  RESET_COLOR " Línea: %d.Error: %s\n" ,yylineno, msg);
}
int n_parametros=0;
int funcion_actual=-1;
int funcion_analizando=-1;
TipoDato daton_anterior=desconocido;
char funcion_declarandose[20][100];
char funcion_usandose[20][100];
char parametros_funcion_usandose[20][20][100];
%}
// TOKENS
//********************
//REVISAR
%right IGUAL
// Operadores binarios 
//%left AND OR XOR
%left LOGICOS
//%left EQUAL NOTEQUAL
%left IGUALDAD
//%left LESS GREATER LESSEQ GREATEREQ
%left COMPARACION
%left MAS MENOSMENOS
//%left POR DIV
%left MULTIPLICATIVO
// Operados unarios
%left NOT
// Operado unarios de las listas
%left SOSTENIDO
%left INTERROGACION
%nonassoc TER1 
// Operadores binarios de lista
%left ARROBA
%left PORCENTAJE
%left DOBLEPOR
%left MENOS
%left LITERAL
%left IDENTIFICADOR
%left LLAVEIZQ
%right LLAVEDCH
%left CORIZQ
%right CORDCH
%left PARIZQ
%right PARDCH
// Terciario de lista
//*******************
%token MAIN
%token TYPE
%token IF
%token THEN
%token ELSE
%token FOR
%token TO 
%token DO
%token WRITE
%token READ
%token COMA
%token PYC
%token RETURN
%token WHILE
%token PRINCIPIOLISTA
%token MOVLISTA
%token INIVARIABLES
%token FINVARIABLES
%%
// Producciones
// ######################
programa : MAIN {generaFich();} bloque PYC {closeFich();}
		  | MAIN {generaFich();} error { yyerrok; explicacion_error_sintactico("Debe incluir un bloque tras el main"); } 
		  | MAIN {generaFich();} bloque error { yyerrok; explicacion_error_sintactico("El programa debe acabar con \';\'"); }
bloque : LLAVEIZQ	{writeStarBlock(funcion_actual);InsertarMarca();} 
        declar_de_variables_locales
        declar_de_subprogs
        sentencias
        LLAVEDCH {writeEndBlock(funcion_actual);EliminarBloque();}
		| LLAVEIZQ {writeStarBlock(funcion_actual);InsertarMarca();}
		declar_de_variables_locales 
		declar_de_subprogs 
		LLAVEDCH {writeEndBlock(funcion_actual);EliminarBloque();}
		//  | LLAVEIZQ error { yyerrok; explicacion_error_sintactico('Error en el orden del bloque'); } 
		//  | LLAVEIZQ declar_de_variables_locales error { yyerrok; explicacion_error_sintactico("Error en el orden del bloque"); } 
		//  | LLAVEIZQ declar_de_variables_locales declar_de_subprogs error { yyerrok; explicacion_error_sintactico('Error en el orden del bloque'); } 
		  | LLAVEIZQ {writeStarBlock(funcion_actual);InsertarMarca();} declar_de_variables_locales declar_de_subprogs error { yyerrok; explicacion_error_sintactico("El bloque debe acabar con }"); } 
declar_de_variables_locales : INIVARIABLES variables_locales FINVARIABLES 
        | 
		| INIVARIABLES error { yyerrok; explicacion_error_sintactico("Error en el bloque de declaración de variables"); } 
		| INIVARIABLES variables_locales error { yyerrok; explicacion_error_sintactico("Error, debe cerrarse el bloque de declaración de variables"); }
variables_locales : variables_locales cuerpo_declar_variables PYC
        | cuerpo_declar_variables PYC
  		  | cuerpo_declar_variables error { yyerrok; explicacion_error_sintactico("Error, la declaración debe acabar en \";\"");};
cuerpo_declar_variables : tipo_basico lista_identificadores 
		  | tipo_basico error { yyerrok; explicacion_error_sintactico("Error, después del tipo básico va una lista de identificadores"); }
lista_identificadores : lista_identificadores
		 COMA 			
		 IDENTIFICADOR  { if(search_parametros_funcion_declardo($3.nombre)) explicacion_error_semantico("Redeclarando un parámetro de la función");
			else if(search_identificador_marca($3.nombre).entrada == marca) 
			{strcpy($3.nombre_traductor,creaNombreTraduccion(variable)); push2($3,variable);writeVarFile(funcion_actual);} 
			else ErrorDeclaradaEnBLoque($3);
						if(search_parametros_funcion_declardo($3.nombre)) explicacion_error_semantico("Redeclarando un parámetro de la función");
						} 
        | IDENTIFICADOR {if(search_parametros_funcion_declardo($1.nombre)) explicacion_error_semantico("Redeclarando un parámetro de la función");
			else if(search_identificador_marca($1.nombre).entrada == marca) {
				strcpy($1.nombre_traductor,creaNombreTraduccion(variable));  push2($1,variable);writeVarFile(funcion_actual);} 
			else ErrorDeclaradaEnBLoque($1);
		} 
		  // El error de esto lo englobo en el de arriba
declar_de_subprogs : declar_de_subprogs declarar_funcion
        | ;
declarar_funcion : tipo_basico 
				  IDENTIFICADOR {funcion_actual++;strcpy(funcion_declarandose[funcion_actual],$2.nombre);} 
				  parametros {$2.n_parametros=n_parametros;if(search_identificador_marca($2.nombre).entrada == marca) 
				  {strcpy($2.nombre_traductor,$2.nombre);push2($2,funcion);writeFunctionFile();} 
				  else ErrorDeclaradaEnBLoque($2); n_parametros=0;}
				  bloque 
				  PYC {if(strcmp(funcion_declarandose[funcion_actual],"")!=0) explicacion_error_semantico("No se ha realizado un return de la función");funcion_actual--;}
		  | tipo_basico error { yyerrok; explicacion_error_sintactico("Error, al declarar una función tras el tipo básico va el identificador"); }
		  | tipo_basico IDENTIFICADOR {funcion_actual++;strcpy(funcion_declarandose[funcion_actual],$2.nombre);}   error { yyerrok; explicacion_error_sintactico("Error, en una función tras el identificador vienen los parámetros."); } 
		  | tipo_basico IDENTIFICADOR {funcion_actual++;strcpy(funcion_declarandose[funcion_actual],$2.nombre);}   parametros {$2.n_parametros=n_parametros;if(search_identificador_marca($2.nombre).entrada == marca) push2($2,funcion); else ErrorDeclaradaEnBLoque($2); n_parametros=0;} error { yyerrok; explicacion_error_sintactico("Error, en una función tras los parámetros viene un bloque."); }
		  | tipo_basico IDENTIFICADOR {funcion_actual++;strcpy(funcion_declarandose[funcion_actual],$2.nombre);}   parametros {$2.n_parametros=n_parametros;if(search_identificador_marca($2.nombre).entrada == marca) push2($2,funcion); else ErrorDeclaradaEnBLoque($2); n_parametros=0;} bloque error { yyerrok; explicacion_error_sintactico("Error, la declaración de funciones acaba en \";\"."); }  
parametros : PARIZQ PARDCH
        | PARIZQ lista_parametros PARDCH 
		  //| PARIZQ error { yyerrok; explicacion_error_sintactico("Error, debe introducir una lista de parámetros separados por comas"); }
		| PARIZQ lista_parametros error { yyerrok; explicacion_error_sintactico("Error, debe cerrarse el paréntesis"); }
lista_parametros : tipo_basico IDENTIFICADOR {n_parametros+=1;if(!search_parametro($2.nombre))
		{strcpy($2.nombre_traductor,$2.nombre);push2($2,parametro_formal);}}
        | lista_parametros COMA tipo_basico IDENTIFICADOR {n_parametros+=1;if(!search_parametro($4.nombre))
		{strcpy($4.nombre_traductor,$4.nombre);push2($4,parametro_formal);}}
		| tipo_basico error { yyerrok; explicacion_error_sintactico("Error, tras el tipo básico debe introducir un identificador"); }
tipo_basico : TYPE { $$.entrada = $1.entrada; }
		// Ya no hay que comparar atributos. Las cosas finales las tenemos
sentencias : sentencias sentencia 
        | sentencia ;
sentencia : sentencia_asignacion
        | sentencia_if 		
        | sentencia_while
        | sentencia_entrada
        | sentencia_salida
        | sentencia_return 
        | sentencia_for_pascal
        | sentencia_lista
        | PYC ;
sentencia_asignacion : IDENTIFICADOR IGUAL expresion PYC {comprobar_asignacion($1,$3);}
		  | IDENTIFICADOR IGUAL error { yyerrok; explicacion_error_sintactico("Error, el identificador debe estar igualada a una expresión");};
		  | IDENTIFICADOR IGUAL expresion error { yyerrok; explicacion_error_sintactico("Error, la asignación debe acabar en \";\"");}
sentencia_if : IF PARIZQ expresion PARDCH THEN bloque ELSE bloque { comprueba_exp_logica($3); }
        | IF PARIZQ expresion PARDCH THEN bloque { comprueba_exp_logica($3); }
		  | IF error { yyerrok; explicacion_error_sintactico("Error, tras el if debe introducir la condición entre paréntesis"); }
		  | IF PARIZQ error { yyerrok; explicacion_error_sintactico("Error, la condición debe ser una expresión"); }
		  | IF PARIZQ expresion error { yyerrok; explicacion_error_sintactico("Error, debe cerrar el paréntesis"); }
		  | IF PARIZQ expresion PARDCH error { yyerrok; explicacion_error_sintactico("Error, se esperaba \"then\""); }
		  | IF PARIZQ expresion PARDCH THEN error { yyerrok; explicacion_error_sintactico("Error, se esperaba un bloque"); }
	//	  | IF PARIZQ expresion PARDCH THEN bloque error { yyerrok; explicacion_error_sintactico("Error, "); }
		  | IF PARIZQ expresion PARDCH THEN bloque ELSE error { yyerrok; explicacion_error_sintactico("Error, se esperaba un bloque"); }
sentencia_while : WHILE PARIZQ expresion PARDCH bloque { comprueba_exp_logica($3); }
		  | WHILE error { yyerrok; explicacion_error_sintactico("Error, introduzca la condición entre paréntesis"); }
		  | WHILE PARIZQ error { yyerrok; explicacion_error_sintactico("Error, la condición debe ser una expresión"); }
		  | WHILE PARIZQ expresion error { yyerrok; explicacion_error_sintactico("Error, debe cerrarse el paréntesis"); }
		  | WHILE PARIZQ expresion PARDCH error { yyerrok; explicacion_error_sintactico("Error, se esperaba un bloque"); }
sentencia_entrada : READ PARIZQ lista_entrada PARDCH PYC 
    	  | READ error { yyerrok; explicacion_error_sintactico("Error, se esperaba un paréntesis"); }
		  | READ PARIZQ error { yyerrok; explicacion_error_sintactico("Error, debe introducir una lista de identificadores separados por comas"); }
		  | READ PARIZQ lista_entrada error { yyerrok; explicacion_error_sintactico("Error, debe cerrar el paréntesis de la lista de identificadores"); }
		  | READ PARIZQ lista_entrada PARDCH error { yyerrok; explicacion_error_sintactico("Error, se esperaba \";\""); }
sentencia_salida : WRITE PARIZQ lista_salida PARDCH PYC
		  | WRITE error { yyerrok; explicacion_error_sintactico("Error, se esperaba un paréntesis"); }
		  | WRITE PARIZQ error { yyerrok; explicacion_error_sintactico("Error, debe introducir una lista de expresiones separados por comas"); }
		  | WRITE PARIZQ lista_salida error { yyerrok; explicacion_error_sintactico("Error, debe cerrar el paréntesis de la lista de expresiones"); }
		  | WRITE PARIZQ lista_salida PARDCH error { yyerrok; explicacion_error_sintactico("Error, se esperaba \";\""); }
sentencia_return : RETURN expresion PYC {if(funcion_actual>-1){if(strcmp(funcion_declarandose[funcion_actual],"")!=0) if(!igualdad($2,search_identificador_pila(funcion_declarandose[funcion_actual]))) {explicacion_error_semantico("No se devuelve el tipo de la función");}
			strcpy(funcion_declarandose[funcion_actual],"");}} 
		  | RETURN error { yyerrok; explicacion_error_sintactico("Error, debe devolverse una expresión"); }
		  | RETURN expresion error { yyerrok; explicacion_error_sintactico("Error, debe acabar en \";\""); }
sentencia_for_pascal : FOR IDENTIFICADOR IGUAL expresion TO expresion DO bloque {comprobar_for_pascal($2,$4,$6);} 
		  | FOR error { yyerrok; explicacion_error_sintactico("Error, se esperaba una sentencia de asignación"); }
		  | FOR sentencia_asignacion error { yyerrok; explicacion_error_sintactico("Error, se esperaba la palabra \"to\""); }
		  | FOR sentencia_asignacion TO error { yyerrok; explicacion_error_sintactico("Error, se esperaba una expresión"); }
		  | FOR sentencia_asignacion TO expresion error { yyerrok; explicacion_error_sintactico("Error, se esperaba la palabra \"do\""); }
		  | FOR sentencia_asignacion TO expresion DO error { yyerrok; explicacion_error_sintactico("Error, se esperaba un bloque"); }
sentencia_lista : IDENTIFICADOR MOVLISTA PYC {
				copiaStruct(&$$,search_identificador_pila($1.nombre)); 
				if($$.entrada!=variable && $$.entrada!=parametro_formal)
				 	ErrorNoDeclarada($1);
				else if($$.dato_referencia!=lista)
					explicacion_error_semantico("El tipo de dato deberia ser una lista"); 
					}
        | PRINCIPIOLISTA IDENTIFICADOR PYC {
			copiaStruct(&$$,search_identificador_pila($2.nombre)); 
			if($$.entrada!=variable && $$.entrada!=parametro_formal) 
				ErrorNoDeclarada($2);
			else if($$.dato_referencia!=lista) 
			explicacion_error_semantico("El tipo de dato deberia ser una lista"); }
		  //| IDENTIFICADOR error { yyerrok; explicacion_error_sintactico("Error, se esperaba \"<<\""); }
		  /* | IDENTIFICADOR MOVLISTA error { yyerrok; explicacion_error_sintactico("Error, debe acabar en \";\""); }
		  | PRINCIPIOLISTA error { yyerrok; explicacion_error_sintactico("Error, se esperaba un identificador"); }
		  | PRINCIPIOLISTA IDENTIFICADOR error { yyerrok; explicacion_error_sintactico("Error, debe acabar en \";\""); } */
lista_entrada : lista_entrada COMA IDENTIFICADOR {copiaStruct(&$$,search_identificador_pila($3.nombre)); if($$.entrada!=variable && $$.entrada!=parametro_formal) {ErrorNoDeclarada($3);}}
        | IDENTIFICADOR  {copiaStruct(&$$,search_identificador_pila($1.nombre)); if($$.entrada!=variable && $$.entrada!=parametro_formal) {ErrorNoDeclarada($1);}}

lista_salida : lista_salida COMA expresion 
		| expresion

expresion : PARIZQ expresion PARDCH { copiaStruct(&$$, $2);}
		| OPUNI expresion %prec NOT { copiaStruct(&$$, operador_unario($2,$1) ); strcpy($$.nombre_traductor,creaNombreTraduccion(indefinido));  
					writeExpresionUnaria($$,$1.tipo_operador,$2,funcion_actual);}
		| expresion OPBIN expresion %prec LOGICOS {copiaStruct(&$$,operador_binario($2, $1, $3)); strcpy($$.nombre_traductor,creaNombreTraduccion(indefinido));
					writeExpresionBinaria($$,$1,$2.tipo_operador,$3,funcion_actual);  }
		| expresion TER1 expresion ARROBA expresion { copiaStruct(&$$,operador_ternario($1,$3,$5)); }
		| IDENTIFICADOR {copiaStruct(&$$,search_identificador_pila($1.nombre)); 
			if($$.entrada!=variable && $$.entrada!=parametro_formal) ErrorNoDeclarada($1);
			else {$$.entrada =indefinido; 
				strcpy($$.nombre_traductor,creaNombreTraduccion(indefinido));
				writeExpresionIdentificador($$,search_identificador_pila($1.nombre),funcion_actual); }}
		| llamar_funcion {copiaStruct(&$$,$1); $$.entrada=variable;}
		| agregado {$$.dato_referencia=lista;$$.dato_lista=$1.dato_referencia;$$.entrada=variable;}
		| LITERAL {copiaStruct(&$$,$1);strcpy($$.nombre_traductor,creaNombreTraduccion(indefinido));writeExpresionLiteral($$,funcion_actual);}
		| PARIZQ error { yyerrok; explicacion_error_sintactico("Error, se esperaba una expresión"); }
		//  | PARIZQ expresion error { yyerrok; explicacion_error_sintactico("Error, debe cerrarse el paréntesis"); }
		| OPUNI error { yyerrok; explicacion_error_sintactico("Error, se esperaba una expresión"); }
		| expresion OPBIN error { yyerrok; explicacion_error_sintactico("Error, se esperaba una expresión"); }
		| expresion TER1 error { yyerrok; explicacion_error_sintactico("Error, se esperaba una expresión"); }
		| expresion TER1 expresion error { yyerrok; explicacion_error_sintactico("Error, se esperaba \"@\""); }
		//  | expresion TER1 expresion ARROBA error { yyerrok; explicacion_error_sintactico("Error, se esperaba una expresión"); } 
llamar_funcion : IDENTIFICADOR {funcion_analizando++;strcpy(funcion_usandose[funcion_analizando],$1.nombre);}
		argumentos
		{copiaStruct(&$$,search_identificador_pila($1.nombre));
		strcpy($$.nombre_traductor,creaNombreTraduccion(indefinido));
		if($$.entrada!=funcion) {ErrorNoDeclarada($1);$$.entrada =indefinido;} 
		else if(n_parametros!=$$.n_parametros) explicacion_error_semantico("Numero de argumentos incorrecto");
		else writeExpresionFuncion($$,search_identificador_pila($1.nombre),parametros_funcion_usandose[funcion_analizando],funcion_actual); 
		funcion_analizando--;n_parametros=0;} //{/*Hace falta algo como push funccion*/}
        
argumentos : PARIZQ lista_argumentos PARDCH
		| PARIZQ lista_argumentos error { yyerrok; explicacion_error_sintactico("Error, la lista de argumentos debe acabar con paréntesis"); }
lista_argumentos : expresion {n_parametros+=1; struct entradaTS s2;
		strcpy(parametros_funcion_usandose[funcion_analizando][n_parametros-1],$1.nombre_traductor);
		copiaStruct(&s2,getArg(funcion_usandose[funcion_analizando],n_parametros));
		if(!igualdad($1,s2)) explicacion_error_semantico("No coinciden los tipos");
		}

        | lista_argumentos COMA expresion {n_parametros+=1; struct entradaTS s2; 	
		strcpy(parametros_funcion_usandose[funcion_analizando][n_parametros-1],$3.nombre_traductor);
		copiaStruct(&s2,getArg(funcion_usandose[funcion_analizando],n_parametros));
		if(!igualdad($3,s2)) explicacion_error_semantico("No coinciden los tipos");
		}
        | ;
agregado : CORIZQ lista_expresiones CORDCH {$$.dato_referencia=lista;$$.dato_lista=daton_anterior;daton_anterior=desconocido;}
		  | CORIZQ error {yyerrok; explicacion_error_sintactico("Error, debe proporcionar una lista de expresiones separadas por comas");}
		  | CORIZQ lista_expresiones error { yyerrok; explicacion_error_sintactico("Error, debe cerrar el corchete"); }

lista_expresiones : lista_expresiones COMA expresion {if (daton_anterior==desconocido) daton_anterior=$3.dato_referencia;
		if(daton_anterior!=$3.dato_referencia) explicacion_error_semantico("Todos los elementos de una lista deben de ser del mismo tipo");
		if(daton_anterior==lista) explicacion_error_semantico("No se puede hacer listas de listas"); 
		$$.dato_referencia=daton_anterior;}
        | expresion {if (daton_anterior==desconocido) daton_anterior=$1.dato_referencia;
		if(daton_anterior!=$1.dato_referencia) explicacion_error_semantico("Todos los elementos de una lista deben de ser del mismo tipo");
		if(daton_anterior==lista) explicacion_error_semantico("No se puede hacer listas de listas");
		$$.dato_referencia=daton_anterior;}

OPUNI : NOT { copiaStruct(&$$,$1);} // Para boolenos
        | SOSTENIDO { copiaStruct(&$$,$1);} // Listas
        | INTERROGACION { copiaStruct(&$$,$1);} // Listas
        | MENOS { copiaStruct(&$$,$1);} // Para enteros y reales
OPBIN : IGUALDAD { copiaStruct(&$$,$1);}
        | MENOS { copiaStruct(&$$,$1);}
        | LOGICOS { copiaStruct(&$$,$1);}
        | MAS { copiaStruct(&$$,$1);}
        | MULTIPLICATIVO { copiaStruct(&$$,$1);}
        | COMPARACION { copiaStruct(&$$,$1);}
        | ARROBA { copiaStruct(&$$,$1);}
        | MENOSMENOS { copiaStruct(&$$,$1);}
        | PORCENTAJE { copiaStruct(&$$,$1);}
        | DOBLEPOR { copiaStruct(&$$,$1);}
%% 


int main (int argc, char** argv) {
    // Se comprueba que se recibe 1 argumento (nombre del fichero fuente)
	if (argc <= 1) {
        printf("\nError al ejecutar la aplicación...\n");
        printf("Uso: %s nombre_fichero_fuente\n", argv[0]);
		exit(-1);
	}
    // Se abre el fichero recibido por parámetro
    yyin = fopen(argv[1], "r");
    // Si "yyin" es nulo no se ha podido abrir el fichero
    if (yyin == NULL) {
        printf ("\nError al abrir el fichero %s\n", argv[1]);
        exit (-2);
    }
    yyparse();
}
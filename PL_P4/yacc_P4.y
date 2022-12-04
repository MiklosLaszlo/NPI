%{
#define YYSTYPE struct entradaTS
#include "lex.yy.c"
#include "p4.h"
#define RESET_COLOR    "\x1b[0m"
#define BG_COLOR_YELLOW     "\x1B[43m"
#define BG_COLOR_GREEN     "\x1B[32m"
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
   TipoEntrada entrada = indefinido;      // Indica el tipo de entrada
   char nombre[100]; 
   // char valor[50];              // Contendra los caracteres que forman el identificador
   TipoDato dato_referencia; // En caso de que entrada sea funcion,variable
   TipoDato dato_referencia = desconocido; // En caso de que entrada sea funcion,variable
                             // o parametro formal indica el tipo de dato al que hace referencia
   TipoDato dato_lista;      //tipo de datos que contiene la lista                    
   unsigned int n_parametros;  //Si tipoDato  es funcion indica el numero de parametros 
	TipoOperador tipo_operador; // En caso de ser operador, qué operador es  
};
#endif

void yyerror( char *msg ){
	//fprintf(stderr, BG_COLOR_YELLOW "YY Error sintáctico"  RESET_COLOR " Línea %d, No se esperaba el lexema \'%s\'\n" ,yylineno, yytext);
}
void explicacion_error_sintactico( char * msg ) {
	fprintf(stderr, BG_COLOR_YELLOW "Error sintáctico"  RESET_COLOR " Línea %d, No se esperaba \'%s\'. %s\n" ,yylineno, yytext, msg);
}
void explicacion_error_semantico( char * msg ) {
	fprintf(stderr, BG_COLOR_GREEN "Error semántico"  RESET_COLOR " Línea: %d.Error: %s\n" ,yylineno, msg);
}
int n_parametros=0;
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
programa : MAIN bloque PYC 
		  | MAIN error { yyerrok; explicacion_error_sintactico("Debe incluir un bloque tras el main"); } 
		  | MAIN bloque error { yyerrok; explicacion_error_sintactico("El programa debe acabar con \';\'"); }
bloque : LLAVEIZQ	{InsertarMarca();}
        declar_de_variables_locales
        declar_de_subprogs
        sentencias
        LLAVEDCH {EliminarBloque();}
		| LLAVEIZQ {InsertarMarca();}
		declar_de_variables_locales 
		declar_de_subprogs 
		LLAVEDCH {EliminarBloque();}
		//  | LLAVEIZQ error { yyerrok; explicacion_error_sintactico('Error en el orden del bloque'); } 
		//  | LLAVEIZQ declar_de_variables_locales error { yyerrok; explicacion_error_sintactico("Error en el orden del bloque"); } 
		//  | LLAVEIZQ declar_de_variables_locales declar_de_subprogs error { yyerrok; explicacion_error_sintactico('Error en el orden del bloque'); } 
		  | LLAVEIZQ {InsertarMarca();} declar_de_variables_locales declar_de_subprogs error { yyerrok; explicacion_error_sintactico("El bloque debe acabar con }"); } 
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
		 IDENTIFICADOR  {push2($1,variable);} 
        | IDENTIFICADOR {push2($1,variable);} 
		  // El error de esto lo englobo en el de arriba
declar_de_subprogs : declar_de_subprogs declarar_funcion
        | ;
declarar_funcion : tipo_basico 
				  IDENTIFICADOR 
				  parametros {$2.n_parametros=n_parametros;push2($2,funcion);n_parametros=0;}
				  bloque 
				  PYC 
		  | tipo_basico error { yyerrok; explicacion_error_sintactico("Error, al declarar una función tras el tipo básico va el identificador"); }
		  | tipo_basico IDENTIFICADOR error { yyerrok; explicacion_error_sintactico("Error, en una función tras el identificador vienen los parámetros."); } 
		  | tipo_basico IDENTIFICADOR parametros {$1.n_parametros=n_parametros;push2($1,funcion);n_parametros=0;} error { yyerrok; explicacion_error_sintactico("Error, en una función tras los parámetros viene un bloque."); }
		  | tipo_basico IDENTIFICADOR parametros {$1.n_parametros=n_parametros;push2($1,funcion);n_parametros=0;} bloque error { yyerrok; explicacion_error_sintactico("Error, la declaración de funciones acaba en \";\"."); }  
parametros : PARIZQ PARDCH
        | PARIZQ lista_parametros PARDCH 
		  //| PARIZQ error { yyerrok; explicacion_error_sintactico("Error, debe introducir una lista de parámetros separados por comas"); }
		| PARIZQ lista_parametros error { yyerrok; explicacion_error_sintactico("Error, debe cerrarse el paréntesis"); }
lista_parametros : tipo_basico IDENTIFICADOR {n_parametros+=1;push2($2,parametro_formal);}
        | lista_parametros COMA tipo_basico IDENTIFICADOR {n_parametros+=1;push2($4,parametro_formal);}
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
sentencia_asignacion : IDENTIFICADOR IGUAL expresion PYC 
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
sentencia_salida : WRITE PARIZQ lista_expresiones PARDCH PYC
		  | WRITE error { yyerrok; explicacion_error_sintactico("Error, se esperaba un paréntesis"); }
		  | WRITE PARIZQ error { yyerrok; explicacion_error_sintactico("Error, debe introducir una lista de expresiones separados por comas"); }
		  | WRITE PARIZQ lista_expresiones error { yyerrok; explicacion_error_sintactico("Error, debe cerrar el paréntesis de la lista de expresiones"); }
		  | WRITE PARIZQ lista_expresiones PARDCH error { yyerrok; explicacion_error_sintactico("Error, se esperaba \";\""); }
sentencia_return : RETURN expresion PYC  
		  | RETURN error { yyerrok; explicacion_error_sintactico("Error, debe devolverse una expresión"); }
		  | RETURN expresion error { yyerrok; explicacion_error_sintactico("Error, debe acabar en \";\""); }
sentencia_for_pascal : FOR IDENTIFICADOR IGUAL expresion TO expresion DO bloque
		  | FOR error { yyerrok; explicacion_error_sintactico("Error, se esperaba una sentencia de asignación"); }
		  | FOR sentencia_asignacion error { yyerrok; explicacion_error_sintactico("Error, se esperaba la palabra \"to\""); }
		  | FOR sentencia_asignacion TO error { yyerrok; explicacion_error_sintactico("Error, se esperaba una expresión"); }
		  | FOR sentencia_asignacion TO expresion error { yyerrok; explicacion_error_sintactico("Error, se esperaba la palabra \"do\""); }
		  | FOR sentencia_asignacion TO expresion DO error { yyerrok; explicacion_error_sintactico("Error, se esperaba un bloque"); }
sentencia_lista : IDENTIFICADOR MOVLISTA PYC
        | PRINCIPIOLISTA IDENTIFICADOR PYC
		  //| IDENTIFICADOR error { yyerrok; explicacion_error_sintactico("Error, se esperaba \"<<\""); }
		  /* | IDENTIFICADOR MOVLISTA error { yyerrok; explicacion_error_sintactico("Error, debe acabar en \";\""); }
		  | PRINCIPIOLISTA error { yyerrok; explicacion_error_sintactico("Error, se esperaba un identificador"); }
		  | PRINCIPIOLISTA IDENTIFICADOR error { yyerrok; explicacion_error_sintactico("Error, debe acabar en \";\""); } */
lista_entrada : lista_entrada
		 COMA 			
		 IDENTIFICADOR 
        | IDENTIFICADOR  

expresion : PARIZQ expresion PARDCH { $$ = $1; }
        | OPUNI expresion %prec NOT {  }
        | expresion OPBIN expresion %prec LOGICOS { $$ = operador_binario($2, $1, $3);  }
        | expresion TER1 expresion ARROBA expresion
        | OPUNI expresion %prec NOT { $$ = operador_unario($1, $2); }
        | expresion OPBIN expresion %prec LOGICOS { $$ = operador_binario($2, $1, $3);  }
        | expresion TER1 expresion ARROBA expresion { $$ = operador_ternario($1,$3,$5);}
        | IDENTIFICADOR 
        | llamar_funcion
        | agregado
        | LITERAL 
		   | PARIZQ error { yyerrok; explicacion_error_sintactico("Error, se esperaba una expresión"); }
		//  | PARIZQ expresion error { yyerrok; explicacion_error_sintactico("Error, debe cerrarse el paréntesis"); }
		  | OPUNI error { yyerrok; explicacion_error_sintactico("Error, se esperaba una expresión"); }
		  | expresion OPBIN error { yyerrok; explicacion_error_sintactico("Error, se esperaba una expresión"); }
		  | expresion TER1 error { yyerrok; explicacion_error_sintactico("Error, se esperaba una expresión"); }
		  | expresion TER1 expresion error { yyerrok; explicacion_error_sintactico("Error, se esperaba \"@\""); }
		//  | expresion TER1 expresion ARROBA error { yyerrok; explicacion_error_sintactico("Error, se esperaba una expresión"); } 
llamar_funcion : IDENTIFICADOR argumentos ; //{/*Hace falta algo como push funccion*/}
        
argumentos : PARIZQ lista_argumentos PARDCH
		 | PARIZQ lista_argumentos error { yyerrok; explicacion_error_sintactico("Error, la lista de argumentos debe acabar con paréntesis"); }
lista_argumentos : IDENTIFICADOR 
        | LITERAL 
        | lista_argumentos COMA IDENTIFICADOR 
        | lista_argumentos COMA LITERAL 
        | ;
agregado : CORIZQ lista_expresiones CORDCH
		  | CORIZQ error {yyerrok; explicacion_error_sintactico("Error, debe proporcionar una lista de expresiones separadas por comas");}
		  | CORIZQ lista_expresiones error { yyerrok; explicacion_error_sintactico("Error, debe cerrar el corchete"); }

lista_expresiones : lista_expresiones COMA expresion 
        | expresion 
OPUNI : NOT { copiaStruct(&$$,$1);}
        | SOSTENIDO { copiaStruct(&$$,$1);}
        | INTERROGACION { copiaStruct(&$$,$1);}
        | MENOS { copiaStruct(&$$,$1);}
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
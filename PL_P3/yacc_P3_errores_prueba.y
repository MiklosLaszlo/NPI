// TOKENS

//********************
//REVISAR
%right IGUAL
// Operadores binarios 
%left AND OR XOR
%left EQUAL NOTEQUAL
%left LESS GREATER LESSEQ GREATEREQ

%left MAS MENOS
%left POR DIV

// Operados unarios
%left NOT

// Operado unarios de las listas
%left SOSTENIDO
%left INTERROGACION

// Operadores binarios de lista
%left ARROBA
%left PORCENTAJE
%left DOBLEPOR

%left MENOSMENOS

// Terciario de lista
%nonassoc TER1 TER2

//*******************
%token MAIN
%token LLAVEIZQ
%token LLAVEDCH
%token CORIZQ
%token CORDCH
%token PARIZQ
%token PARDCH
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
%token LITERAL
%token IDENTIFICADOR
%%
// Producciones
// ######################
programa : MAIN bloque PYC 
		  | MAIN error { yyerrok; explicacion_error('Debe incluir un bloque tras el main'); } 
		  | MAIN bloque error { yyerrok; explicacion_error('El programa debe acabar con \';\'')}

bloque : LLAVEIZQ
        declar_de_variables_locales
        declar_de_subprogs
        sentencias
        LLAVEDCH 
		  | LLAVEIZQ declar_de_variables_locales declar_de_subprogs LLAVEDCH 
		//  | LLAVEIZQ error { yyerrok; explicacion_error('Error en el orden del bloque'); } 
		//  | LLAVEIZQ declar_de_variables_locales error { yyerrok; explicacion_error('Error en el orden del bloque'); } 
		//  | LLAVEIZQ declar_de_variables_locales declar_de_subprogs error { yyerrok; explicacion_error('Error en el orden del bloque'); } 
		  | LLAVEIZQ declar_de_variables_locales declar_de_subprogs error { yyerrok; explicacion_error('El bloque debe acabar con }'); } 

declar_de_variables_locales : INIVARIABLES
        variables_locales
        FINVARIABLES
        | 
		  | INIVARIABLES error { yyerrok; explicacion_error('Error en el bloque de declaración de variables'); } 
		  | INIVARIABLES variables_locales error { yyerrok; explicacion_error('Error, debe cerrarse el bloque de declaración de variables'); }
variables_locales : variables_locales PYC cuerpo_declar_variables
        | cuerpo_declar_variables PYC 
		  | cuerpo_declar_variables error { yyerrok; explicacion_error('Error, la declaración debe acabar en \';\'')};
cuerpo_declar_variables : tipo_basico lista_identificadores
		  | tipo_basico error { yyerrok; explicacion_error('Error, después del tipo básico va una lista de identificadores'); }

lista_identificadores : lista_identificadores COMA IDENTIFICADOR
        | IDENTIFICADOR 
//		  El error de esto lo englobo en el de arriba
declar_de_subprogs : declar_de_subprogs declarar_funcion
        | ;
declarar_funcion : tipo_basico IDENTIFICADOR parametros bloque PYC 
		  | tipo_basico error { yyerrok; explicacion_error('Error, al declarar una función tras el tipo básico va el identificador'); }
		  | tipo_basico IDENTIFICADOR error { yyerrok; explicacion_error('Error, en una función tras el identificador vienen los parámetros.'); } 
		  | tipo_basico IDENTIFICADOR parametros error { yyerrok; explicacion_error('Error, en una función tras los parámetros viene un bloque.'); }
		  | tipo_basico IDENTIFICADOR parametros bloque error { yyerrok; explicacion_error('Error, la declaración de funciones acaba en \';\'.'); }  
parametros : PARIZQ PARDCH
        | PARIZQ lista_parametros PARDCH 
		  | PARIZQ error { yyerrok; explicacion_error('Error, debe introducir una lista de parámetros separados por comas'); }
		  | PARIZQ lista_parametros error { yyerrok; explicacion_error('Error, debe cerrarse el paréntesis'); }
lista_parametros : tipo_basico IDENTIFICADOR 
        | lista_parametros COMA tipo_basico IDENTIFICADOR 
		  | tipo_basico error { yyerrok; explicacion_error('Error, tras el tipo básico debe introducir un identificador'); }
tipo_basico : TYPE ;
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
        | PYC 
sentencia_asignacion : IDENTIFICADOR IGUAL expresion PYC 
		  | IDENTIFICADOR IGUAL error { yyerrok; explicacion_error('Error, el identificador debe estar igualada a una expresión')};
		  | IDENTIFICADOR IGUAL expresion error { yyerrok; explicacion_error('Error, la asignación debe acabar en \';\'')}
sentencia_if : IF PARIZQ expresion PARDCH THEN bloque ELSE bloque
        | IF PARIZQ expresion PARDCH THEN bloque 
		  | IF error { yyerrok; explicacion_error('Error, tras el if debe introducir la condición entre paréntesis'); }
		  | IF PARIZQ error { yyerrok; explicacion_error('Error, la condición debe ser una expresión'); }
		  | IF PARIZQ expresion error { yyerrok; explicacion_error('Error, debe cerrar el paréntesis'); }
		  | IF PARIZQ expresion PARDCH error { yyerrok; explicacion_error('Error, se esperaba \'then\''); }
		  | IF PARIZQ expresion PARDCH THEN error { yyerrok; explicacion_error('Error, se esperaba un bloque'); }
	//	  | IF PARIZQ expresion PARDCH THEN bloque error { yyerrok; explicacion_error('Error, '); }
		  | IF PARIZQ expresion PARDCH THEN bloque ELSE error { yyerrok; explicacion_error('Error, se esperaba un bloque'); }
sentencia_while : WHILE PARIZQ expresion PARDCH bloque 
		  | WHILE error { yyerrok; explicacion_error('Error, introduzca la condición entre paréntesis'); }
		  | WHILE PARIZQ error { yyerrok; explicacion_error('Error, la condición debe ser una expresión'); }
		  | WHILE PARIZQ expresion error { yyerrok; explicacion_error('Error, debe cerrarse el paréntesis'); }
		  | WHILE PARIZQ expresion PARDCH error { yyerrok; explicacion_error('Error, se esperaba un bloque'); }
sentencia_entrada : READ lista_identificadores PYC 
    	  | READ error { yyerrok; explicacion_error('Error, debe introducir una lista de identificadores separados por comas'); }
		  | READ lista_identificadores error { yyerrok; explicacion_error('Error, se esperaba \';\' '); }
sentencia_salida : WRITE lista_expresiones PYC 
		  | WRITE error { yyerrok; explicacion_error('Error, debe introducir una lista de identificadores separados por comas'); }
		  | WRITE lista_expresiones error { yyerrok; explicacion_error('Error, se esperaba \';\''); }
sentencia_return : RETURN expresion PYC
		  | RETURN error { yyerrok; explicacion_error('Error, debe devolverse una expresión'); }
		  | RETURN expresion error { yyerrok; explicacion_error('Error, debe acabar en \';\''); }
sentencia_for_pascal : FOR sentencia_asignacion TO expresion DO bloque 
		  | FOR error { yyerrok; explicacion_error('Error, se esperaba una sentencia de asignación'); }
		  | FOR sentencia_asignacion error { yyerrok; explicacion_error('Error, se esperaba la palabra \'to\''); }
		  | FOR sentencia_asignacion TO error { yyerrok; explicacion_error('Error, se esperaba una expresión'); }
		  | FOR sentencia_asignacion TO expresion error { yyerrok; explicacion_error('Error, se esperaba la palabra \'do\''); }
		  | FOR sentencia_asignacion TO expresion DO error { yyerrok; explicacion_error('Error, se esperaba un bloque'); }
sentencia_lista : IDENTIFICADOR MOVLISTA PYC
        | PRINCIPIOLISTA IDENTIFICADOR PYC 
		  | IDENTIFICADOR error { yyerrok; explicacion_error('Error, se esperaba \'<<\''); }
		  | IDENTIFICADOR MOVLISTA error { yyerrok; explicacion_error('Error, debe acabar en \';\''); }
		  | PRINCIPIOLISTA error { yyerrok; explicacion_error('Error, se esperaba un identificador'); }
		  | PRINCIPIOLISTA IDENTIFICADOR error { yyerrok; explicacion_error('Error, debe acabar en \';\''); }
        
expresion : PARIZQ expresion PARDCH
        | OPUNI expresion %prec NOT
        | expresion OPBIN expresion %prec AND
        | expresion TER1 expresion TER2 expresion
        | IDENTIFICADOR
        | llamar_funcion
        | agregado
        | LITERAL 

		  | PARIZQ error { yyerrok; explicacion_error('Error, se esperaba una expresión'); }
		  | PARIZQ expresion error { yyerrok; explicacion_error('Error, debe cerrarse el paréntesis'); }
		  | OPUNI error { yyerrok; explicacion_error('Error, se esperaba una expresión'); }
		  | expresion OPBIN error { yyerrok; explicacion_error('Error, se esperaba una expresión'); }
		  | expresion TER1 error { yyerrok; explicacion_error('Error, se esperaba una expresión'); }
		  | expresion TER1 expresion error { yyerrok; explicacion_error('Error, se esperaba \'@@\''); }
		  | expresion TER1 expresion TER2 error { yyerrok; explicacion_error('Error, se esperaba una expresión'); } 


llamar_funcion : IDENTIFICADOR argumentos
		  | IDENTIFICADOR error { yyerrok; explicacion_error('Error, debe indicar los argumentos de la función'); };
        
argumentos : PARIZQ lista_argumentos PARDCH 
		  | PARIZQ error { yyerrok; explicacion_error('Error, debe indicar los argumentos separados por coma')}
		  | PARIZQ lista_argumentos error { yyerrok; explicacion_error('Error, la lista de argumentos debe acabar con paréntesis'); }
lista_argumentos : IDENTIFICADOR
        | lista_argumentos COMA IDENTIFICADOR
        | 
agregado : CORIZQ lista_expresiones CORDCH 
		  | CORIZQ error {yytext('Error, debe proporcionar una lista de expresiones separadas por comas')}
		  | CORIZQ lista_expresiones error { yytext('Error, debe cerrar el corchete'); }
lista_expresiones : lista_expresiones COMA expresion
        | expresion 
OPUNI : NOT
        | SOSTENIDO
        | INTERROGACION 
        | MENOS ;
OPBIN : EQUAL
        | MENOS
        | POR
        | NOTEQUAL
        | AND
        | OR
        | XOR
        | MAS
        | DIV
        | LESS
        | GREATER
        | LESSEQ
        | GREATEREQ
        | ARROBA
        | MENOSMENOS
        | PORCENTAJE
        | DOBLEPOR ;
%%      

#include <stdlib.h>
#include <stdio.h>
#include <string.h>   
#include "lex.yy.c"

void yyerrok; explicacion_error( char * prod ) {
	printf("Línea %d, Error sintáctico en la producción %d, no se esperaba: '%s'", nlinea, prod, yytext );
}

// Contamos las lineas, lo cuenta lex pero yacc lo tendra en cuenta
int main(){
	yyparse();
}
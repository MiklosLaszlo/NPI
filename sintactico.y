%{
    #include <stdlib.h>
    #include <stdio.h>
    #include <string.h>    
%}

//********************
//REVISAR
%left OPUNI
%right IGUAL
%left OPBIN
%left TER1
%left TER2
%left MENOSPOR
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

programa : MAIN bloque PYC
bloque : LLAVEIZQ
        declar_de_variables_locales
        declar_de_subprogs
        sentencias
        LLAVEDCH
declar_de_variables_locales : INIVARIABLES
        variables_locales
        FINVARIABLES
        |
variables_locales : variables_locales PYC cuerpo_declar_variables
        | cuerpo_declar_variables PYC
cuerpo_declar_variables : tipo_basico lista_identificadores

lista_identificadores : lista_identificadores COMA IDENTIFICADOR
        | IDENTIFICADOR
declar_de_subprogs : declar_de_subprogs declarar_funcion
        |
declarar_funcion : tipo_basico IDENTIFICADOR parametros bloque PYC

parametros : PARIZQ PARDCH
        | PARIZQ lista_parametros PARDCH
lista_parametros : tipo_basico IDENTIFICADOR
        | lista_parametros COMA tipo_basico IDENTIFICADOR

// sustituir tipo basico por type y borrar tipo basico? o declarar list of como token
tipo_basico : TYPE
// LIST OF??

//falta token "."
//OPCION 1 DECLARAR TOKEN PUNTO
//numero : digitos "." digitos | digitos
//digitos : digitos LITERAL | LITERAL

//OPCION 2 sustituir numero por literal y borrar numero
numero : LITERAL

//BORRAR bolean y sustituir por literal?
boolean : LITERAL

//??
simbolos : simbolos TYPE

sentencias : sentencias sentencia

sentencia : sentencia_asignacion
        | sentencia_if 		
        | sentencia_while
        | sentencia_entrada
        | sentencia_salida
        | sentencia_return 
        | sentencia_for_pascal
        | sentencia_lista
        |

sentencia_asignacion : IDENTIFICADOR IGUAL expresion PYC

sentencia_if : IF PARIZQ expresion PARDCH THEN bloque
        ELSE bloque
        | IF PARIZQ expresion PARDCH THEN bloque
sentencia_while : WHILE PARIZQ expresion PARDCH bloque

sentencia_entrada : READ lista_variables PYC
//lista variables no definida en practica 1
lista_variables : PARIZQ lista_identificadores PARDCH

//En pl 1 <sentencia_salida> ::= write <lista_expresiones>”;”
//corregido : 
sentencia_salida : WRITE lista_identificadores PYC

sentencia_return : RETURN expresion PYC

sentencia_for_pascal : FOR sentencia_asignacion TO expresion DO bloque

sentencia_lista : IDENTIFICADOR MOVLISTA PYC
        | PRINCIPIOLISTA IDENTIFICADOR PYC
        
expresion : PARIZQ expresion PARDCH
        | OPUNI expresion
        | expresion OPBIN expresion
        | expresion TER1 expresion TER2 expresion
        | IDENTIFICADOR
        | llamar_funcion
        | agregado
        | LITERAL // BOOLEAN, NUM, CHAR

llamar_funcion : IDENTIFICADOR argumentos
        | IDENTIFICADOR argumentos PYC 
        //llamar funcion sin PYC??? nse si esta bien

argumentos : PARIZQ lista_argumentos PARDCH

lista_argumentos : IDENTIFICADOR
        | lista_argumentos COMA IDENTIFICADOR
        |

agregado : CORIZQ lista_expresiones CORDCH

lista_expresiones : lista_expresiones COMA expresion
        | expresion


    










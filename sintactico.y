%{
    #include <stdlib.h>
    #include <stdio.h>
    #include <string.h>   
    
    // #include "y.tab.h" // Creo que esto habria que añadirlo al lex

    // La siguiente declaracion permite que ’yyerror’ se pueda invocar desde el
    // fuente de lex
    void yyerror( char * msg ) ;

    // Contamos las lineas, lo cuenta lex pero yacc lo tendra en cuenta
    int linea_actual = 1 ; 
%}


// TOKENS

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

// Producciones

// ######################

programa : MAIN bloque PYC ;
bloque : LLAVEIZQ
        declar_de_variables_locales
        declar_de_subprogs
        sentencias
        LLAVEDCH ;
declar_de_variables_locales : INIVARIABLES
        variables_locales
        FINVARIABLES
        | ;
variables_locales : variables_locales PYC cuerpo_declar_variables
        | cuerpo_declar_variables PYC ;
cuerpo_declar_variables : tipo_basico lista_identificadores ;

lista_identificadores : lista_identificadores COMA IDENTIFICADOR
        | IDENTIFICADOR ;
declar_de_subprogs : declar_de_subprogs declarar_funcion
        | ;
declarar_funcion : tipo_basico IDENTIFICADOR parametros bloque PYC ;

parametros : PARIZQ PARDCH
        | PARIZQ lista_parametros PARDCH ;
lista_parametros : tipo_basico IDENTIFICADOR 
        | lista_parametros COMA tipo_basico IDENTIFICADOR ;

// sustituir tipo basico por type y borrar tipo basico? o declarar list of como token
// NICO: creo que no hace falta porque lex deberia de identificar que es TYPE
tipo_basico : TYPE ;
// LIST OF??

//falta token "."
// NOTA DE NICO: creo que no hace falta declarar el punto, supongo que lex + yacc funciona asi, lex identifica el token
// (el token puede ser como sea, pero tendra el punto en este caso) y yacc se encarga de mirar si sintacticamente tiene sentido

//OPCION 1 DECLARAR TOKEN PUNTO
//numero : digitos "." digitos | digitos
//digitos : digitos LITERAL | LITERAL

// He comentado numero, boolean y simbolos porque al hacer yacc error

//OPCION 2 sustituir numero por literal y borrar numero
//numero : LITERAL ; // Nico: creo que esto no hace falta o se deberia de poner al reves

//BORRAR bolean y sustituir por literal?
//boolean : LITERAL ; // Nico: lo mismo que con numero

//??
//simbolos : simbolos TYPE ;

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
        | ;

sentencia_asignacion : IDENTIFICADOR IGUAL expresion PYC ;

sentencia_if : IF PARIZQ expresion PARDCH THEN bloque
        ELSE bloque
        | IF PARIZQ expresion PARDCH THEN bloque ;
sentencia_while : WHILE PARIZQ expresion PARDCH bloque ;

sentencia_entrada : READ lista_identificadores PYC ;
//lista variables no definida en practica 1 
// NICO: como no esta la lista de variables pero si tenemos lista de identificadores, dira de usar eso
//lista_variables : PARIZQ lista_identificadores PARDCH ;

//En pl 1 <sentencia_salida> ::= write <lista_expresiones>”;”
//corregido : 
sentencia_salida : WRITE lista_identificadores PYC ; // NICO: no estoy de acuerdo, puedo querer imprimir 1, en vez de a con a=1

sentencia_return : RETURN expresion PYC ;

sentencia_for_pascal : FOR sentencia_asignacion TO expresion DO bloque ;

sentencia_lista : IDENTIFICADOR MOVLISTA PYC
        | PRINCIPIOLISTA IDENTIFICADOR PYC ;
        
expresion : PARIZQ expresion PARDCH
        | OPUNI expresion
        | expresion OPBIN expresion
        | expresion TER1 expresion TER2 expresion
        | IDENTIFICADOR
        | llamar_funcion
        | agregado
        | LITERAL ; // BOOLEAN, NUM, CHAR

llamar_funcion : IDENTIFICADOR argumentos
        | IDENTIFICADOR argumentos PYC ;
        //llamar funcion sin PYC??? nse si esta bien

argumentos : PARIZQ lista_argumentos PARDCH ;

lista_argumentos : IDENTIFICADOR
        | lista_argumentos COMA IDENTIFICADOR
        | ;

agregado : CORIZQ lista_expresiones CORDCH ;

lista_expresiones : lista_expresiones COMA expresion
        | expresion ;

%%










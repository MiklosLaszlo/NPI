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
%right IGUAL
// Operadores binarios 
//%left AND OR XOR
%left LOGICOS
//%left EQUAL NOTEQUAL
%left IGUALDAD

//%left LESS GREATER LESSEQ GREATEREQ
%left COMPARACION
%left MAS MENOSMENOS
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
%left MENOS

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
        | PYC ;
sentencia_asignacion : IDENTIFICADOR IGUAL expresion PYC ;
sentencia_if : IF PARIZQ expresion PARDCH THEN bloque
        ELSE bloque
        | IF PARIZQ expresion PARDCH THEN bloque ;
sentencia_while : WHILE PARIZQ expresion PARDCH bloque ;
sentencia_entrada : READ lista_identificadores PYC ;
sentencia_salida : WRITE lista_expresiones PYC ; 
sentencia_return : RETURN expresion PYC ;
sentencia_for_pascal : FOR sentencia_asignacion TO expresion DO bloque ;
sentencia_lista : IDENTIFICADOR MOVLISTA PYC
        | PRINCIPIOLISTA IDENTIFICADOR PYC ;
        
expresion : PARIZQ expresion PARDCH
        | OPUNI expresion %prec NOT
        | expresion OPBIN expresion %prec LOGICOS
        | expresion TER1 expresion TER2 expresion
        | IDENTIFICADOR
        | llamar_funcion
        | agregado
        | LITERAL ;
llamar_funcion : IDENTIFICADOR argumentos
        
argumentos : PARIZQ lista_argumentos PARDCH ;
lista_argumentos : IDENTIFICADOR
        | lista_argumentos COMA IDENTIFICADOR
        | ;
agregado : CORIZQ lista_expresiones CORDCH ;
lista_expresiones : lista_expresiones COMA expresion
        | expresion ;
OPUNI : NOT
        | SOSTENIDO
        | INTERROGACION 
        | MENOS ;
OPBIN : IGUALDAD
        | MENOS
        | POR
        | LOGICOS
        | MAS
        | DIV
        | COMPARACION
        | ARROBA
        | MENOSMENOS
        | PORCENTAJE
        | DOBLEPOR ;
%%      
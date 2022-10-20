%{
//Declaraciones

#include <stdlib.h>
#include <string.h>
#include "constants.h"

%}



%option yylineno
%option noyywrap


letra [a-zA-Z]
numero [0-9]
tipo_basico int|double|char|bool
tipo_dato (int|double|char|bool)|list[ ]of[ ](int|double|char|bool)
blanco [ \t]
otros .
%%


"main" {
    ECHO;
    return (MAIN);
}

"{" {
    ECHO;
    return (LLAVEIZQ);
}


"}" {
    ECHO;
    return (LLAVEDCH);
}


"[" {
    ECHO;
    return (CORIZQ);
}

"]" {
    ECHO;
    return (CORDCH);
}

"(" {
    ECHO;
    return (PARIZQ);
}

")" {
    ECHO;
    return (PARDCH);
}


"not" {
    ECHO;
    return (OPUNI);
}

"=" {
    ECHO;
    return (IGUAL);
}
"&" {
    ECHO;
    return (OPUNI);
}
"#" {
    ECHO;
    return (OPUNI);
}

{tipo_dato} {
   ECHO;
   return (TYPE);
}

"?" {
    ECHO;
    return (OPUNI);
}

"equal" {
    ECHO;
    return (OPBIN);
}

"not_equal" {
    ECHO;
    return (OPBIN);
}
"and" {
    ECHO;
    return (OPBIN);
}

"or" {
    ECHO;
    return (OPBIN);
}
"xor" {
    ECHO;
    return (OPBIN);
}
"+" {
    ECHO;
    return (OPBIN);
}


"/" {
    ECHO;
    return (OPBIN);
}

"less" {
    ECHO;
    return (OPBIN);
}

"greater" {
    ECHO;
    return (OPBIN);
}

"less_eq" {
    ECHO;
    return (OPBIN);
}

"greater_eq" {
    ECHO;
    return (OPBIN);
}

"@" {
    ECHO;
    return (OPBIN);
}

"--" {
    ECHO;
    return (OPBIN);
}
"%" {
    ECHO;
    return (OPBIN);
}

"**" {
    ECHO;
    return (OPBIN);
}

"if" {
    ECHO;
    return (IF);
}

"then" {
    ECHO;
    return (THEN);

}

"else" {
    ECHO;
    return(ELSE);
}

"for" {
    ECHO;
    return (FOR);
}

"to" {
    ECHO;
    return (TO);
}
"do" {
    ECHO;
    return (DO);
}

"write" {
    ECHO;
    return (WRITE);
}
"read" {
    ECHO;
    return (READ);
}
"," {
    ECHO;
    return (COMA);
}

";" {
    ECHO;
    return (PYC);
}
"return" {
    ECHO;
    return (RETURN);
}
"while" {
    ECHO;
    return(WHILE);
}

"++" {
    ECHO;
    return (TER1);
}

"@@" {
    ECHO;
    return (TER2);
}


"$" {
    ECHO;
    return (PRINCIPIOLISTA);
}

"-" {
    ECHO;
    return (MENOS);
}


"<<" {
    ECHO;
    return (MOVLISTA);
 }

">>" {
    ECHO;
    return (MOVLISTA);
}

"open" {
    ECHO;
    return (INIVARIABLES);
}

"close" {
    ECHO;
    return (FINVARIABLES);
}


"*" {
    ECHO;
    return (POR);
}

({letra}|{numero}|{numero}+|{numero}+.{numero}+|"True"|"False") {
    ECHO;
    return (LITERAL);
}

({letra})({letra}|{numero})* {
     ECHO;
     return(IDENTIFICADOR);
 }

{blanco} ;

{otros}	printf(" \nError lexico en linea: %d, No se reconoce la palabra '%s'. \n",yylineno,yytext);

%%

/* Procedimientos de usuario */

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

    // Invoca al analizador léxico para comenzar el análisis
	int an = yylex();

    // Hasta que no termine de analizar todo el fichero no finalizará la
    // ejecución
	while (an != 0) {

		printf(" --> %d   ", an);
        //printf("%s ", yytext());
		an = yylex();

	}

	exit(1);

}
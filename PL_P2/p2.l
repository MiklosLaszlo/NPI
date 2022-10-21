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
    printf("--> atributo: 0");
    return (OPUNI);
}

"=" {
    ECHO;
    return (IGUAL);
}
"&" {
    ECHO;
    printf("--> atributo: 1");
    return (OPUNI);
}
"#" {
    ECHO;
    printf("--> atributo: 2");
    return (OPUNI);
}

"int" {
   ECHO;
   printf("--> atributo: 0");
   return (TYPE); 
}

"double" {
    ECHO;
    printf("--> atributo: 1");
   return (TYPE);
}

"char" {
    ECHO;
    printf("--> atributo: 2");
   return (TYPE);
}

"bool" {
    ECHO;
    printf("--> atributo: 3");
   return (TYPE);
}

"list"[ ]"of"[ ]"int" {
    ECHO;
    printf("--> atributo: 4");
   return (TYPE);
}

"list"[ ]"of"[ ]"double" {
    ECHO;
    printf("--> atributo: 5");
   return (TYPE);
}

"list"[ ]"of"[ ]"char" {
    ECHO;
    printf("--> atributo: 6");
   return (TYPE);
}

"list"[ ]"of"[ ]"bool" {
    ECHO;
    printf("--> atributo: 7");
   return (TYPE);
}

"?" {
    ECHO;
    printf("--> atributo: 3");
    return (OPUNI);
}

"equal" {
    ECHO;
    printf("--> atributo: 0");
    return (OPBIN);
}

"not_equal" {
    ECHO;
    printf("--> atributo: 1");
    return (OPBIN);
}
"and" {
    ECHO;
    printf("--> atributo: 2");
    return (OPBIN);
}

"or" {
    ECHO;
    printf("--> atributo: 3");
    return (OPBIN);
}
"xor" {
    ECHO;
    printf("--> atributo: 4");
    return (OPBIN);
}
"+" {
    ECHO;
    printf("--> atributo: 5");
    return (OPBIN);
}


"/" {
    ECHO;
    printf("--> atributo: 6");
    return (OPBIN);
}

"less" {
    ECHO;
    printf("--> atributo: 7");
    return (OPBIN);
}

"greater" {
    ECHO;
    printf("--> atributo: 8");
    return (OPBIN);
}

"less_eq" {
    ECHO;
    printf("--> atributo: 9");
    return (OPBIN);
}

"greater_eq" {
    ECHO;
    printf("--> atributo: 10");
    return (OPBIN);
}

"@" {
    ECHO;
    printf("--> atributo: 11");
    return (OPBIN);
}

"--" {
    ECHO;
    printf("--> atributo: 12");
    return (OPBIN);
}
"%" {
    ECHO;
    printf("--> atributo: 13");
    return (OPBIN);
}

"**" {
    ECHO;
    printf("--> atributo: 14");
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
    printf("--> atributo: 0");
    return (MOVLISTA);
 }

">>" {
    ECHO;
    printf("--> atributo: 1");
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

("'"({letra}|{numero})"'") {
    ECHO;
    printf("--> atributo: 0");
    return (LITERAL);
}

({numero}+) {
    ECHO;
    printf("--> atributo: 1");
    return (LITERAL);
}

({numero}+.{numero}+) {
    ECHO;
    printf("--> atributo: 2");
    return (LITERAL);
}

("True"|"False") {
    ECHO;
    printf("--> atributo: 3");
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

    printf("\n");
	exit(1);

}
%{
//Declaraciones

#include <stdlib.h>
#include <string.h>
#include "constants.h"

#define RESET_COLOR    "\x1b[0m"
#define BG_COLOR_RED     "\x1B[41m"

int nlinea = 1;
char atributo[100];
%}



%option yylineno
%option noyywrap



letra [a-zA-Z]
numero [0-9]
blanco [ \t]
otros .
%%


"main" {
    strcpy(atributo, "");
    return (MAIN);
}

"{" {
    strcpy(atributo, "");
    return (LLAVEIZQ);
}


"}" {
    strcpy(atributo, "");
    return (LLAVEDCH);
}


"[" {
    strcpy(atributo, "");
    return (CORIZQ);
}

"]" {
    strcpy(atributo, "");
    return (CORDCH);
}

"(" {
    strcpy(atributo, "");
    return (PARIZQ);
}

")" {
    strcpy(atributo, "");
    return (PARDCH);
}


"not" {
    strcpy(atributo, "0");
    return (OPUNI);
}

"=" {
    strcpy(atributo, "");
    return (IGUAL);
}
"&" {
    strcpy(atributo, "1");
    return (OPUNI);
}
"#" {
    strcpy(atributo, "2");
    return (OPUNI);
}

"int" {
   strcpy(atributo, "0");
   return (TYPE); 
}

"double" {
    strcpy(atributo, "1");
   return (TYPE);
}

"char" {
    strcpy(atributo, "2");
   return (TYPE);
}

"bool" {
    strcpy(atributo, "3");
   return (TYPE);
}

"list"[ ]"of"[ ]"int" {
    strcpy(atributo, "4");
   return (TYPE);
}

"list"[ ]"of"[ ]"double" {
    strcpy(atributo, "5");
   return (TYPE);
}

"list"[ ]"of"[ ]"char" {
    strcpy(atributo, "6");
   return (TYPE);
}

"list"[ ]"of"[ ]"bool" {
    strcpy(atributo, "7");
   return (TYPE);
}

"?" {
    strcpy(atributo, "3");
    return (OPUNI);
}

"equal" {
    strcpy(atributo, "0");
    return (OPBIN);
}

"not_equal" {
    strcpy(atributo, "1");
    return (OPBIN);
}
"and" {
    strcpy(atributo, "2");
    return (OPBIN);
}

"or" {
    strcpy(atributo, "3");
    return (OPBIN);
}
"xor" {
    strcpy(atributo, "4");
    return (OPBIN);
}
"+" {
    strcpy(atributo, "5");
    return (OPBIN);
}


"/" {
    strcpy(atributo, "6");
    return (OPBIN);
}

"less" {
    strcpy(atributo, "7");
    return (OPBIN);
}

"greater" {
    strcpy(atributo, "8");
    return (OPBIN);
}

"less_eq" {
    strcpy(atributo, "9");
    return (OPBIN);
}

"greater_eq" {
    strcpy(atributo, "10");
    return (OPBIN);
}

"@" {
    strcpy(atributo, "11");
    return (OPBIN);
}

"--" {
    strcpy(atributo, "12");
    return (OPBIN);
}
"%" {
    strcpy(atributo, "13");
    return (OPBIN);
}

"**" {
    strcpy(atributo, "14");
    return (OPBIN);
}

"if" {
    strcpy(atributo, "");
    return (IF);
}

"then" {
    strcpy(atributo, "");
    return (THEN);

}

"else" {
    strcpy(atributo, "");
    return(ELSE);
}

"for" {
    strcpy(atributo, "");
    return (FOR);
}

"to" {
    strcpy(atributo, "");
    return (TO);
}
"do" {
    strcpy(atributo, "");
    return (DO);
}

"write" {
    strcpy(atributo, "");
    return (WRITE);
}
"read" {
    strcpy(atributo, "");
    return (READ);
}
"," {
    strcpy(atributo, "");
    return (COMA);
}

";" {
    strcpy(atributo, "");
    return (PYC);
}
"return" {
    strcpy(atributo, "");
    return (RETURN);
}
"while" {
    strcpy(atributo, "");
    return(WHILE);
}

"++" {
    strcpy(atributo, "");
    return (TER1);
}

"@@" {
    strcpy(atributo, "");
    return (TER2);
}


"$" {
    strcpy(atributo, "");
    return (PRINCIPIOLISTA);
}

"-" {
    strcpy(atributo, "0");
    return (MENOSPOR);
}


"<<" {
    strcpy(atributo, "0");
    return (MOVLISTA);
 }

">>" {
    strcpy(atributo, "1");
    return (MOVLISTA);
}

"open" {
    strcpy(atributo, "");
    return (INIVARIABLES);
}

"close" {
    strcpy(atributo, "");
    return (FINVARIABLES);
}


"*" {
    strcpy(atributo, "1");
    return (MENOSPOR);
}

("'"({letra}|{numero})"'") {
    strcpy(atributo, "0");
    return (LITERAL);
}

({numero}+) {
    strcpy(atributo, "1");
    return (LITERAL);
}

({numero}+.{numero}+) {
    strcpy(atributo, "2");
    return (LITERAL);
}

("True"|"False") {
    strcpy(atributo, "3");
    return (LITERAL);
}

({letra})({letra}|{numero})* {
     strcpy(atributo, yytext);
     return(IDENTIFICADOR);
 }

{blanco} ;

[\n] {
    nlinea = nlinea + 1;
}

{otros}	printf(BG_COLOR_RED "Error lexico"  RESET_COLOR " linea: %d, No se reconoce la palabra '%s'.\n",nlinea,yytext);

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

		printf("codigo: %d\tatributo:%s\tlexema:%s\tlinea:%d\n", an,atributo,yytext,nlinea);
		an = yylex();

	}

	exit(0);

}
%{
//Declaraciones

#include <stdlib.h>
#include <string.h>
#include "y.tab.h"

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
    return (NOT);
}

"=" {
    strcpy(atributo, "");
    return (IGUAL);
}
"#" {
    strcpy(atributo, "2");
    return (SOSTENIDO);
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

"less" {
    strcpy(atributo, "7");
    return (COMPARACION);
}

"greater" {
    strcpy(atributo, "8");
    return (COMPARACION);
}

"less_eq" {
    strcpy(atributo, "9");
    return (COMPARACION);
}

"greater_eq" {
    strcpy(atributo, "10");
    return (COMPARACION);
}

"equal" {
    strcpy(atributo, "0");
    return (IGUALDAD);
}

"not_equal" {
    strcpy(atributo, "1");
    return (IGUALDAD);
}
"and" {
    strcpy(atributo, "2");
    return (LOGICOS);
}

"or" {
    strcpy(atributo, "3");
    return (LOGICOS);
}
"xor" {
    strcpy(atributo, "4");
    return (LOGICOS);
}

"?" {
    strcpy(atributo, "3");
    return (INTERROGACION);
}

"+" {
    strcpy(atributo, "3");
    return (MAS);
}


"/" {
    strcpy(atributo, "4");
    return (MULTIPLICATIVO);
}


"@" {
    strcpy(atributo, "6");
    return (ARROBA);
}

"--" {
    strcpy(atributo, "7");
    return (MENOSMENOS);
}
"%" {
    strcpy(atributo, "8");
    return (PORCENTAJE);
}

"**" {
    strcpy(atributo, "9");
    return (DOBLEPOR);
}
"*" {
    strcpy(atributo, "10");
    return (MULTIPLICATIVO);
}

"-" {
    strcpy(atributo, "11");
    return (MENOS);
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

/* Procedimientos de usuario 

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
	int codigo = yylex();

    // Hasta que no termine de analizar todo el fichero no finalizará la
    // ejecución
	while (codigo != 0) {

		printf("codigo: %d\tatributo:%s\tlexema:%s\tlinea:%d\n", codigo,atributo,yytext,nlinea);
		codigo = yylex();

	}

	exit(0);

}
*/
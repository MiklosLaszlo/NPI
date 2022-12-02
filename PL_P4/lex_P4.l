%{
//Declaraciones

#include <stdlib.h>
#include <string.h>

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
   char nombre[100]; 
   // char valor[50];              // Contendra los caracteres que forman el identificador
   TipoDato dato_referencia; // En caso de que entrada sea funcion,variable
                             // o parametro formal indica el tipo de dato al que hace referencia
   TipoDato dato_lista;      //tipo de datos que contiene la lista                    
   unsigned int n_parametros;  //Si tipoDato  es funcion indica el numero de parametros 
	TipoOperador tipo_operador; // En caso de ser operador, qué operador es  
};

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
    strcpy(atributo, "");
    return (NOT);
}

"=" {
    strcpy(atributo, "");
    return (IGUAL);
}
"#" {
    strcpy(atributo, "");
    return (SOSTENIDO);
}

"int" {
	yylval.dato_referencia = entero;
   strcpy(atributo, "0");
   return (TYPE); 
}

"double" {
	yylval.dato_referencia = real;
   strcpy(atributo, "1");
   return (TYPE);
}

"char" {
	yylval.dato_referencia = caracter;
    strcpy(atributo, "2");
   return (TYPE);
}

"bool" {
	yylval.dato_referencia = booleano;
    strcpy(atributo, "3");
   return (TYPE);
}

"list"[ ]"of"[ ]"int" {
	yylval.dato_referencia = lista;
	yylval.dato_lista = entero;
    strcpy(atributo, "4");
   return (TYPE);
}

"list"[ ]"of"[ ]"double" {
	yylval.dato_referencia = lista;
	yylval.dato_lista = real;
    strcpy(atributo, "5");
   return (TYPE);
}

"list"[ ]"of"[ ]"char" {
	yylval.dato_referencia = lista;
	yylval.dato_lista = caracter;
    strcpy(atributo, "6");
   return (TYPE);
}

"list"[ ]"of"[ ]"bool" {
	yylval.dato_referencia = lista;
	yylval.dato_lista = booleano;
    strcpy(atributo, "7");
   return (TYPE);
}

"less" {
    strcpy(atributo, "0");
	 yylval.entrada = operador;
	 yylval.tipo_operador = less;
    return (COMPARACION);
}

"greater" {
    strcpy(atributo, "1");
	 yylval.entrada = operador;
	 yylval.tipo_operador = greater;
    return (COMPARACION);
}

"less_eq" {
    strcpy(atributo, "2");
	 yylval.entrada = operador;
	 yylval.tipo_operador = less_eq;
    return (COMPARACION);
}

"greater_eq" {
    strcpy(atributo, "3");
	 yylval.entrada = operador;
	 yylval.tipo_operador = greater_eq;
    return (COMPARACION);
}

"equal" {
    strcpy(atributo, "1");
	 yylval.entrada = operador;
	 yylval.tipo_operador = equal;
    return (IGUALDAD);
}

"not_equal" {
    strcpy(atributo, "0");
	 yylval.entrada = operador;
	 yylval.tipo_operador = not_equal;
    return (IGUALDAD);
}
"and" {
    strcpy(atributo, "0");
	 yylval.entrada = operador;
	 yylval.tipo_operador = and;
    return (LOGICOS);
}

"or" {
    strcpy(atributo, "1");
	 yylval.entrada = operador;
	 yylval.tipo_operador = or;
    return (LOGICOS);
}
"xor" {
    strcpy(atributo, "2");
	 yylval.entrada = operador;
	 yylval.tipo_operador = xor;
    return (LOGICOS);
}

"?" {
    strcpy(atributo, "");
	 yylval.entrada = operador;
	 yylval.tipo_operador = interrogacion;
    return (INTERROGACION);
}

"++" {
    strcpy(atributo, "");
	 yylval.entrada = operador;
	 yylval.tipo_operador = masmas;
    return (TER1);
}

"+" {
    strcpy(atributo, "");
	 yylval.entrada = operador;
	 yylval.tipo_operador = mas;
    return (MAS);
}


"/" {
    strcpy(atributo, "1");
	 yylval.entrada = operador;
	 yylval.tipo_operador = entre;
    return (MULTIPLICATIVO);
}

"@" {
    strcpy(atributo, "");
	 yylval.entrada = operador;
	 yylval.tipo_operador = arroba;
    return (ARROBA);
}

"--" {
    strcpy(atributo, "");
	 yylval.entrada = operador;
	 yylval.tipo_operador = menosmenos;
    return (MENOSMENOS);
}
"%" {
    strcpy(atributo, "");
	 yylval.entrada = operador;
	 yylval.tipo_operador = porcentaje;
    return (PORCENTAJE);
}

"**" {
    strcpy(atributo, "");
	 yylval.entrada = operador;
	 yylval.tipo_operador = doblepor;
    return (DOBLEPOR);
}
"*" {
    strcpy(atributo, "0");
	 yylval.entrada = operador;
	 yylval.tipo_operador = por;
    return (MULTIPLICATIVO);
}

"-" {
    strcpy(atributo, "");
	 yylval.entrada = operador;
	 yylval.tipo_operador = menos;
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




("'"({letra}|{numero}|[ ]|\:)"'") {
	yylval.dato_referencia = caracter;
    strcpy(atributo, "0");
    return (LITERAL);
}

({numero}+) {
	yylval.dato_referencia = entero;
    strcpy(atributo, "1");
    return (LITERAL);
}

({numero}+\.{numero}+) {
	yylval.dato_referencia = real;
    strcpy(atributo, "2");
    return (LITERAL);
}

("True"|"False") {
	yylval.dato_referencia = booleano;
    strcpy(atributo, "3");
    return (LITERAL);
}

({letra})({letra}|{numero})* {
	strcpy(yylval.nombre, yytext);
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
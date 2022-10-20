%{
//Declaraciones

#include <stdlib.h>
#include <string.h>
#include <constants.h>

%}



%option yylineno
%option noyywrap


letra [a-zA-Z]
numero [0-9]
tipo_basico int|double|char|bool
lista {tipo_basico}|list of {tipo_basico}
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


"&" {
    ECHO;
    return (OPUNI);
}
"#" {
    ECHO;
    return (OPUNI);
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
.
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
    ECHO
    return (TER2);
}

"|" {
    ECHO;
    return (MOVLISTA);
}

"$" {
    ECHO;
    return (PRINCIPIOLISTA);
}
"open" {
    ECHO;
    return (OPEN);
}
"close" {
    ECHO;
    return (CLOSE);
}
"-" {
    ECHO;
    return (MENOS);
}
({letra}|_)({letra}|{numero}|_)* {
     ECHO;
     return(IDENTIFICADOR);
 }

"<<" {
    ECHO;
    return (MOVLISTA);
 }

">>" {
    ECHO;
    return (MOVLISTA);
}
"$" {
    ECHO;
    return (PRINCIPIOLISTA);
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
{otros}	printf(" \nError lexico en linea: %d, No se reconoce la palabra '%s'. ",yylineno,yytext);

%%

main(){

printf(yylex());
}
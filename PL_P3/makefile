# $< Primero
# $^ todos
salida_lex.out : lex.yy.c
	gcc $< -o $@

test_lex: salida_lex.out prueba
	./salida_lex.out prueba

lex.yy.c : practica3.l constants.h
	flex $<

all: p3.yacc lex.yy.c 
	yacc $<
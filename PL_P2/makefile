# $< Primero
# $^ todos
lex.yy.c : practica2.l constants.h
	flex $<

salida_lex.out : lex.yy.c
	gcc $< -o $@

test_lex: salida_lex.out prueba
	./salida_lex.out prueba

all : p3.yacc lex.yy.c 
	yacc $<
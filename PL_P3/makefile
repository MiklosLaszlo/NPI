# $< Primero
# $^ todos
salida_lex.out : lex.yy.c
	gcc $< -o $@

test_lex: salida_lex.out prueba
	./salida_lex.out prueba

lex.yy.c : lex_p3.l constants.h
	flex $<

all: yacc_p3.y lex.yy.c 
	yacc $<
# $< Primero
# $^ todos
all: yacc_P3.y lex.yy.c 
	yacc -v -d $<
salida_lex.out : lex.yy.c
	gcc $< -o $@
test_lex: salida_lex.out prueba
	./salida_lex.out prueba

lex.yy.c : lex_P3.l constants.h
	flex $<

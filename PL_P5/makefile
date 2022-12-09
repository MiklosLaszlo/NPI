# $< Primero
# $^ todos
all: yacc

yacc: y.tab.c
	gcc -o salida_yacc y.tab.c
	
salida_lex.out : lex.yy.c
	gcc $< -o $@

test_lex: salida_lex.out prueba
	./salida_lex.out prueba

test_yacc: yacc
	./salida_yacc Ejemplo_cuaderno

lex.yy.c : lex_P5.l y.tab.h
	flex $<

y.tab.c: yacc_P5.y clean lex.yy.c 
	yacc -v -d $<

y.tab.h: yacc_P5.y clean lex.yy.c
	yacc -v -d $<
	

clean:
	rm -f y.output
	rm -f y.tab.c
	rm -f lex.yy.c

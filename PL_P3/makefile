# $< Primero
# $^ todos
all: y.tab.c
	gcc y.tab.c
y.tab.c: yacc_P3.y lex.yy.c 
	yacc -v -d $<

salida_lex.out : lex.yy.c
	gcc $< -o $@

test_lex: salida_lex.out prueba
	./salida_lex.out prueba

lex.yy.c : lex_P3.l y.tab.h
	flex $<

clean:
	rm -f y.output
	rm -f y.tab.c
	rm lex.yy.c

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

y.tab.h: yacc_P3_errores_prueba.y clean
	yacc -v -d $<

clean:
	rm -f y.output
	rm -f y.tab.c
	rm -f lex.yy.c

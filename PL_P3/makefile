# $< Primero
# $^ todos
<<<<<<< HEAD

all: yacc

yacc: y.tab.c
	gcc -o salida_yacc y.tab.c
=======
<<<<<<< HEAD
all: y.tab.c
	gcc y.tab.c
y.tab.c: yacc_P3.y lex.yy.c 
	yacc -v -d $<
>>>>>>> 30fe7abda18cf886c623c23ebe8432f733305550

salida_lex.out : lex.yy.c
	gcc $< -o $@

test_lex: salida_lex.out prueba
	./salida_lex.out prueba

test_yacc: yacc
	./salida_yacc prueba

lex.yy.c : lex_P3.l y.tab.h
	flex $<

y.tab.c: yacc_P3.y clean lex.yy.c 
	yacc -v -d $<

clean:
	rm -f y.output
	rm -f y.tab.c
	rm -f lex.yy.c

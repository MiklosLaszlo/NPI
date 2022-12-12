# $< Primero
# $^ todos
all: yacc

yacc: y.tab.c
	gcc -w -o salida_yacc y.tab.c

warning: y.tab.c
	gcc -o salida_yacc y.tab.c

primos1: yacc
	./salida_yacc primos1
	gcc generated.c -o ejec_primos1
	./ejec_primos1

primos2: yacc
	./salida_yacc primos2
	gcc generated.c -o ejec_primos2
	./ejec_primos2

for: yacc
	./salida_yacc ejemplo_for
	
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
	rm -f generated.c dec_fun
	rm -f salida_yacc
	rm -f ejec_primos1 ejec_primos2

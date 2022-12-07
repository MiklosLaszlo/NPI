/* A Bison parser, made by GNU Bison 3.5.1.  */

/* Bison interface for Yacc-like parsers in C

   Copyright (C) 1984, 1989-1990, 2000-2015, 2018-2020 Free Software Foundation,
   Inc.

   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.

   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */

/* Undocumented macros, especially those whose name start with YY_,
   are private implementation details.  Do not rely on them.  */

#ifndef YY_YY_Y_TAB_H_INCLUDED
# define YY_YY_Y_TAB_H_INCLUDED
/* Debug traces.  */
#ifndef YYDEBUG
# define YYDEBUG 0
#endif
#if YYDEBUG
extern int yydebug;
#endif

/* Token type.  */
#ifndef YYTOKENTYPE
# define YYTOKENTYPE
  enum yytokentype
  {
    IGUAL = 258,
    LOGICOS = 259,
    IGUALDAD = 260,
    COMPARACION = 261,
    MAS = 262,
    MENOSMENOS = 263,
    MULTIPLICATIVO = 264,
    NOT = 265,
    SOSTENIDO = 266,
    INTERROGACION = 267,
    TER1 = 268,
    ARROBA = 269,
    PORCENTAJE = 270,
    DOBLEPOR = 271,
    MENOS = 272,
    LITERAL = 273,
    IDENTIFICADOR = 274,
    LLAVEIZQ = 275,
    LLAVEDCH = 276,
    CORIZQ = 277,
    CORDCH = 278,
    PARIZQ = 279,
    PARDCH = 280,
    MAIN = 281,
    TYPE = 282,
    IF = 283,
    THEN = 284,
    ELSE = 285,
    FOR = 286,
    TO = 287,
    DO = 288,
    WRITE = 289,
    READ = 290,
    COMA = 291,
    PYC = 292,
    RETURN = 293,
    WHILE = 294,
    PRINCIPIOLISTA = 295,
    MOVLISTA = 296,
    INIVARIABLES = 297,
    FINVARIABLES = 298
  };
#endif
/* Tokens.  */
#define IGUAL 258
#define LOGICOS 259
#define IGUALDAD 260
#define COMPARACION 261
#define MAS 262
#define MENOSMENOS 263
#define MULTIPLICATIVO 264
#define NOT 265
#define SOSTENIDO 266
#define INTERROGACION 267
#define TER1 268
#define ARROBA 269
#define PORCENTAJE 270
#define DOBLEPOR 271
#define MENOS 272
#define LITERAL 273
#define IDENTIFICADOR 274
#define LLAVEIZQ 275
#define LLAVEDCH 276
#define CORIZQ 277
#define CORDCH 278
#define PARIZQ 279
#define PARDCH 280
#define MAIN 281
#define TYPE 282
#define IF 283
#define THEN 284
#define ELSE 285
#define FOR 286
#define TO 287
#define DO 288
#define WRITE 289
#define READ 290
#define COMA 291
#define PYC 292
#define RETURN 293
#define WHILE 294
#define PRINCIPIOLISTA 295
#define MOVLISTA 296
#define INIVARIABLES 297
#define FINVARIABLES 298

/* Value type.  */
#if ! defined YYSTYPE && ! defined YYSTYPE_IS_DECLARED
typedef int YYSTYPE;
# define YYSTYPE_IS_TRIVIAL 1
# define YYSTYPE_IS_DECLARED 1
#endif


extern YYSTYPE yylval;

int yyparse (void);

#endif /* !YY_YY_Y_TAB_H_INCLUDED  */

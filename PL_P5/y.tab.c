/* A Bison parser, made by GNU Bison 3.5.1.  */

/* Bison implementation for Yacc-like parsers in C

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

/* C LALR(1) parser skeleton written by Richard Stallman, by
   simplifying the original so-called "semantic" parser.  */

/* All symbols defined below should begin with yy or YY, to avoid
   infringing on user name space.  This should be done even for local
   variables, as they might otherwise be expanded by user macros.
   There are some unavoidable exceptions within include files to
   define necessary library symbols; they are noted "INFRINGES ON
   USER NAME SPACE" below.  */

/* Undocumented macros, especially those whose name start with YY_,
   are private implementation details.  Do not rely on them.  */

/* Identify Bison output.  */
#define YYBISON 1

/* Bison version.  */
#define YYBISON_VERSION "3.5.1"

/* Skeleton name.  */
#define YYSKELETON_NAME "yacc.c"

/* Pure parsers.  */
#define YYPURE 0

/* Push parsers.  */
#define YYPUSH 0

/* Pull parsers.  */
#define YYPULL 1




/* First part of user prologue.  */
#line 1 "yacc_P5.y"

#define YYSTYPE struct entradaTS
#include "lex.yy.c"
#include "p5.h"
#define RESET_COLOR    "\x1b[0m"
#define BG_COLOR_YELLOW     "\x1B[43m"
#define BG_COLOR_PURPLE     "\x1B[45m"
#ifndef ENUM
#define ENUM
	typedef enum {marca,funcion,variable,parametro_formal,indefinido,operador} TipoEntrada;
	typedef enum {booleano,entero,real,caracter,lista,desconocido} TipoDato;
	typedef enum {
		negacion,sostenido,interrogacion,menos,
		equal, not_equal,
		and, or, xor,
		masmas,
		mas,
		entre, por,
		less, greater, less_eq, greater_eq,arroba, menosmenos, porcentaje,doblepor} TipoOperador;

	typedef struct  entradaTS{
   TipoEntrada entrada;      // Indica el tipo de entrada

   char nombre[100];                 // Contendra los caracteres que forman el identificador en nuestro lenguaje
   char nombre_traductor[200];       // Contendra los caracteres que forman el identificador al traducirlo a C
   
   TipoDato dato_referencia; // En caso de que entrada sea funcion,variable
                             // o parametro formal indica el tipo de dato al que hace referencia
   TipoDato dato_lista;      //tipo de datos que contiene la lista                    
   unsigned int n_parametros;  //Si tipoDato  es funcion indica el numero de parametros o el tamaño de la lista
   unsigned int puntero_lista; // Puntero que señala la posición en la lista
   
   TipoOperador tipo_operador; // En caso de ser operador, qué operador es  
};
#endif

void yyerror( char *msg ){
	//fprintf(stderr, BG_COLOR_YELLOW "YY Error sintáctico"  RESET_COLOR " Línea %d, No se esperaba el lexema \'%s\'\n" ,yylineno, yytext);
}
void explicacion_error_sintactico( char * msg ) {
	fprintf(stderr, BG_COLOR_YELLOW "Error sintáctico"  RESET_COLOR " Línea %d, No se esperaba \'%s\'. %s\n" ,yylineno, yytext, msg);
}
void explicacion_error_semantico( char * msg ) {
	fprintf(stderr, BG_COLOR_PURPLE "Error semántico"  RESET_COLOR " Línea: %d.Error: %s\n" ,yylineno, msg);
}
int n_parametros=0;
int funcion_actual=-1;
int funcion_analizando=-1;
TipoDato daton_anterior=desconocido;
char funcion_declarandose[20][100];
char funcion_usandose[20][100];

#line 123 "y.tab.c"

# ifndef YY_CAST
#  ifdef __cplusplus
#   define YY_CAST(Type, Val) static_cast<Type> (Val)
#   define YY_REINTERPRET_CAST(Type, Val) reinterpret_cast<Type> (Val)
#  else
#   define YY_CAST(Type, Val) ((Type) (Val))
#   define YY_REINTERPRET_CAST(Type, Val) ((Type) (Val))
#  endif
# endif
# ifndef YY_NULLPTR
#  if defined __cplusplus
#   if 201103L <= __cplusplus
#    define YY_NULLPTR nullptr
#   else
#    define YY_NULLPTR 0
#   endif
#  else
#   define YY_NULLPTR ((void*)0)
#  endif
# endif

/* Enabling verbose error messages.  */
#ifdef YYERROR_VERBOSE
# undef YYERROR_VERBOSE
# define YYERROR_VERBOSE 1
#else
# define YYERROR_VERBOSE 0
#endif

/* Use api.header.include to #include this header
   instead of duplicating it here.  */
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



#ifdef short
# undef short
#endif

/* On compilers that do not define __PTRDIFF_MAX__ etc., make sure
   <limits.h> and (if available) <stdint.h> are included
   so that the code can choose integer types of a good width.  */

#ifndef __PTRDIFF_MAX__
# include <limits.h> /* INFRINGES ON USER NAME SPACE */
# if defined __STDC_VERSION__ && 199901 <= __STDC_VERSION__
#  include <stdint.h> /* INFRINGES ON USER NAME SPACE */
#  define YY_STDINT_H
# endif
#endif

/* Narrow types that promote to a signed type and that can represent a
   signed or unsigned integer of at least N bits.  In tables they can
   save space and decrease cache pressure.  Promoting to a signed type
   helps avoid bugs in integer arithmetic.  */

#ifdef __INT_LEAST8_MAX__
typedef __INT_LEAST8_TYPE__ yytype_int8;
#elif defined YY_STDINT_H
typedef int_least8_t yytype_int8;
#else
typedef signed char yytype_int8;
#endif

#ifdef __INT_LEAST16_MAX__
typedef __INT_LEAST16_TYPE__ yytype_int16;
#elif defined YY_STDINT_H
typedef int_least16_t yytype_int16;
#else
typedef short yytype_int16;
#endif

#if defined __UINT_LEAST8_MAX__ && __UINT_LEAST8_MAX__ <= __INT_MAX__
typedef __UINT_LEAST8_TYPE__ yytype_uint8;
#elif (!defined __UINT_LEAST8_MAX__ && defined YY_STDINT_H \
       && UINT_LEAST8_MAX <= INT_MAX)
typedef uint_least8_t yytype_uint8;
#elif !defined __UINT_LEAST8_MAX__ && UCHAR_MAX <= INT_MAX
typedef unsigned char yytype_uint8;
#else
typedef short yytype_uint8;
#endif

#if defined __UINT_LEAST16_MAX__ && __UINT_LEAST16_MAX__ <= __INT_MAX__
typedef __UINT_LEAST16_TYPE__ yytype_uint16;
#elif (!defined __UINT_LEAST16_MAX__ && defined YY_STDINT_H \
       && UINT_LEAST16_MAX <= INT_MAX)
typedef uint_least16_t yytype_uint16;
#elif !defined __UINT_LEAST16_MAX__ && USHRT_MAX <= INT_MAX
typedef unsigned short yytype_uint16;
#else
typedef int yytype_uint16;
#endif

#ifndef YYPTRDIFF_T
# if defined __PTRDIFF_TYPE__ && defined __PTRDIFF_MAX__
#  define YYPTRDIFF_T __PTRDIFF_TYPE__
#  define YYPTRDIFF_MAXIMUM __PTRDIFF_MAX__
# elif defined PTRDIFF_MAX
#  ifndef ptrdiff_t
#   include <stddef.h> /* INFRINGES ON USER NAME SPACE */
#  endif
#  define YYPTRDIFF_T ptrdiff_t
#  define YYPTRDIFF_MAXIMUM PTRDIFF_MAX
# else
#  define YYPTRDIFF_T long
#  define YYPTRDIFF_MAXIMUM LONG_MAX
# endif
#endif

#ifndef YYSIZE_T
# ifdef __SIZE_TYPE__
#  define YYSIZE_T __SIZE_TYPE__
# elif defined size_t
#  define YYSIZE_T size_t
# elif defined __STDC_VERSION__ && 199901 <= __STDC_VERSION__
#  include <stddef.h> /* INFRINGES ON USER NAME SPACE */
#  define YYSIZE_T size_t
# else
#  define YYSIZE_T unsigned
# endif
#endif

#define YYSIZE_MAXIMUM                                  \
  YY_CAST (YYPTRDIFF_T,                                 \
           (YYPTRDIFF_MAXIMUM < YY_CAST (YYSIZE_T, -1)  \
            ? YYPTRDIFF_MAXIMUM                         \
            : YY_CAST (YYSIZE_T, -1)))

#define YYSIZEOF(X) YY_CAST (YYPTRDIFF_T, sizeof (X))

/* Stored state numbers (used for stacks). */
typedef yytype_uint8 yy_state_t;

/* State numbers in computations.  */
typedef int yy_state_fast_t;

#ifndef YY_
# if defined YYENABLE_NLS && YYENABLE_NLS
#  if ENABLE_NLS
#   include <libintl.h> /* INFRINGES ON USER NAME SPACE */
#   define YY_(Msgid) dgettext ("bison-runtime", Msgid)
#  endif
# endif
# ifndef YY_
#  define YY_(Msgid) Msgid
# endif
#endif

#ifndef YY_ATTRIBUTE_PURE
# if defined __GNUC__ && 2 < __GNUC__ + (96 <= __GNUC_MINOR__)
#  define YY_ATTRIBUTE_PURE __attribute__ ((__pure__))
# else
#  define YY_ATTRIBUTE_PURE
# endif
#endif

#ifndef YY_ATTRIBUTE_UNUSED
# if defined __GNUC__ && 2 < __GNUC__ + (7 <= __GNUC_MINOR__)
#  define YY_ATTRIBUTE_UNUSED __attribute__ ((__unused__))
# else
#  define YY_ATTRIBUTE_UNUSED
# endif
#endif

/* Suppress unused-variable warnings by "using" E.  */
#if ! defined lint || defined __GNUC__
# define YYUSE(E) ((void) (E))
#else
# define YYUSE(E) /* empty */
#endif

#if defined __GNUC__ && ! defined __ICC && 407 <= __GNUC__ * 100 + __GNUC_MINOR__
/* Suppress an incorrect diagnostic about yylval being uninitialized.  */
# define YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN                            \
    _Pragma ("GCC diagnostic push")                                     \
    _Pragma ("GCC diagnostic ignored \"-Wuninitialized\"")              \
    _Pragma ("GCC diagnostic ignored \"-Wmaybe-uninitialized\"")
# define YY_IGNORE_MAYBE_UNINITIALIZED_END      \
    _Pragma ("GCC diagnostic pop")
#else
# define YY_INITIAL_VALUE(Value) Value
#endif
#ifndef YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
# define YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
# define YY_IGNORE_MAYBE_UNINITIALIZED_END
#endif
#ifndef YY_INITIAL_VALUE
# define YY_INITIAL_VALUE(Value) /* Nothing. */
#endif

#if defined __cplusplus && defined __GNUC__ && ! defined __ICC && 6 <= __GNUC__
# define YY_IGNORE_USELESS_CAST_BEGIN                          \
    _Pragma ("GCC diagnostic push")                            \
    _Pragma ("GCC diagnostic ignored \"-Wuseless-cast\"")
# define YY_IGNORE_USELESS_CAST_END            \
    _Pragma ("GCC diagnostic pop")
#endif
#ifndef YY_IGNORE_USELESS_CAST_BEGIN
# define YY_IGNORE_USELESS_CAST_BEGIN
# define YY_IGNORE_USELESS_CAST_END
#endif


#define YY_ASSERT(E) ((void) (0 && (E)))

#if ! defined yyoverflow || YYERROR_VERBOSE

/* The parser invokes alloca or malloc; define the necessary symbols.  */

# ifdef YYSTACK_USE_ALLOCA
#  if YYSTACK_USE_ALLOCA
#   ifdef __GNUC__
#    define YYSTACK_ALLOC __builtin_alloca
#   elif defined __BUILTIN_VA_ARG_INCR
#    include <alloca.h> /* INFRINGES ON USER NAME SPACE */
#   elif defined _AIX
#    define YYSTACK_ALLOC __alloca
#   elif defined _MSC_VER
#    include <malloc.h> /* INFRINGES ON USER NAME SPACE */
#    define alloca _alloca
#   else
#    define YYSTACK_ALLOC alloca
#    if ! defined _ALLOCA_H && ! defined EXIT_SUCCESS
#     include <stdlib.h> /* INFRINGES ON USER NAME SPACE */
      /* Use EXIT_SUCCESS as a witness for stdlib.h.  */
#     ifndef EXIT_SUCCESS
#      define EXIT_SUCCESS 0
#     endif
#    endif
#   endif
#  endif
# endif

# ifdef YYSTACK_ALLOC
   /* Pacify GCC's 'empty if-body' warning.  */
#  define YYSTACK_FREE(Ptr) do { /* empty */; } while (0)
#  ifndef YYSTACK_ALLOC_MAXIMUM
    /* The OS might guarantee only one guard page at the bottom of the stack,
       and a page size can be as small as 4096 bytes.  So we cannot safely
       invoke alloca (N) if N exceeds 4096.  Use a slightly smaller number
       to allow for a few compiler-allocated temporary stack slots.  */
#   define YYSTACK_ALLOC_MAXIMUM 4032 /* reasonable circa 2006 */
#  endif
# else
#  define YYSTACK_ALLOC YYMALLOC
#  define YYSTACK_FREE YYFREE
#  ifndef YYSTACK_ALLOC_MAXIMUM
#   define YYSTACK_ALLOC_MAXIMUM YYSIZE_MAXIMUM
#  endif
#  if (defined __cplusplus && ! defined EXIT_SUCCESS \
       && ! ((defined YYMALLOC || defined malloc) \
             && (defined YYFREE || defined free)))
#   include <stdlib.h> /* INFRINGES ON USER NAME SPACE */
#   ifndef EXIT_SUCCESS
#    define EXIT_SUCCESS 0
#   endif
#  endif
#  ifndef YYMALLOC
#   define YYMALLOC malloc
#   if ! defined malloc && ! defined EXIT_SUCCESS
void *malloc (YYSIZE_T); /* INFRINGES ON USER NAME SPACE */
#   endif
#  endif
#  ifndef YYFREE
#   define YYFREE free
#   if ! defined free && ! defined EXIT_SUCCESS
void free (void *); /* INFRINGES ON USER NAME SPACE */
#   endif
#  endif
# endif
#endif /* ! defined yyoverflow || YYERROR_VERBOSE */


#if (! defined yyoverflow \
     && (! defined __cplusplus \
         || (defined YYSTYPE_IS_TRIVIAL && YYSTYPE_IS_TRIVIAL)))

/* A type that is properly aligned for any stack member.  */
union yyalloc
{
  yy_state_t yyss_alloc;
  YYSTYPE yyvs_alloc;
};

/* The size of the maximum gap between one aligned stack and the next.  */
# define YYSTACK_GAP_MAXIMUM (YYSIZEOF (union yyalloc) - 1)

/* The size of an array large to enough to hold all stacks, each with
   N elements.  */
# define YYSTACK_BYTES(N) \
     ((N) * (YYSIZEOF (yy_state_t) + YYSIZEOF (YYSTYPE)) \
      + YYSTACK_GAP_MAXIMUM)

# define YYCOPY_NEEDED 1

/* Relocate STACK from its old location to the new one.  The
   local variables YYSIZE and YYSTACKSIZE give the old and new number of
   elements in the stack, and YYPTR gives the new location of the
   stack.  Advance YYPTR to a properly aligned location for the next
   stack.  */
# define YYSTACK_RELOCATE(Stack_alloc, Stack)                           \
    do                                                                  \
      {                                                                 \
        YYPTRDIFF_T yynewbytes;                                         \
        YYCOPY (&yyptr->Stack_alloc, Stack, yysize);                    \
        Stack = &yyptr->Stack_alloc;                                    \
        yynewbytes = yystacksize * YYSIZEOF (*Stack) + YYSTACK_GAP_MAXIMUM; \
        yyptr += yynewbytes / YYSIZEOF (*yyptr);                        \
      }                                                                 \
    while (0)

#endif

#if defined YYCOPY_NEEDED && YYCOPY_NEEDED
/* Copy COUNT objects from SRC to DST.  The source and destination do
   not overlap.  */
# ifndef YYCOPY
#  if defined __GNUC__ && 1 < __GNUC__
#   define YYCOPY(Dst, Src, Count) \
      __builtin_memcpy (Dst, Src, YY_CAST (YYSIZE_T, (Count)) * sizeof (*(Src)))
#  else
#   define YYCOPY(Dst, Src, Count)              \
      do                                        \
        {                                       \
          YYPTRDIFF_T yyi;                      \
          for (yyi = 0; yyi < (Count); yyi++)   \
            (Dst)[yyi] = (Src)[yyi];            \
        }                                       \
      while (0)
#  endif
# endif
#endif /* !YYCOPY_NEEDED */

/* YYFINAL -- State number of the termination state.  */
#define YYFINAL  6
/* YYLAST -- Last index in YYTABLE.  */
#define YYLAST   503

/* YYNTOKENS -- Number of terminals.  */
#define YYNTOKENS  44
/* YYNNTS -- Number of nonterminals.  */
#define YYNNTS  43
/* YYNRULES -- Number of rules.  */
#define YYNRULES  133
/* YYNSTATES -- Number of states.  */
#define YYNSTATES  212

#define YYUNDEFTOK  2
#define YYMAXUTOK   298


/* YYTRANSLATE(TOKEN-NUM) -- Symbol number corresponding to TOKEN-NUM
   as returned by yylex, with out-of-bounds checking.  */
#define YYTRANSLATE(YYX)                                                \
  (0 <= (YYX) && (YYX) <= YYMAXUTOK ? yytranslate[YYX] : YYUNDEFTOK)

/* YYTRANSLATE[TOKEN-NUM] -- Symbol number corresponding to TOKEN-NUM
   as returned by yylex.  */
static const yytype_int8 yytranslate[] =
{
       0,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     1,     2,     3,     4,
       5,     6,     7,     8,     9,    10,    11,    12,    13,    14,
      15,    16,    17,    18,    19,    20,    21,    22,    23,    24,
      25,    26,    27,    28,    29,    30,    31,    32,    33,    34,
      35,    36,    37,    38,    39,    40,    41,    42,    43
};

#if YYDEBUG
  /* YYRLINE[YYN] -- Source line where rule number YYN was defined.  */
static const yytype_int16 yyrline[] =
{
       0,   109,   109,   110,   111,   112,   112,   117,   117,   124,
     124,   125,   126,   127,   128,   129,   130,   131,   132,   133,
     134,   140,   145,   146,   148,   149,   147,   152,   153,   153,
     154,   154,   154,   155,   155,   155,   156,   157,   159,   160,
     161,   162,   163,   165,   166,   167,   168,   169,   170,   171,
     172,   173,   174,   175,   176,   177,   178,   179,   180,   181,
     182,   183,   184,   185,   187,   188,   189,   190,   191,   192,
     193,   194,   195,   196,   197,   198,   199,   200,   201,   202,
     203,   205,   206,   207,   208,   209,   210,   211,   212,   213,
     220,   230,   231,   233,   234,   236,   237,   238,   239,   240,
     241,   242,   243,   244,   246,   247,   248,   249,   251,   251,
     257,   258,   259,   264,   269,   270,   271,   272,   274,   278,
     283,   284,   285,   286,   287,   288,   289,   290,   291,   292,
     293,   294,   295,   296
};
#endif

#if YYDEBUG || YYERROR_VERBOSE || 0
/* YYTNAME[SYMBOL-NUM] -- String name of the symbol SYMBOL-NUM.
   First, the terminals, then, starting at YYNTOKENS, nonterminals.  */
static const char *const yytname[] =
{
  "$end", "error", "$undefined", "IGUAL", "LOGICOS", "IGUALDAD",
  "COMPARACION", "MAS", "MENOSMENOS", "MULTIPLICATIVO", "NOT", "SOSTENIDO",
  "INTERROGACION", "TER1", "ARROBA", "PORCENTAJE", "DOBLEPOR", "MENOS",
  "LITERAL", "IDENTIFICADOR", "LLAVEIZQ", "LLAVEDCH", "CORIZQ", "CORDCH",
  "PARIZQ", "PARDCH", "MAIN", "TYPE", "IF", "THEN", "ELSE", "FOR", "TO",
  "DO", "WRITE", "READ", "COMA", "PYC", "RETURN", "WHILE",
  "PRINCIPIOLISTA", "MOVLISTA", "INIVARIABLES", "FINVARIABLES", "$accept",
  "programa", "bloque", "$@1", "$@2", "$@3", "declar_de_variables_locales",
  "variables_locales", "cuerpo_declar_variables", "lista_identificadores",
  "declar_de_subprogs", "declarar_funcion", "$@4", "$@5", "$@6", "$@7",
  "$@8", "$@9", "$@10", "parametros", "lista_parametros", "tipo_basico",
  "sentencias", "sentencia", "sentencia_asignacion", "sentencia_if",
  "sentencia_while", "sentencia_entrada", "sentencia_salida",
  "sentencia_return", "sentencia_for_pascal", "sentencia_lista",
  "lista_entrada", "lista_salida", "expresion", "llamar_funcion", "$@11",
  "argumentos", "lista_argumentos", "agregado", "lista_expresiones",
  "OPUNI", "OPBIN", YY_NULLPTR
};
#endif

# ifdef YYPRINT
/* YYTOKNUM[NUM] -- (External) token number corresponding to the
   (internal) symbol number NUM (which must be that of a token).  */
static const yytype_int16 yytoknum[] =
{
       0,   256,   257,   258,   259,   260,   261,   262,   263,   264,
     265,   266,   267,   268,   269,   270,   271,   272,   273,   274,
     275,   276,   277,   278,   279,   280,   281,   282,   283,   284,
     285,   286,   287,   288,   289,   290,   291,   292,   293,   294,
     295,   296,   297,   298
};
# endif

#define YYPACT_NINF (-154)

#define yypact_value_is_default(Yyn) \
  ((Yyn) == YYPACT_NINF)

#define YYTABLE_NINF (-109)

#define yytable_value_is_error(Yyn) \
  0

  /* YYPACT[STATE-NUM] -- Index in YYTABLE of the portion describing
     STATE-NUM.  */
static const yytype_int16 yypact[] =
{
      -9,    61,    25,  -154,    12,    10,  -154,    -8,    -8,    -8,
    -154,  -154,    26,  -154,  -154,  -154,  -154,  -154,     1,    11,
      90,   382,    69,    28,  -154,  -154,    45,  -154,  -154,  -154,
    -154,    39,     2,    13,    96,    17,    34,  -154,   215,    62,
      68,  -154,    98,   404,  -154,  -154,  -154,  -154,  -154,  -154,
    -154,  -154,  -154,  -154,  -154,  -154,   108,   234,    79,  -154,
     249,  -154,   132,    22,  -154,   264,  -154,   123,  -154,  -154,
    -154,  -154,  -154,  -154,   129,   279,   294,    97,  -154,  -154,
     309,  -154,   324,   146,  -154,   185,  -154,  -154,  -154,  -154,
     124,  -154,  -154,   184,   234,  -154,   339,  -154,     6,   473,
    -154,  -154,    20,   136,  -154,   473,    38,  -154,   390,  -154,
    -154,  -154,  -154,  -154,  -154,  -154,   354,  -154,  -154,  -154,
    -154,  -154,   369,  -154,   149,  -154,   206,  -154,   170,   186,
     170,   170,  -154,  -154,  -154,     7,    63,  -154,   142,  -154,
      14,   452,  -154,    15,   176,   452,  -154,  -154,  -154,   452,
    -154,  -154,   444,  -154,   486,  -154,    64,   127,  -154,  -154,
    -154,  -154,  -154,    72,   452,  -154,   201,  -154,  -154,   473,
    -154,  -154,  -154,   473,    21,   473,  -154,   409,  -154,  -154,
    -154,    58,   125,   183,   203,   183,  -154,   175,   163,  -154,
    -154,  -154,   452,   103,  -154,  -154,   179,  -154,  -154,   171,
    -154,   216,    87,   183,   473,   199,  -154,  -154,  -154,  -154,
    -154,  -154
};

  /* YYDEFACT[STATE-NUM] -- Default reduction number in state STATE-NUM.
     Performed when YYTABLE does not specify something else to do.  Zero
     means the default is an error.  */
static const yytype_uint8 yydefact[] =
{
       0,     0,     0,     3,     5,     0,     1,    12,    12,    12,
       4,     2,     0,    23,    23,    23,    13,    42,     0,     0,
       0,     0,     0,     0,    14,    11,     0,    17,    16,    19,
      21,    18,     0,     0,     0,     0,     0,    53,     0,     0,
       0,    22,     0,     0,    44,    45,    46,    47,    48,    49,
      50,    51,    52,     8,    10,    15,     0,     0,     0,    59,
       0,    84,     0,     0,    76,     0,    71,     0,    81,   120,
     121,   122,   123,   102,    99,     0,     0,     0,   100,   101,
       0,    66,     0,     0,    27,    24,     6,    43,    20,    55,
       0,    89,    60,     0,     0,    85,     0,    77,     0,    94,
      72,    92,     0,     0,   116,   119,     0,   103,     0,    82,
     126,   124,   129,   127,   131,   128,     0,   130,   132,   133,
     125,    80,     0,   104,    96,    67,     0,    90,     0,     0,
       0,     0,    56,    54,    61,     0,     0,    86,     0,    78,
       0,     0,    73,     0,     0,   114,   109,   117,   115,     0,
      95,   106,     0,   105,    97,    68,     0,     0,    25,    29,
      31,    34,    62,     0,     0,    87,     0,    79,    75,    93,
      74,    70,    91,   112,     0,   118,   107,   130,    69,    65,
      36,     0,     0,     0,     0,     0,    63,    58,     0,    88,
     111,   110,     0,    98,    38,    37,     0,    41,    39,     0,
      32,     0,     0,     0,   113,     0,    26,    35,    64,    57,
      83,    40
};

  /* YYPGOTO[NTERM-NUM].  */
static const yytype_int16 yypgoto[] =
{
    -154,  -154,  -153,  -154,  -154,  -154,   114,  -154,   210,  -154,
     159,  -154,  -154,  -154,  -154,  -154,  -154,  -154,  -154,    51,
    -154,   -12,  -154,   181,   195,  -154,  -154,  -154,  -154,  -154,
    -154,  -154,  -154,  -154,   -56,  -154,  -154,  -154,  -154,  -154,
    -154,  -154,  -154
};

  /* YYDEFGOTO[NTERM-NUM].  */
static const yytype_int16 yydefgoto[] =
{
      -1,     2,     5,     7,     8,     9,    13,    18,    19,    31,
      21,    41,   128,   183,   129,   130,   184,   131,   185,   158,
     181,    42,    43,    44,    45,    46,    47,    48,    49,    50,
      51,    52,   102,    98,    77,    78,   103,   146,   174,    79,
     106,    80,   122
};

  /* YYTABLE[YYPACT[STATE-NUM]] -- What to do in state STATE-NUM.  If
     positive, shift that token.  If negative, reduce the rule whose
     number is the opposite.  If YYTABLE_NINF, syntax error.  */
static const yytype_int16 yytable[] =
{
      20,    90,    24,   179,    93,    57,    20,   139,   162,    99,
     187,    10,    27,    -9,    59,   167,   170,     1,    64,   105,
     108,   142,   190,    95,   124,     6,   126,    16,    17,    54,
     199,   140,   201,    -7,    12,    66,   163,    60,   136,   147,
     138,    65,   141,    58,    25,   143,   191,    11,    28,   209,
     210,   168,   171,    17,    96,    17,   144,   192,    67,   194,
     152,   148,     3,    81,   132,   178,   154,   110,   111,   112,
     113,   114,   115,   186,   149,    56,   116,   117,   118,   119,
     120,     4,    55,   195,     4,   169,    82,    83,   208,   173,
      53,    29,     4,   175,   196,   164,    17,    61,   109,    84,
     133,   110,   111,   112,   113,   114,   115,     4,   188,    30,
     116,   117,   118,   119,   120,    62,    91,    85,   118,   119,
     120,   193,    14,    15,   100,   132,   197,    88,   110,   111,
     112,   113,   114,   115,   121,    94,   204,   116,   117,   118,
     119,   120,   101,   165,   198,   182,   110,   111,   112,   113,
     114,   115,   180,  -108,    17,   116,   117,   118,   119,   120,
     145,   133,   116,   117,   118,   119,   120,   110,   111,   112,
     113,   114,   115,    22,    23,   166,   116,   117,   118,   119,
     120,   160,   161,   127,   205,   134,   -28,   159,   110,   111,
     112,   113,   114,   115,   157,   172,   203,   116,   117,   118,
     119,   120,   189,     4,   200,   202,    17,   155,   206,   135,
     110,   111,   112,   113,   114,   115,    68,   207,   211,   116,
     117,   118,   119,   120,    87,    69,    70,    71,    26,    63,
       0,   156,    72,    73,    74,    89,     0,    75,     0,    76,
       0,     0,     0,     0,    69,    70,    71,     0,     0,     0,
      92,    72,    73,    74,     0,     0,    75,     0,    76,    69,
      70,    71,     0,     0,     0,    97,    72,    73,    74,     0,
       0,    75,     0,    76,    69,    70,    71,     0,     0,     0,
     104,    72,    73,    74,     0,     0,    75,     0,    76,    69,
      70,    71,     0,     0,     0,   107,    72,    73,    74,     0,
       0,    75,     0,    76,    69,    70,    71,     0,     0,     0,
     123,    72,    73,    74,     0,     0,    75,     0,    76,    69,
      70,    71,     0,     0,     0,   125,    72,    73,    74,     0,
       0,    75,     0,    76,    69,    70,    71,     0,     0,     0,
     137,    72,    73,    74,     0,     0,    75,     0,    76,    69,
      70,    71,     0,     0,     0,   151,    72,    73,    74,     0,
       0,    75,     0,    76,    69,    70,    71,     0,     0,     0,
     153,    72,    73,    74,     0,     0,    75,     0,    76,    69,
      70,    71,     0,     0,     0,     0,    72,    73,    74,     0,
       0,    75,     0,    76,   110,   111,   112,   113,   114,   115,
       0,    32,     0,   116,   117,   118,   119,   120,     0,    17,
      33,     0,     0,    34,     0,   150,    35,    36,     0,    37,
      38,    39,    40,    32,     0,    86,    72,    73,    74,     0,
       0,    75,    33,    76,     0,    34,     0,     0,    35,    36,
       0,    37,    38,    39,    40,   176,     0,     0,   110,   111,
     112,   113,   114,   115,     0,     0,     0,   116,   177,   118,
     119,   120,    69,    70,    71,     0,     0,     0,     0,    72,
      73,    74,     0,     0,    75,     0,    76,   110,   111,   112,
     113,   114,   115,     0,     0,     0,   116,   117,   118,   119,
     120,   111,   112,   113,   114,   115,     0,     0,     0,   116,
     117,   118,   119,   120
};

static const yytype_int16 yycheck[] =
{
      12,    57,     1,   156,    60,     3,    18,     1,     1,    65,
     163,     1,     1,     1,     1,     1,     1,    26,     1,    75,
      76,     1,     1,     1,    80,     0,    82,     1,    27,     1,
     183,    25,   185,    21,    42,     1,    29,    24,    94,     1,
      96,    24,    36,    41,    43,    25,    25,    37,    37,   202,
     203,    37,    37,    27,    32,    27,    36,    36,    24,     1,
     116,    23,     1,     1,     1,     1,   122,     4,     5,     6,
       7,     8,     9,     1,    36,    36,    13,    14,    15,    16,
      17,    20,    37,    25,    20,   141,    24,    19,     1,   145,
      21,     1,    20,   149,    36,    32,    27,     1,     1,     1,
      37,     4,     5,     6,     7,     8,     9,    20,   164,    19,
      13,    14,    15,    16,    17,    19,    37,    19,    15,    16,
      17,   177,     8,     9,     1,     1,     1,    19,     4,     5,
       6,     7,     8,     9,    37,     3,   192,    13,    14,    15,
      16,    17,    19,     1,    19,   157,     4,     5,     6,     7,
       8,     9,    25,    24,    27,    13,    14,    15,    16,    17,
      24,    37,    13,    14,    15,    16,    17,     4,     5,     6,
       7,     8,     9,    14,    15,    33,    13,    14,    15,    16,
      17,   130,   131,    37,   196,     1,     1,     1,     4,     5,
       6,     7,     8,     9,    24,    19,    33,    13,    14,    15,
      16,    17,     1,    20,     1,    30,    27,     1,    37,    25,
       4,     5,     6,     7,     8,     9,     1,     1,    19,    13,
      14,    15,    16,    17,    43,    10,    11,    12,    18,    34,
      -1,    25,    17,    18,    19,     1,    -1,    22,    -1,    24,
      -1,    -1,    -1,    -1,    10,    11,    12,    -1,    -1,    -1,
       1,    17,    18,    19,    -1,    -1,    22,    -1,    24,    10,
      11,    12,    -1,    -1,    -1,     1,    17,    18,    19,    -1,
      -1,    22,    -1,    24,    10,    11,    12,    -1,    -1,    -1,
       1,    17,    18,    19,    -1,    -1,    22,    -1,    24,    10,
      11,    12,    -1,    -1,    -1,     1,    17,    18,    19,    -1,
      -1,    22,    -1,    24,    10,    11,    12,    -1,    -1,    -1,
       1,    17,    18,    19,    -1,    -1,    22,    -1,    24,    10,
      11,    12,    -1,    -1,    -1,     1,    17,    18,    19,    -1,
      -1,    22,    -1,    24,    10,    11,    12,    -1,    -1,    -1,
       1,    17,    18,    19,    -1,    -1,    22,    -1,    24,    10,
      11,    12,    -1,    -1,    -1,     1,    17,    18,    19,    -1,
      -1,    22,    -1,    24,    10,    11,    12,    -1,    -1,    -1,
       1,    17,    18,    19,    -1,    -1,    22,    -1,    24,    10,
      11,    12,    -1,    -1,    -1,    -1,    17,    18,    19,    -1,
      -1,    22,    -1,    24,     4,     5,     6,     7,     8,     9,
      -1,    19,    -1,    13,    14,    15,    16,    17,    -1,    27,
      28,    -1,    -1,    31,    -1,    25,    34,    35,    -1,    37,
      38,    39,    40,    19,    -1,    21,    17,    18,    19,    -1,
      -1,    22,    28,    24,    -1,    31,    -1,    -1,    34,    35,
      -1,    37,    38,    39,    40,     1,    -1,    -1,     4,     5,
       6,     7,     8,     9,    -1,    -1,    -1,    13,    14,    15,
      16,    17,    10,    11,    12,    -1,    -1,    -1,    -1,    17,
      18,    19,    -1,    -1,    22,    -1,    24,     4,     5,     6,
       7,     8,     9,    -1,    -1,    -1,    13,    14,    15,    16,
      17,     5,     6,     7,     8,     9,    -1,    -1,    -1,    13,
      14,    15,    16,    17
};

  /* YYSTOS[STATE-NUM] -- The (internal number of the) accessing
     symbol of state STATE-NUM.  */
static const yytype_int8 yystos[] =
{
       0,    26,    45,     1,    20,    46,     0,    47,    48,    49,
       1,    37,    42,    50,    50,    50,     1,    27,    51,    52,
      65,    54,    54,    54,     1,    43,    52,     1,    37,     1,
      19,    53,    19,    28,    31,    34,    35,    37,    38,    39,
      40,    55,    65,    66,    67,    68,    69,    70,    71,    72,
      73,    74,    75,    21,     1,    37,    36,     3,    41,     1,
      24,     1,    19,    68,     1,    24,     1,    24,     1,    10,
      11,    12,    17,    18,    19,    22,    24,    78,    79,    83,
      85,     1,    24,    19,     1,    19,    21,    67,    19,     1,
      78,    37,     1,    78,     3,     1,    32,     1,    77,    78,
       1,    19,    76,    80,     1,    78,    84,     1,    78,     1,
       4,     5,     6,     7,     8,     9,    13,    14,    15,    16,
      17,    37,    86,     1,    78,     1,    78,    37,    56,    58,
      59,    61,     1,    37,     1,    25,    78,     1,    78,     1,
      25,    36,     1,    25,    36,    24,    81,     1,    23,    36,
      25,     1,    78,     1,    78,     1,    25,    24,    63,     1,
      63,    63,     1,    29,    32,     1,    33,     1,    37,    78,
       1,    37,    19,    78,    82,    78,     1,    14,     1,    46,
      25,    64,    65,    57,    60,    62,     1,    46,    78,     1,
       1,    25,    36,    78,     1,    25,    36,     1,    19,    46,
       1,    46,    30,    33,    78,    65,    37,     1,     1,    46,
      46,    19
};

  /* YYR1[YYN] -- Symbol number of symbol that rule YYN derives.  */
static const yytype_int8 yyr1[] =
{
       0,    44,    45,    45,    45,    47,    46,    48,    46,    49,
      46,    50,    50,    50,    50,    51,    51,    51,    52,    52,
      53,    53,    54,    54,    56,    57,    55,    55,    58,    55,
      59,    60,    55,    61,    62,    55,    63,    63,    63,    64,
      64,    64,    65,    66,    66,    67,    67,    67,    67,    67,
      67,    67,    67,    67,    68,    68,    68,    69,    69,    69,
      69,    69,    69,    69,    69,    70,    70,    70,    70,    70,
      71,    71,    71,    71,    71,    72,    72,    72,    72,    72,
      73,    73,    73,    74,    74,    74,    74,    74,    74,    75,
      75,    76,    76,    77,    77,    78,    78,    78,    78,    78,
      78,    78,    78,    78,    78,    78,    78,    78,    80,    79,
      81,    81,    82,    82,    82,    83,    83,    83,    84,    84,
      85,    85,    85,    85,    86,    86,    86,    86,    86,    86,
      86,    86,    86,    86
};

  /* YYR2[YYN] -- Number of symbols on the right hand side of rule YYN.  */
static const yytype_int8 yyr2[] =
{
       0,     2,     3,     2,     3,     0,     6,     0,     5,     0,
       5,     3,     0,     2,     3,     3,     2,     2,     2,     2,
       3,     1,     2,     0,     0,     0,     7,     2,     0,     4,
       0,     0,     6,     0,     0,     7,     2,     3,     3,     2,
       4,     2,     1,     2,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     4,     3,     4,     8,     6,     2,
       3,     4,     5,     6,     8,     5,     2,     3,     4,     5,
       5,     2,     3,     4,     5,     5,     2,     3,     4,     5,
       3,     2,     3,     8,     2,     3,     4,     5,     6,     3,
       3,     3,     1,     3,     1,     3,     2,     3,     5,     1,
       1,     1,     1,     2,     2,     3,     3,     4,     0,     3,
       3,     3,     1,     3,     0,     3,     2,     3,     3,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     1
};


#define yyerrok         (yyerrstatus = 0)
#define yyclearin       (yychar = YYEMPTY)
#define YYEMPTY         (-2)
#define YYEOF           0

#define YYACCEPT        goto yyacceptlab
#define YYABORT         goto yyabortlab
#define YYERROR         goto yyerrorlab


#define YYRECOVERING()  (!!yyerrstatus)

#define YYBACKUP(Token, Value)                                    \
  do                                                              \
    if (yychar == YYEMPTY)                                        \
      {                                                           \
        yychar = (Token);                                         \
        yylval = (Value);                                         \
        YYPOPSTACK (yylen);                                       \
        yystate = *yyssp;                                         \
        goto yybackup;                                            \
      }                                                           \
    else                                                          \
      {                                                           \
        yyerror (YY_("syntax error: cannot back up")); \
        YYERROR;                                                  \
      }                                                           \
  while (0)

/* Error token number */
#define YYTERROR        1
#define YYERRCODE       256



/* Enable debugging if requested.  */
#if YYDEBUG

# ifndef YYFPRINTF
#  include <stdio.h> /* INFRINGES ON USER NAME SPACE */
#  define YYFPRINTF fprintf
# endif

# define YYDPRINTF(Args)                        \
do {                                            \
  if (yydebug)                                  \
    YYFPRINTF Args;                             \
} while (0)

/* This macro is provided for backward compatibility. */
#ifndef YY_LOCATION_PRINT
# define YY_LOCATION_PRINT(File, Loc) ((void) 0)
#endif


# define YY_SYMBOL_PRINT(Title, Type, Value, Location)                    \
do {                                                                      \
  if (yydebug)                                                            \
    {                                                                     \
      YYFPRINTF (stderr, "%s ", Title);                                   \
      yy_symbol_print (stderr,                                            \
                  Type, Value); \
      YYFPRINTF (stderr, "\n");                                           \
    }                                                                     \
} while (0)


/*-----------------------------------.
| Print this symbol's value on YYO.  |
`-----------------------------------*/

static void
yy_symbol_value_print (FILE *yyo, int yytype, YYSTYPE const * const yyvaluep)
{
  FILE *yyoutput = yyo;
  YYUSE (yyoutput);
  if (!yyvaluep)
    return;
# ifdef YYPRINT
  if (yytype < YYNTOKENS)
    YYPRINT (yyo, yytoknum[yytype], *yyvaluep);
# endif
  YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
  YYUSE (yytype);
  YY_IGNORE_MAYBE_UNINITIALIZED_END
}


/*---------------------------.
| Print this symbol on YYO.  |
`---------------------------*/

static void
yy_symbol_print (FILE *yyo, int yytype, YYSTYPE const * const yyvaluep)
{
  YYFPRINTF (yyo, "%s %s (",
             yytype < YYNTOKENS ? "token" : "nterm", yytname[yytype]);

  yy_symbol_value_print (yyo, yytype, yyvaluep);
  YYFPRINTF (yyo, ")");
}

/*------------------------------------------------------------------.
| yy_stack_print -- Print the state stack from its BOTTOM up to its |
| TOP (included).                                                   |
`------------------------------------------------------------------*/

static void
yy_stack_print (yy_state_t *yybottom, yy_state_t *yytop)
{
  YYFPRINTF (stderr, "Stack now");
  for (; yybottom <= yytop; yybottom++)
    {
      int yybot = *yybottom;
      YYFPRINTF (stderr, " %d", yybot);
    }
  YYFPRINTF (stderr, "\n");
}

# define YY_STACK_PRINT(Bottom, Top)                            \
do {                                                            \
  if (yydebug)                                                  \
    yy_stack_print ((Bottom), (Top));                           \
} while (0)


/*------------------------------------------------.
| Report that the YYRULE is going to be reduced.  |
`------------------------------------------------*/

static void
yy_reduce_print (yy_state_t *yyssp, YYSTYPE *yyvsp, int yyrule)
{
  int yylno = yyrline[yyrule];
  int yynrhs = yyr2[yyrule];
  int yyi;
  YYFPRINTF (stderr, "Reducing stack by rule %d (line %d):\n",
             yyrule - 1, yylno);
  /* The symbols being reduced.  */
  for (yyi = 0; yyi < yynrhs; yyi++)
    {
      YYFPRINTF (stderr, "   $%d = ", yyi + 1);
      yy_symbol_print (stderr,
                       yystos[+yyssp[yyi + 1 - yynrhs]],
                       &yyvsp[(yyi + 1) - (yynrhs)]
                                              );
      YYFPRINTF (stderr, "\n");
    }
}

# define YY_REDUCE_PRINT(Rule)          \
do {                                    \
  if (yydebug)                          \
    yy_reduce_print (yyssp, yyvsp, Rule); \
} while (0)

/* Nonzero means print parse trace.  It is left uninitialized so that
   multiple parsers can coexist.  */
int yydebug;
#else /* !YYDEBUG */
# define YYDPRINTF(Args)
# define YY_SYMBOL_PRINT(Title, Type, Value, Location)
# define YY_STACK_PRINT(Bottom, Top)
# define YY_REDUCE_PRINT(Rule)
#endif /* !YYDEBUG */


/* YYINITDEPTH -- initial size of the parser's stacks.  */
#ifndef YYINITDEPTH
# define YYINITDEPTH 200
#endif

/* YYMAXDEPTH -- maximum size the stacks can grow to (effective only
   if the built-in stack extension method is used).

   Do not make this value too large; the results are undefined if
   YYSTACK_ALLOC_MAXIMUM < YYSTACK_BYTES (YYMAXDEPTH)
   evaluated with infinite-precision integer arithmetic.  */

#ifndef YYMAXDEPTH
# define YYMAXDEPTH 10000
#endif


#if YYERROR_VERBOSE

# ifndef yystrlen
#  if defined __GLIBC__ && defined _STRING_H
#   define yystrlen(S) (YY_CAST (YYPTRDIFF_T, strlen (S)))
#  else
/* Return the length of YYSTR.  */
static YYPTRDIFF_T
yystrlen (const char *yystr)
{
  YYPTRDIFF_T yylen;
  for (yylen = 0; yystr[yylen]; yylen++)
    continue;
  return yylen;
}
#  endif
# endif

# ifndef yystpcpy
#  if defined __GLIBC__ && defined _STRING_H && defined _GNU_SOURCE
#   define yystpcpy stpcpy
#  else
/* Copy YYSRC to YYDEST, returning the address of the terminating '\0' in
   YYDEST.  */
static char *
yystpcpy (char *yydest, const char *yysrc)
{
  char *yyd = yydest;
  const char *yys = yysrc;

  while ((*yyd++ = *yys++) != '\0')
    continue;

  return yyd - 1;
}
#  endif
# endif

# ifndef yytnamerr
/* Copy to YYRES the contents of YYSTR after stripping away unnecessary
   quotes and backslashes, so that it's suitable for yyerror.  The
   heuristic is that double-quoting is unnecessary unless the string
   contains an apostrophe, a comma, or backslash (other than
   backslash-backslash).  YYSTR is taken from yytname.  If YYRES is
   null, do not copy; instead, return the length of what the result
   would have been.  */
static YYPTRDIFF_T
yytnamerr (char *yyres, const char *yystr)
{
  if (*yystr == '"')
    {
      YYPTRDIFF_T yyn = 0;
      char const *yyp = yystr;

      for (;;)
        switch (*++yyp)
          {
          case '\'':
          case ',':
            goto do_not_strip_quotes;

          case '\\':
            if (*++yyp != '\\')
              goto do_not_strip_quotes;
            else
              goto append;

          append:
          default:
            if (yyres)
              yyres[yyn] = *yyp;
            yyn++;
            break;

          case '"':
            if (yyres)
              yyres[yyn] = '\0';
            return yyn;
          }
    do_not_strip_quotes: ;
    }

  if (yyres)
    return yystpcpy (yyres, yystr) - yyres;
  else
    return yystrlen (yystr);
}
# endif

/* Copy into *YYMSG, which is of size *YYMSG_ALLOC, an error message
   about the unexpected token YYTOKEN for the state stack whose top is
   YYSSP.

   Return 0 if *YYMSG was successfully written.  Return 1 if *YYMSG is
   not large enough to hold the message.  In that case, also set
   *YYMSG_ALLOC to the required number of bytes.  Return 2 if the
   required number of bytes is too large to store.  */
static int
yysyntax_error (YYPTRDIFF_T *yymsg_alloc, char **yymsg,
                yy_state_t *yyssp, int yytoken)
{
  enum { YYERROR_VERBOSE_ARGS_MAXIMUM = 5 };
  /* Internationalized format string. */
  const char *yyformat = YY_NULLPTR;
  /* Arguments of yyformat: reported tokens (one for the "unexpected",
     one per "expected"). */
  char const *yyarg[YYERROR_VERBOSE_ARGS_MAXIMUM];
  /* Actual size of YYARG. */
  int yycount = 0;
  /* Cumulated lengths of YYARG.  */
  YYPTRDIFF_T yysize = 0;

  /* There are many possibilities here to consider:
     - If this state is a consistent state with a default action, then
       the only way this function was invoked is if the default action
       is an error action.  In that case, don't check for expected
       tokens because there are none.
     - The only way there can be no lookahead present (in yychar) is if
       this state is a consistent state with a default action.  Thus,
       detecting the absence of a lookahead is sufficient to determine
       that there is no unexpected or expected token to report.  In that
       case, just report a simple "syntax error".
     - Don't assume there isn't a lookahead just because this state is a
       consistent state with a default action.  There might have been a
       previous inconsistent state, consistent state with a non-default
       action, or user semantic action that manipulated yychar.
     - Of course, the expected token list depends on states to have
       correct lookahead information, and it depends on the parser not
       to perform extra reductions after fetching a lookahead from the
       scanner and before detecting a syntax error.  Thus, state merging
       (from LALR or IELR) and default reductions corrupt the expected
       token list.  However, the list is correct for canonical LR with
       one exception: it will still contain any token that will not be
       accepted due to an error action in a later state.
  */
  if (yytoken != YYEMPTY)
    {
      int yyn = yypact[+*yyssp];
      YYPTRDIFF_T yysize0 = yytnamerr (YY_NULLPTR, yytname[yytoken]);
      yysize = yysize0;
      yyarg[yycount++] = yytname[yytoken];
      if (!yypact_value_is_default (yyn))
        {
          /* Start YYX at -YYN if negative to avoid negative indexes in
             YYCHECK.  In other words, skip the first -YYN actions for
             this state because they are default actions.  */
          int yyxbegin = yyn < 0 ? -yyn : 0;
          /* Stay within bounds of both yycheck and yytname.  */
          int yychecklim = YYLAST - yyn + 1;
          int yyxend = yychecklim < YYNTOKENS ? yychecklim : YYNTOKENS;
          int yyx;

          for (yyx = yyxbegin; yyx < yyxend; ++yyx)
            if (yycheck[yyx + yyn] == yyx && yyx != YYTERROR
                && !yytable_value_is_error (yytable[yyx + yyn]))
              {
                if (yycount == YYERROR_VERBOSE_ARGS_MAXIMUM)
                  {
                    yycount = 1;
                    yysize = yysize0;
                    break;
                  }
                yyarg[yycount++] = yytname[yyx];
                {
                  YYPTRDIFF_T yysize1
                    = yysize + yytnamerr (YY_NULLPTR, yytname[yyx]);
                  if (yysize <= yysize1 && yysize1 <= YYSTACK_ALLOC_MAXIMUM)
                    yysize = yysize1;
                  else
                    return 2;
                }
              }
        }
    }

  switch (yycount)
    {
# define YYCASE_(N, S)                      \
      case N:                               \
        yyformat = S;                       \
      break
    default: /* Avoid compiler warnings. */
      YYCASE_(0, YY_("syntax error"));
      YYCASE_(1, YY_("syntax error, unexpected %s"));
      YYCASE_(2, YY_("syntax error, unexpected %s, expecting %s"));
      YYCASE_(3, YY_("syntax error, unexpected %s, expecting %s or %s"));
      YYCASE_(4, YY_("syntax error, unexpected %s, expecting %s or %s or %s"));
      YYCASE_(5, YY_("syntax error, unexpected %s, expecting %s or %s or %s or %s"));
# undef YYCASE_
    }

  {
    /* Don't count the "%s"s in the final size, but reserve room for
       the terminator.  */
    YYPTRDIFF_T yysize1 = yysize + (yystrlen (yyformat) - 2 * yycount) + 1;
    if (yysize <= yysize1 && yysize1 <= YYSTACK_ALLOC_MAXIMUM)
      yysize = yysize1;
    else
      return 2;
  }

  if (*yymsg_alloc < yysize)
    {
      *yymsg_alloc = 2 * yysize;
      if (! (yysize <= *yymsg_alloc
             && *yymsg_alloc <= YYSTACK_ALLOC_MAXIMUM))
        *yymsg_alloc = YYSTACK_ALLOC_MAXIMUM;
      return 1;
    }

  /* Avoid sprintf, as that infringes on the user's name space.
     Don't have undefined behavior even if the translation
     produced a string with the wrong number of "%s"s.  */
  {
    char *yyp = *yymsg;
    int yyi = 0;
    while ((*yyp = *yyformat) != '\0')
      if (*yyp == '%' && yyformat[1] == 's' && yyi < yycount)
        {
          yyp += yytnamerr (yyp, yyarg[yyi++]);
          yyformat += 2;
        }
      else
        {
          ++yyp;
          ++yyformat;
        }
  }
  return 0;
}
#endif /* YYERROR_VERBOSE */

/*-----------------------------------------------.
| Release the memory associated to this symbol.  |
`-----------------------------------------------*/

static void
yydestruct (const char *yymsg, int yytype, YYSTYPE *yyvaluep)
{
  YYUSE (yyvaluep);
  if (!yymsg)
    yymsg = "Deleting";
  YY_SYMBOL_PRINT (yymsg, yytype, yyvaluep, yylocationp);

  YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
  YYUSE (yytype);
  YY_IGNORE_MAYBE_UNINITIALIZED_END
}




/* The lookahead symbol.  */
int yychar;

/* The semantic value of the lookahead symbol.  */
YYSTYPE yylval;
/* Number of syntax errors so far.  */
int yynerrs;


/*----------.
| yyparse.  |
`----------*/

int
yyparse (void)
{
    yy_state_fast_t yystate;
    /* Number of tokens to shift before error messages enabled.  */
    int yyerrstatus;

    /* The stacks and their tools:
       'yyss': related to states.
       'yyvs': related to semantic values.

       Refer to the stacks through separate pointers, to allow yyoverflow
       to reallocate them elsewhere.  */

    /* The state stack.  */
    yy_state_t yyssa[YYINITDEPTH];
    yy_state_t *yyss;
    yy_state_t *yyssp;

    /* The semantic value stack.  */
    YYSTYPE yyvsa[YYINITDEPTH];
    YYSTYPE *yyvs;
    YYSTYPE *yyvsp;

    YYPTRDIFF_T yystacksize;

  int yyn;
  int yyresult;
  /* Lookahead token as an internal (translated) token number.  */
  int yytoken = 0;
  /* The variables used to return semantic value and location from the
     action routines.  */
  YYSTYPE yyval;

#if YYERROR_VERBOSE
  /* Buffer for error messages, and its allocated size.  */
  char yymsgbuf[128];
  char *yymsg = yymsgbuf;
  YYPTRDIFF_T yymsg_alloc = sizeof yymsgbuf;
#endif

#define YYPOPSTACK(N)   (yyvsp -= (N), yyssp -= (N))

  /* The number of symbols on the RHS of the reduced rule.
     Keep to zero when no symbol should be popped.  */
  int yylen = 0;

  yyssp = yyss = yyssa;
  yyvsp = yyvs = yyvsa;
  yystacksize = YYINITDEPTH;

  YYDPRINTF ((stderr, "Starting parse\n"));

  yystate = 0;
  yyerrstatus = 0;
  yynerrs = 0;
  yychar = YYEMPTY; /* Cause a token to be read.  */
  goto yysetstate;


/*------------------------------------------------------------.
| yynewstate -- push a new state, which is found in yystate.  |
`------------------------------------------------------------*/
yynewstate:
  /* In all cases, when you get here, the value and location stacks
     have just been pushed.  So pushing a state here evens the stacks.  */
  yyssp++;


/*--------------------------------------------------------------------.
| yysetstate -- set current state (the top of the stack) to yystate.  |
`--------------------------------------------------------------------*/
yysetstate:
  YYDPRINTF ((stderr, "Entering state %d\n", yystate));
  YY_ASSERT (0 <= yystate && yystate < YYNSTATES);
  YY_IGNORE_USELESS_CAST_BEGIN
  *yyssp = YY_CAST (yy_state_t, yystate);
  YY_IGNORE_USELESS_CAST_END

  if (yyss + yystacksize - 1 <= yyssp)
#if !defined yyoverflow && !defined YYSTACK_RELOCATE
    goto yyexhaustedlab;
#else
    {
      /* Get the current used size of the three stacks, in elements.  */
      YYPTRDIFF_T yysize = yyssp - yyss + 1;

# if defined yyoverflow
      {
        /* Give user a chance to reallocate the stack.  Use copies of
           these so that the &'s don't force the real ones into
           memory.  */
        yy_state_t *yyss1 = yyss;
        YYSTYPE *yyvs1 = yyvs;

        /* Each stack pointer address is followed by the size of the
           data in use in that stack, in bytes.  This used to be a
           conditional around just the two extra args, but that might
           be undefined if yyoverflow is a macro.  */
        yyoverflow (YY_("memory exhausted"),
                    &yyss1, yysize * YYSIZEOF (*yyssp),
                    &yyvs1, yysize * YYSIZEOF (*yyvsp),
                    &yystacksize);
        yyss = yyss1;
        yyvs = yyvs1;
      }
# else /* defined YYSTACK_RELOCATE */
      /* Extend the stack our own way.  */
      if (YYMAXDEPTH <= yystacksize)
        goto yyexhaustedlab;
      yystacksize *= 2;
      if (YYMAXDEPTH < yystacksize)
        yystacksize = YYMAXDEPTH;

      {
        yy_state_t *yyss1 = yyss;
        union yyalloc *yyptr =
          YY_CAST (union yyalloc *,
                   YYSTACK_ALLOC (YY_CAST (YYSIZE_T, YYSTACK_BYTES (yystacksize))));
        if (! yyptr)
          goto yyexhaustedlab;
        YYSTACK_RELOCATE (yyss_alloc, yyss);
        YYSTACK_RELOCATE (yyvs_alloc, yyvs);
# undef YYSTACK_RELOCATE
        if (yyss1 != yyssa)
          YYSTACK_FREE (yyss1);
      }
# endif

      yyssp = yyss + yysize - 1;
      yyvsp = yyvs + yysize - 1;

      YY_IGNORE_USELESS_CAST_BEGIN
      YYDPRINTF ((stderr, "Stack size increased to %ld\n",
                  YY_CAST (long, yystacksize)));
      YY_IGNORE_USELESS_CAST_END

      if (yyss + yystacksize - 1 <= yyssp)
        YYABORT;
    }
#endif /* !defined yyoverflow && !defined YYSTACK_RELOCATE */

  if (yystate == YYFINAL)
    YYACCEPT;

  goto yybackup;


/*-----------.
| yybackup.  |
`-----------*/
yybackup:
  /* Do appropriate processing given the current state.  Read a
     lookahead token if we need one and don't already have one.  */

  /* First try to decide what to do without reference to lookahead token.  */
  yyn = yypact[yystate];
  if (yypact_value_is_default (yyn))
    goto yydefault;

  /* Not known => get a lookahead token if don't already have one.  */

  /* YYCHAR is either YYEMPTY or YYEOF or a valid lookahead symbol.  */
  if (yychar == YYEMPTY)
    {
      YYDPRINTF ((stderr, "Reading a token: "));
      yychar = yylex ();
    }

  if (yychar <= YYEOF)
    {
      yychar = yytoken = YYEOF;
      YYDPRINTF ((stderr, "Now at end of input.\n"));
    }
  else
    {
      yytoken = YYTRANSLATE (yychar);
      YY_SYMBOL_PRINT ("Next token is", yytoken, &yylval, &yylloc);
    }

  /* If the proper action on seeing token YYTOKEN is to reduce or to
     detect an error, take that action.  */
  yyn += yytoken;
  if (yyn < 0 || YYLAST < yyn || yycheck[yyn] != yytoken)
    goto yydefault;
  yyn = yytable[yyn];
  if (yyn <= 0)
    {
      if (yytable_value_is_error (yyn))
        goto yyerrlab;
      yyn = -yyn;
      goto yyreduce;
    }

  /* Count tokens shifted since error; after three, turn off error
     status.  */
  if (yyerrstatus)
    yyerrstatus--;

  /* Shift the lookahead token.  */
  YY_SYMBOL_PRINT ("Shifting", yytoken, &yylval, &yylloc);
  yystate = yyn;
  YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
  *++yyvsp = yylval;
  YY_IGNORE_MAYBE_UNINITIALIZED_END

  /* Discard the shifted token.  */
  yychar = YYEMPTY;
  goto yynewstate;


/*-----------------------------------------------------------.
| yydefault -- do the default action for the current state.  |
`-----------------------------------------------------------*/
yydefault:
  yyn = yydefact[yystate];
  if (yyn == 0)
    goto yyerrlab;
  goto yyreduce;


/*-----------------------------.
| yyreduce -- do a reduction.  |
`-----------------------------*/
yyreduce:
  /* yyn is the number of a rule to reduce with.  */
  yylen = yyr2[yyn];

  /* If YYLEN is nonzero, implement the default value of the action:
     '$$ = $1'.

     Otherwise, the following line sets YYVAL to garbage.
     This behavior is undocumented and Bison
     users should not rely upon it.  Assigning to YYVAL
     unconditionally makes the parser a bit smaller, and it avoids a
     GCC warning that YYVAL may be used uninitialized.  */
  yyval = yyvsp[1-yylen];


  YY_REDUCE_PRINT (yyn);
  switch (yyn)
    {
  case 3:
#line 110 "yacc_P5.y"
                               { yyerrok; explicacion_error_sintactico("Debe incluir un bloque tras el main"); }
#line 1651 "y.tab.c"
    break;

  case 4:
#line 111 "yacc_P5.y"
                                      { yyerrok; explicacion_error_sintactico("El programa debe acabar con \';\'"); }
#line 1657 "y.tab.c"
    break;

  case 5:
#line 112 "yacc_P5.y"
                        {InsertarMarca();}
#line 1663 "y.tab.c"
    break;

  case 6:
#line 116 "yacc_P5.y"
                 {EliminarBloque();}
#line 1669 "y.tab.c"
    break;

  case 7:
#line 117 "yacc_P5.y"
                           {InsertarMarca();}
#line 1675 "y.tab.c"
    break;

  case 8:
#line 120 "yacc_P5.y"
                         {EliminarBloque();}
#line 1681 "y.tab.c"
    break;

  case 9:
#line 124 "yacc_P5.y"
                             {InsertarMarca();}
#line 1687 "y.tab.c"
    break;

  case 10:
#line 124 "yacc_P5.y"
                                                                                                     { yyerrok; explicacion_error_sintactico("El bloque debe acabar con }"); }
#line 1693 "y.tab.c"
    break;

  case 13:
#line 127 "yacc_P5.y"
                                     { yyerrok; explicacion_error_sintactico("Error en el bloque de declaración de variables"); }
#line 1699 "y.tab.c"
    break;

  case 14:
#line 128 "yacc_P5.y"
                                                       { yyerrok; explicacion_error_sintactico("Error, debe cerrarse el bloque de declaración de variables"); }
#line 1705 "y.tab.c"
    break;

  case 17:
#line 131 "yacc_P5.y"
                                                  { yyerrok; explicacion_error_sintactico("Error, la declaración debe acabar en \";\"");}
#line 1711 "y.tab.c"
    break;

  case 19:
#line 133 "yacc_P5.y"
                                      { yyerrok; explicacion_error_sintactico("Error, después del tipo básico va una lista de identificadores"); }
#line 1717 "y.tab.c"
    break;

  case 20:
#line 136 "yacc_P5.y"
                                { if(search_parametros_funcion_declardo(yyvsp[0].nombre)) explicacion_error_semantico("Redeclarando un parámetro de la función");
			else if(search_identificador_marca(yyvsp[0].nombre).entrada == marca) push2(yyvsp[0],variable); else ErrorDeclaradaEnBLoque(yyvsp[0]);
						if(search_parametros_funcion_declardo(yyvsp[0].nombre)) explicacion_error_semantico("Redeclarando un parámetro de la función");
						}
#line 1726 "y.tab.c"
    break;

  case 21:
#line 140 "yacc_P5.y"
                        {if(search_parametros_funcion_declardo(yyvsp[0].nombre)) explicacion_error_semantico("Redeclarando un parámetro de la función");
		else
			if(search_identificador_marca(yyvsp[0].nombre).entrada == marca) push2(yyvsp[0],variable); else ErrorDeclaradaEnBLoque(yyvsp[0]);
		}
#line 1735 "y.tab.c"
    break;

  case 24:
#line 148 "yacc_P5.y"
                                                {funcion_actual++;strcpy(funcion_declarandose[funcion_actual],yyvsp[0].nombre);}
#line 1741 "y.tab.c"
    break;

  case 25:
#line 149 "yacc_P5.y"
                                             {yyvsp[-2].n_parametros=n_parametros;if(search_identificador_marca(yyvsp[-2].nombre).entrada == marca) push2(yyvsp[-2],funcion); else ErrorDeclaradaEnBLoque(yyvsp[-2]); n_parametros=0;}
#line 1747 "y.tab.c"
    break;

  case 26:
#line 151 "yacc_P5.y"
                                      {if(strcmp(funcion_declarandose[funcion_actual],"")!=0) explicacion_error_semantico("No se ha realizado un return de la función");funcion_actual--;}
#line 1753 "y.tab.c"
    break;

  case 27:
#line 152 "yacc_P5.y"
                                      { yyerrok; explicacion_error_sintactico("Error, al declarar una función tras el tipo básico va el identificador"); }
#line 1759 "y.tab.c"
    break;

  case 28:
#line 153 "yacc_P5.y"
                                              {funcion_actual++;strcpy(funcion_declarandose[funcion_actual],yyvsp[0].nombre);}
#line 1765 "y.tab.c"
    break;

  case 29:
#line 153 "yacc_P5.y"
                                                                                                                                 { yyerrok; explicacion_error_sintactico("Error, en una función tras el identificador vienen los parámetros."); }
#line 1771 "y.tab.c"
    break;

  case 30:
#line 154 "yacc_P5.y"
                                              {funcion_actual++;strcpy(funcion_declarandose[funcion_actual],yyvsp[0].nombre);}
#line 1777 "y.tab.c"
    break;

  case 31:
#line 154 "yacc_P5.y"
                                                                                                                                      {yyvsp[-2].n_parametros=n_parametros;if(search_identificador_marca(yyvsp[-2].nombre).entrada == marca) push2(yyvsp[-2],funcion); else ErrorDeclaradaEnBLoque(yyvsp[-2]); n_parametros=0;}
#line 1783 "y.tab.c"
    break;

  case 32:
#line 154 "yacc_P5.y"
                                                                                                                                                                                                                                                                                                          { yyerrok; explicacion_error_sintactico("Error, en una función tras los parámetros viene un bloque."); }
#line 1789 "y.tab.c"
    break;

  case 33:
#line 155 "yacc_P5.y"
                                              {funcion_actual++;strcpy(funcion_declarandose[funcion_actual],yyvsp[0].nombre);}
#line 1795 "y.tab.c"
    break;

  case 34:
#line 155 "yacc_P5.y"
                                                                                                                                      {yyvsp[-2].n_parametros=n_parametros;if(search_identificador_marca(yyvsp[-2].nombre).entrada == marca) push2(yyvsp[-2],funcion); else ErrorDeclaradaEnBLoque(yyvsp[-2]); n_parametros=0;}
#line 1801 "y.tab.c"
    break;

  case 35:
#line 155 "yacc_P5.y"
                                                                                                                                                                                                                                                                                                                 { yyerrok; explicacion_error_sintactico("Error, la declaración de funciones acaba en \";\"."); }
#line 1807 "y.tab.c"
    break;

  case 38:
#line 159 "yacc_P5.y"
                                                { yyerrok; explicacion_error_sintactico("Error, debe cerrarse el paréntesis"); }
#line 1813 "y.tab.c"
    break;

  case 39:
#line 160 "yacc_P5.y"
                                             {n_parametros+=1;if(!search_parametro(yyvsp[0].nombre))push2(yyvsp[0],parametro_formal);}
#line 1819 "y.tab.c"
    break;

  case 40:
#line 161 "yacc_P5.y"
                                                          {n_parametros+=1;push2(yyvsp[0],parametro_formal);}
#line 1825 "y.tab.c"
    break;

  case 41:
#line 162 "yacc_P5.y"
                                    { yyerrok; explicacion_error_sintactico("Error, tras el tipo básico debe introducir un identificador"); }
#line 1831 "y.tab.c"
    break;

  case 42:
#line 163 "yacc_P5.y"
                   { yyval.entrada = yyvsp[0].entrada; }
#line 1837 "y.tab.c"
    break;

  case 54:
#line 176 "yacc_P5.y"
                                                         {comprobar_asignacion(yyvsp[-3],yyvsp[-1]);}
#line 1843 "y.tab.c"
    break;

  case 55:
#line 177 "yacc_P5.y"
                                              { yyerrok; explicacion_error_sintactico("Error, el identificador debe estar igualada a una expresión");}
#line 1849 "y.tab.c"
    break;

  case 56:
#line 178 "yacc_P5.y"
                                                        { yyerrok; explicacion_error_sintactico("Error, la asignación debe acabar en \";\"");}
#line 1855 "y.tab.c"
    break;

  case 57:
#line 179 "yacc_P5.y"
                                                                  { comprueba_exp_logica(yyvsp[-5]); }
#line 1861 "y.tab.c"
    break;

  case 58:
#line 180 "yacc_P5.y"
                                                 { comprueba_exp_logica(yyvsp[-3]); }
#line 1867 "y.tab.c"
    break;

  case 59:
#line 181 "yacc_P5.y"
                             { yyerrok; explicacion_error_sintactico("Error, tras el if debe introducir la condición entre paréntesis"); }
#line 1873 "y.tab.c"
    break;

  case 60:
#line 182 "yacc_P5.y"
                                    { yyerrok; explicacion_error_sintactico("Error, la condición debe ser una expresión"); }
#line 1879 "y.tab.c"
    break;

  case 61:
#line 183 "yacc_P5.y"
                                              { yyerrok; explicacion_error_sintactico("Error, debe cerrar el paréntesis"); }
#line 1885 "y.tab.c"
    break;

  case 62:
#line 184 "yacc_P5.y"
                                                     { yyerrok; explicacion_error_sintactico("Error, se esperaba \"then\""); }
#line 1891 "y.tab.c"
    break;

  case 63:
#line 185 "yacc_P5.y"
                                                          { yyerrok; explicacion_error_sintactico("Error, se esperaba un bloque"); }
#line 1897 "y.tab.c"
    break;

  case 64:
#line 187 "yacc_P5.y"
                                                                      { yyerrok; explicacion_error_sintactico("Error, se esperaba un bloque"); }
#line 1903 "y.tab.c"
    break;

  case 65:
#line 188 "yacc_P5.y"
                                                       { comprueba_exp_logica(yyvsp[-2]); }
#line 1909 "y.tab.c"
    break;

  case 66:
#line 189 "yacc_P5.y"
                                { yyerrok; explicacion_error_sintactico("Error, introduzca la condición entre paréntesis"); }
#line 1915 "y.tab.c"
    break;

  case 67:
#line 190 "yacc_P5.y"
                                       { yyerrok; explicacion_error_sintactico("Error, la condición debe ser una expresión"); }
#line 1921 "y.tab.c"
    break;

  case 68:
#line 191 "yacc_P5.y"
                                                 { yyerrok; explicacion_error_sintactico("Error, debe cerrarse el paréntesis"); }
#line 1927 "y.tab.c"
    break;

  case 69:
#line 192 "yacc_P5.y"
                                                        { yyerrok; explicacion_error_sintactico("Error, se esperaba un bloque"); }
#line 1933 "y.tab.c"
    break;

  case 71:
#line 194 "yacc_P5.y"
                       { yyerrok; explicacion_error_sintactico("Error, se esperaba un paréntesis"); }
#line 1939 "y.tab.c"
    break;

  case 72:
#line 195 "yacc_P5.y"
                                      { yyerrok; explicacion_error_sintactico("Error, debe introducir una lista de identificadores separados por comas"); }
#line 1945 "y.tab.c"
    break;

  case 73:
#line 196 "yacc_P5.y"
                                                    { yyerrok; explicacion_error_sintactico("Error, debe cerrar el paréntesis de la lista de identificadores"); }
#line 1951 "y.tab.c"
    break;

  case 74:
#line 197 "yacc_P5.y"
                                                           { yyerrok; explicacion_error_sintactico("Error, se esperaba \";\""); }
#line 1957 "y.tab.c"
    break;

  case 76:
#line 199 "yacc_P5.y"
                                { yyerrok; explicacion_error_sintactico("Error, se esperaba un paréntesis"); }
#line 1963 "y.tab.c"
    break;

  case 77:
#line 200 "yacc_P5.y"
                                       { yyerrok; explicacion_error_sintactico("Error, debe introducir una lista de expresiones separados por comas"); }
#line 1969 "y.tab.c"
    break;

  case 78:
#line 201 "yacc_P5.y"
                                                    { yyerrok; explicacion_error_sintactico("Error, debe cerrar el paréntesis de la lista de expresiones"); }
#line 1975 "y.tab.c"
    break;

  case 79:
#line 202 "yacc_P5.y"
                                                           { yyerrok; explicacion_error_sintactico("Error, se esperaba \";\""); }
#line 1981 "y.tab.c"
    break;

  case 80:
#line 203 "yacc_P5.y"
                                        {if(funcion_actual>-1){if(strcmp(funcion_declarandose[funcion_actual],"")!=0) if(!igualdad(yyvsp[-1],search_identificador_pila(funcion_declarandose[funcion_actual]))) {explicacion_error_semantico("No se devuelve el tipo de la función");}
			strcpy(funcion_declarandose[funcion_actual],"");}}
#line 1988 "y.tab.c"
    break;

  case 81:
#line 205 "yacc_P5.y"
                                 { yyerrok; explicacion_error_sintactico("Error, debe devolverse una expresión"); }
#line 1994 "y.tab.c"
    break;

  case 82:
#line 206 "yacc_P5.y"
                                           { yyerrok; explicacion_error_sintactico("Error, debe acabar en \";\""); }
#line 2000 "y.tab.c"
    break;

  case 83:
#line 207 "yacc_P5.y"
                                                                                {comprobar_for_pascal(yyvsp[-6],yyvsp[-4],yyvsp[-2]);}
#line 2006 "y.tab.c"
    break;

  case 84:
#line 208 "yacc_P5.y"
                              { yyerrok; explicacion_error_sintactico("Error, se esperaba una sentencia de asignación"); }
#line 2012 "y.tab.c"
    break;

  case 85:
#line 209 "yacc_P5.y"
                                                   { yyerrok; explicacion_error_sintactico("Error, se esperaba la palabra \"to\""); }
#line 2018 "y.tab.c"
    break;

  case 86:
#line 210 "yacc_P5.y"
                                                      { yyerrok; explicacion_error_sintactico("Error, se esperaba una expresión"); }
#line 2024 "y.tab.c"
    break;

  case 87:
#line 211 "yacc_P5.y"
                                                                { yyerrok; explicacion_error_sintactico("Error, se esperaba la palabra \"do\""); }
#line 2030 "y.tab.c"
    break;

  case 88:
#line 212 "yacc_P5.y"
                                                                   { yyerrok; explicacion_error_sintactico("Error, se esperaba un bloque"); }
#line 2036 "y.tab.c"
    break;

  case 89:
#line 213 "yacc_P5.y"
                                             {
				copiaStruct(&yyval,search_identificador_pila(yyvsp[-2].nombre)); 
				if(yyval.entrada!=variable && yyval.entrada!=parametro_formal)
				 	ErrorNoDeclarada(yyvsp[-2]);
				else if(yyval.dato_referencia!=lista)
					explicacion_error_semantico("El tipo de dato deberia ser una lista"); 
					}
#line 2048 "y.tab.c"
    break;

  case 90:
#line 220 "yacc_P5.y"
                                           {
			copiaStruct(&yyval,search_identificador_pila(yyvsp[-1].nombre)); 
			if(yyval.entrada!=variable && yyval.entrada!=parametro_formal) 
				ErrorNoDeclarada(yyvsp[-1]);
			else if(yyval.dato_referencia!=lista) 
			explicacion_error_semantico("El tipo de dato deberia ser una lista"); }
#line 2059 "y.tab.c"
    break;

  case 91:
#line 230 "yacc_P5.y"
                                                 {copiaStruct(&yyval,search_identificador_pila(yyvsp[0].nombre)); if(yyval.entrada!=variable && yyval.entrada!=parametro_formal) {ErrorNoDeclarada(yyvsp[0]);}}
#line 2065 "y.tab.c"
    break;

  case 92:
#line 231 "yacc_P5.y"
                         {copiaStruct(&yyval,search_identificador_pila(yyvsp[0].nombre)); if(yyval.entrada!=variable && yyval.entrada!=parametro_formal) {ErrorNoDeclarada(yyvsp[0]);}}
#line 2071 "y.tab.c"
    break;

  case 93:
#line 233 "yacc_P5.y"
                                           {copiaStruct(&yyval,search_identificador_pila(yyvsp[0].nombre)); if(yyval.entrada!=variable) {ErrorNoDeclarada(yyval); yyval.entrada=indefinido;} }
#line 2077 "y.tab.c"
    break;

  case 94:
#line 234 "yacc_P5.y"
                            {copiaStruct(&yyval,search_identificador_pila(yyvsp[0].nombre)); if(yyval.entrada!=variable) {ErrorNoDeclarada(yyval); yyval.entrada=indefinido;}}
#line 2083 "y.tab.c"
    break;

  case 95:
#line 236 "yacc_P5.y"
                                    { copiaStruct(&yyval, yyvsp[-1]); }
#line 2089 "y.tab.c"
    break;

  case 96:
#line 237 "yacc_P5.y"
                                            { copiaStruct(&yyval, operador_unario(yyvsp[0],yyvsp[-1]) ); }
#line 2095 "y.tab.c"
    break;

  case 97:
#line 238 "yacc_P5.y"
                                                          {copiaStruct(&yyval,operador_binario(yyvsp[-1], yyvsp[-2], yyvsp[0]));  }
#line 2101 "y.tab.c"
    break;

  case 98:
#line 239 "yacc_P5.y"
                                                            { copiaStruct(&yyval,operador_ternario(yyvsp[-4],yyvsp[-2],yyvsp[0])); }
#line 2107 "y.tab.c"
    break;

  case 99:
#line 240 "yacc_P5.y"
                                {copiaStruct(&yyval,search_identificador_pila(yyvsp[0].nombre)); if(yyval.entrada!=variable && yyval.entrada!=parametro_formal) ErrorNoDeclarada(yyvsp[0]);else yyval.entrada =indefinido;}
#line 2113 "y.tab.c"
    break;

  case 100:
#line 241 "yacc_P5.y"
                                 {copiaStruct(&yyval,yyvsp[0]); yyval.entrada=variable;}
#line 2119 "y.tab.c"
    break;

  case 101:
#line 242 "yacc_P5.y"
                           {yyval.dato_referencia=lista;yyval.dato_lista=yyvsp[0].dato_referencia;yyval.entrada=variable;}
#line 2125 "y.tab.c"
    break;

  case 102:
#line 243 "yacc_P5.y"
                          {copiaStruct(&yyval,yyvsp[0]);}
#line 2131 "y.tab.c"
    break;

  case 103:
#line 244 "yacc_P5.y"
                               { yyerrok; explicacion_error_sintactico("Error, se esperaba una expresión"); }
#line 2137 "y.tab.c"
    break;

  case 104:
#line 246 "yacc_P5.y"
                              { yyerrok; explicacion_error_sintactico("Error, se esperaba una expresión"); }
#line 2143 "y.tab.c"
    break;

  case 105:
#line 247 "yacc_P5.y"
                                        { yyerrok; explicacion_error_sintactico("Error, se esperaba una expresión"); }
#line 2149 "y.tab.c"
    break;

  case 106:
#line 248 "yacc_P5.y"
                                       { yyerrok; explicacion_error_sintactico("Error, se esperaba una expresión"); }
#line 2155 "y.tab.c"
    break;

  case 107:
#line 249 "yacc_P5.y"
                                                 { yyerrok; explicacion_error_sintactico("Error, se esperaba \"@\""); }
#line 2161 "y.tab.c"
    break;

  case 108:
#line 251 "yacc_P5.y"
                               {funcion_analizando++;strcpy(funcion_usandose[funcion_analizando],yyvsp[0].nombre);}
#line 2167 "y.tab.c"
    break;

  case 109:
#line 253 "yacc_P5.y"
                {copiaStruct(&yyval,search_identificador_pila(yyvsp[-2].nombre));
		if(yyval.entrada!=funcion) {ErrorNoDeclarada(yyvsp[-2]);yyval.entrada =indefinido;} 
		if(n_parametros!=yyval.n_parametros) explicacion_error_semantico("Numero de argumentos incorrecto"); n_parametros=0;}
#line 2175 "y.tab.c"
    break;

  case 110:
#line 257 "yacc_P5.y"
                                            {funcion_analizando--;}
#line 2181 "y.tab.c"
    break;

  case 111:
#line 258 "yacc_P5.y"
                                                { yyerrok; explicacion_error_sintactico("Error, la lista de argumentos debe acabar con paréntesis"); }
#line 2187 "y.tab.c"
    break;

  case 112:
#line 259 "yacc_P5.y"
                             {n_parametros+=1; struct entradaTS s2;
		copiaStruct(&s2,getArg(funcion_usandose[funcion_analizando],n_parametros));
		if(!igualdad(yyvsp[0],s2)) explicacion_error_semantico("No coinciden los tipos");
		}
#line 2196 "y.tab.c"
    break;

  case 113:
#line 264 "yacc_P5.y"
                                          {n_parametros+=1; struct entradaTS s2; 	
		
		copiaStruct(&s2,getArg(funcion_usandose[funcion_analizando],n_parametros));
		if(!igualdad(yyvsp[0],s2)) explicacion_error_semantico("No coinciden los tipos");
		}
#line 2206 "y.tab.c"
    break;

  case 115:
#line 270 "yacc_P5.y"
                                           {yyval.dato_referencia=lista;yyval.dato_lista=daton_anterior;daton_anterior=desconocido;}
#line 2212 "y.tab.c"
    break;

  case 116:
#line 271 "yacc_P5.y"
                                 {yyerrok; explicacion_error_sintactico("Error, debe proporcionar una lista de expresiones separadas por comas");}
#line 2218 "y.tab.c"
    break;

  case 117:
#line 272 "yacc_P5.y"
                                                   { yyerrok; explicacion_error_sintactico("Error, debe cerrar el corchete"); }
#line 2224 "y.tab.c"
    break;

  case 118:
#line 274 "yacc_P5.y"
                                                     {if (daton_anterior==desconocido) daton_anterior=yyvsp[0].dato_referencia;
		if(daton_anterior!=yyvsp[0].dato_referencia) explicacion_error_semantico("Todos los elementos de una lista deben de ser del mismo tipo");
		if(daton_anterior==lista) explicacion_error_semantico("No se puede hacer listas de listas"); 
		yyval.dato_referencia=daton_anterior;}
#line 2233 "y.tab.c"
    break;

  case 119:
#line 278 "yacc_P5.y"
                    {if (daton_anterior==desconocido) daton_anterior=yyvsp[0].dato_referencia;
		if(daton_anterior!=yyvsp[0].dato_referencia) explicacion_error_semantico("Todos los elementos de una lista deben de ser del mismo tipo");
		if(daton_anterior==lista) explicacion_error_semantico("No se puede hacer listas de listas");
		yyval.dato_referencia=daton_anterior;}
#line 2242 "y.tab.c"
    break;

  case 120:
#line 283 "yacc_P5.y"
            { copiaStruct(&yyval,yyvsp[0]);}
#line 2248 "y.tab.c"
    break;

  case 121:
#line 284 "yacc_P5.y"
                    { copiaStruct(&yyval,yyvsp[0]);}
#line 2254 "y.tab.c"
    break;

  case 122:
#line 285 "yacc_P5.y"
                        { copiaStruct(&yyval,yyvsp[0]);}
#line 2260 "y.tab.c"
    break;

  case 123:
#line 286 "yacc_P5.y"
                { copiaStruct(&yyval,yyvsp[0]);}
#line 2266 "y.tab.c"
    break;

  case 124:
#line 287 "yacc_P5.y"
                 { copiaStruct(&yyval,yyvsp[0]);}
#line 2272 "y.tab.c"
    break;

  case 125:
#line 288 "yacc_P5.y"
                { copiaStruct(&yyval,yyvsp[0]);}
#line 2278 "y.tab.c"
    break;

  case 126:
#line 289 "yacc_P5.y"
                  { copiaStruct(&yyval,yyvsp[0]);}
#line 2284 "y.tab.c"
    break;

  case 127:
#line 290 "yacc_P5.y"
              { copiaStruct(&yyval,yyvsp[0]);}
#line 2290 "y.tab.c"
    break;

  case 128:
#line 291 "yacc_P5.y"
                         { copiaStruct(&yyval,yyvsp[0]);}
#line 2296 "y.tab.c"
    break;

  case 129:
#line 292 "yacc_P5.y"
                      { copiaStruct(&yyval,yyvsp[0]);}
#line 2302 "y.tab.c"
    break;

  case 130:
#line 293 "yacc_P5.y"
                 { copiaStruct(&yyval,yyvsp[0]);}
#line 2308 "y.tab.c"
    break;

  case 131:
#line 294 "yacc_P5.y"
                     { copiaStruct(&yyval,yyvsp[0]);}
#line 2314 "y.tab.c"
    break;

  case 132:
#line 295 "yacc_P5.y"
                     { copiaStruct(&yyval,yyvsp[0]);}
#line 2320 "y.tab.c"
    break;

  case 133:
#line 296 "yacc_P5.y"
                   { copiaStruct(&yyval,yyvsp[0]);}
#line 2326 "y.tab.c"
    break;


#line 2330 "y.tab.c"

      default: break;
    }
  /* User semantic actions sometimes alter yychar, and that requires
     that yytoken be updated with the new translation.  We take the
     approach of translating immediately before every use of yytoken.
     One alternative is translating here after every semantic action,
     but that translation would be missed if the semantic action invokes
     YYABORT, YYACCEPT, or YYERROR immediately after altering yychar or
     if it invokes YYBACKUP.  In the case of YYABORT or YYACCEPT, an
     incorrect destructor might then be invoked immediately.  In the
     case of YYERROR or YYBACKUP, subsequent parser actions might lead
     to an incorrect destructor call or verbose syntax error message
     before the lookahead is translated.  */
  YY_SYMBOL_PRINT ("-> $$ =", yyr1[yyn], &yyval, &yyloc);

  YYPOPSTACK (yylen);
  yylen = 0;
  YY_STACK_PRINT (yyss, yyssp);

  *++yyvsp = yyval;

  /* Now 'shift' the result of the reduction.  Determine what state
     that goes to, based on the state we popped back to and the rule
     number reduced by.  */
  {
    const int yylhs = yyr1[yyn] - YYNTOKENS;
    const int yyi = yypgoto[yylhs] + *yyssp;
    yystate = (0 <= yyi && yyi <= YYLAST && yycheck[yyi] == *yyssp
               ? yytable[yyi]
               : yydefgoto[yylhs]);
  }

  goto yynewstate;


/*--------------------------------------.
| yyerrlab -- here on detecting error.  |
`--------------------------------------*/
yyerrlab:
  /* Make sure we have latest lookahead translation.  See comments at
     user semantic actions for why this is necessary.  */
  yytoken = yychar == YYEMPTY ? YYEMPTY : YYTRANSLATE (yychar);

  /* If not already recovering from an error, report this error.  */
  if (!yyerrstatus)
    {
      ++yynerrs;
#if ! YYERROR_VERBOSE
      yyerror (YY_("syntax error"));
#else
# define YYSYNTAX_ERROR yysyntax_error (&yymsg_alloc, &yymsg, \
                                        yyssp, yytoken)
      {
        char const *yymsgp = YY_("syntax error");
        int yysyntax_error_status;
        yysyntax_error_status = YYSYNTAX_ERROR;
        if (yysyntax_error_status == 0)
          yymsgp = yymsg;
        else if (yysyntax_error_status == 1)
          {
            if (yymsg != yymsgbuf)
              YYSTACK_FREE (yymsg);
            yymsg = YY_CAST (char *, YYSTACK_ALLOC (YY_CAST (YYSIZE_T, yymsg_alloc)));
            if (!yymsg)
              {
                yymsg = yymsgbuf;
                yymsg_alloc = sizeof yymsgbuf;
                yysyntax_error_status = 2;
              }
            else
              {
                yysyntax_error_status = YYSYNTAX_ERROR;
                yymsgp = yymsg;
              }
          }
        yyerror (yymsgp);
        if (yysyntax_error_status == 2)
          goto yyexhaustedlab;
      }
# undef YYSYNTAX_ERROR
#endif
    }



  if (yyerrstatus == 3)
    {
      /* If just tried and failed to reuse lookahead token after an
         error, discard it.  */

      if (yychar <= YYEOF)
        {
          /* Return failure if at end of input.  */
          if (yychar == YYEOF)
            YYABORT;
        }
      else
        {
          yydestruct ("Error: discarding",
                      yytoken, &yylval);
          yychar = YYEMPTY;
        }
    }

  /* Else will try to reuse lookahead token after shifting the error
     token.  */
  goto yyerrlab1;


/*---------------------------------------------------.
| yyerrorlab -- error raised explicitly by YYERROR.  |
`---------------------------------------------------*/
yyerrorlab:
  /* Pacify compilers when the user code never invokes YYERROR and the
     label yyerrorlab therefore never appears in user code.  */
  if (0)
    YYERROR;

  /* Do not reclaim the symbols of the rule whose action triggered
     this YYERROR.  */
  YYPOPSTACK (yylen);
  yylen = 0;
  YY_STACK_PRINT (yyss, yyssp);
  yystate = *yyssp;
  goto yyerrlab1;


/*-------------------------------------------------------------.
| yyerrlab1 -- common code for both syntax error and YYERROR.  |
`-------------------------------------------------------------*/
yyerrlab1:
  yyerrstatus = 3;      /* Each real token shifted decrements this.  */

  for (;;)
    {
      yyn = yypact[yystate];
      if (!yypact_value_is_default (yyn))
        {
          yyn += YYTERROR;
          if (0 <= yyn && yyn <= YYLAST && yycheck[yyn] == YYTERROR)
            {
              yyn = yytable[yyn];
              if (0 < yyn)
                break;
            }
        }

      /* Pop the current state because it cannot handle the error token.  */
      if (yyssp == yyss)
        YYABORT;


      yydestruct ("Error: popping",
                  yystos[yystate], yyvsp);
      YYPOPSTACK (1);
      yystate = *yyssp;
      YY_STACK_PRINT (yyss, yyssp);
    }

  YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
  *++yyvsp = yylval;
  YY_IGNORE_MAYBE_UNINITIALIZED_END


  /* Shift the error token.  */
  YY_SYMBOL_PRINT ("Shifting", yystos[yyn], yyvsp, yylsp);

  yystate = yyn;
  goto yynewstate;


/*-------------------------------------.
| yyacceptlab -- YYACCEPT comes here.  |
`-------------------------------------*/
yyacceptlab:
  yyresult = 0;
  goto yyreturn;


/*-----------------------------------.
| yyabortlab -- YYABORT comes here.  |
`-----------------------------------*/
yyabortlab:
  yyresult = 1;
  goto yyreturn;


#if !defined yyoverflow || YYERROR_VERBOSE
/*-------------------------------------------------.
| yyexhaustedlab -- memory exhaustion comes here.  |
`-------------------------------------------------*/
yyexhaustedlab:
  yyerror (YY_("memory exhausted"));
  yyresult = 2;
  /* Fall through.  */
#endif


/*-----------------------------------------------------.
| yyreturn -- parsing is finished, return the result.  |
`-----------------------------------------------------*/
yyreturn:
  if (yychar != YYEMPTY)
    {
      /* Make sure we have latest lookahead translation.  See comments at
         user semantic actions for why this is necessary.  */
      yytoken = YYTRANSLATE (yychar);
      yydestruct ("Cleanup: discarding lookahead",
                  yytoken, &yylval);
    }
  /* Do not reclaim the symbols of the rule whose action triggered
     this YYABORT or YYACCEPT.  */
  YYPOPSTACK (yylen);
  YY_STACK_PRINT (yyss, yyssp);
  while (yyssp != yyss)
    {
      yydestruct ("Cleanup: popping",
                  yystos[+*yyssp], yyvsp);
      YYPOPSTACK (1);
    }
#ifndef yyoverflow
  if (yyss != yyssa)
    YYSTACK_FREE (yyss);
#endif
#if YYERROR_VERBOSE
  if (yymsg != yymsgbuf)
    YYSTACK_FREE (yymsg);
#endif
  return yyresult;
}
#line 297 "yacc_P5.y"
 


int main (int argc, char** argv) {
    // Se comprueba que se recibe 1 argumento (nombre del fichero fuente)
	if (argc <= 1) {
        printf("\nError al ejecutar la aplicación...\n");
        printf("Uso: %s nombre_fichero_fuente\n", argv[0]);
		exit(-1);
	}
    // Se abre el fichero recibido por parámetro
    yyin = fopen(argv[1], "r");
    // Si "yyin" es nulo no se ha podido abrir el fichero
    if (yyin == NULL) {
        printf ("\nError al abrir el fichero %s\n", argv[1]);
        exit (-2);
    }
    yyparse();
}

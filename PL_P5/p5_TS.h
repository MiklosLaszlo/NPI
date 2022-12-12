#ifndef P5TS
#define P5TS
	typedef enum {marca,funcion,variable,parametro_formal,indefinido,operador, control} TipoEntrada;
	typedef enum {booleano,entero,real,caracter,lista,desconocido} TipoDato;
	typedef enum {
		negacion,sostenido,interrogacion,menos,
		equal, not_equal, and, or, xor,
		masmas, mas, entre, por, menosmenos, porcentaje, doblepor,
		less, greater, less_eq, greater_eq,arroba} TipoOperador;

	typedef struct InstruccionControl{
		char Entrada[20];	// Etiqueta a la entrada.
		char Salida[20];	// Etiqueta a la salida.
		char Else[20];		// Etiqueta al else.
		char NombreVar[20];	// Nombre de la var de control.
	};

	typedef struct entradaTS{
		TipoEntrada entrada;      // Indica el tipo de entrada
		char nombre[100];                 // Contendra los caracteres que forman el identificador en nuestro lenguaje
		char nombre_traductor[100];       // Contendra los caracteres que forman el identificador al traducirlo a C
		TipoDato dato_referencia; // En caso de que entrada sea funcion,variable
										// o parametro formal indica el tipo de dato al que hace referencia
		TipoDato dato_lista;      //tipo de datos que contiene la lista                    
		unsigned int n_parametros;  //Si tipoDato  es funcion indica el numero de parametros o el tamaño de la lista
		unsigned int puntero_lista; // Puntero que señala la posición en la lista
		TipoOperador tipo_operador; // En caso de ser operador, qué operador es  
		struct InstruccionControl descriptor_control; // Si lo que tenemos es una instrucción de control, su descriptor.
	};

	const struct entradaTS initialize = {
		.entrada = indefinido, .dato_referencia=desconocido, .dato_lista=desconocido, .n_parametros=0, .puntero_lista=0,
		.descriptor_control = {.Entrada="", .Else="", .Salida="", .NombreVar=""}
		};

	#define YYSTYPE struct entradaTS
	
#endif
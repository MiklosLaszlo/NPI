/*var HORARIO =
	[
		{
			nombre: ["calculo", "cea"],
			dias: ["martes"],
			horario: "de nueve a once"
		},
		{
			nombre: ["fundamentos de programacion", "fft"],
			dias: ["martes", "miercoles"],
			horario: "de ocho a nueve"
		},
		{
			nombre: ["algebra", "algebra lineal y estructuras matematicas", "alem"],
			dias: ["miercoles", "viernes"],
			horario: "de diez a once"
		},
		{
			nombre: ["fundamentos de sofwtare", "efe ese"],
			dias: ["lunes", "martes"],
			horario: "de nueve a diez"
		},
		{
			nombre: ["fundamentos fisicos y tencologicos", "efe efe te"],
			dias: ["martes", "jueves"],
			horario: "de nueve a diez"
		}
	];*/

function busca_horario(asignatura, eng) {
	var salir = false;
	var index = 0;
	var i = 0;
	var j = 0;
	for (i = 0; i < HORARIO.length && !salir; i++) {
		salir = false;
		for (j = 0; j < HORARIO[i].nombre.length && !salir; j++) {
			salir = HORARIO[i].nombre[j] == asignatura;
		}
		if (salir) index = i;
	}

	var l = HORARIO[index].dias.length;
	var salida_dias = "";
	if (l > 1) {
		for (i = 0; i < l; i++) {
			salida_dias += HORARIO[index].dias[i];
			if (i < l - 2)
				salida_dias += ", ";
			else if (i == l - 2)
				salida_dias += eng ? " and " : " y ";
		}
	}
	else
		salida_dias = HORARIO[index].dias[0];

	var salida_horas = HORARIO[index].horario;
	return { dias: salida_dias, horas: salida_horas };
}
/*
function busca_horario(asignatura) {
	var horario = [{
		nombre: ["calculo", "cea"],
		dias: ["martes"],
		horario: "de nueve a once"
	}]

	var salida_dias = horario[0].dias[0];
	var salida_horas = horario[0].horario;
	return { dias: salida_dias, horas: salida_horas };
}*/

console.log(busca_horario('algebra'));

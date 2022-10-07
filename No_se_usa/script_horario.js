function busca_horario(asignatura) {
	/*var horario = [{
		nombre: ["calculo", "cea"],
		dias: ["martes"],
		horario: "de nueve a once"
	}]
	var salir = false;
	var index = 0;
	var i = 0;
	for (i = 0; i < horario.length && !salir; i++) {
		salir = (horario[i].nombre.includes(asignatura))
		if (salir) index = i;
	}
	var l = horario[index].dias.length;
	var salida_dias = "";
	if (l > 1) {
		for (i = 0; i < l; i++) {
			salida_dias += horario[index].dias[i];
			if (i < l - 1)
				salida_dias += ", ";
			else if (i == l - 1)
				salida_dias += " y ";
		}
	}
	else
		salida_dias = horario[index].dias[0];

	var salida_horas = horario[index].horario;
	return { dias: salida_dias, horas: salida_horas };*/
	return { dias: "lunes y martes", horas: "de doce a una" };
}
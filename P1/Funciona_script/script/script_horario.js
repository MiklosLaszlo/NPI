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
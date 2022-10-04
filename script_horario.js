function busca_horario(asignatura) {
	horario = require('./horarios.json')
	let salir = false;
	let index = 0;
	for (let i = 0; i < horario.length && !salir; i++) {
		salir = (horario[i].nombre.includes(asignatura))
		if (salir) index = i;
	}
	l = horario[index].dias.length;
	var salida_dias = ""
	if (l > 1) {
		for (let i = 0; i < l; i++) {
			dias += horario[index].dias[i];
			if (i < l - 1)
				dias += ", ";
			else if (i == l - 1)
				dias += " y ";
		}
	}

	var salida_horas = horario[index].horario

	return { dias: salida_dias, horas: salida_horas }
}
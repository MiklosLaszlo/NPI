
function greetings(eng) {

	var f = new Date();
	var horas = f.getHours();
	var manana = horas > 6 && horas < 13;
	var tarde = horas > 13 && horas <= 19;
	var noche = horas <= 6 && horas > 19;

	var salida = "";

	if (manana)
		salida = eng ? "Good morning" : "Buenos dias";
	if (tarde)
		salida = eng ? "Good afternoon" : "Buenas tardes";
	if (noche)
		salida = eng ? "Good evening" : "Buenas noches";

	return salida;
}

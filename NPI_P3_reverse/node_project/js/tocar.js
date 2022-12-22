function tocar(){
	var titulo = document.getElementById("titulo");
	console.log("Tocado");
	titulo.innerHTML = "algo";
}

document.getElementById("boton").onclick = tocar
// document.getElementById("boton").onclick = () => {tocar();}
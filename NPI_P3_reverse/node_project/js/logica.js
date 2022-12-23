function contarDedos(hand){
	var contador = 0;
	console.log(hand.pointables.length)
	if (hand) {
		for (var i = 0; i < hand.pointables.length; i++){
			var dedo = hand.pointables[i];
			if(dedo.extended) contador++;
		}
	}
	//console.log("contando " + contador);
	return contador;
}

function buscaDedo(dedos, tipo){
	var dedo = null;
	var salir = false;
	for(var i = 0; i < dedos.length && !salir; i++){
	  salir = dedos[i].type == tipo;
	  if(salir) 
		 dedo = dedos[i]
	}
	return dedo;
 }

var changeMenuButton = document.querySelector("#changeMenuButton");

changeMenuButton.addEventListener("click", () => {desplazaMenu(false);});

// Cambiar dia de horarios
var changeDayButton = document.querySelector("#changeDayButton");
var dayCell = document.querySelector("#Dia");
var subjectCells = document.querySelectorAll("#subject");
var clasesCells = document.querySelectorAll("#clas");
var columHorasHorarios = document.querySelectorAll("#horasHorariosTabla");
var primera1filaHorarios = document.querySelectorAll("#fila1Horarios");


columHorasHorarios.forEach((cell, index) => {
	cell.style.backgroundColor = "rgba(0, 255, 255, 0.562)";
});

primera1filaHorarios.forEach((cell, index) => {
	cell.style.backgroundColor = "#009879";
});

var days = ["Lunes", "Martes", "Miercoles", "Jueves", "Viernes"];
var currentDayIndex = 0;

var subjects = [
	[" ","SS", "SS", "TIC", "TIC"," "], // Lunes
	[" ","TIC", "TIC", "PTC","PTC", " "], // Martes
	[" ","SS", "SS", "PTC","PTC"," "], // Miercoles
	["VC", "VC", "VC(PRACTICAS)","VC(PRACTICAS)","PL","PL"], // Jueves
	["NPI(PRACTICAS)", "NPI(PRACTICAS)", "NPI","NPI","PL(PRACTICAS)","PL(PRACTICAS)"]  // Viernes
];

var clases = [
[" ","1.1", "1.1","2.2" ,"2.2"," "],//Lunes
[" ","1.1", "1.1","2.2" ,"2.2"," "],//Martes
[" ","3.7", "3.7","1.5" ,"1.5",""],//Miercoles
["1.2","1.2", "3.7","3.7" ,"1.2","1.2"],//Jueves
["3.3","3.3", "1.2","1.2" ,"3.9","3.9"] // Viernes

];

function updateTimetable() {
	dayCell.textContent = days[currentDayIndex];
	subjectCells.forEach((cell, index) => {
	cell.textContent = subjects[currentDayIndex][index];
	cell.style.backgroundColor = "rgba(0, 255, 255, 0.562)";
	});

	dayCell.textContent = days[currentDayIndex];
	clasesCells.forEach((cell, index) => {
	cell.textContent = clases[currentDayIndex][index];
	cell.style.backgroundColor = "rgba(0, 255, 255, 0.562)";
	});
}

// Cambiar dia de comedor
var diaActualComedor = 0;
var comidaPrimero1 = ["Raviolis a la carbonara","Olla gitana tradicional","Arroz mar y montaña","Estofado de lentejas","Sopa minestrone"];
var comidaSegundo1 = ["Escalope de cerdo","Muslo de pollo en escabeche","Encebollado de bacalao","Lomo braseado","Huevos rotos con bacalao y garrapiñadas"];
var comidaPostre1 = ["Naranja","Plátano","Piña","Mandarinas","Tomate asado"];
var comidaPrimero2 = ["Macarrones Florentina (OV)",,"Olla gitana","Arroz con hortalizas al curry","Estofado de lentejas","Sopa minestrone"];
var comidaSegundo2 = ["Tortilla de calabacín","Tofu villeroy","Milhojas de verduras gratinadas","Tajín de verduras","Huevos rotos con setas"];
var comidaPostre2 = ["Naranja","Plátano","Piña","Mandarinas","Tomate asado"];
var algunMenuSeleccionado = false;
var dayComida = document.querySelector("#DiaComedor");
var comedorprimero1 = document.querySelector("#primero1");
var comedorsegundo1 = document.querySelector("#segundo1");
var comedorpostre1 = document.querySelector("#postre1");
var comedorprimero2 = document.querySelector("#primero2");
var comedorsegundo2 = document.querySelector("#segundo2");
var comedorpostre2 = document.querySelector("#postre2");
var snackbar = document.getElementById("snackbar");
snackbar.style.display = "none"

var selectMenuButton = document.getElementById("selecionando");

var rowMenu1 = document.getElementById("menu1");
var rowPrimero1 = document.getElementById("menuprimero1");
var rowSegundo1 = document.getElementById("menusegundo1");
var rowPostre1 = document.getElementById("menupostre1");

var rowMenu2 = document.getElementById("menu2");
var rowPrimero2 = document.getElementById("menuprimero2");
var rowSegundo2 = document.getElementById("menusegundo2");
var rowPostre2 = document.getElementById("menupostre2");

function unselect(){

	rowMenu1.style.backgroundColor = "#009879";
	rowPrimero1.style.backgroundColor = "rgba(0, 255, 255, 0.562)";
	rowSegundo1.style.backgroundColor = "rgba(0, 255, 255, 0.562)";
	rowPostre1.style.backgroundColor = "rgba(0, 255, 255, 0.562)";

	rowMenu2.style.backgroundColor = "#009879";
	rowPrimero2.style.backgroundColor = "rgba(0, 255, 255, 0.562)";
	rowSegundo2.style.backgroundColor = "rgba(0, 255, 255, 0.562)";
	rowPostre2.style.backgroundColor = "rgba(0, 255, 255, 0.562)";

	algunMenuSeleccionado = false;
}

function selectMenu1(){
	unselect();
	rowMenu1.style.backgroundColor = "#982b00";
	rowPrimero1.style.backgroundColor = "rgba(216, 82, 21,0.562)";
	rowSegundo1.style.backgroundColor = "rgba(216, 82, 21,0.562)";
	rowPostre1.style.backgroundColor = "rgba(216, 82, 21,0.562)";
	algunMenuSeleccionado = true;
}

function selectMenu2(){
	unselect();
	rowMenu2.style.backgroundColor = "#982b00";
	rowPrimero2.style.backgroundColor = "rgba(216, 82, 21,0.562)";
	rowSegundo2.style.backgroundColor = "rgba(216, 82, 21,0.562)";
	rowPostre2.style.backgroundColor = "rgba(216, 82, 21,0.562)";
	algunMenuSeleccionado = true;
}

function queAparezcaELSNACK() {
	// Add the "show" class to DIV
	snackbar.style.display = "";

	// After 3 seconds, remove the show class from DIV
	setTimeout(function(){ unselect(); snackbar.style.display = "none";algunMenuSeleccionado=false; }, 2000);
} 

function mensajeAceptarReserva(){
	if(algunMenuSeleccionado)
		queAparezcaELSNACK();
}

selectMenuButton.addEventListener("click", () => {
	mensajeAceptarReserva();
	selectMenu1();
	
});

function updateTimetableComedores() {
	unselect();
	dayComida.textContent = days[diaActualComedor];
	
	comedorprimero1.textContent = comidaPrimero1[diaActualComedor];
	comedorsegundo1.textContent = comidaSegundo1[diaActualComedor];
	comedorpostre1.textContent = comidaPostre1[diaActualComedor];

	comedorprimero2.textContent = comidaPrimero2[diaActualComedor];
	comedorsegundo2.textContent = comidaSegundo2[diaActualComedor];
	comedorpostre2.textContent = comidaPostre2[diaActualComedor];

	dayCell.textContent = days[currentDayIndex];
	clasesCells.forEach((cell, index) => {
	cell.textContent = clases[currentDayIndex][index];
	});
	
}

updateTimetableComedores();

changeDayButton.addEventListener("click", () => {
	currentDayIndex = (currentDayIndex + 1) % days.length;
	diaActualComedor = (diaActualComedor+ 1) % days.length;
	updateTimetable();
	updateTimetableComedores();
});

function cambiaDiaHorario(anterior){
	if(anterior)
		currentDayIndex = (currentDayIndex + days.length - 1) % days.length;
	else
		currentDayIndex = (currentDayIndex + 1) % days.length;

	updateTimetable();
}

function cambiaDiaComedor(anterior){
	if(anterior)
		diaActualComedor = (diaActualComedor + days.length - 1) % days.length;
	else
		diaActualComedor = (diaActualComedor + 1) % days.length;

	updateTimetableComedores();
}

// -------------------- Cosas del menu de navegacion ---------------------------------------
var images = [{
	"src":"images/Entrada Principal.jpeg",
	"title":"Entrada Principal",
	"qr":"qr/entrada.png"
},{
	"src":"images/Comedor.jpeg",
	"title":"Comedor",
	"qr":"qr/comedor.png"
},{
	"src":"images/1.2.jpeg",
	"title":"1.2",
	"qr":"qr/1_2.png"
},{
	"src":"images/3.3.jpeg",
	"title":"3.3",
	"qr":"qr/3_3.png"
},{
	"src":"images/3.9.jpeg",
	"title":"3.9",
	"qr":"qr/3_9.png"
}];

// Lo pongo por defecto en 0 para poder usar la función inicaFotos cada vez que se cargue el menú de navegación
var index = images.length; //fuera de rango

// Cargo la immg
function iniciaFotos(){
	index = 0;

	var img = images[index].src;
	var nom = images[index].title;

	document.getElementById("image").setAttribute("src",img);
	document.getElementById("title").innerHTML = nom;
}

function siguienteFoto() {
	++index;
	if(images.length <= index){
		index = index - images.length;
	}
	var img = images[index].src;
	var nom = images[index].title;

	document.getElementById("image").setAttribute("src",img);
	document.getElementById("title").innerHTML = nom;
}


function anteriorFoto() {
	--index;
	if(index < 0){
		index = index + images.length;
	}
	var img = images[index].src;
	var nom = images[index].title;

	document.getElementById("image").setAttribute("src",img);
	document.getElementById("title").innerHTML = nom;
}

function dedosExtendidos(frame){
	var extendido = false;
	for(var i =0;i<frame.pointable.length && !extendido; ++i){
		var pointable = frame.pointables[i];
		if(pointable.extended){
			extendido = true;
		}
	}
	return extendido;
}

function showQR(){
	var img = images[index].qr;
	var nom = images[index].title;
	document.getElementById("image").setAttribute("src",img);
	document.getElementById("title").innerHTML = nom;
}

// -------------------------------Gestion de menu -------------------------------------------

var inicioHtml = document.getElementById("inicio");
var comedoresHtml = document.getElementById("comedores");
var horariosHtml = document.getElementById("horarios");
var navegacionHtml = document.getElementById("navegacion");

var wcomedores = comedoresHtml.clientWidth;
var hcomedores = comedoresHtml.clientHeight;
var tcomedores = comedoresHtml.getBoundingClientRect().top;
var lcomedores = comedoresHtml.getBoundingClientRect().left;

var currentMenu = -1;

function ocultarTodo(){
	comedoresHtml.style.display = "none";
	horariosHtml.style.display = "none";
	navegacionHtml.style.display = "none";
	inicioHtml.style.display = "none";
}

function cambiarMenu(){
	ocultarTodo();
	unselect();
	switch(currentMenu){
	case -1:
		inicioHtml.style.display = "";
		break;
	case 0:
		comedoresHtml.style.display = "";
		break;
	case 1:
		horariosHtml.style.display = "";
		break;
	case 2:
		iniciaFotos();	//Reseteo sano de las fotos para que no se quede en la foto antes seleccionada
		navegacionHtml.style.display = "";
		break;
	}
}

cambiarMenu();

function desplazaMenu(izquierda){
	if(izquierda)
		currentMenu = (currentMenu + 1) % 3;
	else
		currentMenu = (currentMenu + 2) % 3;
	cambiarMenu();
}

// Store frame for motion functions
var previousFrame = null;
var paused = false;
var pauseOnGesture = false;
var interactionWidth;
var interactionHeight;

var cursor = document.getElementById("cursor");
var screenWidth = window.innerWidth;
var screenHeight = window.innerHeight;
var comedores;

// Setup Leap loop with frame callback function
var controllerOptions = {enableGestures: true};

// to use HMD mode:
// controllerOptions.optimizeHMD = true;


// Funcion para realizar gestos, la variable gestoRealizado indica el ultimo gesto detectado.

// var gestoRealizado = "unknown"
var interactionSize
var interactionCenter

// setInterval( function() {
// 	switch(gestoRealizado){
// 		case "circle":
// 		comedoresHtml.style.background = "rgba(80, 228, 21, 0.15)";
// 		horariosHtml.style.background = "rgba(80, 228, 21, 0.15)";
// 		updateTimetable();
// 		break;
// 		case "screenTapArriba":
// 		break;
// 		case "screenTapAbajo":
// 		break;
// 		case "swipeIzquierda":
// 		break;
// 		case "swipeDerecha":
// 		break;
// 		case "rotateIzquierda":
// 		break;
// 		case "rotateDerecha":
// 		break;
// 		default:
// 		break;
// 	}
// 	gestoRealizado = "unknown"
// }
// , 500);


function inInteractionBox(pos){
	if(pos[0] < interactionCenter[0] - interactionSize[0]/2)
		return false;
	if(pos[0] > interactionCenter[0] + interactionSize[0]/2)
		return false;
	if(pos[1] < interactionCenter[1] - interactionSize[1]/2)
		return false;
	if(pos[1] > interactionCenter[1] + interactionSize[1]/2)
		return false;
	if(pos[2] < interactionCenter[2] - interactionSize[2]/2)
		return false;
	if(pos[2] > interactionCenter[2] + interactionSize[2]/2)
		return false;

	return true;
}

Leap.loop(controllerOptions, async function(frame) {
	screenWidth = window.innerWidth;
	screenHeight = window.innerHeight;

	interactionSize = frame.interactionBox.size;
	interactionCenter = frame.interactionBox.center;

	// GESTOS DE LEAP
	if (frame.gestures.length > 0) {
		for (var i = 0; i < frame.gestures.length; i++) {
			var gesture = frame.gestures[i];

			
			switch (gesture.type) {
				case "swipe":
					//console.log("ded", contarDedos(frame.hands[0] > 0))
					//console.log("speed", gesture.speed)
					var dir = (gesture.direction[0] > 0) ? 'derecha' : 'izquierda';
		      	if((contarDedos(frame.hands[0]) >= 3) && (gesture.speed > 150) && frame.hands[0].sphereRadius > 100 && gesture.duration < 6000 && frame.hands[0].palmNormal[1] < -0.6)
      				await ejecutaGesto.ejecutaSwipe(dir);
        			break;
      		case "circle":
        			if ( frame.hands[0].sphereRadius < 50 && gesture.progress > 0.9 && gesture.duration < 1000000 && gesture.duration > 1000 && gesture.radius > 10 && gesture.radius < 50)
          			await ejecutaGesto.ejecutaCircle(/*()=>{comedoresHtml.style.background = "rgba(80, 228, 21, 0.15)";}*/)
		        	break;
				case "screenTap":
				case "keyTap":
					var dedo_ind = buscaDedo(frame.hands[0].fingers, 1);
					console.log(dedo_ind.tipVelocity[2])
					if(dedo_ind.tipVelocity[2] > 80 && dedo_ind){
						if(dedo_ind.tipPosition[1]>interactionCenter[1]){
							await ejecutaGesto.ejecutaPoint("arriba");
						}
						else{
							await ejecutaGesto.ejecutaPoint("abajo");
						}
					}
        			break;
      		default:
	      }
		}
	}

	// Gesto comprobación manual
	if (frame.hands.length > 0) {
		for (var i = 0; i < frame.hands.length; i++) {
			var hand = frame.hands[i];

			var hand = frame.hands[i];
			var axis = hand.direction;
			var radius = hand.sphereRadius;
			axis = axis[0];

			// GESTO ROTACION
			if(30 < radius && radius < 70){
				if(0.3 < axis && axis < 0.9 && !dedosExtendidos(frame)  ){
					await ejecutaGesto.ejecutaRotation("left")
				}
				else if ( -0.9 < axis && axis < -0.3  && !dedosExtendidos(frame)){
					await ejecutaGesto.ejecutaRotation("right");
				}
			}
		}
	}

	var pointableString = "";
	if (frame.pointables.length > 0) {
		var fingerTypeMap = ["Thumb", "Index finger", "Middle finger", "Ring finger", "Pinky finger"];
		for (var i = 0; i < frame.pointables.length; i++) {
			var pointable = frame.pointables[i];

			if(!pointable.tool && fingerTypeMap[pointable.type] == "Index finger" && pointable.extended){
				var posDedo = pointable.tipPosition;
				if(inInteractionBox(posDedo) ){
					var x = ((interactionSize[0]/2)* screenWidth/ (interactionSize[0]))+(screenWidth/ (interactionSize[0]))*posDedo[0];
					var y = (-(interactionCenter[1] + interactionSize[1]/2)* screenHeight/ (-interactionSize[1]))+(screenHeight/ (-interactionSize[1]))*posDedo[1] ;
					cursor.style.left = x + "px";
					cursor.style.top = y + "px";
				}
			}
		}
	}

	previousFrame = frame;
})

document.addEventListener('mousemove', function(e){
	var x = e.clientX;
	var y = e.clientY;
	cursor.style.left = x + "px";
	cursor.style.top = y + "px";
});
	
updateTimetable();

// --------------------------- Ejecutores de gestos ----------------------------------------



class EjecutadorGesto{
	constructor(){
   	this.lock_str = "gestos";
	}

	async timeout(){
		return new Promise( (resolve) => {setTimeout(resolve, 1700); } ).then(() => {console.log("\tSe desbloquea");});
	}

	/**
	 * Para el gesto de swipe
	 * @param {String} dir izquierda o derecha
	 */
	async ejecutaSwipe(dir){
		await navigator.locks.request(this.lock_str, {mode: "exclusive", ifAvailable : true}, async (lock) => {
			if (lock){
				console.log("Swipe " + dir);
				var espera = true;
				switch (currentMenu){
					case 0:
					case 1:
					case 2:
						console.log("\tDesplazando hacia " + dir);
						if(dir == "izquierda") desplazaMenu(false);
						if(dir == "derecha") desplazaMenu(true);
						break;
					default:
						espera = false;
				}
				if(espera) await this.timeout();
			}
		});
	}

	async ejecutaCircle() {
		await navigator.locks.request(this.lock_str,{mode: "exclusive",ifAvailable : true}, async (lock) => {
			if (lock){
				console.log("Circle")
				var espera = true;
				switch(currentMenu){
					case -1:
						desplazaMenu(false);
						break;
					case 0:
						console.log("\tComprando menú")
						mensajeAceptarReserva();
						break;
					case 2:
						console.log("\tMostrando qr")
						showQR();
						break;
					default:
						espera = false;
				}
				if (espera) await this.timeout();
			}
		});
	}

	/**
	 * Función que se llama con el gesto point
	 * @param {string} dir "arriba" o "abajo"
	 */
	async ejecutaPoint(dir){
		await navigator.locks.request(this.lock_str, {mode: "exclusive",ifAvailable : true},async (lock) => {
			if (lock){
				console.log("Point")
				var espera = true;
				switch(currentMenu){
					case 0:
						console.log("\tSeleccionar menú " + dir);
						if(dir == "arriba"){
							selectMenu1();
						}
						else{
							selectMenu2();
						}
						break;
					default:
						espera = false
				}
				if (espera) await this.timeout();
			}
		});
	}

	/**
	 * Se llama al detectar la rotación
	 * @param {string} dir "right" o "left"
	 */
	async ejecutaRotation(dir){
		await navigator.locks.request(this.lock_str,{mode: "exclusive",ifAvailable : true}, async (lock) => {
		if (lock){
			console.log("Rotacion");
			var espera = true;
			switch (currentMenu){
				case 0:
					if(dir =="right") {
						console.log("\tSe pasa el dia de comedor rotacion derecha");
						cambiaDiaComedor(false);
					}
					if(dir =="left") {
						console.log("\tSe pasa el dia de comedor rotacion izquierda");
						cambiaDiaComedor(true);
					}
					break;
				case 1:
					if(dir =="right") {
						console.log("\tSe pasa el dia del horario rotacion derecha");
						cambiaDiaHorario(false);
					}
					if(dir =="left") {
						console.log("\tSe pasa el dia de horario rotacion izquierda");
						cambiaDiaHorario(true);
					}
					break;
				case 2:
					if(dir =="right") {
						console.log("\tSe pasa a la imagen rotacion derecha");
						anteriorFoto();
					}
					if(dir =="left") {
						console.log("\tSe pasa a la imagen rotacion izquierda");
						siguienteFoto();
					}
					break;
				default:
					espera = false;
			}
			if (espera) await this.timeout();
		}
		});
	}
}
var ejecutaGesto = new EjecutadorGesto();

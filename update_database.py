import re
import requests
import sqlite3

def create_connection(db_file):
    conn = None
    try:
        conn = sqlite3.connect(db_file)
    except e as e:
        print(e)
    return conn

def create_asignatura(conn, asignatura):
	sql = ''' INSERT INTO asignatura(nombre, grado, cuatri) VALUES (?,?,?) '''
	cur = conn.cursor()
	cur.execute(sql, asignatura)
	conn.commit()
	return cur.lastrowid

def create_subgrupo(conn, subgrupo):
	sql = ''' INSERT INTO subgrupo(asignaturaId, nombre, teoria) VALUES (?,?,?) '''
	cur = conn.cursor()
	cur.execute(sql, subgrupo)
	conn.commit()
	return cur.lastrowid

def create_hora(conn, hora):
	sql = ''' INSERT INTO hora(horaInicio, horaFin, dia, clase, subgrupoId) VALUES (?,?,?,?,?) '''
	cur = conn.cursor()
	cur.execute(sql, hora)
	conn.commit()
	return cur.lastrowid

class Hora:
	def __init__(self, horaInicio, horaFin, dia, clase):
		self.horaInicio =  horaInicio
		self.horaFin =  horaFin
		self.dia =  dia
		self.clase =  clase
	def toString(self):
		separador = " "
		return "" + self.horaInicio + separador + self.horaFin + separador + self.dia + separador + self.clase 

# GRADO = "informatica"
# pagina_asignaturas = requests.get(
#    "https://grados.ugr.es/ramas/ingenieria-arquitectura/grado-ingenieria-informatica"
# )
# regex_asignaturas = re.compile(
#    r"\"http://grados\.ugr\.es/ramas/ingenieria-arquitectura/grado-ingenieria-informatica/(?P<url>(?:[a-z]|-)+)\">(?P<asignatura>(?:\w| |\(|\))+)"
# )
# base_url =  "https://grados.ugr.es/ramas/ingenieria-arquitectura/grado-ingenieria-informatica/"
# regex_subgrupo = re.compile(
# 	r"<div class=\"clase dia-(?P<dia>\d)\" style=\"(?:\w| |;|:|\s|%|#|-)*\">\s*<div class=\"grupo\"><span>Grupo:</span> ?(?P<subgrupo>(?:\d|\w)+) ?</div>\s*<div class=\"otros-datos\">\s*<div>Aula: (?P<aula>[\w ]+)</div>\s*(?:<div>(?:[\w :/]+)</div>\s*){2}<div>Horario: De (?P<ini>[\d:]+) a (?P<fin>[\d:]+)</div>" 
# )

GRADO = "informatica y ade"
RAMA = "dobles-titulaciones/grado-inga-informatica-administ-direcc-empresas"

def descarga(grado, rama, conn):

	print("////////////////////////////////// Procediendo a descargar " + grado + " ///////////////////////////////////")

	pagina_asignaturas = requests.get("https://grados.ugr.es/ramas/"+rama)
	regex_asignaturas = re.compile(r"\"http://grados\.ugr\.es/ramas/"+rama+"/(?P<url>(?:[a-z]|-)+)\">(?P<asignatura>(?:\w| |\(|\))+)")
	base_url =  "https://grados.ugr.es/ramas/"+rama+"/"
	regex_subgrupo = re.compile(r"<div class=\"clase dia-(?P<dia>\d)\" style=\"(?:\w| |;|:|\s|%|#|-)*\">\s*<div class=\"grupo\"><span>Grupo:</span> ?(?P<subgrupo>(?:\d|\w)+) ?</div>\s*<div class=\"otros-datos\">\s*<div>Aula: (?P<aula>[\w ]+)</div>\s*(?:<div>(?:[\w :/]+)</div>\s*){2}<div>Horario: De (?P<ini>[\d:]+) a (?P<fin>[\d:]+)</div>" )

	result = regex_asignaturas.findall(pagina_asignaturas.content.decode("utf-8"))\

	dict_asignaturas = {}
	for r in result:
		dict_asignaturas[r[0]] = r[1]

	print("Conseguido el listado de asignaturas")

	for url in dict_asignaturas:
		asig_id = create_asignatura(conn, (dict_asignaturas[url], grado, 0))
		print("Creada asignatura " + dict_asignaturas[url] + " g: " + grado)
		pagina = requests.get(base_url + url)
		result_horas = regex_subgrupo.findall(pagina.content.decode("utf-8"))

		dict_subgrupos = {}
		for r in result_horas:
			if not dict_subgrupos.get(r[1]):
				dict_subgrupos[r[1]] = []
			dict_subgrupos[r[1]].append( Hora(r[3], r[4], r[0], r[2]) )
		print("Obtenido horarios")

		for h in dict_subgrupos:
			subgrupo_id = create_subgrupo(conn, (asig_id, h, bool(re.search(r"\d+", h))))
			print("--Subgrupo " + h)
			for i in dict_subgrupos[h]:
				print("----" + i.toString())
				create_hora(conn, (i.horaInicio, i.horaFin, i.dia, i.clase, subgrupo_id))


objetivos = { 
	"informática" : "ingenieria-arquitectura/grado-ingenieria-informatica",
	"informática y matemáticas" : "dobles-titulaciones/grado-ingenieria-informatica-matematicas",
	"informática y ade" : "dobles-titulaciones/grado-inga-informatica-administ-direcc-empresas"
} 
path = "horario_database.db"
conn = create_connection(path)

with conn:
	for ob in objetivos:
		print(ob, objetivos[ob])
		descarga(ob, objetivos[ob], conn)

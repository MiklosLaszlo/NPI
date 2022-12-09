## BUENOS DIAS

Cosas a tener en cuenta para la práctica:
- Todas las variables del main están en el archivo dec_var.
- Como gcc puede compilar (por motivos) funciones anidadas no hace falta declarar todas las variables globales, solo las del main. Además no hace falta tener cuidado con declaraciones de funciones dentro de otras.
- Se ha añadido al entradaTS:
    - Nombre de la variable/funcion/parametro/expresion_temporal. De forma automatica se generan el nombre de la variables del y de las expresiones temporales. El motivo es el dominio global de las variables del main (SI NO FUESE POR ESO NO MOLESTARIAN LAS VARIABLES). Las temporales se cambian por motivos evidentes.
    - Para las listas puntero a la posición actual.
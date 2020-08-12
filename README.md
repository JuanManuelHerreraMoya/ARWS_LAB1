# ARWS_LAB1

## Primera parte
Part I - Introduction to threads in JAVA
In agreement with the lectures, complete the classes CountThread, so that they define the life cycle of a thread that prints the numbers between A and B on the screen.
Complete the main method of the CountMainThreads class so that: 
Create 3 threads of type CountThread, assigning the first interval [0..99], the second [99..199], and the third [200..299]. 
Start the three threads with start(). Run and check the output on the screen. 
Change the beginning with start() to run(). How does the output change? Why?

_______________________

1. A continuacion podemos observar la diferencia entre el .run() y el .start()
  al extender la clase thread podemos usar los metodos tanto como run y start 
  pero al implementar Runnable nos damos cuenta de que debemos sobre cargar el metodo .start
  para iniciar el hilo a diferencia del extend ya que se herada el thread y podemos 
  usar sus metodos.
  
*****************************

2. Al usar el Start nos damos cuenta que inicia el hilo y luego de haber iniciado , es ir y ejecutar lo que tenga el metodo run() en este caso imprimir entre un rango a y b
  ![](imagenes/start.jpg)
_______________________

3. Usamos el run y nos damos cuenta que como no se inicia el hilo , ejecuta secuencialmente el metodo run y mostrara en orden los rangos de acuerdo como se coloquen en el main

  ![run](imagenes/run.jpg)

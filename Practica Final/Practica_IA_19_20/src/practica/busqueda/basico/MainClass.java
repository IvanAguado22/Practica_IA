package practica.busqueda.basico;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import practica.json.LectorJSON;
import practica.objetos.Herramienta;
import practica.objetos.Tarea;
import practica.objetos.Trabajador;

/**
 * Clase creada como base para la parte 1 de la práctica 2019-2020 de Inteligencia Artificial, UC3M, Colmenarejo
 *
 * @author Daniel Amigo Herrero
 * @author David Sánchez Pedroche
 */

public class MainClass {

	/**
	 * Ejecuta la solución del problema avanzado con un algoritmo de búsqueda
	 * MODIFICAR
	 * @param args[0]: Ruta del fichero a ejecutar
	 * @param args[1]: Número de debug a utilizar
	 */
	public static void main(java.lang.String[] args) throws IOException {

		/**
		 * No se permite modificar el código desde aquí. Salvo el valor de printDebug o problemPath
		 */ 

		System.out.println("--------------------------------------------------------");
		System.out.println("********** PRACTICA IA 19-20 UC3M COLMENAREJO **********");
		System.out.println("************** SOLUCION BUSQUEDA - BASICO **************");
		System.out.println("--------------------------------------------------------");

		// Se define el nivel de debug a utilizar: Por argumentos el segundo parámetro.
		int printDebug; // Nivel de debug. Permite elegir la cantidad de mensajes a imprimir
		if (args.length > 1) printDebug =  Integer.parseInt(args[1]);
		else printDebug = 0; // Definir aquí el valor

		//----------------------------- Se carga el problema -----------------------------//
		String problemPath = "problema.json"; // Problema en la misma ruta del paquete
		InputStream isJSON;
		// Si hay argumentos, se busca un fichero por parámetro. NO MODIFICAR
		if (args.length > 0 && !args[0].equals("")) isJSON = new FileInputStream(args[0]);
		else isJSON = LectorJSON.class.getResourceAsStream(problemPath); // Se busca en el path de LectorJSON dicho fichero
		LectorJSON lectorJSON = new LectorJSON();
		lectorJSON.readJSON(isJSON);
		// Se crean todas las herramientas repetidas en función de la cantidad. Por ejemplo, si hay 4 destornilladores se crean otros 3 únicos
		/*for(Herramienta herramienta : lectorJSON.getHerramientas()) {
			if(herramienta.getCantidad() > 1) {
				for(int i = 0; i < herramienta.getCantidad()-1; i++) {
					Herramienta nuevaHerramienta = new Herramienta(herramienta);
					lectorJSON.getHerramientas().add(nuevaHerramienta);
				}
			}
		}
		for(int i = 0; i < lectorJSON.getHerramientas().size(); i++) {
			lectorJSON.getHerramientas().get(i).setId(i);
		}*/
		ArrayList<Herramienta> readedHerramientas = lectorJSON.getHerramientas();
		ArrayList<Trabajador>  readedTrabajadores = lectorJSON.getTrabajadores();
		ArrayList<Tarea>       readedTareas       = lectorJSON.getTareas();

		/**
		 * No se permite modificar el código hasta aquí. Salvo el valor de printDebug o problemPath
		 */

		//----------------------------- Se preparan los objetos a utilizar en esta solución básica -----------------------------//


		// Herramientas
		ArrayList<Herramienta> herramientas = readedHerramientas;
		// Trabajadores
		// Al tratarse de un solo trabajador, Antonio, en esta solución básica, el array trabajadores contendrá solo a Antonio
		ArrayList<Trabajador> trabajadores = new ArrayList<Trabajador>();
		for(int i = 0; i < readedTrabajadores.size(); i++) {
			if(readedTrabajadores.get(i).getNombre().equals("Antonio")) {
				trabajadores.add(readedTrabajadores.get(i));
			}
		}
		// Tareas
		ArrayList<Tarea> tareas = readedTareas;

		//-------- Se crean los inicializan los objetos para ejecutar la solución --------//
		Node initialNode = new Node(null, herramientas, trabajadores, tareas);
		Node goalNode    = new Node(initialNode);
		for(int i = 0; i < goalNode.tareas.size(); i++) {
			goalNode.tareas.get(i).setUnidades(0);
		}
		AStar aStar = new AStar(printDebug, initialNode, goalNode); // Se inicializa el A-Estrella

		//----------------------------- Ejecución del algoritmo ---------------------------//
		double executionTime = aStar.Algorithm();
		System.out.println("--------------------------------------------------------");
		System.out.println("******************** FIN EJECUCION *********************");
		System.out.println("--------------------------------------------------------");

		//---------------------------- Extracción de información ---------------------------//
		printFinalPath(aStar);
		System.out.println("------------------------ METRICAS -----------------------");
		printMetrics(aStar, executionTime);
	}

	/**
	 * Se generan las métricas implementadas y se imprimen sus resultados
	 * @param aStar
	 */
	public static void printFinalPath(AStar aStar) {
		if(aStar.isFindGoal()) System.out.println("****************** CAMINO ENCONTRADO *******************");
		else System.out.println("***************** CAMINO NO ENCONTRADO *****************");
		System.out.println("************** IMPRESION DEL CAMINO REALIZADO **************");

		// Genera el camino para llegar a la meta desde el nodo inicial
		List<Node> path = aStar.getPath(aStar.getGoalNode());
		for (Node node:path) {
			node.printNodeData(2);	// printDebug = 2
		}
		System.out.println();
	}

	/**
	 * Se generan las métricas implementadas y se imprimen sus resultados
	 * MODIFICAR
	 * @param aStar
	 * @param executionTime
	 */
	public static void printMetrics(AStar aStar, double executionTime) {
		System.out.println("************** IMPRESION DE METRICAS **************");
		System.out.println("La ejecución ha tardado: "+executionTime +" segundos");
		System.out.println("El número de nodos visitados es: " + aStar.getPath(aStar.getGoalNode()).size() + " nodos");
		// TODO añadir resto de métricas (nodos expandidos, etc)
	}

}

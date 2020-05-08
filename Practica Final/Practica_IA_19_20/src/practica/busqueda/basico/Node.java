package practica.busqueda.basico;

import java.util.ArrayList;

import practica.objetos.Herramienta;
import practica.objetos.Tarea;
import practica.objetos.Trabajador;

/**
 * Clase creada como base para la parte 2 de la pr�ctica 2019-2020 de Inteligencia Artificial, UC3M, Colmenarejo
 *
 * @author Daniel Amigo Herrero
 * @author David S�nchez Pedroche
 * 
 */

public class Node {
	private double cost;							// Valor del coste de llegada al nodo
	private double heuristic;						// Valor de la heur�stica del nodo
	private double evaluation;						// Valor de la evaluaci�n
	private Node parent;							// Nodo padre del arbol A*
	private Node nextNodeList = null;				// Para la gesti�n de la lista
	ArrayList<Herramienta> herramientas;
	ArrayList<Trabajador>  trabajadores;
	ArrayList<Tarea>       tareas;
	// A�adir m�s variables si se desea

	/**
	 * MODIFICAR
	 * Constructor para introducir un nuevo nodo en el algoritmo A estrella
	 */
	public Node(Node parentNode, ArrayList<Herramienta> herramientas, ArrayList<Trabajador> trabajadores, ArrayList<Tarea> tareas) {
		this.parent       = parentNode;  // padre en el �rbol A*
		this.herramientas = herramientas;
		this.trabajadores = trabajadores;
		this.tareas       = tareas;
		// A�adir m�s variables si se desea
	}

	/**
	 * MODIFICAR
	 * Constructor auxiliar para la implementaci�n del algoritmo. Genera una copia de un nodo para introducirla en la OpenList
	 */ 
	public Node(Node original) {
		// Incluir todas las variables del nodo
		this.cost        = original.cost;
		this.heuristic   = original.heuristic;
		this.evaluation   = original.evaluation;
		this.parent       = original.parent;
		this.nextNodeList = original.nextNodeList;
		// A�adir m�s variables si se desea

		// Se copian los objetos de los ArrayList a uno nuevo de este Nodo
		// Si se necesita a�adir valores variables, como un ID, utilizar setters
		ArrayList<Trabajador> trabajadores = new ArrayList<Trabajador>();
		for (int i = 0; i < original.trabajadores.size(); i++) {
			Trabajador trabajador = new Trabajador(original.trabajadores.get(i).getNombre(), original.trabajadores.get(i).getHabPodar(), original.trabajadores.get(i).getHabLimpiar(), original.trabajadores.get(i).getHabReparar());
			trabajadores.add(trabajador);
			Herramienta herramienta = new Herramienta(original.trabajadores.get(i).getHerramienta());
			trabajador.setHerramienta(herramienta);
			trabajador.setArea(original.trabajadores.get(i).getArea());
			trabajador.setTiempoOcupado(original.trabajadores.get(i).getTiempoOcupado());
			Tarea tarea = new Tarea(original.trabajadores.get(i).getTarea());
			trabajador.setTarea(tarea);
		}
		this.trabajadores = trabajadores;
		ArrayList<Herramienta> herramientas = new ArrayList<Herramienta>();
		for (int i = 0; i < original.herramientas.size(); i++) {
			Herramienta herramienta = new Herramienta(original.herramientas.get(i).getNombre(), original.herramientas.get(i).getTrabajo(), original.herramientas.get(i).getPeso(), original.herramientas.get(i).getMejora(), original.herramientas.get(i).getCantidad());
			herramientas.add(herramienta);
		}
		this.herramientas = herramientas;
		ArrayList<Tarea> tareas = new ArrayList<Tarea>();
		for (int i = 0; i < original.tareas.size(); i++) {
			Tarea tarea = new Tarea(original.tareas.get(i).getTipo(), original.tareas.get(i).getArea(), original.tareas.get(i).getUnidades());
			tareas.add(tarea);
		}
		this.tareas = tareas;
	}

	/**
	 * Constructor auxiliar para generar el primer nodo de la lista abierta
	 */ 
	public Node() {	}

	/**
	 *  Calcula el valor de la heuristica del problema para el nodo 
	 *  MODIFICAR
	 * @param finalNode - El nodo sobre el que calcular la heur�stica
	 * this.heuristica  - Resultado
	 */
	public void computeHeuristic(Node finalNode) {
		// Heur�stica muy b�sica: las tareas restantes por hacer
		
		this.heuristic = 0;
		
		for(int i = 0; i < this.tareas.size(); i++) {
			if(this.tareas.get(i).getUnidades() > 0) {
				this.heuristic++;
			}
		}
		
	}

	/**
	 * Comprobaci�n de que la informaci�n de un nodo es equivalente a la de otro nodo
	 * Solo comparar la informaci�n necesaria para ver si es el mismo estado del problema
	 * 
	 * @param other - el nodo con el que comparar this
	 * @return true: son iguales. false: no lo son
	 */
	public boolean equals(Node other) {
		boolean check = true;
		
		// Caso nodo meta: bastar� con que las unidades de trabajo sean nulas
		boolean goalNode = true;
		for(int i = 0; i < this.tareas.size(); i++) {
			if(this.tareas.get(i).getUnidades() != 0) {
				goalNode = false;
			}
		}
		for(int i = 0; i < this.tareas.size(); i++) {
			if(other.tareas.get(i).getUnidades() != 0) {
				goalNode = false;
			}
		}
		if(goalNode) {
			return check;
		}
		
		// Resto de casos: ser�n iguales si los trabajadores est�n en las mismas �reas, con las mismas herramientas equipadas y con las mismas unidades de tarea restantes
		// Primero se comprueba si alg�n trabajador est� en �reas distintas en cada nodo
		for(int i = 0; i < this.trabajadores.size(); i++) {
			if(!this.trabajadores.get(i).getArea().equals(other.trabajadores.get(i).getArea())) {
				check = false;
			}
		}
		// Viendo que est�n en las mismas �reas, se comprueba si alguno tiene una herramienta distinta
		for(int i = 0; i < this.trabajadores.size(); i++) {
			if(!this.trabajadores.get(i).getHerramienta().equals(other.trabajadores.get(i).getHerramienta())) {
				check = false;
			}
		}
		// Por �ltimo, se comprueba si las unidades de trabajo de alguna tarea es distinta
		for(int i = 0; i < this.tareas.size(); i++) {
			if(this.tareas.get(i).getUnidades() != other.tareas.get(i).getUnidades()) {
				check = false;
			}
		}
		return check;
	}


	/**
	 * Impresi�n de la informaci�n del nodo
	 * @param printDebug. Permite seleccionar cu�ntos mensajes imprimir
	 */
	public void printNodeData(int printDebug) {
		
	}

	/**
	 * Ejecuta la funci�n de evaluacion del problema para el nodo. IMPORTANTE: ejecutar despu�s el c�lculo del coste y heur�stica
	 */
	public void computeEvaluation() {
		this.evaluation = this.cost + this.heuristic; 
	}
	
	/**
	 * M�todo auxiliar para a�adir una cantidad al coste
	 */
	public void addCoste(double cantidad) {
		setCoste(cost + cantidad);
	}

	/**** Getters y Setters ****/
	/**
	 * MODIFICAR si se considera necesario. No es imprescindible, solo si consideras que puede ayudar a tu implementaci�n
	 */
	public double getEvaluation() {
		return evaluation;
	}
	public ArrayList<Herramienta> getHerramientas() {
		return herramientas;
	}
	public void setHerramientas(ArrayList<Herramienta> herramientas) {
		this.herramientas = herramientas;
	}
	public ArrayList<Trabajador> getTrabajadores() {
		return trabajadores;
	}
	public void setTrabajadores(ArrayList<Trabajador> trabajadores) {
		this.trabajadores = trabajadores;
	}
	public ArrayList<Tarea> getTareas() {
		return tareas;
	}
	public void setTareas(ArrayList<Tarea> tareas) {
		this.tareas = tareas;
	}
	public void setEvaluation(double evaluacion) {
		this.evaluation = evaluacion;
	}
	public double getCost() {
		return cost;
	}
	public void setCoste(double coste) {
		this.cost = coste;
	}
	public double getHeuristic() {
		return heuristic;
	}
	public void setHeuristic(double heuristica) {
		this.heuristic = heuristica;
	}
	public Node getParent() {
		return parent;
	}
	public void setParent(Node parent) {
		this.parent = parent;
	}
	public Node getNextNode() {
		return nextNodeList;
	}
	public void setNextNode(Node nextNode) {
		this.nextNodeList = nextNode;
	}
}

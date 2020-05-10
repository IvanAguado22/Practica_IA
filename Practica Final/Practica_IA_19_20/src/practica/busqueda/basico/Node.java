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
			if(original.trabajadores.get(i).getHerramienta() != null) {
				Herramienta herramienta = new Herramienta(original.trabajadores.get(i).getHerramienta());
				trabajador.setHerramienta(herramienta);
			}
			trabajador.setArea(original.trabajadores.get(i).getArea());
			trabajador.setTiempoOcupado(original.trabajadores.get(i).getTiempoOcupado());
			if(original.trabajadores.get(i).getTarea() != null) {
				Tarea tarea = new Tarea(original.trabajadores.get(i).getTarea());
				trabajador.setTarea(tarea);
			}
			trabajadores.add(trabajador);
		}
		this.trabajadores = trabajadores;
		ArrayList<Herramienta> herramientas = new ArrayList<Herramienta>();
		for (int i = 0; i < original.herramientas.size(); i++) {
			Herramienta herramienta = new Herramienta(original.herramientas.get(i).getNombre(), original.herramientas.get(i).getTrabajo(), original.herramientas.get(i).getPeso(), original.herramientas.get(i).getMejora(), original.herramientas.get(i).getCantidad());
			herramienta.setId(original.herramientas.get(i).getId());
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
			this.heuristic += 60 * this.tareas.get(i).getUnidades();
		}
		
		double costeDesplazar = 0;
		for(int i = 0; i < this.trabajadores.size(); i++) {
			for(int j = 0; j < this.tareas.size(); j++) {
				if(this.trabajadores.get(i).getHerramienta() != null) {
					if(this.trabajadores.get(i).getHerramienta().getTrabajo().equals(this.tareas.get(j).getTipo())) {
						if((j + i*this.tareas.size()) == 0) {
							costeDesplazar = this.trabajadores.get(i).TiempoEnDesplazarse(this.trabajadores.get(i).getArea(), this.tareas.get(j).getArea());
						}
						if(costeDesplazar > this.trabajadores.get(i).TiempoEnDesplazarse(this.trabajadores.get(i).getArea(), this.tareas.get(j).getArea())) {
							costeDesplazar = this.trabajadores.get(i).TiempoEnDesplazarse(this.trabajadores.get(i).getArea(), this.tareas.get(j).getArea());
						}
					}
				}
			}
		}
		this.heuristic += costeDesplazar;
	}

	/**
	 * Comprobaci�n de que la informaci�n de un nodo es equivalente a la de otro nodo
	 * Solo comparar la informaci�n necesaria para ver si es el mismo estado del problema
	 * 
	 * @param other - el nodo con el que comparar this
	 * @return true: son iguales. false: no lo son
	 */
	public boolean equals(Node other) {
		// Que todas las tareas tengan las mismas unidades
		for(int i = 0; i < this.tareas.size(); i++) {
			if(this.tareas.get(i).getUnidades() != other.tareas.get(i).getUnidades()) {
				return false;
			}
		}
		// Que todos los trabajadores hayan trabajado el mismo tiempo, est�n en los mismos sitios y con las mismas herramientas
		for(int i = 0; i < this.trabajadores.size(); i++) {
			if(this.trabajadores.get(i).getTiempoOcupado() != other.trabajadores.get(i).getTiempoOcupado()) {
				return false;
			}
			if(!this.trabajadores.get(i).getArea().equals(other.trabajadores.get(i).getArea())) {
				return false;
			}
			if(this.trabajadores.get(i).getHerramienta() != null && other.trabajadores.get(i).getHerramienta() != null) {
				if(this.trabajadores.get(i).getHerramienta().getId() != other.trabajadores.get(i).getHerramienta().getId()) {
					return false;
				}
			}else if(this.trabajadores.get(i).getHerramienta() != null && other.trabajadores.get(i).getHerramienta() == null) {
				return false;
			}else if(this.trabajadores.get(i).getHerramienta() == null && other.trabajadores.get(i).getHerramienta() != null) {
				return false;
			}
		}
		// Que las herramientas est�n en los mismos sitios
		for(int i = 0; i < this.tareas.size(); i++) {
			if(this.herramientas.get(i).getArea().equals(other.herramientas.get(i).getArea())) {
				return false;
			}
		}
		
		return true;
	}


	/**
	 * Impresi�n de la informaci�n del nodo
	 * @param printDebug. Permite seleccionar cu�ntos mensajes imprimir
	 */
	public void printNodeData(int printDebug) {
		// Se imprime, para cada trabajador, d�nde est�, qu� herramienta lleva y el tiempo que lleva trabajando. Tambi�n se imprime el n�mero 
		// de tareas restantes por hacer
		for(int i = 0; i < this.trabajadores.size(); i++) {
			Trabajador trabajador = this.trabajadores.get(i);
			if(trabajador.getHerramienta() == null) {
				System.out.println(trabajador.getNombre() + " est� en " + trabajador.getArea() + ", sin herramienta y lleva trabajando " + trabajador.getTiempoOcupado() + " minutos.");
			}else {
				System.out.println(trabajador.getNombre() + " est� en " + trabajador.getArea() + ", con herramienta " + trabajador.getHerramienta().getNombre() + " y lleva trabajando " + trabajador.getTiempoOcupado() + " minutos.");
			}
		}
		int tareasRestantes = 0;
		for(int i = 0; i < this.tareas.size(); i++) {
			if(tareas.get(i).getUnidades() > 0) {
				tareasRestantes++;
			}
		}
		System.out.println("Quedan " + tareasRestantes + " tareas restantes por hacer.");
		System.out.println("H(n): " + this.heuristic + "; C(n): " + this.cost + "; E(n): " + this.evaluation);
		System.out.println();
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
	
	public void setNodeListNull() {
		this.nextNodeList = null;
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

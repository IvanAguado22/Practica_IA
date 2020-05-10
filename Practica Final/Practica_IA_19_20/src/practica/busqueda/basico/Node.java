package practica.busqueda.basico;

import java.util.ArrayList;

import practica.objetos.Herramienta;
import practica.objetos.Tarea;
import practica.objetos.Trabajador;

/**
 * Clase creada como base para la parte 2 de la práctica 2019-2020 de Inteligencia Artificial, UC3M, Colmenarejo
 *
 * @author Daniel Amigo Herrero
 * @author David Sánchez Pedroche
 * 
 */

public class Node {
	private double cost;							// Valor del coste de llegada al nodo
	private double heuristic;						// Valor de la heurística del nodo
	private double evaluation;						// Valor de la evaluación
	private Node parent;							// Nodo padre del arbol A*
	private Node nextNodeList = null;				// Para la gestión de la lista
	ArrayList<Herramienta> herramientas;
	ArrayList<Trabajador>  trabajadores;
	ArrayList<Tarea>       tareas;
	// Añadir más variables si se desea

	/**
	 * MODIFICAR
	 * Constructor para introducir un nuevo nodo en el algoritmo A estrella
	 */
	public Node(Node parentNode, ArrayList<Herramienta> herramientas, ArrayList<Trabajador> trabajadores, ArrayList<Tarea> tareas) {
		this.parent       = parentNode;  // padre en el árbol A*
		this.herramientas = herramientas;
		this.trabajadores = trabajadores;
		this.tareas       = tareas;
		// Añadir más variables si se desea
	}

	/**
	 * MODIFICAR
	 * Constructor auxiliar para la implementación del algoritmo. Genera una copia de un nodo para introducirla en la OpenList
	 */ 
	public Node(Node original) {
		// Incluir todas las variables del nodo
		this.cost        = original.cost;
		this.heuristic   = original.heuristic;
		this.evaluation   = original.evaluation;
		this.parent       = original.parent;
		this.nextNodeList = original.nextNodeList;
		// Añadir más variables si se desea

		// Se copian los objetos de los ArrayList a uno nuevo de este Nodo
		// Si se necesita añadir valores variables, como un ID, utilizar setters
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
	 * @param finalNode - El nodo sobre el que calcular la heurística
	 * this.heuristica  - Resultado
	 */
	public void computeHeuristic(Node finalNode) {
		// Heurística muy básica: las tareas restantes por hacer
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
	 * Comprobación de que la información de un nodo es equivalente a la de otro nodo
	 * Solo comparar la información necesaria para ver si es el mismo estado del problema
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
		// Que todos los trabajadores hayan trabajado el mismo tiempo, están en los mismos sitios y con las mismas herramientas
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
		// Que las herramientas estén en los mismos sitios
		for(int i = 0; i < this.tareas.size(); i++) {
			if(this.herramientas.get(i).getArea().equals(other.herramientas.get(i).getArea())) {
				return false;
			}
		}
		
		return true;
	}


	/**
	 * Impresión de la información del nodo
	 * @param printDebug. Permite seleccionar cuántos mensajes imprimir
	 */
	public void printNodeData(int printDebug) {
		// Se imprime, para cada trabajador, dónde está, qué herramienta lleva y el tiempo que lleva trabajando. También se imprime el número 
		// de tareas restantes por hacer
		for(int i = 0; i < this.trabajadores.size(); i++) {
			Trabajador trabajador = this.trabajadores.get(i);
			if(trabajador.getHerramienta() == null) {
				System.out.println(trabajador.getNombre() + " está en " + trabajador.getArea() + ", sin herramienta y lleva trabajando " + trabajador.getTiempoOcupado() + " minutos.");
			}else {
				System.out.println(trabajador.getNombre() + " está en " + trabajador.getArea() + ", con herramienta " + trabajador.getHerramienta().getNombre() + " y lleva trabajando " + trabajador.getTiempoOcupado() + " minutos.");
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
	 * Ejecuta la función de evaluacion del problema para el nodo. IMPORTANTE: ejecutar después el cálculo del coste y heurística
	 */
	public void computeEvaluation() {
		this.evaluation = this.cost + this.heuristic; 
	}
	
	/**
	 * Método auxiliar para añadir una cantidad al coste
	 */
	public void addCoste(double cantidad) {
		setCoste(cost + cantidad);
	}
	
	public void setNodeListNull() {
		this.nextNodeList = null;
	}

	/**** Getters y Setters ****/
	/**
	 * MODIFICAR si se considera necesario. No es imprescindible, solo si consideras que puede ayudar a tu implementación
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

package practica.busqueda.basico;

import java.util.ArrayList;
import java.util.List;

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

public class AStar {

	int printDebug; // 0: nada, 1: informaci�n b�sica, 2: informaci�n completa

	private OpenList openList = new OpenList();						// Lista de nodos por explorar
	private ArrayList<Node> closedList = new ArrayList<Node>();		// Lista de nodos explorados
	private Node initialNode;										// Nodo inicial del problema
	private Node goalNode;											// Nodo meta del problema
	private boolean findGoal;										// Se ha encontrado la meta

	/**
	 * Insertamos en la lista de nodos abiertos los nodos seg�n las acciones que se pueden realizar en este instante
	 * MODIFICAR
	 * @param currentNode - el nodo actual
	 */
	private void addAdjacentNodes(Node currentNode) {
		// MODIFICAR para insertar las acciones espec�ficas del problema		
		Node copy = new Node(currentNode);
		
		ArrayList<Trabajador> trabajadores  = copy.getTrabajadores();
		ArrayList<Herramienta> herramientas = copy.getHerramientas();
		ArrayList<Tarea> tareas             = copy.getTareas();
		
		for(int i = 0; i < trabajadores.size(); i++) {
			Trabajador t = trabajadores.get(i);
			// Si est� en el almac�n y no tiene herramienta, coge la primera disponible (no consume tiempo)
			if(t.getArea().equals("A") && t.getHerramienta()==null) {
				for(int j = 0; j < herramientas.size(); j++) {
					if(herramientas.get(j).getCantidad() > 0) {
						t.cogerHerramienta(herramientas.get(j));
						if(checkNode(copy)) {
							copy.computeHeuristic(goalNode);
							// @TODO cambiar coste si necesario
							copy.computeEvaluation();
							copy.setParent(currentNode);
							this.openList.insertAtEvaluation(copy);
						}
					}
				}
			}
			
			// Moverse a un �rea adyacente
			switch(t.getArea()) {
			case "A":
				// Desde A puede moverse a 3 �reas
				for(int j=0; j<3; j++) {
					// Moverse a J2
					if(j==0) {
						t.aumentarTiempoDesp("A", "J2");
						t.setArea("J2");
					}
					// Moverse a J3
					if(j==1) {
						t.aumentarTiempoDesp("A", "J3");
						t.setArea("J3");
					}
					// Moverse a C2
					if(j==2) {
						t.aumentarTiempoDesp("A", "C2");
						t.setArea("C2");
					}
					if(checkNode(copy)) {
						copy.computeHeuristic(goalNode);
						copy.addCoste(5);
						copy.computeEvaluation();
						copy.setParent(currentNode);
						this.openList.insertAtEvaluation(copy);
					}
				}
				break;
			case "J2":
				// Desde J2 puede moverse a 5 �reas
				for(int j=0; j<5; j++) {
					// Moverse a A
					if(j==0) {
						t.aumentarTiempoDespA();
						t.setArea("A");
					}
					// Moverse a U
					if(j==1) {
						t.aumentarTiempoDesp("J2", "U");
						t.setArea("U");
					}
					// Moverse a C2
					if(j==2) {
						t.aumentarTiempoDesp("J2", "C2");
						t.setArea("C2");
					}
					// Moverse a C1
					if(j==3) {
						t.aumentarTiempoDesp("J2", "C1");
						t.setArea("C1");
					}
					// Moverse a J1
					if(j==4) {
						t.aumentarTiempoDesp("J2", "J1");
						t.setArea("J1");
					}
					if(checkNode(copy)) {
						copy.computeHeuristic(goalNode);
						copy.addCoste(5);
						copy.computeEvaluation();
						copy.setParent(currentNode);
						this.openList.insertAtEvaluation(copy);
					}
				}
				break;
			case "R":
				// Desde R solo puede moverse a 1 �rea
				t.aumentarTiempoDesp("R", "J3");
				t.setArea("J3");
				if(checkNode(copy)) {
					copy.computeHeuristic(goalNode);
					copy.addCoste(5);
					copy.computeEvaluation();
					copy.setParent(currentNode);
					this.openList.insertAtEvaluation(copy);
				}
				break;
			case "J3":
				// Desde J3 puede moverse a 3 �reas
				for(int j=0; j<3; j++) {
					// Moverse a A
					if(j==0) {
						t.aumentarTiempoDespA();
						t.setArea("A");
					}
					// Moverse a R
					if(j==1) {
						t.aumentarTiempoDesp("J3", "R");
						t.setArea("R");
					}
					// Moverse a C2
					if(j==2) {
						t.aumentarTiempoDesp("J3", "C2");
						t.setArea("C2");
					}
					if(checkNode(copy)) {
						copy.computeHeuristic(goalNode);
						copy.addCoste(5);
						copy.computeEvaluation();
						copy.setParent(currentNode);
						this.openList.insertAtEvaluation(copy);
					}
				}
				break;
			case "C2":
				// Desde C2 se puede mover a 4 �reas
				for(int j=0; j<4; j++) {
					// Moverse a A
					if(j==0) {
						t.aumentarTiempoDespA();
						t.setArea("A");
					}
					// Moverse a J3
					if(j==1) {
						t.aumentarTiempoDesp("C2", "J3");
						t.setArea("J3");
					}
					// Moverse a J2
					if(j==2) {
						t.aumentarTiempoDesp("C2", "J2");
						t.setArea("J2");
					}
					// Moverse a C1
					if(j==3) {
						t.aumentarTiempoDesp("C2", "C1");
						t.setArea("C1");
					}
					if(checkNode(copy)) {
						copy.computeHeuristic(goalNode);
						copy.addCoste(5);
						copy.computeEvaluation();
						copy.setParent(currentNode);
						this.openList.insertAtEvaluation(copy);
					}
				}
				break;
			case "C1":
				// Desde C1 se puede mover a 3 �reas
				for(int j=0; j<3; j++) {
					// Moverse a J2
					if(j==0) {
						t.aumentarTiempoDesp("C1", "J2");
						t.setArea("J2");
					}
					// Moverse a J1
					if(j==1) {
						t.aumentarTiempoDesp("C1", "J1");
						t.setArea("J1");
					}
					// Moverse a C2
					if(j==2) {
						t.aumentarTiempoDesp("C1", "C2");
						t.setArea("C2");
					}
					if(checkNode(copy)) {
						copy.computeHeuristic(goalNode);
						copy.addCoste(5);
						copy.computeEvaluation();
						copy.setParent(currentNode);
						this.openList.insertAtEvaluation(copy);
					}
				}
				break;
			case "J1":
				// Desde J1 se puede mover a 4 �reas
				for(int j=0; j<4; j++) {
					// Moverse a U
					if(j==0) {
						t.aumentarTiempoDesp("J1", "U");
						t.setArea("U");
					}
					// Moverse a B
					if(j==1) {
						t.aumentarTiempoDesp("J1", "B");
						t.setArea("B");
					}
					// Moverse a J2
					if(j==2) {
						t.aumentarTiempoDesp("J1", "J2");
						t.setArea("J2");
					}
					// Moverse a C1
					if(j==3) {
						t.aumentarTiempoDesp("J1", "C1");
						t.setArea("C1");
					}
					if(checkNode(copy)) {
						copy.computeHeuristic(goalNode);
						copy.addCoste(5);
						copy.computeEvaluation();
						copy.setParent(currentNode);
						this.openList.insertAtEvaluation(copy);
					}
				}
				break;
			case "U":
				// Desde U se puede mover a 3 �reas
				for(int j=0; j<3; j++) {
					// Moverse a B
					if(j==0) {
						t.aumentarTiempoDesp("U", "B");
						t.setArea("B");
					}
					// Moverse a J1
					if(j==1) {
						t.aumentarTiempoDesp("U", "J1");
						t.setArea("J1");
					}
					// Moverse a J2
					if(j==2) {
						t.aumentarTiempoDesp("U", "J2");
						t.setArea("J2");
					}
					if(checkNode(copy)) {
						copy.computeHeuristic(goalNode);
						copy.addCoste(5);
						copy.computeEvaluation();
						copy.setParent(currentNode);
						this.openList.insertAtEvaluation(copy);
					}
				}
				break;
			case "B":
				// Desde B se puede mover a 2 �reas
				for(int j=0; j<2; j++) {
					// Moverse a U
					if(j==0) {
						t.aumentarTiempoDesp("B", "U");
						t.setArea("U");
					}
					// Moverse a J1
					if(j==1) {
						t.aumentarTiempoDesp("B", "J1");
						t.setArea("J1");
					}
					if(checkNode(copy)) {
						copy.computeHeuristic(goalNode);
						copy.addCoste(5);
						copy.computeEvaluation();
						copy.setParent(currentNode);
						this.openList.insertAtEvaluation(copy);
					}
				}
				break;
			default:
				break;
			}
			
			// Realizar una tarea con la herramienta que tiene y el area donde est�, si puede
		}
		
	}
	
	/**
	 * Implementaci�n de A estrella
	 */
	public double Algorithm() {
		double initialTime = Double.parseDouble(""+System.currentTimeMillis()); // Para contar el tiempo de ejecuci�n

		Node currentNode = null;

		while(!this.openList.isEmpty()) { 				// Recorremos la lista de nodos sin explorar
			currentNode = this.openList.pullFirst(); 	// Extraemos el primero (la lista esta ordenada segun la funcion de evaluaci�n)
			if(checkNode(currentNode)) {				// Si el nodo ya se ha visitado con un coste menor (esta en la lista de explorados) lo ignoramos
				currentNode.printNodeData(printDebug);
				closedList.add(currentNode); 			// A�adimos dicho nodo a la lista de explorados

				if(this.getGoalNode().equals(currentNode)) {	// Si es el nodo meta hemos acabado y no hace falta continuar
					this.setGoalNode(currentNode);
					this.setFindGoal(true);
					break;
				}
				this.addAdjacentNodes(currentNode); 	// Expandimos el nodo segun las acciones posibles    	
			}
		}
		
		// Para contar el tiempo de ejecuci�n
		double fin    = Double.parseDouble(""+System.currentTimeMillis());
		double tiempo = (fin - initialTime) / 1000;
		return tiempo;
	}


	/**
	 * Constructor del algoritmo, obtiene el nodo de inicio y el nodo meta
	 * NO MODIFICAR
	 * @param initialNode
	 * @param goalNode
	 */
	public AStar(int printDebug, Node initialNode, Node goalNode) { 
		this.printDebug = printDebug;
		this.setInitialNode(initialNode);
		this.setGoalNode(goalNode);
		this.setFindGoal(false); 					// No se ha llegado al nodo meta

		// Introducir heur�sticas y costes para el nodo inicial. El nodo meta solo tiene heur�stica
		initialNode.computeHeuristic(goalNode);	// Coste esperado por la heur�stica para llegar al nodo final desde el inicial
		initialNode.setCoste(0);					// el nodo inicial tiene coste cero
		initialNode.computeEvaluation();			// coste + heur�stica
		goalNode.computeHeuristic(goalNode);		// Debe ser 0, ya es el nodo final

		// Genera la lista de nodos explorados y sin explorar
		this.closedList = new ArrayList<Node>();
		this.openList   = new OpenList();
		this.openList.insertAtEvaluation(initialNode); // A�adimos a la lista de nodos sin explorar el nodo inicial
	}


	/**
	 * Comprobaci�n de si el nodo ya se ha explorado
	 * NO MODIFICAR
	 * @param currentNode
	 * @return
	 */
	private boolean checkNode(Node currentNode) {
		boolean expandirNodo = true;
		for (Node node : this.closedList) { // Se observa si el nodo est� en la lista de cerrados
			if(currentNode.equals(node)) {	// Comprueba si la informaci�n del nodo es igual
				expandirNodo = false;
				break;
			}
		}
		return expandirNodo;				// false en el caso de que el nodo se haya visitado, indicando que no hay que expandirlo
	}


	/**
	 * M�todo para calcular el camino desde el nodo Inicial hasta el nodo actual
	 * NO MODIFICAR
	 * @param currentNode
	 * @return lista de nodos ordenada, desde el primer nodo al �ltimo
	 */
	public List<Node> getPath(Node currentNode) {
		List<Node> path = new ArrayList<Node>();	
		path.add(currentNode);	
		Node parent;
		while ((parent = currentNode.getParent()) != null) {	// Desde el nodo actual, se busca el nodo padre y se insertan 
			path.add(0, parent);								//  dentro de la lista de manera inversa
			currentNode = parent;
		}
		return path;
	}


	/**** Getters y Setters ****/
	/**
	 * MODIFICAR y/o A�ADIR si se considera necesario. No es imprescindible, solo si se considera que puede ayudar a la implementaci�n
	 */
	public Node getInitialNode() {
		return initialNode;
	}
	public void setInitialNode(Node initialNode) {
		this.initialNode = initialNode;
	}
	public boolean isFindGoal() {
		return findGoal;
	}
	public void setFindGoal(boolean findGoal) {
		this.findGoal = findGoal;
	}
	public Node getGoalNode() {
		return goalNode;
	}
	public void setGoalNode(Node goalNode) {
		this.goalNode = goalNode;
	}

}

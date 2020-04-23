package practica.objetos;

/**
 * Clase creada como objeto base para la práctica 2019-2020 de Inteligencia Artificial, UC3M, Colmenarejo
 *
 * @author Daniel Amigo Herrero
 * @author David Sánchez Pedroche
 */

public class Trabajador {

	// Variables del objeto Trabajador
	String nombre;
	int habPodar;
	int habLimpiar;
	int habReparar;
	Herramienta herramienta;
	String area;
	int tiempoOcupado;
	Tarea tarea;
	int tiempoDesp;
	// AÑADIR LAS VARIABLES NECESARIAS

	/**
	 * Constructor para el objeto
	 * NO MODIFICAR LA LLAMADA DEL CONSTRUCTOR
	 */
	public Trabajador(String nombre, int habPodar, int habLimpiar, int habReparar) {
		this.nombre      = nombre;
		this.habPodar    = habPodar;
		this.habLimpiar  = habLimpiar;
		this.habReparar  = habReparar;
		this.herramienta = null;
		this.area = "A";
		this.tiempoOcupado = 0;
		this.tarea = null;
		// Añadir el estado inicial (estático) de las variables que se añadan
		// Si se necesita añadir valores variables, como un ID, utilizar setters
	}
	public void aumentarTiempoTarea() {
		switch(this.herramienta.getTrabajo()) {
			case "limpiar":
				while(this.tarea.getUnidades() > 0) {
				if(this.tarea.getUnidades() >= this.habLimpiar) {
					this.tiempoOcupado += 60;
				}
				else {
					this.tiempoOcupado += (60*this.tarea.getUnidades())/this.getHabLimpiar();
				}
				this.tarea.setUnidades(this.tarea.getUnidades() - this.getHabLimpiar());				
				}
				break;
			case "podar":
				while(this.tarea.getUnidades() > 0) {
				if(this.tarea.getUnidades() >= this.habPodar) {
					this.tiempoOcupado += 60;
				}
				else {
					this.tiempoOcupado += (60*this.tarea.getUnidades())/this.getHabPodar();
				}
				this.tarea.setUnidades(this.tarea.getUnidades() - this.getHabPodar());				
				}
				break;
			case "reparar":
				while(this.tarea.getUnidades() > 0) {
				if(this.tarea.getUnidades() >= this.habReparar) {
					this.tiempoOcupado += 60;
				}
				else {
					this.tiempoOcupado += (60*this.tarea.getUnidades())/this.getHabReparar();
				}
				this.tarea.setUnidades(this.tarea.getUnidades() - this.getHabReparar());				
				}
				break;
				default: 
					break;
		}
	}
	
	public void aumentarTiempoDesp(String origen, String destino) {
		if(destino != "A") {
		switch(origen + destino) {
		case "AR":
			this.tiempoOcupado += 10;
			break;
		case "AJ3":
			this.tiempoOcupado += 5;
			break;
		case "AC2":
			this.tiempoOcupado += 5;
			break;
		case "AJ2":
			this.tiempoOcupado += 5;
			break;
		case "AU":
			this.tiempoOcupado += 10;
			break;
		case "AC1":
			this.tiempoOcupado += 10;
			break;
		case "AJ1":
			this.tiempoOcupado += 10;
			break;
		case "AB":
			this.tiempoOcupado += 15;
			break;
		case "RU":
		case "UR":
			this.tiempoOcupado += 20;
			break;
		case "RC2":
		case "C2R":
			this.tiempoOcupado += 10;
			break;
		case "UC2":
		case "C2U":
			this.tiempoOcupado += 10;
			break;
		case "J1C2":
		case "C2J1":
			this.tiempoOcupado += 10;
			break;
		case "J2C2":
		case "C2J2":
			this.tiempoOcupado += 5;
			break;
		case "J3C2":
		case "C2J3":
			this.tiempoOcupado += 5;
			break;
		case "J2J1":
		case "J1J2":
			this.tiempoOcupado += 5;
			break;
		case "J3J1":
		case "J1J3":
			this.tiempoOcupado += 15;
			break;
		case "J2J3":
		case "J3J2":
			this.tiempoOcupado += 10;
			break;
		case "UB":
		case "BU":
			this.tiempoOcupado += 5;
			break;
		case "UJ1":
		case "J1U":
			this.tiempoOcupado += 5;
			break;
		case "UJ2":
		case "J2U":
			this.tiempoOcupado += 5;
			break;
		case "C2B":
		case "BC2":
			this.tiempoOcupado += 15;
			break;
		case "J1B":
		case "BJ1":
			this.tiempoOcupado += 5;
			break;
		case "J2B":
		case "BJ2":
			this.tiempoOcupado += 10;
			break;
		default:
			break;
		}
		}
	}
	
	public void aumentarTiempoDespA() {
		switch(this.area) {
			case "R":
				this.tiempoOcupado += 10;
				break;
			case "J3":
				this.tiempoOcupado += 5;
				break;
			case "C2":
				this.tiempoOcupado += 5;
				break;
			case "J2":
				this.tiempoOcupado += 5;
				break;
			case "U":
				this.tiempoOcupado += 10;
				break;
			case "C1":
				this.tiempoOcupado += 10;
				break;
			case "J1":
				this.tiempoOcupado += 10;
				break;
			case "B":
				this.tiempoOcupado += 15;
				break;
			default:
				this.tiempoOcupado += 0;
				break;
		}		
	}
	
	/**
	 * Añadir (si procede) métodos auxiliares, como getters o setters
	 */
	public void printTrabajador() {
        System.out.println(this.nombre + " " + this.tiempoOcupado + " mins " + 
    this.herramienta.getNombre() + " " + this.herramienta.getTrabajo() + " " + this.area);
    }
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getHabPodar() {
		return habPodar;
	}
	public void setHabPodar(int habPodar) {
		this.habPodar = habPodar;
	}
	public int getHabLimpiar() {
		return habLimpiar;
	}
	public void setHabLimpiar(int habLimpiar) {
		this.habLimpiar = habLimpiar;
	}
	public int getHabReparar() {
		return habReparar;
	}
	public void setHabReparar(int habReparar) {
		this.habReparar = habReparar;
	}
	public Herramienta getHerramienta() {
		return herramienta;
	}
	public void setHerramienta(Herramienta herramienta) {
		this.herramienta = herramienta;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public int getTiempoOcupado() {
		return tiempoOcupado;
	}
	public void setTiempoOcupado(int tiempoOcupado) {
		this.tiempoOcupado = tiempoOcupado;
	}
	public Tarea getTarea() {
		return tarea;
	}
	public void setTarea(Tarea tarea) {
		this.tarea = tarea;
	}
}

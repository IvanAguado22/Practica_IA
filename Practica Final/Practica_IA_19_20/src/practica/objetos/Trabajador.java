package practica.objetos;
import java.util.concurrent.TimeUnit;

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
	double tiempoOcupado;
	Tarea tarea;
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
	
	/**
	 * Añadir (si procede) métodos auxiliares, como getters o setters
	 */
	
	public void aumentarTiempoTarea() {
		switch(this.herramienta.getTrabajo()) {
			case "limpiar":
				while(this.tarea.getUnidades() > 0) {
				if(this.tarea.getUnidades() >= (this.habLimpiar + this.herramienta.getMejora() )) {
					this.tiempoOcupado += 60;
				}
				else {
					this.tiempoOcupado += (60*this.tarea.getUnidades()) / (this.habLimpiar + this.herramienta.getMejora());
				}
				this.tarea.setUnidades(this.tarea.getUnidades() - this.habLimpiar - this.herramienta.getMejora());				
				}
				break;
			case "podar":
				while(this.tarea.getUnidades() > 0) {
				if(this.tarea.getUnidades() >= (this.habPodar + this.herramienta.getMejora() )) {
					this.tiempoOcupado += 60;
				}
				else {
					this.tiempoOcupado += (60*this.tarea.getUnidades())/ (this.habPodar + this.herramienta.getMejora());
				}
				this.tarea.setUnidades(this.tarea.getUnidades() - this.habPodar -  this.herramienta.getMejora());				
				}
				break;
			case "reparar":
				while(this.tarea.getUnidades() > 0) {
				if(this.tarea.getUnidades() >= (this.habReparar + this.herramienta.getMejora() )) {
					this.tiempoOcupado += 60;
				}
				else {
					this.tiempoOcupado += (60*this.tarea.getUnidades()) / (this.habReparar + this.herramienta.getMejora());
				}
				this.tarea.setUnidades(this.tarea.getUnidades() - this.habReparar -  this.herramienta.getMejora());				
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
			this.tiempoOcupado += 10 + (this.herramienta.getPeso() * 2);
			break;
		case "AJ3":
			this.tiempoOcupado += 5 + this.herramienta.getPeso();
			break;
		case "AC2":
			this.tiempoOcupado += 5 + this.herramienta.getPeso();
			break;
		case "AJ2":
			this.tiempoOcupado += 5 + this.herramienta.getPeso();
			break;
		case "AU":
			this.tiempoOcupado += 10 + (this.herramienta.getPeso() * 2);
			break;
		case "AC1":
			this.tiempoOcupado += 10 + (this.herramienta.getPeso() * 2);
			break;
		case "AJ1":
			this.tiempoOcupado += 10 + (this.herramienta.getPeso() * 2);
			break;
		case "AB":
			this.tiempoOcupado += 15 + (this.herramienta.getPeso() * 3);
			break;
		case "RU":
		case "UR":
			this.tiempoOcupado += 20 + (this.herramienta.getPeso() * 4);
			break;
		case "RC2":
		case "C2R":
			this.tiempoOcupado += 10 + (this.herramienta.getPeso() * 2);
			break;
		case "UC2":
		case "C2U":
			this.tiempoOcupado += 10 + (this.herramienta.getPeso() * 2);
			break;
		case "J1C2":
		case "C2J1":
			this.tiempoOcupado += 10 + (this.herramienta.getPeso() * 2);
			break;
		case "J2C2":
		case "C2J2":
			this.tiempoOcupado += 5 + this.herramienta.getPeso();
			break;
		case "J3C2":
		case "C2J3":
			this.tiempoOcupado += 5 + this.herramienta.getPeso();
			break;
		case "J2J1":
		case "J1J2":
			this.tiempoOcupado += 5 + this.herramienta.getPeso();
			break;
		case "J3J1":
		case "J1J3":
			this.tiempoOcupado += 15 + (this.herramienta.getPeso() * 3);
			break;
		case "J2J3":
		case "J3J2":
			this.tiempoOcupado += 10 + (this.herramienta.getPeso() * 2);
			break;
		case "UB":
		case "BU":
			this.tiempoOcupado += 5 + this.herramienta.getPeso();
			break;
		case "UJ1":
		case "J1U":
			this.tiempoOcupado += 5 + this.herramienta.getPeso();
			break;
		case "UJ2":
		case "J2U":
			this.tiempoOcupado += 5 + this.herramienta.getPeso();
			break;
		case "C2B":
		case "BC2":
			this.tiempoOcupado += 15 + (this.herramienta.getPeso() * 3);
			break;
		case "J1B":
		case "BJ1":
			this.tiempoOcupado += 5 + this.herramienta.getPeso();
			break;
		case "J2B":
		case "BJ2":
			this.tiempoOcupado += 10 + (this.herramienta.getPeso() * 2);
			break;
		default:
			break;
		}
		}
	}
	
	public void aumentarTiempoDespA() {
		switch(this.area) {
			case "R":
				this.tiempoOcupado += 10 + (this.herramienta.getPeso() * 2);
				break;
			case "J3":
				this.tiempoOcupado += 5 + this.herramienta.getPeso();
				break;
			case "C2":
				this.tiempoOcupado += 5 + this.herramienta.getPeso();
				break;
			case "J2":
				this.tiempoOcupado += 5 + this.herramienta.getPeso();
				break;
			case "U":
				this.tiempoOcupado += 10 + (this.herramienta.getPeso() * 2);
				break;
			case "C1":
				this.tiempoOcupado += 10 + (this.herramienta.getPeso() * 2);
				break;
			case "J1":
				this.tiempoOcupado += 10 + (this.herramienta.getPeso() * 2);
				break;
			case "B":
				this.tiempoOcupado += 15 + (this.herramienta.getPeso() * 3);
				break;
			default:
				this.tiempoOcupado += 0;
				break;
		}		
	}
	
	public double DesplazarseHaciaA() {
		double coste = 0;
		switch(this.area) {
			case "R":
				coste = 10 + (this.herramienta.getPeso() * 2);
				break;
			case "J3":
				coste = 5 + this.herramienta.getPeso();
				break;
			case "C2":
				coste = 5 + this.herramienta.getPeso();
				break;
			case "J2":
				coste = 5 + this.herramienta.getPeso();
				break;
			case "U":
				coste =  10 + (this.herramienta.getPeso() * 2);
				break;
			case "C1":
				coste =  10 + (this.herramienta.getPeso() * 2);
				break;
			case "J1":
				coste =  10 + (this.herramienta.getPeso() * 2);
				break;
			case "B":
				coste = 15 + (this.herramienta.getPeso() * 3);	
				break;
			default:
				break;
		}
		this.tiempoOcupado += coste;
		this.setArea("A");
		return coste;
	}
	
	public double Desplazarse(String origen, String destino) {
		double coste = 0;
		if(destino != "A") {
			switch(origen + destino) {
			case "AR":
				coste = 10 + (this.herramienta.getPeso() * 2);
				break;
			case "AJ3":
				coste = 5 + this.herramienta.getPeso();
				break;
			case "AC2":
				coste = 5 + this.herramienta.getPeso();
				break;
			case "AJ2":
				coste = 5 + this.herramienta.getPeso();
				break;
			case "AU":
				coste = 10 + (this.herramienta.getPeso() * 2);
				break;
			case "AC1":
				coste = 10 + (this.herramienta.getPeso() * 2);
				break;
			case "AJ1":
				coste = 10 + (this.herramienta.getPeso() * 2);
				break;
			case "AB":
				coste = 15 + (this.herramienta.getPeso() * 3);
				break;
			case "UR":
				coste = 20 + (this.herramienta.getPeso() * 4);
				break;
			case "C2R":
				coste = 10 + (this.herramienta.getPeso() * 2);
				break;
			case "C2U":
				coste = 10 + (this.herramienta.getPeso() * 2);
				break;
			case "C2J1":
				coste = 10 + (this.herramienta.getPeso() * 2);
				break;
			case "C2J2":
				coste = 5 + this.herramienta.getPeso();
				break;
			case "C2J3":
				coste = 5 + this.herramienta.getPeso();
				break;
			case "J1J2":
				coste = 5 + this.herramienta.getPeso();
				break;
			case "J1J3":
				coste = 15 + (this.herramienta.getPeso() * 3);
				break;
			case "J3J2":
				coste = 10 + (this.herramienta.getPeso() * 2);
				break;
			case "BU":
				coste = 5 + this.herramienta.getPeso();
				break;
			case "J1U":
				coste = 5 + this.herramienta.getPeso();
				break;
			case "J2U":
				coste = 5 + this.herramienta.getPeso();
				break;
			case "BC2":
				coste = 15 + (this.herramienta.getPeso() * 3);
				break;
			case "BJ1":
				coste = 5 + this.herramienta.getPeso();
				break;
			case "BJ2":
				coste = 10 + (this.herramienta.getPeso() * 2);
				break;
			default:
				break;
			}
		}
		this.tiempoOcupado += coste;
		this.setArea(destino);
		return coste;
	}
	
	public double TiempoEnDesplazarse(String origen, String destino) {
		double coste = 0;
		if(destino != "A") {
			switch(origen + destino) {
			case "AR":
				coste = 10 + (this.herramienta.getPeso() * 2);
				break;
			case "AJ3":
				coste = 5 + this.herramienta.getPeso();
				break;
			case "AC2":
				coste = 5 + this.herramienta.getPeso();
				break;
			case "AJ2":
				coste = 5 + this.herramienta.getPeso();
				break;
			case "AU":
				coste = 10 + (this.herramienta.getPeso() * 2);
				break;
			case "AC1":
				coste = 10 + (this.herramienta.getPeso() * 2);
				break;
			case "AJ1":
				coste = 10 + (this.herramienta.getPeso() * 2);
				break;
			case "AB":
				coste = 15 + (this.herramienta.getPeso() * 3);
				break;
			case "UR":
				coste = 20 + (this.herramienta.getPeso() * 4);
				break;
			case "C2R":
				coste = 10 + (this.herramienta.getPeso() * 2);
				break;
			case "C2U":
				coste = 10 + (this.herramienta.getPeso() * 2);
				break;
			case "C2J1":
				coste = 10 + (this.herramienta.getPeso() * 2);
				break;
			case "C2J2":
				coste = 5 + this.herramienta.getPeso();
				break;
			case "C2J3":
				coste = 5 + this.herramienta.getPeso();
				break;
			case "J1J2":
				coste = 5 + this.herramienta.getPeso();
				break;
			case "J1J3":
				coste = 15 + (this.herramienta.getPeso() * 3);
				break;
			case "J3J2":
				coste = 10 + (this.herramienta.getPeso() * 2);
				break;
			case "BU":
				coste = 5 + this.herramienta.getPeso();
				break;
			case "J1U":
				coste = 5 + this.herramienta.getPeso();
				break;
			case "J2U":
				coste = 5 + this.herramienta.getPeso();
				break;
			case "BC2":
				coste = 15 + (this.herramienta.getPeso() * 3);
				break;
			case "BJ1":
				coste = 5 + this.herramienta.getPeso();
				break;
			case "BJ2":
				coste = 10 + (this.herramienta.getPeso() * 2);
				break;
			default:
				break;
			}
		}
		return coste;
	}
	
	public double RealizarTarea() {
		double coste = 0;
		switch(this.herramienta.getTrabajo()) {
			case "limpiar":
				while(this.tarea.getUnidades() > 0) {
					if(this.tarea.getUnidades() >= (this.habLimpiar + this.herramienta.getMejora() )) {
						coste += 60;
					}else {
						coste += (60*this.tarea.getUnidades()) / (this.habLimpiar + this.herramienta.getMejora());
					}
					this.tarea.setUnidades(this.tarea.getUnidades() - this.habLimpiar - this.herramienta.getMejora());				
				}
				break;
			case "podar":
				while(this.tarea.getUnidades() > 0) {
					if(this.tarea.getUnidades() >= (this.habPodar + this.herramienta.getMejora() )) {
						coste += 60;
					}else {
						coste += (60*this.tarea.getUnidades())/ (this.habPodar + this.herramienta.getMejora());
					}
					this.tarea.setUnidades(this.tarea.getUnidades() - this.habPodar - this.herramienta.getMejora());			
				}
				break;
			case "reparar":
				while(this.tarea.getUnidades() > 0) {
					if(this.tarea.getUnidades() >= (this.habReparar + this.herramienta.getMejora() )) {
						coste += 60;
					}else {
						coste += (60*this.tarea.getUnidades()) / (this.habReparar + this.herramienta.getMejora());
					}
					this.tarea.setUnidades(this.tarea.getUnidades() - this.habReparar - this.herramienta.getMejora());				
				}
				break;
			default: 
				break;
		}
		this.tiempoOcupado += coste;
		if(this.tarea.getUnidades() < 0) {
			this.tarea.setUnidades(0);
		}
		return coste;
	}
	
	public void cogerHerramienta(Herramienta herramienta) {
		setHerramienta(herramienta);
		herramienta.setEquipada(true);
	}
	
	public void dejarHerramienta() {
		this.herramienta.setEquipada(false);
		setHerramienta(null);
	}

	/*public String formatearMinutosAHoraMinuto(int minutos) {
	    String formato = "%02d:%02d";
	    long horasReales = TimeUnit.MINUTES.toHours(minutos);
	    long minutosReales = TimeUnit.MINUTES.toMinutes(minutos) - TimeUnit.HOURS.toMinutes(TimeUnit.MINUTES.toHours(minutos));
	    return String.format(formato, horasReales, minutosReales);
	}*/
	
	public void printTrabajador() {
        System.out.println(this.nombre + " " + this.tiempoOcupado + " mins " + 
    this.herramienta.getNombre() + " " + this.herramienta.getTrabajo() + " " + this.area);
    }
	
	/*public void printTiempoHoras() {
		System.out.println(this.nombre + " " + "ha trabajado" + " " + formatearMinutosAHoraMinuto(this.tiempoOcupado) + " " + "horas");
	}*/
	
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
	public double getTiempoOcupado() {
		return tiempoOcupado;
	}
	public void setTiempoOcupado(double tiempoOcupado) {
		this.tiempoOcupado = tiempoOcupado;
	}
	public Tarea getTarea() {
		return tarea;
	}
	public void setTarea(Tarea tarea) {
		this.tarea = tarea;
	}
}

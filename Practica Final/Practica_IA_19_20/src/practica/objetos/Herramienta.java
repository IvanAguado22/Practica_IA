package practica.objetos;

/**
 * Clase creada como objeto base para la pr?ctica 2019-2020 de Inteligencia Artificial, UC3M, Colmenarejo
 *
 * @author Daniel Amigo Herrero
 * @author David S?nchez Pedroche
 */

public class Herramienta {

	// Variables del objeto Herramienta
	String nombre;
	String trabajo;
	double peso;
	int mejora;
	int cantidad;
	// A?ADIR LAS VARIABLES NECESARIAS
	boolean equipada;
	int id;
	String area;

	/**
	 * Constructor para el objeto
	 * NO MODIFICAR LA LLAMADA DEL CONSTRUCTOR
	 */
	public Herramienta(String nombre, String trabajo, double peso, int mejora, int cantidad) {
		this.nombre = nombre;
		this.trabajo = trabajo;
		this.peso = peso;
		this.mejora = mejora;
		this.cantidad = cantidad;
		// A?adir el estado inicial (est?tico) de las variables que se a?adan
		// Si se necesita a?adir valores variables, como un ID, utilizar setters
		this.equipada = false;
		this.area = "A";
	}
	
	public Herramienta(Herramienta original) {
		if(original != null) {
			this.nombre = original.getNombre();
			this.trabajo = original.getTrabajo();
			this.peso = original.getPeso();
			this.mejora = original.getMejora();
			this.cantidad = original.getCantidad();
			this.area = original.getArea();
		}
	}

	/**
	 * M?todos getters y setters
	 */
	// A?adir (si procede) m?todos auxiliares, como getters o setters

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTrabajo() {
		return trabajo;
	}
	public void setTrabajo(String trabajo) {
		this.trabajo = trabajo;
	}
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	public int getMejora() {
		return mejora;
	}
	public void setMejora(int mejora) {
		this.mejora = mejora;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}	
	public boolean getEquipada() {
		return equipada;
	}
	public void setEquipada(boolean valor) {
		this.equipada = valor;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}

}

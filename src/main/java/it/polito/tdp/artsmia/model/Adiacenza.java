package it.polito.tdp.artsmia.model;

public class Adiacenza {

	//CLASSE CREATA PER USARE L'APPROCCIO 3 E PER ESSERE L UNICO MODO CHE VA PER ESEGUIRE LA CONSEGNA
	private int id1;
	private int id2;
	private int peso;
	
	public Adiacenza(int id1, int id2, int peso) {
		super();
		this.id1 = id1;
		this.id2 = id2;
		this.peso = peso;
	}
	
	public int getId1() {
		return id1;
	}
	public void setId1(int id1) {
		this.id1 = id1;
	}
	public int getId2() {
		return id2;
	}
	public void setId2(int id2) {
		this.id2 = id2;
	}
	public int getPeso() {
		return peso;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}
	// NON SERVONO HASHCODE E EQUALS O ANCHE IL TOSTRING  NON è UN JAVA BEAN MA SOLO UNA CLASSE CHE CI AIUTA CON IL METODO DEL DAO
	
}

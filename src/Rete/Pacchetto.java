package Rete;

import Nodo.Nodo;
import Eccezzioni.LoopException;

import java.util.ArrayList;

public class Pacchetto implements Comparable<Pacchetto> {
	
	//Peso percorso
	private int peso;

	//Numero massimo hop (TTL)
	private int TTL;
	
	//Lista di nodi attraversati dal pacchetto
	private ArrayList<Nodo> nodi = new ArrayList<Nodo>();

	
	//Costruttore
	public Pacchetto() {
		this.peso = 0;
		this.TTL = 0;
	}
	
	//Costruttore con specificzione TTL
	public Pacchetto(int TTL) {
		this.peso = 0;
		this.TTL = TTL;
	}
	
	//Costruttore copia
	public Pacchetto(Pacchetto p) {
		this.TTL = p.getTTL(); 
		this.peso = p.getPeso();
		this.nodi = p.getNodi();
	}
	

	/*
		Metodo per aggiungere un nodo al pacchetto solo se
		questo non è gia presente al suo interno
	*/
	public void aggiungiNodo(Nodo n) throws LoopException {

		for(Nodo t : nodi){
			if(t.getNome().equals(n.getNome())){
				throw new LoopException();
			}
		}

		nodi.add(n);

	}
	
	
	//Metodo toString
	public String toString() {
		return "Rete.Pacchetto [peso=" + peso + ", TTL=" + TTL + ", nodi=" + nodi + "]";
	}
	
	
	/*
		Questa funzione azzera il contenuto
		del pacchetto in modo tale che esso possa
		essere inviato nuovamente sulla rete
	*/
	public void azzeraPacchetto() {
		peso = 0;
		nodi = new ArrayList<Nodo>();
	}

	/*
		Il metodo compare to serve a ordinare i pacchetti
		in modo da mettere quelli con peso minore prima
	*/
	public int compareTo(Pacchetto p) {
		if(peso < p.getPeso()) {return -1;}
		if(peso > p.getPeso()) {return +1;}
		else return 0;
	}
	
	/*
	 	Questo metodo serve a rimuovere l'ultimo
	 	nodo dall'array dei nodi da cui il pacchetto
	 	è passato
	*/
	public void rimuoviUltimoNodo() {
		try {
			nodi.remove(nodi.size()-1);
		} catch (Exception e) {
			
		}
	}
	
	//Metodo aggiunta peso
	public void aggiungiPeso(int p) {
		peso = peso + p;
	}
	
	//Metodo per decrementare il TTL
	public void decrementaTTL() {
		TTL = TTL - 1;
	}
	
	//Metodi getters/setters
	public int getTTL() {
		return TTL;
	}
	
	public int getPeso() {
		return peso;
	}

	public void setTTL(int tTL) {
		TTL = tTL;
	}

	public ArrayList<Nodo> getNodi() {
		return nodi;
	}
		
}

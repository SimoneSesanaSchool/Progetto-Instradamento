package Classi.Nodo;

import javafx.scene.shape.Line;

/*
  La classe collegamento definisce un collegamento
  fra due nodi. Gli attributi di questa classe sono
  i due nodi e il peso del collegamento
*/
public class Collegamento{

    //Attributi
    private int peso;
    private Nodo nodoCollegato;

    //Rappresentazione grafica del collegamento
    private Line linea;


    //Costruttore
    public Collegamento(int peso, Nodo nodoCollegato){
      this.peso = peso;
      this.nodoCollegato = nodoCollegato;
    }

    //Costruttore
    public Collegamento(int peso, Nodo nodoCollegato, Line linea){
        this.peso = peso;
        this.linea = linea;
        this.nodoCollegato = nodoCollegato;
    }

    //Metodi getters/setters
    public Nodo getNodoCollegato(){
      return nodoCollegato;
    }
    
    public int getPeso() {
    	return peso;
    }

    public Line getLinea() {
        return linea;
    }

    public void setLinea(Line linea) {
        this.linea = linea;
    }

    //toString
    public String toString(){
      return ("Peso: " + peso + " - NodoCollegato: " + nodoCollegato.getNome());
    }
  
}
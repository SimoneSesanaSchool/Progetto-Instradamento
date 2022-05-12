package Classi.Nodo;

/*
  La classe collegamento definisce un collegamento
  fra due nodi. Gli attributi di questa classe sono
  i due nodi e il peso del collegamento
*/
public class Collegamento{

    //Attributi
    private int peso;
    private Nodo nodoCollegato;

    //Costruttore
    public Collegamento(int peso, Nodo nodoCollegato){
      this.peso = peso;
      this.nodoCollegato = nodoCollegato;
    }

    //Get nodo
    public Nodo getNodoCollegato(){
      return nodoCollegato;
    }
    
    //Get peso
    public int getPeso() {
    	return peso;
    }

    //toString
    public String toString(){
      return ("Peso: " + peso + " - NodoCollegato: " + nodoCollegato.getNome());
    }
  
}
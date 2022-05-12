package Classi.Nodo;

import Classi.Eccezzioni.LoopException;
import Classi.Rete.Pacchetto;

import java.util.ArrayList;


/*
  La classe nodo rappresenta un nodo della rete
  caratterizzato da un nome
*/
public class Nodo{

  //Nome del nodo
  private String nome;

  //Nodi collegati
  private ArrayList<Collegamento> collegamenti = new ArrayList<Collegamento>();


  //Costruttore
  public Nodo(String nome){
    this.nome = nome;
  }


  //Metodo aggiunta collegamento
  public void aggiungiCollegamento(Collegamento c){
    collegamenti.add(c);
  }


  //Metodi getters/setters
  public String getNome(){
    return nome;
  }


  public void setNome(String nome){
    this.nome = nome;
  }


  //Metodo toString
  public String toString(){
    return "Nome nodo: " + nome;
  }


  //Ricerca percorso minore da un nodo all'altro
  public void ricercaPercorsoMinore(String nomeNodo, Pacchetto p, ArrayList<Pacchetto> pacchetti){

    /*
       Questa funzione controlla tutti i nodi a cui il nodo
       è collegato. Se trova una corrispondenza con il nodo cercato
       allora aggiunge il percorso trovato alla lista dei percorsi
       possibili verso il nodo. Se non viene trovata alcuna corrispondenza
       la funzione viene rilanciata sul primo nodo collegato all'interno
       della lista "collegamenti"
    */
    for(int i = 0; i < collegamenti.size(); i++){	

        /*
          Se il pacchetto è già passato dal nodo il ciclo viene
          interrotto in quanto viene rilevata la presenza di un
          loop
        */
    	try{
          p.aggiungiNodo(this);
        } catch (LoopException e){
          break;
        }
     
        //System.out.println(p);
        if(collegamenti.get(i).getNodoCollegato().getNome().equals(nomeNodo)){
	      	
	      p.aggiungiPeso(collegamenti.get(i).getPeso());

          /*
            Non c'è bisogno di gestire l'eccezione in quanto siamo
            sicuro che questo nodo non sia presente nel percorso
            del pacchetto
          */
          try {
            p.aggiungiNodo(collegamenti.get(i).getNodoCollegato());
          } catch (Exception e){}


	      /*
	      	La copia del pacchetto viene aggiunta alla lista
	      	dei percorsi possibili verso il nodo
	      */
	      pacchetti.add(new Pacchetto(p));
	      		          
          //Il pacchetto viene azzerato e rimandato sulla rete
          p.azzeraPacchetto();
      	
      	
        } else {
          p.aggiungiPeso(collegamenti.get(i).getPeso());
          collegamenti.get(i).getNodoCollegato().ricercaPercorsoMinore(nomeNodo, p, pacchetti);
        }
        
        /*
      		L'ultimo nodo viene rimosso dal pacchetto
        */
        p.rimuoviUltimoNodo();
        
      }
    	
  }

  //Metodo stampa collegamenti
  public void stampaCollegamenti(){
    for(int i = 0; i < collegamenti.size(); i++){
      System.out.println(collegamenti.get(i));
    }
  }
  
}
package Classi.Rete;

import Classi.Eccezzioni.CollegamentoGiaEsistenteException;
import Classi.Eccezzioni.NodoInesistenteException;
import Classi.Nodo.Collegamento;
import Classi.Nodo.Nodo;

import java.util.ArrayList;

/*
  Una rete � costituita da un'insieme di
  collegamenti e nodi
*/
public class Rete{


    //Lista nodi
    private ArrayList<Nodo> nodi = new ArrayList<Nodo>();

    /*
      Metodo per calcolare il percorso minore
      da un nodo all'altro
    */
    public void calcolaPercorsoMinore(String nomeNodo1, String nomeNodo2, Pacchetto p) throws NodoInesistenteException {

        /*
            Questo arraylist memorizza un insieme di pacchetti
            e viene passato alla funzione per la ricerca del
            percorso minore. La funzione riempie l'arraylist con
            l'insieme di tutti i percorsi possibili verso il nodo
            specificato
        */
        ArrayList<Pacchetto> pacchetti = new ArrayList<Pacchetto>();

        this.ricercaNodo(nomeNodo1).ricercaPercorsoMinore(nomeNodo2, p, pacchetti);

        for(int i = 0; i < pacchetti.size(); i++) {
             System.out.println(pacchetti.get(i));
        }

    }


    //Ricerca nodo dato il suo codice
    public Nodo ricercaNodo(String nome) throws NodoInesistenteException {

        for(Nodo n : nodi){
            if(n.getNome().equals(nome)){
                return n;
            }
        }

        //Se non viene trovato si ritorna null
        throw new NodoInesistenteException();

    }


    //Aggiunta nodo
    public void aggiungiNodo(Nodo n){
      nodi.add(n);
    }


    //Aggiunta collegameto da un nodo all'altro dati i  loro nomi
    public void aggiungiCollegamento(String nodo1, String nodo2, int peso) throws NodoInesistenteException, CollegamentoGiaEsistenteException {

        Nodo n1, n2;

        //Ricerca dei due nodi
        n1 = this.ricercaNodo(nodo1);
        n2 = this.ricercaNodo(nodo2);

        //Aggiunta del collegamento
        n1.aggiungiCollegamento(new Collegamento(peso, n2));

    }


    //Metodo che restituisce un ArrayList contenente i nodi della rete
    public ArrayList<Nodo> getNodi(){
      return nodi;
    }

}
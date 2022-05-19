package Classi.Rete;

import Classi.Eccezioni.CollegamentoGiaEsistenteException;
import Classi.Eccezioni.NodoInesistenteException;
import Classi.Nodo.Collegamento;
import Classi.Nodo.Nodo;
import javafx.scene.control.TextArea;
import javafx.scene.shape.Line;

import javax.xml.soap.Text;
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
    public ArrayList<Pacchetto> calcolaPercorsoMinore(String nomeNodo1, String nomeNodo2, Pacchetto p, TextArea log) throws NodoInesistenteException {

        /*
            Questo arraylist memorizza un insieme di pacchetti
            e viene passato alla funzione per la ricerca del
            percorso minore. La funzione riempie l'arraylist con
            l'insieme di tutti i percorsi possibili verso il nodo
            specificato
        */
        ArrayList<Pacchetto> pacchetti = new ArrayList<Pacchetto>();

        this.ricercaNodo(nomeNodo1).ricercaPercorsoMinore(nomeNodo2, p, pacchetti, log);

        return pacchetti;

    }


    /*
        Metodo per il calcolo dell'albero dei cammini minimi
    */
    public ArrayList<Pacchetto> calcolaGrafoCamminiMinimi(String nodoPartenza, TextArea log) {

        //Lista percorsi minimi dal nodo di partenza
        ArrayList<Pacchetto> percorsiMinimi = new ArrayList<Pacchetto>();

        for(Nodo n : nodi){
            if(!n.getNome().equals(nodoPartenza)){
                try{

                    ArrayList<Pacchetto> t = calcolaPercorsoMinore(nodoPartenza, n.getNome(), new Pacchetto(20), log);
                    Pacchetto minore = t.get(0);

                    //Si cerca il percorso minore
                    for(Pacchetto p : t){
                        if(p.getPeso() < minore.getPeso()){
                            minore = p;
                        }
                    }
                    percorsiMinimi.add(minore);

                } catch (Exception e){}
            }
        }

        return percorsiMinimi;

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

    //Rimozione nodo
    public void rimuoviNodo(String nome){

        //Il nodo viene rimosso dai collegamenti
        for(Nodo n : nodi){
            for(int i = 0; i < n.getCollegamenti().size(); i++){
                if(n.getCollegamenti().get(i).getNodoCollegato().getNome().equals(nome)){
                    n.getCollegamenti().remove(i);
                }
            }
        }

        //Il nodo viene rimosso dalla rete
        for(int i = 0; i < nodi.size(); i++){
            if(nodi.get(i).getNome().equals(nome)){
                nodi.remove(i);
            }
        }

    }


    //Aggiunta collegameto da un nodo all'altro dati i  loro nomi
    public void aggiungiCollegamento(String nodo1, String nodo2, int peso, Line linea) throws NodoInesistenteException, CollegamentoGiaEsistenteException {

        //Aggiunta dei collegamenti fra i due nodi
        this.ricercaNodo(nodo1).aggiungiCollegamento(new Collegamento(peso, this.ricercaNodo(nodo2), linea));

    }

    //Metodi getters/setters
    public ArrayList<Nodo> getNodi() {
        return nodi;
    }

}
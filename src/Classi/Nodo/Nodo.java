package Classi.Nodo;

import Classi.Eccezioni.CollegamentoGiaEsistenteException;
import Classi.Eccezioni.LoopException;
import Classi.Rete.Pacchetto;
import javafx.scene.control.TextArea;
import javafx.scene.shape.Line;
import java.util.ArrayList;


/*
  La classe nodo rappresenta un nodo della rete
  caratterizzato da un nome
*/
public class Nodo {

  //Nome del nodo
  private String nome;

  //Nodi collegati
  private ArrayList<Collegamento> collegamenti = new ArrayList<Collegamento>();

  //Linee che partono dal nodo nell'interfaccia
  private ArrayList<Line> collegamentiGrafici = new ArrayList<Line>();

  //Costruttore
  public Nodo(String nome){
    this.nome = nome;
  }


  //Metodo aggiunta collegamento
  public void aggiungiCollegamento(Collegamento c) throws CollegamentoGiaEsistenteException {

    //Controllo che il collegamento non sia gà presente
    for(Collegamento t : collegamenti){
      if(t.getNodoCollegato().getNome().equals(c.getNodoCollegato().getNome())){
        throw new CollegamentoGiaEsistenteException();
      }
    }

    //Controllo che non si tenti di collegare il nodo a se stesso
    if(c.getNodoCollegato().getNome().equals(this.nome)){
      throw new CollegamentoGiaEsistenteException();
    }

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
    return nome;
  }


  //Ricerca percorso minore da un nodo all'altro
  public void ricercaPercorsoMinore(String nomeNodo, Pacchetto p, ArrayList<Pacchetto> pacchetti, TextArea log){

    /*
      Il nodo in cui ci si trova viene aggiunto al pacchetto
      purché questo non sia già presente, successivamente il
      TTL viene decrementato
    */
    try{
      p.aggiungiNodo(this);
      log.setText(log.getText() + "Pacchetto passato per: " + this.getNome() + "\n");
    } catch (Exception e){
      return;
    }
    p.decrementaTTL();

    /*
      Se il nodo in cui il pacchetto si trova corrisponde al nodo
      di arrivo il pacchetto viene aggiunto alla lista dei percorsi
      possibili verso il nodo
    */
    if(this.nome.equals(nomeNodo)){
      pacchetti.add(new Pacchetto(p));
      log.setText(log.getText() + "Percorso trovato: \n" +
              " -Peso percorso: " + p.getPeso() + "\n -TTL: " + p.getTTL() +
              "\n -Nodi: " + p.getNodi() + "\n\n");
      p.incrementaTTL();
      p.rimuoviUltimoNodo();
      return;
    }

    /*
      Viene controllato il TTL, e se questo è uguale a 0 il pacchetto
      viene rispedito indietro
    */
    if(p.getTTL() == 0){
      p.incrementaTTL();
      p.rimuoviUltimoNodo();
      return;
    }

    /*
      Se il nodo non è collegato a nulla il pacchetto viene rimandato
      indietro per trovare un altro percorso
    */
    if(this.collegamenti.isEmpty()){
      p.incrementaTTL();
      p.rimuoviUltimoNodo();
      return;
    }

    /*
      In caso nessuna delle condizioni precedenti si verifichi la ricerca
      continua nei nodi collegati al pacchetto
    */
    for(int i = 0; i < collegamenti.size(); i++){
      collegamenti.get(i).getNodoCollegato().ricercaPercorsoMinore(nomeNodo, p, pacchetti, log);
    }
    p.incrementaTTL();
    p.rimuoviUltimoNodo();

  }


  /*
    Metodo per restituire il peso di un collegamento diretto da un
    nodo all'altro
  */
  public int getPesoCollegamento(String nodo2){
    for(Collegamento c : collegamenti){
      if(c.getNodoCollegato().getNome().equals(nodo2)){
        return c.getPeso();
      }
    }
    return 0;
  }

  //Aggiunta di un collegamento grafico
  public void aggiungiCollegamentoGrafico(Line l){
    collegamentiGrafici.add(l);
  }

  //Aggiunta di un collegamento grafico
  public void rimuoviCollegamentoGrafico(Line l){
    collegamentiGrafici.remove(l);
  }

  //Metodi getters/setters
  public ArrayList<Collegamento> getCollegamenti() {
    return collegamenti;
  }


}
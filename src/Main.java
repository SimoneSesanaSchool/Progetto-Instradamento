import Eccezzioni.NodoInesistenteException;
import Rete.Pacchetto;
import Nodo.Nodo;
import Rete.Rete;

import java.util.ArrayList;
import java.util.Scanner;

class Main {

  //Rete
  public static Rete rete = new Rete();

  public static Scanner in = new Scanner(System.in);

  //Stampa menu
  public static void StampaMenu(){

    System.out.println("Menu: ");
    System.out.println("   1. Aggiungi nodo");
    System.out.println("   2. Aggiungi collegamento");
    System.out.println("   3. Calcola percorso minore fra due nodi");
    System.out.println("   4. Calcola labero cammini minimi");
    System.out.println("   5. Esci");

  }

  //1.AGGIUNGI NODO
  public static void aggiundiNodo(){

    String nome;

    System.out.print("Inserire nome nodo: ");
    nome = in.next();

    rete.aggiungiNodo(new Nodo(nome));

  }


  //2.AGGIUNGI COLLEGAMENTO
  public static void aggiungiCollegamento(){

    int peso;
    String nome1, nome2;

    System.out.print("Inserire nome nodo di partenza: ");
    nome1 = in.next();
    System.out.print("Inserire nome nodo di arrivo: ");
    nome2 = in.next();
    System.out.print("Inserire peso collegamento: ");
    peso = in.nextInt();

    try {
      rete.aggiungiCollegamento(nome1, nome2, peso);
    } catch (NodoInesistenteException e){
      System.out.println(e.getMessage());
    }

  }


  //3.CALCOLO PERCORSO MINIMO FRA DUE NODI
  public static void calcoloPercorsoMinimoFraNodi(){

    String nome1, nome2;
    System.out.print("Inserire nome nodo di partenza: ");
    nome1 = in.next();
    System.out.print("Inserire nome nodo di arrivo: ");
    nome2 = in.next();

    try{
      rete.calcolaPercorsoMinore(nome1, nome2, new Pacchetto());
    } catch (NodoInesistenteException e){
      System.out.println(e.getMessage());
    }

  }


  public static void main(String[] args){

    //Menu
    int scelta;

    while (true){

      //Stampa menu
      StampaMenu();


      //Inserimento scelta
      System.out.print("Inserire scelta: ");
      //Intero
      if (in.hasNextInt()) {
        scelta = in.nextInt();
      } else {
        scelta = -1;
        in.next();
      }


      switch(scelta){
        case 1:
          aggiundiNodo();
          break;
        case 2:
          aggiungiCollegamento();
          break;
        case 3:
          calcoloPercorsoMinimoFraNodi();
          break;
        case 4:
          break;
        case 5:
          System.exit(0);
          break;
        default:
          System.out.println("Errore: scelta non valida");
          break;
      }


    }
        
  }
}
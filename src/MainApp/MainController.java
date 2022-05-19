/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainApp;

import Classi.Eccezioni.CollegamentoGiaEsistenteException;
import Classi.Eccezioni.NodoInesistenteException;
import Classi.Nodo.Collegamento;
import Classi.Nodo.Nodo;
import Classi.Rete.Pacchetto;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Optional;

/**
 *
 * @author simone.sesana
 */
public class MainController 
{

    //Mainapp
    private MainApp mainApp;


    //ELEMENTI INTERFACCIA
    //Schermata rete
    @FXML Pane root;
    @FXML TextField txtNodoPartenza;
    @FXML TextField txtNodoArrivo;
    @FXML TextField txtTTL;

    //Shermata invio pacchetto
    @FXML TextArea txtPercorsoMinore;
    @FXML TextArea txtPercorsiTrovati;

    //Schermata grafo cammini minimi
    @FXML Pane paneCamminiMinimi;

    //Schermata log
    @FXML TextArea log;

    //VARIABILI
    //Nodo da collegare
    Button nodoDaCollegare;

    //ArrayList di nodi contenente i bottoni
    private ArrayList<Button> nodi;

    //Numero ultimo nodo aggiunto
    int nUltimoNodo = 0;

    //Nodo di partenza per il calcolo del grafo minimo
    String nodoPartenzaGrafoMinimo = "R0";


    //METODI
    //Metodo aggiunta nodo
    public void aggiungiNodo(double x, double y, Pane root){

        Button b = new Button();
        b.setText("R" + nUltimoNodo);
        nUltimoNodo++;

        b.setLayoutX(x-10);
        b.setLayoutY(y-40);

        //b.setShape(new Circle(1.5));
        b.setStyle("-fx-font-size:15");

        b.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                /*
                    Se viene premuto il tasto sinistro si realizza
                    un collegamento fra due nodi, altrimenti si visualizza
                    la pagina di modifica/rimozione del nodo
                */
                if(event.getButton() == MouseButton.PRIMARY){
                    if(nodoDaCollegare == null){
                        nodoDaCollegare = b;
                    } else {

                        //Viene aggiunto il collegamento graficamente
                        aggiungiCollegamento(b, root);

                    }
                } else {

                    /*
                        Se l'utente clicca con il tasto sinistro del mouse sul bottone
                        questo viene rimosso, e con esso anche tutti i collegamenti verso
                        di esso
                    */
                    rimuoviNodo(b);

                }
            }
        });

        //Aggiunta nodo alla rete
        mainApp.getRete().aggiungiNodo(new Nodo("R" + Integer.toString(nodi.size())));

        nodi.add(b);
        root.getChildren().add(nodi.get(nodi.size()-1));

    }

    //Metodo rimozione nodo
    public void rimuoviNodo(Button b){

        //Viene inizializzato il dialogo
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Rimozione collegamento");
        alert.setHeaderText("Sei sicuro di voler rimuovere il nodo selezionato?\nIl nodo e tutti i collegamenti" +
                " verso di esso verranno rimossi");
        alert.setContentText(null);

        //Il dialogo viene visualizzato
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){

            //Il nodo viene rimosso dalla gui
            root.getChildren().remove(b);


            //Le linee di collegamento vengono rimosse dall'interfaccia grafica
            for(Nodo t : mainApp.getRete().getNodi()){
                for(int i = 0; i < t.getCollegamenti().size(); i++){
                    if(t.getCollegamenti().get(i).getNodoCollegato().getNome().equals(b.getText())){
                        root.getChildren().remove(t.getCollegamenti().get(i).getLinea());
                        break;
                    }
                }
            }

            //Il nodo viene rimosso dall'istanza della rete
            mainApp.getRete().rimuoviNodo(b.getText());

            //Rimozione nodo dall'arraylist di bottoni
            for(int i = 0; i < nodi.size(); i++){
                if(nodi.get(i).getText().equals(b.getText())){
                    nodi.remove(i);
                }
            }

        } else {}

    }

    //Metodo aggiunta collegamento
    public void aggiungiCollegamento(Button nodoDestinazione, Pane root){

        int peso = 1;

        //Viene visualizzato un dialogo per l'inserimento del peso del collegamento
        //Inizializzazione dialogo
        TextInputDialog dialog = new TextInputDialog("1");
        dialog.setTitle("Peso collegamento");
        dialog.setHeaderText(null);
        dialog.setContentText("Inserire peso collegamento:");

        //Il dialogo viene visualizzato
        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()){
            try{
                peso = Integer.parseInt(result.get());
            } catch (Exception e){
                mainApp.visualizzaDialogoErrore("Peso non valido",
                        "Errore: peso inserito non valido");
                return;
            }
        }

        //Viene creato un collegamento fra i due nodi
        Line line = new Line();

        /*
            Vengono ipostate le coordinate di partenza e di arrivo della linea
            date le cordinate dei nodi da collegare
        */
        line.setStartX(nodoDaCollegare.getLayoutX()+10);
        line.setStartY(nodoDaCollegare.getLayoutY()+10);

        line.setEndX(nodoDestinazione.getLayoutX()+10);
        line.setEndY(nodoDestinazione.getLayoutY()+10);


        /*
            Il collegamento viene creato anche all'interno dell'oggetto rete controllando che
            non sia già presente un collegamento fra i due nodi selezionati. Se è già presente
            viene visualizzato un messaggio di errore
        */
        try {
            mainApp.getRete().aggiungiCollegamento(nodoDaCollegare.getText(), nodoDestinazione.getText(), peso, line);
            mainApp.getRete().aggiungiCollegamento(nodoDestinazione.getText(), nodoDaCollegare.getText(), peso, line);
        } catch (CollegamentoGiaEsistenteException cE){
            mainApp.visualizzaDialogoErrore("Collegamento già esistente",
                    "Errore: il collegamento fra i due nodi selezionati è già esistente");
            nodoDaCollegare = null;
            return;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            nodoDaCollegare = null;
            return;
        }

        //La linea viene aggiunta all'interfaccia
        root.getChildren().add(line);

        nodoDestinazione.toFront();
        nodoDaCollegare.toFront();

        nodoDaCollegare = null;

    }


    //Calcolo percorso minimo fra due nodi
    public void calcoloPercorsoMinimo(){

        log.clear();

        //Pacchetto con percorso minore
        Pacchetto minore = null;

        //Insieme di percorsi trovati
        ArrayList<Pacchetto> pacchetti = null;

        String nome1, nome2;
        nome1 = txtNodoPartenza.getText();
        nome2 = txtNodoArrivo.getText();


        /*
            Se la casella del TTL è riempita con un valore errato viene
            visualizzato un messaggio d'errore
        */
        try{
            Integer.parseInt(txtTTL.getText());
        } catch (Exception e){
            mainApp.visualizzaDialogoErrore("Valore TTL errato",
                    "Errore: inserire un valore di TTL accettabile");
            return;
        }


        /*
            Se l'utente non specifica un TTL questo viene impostato
            ad un valore molto alto
        */
        if(txtTTL.getText().isEmpty()){
            try{
                pacchetti = mainApp.getRete().calcolaPercorsoMinore(nome1, nome2, new Pacchetto(1000), log);
            } catch (NodoInesistenteException e){
                mainApp.visualizzaDialogoErrore("Nodo inesistente",
                        "Errore: nodo inesistente");
            }
        } else {
            try{
                pacchetti = mainApp.getRete().calcolaPercorsoMinore(nome1, nome2, new Pacchetto(Integer.parseInt(txtTTL.getText()) + 1), log);
            } catch (NodoInesistenteException e){
                mainApp.visualizzaDialogoErrore("Nodo inesistente",
                        "Errore: nodo inesistente");
                return;
            }
        }

        //Ricerca e stampa il percorso minore
        try{
            minore = new Pacchetto(pacchetti.get(0));
        } catch (Exception e){
        }
        for(int i = 1; i < pacchetti.size(); i++){
            if(pacchetti.get(i).getPeso() < minore.getPeso()){
                minore = new Pacchetto(pacchetti.get(i));
            }
        }

        /*
            Imposta contenuto delle caselle testuali in base
            ai percorsi che sono stati trovati
        */
        if(minore == null){
            txtPercorsoMinore.setText("Nessun percorso trovato");
            txtPercorsiTrovati.setText("Nessun percorso trovato");
        } else {

            txtPercorsoMinore.setText(minore.toString());
            String percorsiTrovatiStr = "";

            for(Pacchetto p : pacchetti){
                percorsiTrovatiStr = percorsiTrovatiStr + p.toString() + "\n";
            }

            txtPercorsiTrovati.setText(percorsiTrovatiStr);

        }
        
    }


    /*
        Questa funzione calcola il grafo dei cammini minimi e lo crea
        all'interno della scheda dedicata
    */
    public void calcolaGrafoMinimo() {

        log.clear();
        paneCamminiMinimi.getChildren().clear();

        /*
            L'arraylist viene riempito con tutti i percorsi minimi dal nodo di partenza fino
            a tutti gli altri nodi della rete
         */
        ArrayList<Button> bottoniPercorsiMinimi = new ArrayList<Button>();
        ArrayList<Pacchetto> percorsiMinimi = mainApp.getRete().calcolaGrafoCamminiMinimi(nodoPartenzaGrafoMinimo, log);


        /*
            Viene creato un arrayList contentente tutti i nodi
            che compaiono nel grafo dei percorsi minimi
        */
        ArrayList<Nodo> nodiMinimi = new ArrayList<Nodo>();
        for(Pacchetto t : percorsiMinimi){
            for(Nodo n : t.getNodi()){
                boolean in = false;
                for(Nodo j : nodiMinimi){
                    if(j.getNome().equals(n.getNome())){
                        in = true;
                    }
                }
                if(!in){
                    nodiMinimi.add(n);
                }
            }
        }


        //Viene cercato il bottone corrispondente al nodo e viene aggiunto all'interfaccia
        for(Nodo n : nodiMinimi){
            for(Button b : nodi){
                if(b.getText().equals(n.getNome())){

                    //Viene creato il nuovo bottone nel grafo dei cammini minimi
                    Button t = new Button();
                    t.setLayoutX(b.getLayoutX());
                    t.setLayoutY(b.getLayoutY());
                    t.setText(b.getText());
                    t.setStyle(b.getStyle());

                    //Il bottone viene aggiunto
                    paneCamminiMinimi.getChildren().add(t);
                    bottoniPercorsiMinimi.add(t);

                }
            }
        }


        //Vengono aggiunte le linee di collegamento fra i nodi
        for(Pacchetto p : percorsiMinimi){
            for(int i = 0; i < p.getNodi().size(); i++){
                try{
                    for(Collegamento c : mainApp.getRete().ricercaNodo(p.getNodi().get(i).getNome()).getCollegamenti()){
                        if(c.getNodoCollegato().getNome().equals(p.getNodi().get(i+1).getNome())){
                            Line l = new Line();

                            l.setStartX(c.getLinea().getStartX());
                            l.setStartY(c.getLinea().getStartY());

                            l.setEndX(c.getLinea().getEndX());
                            l.setEndY(c.getLinea().getEndY());

                            paneCamminiMinimi.getChildren().add(l);
                        }
                    }

                } catch (Exception e){

                }
            }
        }

        //Tutti i bottoni vengono spinti in avanti
        for(Button b : bottoniPercorsiMinimi){
            b.toFront();
        }

    }

    /*
        Funzione per cambiare il nodo di partenza per il calcolo
        dei cammini minimi
    */
    public void cambiaNodoPartenzaGrafoMinimo(){

        //Viene visualizzato un dialogo per l'inserimento del nome del nodo
        //Inizializzazione dialogo
        TextInputDialog dialog = new TextInputDialog("R0");
        dialog.setTitle("Nodo di partenza");
        dialog.setHeaderText(null);
        dialog.setContentText("Inserire nodo di partenza:");

        //Il dialogo viene visualizzato
        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()){
            nodoPartenzaGrafoMinimo = result.get();
        }

        //Il grafo viene ricalcolato
        calcolaGrafoMinimo();

    }


    //Metodo pulizia log
    public void pulisciLog(){
        log.clear();
    }


    public void initialize(){


        //Inizializzazione ArrayList nodi
        nodi = new ArrayList<Button>();

        /*
            Funzione per creare un bottone (nodo) ogni volta che l'utente
            effettua un click del mouse. Il bottone viene creato dove il mouse
            è stato clickato
        */
        root.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //Viene aggiunto un nodo
                aggiungiNodo(event.getSceneX(), event.getSceneY(), root);
            }
        });

    }


    public void setMainApp(MainApp mainApp) {
         //To change body of generated methods, choose Tools | Templates.
         this.mainApp=mainApp;
    }
    
}

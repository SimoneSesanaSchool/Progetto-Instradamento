/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainApp;

import java.io.IOException;

import Classi.Rete.Rete;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author simone.sesana
 */
public class MainApp extends Application
{

    private Rete rete;
    private Stage primaryStage;
    private MainController mainController;

    @Override
    public void start(Stage primaryStage) throws Exception 
    {
        
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Progetto instradamento");

        inizializza();
        
    }


    public static void main(String[] args)
    {
        launch(args);
    }


    private void inizializza() 
    {
        
        try 
        {

            //inizializzazione rete
            rete = new Rete();
            
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("mainGui.fxml"));
            AnchorPane rootLayout = (AnchorPane) loader.load();
            

            //inizializzazione del model
            mainController=loader.getController();
            mainController.setMainApp(this);
            
            
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        
    }


    /*
        Questa funzione visualizza un dialogo di errore con contenuto personalizzato costituito
        da una stringa da passare come parametro alla funzione
    */
    public void visualizzaDialogoErrore(String titoloDialogo, String errore){

        //Inizializzazione messagio di errore
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titoloDialogo);
        alert.setContentText(errore);
        alert.setHeaderText(null);

        //Visualizzazione messaggio di errore
        alert.showAndWait();

    }


    //Funzione che restituisce la rete
    public Rete getRete(){
        return rete;
    }

    
}

package Eccezzioni;

public class LoopException extends Exception{

    /*
        Questa eccezzione viene lanciata nel momento in cui si sta
        generando un loop infinito all'interno del protocollo preposto
        al calcolo dei cammini minimi 
    */
    public LoopException(){
        super("Errore: si sta generando un loop infinito");
    }

}

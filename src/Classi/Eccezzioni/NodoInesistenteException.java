package Classi.Eccezzioni;

public class NodoInesistenteException extends Exception{

    /*
        Eccezione lanciata quando si cerca di richiamare
        un nodo inesistente all'interno di una rete
    */
    public NodoInesistenteException(){
        super("Errore: nodo inesistente");
    }

}

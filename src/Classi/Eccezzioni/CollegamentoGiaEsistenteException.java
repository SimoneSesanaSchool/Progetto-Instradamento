package Classi.Eccezzioni;

public class CollegamentoGiaEsistenteException extends Exception{

    /*
        Eccezione lanciata quando si cerca di richiamare
        un nodo inesistente all'interno di una rete
    */
    public CollegamentoGiaEsistenteException(){
        super("Errore: collegamento già esistente");
    }

}

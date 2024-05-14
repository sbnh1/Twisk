package twisk.monde;

public class SasSortie extends Activite{

    /**
     * Constructeur d'un sas de sortie
     * Initialise un sas de sortie avec un nom et un temps d'exécution de 0 et un écart de temps de 0
     */
    public SasSortie(){
        super("SasSortie",0,0);
    }

    /**
     * Retourne une représentation en langage C du sas de sortie
     * @return Une représentation en langage C du sas de sortie
     */
    public String toC(){
        StringBuilder string = new StringBuilder();
        string.append("");
        return string.toString();
    }
}


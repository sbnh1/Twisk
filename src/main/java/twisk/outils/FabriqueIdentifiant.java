package twisk.outils;

public class FabriqueIdentifiant {
    private int noEtape;
    private static FabriqueIdentifiant instance;

    /**
     * Constructeur privé de la classe FabriqueIdentifiant.
     * Initialise le numéro de l'étape à zéro.
     */
    private FabriqueIdentifiant(){
        this.noEtape = 0;
    }

    /**
     * Méthode statique permettant d'obtenir l'instance unique de la fabrique (Singleton).
     * Si aucune instance n'existe, une nouvelle instance est créée.
     * @return l'instance unique de la fabrique
     */
    public static FabriqueIdentifiant getInstance() {
        if (instance == null) {
            instance = new FabriqueIdentifiant();
        }
        return instance;
    }

    /**
     * Méthode pour obtenir un identifiant unique pour une étape.
     * L'identifiant est de la forme "Activite" suivi d'un numéro incrémenté.
     * @return un identifiant unique pour une étape
     */
    public String getIdentifiantEtape(){
        String res =  "Activite" + noEtape;
        incrementerNoEtape();
        return res;
    }

    public String getIdentifiantGuichet(){
        String res = "Guichet" + noEtape;
        incrementerNoEtape();
        return res;
    }

    /**
     * Méthode pour incrémenter le numéro de l'étape.
     */
    public void incrementerNoEtape(){
        noEtape++;
    }

    /**
     * Réinitialise le numéro de l'étape à zéro.
     * Cette méthode peut être utilisée pour réinitialiser la fabrique.
     */
    public void reset(){
        this.noEtape = 0;
    }
}

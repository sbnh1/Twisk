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
     * Défini à quel numéro d'étape est la fabrique
     * @param noEtape le numéro de l'étape auquel la fabrique est
     */
    public void setNoEtape(int noEtape){
        this.noEtape = noEtape;
    }

    /**
     * Retourne à quel numéro étape en est la fabrique
     * @return le numéro étape
     */
    public int getNoEtape(){
        return noEtape;
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
     * Méthode pour obtenir un identifiant unique pour une activité.
     * L'identifiant est de la forme "Activite" suivi d'un numéro incrémenté.
     * @return un identifiant unique pour une étape
     */
    public String getIdentifiantEtape(){
        String res =  "Activite" + noEtape;
        incrementerNoEtape();
        return res;
    }

    /**
     * Méthode pour obtenir un identifiant unique pour un guichet
     * L'identifiant est de la forme "Guichet" suivi d'un nombre incrémenté
     * @return un identifiant unique pour un guichet
     */
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

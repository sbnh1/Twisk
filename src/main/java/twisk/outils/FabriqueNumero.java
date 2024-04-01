package main.java.twisk.outils;

public class FabriqueNumero {
    // La classe est testé dans testEtape
    private static FabriqueNumero instance;
    private int cptEtape;
    private int cptSemaphore;

    /**
     * Constructeur sans argument de la classe FabriqueNumero
     * initialisation des compteurs à 0 pour les etape et 1 pour les semaphores
     */
    private FabriqueNumero() {
        this.cptEtape = 0;
        this.cptSemaphore = 1;
    }

    /**
     * Méthode qui permet d'obtenir l'instance de la fabrique qui est unique
     * si l'instance n'existe pas elle est créée
     * @return l'unique instance de la fabrique
     */
    public static FabriqueNumero getInstance() {
        if (instance == null) {
            instance = new FabriqueNumero();
        }
        return instance;
    }

    /**
     * Méthode qui permet d'obtenir un numéro unique pour une étape
     * @return un numéro unique pour une étape
     */
    public int getNumeroEtape() {
        return this.cptEtape++;
    }

    /**
     * Méthode qui permet d'obtenir un numéro unique pour un sémaphore
     * @return un numéro unique pour un sémahore
     */
    public int getNumeroSemaphore(){
        return this.cptSemaphore++;
    }

    /**
     * Méthode qui permet de remettre à 0 le compteur de numéro d'étape
     */
    public void resetNumeroEtape() {
        this.cptEtape = 0;
    }

    /**
     * Méthode qui permet de remettre à 1 le compteur de numéro sémaphore
     */
    public void resetNumeroSemaphore(){
        this.cptSemaphore = 1;
    }
}

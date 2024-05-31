package twisk.outils;

public class FabriqueIdPc {
    // La classe est testé dans testEtape
    private static FabriqueIdPc instance;
    private int id;

    /**
     * Constructeur sans argument de la classe FabriqueNumero
     * initialisation des compteurs à 0 pour les etape et 1 pour les semaphores
     */
    private FabriqueIdPc() {
        this.id = 0;
    }

    /**
     * Méthode qui permet d'obtenir l'instance de la fabrique qui est unique
     * si l'instance n'existe pas elle est créée
     * @return l'unique instance de la fabrique
     */
    public static FabriqueIdPc getInstance() {
        if (instance == null) {
            instance = new FabriqueIdPc();
        }
        return instance;
    }

    /**
     * Renvoie un novel id
     * @return un nouvel id
     */
    public int getId() {
        return ++this.id;
    }
}

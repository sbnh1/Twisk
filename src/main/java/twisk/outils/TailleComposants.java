package twisk.outils;

public class TailleComposants {
    private static TailleComposants instance = null;
    public final int activiteLargeur = 240;
    public final int activiteHauteur = 70;
    public final int largeur = 1300;
    public final int hauteur = 900;
    public final int rectangleLargeur = 100;
    public final int rectangleHauteur = 30;
    public final double rayonCercle = 6.;
    public final int rayonCercleClient = 5;

    /**
     * Méthode statique permettant d'obtenir l'instance unique de la classe TailleComposants (Singleton).
     * Si aucune instance n'existe, une nouvelle instance est créée.
     * @return l'instance unique de la classe TailleComposants
     */
    public static TailleComposants getInstance() {
        if (instance == null) {
            instance = new TailleComposants();
        }
        return instance;
    }
}

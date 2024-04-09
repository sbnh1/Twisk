package twisk.outils;

public class TailleComposants {
    private static TailleComposants instance = null;
    public final int activiteLargeur = 160;
    public final int activiteHauteur = 70;
    public final int largeur = 1000;
    public final int hauteur = 700;
    public final int rectangleLargeur = 100;
    public final int rectangleHauteur = 30;
    public final double rayonCercle = 7.;

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

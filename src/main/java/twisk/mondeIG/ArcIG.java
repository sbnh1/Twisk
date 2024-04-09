package main.java.twisk.mondeIG;

public class ArcIG {
    private PointDeControleIG pointDeControleDepart;
    private PointDeControleIG pointDeControleArrivee;

    /**
     * Constructeur de la classe ArcIG
     * @param pointDeControleDepart le point de départ de l'arc
     * @param pointDeControleArrivee le point d'arrivé de l'arc
     */
    public ArcIG(PointDeControleIG pointDeControleDepart, PointDeControleIG pointDeControleArrivee){
        this.pointDeControleDepart = pointDeControleDepart;
        this.pointDeControleArrivee = pointDeControleArrivee;
    }

    /**
     * Récupère le point de départ de l'arc
     * @return le point de départ de l'arc
     */
    public PointDeControleIG getPointDeControleDepart() {
        return pointDeControleDepart;
    }

    /**
     * Récupère le point d'arrivé de l'arc
     * @return le point d'arrivé de l'arc
     */
    public PointDeControleIG getPointDeControleArrivee() {
        return pointDeControleArrivee;
    }
}

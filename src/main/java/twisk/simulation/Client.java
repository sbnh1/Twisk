package twisk.simulation;

import twisk.monde.Etape;

import java.util.Random;

public class Client {
    private int numeroClient;
    private int rang;
    private Etape etape;
    private int couleur;

    /**
     * Constructeur de la classe Client
     * @param numero
     */
    public Client(int numero){
        this.numeroClient = numero;
        Random random = new Random();
        this.couleur = random.nextInt(4);
    }
    /**
     * Méthode qui déplace un client vers l'étape donnée et le rang donné
     * @param etape l'étape de destination
     * @param rang le rang qu'occupera le client dans la file d'attente
     */
    public void allerA(Etape etape, int rang){
        this.etape = etape;
        this.rang = rang;
    }

    /**
     * Renvoie le numéro du client
     * @return le numéro du client
     */
    public int getNumeroClient(){
        return this.numeroClient;
    }

    /**
     * Renvoie l'étape du client
     * @return l'étape du client
     */
    public Etape getEtape(){
        return this.etape;
    }

    /**
     * Renvoie le rang du client
     * @return le rang du client
     */
    public int getRang(){
        return this.rang;
    }

    @Override
    public String toString() {
        return "Client{" +
                "numeroClient=" + numeroClient +
                ", rang=" + rang +
                ", etape=" + (etape != null ? etape.getNom() : "null") +
                '}';
    }

    public int getCouleur() {
        return couleur;
    }

    public void setCouleur(int couleur) {
        this.couleur = couleur;
    }
}

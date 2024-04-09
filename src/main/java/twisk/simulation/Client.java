package main.java.twisk.simulation;

import main.java.twisk.monde.Etape;

public class Client {
    private int numeroClient;
    private int rang;
    private Etape etape;

    /**
     * Constructeur de la classe Client
     * @param numero
     */
    public Client(int numero){
        this.numeroClient = numero;
    }
    /**
     * Méthode qui déplace un client vers l'étape donné et le rang donné
     * @param etape l'étape de destination
     * @param rang le rang qu'occupera le client dans la file d'attente
     */
    public void allerA(Etape etape, int rang){
        this.etape = etape;
        this.rang = rang;
    }
    public int getNumeroClient(){
        return this.numeroClient;
    }
    public Etape getEtape(){
        return this.etape;
    }
    public int getRang(){
        return this.rang;
    }
}
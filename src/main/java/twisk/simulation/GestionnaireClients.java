package twisk.simulation;

import twisk.monde.Etape;

import java.util.HashMap;
import java.util.Iterator;

public class GestionnaireClients implements Iterable<Client> {
    public HashMap<Integer, Client> listClient;

    /**
     * Constructeur de la classe GestionnaireClients
     * initialise la liste des clients
     */
    public GestionnaireClients(){
        this.listClient = new HashMap<>();
    }

    /**
     * Méthode qui permet d'instancier les clients identifiés par leur numéro de processus
     * @param tabClients tableau contenant tout les numéro de clients à instancier
     */
    public void setClients(int... tabClients){
        for(int i = 0; i < tabClients.length; i++){
            listClient.put(tabClients[i], new Client(tabClients[i]));
        }
    }

    /**
     * Renvoie le client ayant le numéro donné en paramètre
     * @param numeroClient numéro du client
     * @return le client ayant le numéro en paramètre
     */
    public Client getClients(int numeroClient){
        return this.listClient.get(numeroClient);
    }

    /**
     * Méthode qui permet de déplacer un client dans le monde à chaque changement
     * @param numeroClient le numéro du client
     * @param etape l'étape de destination
     * @param rang le rang qu'occupera le client dans la file d'attente
     */
    public void allerA(int numeroClient, Etape etape, int rang){
        this.listClient.get(numeroClient).allerA(etape, rang);
    }

    /**
     * Méthode qui nettoie la liste des clients
     * méthode utilisé pour préparer une nouvelle simulation
     */
    public void nettoyer(){
        this.listClient.clear();
    }

    /**
     * Methode qui renvoie la taille de la HashMap
     * @return La taille de la HashMap clients
     */
    public int size(){
        return this.listClient.size();
    }

    /**
     * Méthode qui retourne un itérateur sur la liste des clients
     * @return un itérateur sur la liste des clients
     */
    public Iterator<Client> iterator(){
        return listClient.values().iterator();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("GestionnaireClients{\n");
        for (Client client : listClient.values()) {
            sb.append("  ").append(client.toString()).append("\n");
        }
        sb.append('}');
        return sb.toString();
    }
}

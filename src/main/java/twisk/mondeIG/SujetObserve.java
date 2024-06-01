package twisk.mondeIG;

import twisk.vues.Observateur;

import java.util.ArrayList;

public class SujetObserve {
    private ArrayList<Observateur> obs = new ArrayList<>(20);

    /**
     * Notifie tous les Observateurs du mondeIG de tous les changements notifié
     */
    public void notifierObservateur() {
        for (Observateur o : this.obs) {
            o.reagir();
        }
    }

    /**
     * Méthode qui permet au Vue de s'inscrire toute seul à la liste des Observateur
     * @param o observateur ajouté à la liste
     */
    public void ajouterObservateur(Observateur o) {
        this.obs.add(o);
    }


    /**
     * Retourne le nombre d'observateurs inscrits
     * @return nombre d'observateurs
     */
    public int getNombreObservateurs() {
        return this.obs.size();
    }
}

package twisk.outils;

import twisk.monde.Etape;
import twisk.mondeIG.EtapeIG;

import java.util.HashMap;

public class CorrespondanceEtapes {
    private HashMap<EtapeIG, Etape> etapeIGEtapeHashMap;

    /**
     * Constructeur de la classe CorrespondanceEtapes
     */
    public CorrespondanceEtapes(){
        this.etapeIGEtapeHashMap = new HashMap<>();
    }

    /**
     * Méthode qui ajoute une EtapeIG et son Etape correspondante dans la Hashmap
     * @param etapeIG l'EtapeIG à ajouter
     * @param etape l'Etape correspondante
     */
    public void ajouter(EtapeIG etapeIG, Etape etape){
        this.etapeIGEtapeHashMap.put(etapeIG, etape);
    }

    /**
     * Méthode qui permet de retrouver une Etape à partir d'une EtapeIG
     * @param etapeIG l'EtapeIG à partir de laquel on fait la recherche
     * @return l'Etape qui correspond à l'EtapeIG donné en paramètre
     */
    public Etape get(EtapeIG etapeIG){
        return this.etapeIGEtapeHashMap.get(etapeIG);
    }
}

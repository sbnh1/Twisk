package twisk.outils;

import twisk.monde.Etape;
import twisk.mondeIG.EtapeIG;

import java.util.HashMap;
import java.util.Map;

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

    /**
     * Methode qui renvoie l'EtapeIG associee a l'Etape donnee en parametre
     * @param etape Etape servant pour obtenir l'EtapeIG
     * @return L'EtapeIG
     */
    public EtapeIG getEtapeIG(Etape etape){
        for(Map.Entry<EtapeIG, Etape> entry : this.etapeIGEtapeHashMap.entrySet()){
            if(entry.getValue().equals(etape)){
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères de la map
     * @return une chaîne de caractères représentant les correspondances entre EtapeIG et Etape
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("CorrespondanceEtapes {\n");

        for (Map.Entry<EtapeIG, Etape> entry : etapeIGEtapeHashMap.entrySet()) {
            sb.append("  ").append(entry.getKey()).append(" -> ").append(entry.getValue()).append("\n");
        }
        sb.append("}");
        return sb.toString();
    }
}

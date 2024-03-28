package main.java.twisk.monde;

public class SasEntree extends Activite{

    public SasEntree(String nom){ super(nom, 0, 0); }

    public String toC(){
        StringBuilder string = new StringBuilder();
        string.append("entrer(sasEntree);\n" +
                "    delai(6,3);\n" +
                "    transfert(sasEntree, " + this.getSuccesseur().getEtape(0).getNom() +");");
        return string.toString();
    }
}

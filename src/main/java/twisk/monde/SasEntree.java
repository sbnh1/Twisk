package main.java.twisk.monde;

public class SasEntree extends Activite{

    public SasEntree(String nom){ super(nom, 0, 0); }

    public SasEntree(String nom, int temps, int ecartTemps){
        super(nom, temps, ecartTemps);
    }

    public StringBuilder toC(){
        StringBuilder string = new StringBuilder();
        string.append("entrer(sasEntree);\n" +
                "    delai(6,3);\n" +
                "    transfert(sasEntree, " + this.getSuccesseur().getEtape(0) +");");
        return string;
    }
}

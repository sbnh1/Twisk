package main.java.twisk.monde;

import main.java.twisk.outils.FabriqueNumero;
public class Guichet extends Etape {

    private int nbJetons;
    private int semaphores;
    public Guichet(String nom){
        super(nom);
        this.nbJetons = 0;
        this.semaphores = FabriqueNumero.getInstance().getNumeroSemaphore();
    }

    public Guichet(String nom, int nbJetons){
        super(nom);
        this.nbJetons = nbJetons;
        this.semaphores = FabriqueNumero.getInstance().getNumeroSemaphore();
    }

    public Boolean estUnGuichet(){
        return true;
    }

    public Boolean estUneActivite(){
        return false;
    }

    public int getNbJetons(){
        return this.nbJetons;
    }

    public int getNumeroSemaphore(){
        return this.semaphores;
    }

    /*public String toC(){
        //ou est dit qui est le successeur ? comment l'avoir
        this.ajouterSuccesseur(ActiviteRestreinte next = new ActiviteRestreinte(this.getNom()));
        return "P(ids, " + this.getNumeroSemaphore() + ");\n" +
                "transfert(" + this.getId() + "," + this.getSuccesseur().getEtape(0).getId() + ");\n" +
                "delai(" +  next.getTemps() + "," + next.getEcartTemps() + ");\n" +
                "V(ids," + this.getNumeroSemaphore() + ");\n" +
                next.toC();
    }*/
    public StringBuilder toC(){
        StringBuilder string = new StringBuilder();
        string.append("    delai(4,1);");
        string.append("    P(ids, guichet_semaphore" + this.getNumeroSemaphore() + ");"); //pour avoir guichet_semaphore1/guichet_semaphore2...
        string.append("    transfert(" + this.getNom() + ", " + this.getSuccesseur().getEtape(0).getNom() + ");");

        string.append(this.getSuccesseur().getEtape(0).toC());

        string.append("    V(ids, guichet_semaphore" + this.getNumeroSemaphore() + ");");
        return string;
    }
}

import java.util.ArrayList;

public class Flot {
    private Reseau reseauF;
    private double[][] valeurFlot; 
    //pour tout i,j, 0 <= valeurFlot[i][j] <= reseauF.get(i,j) (qui représente la capacité de l'arc i->j)
    //et conservation du flot
    

    //--------------------------------------------------------
    //---------------------CONTRUCTEUR -----------------------
    //--------------------------------------------------------
    
    public Flot(Reseau r) {
        reseauF = new Reseau(r);
        valeurFlot = new double[reseauF.getN()][reseauF.getN()];

        for (int i = 0; i < reseauF.getN(); i++) {
            for (int j = 0; j < reseauF.getN(); j++) {
                valeurFlot[i][j] = 0;
            }
        }
    }

    //----------------------METHODES------------------------------
    

     public String toString() {

        StringBuilder res = new StringBuilder("\n");
         for (double[] doubles : valeurFlot) {
             for (double aDouble : doubles) {
                 res.append(aDouble).append("\t");
             }
             res.append("\n");
         }
	return res.toString();
    }

    public void modifierFlot(int i, int j, double flot) {

        valeurFlot[i][j] += flot;
    }

   
    
    /**
     * On suppose que le reseauF est sans digon.
     * @return le réseau résiduel associé à reseauF et au flot this. 
     * Ce réseau résiduel pourra lui avoir des digons.
     * 
     */
    public Reseau créerReseauResiduel() {
        Reseau residuel = new Reseau(reseauF.getN(), reseauF.getS(), reseauF.getT());
	//à compléter
	
        return residuel;
    }

    /**
     *
     * @param chemin est un s-t chemin dans le réseau résiduel de reseauF et this
     * @param epsilon > 0 est la capacité min de chemin telle définie dans le cours 
     * action : modifie le flot this en ajoutant ou retirant epsilon comme indiqué dans le cours
     */
    public void modifieSelonChemin(ArrayList<Integer> chemin, double epsilon) { 
	//à compléter
    }

}

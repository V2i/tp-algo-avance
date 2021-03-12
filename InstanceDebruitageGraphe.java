import java.util.ArrayList;

public class InstanceDebruitageGraphe {
    private boolean[][] g;
    //va contenir la matrice d'ajacence du graphe G sur n sommets modélisant les n pixels de l'image
    //pour nous : chaque pixel correpondra à un sommet, et chaque pixel est adjacent à ses voisins H,B,G,D (sauf au bord bien sûr), le graphe G sera donc une grille
    //Cela dit, on peut tout à fait utiliser cette classe avec un graphe quelconque
    
    private double[] b; //b est de taille n, 0 <= b[i] <= 255 contient niveau de gris du sommet i (pour nous b[i]=0 (noir) ou 255 (blanc), sauf dans les bonus)
    private double alpha;

    //sortie du problème : sous ensemble S de sommets de g (représentant ensemble de sommets qu'on colorie en blanc, les autres seront en noir)
    //objectif : min f(S) = N(S) + B(V\S) + alpha |{ {i,j} dans E de g tq |{i,j} \cap S|=1 }| 
   // avec N(S) = # de sommets de S qui étaient noirs, B(V\S) = # de sommets de V\S qui étaient blancs
   // 

    public InstanceDebruitageGraphe(boolean[][] g, double[] bi, double alpha){
        this.g = g;
        this.b = bi;
        this.alpha = alpha;
    }

 
    public InstanceDebruitageGraphe(Img im) {
        /**
         * si im est une image 3x3, va créer un graphe à 9 sommets, avec la numérotation
	 * 0 1 2
	 * 3 4 5
	 * 6 7 8
	 * 
	 * on aura par exemple les aretes {0,3} {3,4} {3,6}  
	 * b[3] correspondra au niveau de gris du pixel 
         * g[i][j] = true lorsque i et j sont voisins dans l'image.
         * 
         */

        int hauteur = im.nbLignes();
        int largeur = im.nbColonnes();
        int nbTotalNodes = largeur * hauteur;
		
        g = new boolean[nbTotalNodes][nbTotalNodes]; 
        b = new double[nbTotalNodes];
		
		
        for (int i = 0; i < largeur; i++) { 
            for (int j = 0; j < hauteur; j++) {
		
		int x = im.calculIndice(i,j);
		b[x]=im.getData()[i][j];
		if (i < largeur-1) { 
		    int y = im.calculIndice(i+1,j);
		    g[x][y] = true;
		    
		}
		if (i > 0) { 
		    int y = im.calculIndice(i-1,j);
		    g[x][y] = true;
		    
		}
		if (j < hauteur-1) { 
		    int y = im.calculIndice(i,j+1);
		    g[x][y] = true;
		    
		}
		
		if (j > 0) { 
		    int y = im.calculIndice(i,j-1);
		    g[x][y] = true;
		    
		}
	    }
	}
    
    

    }

    public int getN(){
	return g.length;
    }

    public double getB(int i){
	return b[i];
    }

    public double getAlpha(){
	return alpha;
    }

    public boolean isVoisin(int i, int j){
	return g[i][j];
    }
    
   

    /**
     * calcul un opt en se <=S réduisant à un problème de Coupe Minimum comme indiqué dans le sujet.
     * Attention, utilisez bien la méthode coupeMin() pour calculer votre coupe minimum (et non pas flotMaxCoupeMin(), qui ne gère pas les réseaux avec des digons)
     *  @return une solution optimale (correspondant donc à un ensemble de pixels à mettre en blanc)
     */
    public ArrayList<Integer> calculOpt(){
       //à completer
       return null;
    }

    
 
    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }
 

    public String toString() {
        String str = "";
        for (int i = 0; i < this.g.length ; i++) {
            for (int j = 0; j < this.g[0].length ; j++) {
  		    String c = "f";
		if (g[i][j]) 
				c = "t";            	  
                str = str + c + " ";
                
            }
	    str += "\n";
        }
	str += "tableau b \n";
	for (int i = 0; i < this.b.length ; i++) {
		str += b[i]+" ";
        }
        return str;
    }

   
}

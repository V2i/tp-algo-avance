import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Reseau {
    private double[][] arc ; //arc[i][j] = capacité de l'arc i -> j (0 si pas d'arc)
    private int s;
    private int t;


    //------------------------------------------------------------------
    //------------------CONSTRUCTEURS ----------------------------------
    //------------------------------- ----------------------------------
    

    public Reseau(int nbNoeud,int s,int t)
    {
        initAllArcToZero(nbNoeud);
        this.s = s;
        this.t = t;
    }

    public Reseau(double[][] arc, int s, int t) {
        this.arc = arc;
        this.s = s;
        this.t = t;
    }

    public Reseau(Reseau courant){
        int nb = courant.arc[0].length;
        this.arc = new double[nb][nb];
        for (int i = 0;i<nb;i++)
        {
            for (int j = 0; j <nb;j++)
            {
                this.arc[i][j] = courant.arc[i][j];
            }
        }
        this.s = courant.s;
        this.t = courant.t;
    }
 
    
    /**Constructeur en fonction d'un fichier d'initialisation
     *
     * @param fich : l'adresse complète du ficher .txt (ex file = "r1.txt"), au format suivant par exemple
     7
     0 1:0.5 2:2 
     2 1:1 3:1 4:1 6:1
     4 5:1
     6 3:1 5:1
     (7 represente le nombre de sommets, 0 à un arc sortant vers 1 avec capacité 0.5, etc

     * @param s,t vérifient s < n et t < n, avec n en premiere ligne de file 
     *
     */
    public Reseau(String fich,int s ,int t) throws FileNotFoundException {

        this.s = s;
        this.t = t;
        File file = new File(fich);
        Scanner sc = new Scanner(file);

        int nb = sc.nextInt();
        initAllArcToZero(nb);

        while (sc.hasNext())
        {
            int n = sc.nextInt();
            String [] str = sc.nextLine().split(" ");
	    for (int i = 1; i < str.length ; i++)
            {
                String [] netcap = str[i].split(":");
                modifierArc(n, new int[]{Integer.parseInt(netcap[0])},Double.parseDouble(netcap[1]));

            }
        }

    }

    
/**
     * Créé un réseau en fonction d'une instance du problème de DebruitageGraphe inst comme spécifié dans le sujet.
     * En particulier, si le graph de inst à n sommets il faudra créer un réseau avec n+2 sommets, et avec s=n et t=n+1.
     */
    public Reseau(InstanceDebruitageGraphe inst){

        initAllArcToZero(inst.getN()+2);

        this.s = inst.getN();
        this.t = inst.getN()+1;
        inst.setAlpha(0.5);

        for(int i=0; i< inst.getN(); i++) {
            if(inst.getB(i) == 255.0) {
                set(inst.getN(), i,1.0);
            } else {
                //puit a 1???
                set(inst.getN()+1, i, 1.0);
            }
        }

        for (int i = 0;i< inst.getN();i++) {
            for (int j = 0; j <inst.getN();j++)
            {
                if(inst.isVoisin(i, j)) {
                    this.arc[i][j] = inst.getAlpha();
                }

            }
        }
	
    }



    //------------------------------------------------------------------
    //------------------ GETTERS, SETTERS, METHODES UTILES et TOSTRING--
    //------------------------------- ----------------------------------

    
    
    public int getN(){
        return arc.length;
    }

    public int getS() {
        return s;
    }

    public int getT() {
        return t;
    }

    public void set(int i, int j, double v){
	arc[i][j]=v;
    }

    public double get(int i, int j){
	return arc[i][j];
    }

   public ArrayList<Integer> getVoisins(int i){
	ArrayList<Integer> res = new ArrayList<Integer>();
	for (int j = 0 ; j< arc[0].length ; j++)
            {
                if(arc[i][j] > 0){
		    res.add(j);
		}
            }
        return res;
    }
    

    private void initAllArcToZero(int nbNoeud)
    {
        arc = new double[nbNoeud][nbNoeud];
        for(int i = 0 ; i< nbNoeud ; i++)
        {
            for (int j = 0 ; j< nbNoeud ; j++)
            {
                arc[i][j] = 0;
            }
        }
    }

    /**
     * modifie la capacité des arcs sortant du noeud n1 vers les noeuds stocké dans n2.
     * ils auront la capacité cap
     */
    private void modifierArc(int n1, int[] n2, double cap)
    {
        for (int x: n2)
        {
            arc[n1][x] = cap;

        }
    }
    
    public String toString(){
	String res = "s : "+ s + " t : " + t + "\n";
	for (int i=0;i<arc.length;i++){
		for(int j=0;j<arc[i].length;j++){
		 	res+=arc[i][j] + " ";	
		}	
		res+="\n";
	}
	return res;
    }

    

    //------------------------------------------------------------------
    //------------------ METHODES POUR MAX FLOT / MIN CUT---------------
    //------------------------------- ----------------------------------


    /**
     * Cherche un s-t chemin dans this
     *
     *
     * @return un couple de deux arraylist d'Integer. 
     * S'il existe un chemin entre la source et le puit : le couple vaudra {les entiers du chemin, null}
     * S'il n'existe pas un chemin entre la source et le puit : le couple vaudra {null, les entiers atteignables depuis s}
     */
    public Couple<ArrayList<Integer>,ArrayList<Integer>> trouverChemin()
    {
	ArrayList<Integer> avoir = new ArrayList<Integer>();
	ArrayList<Integer> vus = new ArrayList<Integer>();
	int[] pred = new int[arc[0].length];
	for(int i=0;i<pred.length;i++){
	    pred[i]=-1;
	}
	
	avoir.add(s);
	boolean trouve = false;
	while(!trouve && !avoir.isEmpty()){
	    //avoir est disjoint de vus
	    //pour tout i dans vu U avoir, on a un chemin de s -> .. -> i dans prec
	    int v = avoir.remove(0);
	    vus.add(v);
	    if(v==t){
		trouve=true;
	    }
	    else{
		ArrayList<Integer> vois = getVoisins(v);
		for(int u : vois){
		    if(!vus.contains(u) && !avoir.contains(u)){
			//u est un nouveau sommet
			avoir.add(u);
			pred[u]=v;
		    }
		}
	    }
	}
	if(!trouve){
	    return new Couple<ArrayList<Integer>,ArrayList<Integer>>(null,vus);
	}
	else{
	    return new Couple<ArrayList<Integer>,ArrayList<Integer>>(remonteChemin(pred),null);
	}
    }


    private  ArrayList<Integer> remonteChemin(int[] pred){
	ArrayList<Integer> res =  new  ArrayList<Integer>();
	int c = t;
	while(pred[c]!=-1){
	    res.add(0,c);
	    c=pred[c];
	}
	res.add(0,c);
	return res;
    }


    //---------------------------- AUTRES METHODES --------------------------

    /**
   
     * @param chemin est un s-t chemin (tous les chemin[i][i+1] sont des arcs de capacité non nulle de this)
     * @return la capacité min d'un arc du chemin (min_i chemin[i][i+1])
     */
    public double calculCapMinChemin(ArrayList<Integer> chemin) {
        double epsilon = arc[chemin.get(0)][chemin.get(1)];

        for (int i = 0; i < chemin.size()-1; i++) {
            if (arc[chemin.get(i)][chemin.get(i + 1)] < epsilon) {
                epsilon = arc[chemin.get(i)][chemin.get(i + 1)];
            }
        }

        return epsilon;
    }

    /**
     *
     * On suppose que this est un réseau sans digon
     * @return un flot maximum, et une coupe minimum 
     *
     * Applique les étapes de l'algorithme de Ford-Fulkerson vu en cours 
          
     */
    public Couple<Flot,ArrayList<Integer>> flotMaxCoupeMin() {
	//à compléter
	return null;
    }

    /**
     *
     * On suppose que this est un réseau quelconque (avec peut être des digons)
     * @return une coupe minimum 
     *

     */
    public ArrayList<Integer> coupeMin() {
        Reseau r = new Reseau(this);
	r.supprimerDigons();        
	ArrayList<Integer> minCut = r.flotMaxCoupeMin().getElement2();		

        
        //on enleve les points fictif des digons de la coupe min
	ArrayList<Integer> minCutclean = new ArrayList<Integer>();        
	for (int x : minCut) {
            if (x < t) {
		minCutclean.add(x);
            }
        }
	return minCutclean;
    }




    /**
     * utilisé seulement pour résoudre le problème de débruitage, puisque lorsque l'on créé le réseau en fonction de l'instance de débruitage
     * comme indiqué dans le sujet, le réseau obtenu possède de nombreux digons, qu'il faudra donc supprimer pour pouvoir appliquer
     * FF
     * supprime les digons du reseau : pour chaque digon i->j (de capacité c1) et j->i (de capacité c2) (avec c1 et c2 non nuls) :
       - on ajoute un nouveau sommet d (pour le premier nouveau sommet d=n, pour le second d=n+1 etc)
       - i->j reste de capacité c1, j->d et d->i sont de capacité c2
     */
	 
	public void supprimerDigons()
    {
        int i=0,j = 0;
        int n = arc[i].length;
        ArrayList<int[]> digons = new ArrayList<>();
        while (i < n){
            j = 0;
            while (j < n){
                if(arc[i][j] != 0 && arc[j][i] != 0){
                    digons.add(new int[]{j,i});
                    arc[j][i]=0;
                }
                j++;
            }
            i++;
        }
        double[][] newArc = new double[n+digons.size()][n+digons.size()];
	for(i=0; i<n;i++ ){
            newArc[i] = Arrays.copyOf(arc[i],newArc[i].length);
        }
        int d = n;
        for (int[] digon : digons){
            i = digon[1];
            j = digon[0];
            newArc[j][d] = arc[i][j];
            newArc[d][i] = arc[i][j];
            d++;
        }
        arc = newArc;
    }



}

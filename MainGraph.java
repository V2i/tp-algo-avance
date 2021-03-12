
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;

public class MainGraph {


    public static void main(String args[]) throws FileNotFoundException, IllegalArgumentException, IOException {

        int[][] data = new int[2][4];
        data[0][0] = 0;
        data[0][1] = 255;
        data[0][2] = 0;
        data[0][3] = 0;
        data[1][0] = 255;
        data[1][1] = 0;
        data[1][2] = 255;
        data[1][3] = 255;

        Img im = new Img(data);
        im.creerImage("inputDamierBruite.pgm");
        InstanceDebruitageGraphe instDeb = new InstanceDebruitageGraphe(im);
        System.out.println(instDeb);

        /*
        //tests du debruitage sur l'instance de la fin du sujet où l'on doit voir differents output selon la valeur de alpha
        int[][]data = new int[5][5];
        data[0][0]=255;
        data[0][1]=0;
        data[0][2]=255;
        data[0][3]=0;
        data[0][4]=255;
        data[1][0]=0;
        data[1][1]=255;
        data[1][2]=0;
        data[1][3]=255;
        data[1][4]=0;
        data[2][0]=0;
        data[2][1]=0;
        data[2][2]=255;
        data[2][3]=0;
        data[2][4]=255;
        data[3][0]=0;
        data[3][1]=255;
        data[3][2]=0;
        data[3][3]=255;
        data[3][4]=0;
        data[4][0]=255;
        data[4][1]=0;
        data[4][2]=255;
        data[4][3]=0;
        data[4][4]=255;
        */

        /*
        //tests sur instance damier

        //creation du damier
		int taille = 30;
		int[][]data3 = new int[taille][taille];
		for(int i=0;i<taille;i++){
			for(int j=0;j<taille;j++){
				int qi = i/10;
				int qj = j/10;
				if(qi%2==qj%2){
					data3[i][j]=0;
				}
				else{
					data3[i][j]=255;
				}
			}
		}

		//creation de "nbRectBruit" petits rectangles horizontaux dans lesquels on inverse les couleurs

		int nbRectBruit = 5;
		Random rand = new Random();

		for(int l=0;l<nbRectBruit;l++) {
			int ir = rand.nextInt(taille);
			int jr = rand.nextInt(taille);
			for (int i = 0; i < 7; i++) {
				for (int j = 0; j < 2; j++) {
					int ib = Math.min(ir+i,taille-1);
					int jb = Math.min(jr+j,taille-1);
					data3[ib][jb] = 255 - data3[ib][jb];
				}
			}
		}
		Img image3 = new Img(data3);
		image3.creerImage("inputDamierBruite.pgm");
		image3.resoudre(0.8).creerImage("outputdamier1.pgm");
		image3.resoudre(1).creerImage("outputdamier2.pgm");
		image3.resoudre(1.4).creerImage("outputdamier3.pgm");
		image3.resoudre(1.8).creerImage("outputdamier4.pgm");
		image3.resoudre(2.2).creerImage("outputdamier5.pgm");
		image3.resoudre(2.6).creerImage("outputdamier6.pgm");
         */

   }
}

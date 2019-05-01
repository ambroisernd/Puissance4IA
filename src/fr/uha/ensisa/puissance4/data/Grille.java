package fr.uha.ensisa.puissance4.data;


import fr.uha.ensisa.puissance4.util.Constantes;
import fr.uha.ensisa.puissance4.util.Constantes.Case;

import java.util.ArrayList;

@SuppressWarnings("Duplicates")
public class Grille {

	private Case[][] grille;

	public Grille()
	{
		grille= new Case[Constantes.NB_COLONNES][Constantes.NB_LIGNES];
		for(int i=0;i<Constantes.NB_COLONNES;i++)
			for(int j=0;j<Constantes.NB_LIGNES;j++)
			{
				grille[i][j] = Case.V;
			}
	}

	/**
	 * Constructeur qui créé une copie de la grille donné en argument
	 * @param original
	 */
	private Grille(Grille original)
	{
		this();
		for(int i = 0; i < Constantes.NB_COLONNES; i++){
			for (int j = 0; j < Constantes.NB_LIGNES; j++){
				grille[i][j] = original.getCase(j, i);
			}
		}
	}

	/**
	 * Renvoie le contenu de la case aux coordonnées données en argument
	 * @param ligne
	 * @param colonne
	 * @return
	 */
	public Case getCase(int ligne, int colonne)
	{
		return grille[colonne][ligne];
	}

	/**
	 * Indique s'il y a encore de la place dans la colonne indiquée
	 * @param colonne
	 * @return
	 */
	public boolean isCoupPossible(int colonne) {
		if(colonne>=0&&colonne<Constantes.NB_COLONNES)
		{
			return grille[colonne][Constantes.NB_LIGNES-1]==Case.V;
		}
		else
		{
			return false;
		}
	}

	/**
	 * Ajoute le symbole indiqué dans la colonne indiquée
	 * ce qui permet de jouer ce coup
	 * @param colonne
	 * @param symbole
	 */
	public void ajouterCoup(int colonne, Case symbole) {
		for(int j=0;j<Constantes.NB_LIGNES;j++)
		{
			if(grille[colonne][j] == Case.V)
			{
				grille[colonne][j]= symbole;
				break;
			}
		}

	}

	/**
	 * Renvoie l'état de la partie
	 * @param symboleJoueurCourant
	 * @param tour
	 * @return
	 */
	public int getEtatPartie(Case symboleJoueurCourant, int tour)
	{
		int victoire;
		if(symboleJoueurCourant==Constantes.SYMBOLE_J1)
		{
			victoire=Constantes.VICTOIRE_JOUEUR_1;
		}
		else
		{
			victoire=Constantes.VICTOIRE_JOUEUR_2;
		}
		int nbAlignes=0;
		//Vérification alignement horizontaux
		for(int i=0;i<Constantes.NB_LIGNES;i++)
		{
			for(int j=0;j<Constantes.NB_COLONNES;j++)
			{
				if(grille[j][i]==symboleJoueurCourant)
					nbAlignes++;
				else
					nbAlignes=0;
				if(nbAlignes==4)
				{
					return victoire;
				}
			}
			nbAlignes=0;
		}
		//Vérification alignement verticaux
		for(int j=0;j<Constantes.NB_COLONNES;j++)
		{
			for(int i=0;i<Constantes.NB_LIGNES;i++)
			{
				if(grille[j][i]==symboleJoueurCourant)
					nbAlignes++;
				else
					nbAlignes=0;
				if(nbAlignes==4)
				{
					return victoire;
				}
			}
			nbAlignes=0;
		}
		//Vérification alignement diagonaux (bas-droite vers haut-gauche)
		for(int i=0;i<Constantes.NB_LIGNES-3;i++)
			for(int j=0;j<Constantes.NB_COLONNES-3;j++)
			{
				for(int x=0;i+x<Constantes.NB_LIGNES&&j+x<Constantes.NB_COLONNES;x++)
				{
					if(grille[j+x][i+x]==symboleJoueurCourant)
						nbAlignes++;
					else
						nbAlignes=0;
					if(nbAlignes==4)
					{
						return victoire;
					}
				}
				nbAlignes=0;
			}

		//Vérification alignement diagonaux (bas-gauche vers haut-droit)
		for(int i=0;i<Constantes.NB_LIGNES-3;i++)
			for(int j=Constantes.NB_COLONNES-1;j>=3;j--)
			{
				for(int x=0;i+x<Constantes.NB_LIGNES&&j-x>=0;x++)
				{
					if(grille[j-x][i+x]==symboleJoueurCourant)
						nbAlignes++;
					else
						nbAlignes=0;
					if(nbAlignes==4)
					{
						return victoire;
					}
				}
				nbAlignes=0;
			}

		if(tour==Constantes.NB_TOUR_MAX)
		{
			return Constantes.MATCH_NUL;
		}

		return Constantes.PARTIE_EN_COURS;
	}

	/**
	 * Donne un score à la grille en fonction du joueur
	 * @param symboleJoueurCourant
	 * @return score de la grille courante
	 */
	public double evaluer(Case symboleJoueurCourant)
	{
		Case symboleAutreJoueur;
		if (symboleJoueurCourant == Case.X){
			symboleAutreJoueur = Case.O;
		}
		else {
			symboleAutreJoueur = Case.X;
		}
		//ArrayList<Integer> alignementX = verificationAlignements(symboleJoueurCourant);
		//ArrayList<Integer> alignementO = verificationAlignements(symboleAutreJoueur);
		//double poidsDeuxX = alignementX.get(0)*2;
		//double poidsDeuxO = alignementO.get(0)*3*(-1);
		//double poidsTroisX = alignementX.get(1)*8;
		//double poidsTroisO = alignementO.get(1)*10*(-1);
		//double poidsQuatreX = alignementX.get(2)*1;
		//double poidsQuatreO = alignementO.get(2)*-1;
		//System.out.println(verificationAlignements(symboleAutreJoueur));
		return verificationAlignements(symboleJoueurCourant)*1-verificationAlignements(symboleAutreJoueur)*(100)+gauss(symboleJoueurCourant)-gauss(symboleAutreJoueur);//+poidsDeuxO+poidsDeuxX+poidsTroisX+poidsTroisO+gauss(symboleJoueurCourant)*0.5-gauss(symboleAutreJoueur)*0.5;//+gauss(symboleJoueurCourant)-gauss(symboleAutreJoueur)
		//return poidsQuatreO+poidsQuatreX;
	}

	public int gauss(Case symboleJoueurCourant){
		int[][] evaluationTable = {{3, 4, 5, 7, 5, 4, 3},
				{4, 6, 8, 10, 8, 6, 4},
				{5, 8, 11, 13, 11, 8, 5},
				{5, 8, 11, 13, 11, 8, 5},
				{4, 6, 8, 10, 8, 6, 4},
				{3, 4, 5, 7, 5, 4, 3}};
		int utility = 138;
		int sum = 0;
		for (int i = 0; i < Constantes.NB_LIGNES; i++)
			for (int j = 0; j <Constantes.NB_COLONNES; j++)
				if (grille[j][i] == symboleJoueurCourant)
					sum += evaluationTable[i][j];
				else if (grille[j][i] != symboleJoueurCourant && grille[j][i] != Case.V)
					sum -= evaluationTable[i][j];
		return utility + sum;
	}

	private double verificationAlignements(Case symboleJoueurCourant) {
		int nbAlignes = 0;
		//Vérification alignement horizontaux
		for (int i = 0; i < Constantes.NB_LIGNES; i++) {
			for (int j = 0; j < Constantes.NB_COLONNES; j++) {
				if (grille[j][i] == symboleJoueurCourant)
					nbAlignes++;
				else
					nbAlignes = 0;
				if (nbAlignes == 4) {
					return 10_000;
				}
			}
			nbAlignes = 0;
		}
		//Vérification alignement verticaux
		for (int j = 0; j < Constantes.NB_COLONNES; j++) {
			for (int i = 0; i < Constantes.NB_LIGNES; i++) {
				if (grille[j][i] == symboleJoueurCourant)
					nbAlignes++;
				else
					nbAlignes = 0;
				if (nbAlignes == 4) {
					return 10_000;
				}
			}
			nbAlignes = 0;
		}
		//Vérification alignement diagonaux (bas-droite vers haut-gauche)
		for (int i = 0; i < Constantes.NB_LIGNES - 3; i++)
			for (int j = 0; j < Constantes.NB_COLONNES - 3; j++) {
				for (int x = 0; i + x < Constantes.NB_LIGNES && j + x < Constantes.NB_COLONNES; x++) {
					if (grille[j + x][i + x] == symboleJoueurCourant)
						nbAlignes++;
					else
						nbAlignes = 0;
					if (nbAlignes == 4) {
						return 10_000;
					}
				}
				nbAlignes = 0;
			}

		//Vérification alignement diagonaux (bas-gauche vers haut-droit)
		for (int i = 0; i < Constantes.NB_LIGNES - 3; i++)
			for (int j = Constantes.NB_COLONNES - 1; j >= 3; j--) {
				for (int x = 0; i + x < Constantes.NB_LIGNES && j - x >= 0; x++) {
					if (grille[j - x][i + x] == symboleJoueurCourant)
						nbAlignes++;
					else
						nbAlignes = 0;
					if (nbAlignes == 4) {
						return 10_000;
					}
				}
				nbAlignes = 0;
			}
		return 0;
	}

		/**
         * Clone la grille
         */
	public Grille clone()
	{
		Grille copy = new Grille(this);
		return copy;
	}

}

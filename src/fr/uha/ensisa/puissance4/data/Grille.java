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
		int nbColonnes = original.grille.length;
		int nbLigne = original.grille[0].length;
		for(int i = 0; i < nbColonnes; i++){
			for (int j = 0; j < nbLigne; j++){
				grille[i][j] = original.getCase(i, j);
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
		ArrayList<Integer> alignement = verificationAlignements(symboleJoueurCourant);
		double poidsDeux = alignement.get(0)*alignement.get(0);
		double poidsTrois = alignement.get(1)*alignement.get(1);
		double poidsQuatre = alignement.get(2)*100000; //infinie
		return poidsDeux+poidsTrois+poidsQuatre;
	}

	/**
	 *
	 * @param symboleJoueurCourant
	 * @return ArrayList contenant de 0 à 2 la liste du nombre d'alignements de 2 à 4
	 */

	private ArrayList<Integer> verificationAlignements(Case symboleJoueurCourant){
		//Vérification alignement horizontaux
		ArrayList<Integer> alignements = new ArrayList<Integer>();
		int deux = 0;
		int trois = 0;
		int quatre = 0;
		int nbAlignes=0;
		for(int i=0;i<Constantes.NB_LIGNES;i++)
		{
			for(int j=0;j<Constantes.NB_COLONNES;j++)
			{
				if(grille[j][i]==symboleJoueurCourant)
					nbAlignes++;
				else{
					switch (nbAlignes){
						case 2:
							deux = deux + 1;
							break;
						case 3:
							trois = trois + 1;
							break;
						case 4:
							quatre = quatre + 1;
							break;
						default:

					}
					nbAlignes=0;
				}
			}
			nbAlignes=0;
		}
		//Vérification alignement verticaux
		for(int j=0;j<Constantes.NB_COLONNES;j++) {
			for (int i = 0; i < Constantes.NB_LIGNES; i++) {
				if (grille[j][i] == symboleJoueurCourant)
					nbAlignes++;
				else {
					switch (nbAlignes) {
						case 2:
							deux = deux + 1;
							break;
						case 3:
							trois = trois + 1;
							break;
						case 4:
							quatre = quatre + 1;
							break;
						default:

					}
					nbAlignes = 0;
				}
			}
			nbAlignes = 0;
		}
		//Vérification alignement diagonaux (bas-droite vers haut-gauche)
		for(int i=0;i<Constantes.NB_LIGNES-3;i++)
			for(int j=0;j<Constantes.NB_COLONNES-3;j++)
			{
				for(int x=0;i+x<Constantes.NB_LIGNES&&j+x<Constantes.NB_COLONNES;x++)
				{
					if(grille[j+x][i+x]==symboleJoueurCourant)
						nbAlignes++;
					else {
						switch (nbAlignes) {
							case 2:
								deux = deux + 1;
								break;
							case 3:
								trois = trois + 1;
								break;
							case 4:
								quatre = quatre + 1;
								break;
							default:

						}
						nbAlignes = 0;
					}
				}
				nbAlignes = 0;
			}

		//Vérification alignement diagonaux (bas-gauche vers haut-droit)
		for(int i=0;i<Constantes.NB_LIGNES-3;i++)
			for(int j=Constantes.NB_COLONNES-1;j>=3;j--)
			{
				for(int x=0;i+x<Constantes.NB_LIGNES&&j-x>=0;x++)
				{
					if(grille[j-x][i+x]==symboleJoueurCourant)
						nbAlignes++;
					else {
						switch (nbAlignes) {
							case 2:
								deux = deux + 1;
								break;
							case 3:
								trois = trois + 1;
								break;
							case 4:
								quatre = quatre + 1;
								break;
							default:

						}
						nbAlignes = 0;
					}
				}
				nbAlignes = 0;
			}
		alignements.add(deux);
		alignements.add(trois);
		alignements.add(quatre);
		return alignements;
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

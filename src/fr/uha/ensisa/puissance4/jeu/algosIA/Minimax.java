package fr.uha.ensisa.puissance4.jeu.algosIA;

import fr.uha.ensisa.puissance4.data.Grille;
import fr.uha.ensisa.puissance4.data.Joueur;
import fr.uha.ensisa.puissance4.util.Constantes;

import java.util.ArrayList;


public class Minimax extends Algorithm {



	public Minimax(int levelIA, Grille grilleDepart, Joueur joueurActuel, int tour) {
		super(levelIA, grilleDepart, joueurActuel, tour);

	}

	@Override
	public int choisirCoup() {
		int coup = 0;
		double value = Double.MIN_VALUE;
		for (int i = 0; i<Constantes.NB_COLONNES; i++){
			double evaluation = Double.MIN_VALUE;
			if (grilleDepart.isCoupPossible(i)){
				Grille cpy = grilleDepart.clone();
				cpy.ajouterCoup(i, symboleMax);
				evaluation = minimax(cpy, levelIA, symboleMax);
				if (evaluation > value){
					value = evaluation;
					coup = i;
				}
			}
		}

		//blocage

		for (int i = 0; i<Constantes.NB_COLONNES; i++){
			double evaluation = Double.MIN_VALUE;
			if (grilleDepart.isCoupPossible(i)){
				Grille cpy = grilleDepart.clone();
				cpy.ajouterCoup(i, symboleMin);
				if (cpy.evaluer(symboleMin)>100000000){
					return i;
				}
			}
		}
		return coup;
	}

	public double minimax(Grille grille, int profondeur, Constantes.Case max){
		if (profondeur == 0 ||
				grille.getEtatPartie(max, tourDepart+profondeur)==Constantes.VICTOIRE_JOUEUR_1
				|| grille.getEtatPartie(max, tourDepart+profondeur)== Constantes.VICTOIRE_JOUEUR_2
				|| grille.getEtatPartie(max, tourDepart+profondeur)== Constantes.MATCH_NUL){
			return grille.evaluer(symboleMax);
		}
		if (max == symboleMax){
			double value = Double.MIN_VALUE;
			for (int i = 0; i<Constantes.NB_COLONNES; i++){
				Grille cpy = grille.clone();
				cpy.ajouterCoup(i, symboleMin);
				value = Math.max(value, minimax(cpy, profondeur - 1, symboleMin));
			}
			return value;
		}
		else {
			double value = Double.MAX_VALUE;
			for (int i = 0; i<Constantes.NB_COLONNES; i++){
				Grille cpy = grille.clone();
				cpy.ajouterCoup(i, symboleMax);
				value = Math.min(value, minimax(cpy, profondeur-1, symboleMax));
			}
			return value;
		}
	}








}

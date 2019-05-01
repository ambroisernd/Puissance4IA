package fr.uha.ensisa.puissance4.jeu.algosIA;

import fr.uha.ensisa.puissance4.data.Grille;
import fr.uha.ensisa.puissance4.data.Joueur;
import fr.uha.ensisa.puissance4.util.Constantes;

import java.util.ArrayList;

public class AlphaBeta extends Algorithm {

	int coup;

	public AlphaBeta(int levelIA, Grille grilleDepart, Joueur joueurActuel, int tour) {
		super(levelIA, grilleDepart, joueurActuel, tour);

	}

	@Override
	public int choisirCoup() {
		Grille grille = this.grilleDepart;
		int profondeur = this.levelIA;
		ArrayList<Integer> actions = new ArrayList<Integer>();
		for (int i = 0; i < Constantes.NB_COLONNES; i++) {
			if (grille.isCoupPossible(i)) {
				actions.add(i);
			}
		}
		boolean isMax = true;
		double maxEval = -Double.MAX_VALUE;
		double newEval;
		int maxCoup = 1;
		int newCoup = 1;

		for (Integer i : actions) {
			newCoup = i;
			double alpha = -Double.MAX_VALUE;
			double beta = Double.MAX_VALUE;
			newEval = alphabeta(symboleMax,grille.clone(), alpha, beta, profondeur, isMax);

			if (newEval > maxEval) {
				maxEval = newEval;
				maxCoup = newCoup;
			}
		}
		coup = maxCoup;
		return coup;
	}
/*
	double alphabeta(Constantes.Case symboleJoueurCourant, Grille grille, double alpha, double beta, int profondeur){
		if(profondeur == 0 ){
			return grille.evaluer(symboleJoueurCourant);
		}
		else if (symboleJoueurCourant == symboleMin){ //Si le joueur est Min (donc autre)
			double v = Double.MAX_VALUE;
			//Construction coups possible
			ArrayList<Integer> actions = new ArrayList<Integer>();
			for(int i = 0; i< Constantes.NB_COLONNES; i++){
				if(grille.isCoupPossible(i)){
					actions.add(i);
				}
			}

			for(Integer i : actions){
				Grille cpy = grille.clone();
				cpy.ajouterCoup(i,symboleMin);
				v = Math.min(v, alphabeta(symboleJoueurCourant, cpy, alpha, beta, profondeur -1));
				if(alpha > v){
					return v;
				}
				beta = Math.min(beta, v);
			}
		}
		else{
			double v = -Double.MAX_VALUE;

			//Construction coups possible
			ArrayList<Integer> actions = new ArrayList<Integer>();
			for(int i = 0; i< Constantes.NB_COLONNES; i++){
				if(grille.isCoupPossible(i)){
					actions.add(i);
				}
			}

			for(Integer i : actions){
				Grille cpy = grille.clone();
				cpy.ajouterCoup(i,symboleMax);
				v = Math.max(v, alphabeta(symboleJoueurCourant, cpy, alpha, beta, profondeur -1));
				if(v >= beta){
					return v;
				}
				alpha = Math.max(beta, v);
			}

		}
		return v;
	}
*/

	double alphabeta(Constantes.Case symboleJoueurCourant, Grille grille, double alpha, double beta, int profondeur, boolean isMax) {
		if (profondeur == 0) {
			return grille.evaluer(symboleJoueurCourant);
		} else {
			double meilleur = -Double.MAX_VALUE;
			//Construction coups possible
			ArrayList<Integer> actions = new ArrayList<Integer>();
			for (int i = 0; i < Constantes.NB_COLONNES; i++) {
				if (grille.isCoupPossible(i)) {
					actions.add(i);
				}
			}

			for (Integer i : actions) {
				Grille cpy = grille.clone();
				cpy.ajouterCoup(i, symboleJoueurCourant);
				double v;
				if (isMax){
					v = -alphabeta(symboleMin, cpy, -beta, -alpha, profondeur - 1, !isMax);
				}
				else{
					v = -alphabeta(symboleMax, cpy, -beta, -alpha, profondeur - 1, !isMax);
				}

				if (v > meilleur) {
					meilleur = v;
					if (meilleur > alpha) {
						alpha = meilleur;
						if (alpha >= beta) {
							return meilleur;
						}
					}
				}

			}
			return meilleur;
		}

	}
}

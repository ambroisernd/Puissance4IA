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

		double maxEval = -Double.MAX_VALUE;
		double newEval;
		int maxCoup = 1;
		int newCoup = 1;

		double alpha = Constantes.SCORE_MAX_NON_DEFINI;
		double beta = Constantes.SCORE_MIN_NON_DEFINI;

		for (Integer i : actions) {
			newCoup = i;
			Grille nouvelleGrille = grilleDepart.clone();
			nouvelleGrille.ajouterCoup(i, symboleMax);
			newEval = mini(grille.clone(), alpha, beta, profondeur);

			if (newEval >= maxEval) {
				maxEval = newEval;
				maxCoup = newCoup;
			}
		}
		coup = maxCoup;
		return coup;
	}

	public double maxi(Grille grille, double alpha, double beta, int profondeur) {
		double max = Constantes.SCORE_MAX_NON_DEFINI;
		if (profondeur == 0 || victoire(grille, symboleMin)) {
			return grille.evaluer(symboleMax);
		} else {
			for (int i = 0; i < Constantes.NB_COLONNES; i++) {
				if (grille.isCoupPossible(i)) {
					Grille grilleNew = grille.clone();
					grilleNew.ajouterCoup(i, symboleMax);
					double meilleur = mini(grilleNew, alpha, beta, profondeur - 1);

					if (meilleur > beta) {
						return meilleur;
					}
					max = Math.max(meilleur, max);
					alpha = Math.max(alpha, meilleur);
				}
			}
			return max;
		}
	}




	public double mini(Grille grille, double alpha, double beta, int profondeur) {
		double min = Constantes.SCORE_MIN_NON_DEFINI;
		if (profondeur==0 || victoire(grille, symboleMax)){
			return grille.evaluer(symboleMax);
		}
		else {
			for (int i = 0; i < Constantes.NB_COLONNES; i++) {
				if (grille.isCoupPossible(i)) {
					Grille grilleNew = grille.clone();
					grilleNew.ajouterCoup(i, symboleMax);
					double meilleur = maxi(grilleNew, alpha, beta, profondeur - 1);

					if (meilleur < alpha) {
						return meilleur;
					}
					min = Math.min(meilleur, min);
					beta = Math.min(beta, meilleur);
				}
			}
			return min;
		}
	}


	private boolean victoire(Grille grille, Constantes.Case joueur) {
		return (grille.verificationAlignements(joueur) > 0);
	}

}

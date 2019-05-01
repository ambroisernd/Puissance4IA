package fr.uha.ensisa.puissance4.jeu.algosIA;

import fr.uha.ensisa.puissance4.data.Grille;
import fr.uha.ensisa.puissance4.data.Joueur;
import fr.uha.ensisa.puissance4.util.Constantes;

import java.util.ArrayList;


@SuppressWarnings("Duplicates")
public class AlphaBeta extends Algorithm {

	public AlphaBeta(int levelIA, Grille grilleDepart, Joueur joueurActuel, int tour) {
		super(levelIA, grilleDepart, joueurActuel, tour);

	}

	@Override
	public int choisirCoup() {
		int colAJouer = 0;
		double meilleurValue = Constantes.SCORE_MAX_NON_DEFINI;
		for (int i =0; i < Constantes.NB_COLONNES; i++){
			if (grilleDepart.isCoupPossible(i)){
				Grille cpy = grilleDepart.clone();
				cpy.ajouterCoup(i, symboleMax);
				double value = mini(cpy, tourDepart, Constantes.SCORE_MAX_NON_DEFINI, Constantes.SCORE_MIN_NON_DEFINI);
				if (value >= meilleurValue){
					meilleurValue = value;
					colAJouer = i;
				}
			}

		}
		return colAJouer;
	}

	public double maxi(Grille grille, int profondeur, double alpha, double beta) {
		ArrayList<Integer> successors = new ArrayList<>();
		if (profondeur==tourMax || victoire(grille, symboleMin)){
			return grille.evaluer(symboleMax);
		}
		else {
			for (int i = 0; i < Constantes.NB_COLONNES; i++) {
				if (grille.isCoupPossible(i)) {
					successors.add(i);
				}
			}
			double value = Constantes.SCORE_MAX_NON_DEFINI;
			double tmp;
			for (int i = 0; i < successors.size(); i++) {

				tmp = mini(genSuccessor(grille, symboleMax, successors.get(i)), profondeur+1, alpha, beta);
				if (tmp > beta){
					return tmp;
				}
				value = Math.max(value, tmp);
				alpha = Math.max(alpha, value);
			}
			return value;
		}
	}


	public double mini(Grille grille, int profondeur, double alpha, double beta) {
		ArrayList<Integer> successors = new ArrayList<>();
		if (profondeur==tourMax || victoire(grille, symboleMax)){
			return grille.evaluer(symboleMax);
		}
		else {
			for (int i = 0; i < Constantes.NB_COLONNES; i++) {
				if (grille.isCoupPossible(i)) {
					successors.add(i);
				}
			}
			double value = Constantes.SCORE_MIN_NON_DEFINI;
			double tmp;
			for (int i = 0; i < successors.size(); i++) {
				tmp = maxi(genSuccessor(grille, symboleMin, successors.get(i)), profondeur+1, alpha, beta);
				if (tmp < alpha){
					return tmp;
				}
				value = Math.min(value, tmp);
				beta = Math.min(beta, value);
			}
			return value;
		}
	}

	private boolean victoire(Grille grille, Constantes.Case joueur) {
		return (grille.verificationAlignements(joueur) > 0);
	}

	public Grille genSuccessor(Grille grille, Constantes.Case symbolJoueur, int i) {
		Grille cpy = grille.clone();
		cpy.ajouterCoup(i, symbolJoueur);
		return cpy;
	}
}

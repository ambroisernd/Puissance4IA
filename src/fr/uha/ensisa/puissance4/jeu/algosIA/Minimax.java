package fr.uha.ensisa.puissance4.jeu.algosIA;

import fr.uha.ensisa.puissance4.data.Grille;
import fr.uha.ensisa.puissance4.data.Joueur;
import fr.uha.ensisa.puissance4.util.Constantes;

import java.util.ArrayList;


@SuppressWarnings("Duplicates")
public class Minimax extends Algorithm {

	int coup;
	public Minimax(int levelIA, Grille grilleDepart, Joueur joueurActuel, int tour) {
		super(levelIA, grilleDepart, joueurActuel, tour);

	}

	@Override
	public int choisirCoup() {
		double value = maxi(grilleDepart, levelIA);
		return coup;
	}

	public double maxi(Grille grille, int profondeur) {
		ArrayList<Integer> successors = new ArrayList<>();
		if (profondeur == 0 || victoire(grille)){
			double value = grille.evaluer(symboleMax);
			return value;
		}
		else {
			for (int i = 0; i < Constantes.NB_COLONNES; i++) {
				if (grille.isCoupPossible(i)) {
					successors.add(i);
				}
			}
		double value = -100000000;
		double tmp;
		for (int i = 0; i < successors.size(); i++) {

			tmp = mini(genSuccessor(grille, symboleMax, successors.get(i)), profondeur);
			if (tmp >= value){
				value = tmp;
				this.coup = successors.get(i);
			}
		}
			return value;
		}
	}


	public double mini(Grille grille, int profondeur) {
		ArrayList<Integer> successors = new ArrayList<>();
		if (profondeur == 0 || victoire(grille)){
			return grille.evaluer(symboleMax);
		}
		else {
			for (int i = 0; i < Constantes.NB_COLONNES; i++) {
				if (grille.isCoupPossible(i)) {
					successors.add(i);
				}
			}
			double value = 100000000;
			double tmp;
			for (int i = 0; i < successors.size(); i++) {
				tmp = maxi(genSuccessor(grille, symboleMin, successors.get(i)), profondeur-1);
				if (tmp <= value){
					value = tmp;
				}
			}
			return value;
		}
	}

	private boolean victoire(Grille grille) {
		return (grille.verificationAlignements(symboleMax) != 0 || grille.verificationAlignements(symboleMin) != 0);
	}

	public Grille genSuccessor(Grille grille, Constantes.Case symbolJoueur, int i) {
		Grille cpy = grille.clone();
		cpy.ajouterCoup(i, symbolJoueur);
		return cpy;
	}
}

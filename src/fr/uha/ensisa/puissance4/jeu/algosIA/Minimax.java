package fr.uha.ensisa.puissance4.jeu.algosIA;

import fr.uha.ensisa.puissance4.data.Grille;
import fr.uha.ensisa.puissance4.data.Joueur;
import fr.uha.ensisa.puissance4.util.Constantes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


@SuppressWarnings("Duplicates")
public class Minimax extends Algorithm {
	
	

	public Minimax(int levelIA, Grille grilleDepart, Joueur joueurActuel, int tour) {
		super(levelIA, grilleDepart, joueurActuel, tour);
		
	}

	@Override
	public int choisirCoup() {
		//À compléter
		
		return 0;
	}


	private Grille miniMaxDecision(Grille state){
		return null;
	}

	private double maxValue(Grille state){
		return 0;
	}

	private double minValue(Grille state){
		return 0;
	}





}

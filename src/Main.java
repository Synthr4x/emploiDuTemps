/*
 * 
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;

import salles.Salle;
import calendar.Calendrier;
import configuration.Config;
import configuration.Config.MODE;
import configuration.Constantes;
import ecole.Ecole;
import ecole.EmploiDuTemps;

/**
 * La classe Main.
 */
public class Main {

	public static void main(String[] args) {

		try {
			String pathActuel = new java.io.File(".").getCanonicalPath();
			Constantes.pathActuel = pathActuel;
		} catch (Exception e) {
			System.out.println("Impossible de récuperer le path");
		}

		@SuppressWarnings("unchecked")
		HashMap<Integer, Salle> m = (HashMap<Integer, Salle>) Config.load(
				"./config/ConfigSalles-1.0.0.conf", "1.0.0", MODE.CONF);

		EmploiDuTemps e = new EmploiDuTemps();
		Ecole.instancier();

		Iterator<?> i = m.keySet().iterator();
		Salle s = null;

		while (i.hasNext()) {
			int j = (Integer) i.next();
			s = m.get(j);
			Ecole.ajouterSalle(s);
		}

		Calendrier c = new Calendrier(e);

		// s = Ecole.getSalle("E+117");

		// System.out.println(s.getNomSalle());
		String lien = "./TESTTXT.txt";

		try {

			BufferedReader fichier = new BufferedReader(new FileReader(
					new File(lien)));

		} catch (Exception e1) {
			System.out.println("erreur => " + e1);
		}
	}
}
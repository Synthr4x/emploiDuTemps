/*
 * 
 */
package configuration;

import java.util.HashMap;

import salles.Amphi;
import salles.Cours;
import salles.Reunion;
import salles.Salle;
import salles.TD;
import salles.TP;
import configuration.Config.MODE;

/**
 * La classe configSalles.
 */
public class ConfigSalles {

	// Decrire les parametres de configuration
	//
	/**
	 * Configurer, retourne un dictionnaire contenant les salles existantes.
	 * 
	 * @return the hash map
	 */
	public static HashMap<Integer, Salle> configurer() {

		HashMap<Integer, Salle> config = new HashMap<Integer, Salle>();
		Salle s1 = null, s2 = null, s3 = null, s4 = null, s5 = null;

		try {
			s1 = new Amphi(20, -2, Batiment.O, true);
			s2 = new Cours(19, -1, Batiment.O, false);
			s3 = new Reunion(18, 0, Batiment.N);
			s4 = new TD(17, 1, Batiment.S);
			s5 = new TP(16, 2, Batiment.E, true, true, "Dell XPS");
		} catch (Throwable e) {
			e.printStackTrace();
		}

		config.put(1, s1);
		config.put(2, s2);
		config.put(3, s3);
		config.put(4, s4);
		config.put(5, s5);

		return config;
	}

	public static void main(String[] args) {
		HashMap<Integer, Salle> config = configurer();

		Config.store(config, "./config/ConfigSalles", "1.0.0", MODE.CONF);

	}
}

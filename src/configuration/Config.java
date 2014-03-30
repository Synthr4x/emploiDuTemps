/*
 * 
 */
package configuration;

// Classe Config - Services de gestion des fichiers de configuration
//
// Edition A 		: enregistrement et chargement d'un fichier de configuration
//
//    + Version 1.0.0	: version initiale, avec controle de la classe d'origine
//                        d'une configuration (HashMap ou LinkedHashMap)
//    + Version 1.1.0	: avec trace de l'execution d'un service dans la console
//    + Version 2.0.0   : prise en charge du standard XML
//	  + Version 2.1.0   : fusion des méthodes pour les différents formats de fichiers

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import salles.Amphi;
import salles.Cours;
import salles.Reunion;
import salles.Salle;
import salles.TD;
import ecole.Classe;
import ecole.EmploiDuTemps;
import ecole.Reservations;

// TODO: Auto-generated Javadoc
/**
 * La classe Config fournit deux services destinés à simplifier et à uniformiser
 * la gestion des fichiers de configuration.
 * 
 * Les services fournis sont :
 * 
 * load : charger un dictionnaire de configuration depuis le repertoire courant,
 * store : enregistrer un dictionnaire de configuration dans le repertoire
 * courant.
 * 
 * @author A. Thuaire
 * @author G. Quilici
 * @author R. Vivant
 * @author C. Pavinich
 * @author M. Gharbi
 */

public abstract class Config {

	/**
	 * L'enumérateur MODE.
	 */
	public enum MODE {

		/** Le mode txt. */
		TXT,
		/** Le mode conf. */
		CONF
	}

	/**
	 * La methode store enregistre dans un fichier du repertoire courant le
	 * dictionnaire de configuration fourni en premier parametre. Le fichier
	 * resultant est cree avec l'extension .conf. Le nom du fichier est forme
	 * automatiquement de la facon suivante : p2-p3.conf, où p2 et p3 designent
	 * les deux derniers parametres effectifs.
	 * 
	 * @param config
	 *            the config
	 * @param name
	 *            the name
	 * @param version
	 *            the version
	 * @param mode
	 *            the mode
	 * @return boolean Reussite de l'enregistrement
	 */

	// ------------------------------------------ *** Methode store
	//
	@SuppressWarnings("resource")
	public static boolean store(Object config, String name, String version,
			MODE mode) {
		String origine;
		String nomFichier;
		FileOutputStream f = null;
		FileWriter fw = null;
		Object out = null;

		// Controler l'existence de la configuration
		if (config == null)
			return false;

		// Controler la classe d'origine de la configuration
		origine = config.getClass().getName();
		if (origine != "java.util.HashMap"
				&& origine != "java.util.LinkedHashMap")
			return false;

		// Construire le nom du fichier de configuration
		nomFichier = name + "-" + version
				+ (mode == MODE.TXT ? ".txt" : ".conf");

		// Construire un fichier logique et le fichier physique associe
		try {
			fw = new FileWriter(nomFichier);
			f = new FileOutputStream(nomFichier);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		// Construire un flux de sortie base sur le fichier logique
		try {
			out = (mode == MODE.TXT ? (Object) new BufferedWriter(fw)
					: (Object) new ObjectOutputStream(f));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		// Construire un encodeur pour enregistrer l'objet
		PrintWriter fichierSortie = null;
		if (mode == MODE.TXT)
			fichierSortie = new PrintWriter((BufferedWriter) out);

		// Enregistrer la configuration dans le flux de sortie
		try {
			if (mode == MODE.TXT) {
				parserContenu(fichierSortie, config);
			} else {
				((ObjectOutputStream) out).writeObject(config);
			}
		} catch (Exception e) {

			e.printStackTrace();
			return false;
		}

		if (mode == MODE.TXT)
			fichierSortie.close();

		// Confirmer la reussite de l'enregistrement
		System.out.println("Enregistrement du fichier " + nomFichier + " : OK");
		return true;

	}

	/**
	 * Parser contenu.
	 * 
	 * @param fichierSortie
	 *            the fichier sortie
	 * @param config
	 *            the config
	 * @return true, if successful
	 * @throws Exception
	 *             the exception
	 */
	private static boolean parserContenu(PrintWriter fichierSortie,
			Object config) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("ok");
		try {
			// config peut etre un hashmap ou un linkedhashmap, faudra donc
			// vérifier le type et s'adapter

			// On commence à écrire dans le fichier en le signant
			fichierSortie.println("<" + Constantes.RESERVATION + ">");

			String typeData = config.getClass().getName();

			HashMap<String, HashMap<Creneau, Reservations>> calendrier = (HashMap<String, HashMap<Creneau, Reservations>>) config;

			// BOUCLER LA LISTE DES CALENDRIER DE RESERVATIONS POUR CHAQUE JOUR
			// DE L'ANNEE
			for (Entry<String, HashMap<Creneau, Reservations>> entry : calendrier
					.entrySet()) {
				String cle = entry.getKey();
				HashMap<Creneau, Reservations> valeur = entry.getValue();
				// On ecrira sous le format jj/mm/aaaa
				fichierSortie.println("<" + Constantes.CALENDRIER + " "
						+ Constantes.ATTR_DATE + "='" + cle + "'>");

				for (Entry<Creneau, Reservations> listeSalles : valeur
						.entrySet()) { // BOUCLER LA LISTE DES RESERVATIONS DE
										// SALLES (pour chaque créneau)
					Creneau creneau = listeSalles.getKey();
					Reservations reserv = listeSalles.getValue();

					fichierSortie.println("<" + Constantes.CRENEAU + " "
							+ Constantes.ATTR_NUMERO_CRENEAU + "='" + creneau
							+ "'>");

					// Maintenant, il faut faire afficher chaque salle reservé
					// avec le nom du prof, et les détails importants
					HashMap<Salle, Classe> salles = reserv.getReservation();

					for (Entry<Salle, Classe> listeReservation : salles
							.entrySet()) {
						Salle salle = listeReservation.getKey();
						Classe classe = listeReservation.getValue();

						String numeroSalle = Constantes.ATTR_NUMERO_SALLE
								+ "='" + salle.getNumero() + "'";
						String capaciteSalle = Constantes.ATTR_CAPACITE_SALLE
								+ "='" + salle.getCapacite() + "'";
						String numeroEtage = Constantes.ATTR_NUMERO_ETAGE
								+ "='" + salle.getEtage() + "'";
						String nomSalle = Constantes.ATTR_NOM_SALLE + "='"
								+ salle.getNomSalle() + "'";
						String batimentSalle = Constantes.ATTR_BATIMENT + "='"
								+ salle.getBatiment() + "'";

						String nomProf = Constantes.ATTR_NOM_PROF + "='"
								+ classe.getNomProf() + "'";
						String effectif = Constantes.ATTR_EFFECTIF + "='"
								+ classe.getEffectif() + "'";
						String type_salle = Constantes.ATTR_TYPE_SALLE + "='"
								+ salle.getClass().getName() + "'";

						String attributs = Constantes.SALLE + " " + numeroSalle
								+ " " + capaciteSalle + " " + numeroEtage + " "
								+ nomSalle + " " + batimentSalle + " "
								+ nomProf + " " + effectif + " " + type_salle;

						// BALISE SALLE
						fichierSortie.println("< " + attributs + " />");

					}

					fichierSortie.println("</" + Constantes.CRENEAU + ">"); // FERMER
																			// BALISE
																			// CRENEAU
				}

				fichierSortie.println("</" + Constantes.CALENDRIER + ">"); // FERMER
																			// BALISE
																			// CALENDRIER
																			// D'UN
																			// JOUR
																			// EN
																			// PARTICULIER
			}

			// On referme notre balise generale
			fichierSortie.println("</" + Constantes.RESERVATION + ">"); // FERMER
																		// BALISE
																		// GENERALE
																		// DES
																		// RESERVATION
		} catch (Exception e) {
			throw new Exception("Problème lors de la lecture du fichier..");
		}
		return false;

	}

	/**
	 * La methode load charge le contenu d'un fichier de configuration depuis le
	 * repertoire courant. Le dictionnaire resultant est la valeur de retour. Le
	 * fichier origine possede obligatoirement l'extension .conf. Le nom du
	 * fichier est forme automatiquement de la facon suivante : p1-p2.conf, ou
	 * p1 et p2 designent les deux parametres effectifs.
	 * 
	 * @param name
	 *            the name
	 * @param version
	 *            the version
	 * @param mode
	 *            the mode
	 * @return Object Dictionnaire de configuration charge
	 */
	// ------------------------------------------ *** Methode load
	//
	@SuppressWarnings("resource")
	public static Object load(String name, String version, MODE mode) {
		String origine;
		String nomFichier;
		FileInputStream f = null;
		InputStream in = null;
		InputStreamReader isr = null;
		Object resultat;
		BufferedReader br = null;

		// Construire le nom du fichier source de la configuration
		nomFichier = name;

		// Construire un fichier logique correspondant
		try {
			f = new FileInputStream(nomFichier);
		}

		catch (FileNotFoundException e) {
			System.out.println(e);
			JOptionPane.showMessageDialog(null,
					"Il n'y a pas d'emploi du temps enregistré.");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		// Construire un flux d'entree base sur le fichier logique
		try {
			if (mode == MODE.TXT)
				isr = new InputStreamReader(f);
			else if (mode == MODE.CONF)
				in = new ObjectInputStream(f);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		// Construire un decodeur pour lire l'objet enregistre
		if (mode == MODE.TXT)
			br = new BufferedReader(isr);

		// Acquerir et deserialiser le flux d'entree
		try {
			resultat = (mode == MODE.TXT) ? recupereFichier(br)
					: ((ObjectInputStream) in).readObject();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		if(resultat == null){
			JOptionPane.showMessageDialog(null,
					"Le fichier n'est pas valide.");
			return null;
		}
		
		// Controler la classe d'origine du resultat
		origine = resultat.getClass().getName();
		if (origine != "java.util.HashMap"
				&& origine != "java.util.LinkedHashMap")
			return null;
		
		// Confirmer la reussite du chargement
		System.out.println("Chargement du fichier " + nomFichier + " : OK");
		return resultat;
	}

	/**
	 * Renvoit true si le fichier est valide au protocole qu'on a établit, false
	 * sinon.
	 * 
	 * @param br
	 *            the br
	 * @return true, if is valide fichier
	 * @throws Exception
	 *             the exception
	 */

	public static HashMap recupereFichier(BufferedReader br) throws Exception {

		Stack<String> fifo = new Stack();
		EmploiDuTemps result = new EmploiDuTemps();
		DatePerso date_calendrier_actuel = null;
		Creneau creneau_actuel = null;
		
		try {

			String ligneLu; // On compte lire le fichier ligne par ligne et
							// analyser son contenu
			/*
			 * On va maintenant lire le contenu du fichier, en tentant de
			 * determiner si celui ci est valide ou non. Pour cela, on divisera
			 * le probleme en plusieurs étapes: _ boucler le fichier et lire les
			 * elements ligne par ligne _ determiner le type de la balise _ pour
			 * chaque balise, determiner si aucun attribut ne manque (il faudra
			 * donc gérer autant de cas de figure qu'il n'y a de balises)
			 * 
			 * On commence donc par etablir 2 regex, une pour les balise
			 * ouvrante <..>, et une pour les balise fermante </..> La logique
			 * sera simple, on crééera une pile (LIFO), et a chaque balise
			 * ouvrante, on mettra son nom au dessus de la pile. Ainsi, si
			 * lorsqu'on repere une balise fermante, le nom doit correspondre
			 * avec le sommet de la pile, sinon il y a un probleme (return
			 * false). Si la pile n'est pas vide en sortant de la boucle, il y a
			 * aussi un probleme. Dans notre cas, on décide de ne pas permettre
			 * cela, on renverra donc false. Ensuite, on ira chercher pour
			 * chaque balise (et de maniere spécifique), l'ensemble des valeurs
			 * des attributs sous-jacent grace au regex (en verifiant que ces
			 * attributs existent..). Il faudra vérifier le format de chaque
			 * attribut afin de s'assurer qu'on respecte le protocole, il faudra
			 * aussi s'assurer qeu tout les attr sont la.
			 */
			
			String verifBaliseOuvrante = "<[a-zA-Z0-9]+[^>]*>";
			String verifValiseFermante = "</(\\s)*[a-zA-Z0-9]+>";

			Pattern baliseOuvrante = Pattern.compile(verifBaliseOuvrante);
			Pattern baliseFermante = Pattern.compile(verifValiseFermante);

			while ((ligneLu = br.readLine()) != null) { // On boucle ligne par
														// ligne le contenu tant
														// qu'on arrive pas au
														// bout

				Matcher isOuvrante = baliseOuvrante.matcher(ligneLu);
				Matcher isFermante = baliseFermante.matcher(ligneLu);

				Pattern p = Pattern.compile("[a-zA-Z0-9]+(='[a-zA-Z0-9]*')*");
				Matcher m = p.matcher(ligneLu);
				String nomBalise = ""; // Nom de la balise actuelle

				// On recupere le nom de la balise
				if (m.find()) {
					nomBalise = m.group();
				}

				if (isOuvrante.find()) {
					Hashtable<String, String> attr = retourneAttributs(ligneLu); // Retourne
																					// tout
																					// les
																					// attributs
																					// de
																					// la
																					// balise
																					// actuelle
					String topPile = null;

					// RECUPERATION DU TOP DE LA PILE
					if (!fifo.isEmpty()) { // Si non vide
						topPile = fifo.lastElement(); // On récupère le sommet
														// de la pile, nous
														// servira pour les
														// vérifications
					}

					/** GESTION DE LA BALISE */
					if (nomBalise.equals(Constantes.RESERVATION)) {
						fifo.push(nomBalise); // On a une balise ouvrante donc
												// on l'ajoute en haut de la
												// pile
					}

					else if (nomBalise.equals(Constantes.CALENDRIER)
							&& (topPile.equals(Constantes.RESERVATION))) {
						fifo.push(nomBalise);
						String date;

						// VERIFICATION DES ATTRIBUTS
						if ((date = attr.get(Constantes.ATTR_DATE)) == null) {
							return null;
						} else { // Si existe, on vérifie que le format est
									// correct

							if (!verifie_format_date(date)) {
								return null;
							}else{
							
								String[] nombres = date.split("/");

								int jour = Integer.parseInt(nombres[0]);
								int mois = Integer.parseInt(nombres[1]);
								int annee = Integer.parseInt(nombres[2]);
							
								date_calendrier_actuel = new DatePerso(jour, mois, annee);
							}

						}
					}

					else if (nomBalise.equals(Constantes.CRENEAU)
							&& (topPile.equals(Constantes.CALENDRIER))) {

						fifo.push(nomBalise);
						String num_creneau;
						
						// VERIF ATTRIBUTS
						if ((num_creneau = attr
								.get(Constantes.ATTR_NUMERO_CRENEAU)) == null) {
							return null;
						} else { // verifier format
							
							if(num_creneau.equals((Creneau.UN).toString())){
								creneau_actuel = Creneau.UN;
							}else if(num_creneau.equals((Creneau.DEUX).toString())){
								creneau_actuel = Creneau.DEUX;
							}else if(num_creneau.equals((Creneau.TROIS).toString())){
								creneau_actuel = Creneau.TROIS;
							}else if(num_creneau.equals((Creneau.QUATRE).toString())){
								creneau_actuel = Creneau.QUATRE;
							}else{
								return null;
							}

						}
					}

					else if (nomBalise.equals(Constantes.SALLE)
							&& (topPile.equals(Constantes.CRENEAU))) {
						// Pour salle, on ne l'ajoute pas a la pile car c'est
						// une balise sur une ligne
						String num_salle, capacite_salle, num_etage, nom_salle, batiment_salle, nom_prof, effectif, type_salle;
						
						// VERIFICATION ATTRIBUTS
						if ((num_salle = attr.get(Constantes.ATTR_NUMERO_SALLE)) == null
								|| (capacite_salle = attr
										.get(Constantes.ATTR_CAPACITE_SALLE)) == null
								|| (num_etage = attr
										.get(Constantes.ATTR_NUMERO_ETAGE)) == null
								|| (nom_salle = attr
										.get(Constantes.ATTR_NOM_SALLE)) == null
								|| (batiment_salle = attr
										.get(Constantes.ATTR_BATIMENT)) == null
								|| (nom_prof = attr
										.get(Constantes.ATTR_NOM_PROF)) == null
								|| (effectif = attr
										.get(Constantes.ATTR_EFFECTIF)) == null
								|| (type_salle = attr
										.get(Constantes.ATTR_TYPE_SALLE)) == null) {

							return null;
						} else { // Sinon, si tout existe; on vérifie les formats
							int numeroS = Integer.parseInt(num_salle); 
							int capaciteS = Integer.parseInt(capacite_salle);
							int etageS = Integer.parseInt(num_etage);
							int effectifS = Integer.parseInt(effectif);

							// Vérif sur les string
							if (nom_prof.length() < 2) {
								return null;
							}
							if (nom_salle.length() < 1) {
								return null;
							}
							boolean bat_valide = false;
							Batiment bat = null;

							for (Batiment b : Batiment.values()) {

								if (b.name().equals(batiment_salle)) {
									bat_valide = true;
									bat = b;
								}
							}

							if (!bat_valide) { // Si le nom du batiment n'est pas bon, on sort
								return null;
							}

							Reservations res = new Reservations();
							// Initialisation des classes
							Classe classe = new Classe(nom_prof, effectifS);
							Salle salle = null;
							
							try {
								if (type_salle.equals("salles.TD")) {
									salle = new TD(numeroS, etageS, bat);

								} else if (type_salle.equals("salles.TP")) {
									// TP salleTP = new TP(numeroS, etageS,
									// bat);
									// res.ajouterReservation(salleTD, classe);
								} else if (type_salle.equals("salles.Reunion")) {
									salle = new Reunion(numeroS,
											etageS, bat);

								} else if (type_salle.equals("salles.Cours")) {
									salle = new Cours(numeroS, etageS,
											bat, false);

								} else if (type_salle.equals("salles.Amphi")) {
									salle = new Amphi(numeroS, etageS,
											bat, false);
									
								} else {
									return null;
								}
								
								res.ajouterReservation(salle, classe);
								// Enfin, on ajoute la reservation à l'objet
								result.addReservation(date_calendrier_actuel, creneau_actuel, salle, classe);
								
							} catch (Throwable e) {
								// TODO Auto-generated catch block
								
								return null;
							}
						}

					}

					else {
						// BALISE INCONNU, ON SORT
						
						return null;
					}
				} else if (isFermante.find()) {

					String hautPile = fifo.pop();
					// On vérifie si l'élément est égale au nom de la balise
					// fermante.. si c'est le cas, c'est bon, sinon erreur
					if (!nomBalise.equals(hautPile)) {
						return null;
					}
				} else { // Sinon, on passe au prochain
					continue;
				}
			}

		} catch (Exception e) {
			return null;
		}

		// On vérifie si la stack est vide
		if (!fifo.isEmpty())
			return null;
		
		System.out.println("VALIDE");
		 System.out.println("=>"+result.getEmploiDuTemps().size());
		return result.getEmploiDuTemps();
	}

	/**
	 * Lire contenu.
	 * 
	 * @param br
	 *            the br
	 * @return the object
	 */
	private static Object lireContenu(BufferedReader br) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Prend une balise en argument et retourne la liste des attributs. On la
	 * retourne sous forme de tableau associatif ( nom => valeur ) retourne null
	 * si aucun attribut reconnu
	 * 
	 * @param balise
	 *            the balise
	 * @return the hashtable
	 */
	private static Hashtable<String, String> retourneAttributs(String balise) {
		Hashtable<String, String> result = new Hashtable<String, String>();

		String[] elem = balise.split(" ");

		for (int i = 0; i < elem.length; i++) {
			Pattern pat = Pattern.compile("(.*)='(.*)'");
			Matcher mat = pat.matcher(elem[i]);

			if (mat.find()) { // ATTRIBUT
				String[] nom_valeur = mat.group().split("="); // On récupere le
																// contenu et la
																// valeur
				String nom_attr = nom_valeur[0];
				String valeur_attr = nom_valeur[1].substring(1,
						nom_valeur[1].length() - 1); // On enleve les quotes

				result.put(nom_attr, valeur_attr);
			}
		}

		return result;
	}

	/**
	 * Vérifie le format d'une date (sous forme de string) passé en paramètre.
	 * On se donne pour objectif de déterminer si une chaine est sous la forme
	 * "jj/mm/aaaa" avec jj,mm,aaaa entiers Pour commencer, on splitte la chaine
	 * via le délimiteur "/", il faut que le tableau obtenu soit exactement de
	 * longueur 3, ensuite on s'assure que le jour soit un nombre compris entre
	 * 1 et 31, et que le mois soit compris entre 1 et 12. (Pour l'année, on se
	 * contentera simplement de vérifier qu'elle est supérieur à 2000.
	 * 
	 * @param date
	 *            the date
	 * @return true, if successful
	 */

	private static boolean verifie_format_date(String date) {
		String[] nombres = date.split("/");

		if (nombres.length != 3) {
			return false;
		}

		int jour = Integer.parseInt(nombres[0]);
		int mois = Integer.parseInt(nombres[1]);
		int annee = Integer.parseInt(nombres[2]);

		// VERIF
		if ((jour < 1) || (jour > 31)) {
			return false;
		} else if ((mois < 1) || (mois > 12)) {
			return false;
		} else if ((annee < 2000)) {
			return false;
		}

		// Sinon, c'est bon
		return true;
	}
}

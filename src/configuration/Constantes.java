/*
 * 
 */
package configuration;

/**
 * Constantes de notre projet.
 * 
 * @author mazen, christian
 */

public abstract class Constantes {

	// Path
	public static String pathActuel = "/"; // Non static car il faudra
											// l'initialiser

	// Capacites
	/** La Constante CAPACITE_SALLE_TP. */
	public static final int CAPACITE_SALLE_TP = 24;

	/** La Constante CAPACITE_SALLE_TD. */
	public static final int CAPACITE_SALLE_TD = 48;

	/** La Constante CAPACITE_AMPHI_MIN. */
	public static final int CAPACITE_AMPHI_MIN = 150;

	/** La Constante CAPACITE_AMPHI_MAX. */
	public static final int CAPACITE_AMPHI_MAX = 300;

	// Etage
	/** La Constante SOUS_SOL. */
	public static final char SOUS_SOL = '-';

	/** La Constante REZ_DE_CHAUSSE. */
	public static final char REZ_DE_CHAUSSE = ' ';

	/** La Constante ETAGE. */
	public static final char ETAGE = '+';

	// Nom fichier config
	/** La Constante NOM_EDT. */
	public static final String NOM_EDT = "emploiDuTemps";

	/** La Constante TITRE_MINI_FENETRE_RESERVATION. */
	public static final String TITRE_MINI_FENETRE_RESERVATION = "Reserver le ";

	/** La Constante VERSION_EDT. */
	public static final String VERSION_EDT = "1.0.0";

	/** La Constante TITRE. */
	public static final String TITRE = "Calendrier de réservation";

	/** La Constante LARGEUR_FENETRE. */
	public static final int LARGEUR_FENETRE = 800;

	/** La Constante HAUTEUR_FENETRE. */
	public static final int HAUTEUR_FENETRE = 400;

	/** La Constante CRENEAU1. */
	public static final String CRENEAU1 = "8h - 10h";

	/** La Constante CRENEAU2. */
	public static final String CRENEAU2 = "10h15 - 12h15";

	/** La Constante CRENEAU3. */
	public static final String CRENEAU3 = "13h30 - 15h30";

	/** La Constante CRENEAU4. */
	public static final String CRENEAU4 = "15h45 - 17h45";

	/** NOM BALISE FICHIER SAUVEGARDE EMPLOI DU TEMPS. */
	// Balise englobante du fichier
	public static final String RESERVATION = "reservations";

	// Balise contenant nos calendrier de reservations pour 1 jour
	/** La Constante CALENDRIER. */
	public static final String CALENDRIER = "calendrier";

	/** La Constante ATTR_DATE. */
	public static final String ATTR_DATE = "date"; // Attribut de la balise
													// CALENDRIER, correspond à
													// la date attribué au
													// calendrier

	// Balise correspondant à chaque creneau de la journée!
	/** La Constante CRENEAU. */
	public static final String CRENEAU = "creneau";

	/** La Constante ATTR_NUMERO_CRENEAU. */
	public static final String ATTR_NUMERO_CRENEAU = "numero_creneau"; // Numéro
																		// du
																		// créneau
																		// actuel
																		// (va
																		// de 1
																		// à 4
																		// ce
																		// qui
																		// correspond
																		// aux 4
																		// créneaux
																		// possible
																		// dans
																		// une
																		// journée)

	// Balise correpsondant à chaque salle reservé (dans un créneau)
	/** La Constante SALLE. */
	public static final String SALLE = "salle";
	// Attributs de la balise salle, donne les indications sur la reservation en
	// question
	/** La Constante ATTR_NUMERO_SALLE. */
	public static final String ATTR_NUMERO_SALLE = "numeroSalle";

	/** La Constante ATTR_CAPACITE_SALLE. */
	public static final String ATTR_CAPACITE_SALLE = "capaciteSalle";

	/** La Constante ATTR_NUMERO_ETAGE. */
	public static final String ATTR_NUMERO_ETAGE = "numeroEtage";

	/** La Constante ATTR_NOM_SALLE. */
	public static final String ATTR_NOM_SALLE = "nomSalle";

	/** La Constante ATTR_BATIMENT. */
	public static final String ATTR_BATIMENT = "batimentSalle";

	/** La Constante ATTR_NOM_PROF. */
	public static final String ATTR_NOM_PROF = "nomProf";

	/** La Constante ATTR_EFFECTIF. */
	public static final String ATTR_EFFECTIF = "effectif";

	/** La Constante ATTR_TYPE_SALLE. */
	public static final String ATTR_TYPE_SALLE = "type_salle";

	public static final int HAUT_FEN_RESERV = 150;

	public static final int LARG_FEN_RESERV = 300;

	public static final String TEXTE_BOUTON_RESERVER = "Réserver à cette date";

	public static final String MESSAGE_AUCUNE_SALLE_LIBRE = "Il n'y a aucune salle libre à ce creneau là !";

	public static final String TEXTE_CHOIX_SALLE = "Choissisez une salle";

	public static final String TEXTE_SAISIE_NOM = "Nom : ";

	public static final String TESTE_SAISIE_EFFECTIF = "Effectif de votre classe : ";
}

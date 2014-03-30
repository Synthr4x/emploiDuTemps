package ecole;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import salles.Salle;
import configuration.Config;
import configuration.Config.MODE;
import configuration.Creneau;
import configuration.DatePerso;

// TODO: Auto-generated Javadoc
/**
 * Cette classe représentera le "calendrier" des réservations. En effet, le
 * tableau représentera les 7 jours de la semaine actuelle, ou chaque jour sera
 * composé des 4 créneaux horaires (
 * "2 créneaux le matin, 8H-10H et 10H15-12H15, et 2 créneaux l’après-midi, 13H30-15H30 et 15H45-17H45"
 * ). Pour chaque créneau, on aura une liste des salles réservé (dans ce créneau
 * là).
 * 
 * On compte modéliser cela de la même manière dont est modéliser la tableau de
 * hash => Un HashMap ou le hashMap "sous-jacent" contiendrait les objets Salle.
 * Ainsi, pour savoir si une salle est reservé le lundi au crénaux horaire n°1,
 * on irait lister les salle contenu dans le premier hashmap correspondant à
 * lundi, et comparerait les numéros de salle avec le numéro de salle que l'on
 * souhaite réserver.
 * 
 * Le choix de notre algorithme privilégie la vitesse d'éxecution au dépend du
 * stockage mémoire (qui sera plus important) car on choisit de créer autant
 * d'objet "Reservations" qu'il y a de types de salles. La raison qui nous
 * pousse à faire ce choix étant la quantité de mémoire vive importante que
 * possède les ordinateurs aujourd'hui.
 * 
 * Le choix des hashmap imbriqué nous évite l'altérnative avec les arrays qui
 * nous forcerait à faire 4 * 7 initialisations.
 * 
 * @author mazen, christian
 */

//
public class EmploiDuTemps implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8636625482365083941L;

	/** The emploi du temps. */
	private HashMap<String, HashMap<Creneau, Reservations>> emploiDuTemps = null;

	/**
	 * Instantiates a new emploi du temps.
	 */
	public EmploiDuTemps() {
		setEmploiDuTemps(new HashMap<String, HashMap<Creneau, Reservations>>());
	}

	/**
	 * Instantiates a new emploi du temps.
	 * 
	 * @param nom
	 *            the nom
	 * @param version
	 *            the version
	 */
	public EmploiDuTemps(String nom, String version) {
		setEmploiDuTemps(new HashMap<String, HashMap<Creneau, Reservations>>());

		this.charger(nom, version);
	}

	/**
	 * Adds the reservation.
	 * 
	 * @param date
	 *            la date
	 * @param numCreneau
	 *            the num creneau
	 * @param salle
	 *            the salle
	 * @param classe
	 *            the classe
	 * @return true, if successful
	 * @throws Throwable
	 *             the Throwable
	 */
	public boolean addReservation(DatePerso date, Creneau numCreneau,
			Salle salle, Classe classe) throws Throwable {

		if (classe.getEffectif() > salle.getCapacite()) {
			throw new Throwable("La salle ne peut contenir autant d'élèves");
		}

		Reservations r = null;
		if (!this.getEmploiDuTemps().containsKey(date.toString())) {
			r = new Reservations();
			r.ajouterReservation(salle, classe);

			HashMap<Creneau, Reservations> h = new HashMap<Creneau, Reservations>();
			h.put(numCreneau, r);
			this.emploiDuTemps.put(date.toString(), h);

			return true;
		} else {
			HashMap<Creneau, Reservations> h = this.getEmploiDuTemps().get(
					date.toString());
			r = h.get(numCreneau);

			if (r == null) {
				r = new Reservations();
				r.ajouterReservation(salle, classe);

				h.put(numCreneau, r);
				this.emploiDuTemps.put(date.toString(), h);

				return true;

			} else {
				if (r.ajouterReservation(salle, classe)) {
					h.put(numCreneau, r);
					this.emploiDuTemps.put(date.toString(), h);
					return true;
				} else
					return false;
			}

		}

	}

	/**
	 * Retourne la liste des salles réservées trouvées à un jour donné.
	 * 
	 * @param date
	 *            la date
	 * @param numCreneau
	 *            the num creneau
	 * 
	 * @return the reservees
	 */
	public LinkedList<Salle> getReservees(DatePerso date, Creneau numCreneau) {

		LinkedList<Salle> list = new LinkedList<Salle>();
		HashMap<Creneau, Reservations> dateCherchee = this.getEmploiDuTemps()
				.get(date.toString());

		Reservations r = null;

		if (dateCherchee == null) {
			return null;
		}

		r = dateCherchee.get(numCreneau);

		if (r == null)
			return null;

		HashMap<Salle, Classe> m = r.getReservation();
		Salle s = null;
		Iterator<Salle> j = m.keySet().iterator();
		while (j.hasNext()) {
			s = j.next();
			list.add(s);
		}

		return list;

	}

	/**
	 * Retourne la liste des salles libres trouvées à une date donné, et à un
	 * créneau.
	 * 
	 * @param date
	 *            la date
	 * @param numCreneau
	 *            the num creneau
	 * @return the reservees
	 */
	public LinkedList<Salle> getLibres(DatePerso date, Creneau numCreneau) {

		// On considère d'abord que toutes les salles sont libres
		LinkedList<Salle> sallesLibres = Ecole.getSalles();

		HashMap<Creneau, Reservations> dateCherchee = this.getEmploiDuTemps()
				.get(date.toString());
		Reservations r = null;

		// Toutes les salles sont disponibles ce jour là
		if (dateCherchee == null) {
			return sallesLibres;
		} else if (!dateCherchee.containsKey(numCreneau)) {
			return sallesLibres;
		} else {

			r = dateCherchee.get(numCreneau);

			HashMap<Salle, Classe> m = r.getReservation();
			Salle s = null;
			Iterator<Salle> j = m.keySet().iterator();

			while (j.hasNext()) {
				s = j.next();
				sallesLibres.remove(s);
			}

			return sallesLibres;
		}

	}

	/**
	 * Annuler reservation.
	 * 
	 * @param jour
	 *            the jour
	 * @param mois
	 *            the mois
	 * @param annee
	 *            the annee
	 * @param numCreneau
	 *            the num creneau
	 * @param salle
	 *            the salle
	 * @param classe
	 *            the classe
	 * @return true, if successful
	 */
	public boolean annulerReservation(DatePerso date, Creneau numCreneau,
			Salle salle) {

		Reservations r = null;

		if (!this.getEmploiDuTemps().containsKey(date.toString())) {
			System.out.println("Aucune réservation a cette date");
			return false;
		} else {
			HashMap<Creneau, Reservations> h = this.getEmploiDuTemps().get(
					date.toString());
			r = h.get(numCreneau);

			if (r == null) {
				System.out.println("Aucune réservation a ce creneau");
				return false;
			} else {
				if (r.annulerReservation(salle)) {
					h.put(numCreneau, r);
					return true;
				} else {
					System.out.println("Cette salle n'a pas été réservée");
					return false;
				}
			}

		}

	}

	/**
	 * Sauvegarder.
	 * 
	 * @param nom
	 *            the nom
	 * @param version
	 *            the version
	 * @return true, if successful
	 */
	public boolean sauvegarder(String nom, String version) {
		return Config.store(this.getEmploiDuTemps(), nom, version, MODE.CONF);
	}

	/**
	 * Charger.
	 * 
	 * @param nom
	 *            the nom
	 * @param version
	 *            the version
	 */
	@SuppressWarnings("unchecked")
	public boolean charger(String nom, String version) {

		HashMap conf = (HashMap<DatePerso, HashMap<Creneau, Reservations>>) Config
				.load(nom, version, MODE.CONF);

		if (conf == null)
			return false;
		else {
			this.setEmploiDuTemps(conf);
		}
		return true;
	}

	/**
	 * Gets the emploi du temps.
	 * 
	 * @return the emploi du temps
	 */
	public HashMap<String, HashMap<Creneau, Reservations>> getEmploiDuTemps() {
		return this.emploiDuTemps;
	}

	/**
	 * Sets the emploi du temps.
	 * 
	 * @param emploiDuTemps
	 *            the emploi du temps
	 */
	public void setEmploiDuTemps(
			HashMap<String, HashMap<Creneau, Reservations>> emploiDuTemps) {
		this.emploiDuTemps = emploiDuTemps;
		System.out.println(this.emploiDuTemps.size());
	}

}

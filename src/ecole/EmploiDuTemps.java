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
 * Cette classe repr�sentera le "calendrier" des r�servations. En effet, le
 * tableau repr�sentera les 7 jours de la semaine actuelle, ou chaque jour sera
 * compos� des 4 cr�neaux horaires (
 * "2 cr�neaux le matin, 8H-10H et 10H15-12H15, et 2 cr�neaux l�apr�s-midi, 13H30-15H30 et 15H45-17H45"
 * ). Pour chaque cr�neau, on aura une liste des salles r�serv� (dans ce cr�neau
 * l�).
 * 
 * On compte mod�liser cela de la m�me mani�re dont est mod�liser la tableau de
 * hash => Un HashMap ou le hashMap "sous-jacent" contiendrait les objets Salle.
 * Ainsi, pour savoir si une salle est reserv� le lundi au cr�naux horaire n�1,
 * on irait lister les salle contenu dans le premier hashmap correspondant �
 * lundi, et comparerait les num�ros de salle avec le num�ro de salle que l'on
 * souhaite r�server.
 * 
 * Le choix de notre algorithme privil�gie la vitesse d'�xecution au d�pend du
 * stockage m�moire (qui sera plus important) car on choisit de cr�er autant
 * d'objet "Reservations" qu'il y a de types de salles. La raison qui nous
 * pousse � faire ce choix �tant la quantit� de m�moire vive importante que
 * poss�de les ordinateurs aujourd'hui.
 * 
 * Le choix des hashmap imbriqu� nous �vite l'alt�rnative avec les arrays qui
 * nous forcerait � faire 4 * 7 initialisations.
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
			throw new Throwable("La salle ne peut contenir autant d'�l�ves");
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
	 * Retourne la liste des salles r�serv�es trouv�es � un jour donn�.
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
	 * Retourne la liste des salles libres trouv�es � une date donn�, et � un
	 * cr�neau.
	 * 
	 * @param date
	 *            la date
	 * @param numCreneau
	 *            the num creneau
	 * @return the reservees
	 */
	public LinkedList<Salle> getLibres(DatePerso date, Creneau numCreneau) {

		// On consid�re d'abord que toutes les salles sont libres
		LinkedList<Salle> sallesLibres = Ecole.getSalles();

		HashMap<Creneau, Reservations> dateCherchee = this.getEmploiDuTemps()
				.get(date.toString());
		Reservations r = null;

		// Toutes les salles sont disponibles ce jour l�
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
			System.out.println("Aucune r�servation a cette date");
			return false;
		} else {
			HashMap<Creneau, Reservations> h = this.getEmploiDuTemps().get(
					date.toString());
			r = h.get(numCreneau);

			if (r == null) {
				System.out.println("Aucune r�servation a ce creneau");
				return false;
			} else {
				if (r.annulerReservation(salle)) {
					h.put(numCreneau, r);
					return true;
				} else {
					System.out.println("Cette salle n'a pas �t� r�serv�e");
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

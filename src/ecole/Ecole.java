/*
 * 
 */
package ecole;

import java.util.Iterator;
import java.util.LinkedList;

import salles.Salle;

/**
 * The Class Ecole.
 */
public class Ecole {

	/** The salles. */
	private static LinkedList<Salle> salles = null;

	/**
	 * Instancier.
	 */
	public static void instancier() {
		if (salles == null)
			salles = new LinkedList<Salle>();
	}

	/**
	 * Ajouter salle.
	 * 
	 * @param salle
	 *            the salle
	 * @return true, if successful
	 */
	public static boolean ajouterSalle(Salle salle) {
		if (!salles.contains(salle)) {
			salles.add(salle);
			return true;
		} else
			return false;
	}

	/**
	 * Enlever salle.
	 * 
	 * @param nom
	 *            the nom
	 * @return true, if successful
	 */
	public static boolean enleverSalle(String nom) {
		Iterator<Salle> i = salles.iterator();
		Salle s = null;

		while (i.hasNext()) {
			s = i.next();

			if (s.getNomSalle().equals(nom)) {
				salles.remove(s);
				return true;
			}
		}

		return false;
	}

	/**
	 * Enlever salle.
	 * 
	 * @param s
	 *            the s
	 * @return true, if successful
	 */
	public static boolean enleverSalle(Salle s) {
		if (salles.contains(s)) {
			salles.remove(s);
			return true;
		}

		return false;
	}

	/**
	 * Gets the salle.
	 * 
	 * @param nom
	 *            the nom
	 * @return the salle
	 */
	public static Salle getSalle(String nom) {
		Iterator<Salle> i = salles.iterator();
		Salle s = null;

		while (i.hasNext()) {
			s = i.next();

			if (s.getNomSalle().equals(nom)) {
				return s;
			}
		}

		return null;

	}

	/**
	 * Gets the salles.
	 * 
	 * @return the salles
	 */
	public static LinkedList<Salle> getSalles() {
		return (LinkedList<Salle>) salles.clone();
	}

}

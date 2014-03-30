/*
 * 
 */
package ecole;

import java.io.Serializable;
import java.util.HashMap;

import salles.Salle;

/**
 * The Class Reservations.
 */
public class Reservations implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6201443811199303843L;
	/** The reservation. */
	private HashMap<Salle, Classe> reservation;

	/**
	 * Instantiates a new reservations.
	 */
	public Reservations() {
		setReservation(new HashMap<Salle, Classe>());
	}

	/**
	 * Ajouter reservation.
	 * 
	 * @param salle
	 *            the salle
	 * @param classe
	 *            the classe
	 * @return true, if successful
	 */
	public boolean ajouterReservation(Salle salle, Classe classe) {

		if (!getReservation().containsKey(salle)) {
			getReservation().put(salle, classe);

			return true;
		}

		return false;
	}

	/**
	 * Gets the classe.
	 * 
	 * @param s
	 *            the s
	 * @return the classe
	 */
	public Classe getClasse(Salle s) {
		return getReservation().get(s);
	}

	/**
	 * Annuler reservation.
	 * 
	 * @param salle
	 *            the salle
	 * @return true, if successful
	 */
	public boolean annulerReservation(Salle salle) {
		return (getReservation().remove(salle) != null) ? true : false;
	}

	/**
	 * Gets the reservation.
	 * 
	 * @return the reservation
	 */
	public HashMap<Salle, Classe> getReservation() {
		return reservation;
	}

	/**
	 * Sets the reservation.
	 * 
	 * @param reservation
	 *            the reservation
	 */
	private void setReservation(HashMap<Salle, Classe> reservation) {
		this.reservation = reservation;
	}
}

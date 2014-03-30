/*
 * 
 */
package configuration;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * The Class DatePerso.
 */
public class DatePerso implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3165076526666808313L;

	/** The jour. */
	private int jour;

	/** The mois. */
	private int mois;

	/** The annee. */
	private int annee;

	/**
	 * Instantiates a new date perso.
	 */
	public DatePerso() {
		this((GregorianCalendar) GregorianCalendar.getInstance());
	}

	/**
	 * Instantiates a new date perso.
	 * 
	 * @param g
	 *            the g
	 */
	public DatePerso(GregorianCalendar g) {
		int j = g.get(Calendar.DAY_OF_MONTH);
		int m = g.get(Calendar.MONTH) + 1;
		int a = g.get(Calendar.YEAR);

		setJour(j);
		setMois(m);
		setAnnee(a);
	}

	public DatePerso(int j, int m, int a) {
		setJour(j);
		setMois(m);
		setAnnee(a);
	}
	
	/**
	 * Equals.
	 * 
	 * @param d
	 *            the d
	 * @return true, if successful
	 */
	public boolean equals(DatePerso d) {

		if (d.getAnnee() == this.annee && d.getJour() == this.jour
				&& d.getMois() == this.mois)
			return true;

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return this.getJour() + "/" + this.getMois() + "/" + this.getAnnee();
	}

	/**
	 * Gets the jour.
	 * 
	 * @return the jour
	 */
	public int getJour() {
		return jour;
	}

	/**
	 * Sets the jour.
	 * 
	 * @param jour
	 *            the new jour
	 */
	public void setJour(int jour) {
		this.jour = jour;
	}

	/**
	 * Gets the mois.
	 * 
	 * @return the mois
	 */
	public int getMois() {
		return mois;
	}

	/**
	 * Sets the mois.
	 * 
	 * @param mois
	 *            the new mois
	 */
	public void setMois(int mois) {
		this.mois = mois;
	}

	/**
	 * Gets the annee.
	 * 
	 * @return the annee
	 */
	public int getAnnee() {
		return annee;
	}

	/**
	 * Sets the annee.
	 * 
	 * @param annee
	 *            the new annee
	 */
	public void setAnnee(int annee) {
		this.annee = annee;
	}

}

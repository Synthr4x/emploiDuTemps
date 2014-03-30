/*
 * 
 */
package salles;

import java.io.Serializable;

import configuration.Batiment;
import configuration.Constantes;

// TODO: Auto-generated Javadoc
/**
 * The Class Cours.
 */
public class Cours extends Salle implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6622964901901025184L;

	// Une salle de cours peut avoir un video projecteur (ou non? dans le doute
	// je met un boolean)
	/** The a vid proj. */
	private boolean aVidProj = false;

	/**
	 * Instantiates a new cours.
	 */
	public Cours() {
	}

	/**
	 * Instantiates a new cours.
	 * 
	 * @param numero
	 *            the numero
	 * @param etage
	 *            the etage
	 * @param batiment
	 *            the batiment
	 * @param aVidProj
	 *            the a vid proj
	 * @throws Throwable
	 *             the throwable
	 */
	public Cours(int numero, int etage, Batiment batiment, boolean aVidProj)
			throws Throwable {
		super(numero, etage, batiment);

		// On admet que les salles de cours sont des salles de TD
		this.capacite = Constantes.CAPACITE_SALLE_TD;

		this.setVidProj(aVidProj);
	}

	/**
	 * Gets the vid proj.
	 * 
	 * @return the vid proj
	 */
	public boolean getVidProj() {
		return aVidProj;
	}

	/**
	 * Sets the vid proj.
	 * 
	 * @param aVidProj
	 *            the new vid proj
	 */
	public void setVidProj(boolean aVidProj) {
		this.aVidProj = aVidProj;
	}

	/*
	 * 
	 * @see salles.Salle#getType()
	 */
	@Override
	public String getType() {
		return "Cours";
	}

}

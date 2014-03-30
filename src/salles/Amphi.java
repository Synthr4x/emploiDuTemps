/*
 * 
 */
package salles;

import java.io.Serializable;

import configuration.Batiment;
import configuration.Constantes;

// TODO: Auto-generated Javadoc
/**
 * The Class Amphi.
 */
public class Amphi extends Salle implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 487550096495144925L;

	// Un amphi peut avoir un video projecteur (ou non? dans le doute je met un
	// boolean)
	/** The a vid proj. */
	private boolean aVidProj = false;

	/**
	 * Instantiates a new amphi.
	 */
	public Amphi() {
	}

	/**
	 * Instantiates a new amphi.
	 * 
	 * @param numero
	 *            the numero
	 * @param etage
	 *            the etage
	 * @param batiment
	 *            the batiment
	 * @param aProjecteur
	 *            the a projecteur
	 * @throws Throwable
	 *             the throwable
	 */
	public Amphi(int numero, int etage, Batiment batiment, boolean aProjecteur)
			throws Throwable {

		super(numero, etage, batiment);

		this.capacite = Constantes.CAPACITE_AMPHI_MAX;
		this.setVidProj(aProjecteur);

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
	 * (non-Javadoc)
	 * 
	 * @see salles.Salle#getType()
	 */
	@Override
	public String getType() {
		return "Amphi";
	}

}

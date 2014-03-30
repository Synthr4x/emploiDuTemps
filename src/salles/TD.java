/*
 * 
 */
package salles;

import java.io.Serializable;

import configuration.Batiment;
import configuration.Constantes;

// TODO: Auto-generated Javadoc
/**
 * The Class TD.
 */
public class TD extends Salle implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8780293068037984858L;

	/**
	 * Instantiates a new td.
	 */
	public TD() {
	}

	/**
	 * Instantiates a new td.
	 * 
	 * @param numero
	 *            the numero
	 * @param etage
	 *            the etage
	 * @param batiment
	 *            the batiment
	 * @throws Throwable
	 *             the throwable
	 */
	public TD(int numero, int etage, Batiment batiment) throws Throwable {
		super(numero, etage, batiment);

		this.capacite = Constantes.CAPACITE_SALLE_TD;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salles.Salle#getType()
	 */
	@Override
	public String getType() {
		return "TD";
	}

}

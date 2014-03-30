/*
 * 
 */
package salles;

import java.io.Serializable;

import configuration.Batiment;
import configuration.Constantes;

// TODO: Auto-generated Javadoc
/**
 * The Class Reunion.
 */
public class Reunion extends Salle implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3845729926062572297L;

	/**
	 * Instantiates a new reunion.
	 */
	public Reunion() {
	}

	/**
	 * Instantiates a new reunion.
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
	public Reunion(int numero, int etage, Batiment batiment) throws Throwable {
		super(numero, etage, batiment);

		// Admettons que les réunions se font dans des salles de TD
		this.capacite = Constantes.CAPACITE_SALLE_TD;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salles.Salle#getType()
	 */
	@Override
	public String getType() {
		return "Reunion";
	}

}

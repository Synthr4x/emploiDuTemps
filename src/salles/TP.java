/*
 * 
 */
package salles;

import java.io.Serializable;

import configuration.Batiment;
import configuration.Constantes;

// TODO: Auto-generated Javadoc
/**
 * The Class TP.
 */
public class TP extends Salle implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3603285248014479402L;

	// Chaque salle de TP a un accès réseau et des prises électriques, si le
	// type des machines installées = "void"
	// ou autre valeur choisie unique, ça veut dire que cest une salle de TP
	// avec machines portables
	/** The acces reseau. */
	private boolean accesReseau = false;

	/** The prises electriques. */
	private boolean prisesElectriques = false;

	/** The type machines. */
	private String typeMachines = "void";

	/**
	 * Instantiates a new tp.
	 */
	public TP() {
	}

	/**
	 * Instantiates a new tp.
	 * 
	 * @param numero
	 *            the numero
	 * @param etage
	 *            the etage
	 * @param batiment
	 *            the batiment
	 * @param reseau
	 *            the reseau
	 * @param prises
	 *            the prises
	 * @param typeMachines
	 *            the type machines
	 * @throws Throwable
	 *             the throwable
	 */
	public TP(int numero, int etage, Batiment batiment, boolean reseau,
			boolean prises, String typeMachines) throws Throwable {

		super(numero, etage, batiment);

		this.setAccesReseau(reseau);
		this.setPrisesElectriques(prises);
		this.setTypeMachines(typeMachines);

		this.capacite = Constantes.CAPACITE_SALLE_TP;

	}

	/**
	 * Gets the acces reseau.
	 * 
	 * @return the acces reseau
	 */
	public boolean getAccesReseau() {
		return accesReseau;
	}

	/**
	 * Sets the acces reseau.
	 * 
	 * @param accesReseau
	 *            the new acces reseau
	 */
	public void setAccesReseau(boolean accesReseau) {
		this.accesReseau = accesReseau;
	}

	/**
	 * Gets the prises electriques.
	 * 
	 * @return the prises electriques
	 */
	public boolean getPrisesElectriques() {
		return prisesElectriques;
	}

	/**
	 * Sets the prises electriques.
	 * 
	 * @param prisesElectriques
	 *            the new prises electriques
	 */
	public void setPrisesElectriques(boolean prisesElectriques) {
		this.prisesElectriques = prisesElectriques;
	}

	/**
	 * Gets the type machines.
	 * 
	 * @return the type machines
	 */
	public String getTypeMachines() {
		return typeMachines;
	}

	/**
	 * Sets the type machines.
	 * 
	 * @param typeMachines
	 *            the new type machines
	 */
	public void setTypeMachines(String typeMachines) {
		this.typeMachines = typeMachines;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salles.Salle#getType()
	 */
	@Override
	public String getType() {
		return "TP";
	}
}

/*
 * 
 */
package salles;

import java.io.Serializable;

import configuration.Batiment;

// TODO: Auto-generated Javadoc
/**
 * The Class Salle.
 */
public abstract class Salle implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3277037800309492006L;

	/** The numero. */
	protected int numero;

	/** The etage. */
	protected int etage;

	/** The batiment. */
	protected Batiment batiment;

	/** The capacite. */
	protected int capacite;

	/**
	 * Instantiates a new salle.
	 */
	public Salle() {
	}

	/**
	 * Instantiates a new salle.
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
	public Salle(int numero, int etage, Batiment batiment) throws Throwable {

		if (numero < 99 && numero >= 10)
			this.numero = numero;
		else
			throw new Exception(
					"Erreur => le numéro de la salle doit être compris entre 0 et 99");

		if (etage >= -9 && etage <= 9)
			this.etage = etage;
		else
			throw new Exception(
					"Erreur => l'étage doit être compris entre -9 et 9");

		this.batiment = batiment;

	}

	/**
	 * Gets the numero.
	 * 
	 * @return the numero
	 */
	public int getNumero() {
		return numero;
	}

	/**
	 * Gets the etage.
	 * 
	 * @return the etage
	 */
	public int getEtage() {
		return etage;
	}

	/**
	 * Gets the batiment.
	 * 
	 * @return the batiment
	 */
	public Batiment getBatiment() {
		return batiment;
	}

	/**
	 * Gets the capacite.
	 * 
	 * @return the capacite
	 */
	public int getCapacite() {
		return capacite;
	}

	/**
	 * Gets the nom salle.
	 * 
	 * @return the nom salle
	 */
	public String getNomSalle() {
		String num = "";

		// On rajoute un "0" quand le numéro est compris entre 0 et 9 pour pas
		// avoir des noms de salles comme O+34
		if (this.numero >= 0 && this.numero <= 9)
			num = "0" + this.numero;
		else
			num = "" + this.numero;

		if (etage < 0)
			return this.batiment + "-" + Math.abs(this.etage) + num;
		else
			return this.batiment + "+" + Math.abs(this.etage) + num;
	}

	/**
	 * Gets the type.
	 * 
	 * @return the type
	 */
	abstract public String getType();

}

/*
 * 
 */
package ecole;

import java.io.Serializable;

/**
 * La classe Classe.
 */
public class Classe implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7853263092413048919L;

	/** Nom du prof. */
	private String nomProf;

	/** L'effectif effectif. */
	private int effectif;

	/**
	 * Instancie une nouvelle classe.
	 * 
	 * @param nomProf
	 *            Le nom du prof
	 * @param effectif
	 *            L'effectif
	 */
	public Classe(String nomProf, int effectif) {
		this.setNomProf(nomProf);
		this.setEffectif(effectif);
	}

	/**
	 * Retourne le nom du prof.
	 * 
	 * @return the nom prof
	 */
	public String getNomProf() {
		return nomProf;
	}

	/**
	 * Sets the nom prof.
	 * 
	 * @param nomProf
	 *            the new nom prof
	 */
	public void setNomProf(String nomProf) {
		this.nomProf = nomProf;
	}

	/**
	 * Retourne l'effectif.
	 * 
	 * @return the effectif
	 */
	public int getEffectif() {
		return effectif;
	}

	/**
	 * Sets l'effectif.
	 * 
	 * @param effectif
	 *            Le nouvel effectif effectif
	 */
	public void setEffectif(int effectif) {
		this.effectif = effectif;
	}

}

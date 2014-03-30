/*
 * 
 */
package calendar;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import configuration.Constantes;
import configuration.Creneau;

/**
 * La classe ControleurCombo.
 */
public class ControleurCombo implements ItemListener {

	private Calendrier calen;

	public ControleurCombo(Calendrier c) {
		this.calen = c;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	@Override
	public void itemStateChanged(ItemEvent e) {

		// Mettre à jour le créneau selectionné
		if (e.getItem().equals(Constantes.CRENEAU1)) {
			calen.setCreneauChoisi(Creneau.UN);
		} else if (e.getItem().equals(Constantes.CRENEAU2)) {
			calen.setCreneauChoisi(Creneau.DEUX);

		} else if (e.getItem().equals(Constantes.CRENEAU3)) {
			calen.setCreneauChoisi(Creneau.TROIS);

		} else if (e.getItem().equals(Constantes.CRENEAU4)) {
			calen.setCreneauChoisi(Creneau.QUATRE);
		}

	}

}

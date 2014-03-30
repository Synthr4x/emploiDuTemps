/*
 * 
 */
package calendar;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;

import configuration.Creneau;
import ecole.EmploiDuTemps;

/**
 * La Classe ControleurBouton.
 */
public class ControleurBouton implements MouseListener {

	private Calendrier calen;
	private EmploiDuTemps edt;

	// Constructeur pour récupérer le calendrier et l'emploi du temps
	public ControleurBouton(Calendrier c, EmploiDuTemps edt) {
		this.calen = c;
		this.edt = edt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		Creneau creneauDemande = calen.getCreneauChoisi();

		FormReservation form = null;

		// Si il n'y a pas de salles libres à ce créneau
		if (this.edt.getLibres(this.calen.getDerniereDate(),
				this.calen.getCreneauChoisi()).size() == 0) {
			JOptionPane
					.showMessageDialog(null,
							"Il n'y a plus de salles disponibles ce jour là à ce créneau.");
		} else {
			// Sinon lancer le formulaire
			form = new FormReservation(this.edt, this.calen);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}

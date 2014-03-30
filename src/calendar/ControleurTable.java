/*
 * 
 */
package calendar;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;

import configuration.Creneau;
import ecole.Ecole;
import ecole.EmploiDuTemps;

/**
 * La classe ControleurTable.
 */
public class ControleurTable implements MouseListener {

	private Calendrier calen;
	private EmploiDuTemps edt;

	public ControleurTable(Calendrier c, EmploiDuTemps edt) {
		this.calen = c;
		this.edt = edt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent evt) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent e) {

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
	public void mousePressed(MouseEvent evt) {

		// Récupérer la cellule cliquée
		int row = calen.getTable().rowAtPoint(evt.getPoint());
		int col = calen.getTable().columnAtPoint(evt.getPoint());

		// Récupérer le nom de la salle
		String nomSalle = (String) calen.getTable().getModel()
				.getValueAt(row, col);

		// Si la cellule n'est pas vide
		if (nomSalle != null) {

			// Demander confirmation à l'utilisateur
			int option = JOptionPane.showConfirmDialog(null,
					"Voulez-vous annuler cette réservation ?",
					"Annulation de réservation",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE);

			if (option == JOptionPane.YES_OPTION) {

				// Récupérer le créneau
				Creneau c = null;
				if (col == 0)
					c = Creneau.UN;
				else if (col == 1)
					c = Creneau.DEUX;
				else if (col == 2)
					c = Creneau.TROIS;
				else if (col == 3)
					c = Creneau.QUATRE;

				// Annuler la réservation (Récupérer que le nom de la salle)
				this.edt.annulerReservation(this.calen.getDerniereDate(), c,
						Ecole.getSalle(nomSalle.substring(0, 5)));

				// Mettre à jour l'affichage de la table
				calen.majTable();
			}
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

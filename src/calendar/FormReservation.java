/*
 * 
 */
package calendar;

import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import salles.Salle;
import configuration.Constantes;
import ecole.Classe;
import ecole.Ecole;
import ecole.EmploiDuTemps;

/**
 * La Classe FormReservation.
 */
public class FormReservation {

	/** Le frame. */
	private JFrame frame;

	/** Le panel. */
	private JPanel panel;

	/** Le nom du prof. */
	private String nomProf;

	/** L'effectif. */
	private int effectif;

	/** La zone de saisie du nom. */
	private JTextField nom;

	/** La zone de saisie de l'effectif. */
	private JTextField eff;

	/** La liste déroulante. */
	private JComboBox<String> combo;

	private EmploiDuTemps edt;
	private Calendrier calen;

	/**
	 * Instancie un nouveau formulaire de reservation.
	 * 
	 * @param dateDemandee
	 *            la date demandée
	 * @param creneauDemande
	 *            le creneau demandée
	 */
	public FormReservation(EmploiDuTemps edt, Calendrier c) {

		this.calen = c;
		this.edt = edt;

		this.frame = new JFrame();
		this.panel = new JPanel();

		this.initialiserComposants();

	}

	/**
	 * Initialiser composants.
	 */
	private void initialiserComposants() {
		frame.setSize(Constantes.LARG_FEN_RESERV, Constantes.HAUT_FEN_RESERV);
		frame.setTitle(Constantes.TITRE_MINI_FENETRE_RESERVATION
				+ calen.getDerniereDate().getJour() + "/"
				+ calen.getDerniereDate().getMois() + "/"
				+ calen.getDerniereDate().getAnnee());

		frame.setResizable(false);
		frame.setLocationRelativeTo(null);

		panel.setLayout(new GridLayout(4, 2));

		combo = new JComboBox<String>();
		LinkedList<Salle> list = null;

		// Récupérer les salles libres
		list = edt.getLibres(calen.getDerniereDate(), calen.getCreneauChoisi());

		// Si il n'y a plus de salles libres à ce créneau
		if (list.size() == 0) {
			JOptionPane.showMessageDialog(null,
					Constantes.MESSAGE_AUCUNE_SALLE_LIBRE);
			// Fermer le formulaire
			frame.dispose();
		}

		// Ajouter les salles libres à la liste déroulante
		for (Salle s : list) {
			combo.addItem(s.getNomSalle() + " (" + s.getType() + ")");
		}

		JButton reserv, annul;

		// Créer les zones de saisie et les boutons
		this.nom = new JTextField();
		this.eff = new JTextField();

		reserv = new JButton("Reserver");
		annul = new JButton("Annuler");

		annul.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {

			}

			@Override
			public void mouseExited(MouseEvent arg0) {

			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// Fermer le formulaire
				frame.dispose();
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {

			}

		});

		// Ajouter l'écouteur au bouton réserver
		reserv.addMouseListener(new ControleurReserver());

		// Construire le panneau de formulaire
		panel.add(new JLabel(Constantes.TEXTE_CHOIX_SALLE));
		panel.add(combo);

		panel.add(new JLabel(Constantes.TEXTE_SAISIE_NOM));
		panel.add(nom);

		panel.add(new JLabel(Constantes.TESTE_SAISIE_EFFECTIF));
		panel.add(eff);

		panel.add(reserv);
		panel.add(annul);

		frame.add(panel);
		frame.setVisible(true);

	}

	/**
	 * La classe ControleurReserver.
	 */
	public class ControleurReserver implements MouseListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseClicked(MouseEvent arg0) {

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseEntered(MouseEvent arg0) {

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseExited(MouseEvent arg0) {

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
		 */
		@Override
		public void mousePressed(MouseEvent arg0) {

			// récupérer les informations de réservation
			String nomSaisi = nom.getText();
			String effSaisi = eff.getText();
			String salle = ((String) combo.getSelectedItem()).substring(0, 5);

			// Si le nom et l'effectif est valide
			if (nomSaisi.length() != 0 && effSaisi.length() != 0) {
				try {

					// Ajouter lé réservation
					edt.addReservation(calen.getDerniereDate(),
							calen.getCreneauChoisi(), Ecole.getSalle(salle),
							new Classe(nomSaisi, Integer.parseInt(effSaisi)));

					// Méttre à jour l'affichage de la JTable
					calen.majTable();

					// On ferme la fenetre
					frame.dispose();
				} catch (NumberFormatException e) {
					// Si l'effectif saisi n'a pu être converti en int
					JOptionPane.showMessageDialog(null,
							"Veuillez rentrer un effectif valide.");
				} catch (Throwable e) {
					// Si l'effectif est trop grand
					e.printStackTrace();
					JOptionPane.showMessageDialog(null,
							"La salle ne peut contenir autant d'élèves ");
				}
			} else {
				// Si l'utilisateur n'a pas renseigné tous les champs
				JOptionPane.showMessageDialog(null,
						"Veuillez renseigner tous les champs");
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseReleased(MouseEvent arg0) {

		}

	}
}

/*
 * 
 */
package calendar;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import salles.Salle;

import com.toedter.calendar.JCalendar;

import configuration.Constantes;
import configuration.Creneau;
import configuration.DatePerso;
import ecole.EmploiDuTemps;

/**
 * La Classe Calendrier.
 * 
 * C'est la classe qui représente la partie graphique du projet
 * 
 * @author Christian P. Mazen G.
 * 
 */
public class Calendrier extends JFrame {

	/** La Constante serialVersionUID. */
	private static final long serialVersionUID = 6965169604071304265L;

	/** Le calendrier. */
	private JCalendar calendrier = null;

	/** Le paneau. */
	private JPanel pan = new JPanel();

	/** La table. */
	private JTable table = null;

	/** La derniere date choisie. */
	private DatePerso derniereDate = null;

	/** Le dernier creneau choisi. */
	private Creneau creneauChoisi = Creneau.UN;

	/** L'emploi du temps */
	private EmploiDuTemps edt;

	/** La barre de menu */
	private JMenuBar barreMenu;

	/** Le menu fichier */
	private JMenu fichier;

	/** Le menuItem charger */
	private JMenuItem charger;
	/** Le menuItem enregistrer */
	private JMenuItem save;

	/** Le bouton réserver */
	private JButton bouton;

	/**
	 * Instancie un nouveau calendrier.
	 */
	public Calendrier(EmploiDuTemps edt) {

		// Initialiser les attributs et le frame
		this.edt = edt;
		this.derniereDate = new DatePerso();

		pan.setLayout(new BorderLayout());
		this.setContentPane(pan);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(Constantes.LARGEUR_FENETRE, Constantes.HAUTEUR_FENETRE);
		this.setTitle(Constantes.TITRE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);

		calendrier = new JCalendar();

		// Initialiser la JTable et la barre de menu
		JScrollPane scrollpane = initialiserTable();
		this.initialiserMenu();

		// Initialiser le bouton et ajouter l'écouteur
		setBouton(new JButton(Constantes.TEXTE_BOUTON_RESERVER));
		getBouton().addMouseListener(new ControleurBouton(this, this.edt));

		// Ajouter l'écouteur au calendrier
		calendrier.addPropertyChangeListener(new ControleurCalendrier(this,
				this.edt));

		// Initialiser la liste déroualante
		JComboBox<String> combo = new JComboBox<String>();
		combo.setPreferredSize(new Dimension(100, 20));
		combo.addItem(Constantes.CRENEAU1);
		combo.addItem(Constantes.CRENEAU2);
		combo.addItem(Constantes.CRENEAU3);
		combo.addItem(Constantes.CRENEAU4);
		combo.addItemListener(new ControleurCombo(this));

		// Ajouter les composatns au panneau principal
		pan.add(scrollpane, BorderLayout.WEST);
		pan.add(calendrier, BorderLayout.CENTER);

		// Ajouter un sous panneau pour le bouton de réservation et la lsite
		// déroulante
		JPanel pan2 = new JPanel();
		pan2.setLayout(new GridLayout(2, 1));
		pan2.add(combo);
		pan2.add(getBouton());

		// ajouter le sous panneau
		pan.add(pan2, BorderLayout.SOUTH);

		this.setVisible(true);

	}

	/**
	 * Méthode pour initialiser la barre de menu
	 */
	private void initialiserMenu() {

		this.barreMenu = new JMenuBar();
		this.fichier = new JMenu("Fichier");
		this.charger = new JMenuItem("Charger emploi du temps");
		this.save = new JMenuItem("Enregistrer emploi du temps");

		// Ajouter l'écouteur au bouton charger
		this.charger.addMouseListener(new MouseListener() {

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

				// Demander le chemin du fichier à l'utilisateur
				JFileChooser chooser = new JFileChooser(Constantes.pathActuel);

				String path = null;
				int valeur = chooser.showOpenDialog(Calendrier.this);

				if (valeur == JFileChooser.APPROVE_OPTION) {
					path = chooser.getSelectedFile().getAbsolutePath();

					boolean b = edt.charger(path, "1.0.0");
					if (b) {
						// Mettre à jour la JTable
						majTable();
					}

				}
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {

			}

		});

		this.save.addMouseListener(new MouseListener() {

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
				// Demander le chemin du fichier à l'utilisateur
				JFileChooser chooser = new JFileChooser(Constantes.pathActuel);

				String path = null;
				int valeur = chooser.showOpenDialog(Calendrier.this);

				if (valeur == JFileChooser.APPROVE_OPTION) {
					path = chooser.getSelectedFile().getName();
					edt.sauvegarder(path, "1.0.0");

				}
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {

			}

		});

		// Construire la barre de menu et l'ajouter au frame principal
		this.fichier.add(this.charger);
		this.fichier.add(this.save);
		this.barreMenu.add(this.fichier);
		this.setJMenuBar(this.barreMenu);

	}

	/**
	 * Initialiser la table.
	 * 
	 * @return La Jscroll pane contenant le tableau
	 */
	private JScrollPane initialiserTable() {
		// Construire un modèle de tableau
		DefaultTableModel model = new DefaultTableModel(0, 4) {

			/**
			 * 
			 */
			private static final long serialVersionUID = -6462681671254367203L;

			// Rendre les cellules non éditables
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};

		table = new JTable(model);

		// Nommer les 4 colonnes selon les créneau existants
		table.getColumnModel().getColumn(0).setHeaderValue(Constantes.CRENEAU1);
		table.getColumnModel().getColumn(1).setHeaderValue(Constantes.CRENEAU2);
		table.getColumnModel().getColumn(2).setHeaderValue(Constantes.CRENEAU3);
		table.getColumnModel().getColumn(3).setHeaderValue(Constantes.CRENEAU4);

		// Insérer la JTable dans un scroll pane au cas ou celui ci dépasse la
		// taille du frame (apparition d'une scroll bar dans ce cas)
		JScrollPane scrollpane = new JScrollPane(table);

		// Ajouter l'écouteur sur la JTable
		table.addMouseListener(new ControleurTable(this, this.edt));
		return scrollpane;
	}

	/**
	 * Retourne la table.
	 * 
	 * @return la table
	 */
	public JTable getTable() {
		return table;
	}

	/**
	 * Sets La table.
	 * 
	 * @param table
	 *            La nouvelle table
	 */
	public void setTable(JTable table) {
		this.table = table;
	}

	/**
	 * Retourne la derniere date.
	 * 
	 * @return la derniere date
	 */
	public DatePerso getDerniereDate() {
		return derniereDate;
	}

	/**
	 * Sets la derniere date.
	 * 
	 * @param derniereDate
	 *            La nouvelle derniere date
	 */
	public void setDerniereDate(DatePerso derniereDate) {
		this.derniereDate = derniereDate;
	}

	/**
	 * Retourne le creneau choisi.
	 * 
	 * @return Le creneau choisi
	 */
	public Creneau getCreneauChoisi() {
		return creneauChoisi;
	}

	/**
	 * Sets le creneau choisi.
	 * 
	 * @param creneauChoisi
	 *            La new creneau choisi
	 */
	public void setCreneauChoisi(Creneau creneauChoisi) {
		this.creneauChoisi = creneauChoisi;
	}

	/**
	 * Méthode servant à mettre à jour la table avec les réservations du jour
	 * choisi
	 */
	public void majTable() {

		// Vider la table avant de la re remplir
		this.viderTable();

		DatePerso c = (DatePerso) this.getDerniereDate();

		// Récupérer toutes les salles réservées
		LinkedList<Salle> sallesLibre_1 = edt.getReservees(c, Creneau.UN);
		LinkedList<Salle> sallesLibre_2 = edt.getReservees(c, Creneau.DEUX);
		LinkedList<Salle> sallesLibre_3 = edt.getReservees(c, Creneau.TROIS);
		LinkedList<Salle> sallesLibre_4 = edt.getReservees(c, Creneau.QUATRE);

		// Construire une liste contenant les 4 crénaux de réservation
		LinkedList<LinkedList<Salle>> tableau = new LinkedList<LinkedList<Salle>>();

		tableau.add(sallesLibre_1);
		tableau.add(sallesLibre_2);
		tableau.add(sallesLibre_3);
		tableau.add(sallesLibre_4);

		// Initialiser les indices servant à la construction de la JTable
		int ligneMax = -1;
		int creneau = -1;
		int ligneActuelle = -1;

		for (LinkedList<Salle> liste : tableau) {

			// incrémenter le créneau
			creneau++;

			// Si il n'y a pas de salles réservées à ce créneau passer au
			// créneau suivant
			if (liste == null) {
				continue;
			}

			Creneau cr = null;
			if (creneau == 0)
				cr = Creneau.UN;
			else if (creneau == 1)
				cr = Creneau.DEUX;
			else if (creneau == 2)
				cr = Creneau.TROIS;
			else if (creneau == 3)
				cr = Creneau.QUATRE;

			// Ré initialiser la ligne actuelle pour éditer depuis la première
			// ligne
			ligneActuelle = -1;

			// Parcourir les la liste des salles réservés à ce créneau
			for (Salle entry : liste) {
				// Passer à ligne suivante
				ligneActuelle++;

				// Si on doit insérer une valeur dans une ligne qui éxiste pas,
				// ajouter une ligne au tableau
				if (ligneActuelle > ligneMax) {
					ligneMax = ligneActuelle;
					((DefaultTableModel) table.getModel())
							.addRow(new Object[4]);
				}

				// Insérer le nom de la salle et du réservant dans la cellule
				table.setValueAt(
						entry.getNomSalle()
								+ " ("
								+ edt.getEmploiDuTemps()
										.get(this.getDerniereDate().toString())
										.get(cr).getClasse(entry).getNomProf()
								+ ")", ligneActuelle, creneau);
			}
		}

	}

	/**
	 * Méthode servant à vider la JTable
	 */
	private void viderTable() {
		int i = table.getRowCount();
		if (i == 0)
			return;

		for (int j = i - 1; j >= 0; --j)
			((DefaultTableModel) table.getModel()).removeRow(j);

	}

	public JButton getBouton() {
		return bouton;
	}

	private void setBouton(JButton bouton) {
		this.bouton = bouton;
	}
}

/*�
 * 
 */
package calendar;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.GregorianCalendar;

import configuration.DatePerso;
import ecole.EmploiDuTemps;

/**
 * La classe ControleurCalendrier.
 */
public class ControleurCalendrier implements PropertyChangeListener {

	private Calendrier calen;
	private EmploiDuTemps edt;

	public ControleurCalendrier(Calendrier c, EmploiDuTemps edt) {
		this.calen = c;
		this.edt = edt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.
	 * PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getSource().getClass().getName()
				.equals("com.toedter.calendar.JCalendar")
				&& evt.getNewValue() instanceof GregorianCalendar) {

			// Mettre � jour la date s�lectionn�e ainsi que la JTable affichant
			// les salles r�serv�es
			calen.setDerniereDate((new DatePerso((GregorianCalendar) evt
					.getNewValue())));
			calen.majTable();

		}

	}
}

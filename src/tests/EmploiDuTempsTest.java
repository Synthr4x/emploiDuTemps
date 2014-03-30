package tests;

import static org.junit.Assert.*;

import java.util.LinkedList;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import salles.Salle;
import salles.TD;

import configuration.Batiment;
import configuration.Creneau;
import configuration.DatePerso;

import ecole.Classe;
import ecole.EmploiDuTemps;

public class EmploiDuTempsTest {
	EmploiDuTemps edt;
	
	@Before
	public void setUp() throws Exception {
		edt = new EmploiDuTemps();
	}

	@Test
	public void testGetReservees() {
		DatePerso date = new DatePerso(12, 10, 2012);
		Creneau numCreneau = Creneau.UN;
		LinkedList<Salle> list = new LinkedList<Salle>();
		TD s = null;
		Classe classe = new Classe("Toto", 22);
		
		try {
			s = new TD(12, 2, Batiment.O);
			edt.addReservation(date, numCreneau, s, classe);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		list.add(s);
		
		Assert.assertEquals(list, edt.getReservees(date, numCreneau));
	}

	@Test
	public void testGetLibres() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetEmploiDuTemps() {
		EmploiDuTemps new_edt = new EmploiDuTemps();
		this.edt.setEmploiDuTemps(new_edt.getEmploiDuTemps());
				
		Assert.assertEquals(new_edt.getEmploiDuTemps(), this.edt.getEmploiDuTemps());
	}

}

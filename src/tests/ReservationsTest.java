package tests;

import static org.junit.Assert.*;

import java.util.HashMap;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import configuration.Batiment;

import salles.Salle;
import salles.TD;

import ecole.Classe;
import ecole.Reservations;

public class ReservationsTest {

	Reservations res;
	
	@Before
	public void setUp() throws Exception {
		res = new Reservations();
	}

	@Test
	public void testGetClasse() {

		TD salle = null;
		
		try {
			salle = new TD(22, 2, Batiment.O);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Classe classe = new Classe("Toto", 22);
		
		res.ajouterReservation(salle, classe);
		
		Assert.assertEquals(classe, res.getReservation().get(salle));
	}

	@Test
	public void testGetReservation() {
		HashMap<Salle, Classe> test = new HashMap();
		TD salle = null;
		try {
			salle = new TD(22, 2, Batiment.O);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Classe classe = new Classe("Toto", 22);
		test.put(salle, classe);
		
		res.ajouterReservation(salle, classe);
		
		Assert.assertEquals(test, res.getReservation());
	}
	

}

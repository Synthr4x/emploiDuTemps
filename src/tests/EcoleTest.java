package tests;

import static org.junit.Assert.*;

import java.util.LinkedList;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import configuration.Batiment;
import ecole.Ecole;

import salles.Salle;
import salles.TD;

public class EcoleTest {

	Ecole ec;
	@Before
	public void setUp() throws Exception {
		ec.instancier();
	}

	@Test
	public void testGetSalle() {
		TD s = null;
		
		try {
			s = new TD(10, 10, Batiment.E);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		String nom = s.getNomSalle();
		
		this.ec.ajouterSalle(s);
		
		Assert.assertEquals(s, this.ec.getSalle(nom));
	}

	@Test
	public void testGetSalles() {
		TD s = null;
		TD s2 = null;
		
		try {
			s = new TD(10, 10, Batiment.E);
			s2 = new TD(10, 23, Batiment.O);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		this.ec.ajouterSalle(s);
		this.ec.ajouterSalle(s2);
		LinkedList<Salle> listeTest = new LinkedList();
		
		Assert.assertEquals(listeTest, this.ec.getSalles());
	}

}

package tests;

import static org.junit.Assert.*;

import java.util.GregorianCalendar;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import configuration.DatePerso;


public class DatePersoTest {
	
	DatePerso date;
	
	@Before
	public void setUp() throws Exception {
		this.date = new DatePerso(21, 12, 2012);
	}
	
	@Test
	public void testGetJour() {
		int jour = 2;
		date.setJour(jour);
		
		Assert.assertEquals(jour, this.date.getJour());
	}

	@Test
	public void testGetMois() {
		int mois = 2;
		date.setMois(mois);
		
		Assert.assertEquals(mois, this.date.getMois());
	}

	@Test
	public void testGetAnnee() {
		int annee = 2000;
		date.setAnnee(annee);
		
		Assert.assertEquals(annee, this.date.getAnnee());
	}

}

package tests;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import ecole.Classe;

public class ClasseTest {

	Classe c;
	@Before
	public void setUp() throws Exception {
		Classe c = new Classe("Toto", 22);
	}

	@Test
	public void testGetNomProf() {
		String nom = "Titi";
		this.c.setNomProf(nom);
		
		Assert.assertEquals(nom, this.c.getNomProf());
	}

	@Test
	public void testGetEffectif() {
		int eff = 23;
		this.c.setEffectif(eff);
		
		Assert.assertEquals(eff, this.c.getEffectif());
	}

}

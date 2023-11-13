package ticketmachine;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class TicketMachineTest {
	private static final int PRICE = 50; // Une constante

	private TicketMachine machine; // l'objet à tester

	@BeforeEach
	public void setUp() {
		machine = new TicketMachine(PRICE); // On initialise l'objet à tester
	}

	@Test
	// On vérifie que le prix affiché correspond au paramètre passé lors de
	// l'initialisation
	// S1 : le prix affiché correspond à l’initialisation.
	void priceIsCorrectlyInitialized() {
		// Paramètres : valeur attendue, valeur effective, message si erreur
		assertEquals(PRICE, machine.getPrice(), "Initialisation incorrecte du prix");
	}

	@Test
	// S2 : la balance change quand on insère de l’argent
	void insertMoneyChangesBalance() {
		machine.insertMoney(10);
		machine.insertMoney(20);
		// Les montants ont été correctement additionnés
		assertEquals(10 + 20, machine.getBalance(), "La balance n'est pas correctement mise à jour");
	}

	@Test
		// S3 : on n’imprime pas le ticket si le montant inséré est insuffisant
	void nImprimePasSiPasAssezDargent() {
		machine.insertMoney(PRICE-1);
		assertFalse( machine.printTicket(), "Pas assez d'argent, la machine ne doit pas imprimer");
	}

	@Test
		// S4 : on imprime le ticket si le montant inséré est suffisant
	void imprimeTicket() {
		machine.insertMoney(PRICE+1);
		assertTrue( machine.printTicket(), "Montant suffisant");
	}

	@Test
		// S5 : Quand on imprime un ticket la balance est décrémentée du prix du ticket
	void nouveauMontant() {
		machine.insertMoney(PRICE+1);
		machine.printTicket();
		assertEquals( 1, machine.getBalance());
	}

	@Test
		// S6 : le montant collecté est mis à jour quand on imprime un ticket (pas avant)
	void montantMisAJour() {
		machine.insertMoney(PRICE);
		machine.printTicket();
		assertEquals( PRICE, machine.getTotal() );
	}

	@Test
		// S7 : refund() rend correctement la monnaie
	void monnaieRendueCorrectement() {
		machine.insertMoney(PRICE+60);
		assertEquals( 60, machine.refund() );
	}

	@Test
		// S8 : refund() remet la balance à zéro
	void refundSetBalanceNull(){
		machine.insertMoney(PRICE);
		machine.refund();
		assertEquals(0,machine.getBalance(), "La monnaie est bien rendue");
	}



	@Test
		// S9 : on ne peut pas insérerun montant négatif
	void montantNeg(){

		try {
			machine.insertMoney(-60); // Cette ligne doit lever une exception
			fail("Cet appel doit lever une exception");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
		// S10 : on ne peut pas créer de machine qui délivre des tickets dont le prix est négatif
	void montantNegTicket(){
		TicketMachine machine2;
		try {
			machine = new TicketMachine(-PRICE);
			fail("Cet appel doit lever une exception");
		} catch (IllegalArgumentException e) {
		}
	}



}

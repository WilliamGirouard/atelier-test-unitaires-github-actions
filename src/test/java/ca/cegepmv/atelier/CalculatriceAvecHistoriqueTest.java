package ca.cegepmv.atelier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Atelier — Tests avec Mock
 * =========================
 *
 * {@link CalculatriceAvecHistorique} dépend d'un {@link Journal} pour enregistrer chaque
 * opération. On ne veut PAS utiliser un vrai journal dans un test unitaire (il pourrait
 * écrire dans un fichier, une base de données, etc.) — on le remplace donc par un "mock" :
 * une doublure entièrement contrôlée par le test, qui ne fait rien de réel.
 *
 * Vocabulaire :
 * - "Mock" (ou doublure de test) : un faux objet qui remplace une vraie dépendance dans un
 *   test, pour isoler la classe testée du reste du système (BD, réseau, fichiers, etc.).
 * - @Mock : demande à Mockito de créer automatiquement un mock pour cette dépendance.
 * - verify(mock).methode(...) : vérifie qu'une méthode du mock a bien été appelée (avec
 *   quels arguments), sans se soucier de ce qu'elle "fait" réellement.
 * - @ExtendWith(MockitoExtension.class) : active l'intégration Mockito avec JUnit 6, pour que
 *   les champs annotés @Mock soient automatiquement initialisés avant chaque test.
 */
@ExtendWith(MockitoExtension.class)
class CalculatriceAvecHistoriqueTest {

    @Mock
    private Journal journalMock;

    private CalculatriceAvecHistorique calculatrice;

    @BeforeEach
    void setUp() {
        // Arrange (commun à tous les tests) : on injecte le MOCK à la place d'un vrai Journal
        calculatrice = new CalculatriceAvecHistorique(journalMock);
    }

    @Test
    void additionnerRetourneLeBonResultat() {
        // Act
        int resultat = calculatrice.additionner(2, 3);

        // Assert
        assertEquals(5, resultat);
    }

    @Test
    void additionnerEnregistreUneEntreeDansLeJournal() {

        // Appelez calculatrice.additionner(2, 3)
        int result = calculatrice.additionner(2, 3);

        verify(journalMock).enregistrer("2 + 3 = 5");
        // Utilisez verify(journalMock).enregistrer(...) pour vérifier qu'un message a bien
        // été enregistré. Astuce : vous pouvez vérifier le message exact attendu :
        // verify(journalMock).enregistrer("2 + 3 = 5");
    }

    @Test
    void diviserParZeroNeFaitAucunEnregistrementDansLeJournal() {

        //assert
        ArithmeticException exception = assertThrows(
            ArithmeticException.class, 
            () -> calculatrice.diviser(10, 0));

        assertNotNull(exception.getMessage());
        verify(journalMock, never()).enregistrer(anyString());
        // Vérifiez que diviser(10, 0) lance bien une ArithmeticException (comme avant),
        // ET utilisez verify(journalMock, never()).enregistrer(anyString()) pour vérifier
        // qu'aucun message n'a été enregistré dans ce cas (puisque l'exception est lancée
        // AVANT l'appel à journal.enregistrer(...)).

    }
}

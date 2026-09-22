package ca.cegepmv.atelier;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Atelier — Tests unitaires
 * =========================
 *
 * Objectif : revoir les bases des tests unitaires avec JUnit 6 en complétant les tests
 * manquants (marqués TODO) sur la classe {@link Calculatrice}.
 *
 * Rappels de vocabulaire :
 * - Un "cas de test" (test case) = une seule méthode annotée @Test qui vérifie UN
 *   comportement précis.
 * - Une "suite de tests" (test suite) = l'ensemble des cas de test d'une classe
 *   (ici, toute la classe CalculatriceTest).
 * - Une "assertion" = un appel assertXxx(...) qui compare le résultat obtenu au résultat
 *   attendu. Si l'assertion échoue, le test échoue.
 * - Patron AAA : Arrange (préparer les données), Act (appeler la méthode testée),
 *   Assert (vérifier le résultat).
 *
 * Consigne : complétez chaque méthode marquée "// TODO" en suivant le patron AAA.
 * Ne modifiez pas les tests déjà fournis (niveau 1), ils servent d'exemple.
 */
class CalculatriceTest {

    private final Calculatrice calculatrice = new Calculatrice();

    // ------------------------------------------------------------------
    // Niveau 1 — Assertions simples (déjà fournis, à titre d'exemple)
    // ------------------------------------------------------------------

    @Test
    void additionnerDeuxNombresPositifs() {
        // Arrange
        int a = 2;
        int b = 3;

        // Act
        int resultat = calculatrice.additionner(a, b);

        // Assert
        assertEquals(5, resultat);
    }

    @Test
    void soustraireDonneLaDifference() {
        // Arrange
        int a = 10;
        int b = 4;

        // Act
        int resultat = calculatrice.soustraire(a, b);

        // Assert
        assertEquals(6, resultat);
    }

    // ------------------------------------------------------------------
    // Niveau 1 — À vous de jouer (suivez le même modèle que ci-dessus)
    // ------------------------------------------------------------------

    @Test
    void multiplierDeuxNombres() {

        int a = 4;
        int b = 5;
        
        //ACT
        int resultat = calculatrice.multiplier(a, b);

        //assert 
        assertEquals(20, resultat);
    }

    @Test
    void maxRetourneLePlusGrandDesDeuxNombres() {

        //ARRANGE
        int a = 7;
        int b = 3;

        //ACT
        int resultat = calculatrice.max(a, b);

        //ASSERT
        assertEquals(7, resultat);
    }

    // ------------------------------------------------------------------
    // Niveau 2 — Comportements composés (assertAll, assertThrows)
    // ------------------------------------------------------------------

    @Test
    void estPairDistingueLesNombresPairsEtImpairs() {

        assertAll("Verif de est pair",
            () -> assertTrue(calculatrice.estPair(4)),
            () -> assertFalse(calculatrice.estPair(7)),
            () -> assertTrue(calculatrice.estPair(0))
        );
        //  - estPair(4) doit être vrai
        //  - estPair(7) doit être faux
        //  - estPair(0) doit être vrai
    }

    @Test
    void diviserParZeroLanceUneException() {

        ArithmeticException exception = assertThrows(
            ArithmeticException.class, 
            () -> calculatrice.diviser(10, 0));
        assertNotNull(exception.getMessage());
        // pour vérifier que diviser(10, 0) lance bien une ArithmeticException.
    }

    // ------------------------------------------------------------------
    // Niveau 3 — Tests paramétrés (@ValueSource / @CsvSource) et cas limites
    // ------------------------------------------------------------------

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 5, 7, 11, 13})
    void estPremierRetourneVraiPourLesNombresPremiersConnus(int nombre) {

        //ACT
        boolean result = calculatrice.estPremier(nombre);
        assertTrue(result);
        // Vérifiez que estPremier(nombre) retourne true pour chacune des valeurs fournies.
    }

    @ParameterizedTest
    @CsvSource({
        "1, false",   // 1 n'est pas premier par définition
        "4, false",   // 4 = 2 x 2
        "9, false",   // 9 = 3 x 3
        "17, true"    // 17 est premier
    })
    void estPremierGereLesCasLimites(int nombre, boolean attendu) {

        boolean result = calculatrice.estPremier(nombre);
        assertEquals(attendu, result);
        // Vérifiez que estPremier(nombre) correspond bien à la valeur "attendu".
    }

    @Test
    void diviserAvecNombresNegatifs() {

        int a = -10;
        int b = 2;

        int result = calculatrice.diviser(a, b);

        assertEquals(-5, result);

        // Cas limite : que se passe-t-il quand on divise un nombre négatif ?
        // Vérifiez que diviser(-10, 2) retourne -5.
    }
}

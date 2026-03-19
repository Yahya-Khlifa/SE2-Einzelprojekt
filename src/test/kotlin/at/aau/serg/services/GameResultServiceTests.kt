package at.aau.serg.services

import at.aau.serg.models.GameResult
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class GameResultServiceTests {

    private lateinit var service: GameResultService

    @BeforeEach
    fun setup() {
        service = GameResultService()
    }

    // 1. Test: Leere Liste am Anfang (war in deinem alten Test)
    @Test
    fun test_getGameResults_emptyList() {
        assertEquals(0, service.getGameResults().size)
    }

    // 2. Test: Hinzufügen und ID Check (verbesserte Version deines alten Tests)
    @Test
    fun test_addAndGetGameResult() {
        val result = GameResult(0, "Player1", 100, 15.0)
        service.addGameResult(result)

        val found = service.getGameResult(1) // Die erste ID muss 1 sein
        assertEquals("Player1", found?.playerName)
        assertEquals(1, found?.id)
    }

    // 3. Test: Suche nach ID, die nicht existiert (war in deinem alten Test)
    @Test
    fun test_getGameResult_returnsNullIfNotFound() {
        assertNull(service.getGameResult(999))
    }

    // 4. Test: Löschen von Einträgen (NEU für 100% Coverage)
    @Test
    fun test_deleteGameResult() {
        val result = GameResult(0, "DeleteMe", 10, 5.0)
        service.addGameResult(result)

        val deleted = service.deleteGameResult(1)
        assertTrue(deleted)
        assertEquals(0, service.getGameResults().size)
    }

    // 5. Test: Leaderboard Sortierung (NEU für Aufgabe 2.2.1 & 2.2.3)
    @Test
    fun test_getLeaderboard_fullSortingLogic() {
        // Testet auch den "Empty"-Pfad im Leaderboard
        assertTrue(service.getLeaderboard().isEmpty())

        val p1 = GameResult(0, "LowScore", 50, 10.0)
        val p2 = GameResult(0, "HighScoreSlow", 200, 60.0)
        val p3 = GameResult(0, "HighScoreFast", 200, 30.0) // Bester (200 Score, 30s)

        service.addGameResult(p1)
        service.addGameResult(p2)
        service.addGameResult(p3)

        val lb = service.getLeaderboard()

        assertEquals(3, lb.size)
        assertEquals("HighScoreFast", lb[0].playerName) // Platz 1
        assertEquals("HighScoreSlow", lb[1].playerName) // Platz 2
        assertEquals("LowScore", lb[2].playerName)      // Platz 3
    }
}
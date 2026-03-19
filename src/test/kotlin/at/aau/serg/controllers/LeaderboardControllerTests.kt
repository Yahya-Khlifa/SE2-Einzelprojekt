package at.aau.serg.controllers

import at.aau.serg.models.GameResult
import at.aau.serg.services.GameResultService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import kotlin.test.assertEquals
import org.mockito.Mockito.`when` as whenever

class LeaderboardControllerTests {

    private lateinit var mockedService: GameResultService
    private lateinit var controller: LeaderboardController

    @BeforeEach
    fun setup() {
        mockedService = mock(GameResultService::class.java)
        controller = LeaderboardController(mockedService)
    }

    @Test
    fun test_getLeaderboard_withoutRank_returnsFullList() {
        // Vorbereitung der Testdaten
        val list = listOf(
            GameResult(1, "Bester", 100, 30.0),
            GameResult(2, "Zweiter", 80, 40.0)
        )
        whenever(mockedService.getLeaderboard()).thenReturn(list)

        // Aufruf ohne Parameter (rank = null)
        val res = controller.getLeaderboard(null)

        assertEquals(2, res.size)
        assertEquals("Bester", res[0].playerName)
        verify(mockedService).getLeaderboard()
    }

    @Test
    fun test_getLeaderboard_withValidRank_returnsSingleElement() {
        val list = listOf(
            GameResult(1, "Bester", 100, 30.0),
            GameResult(2, "Zweiter", 80, 40.0)
        )
        whenever(mockedService.getLeaderboard()).thenReturn(list)

        // Aufruf mit rank = 2
        val res = controller.getLeaderboard(2)

        assertEquals(1, res.size)
        assertEquals("Zweiter", res[0].playerName)
    }

    @Test
    fun test_getLeaderboard_withInvalidRank_returnsEmptyList() {
        val list = listOf(GameResult(1, "Bester", 100, 30.0))
        whenever(mockedService.getLeaderboard()).thenReturn(list)

        // Teste zu hohen Rang und Rang 0/negativ
        assertEquals(0, controller.getLeaderboard(99).size)
        assertEquals(0, controller.getLeaderboard(0).size)
        assertEquals(0, controller.getLeaderboard(-1).size)
    }
}
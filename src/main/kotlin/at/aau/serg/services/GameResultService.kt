package at.aau.serg.services

import at.aau.serg.models.GameResult
import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicLong

@Service
class GameResultService {

    private val gameResults = mutableListOf<GameResult>()
    private var nextId = AtomicLong(1)

    fun addGameResult(gameResult: GameResult) {
        gameResult.id = nextId.getAndIncrement()
        gameResults.add(gameResult)
    }

    fun getGameResult(id: Long): GameResult? = gameResults.find { it.id == id } // ? allows null

    fun getGameResults(): List<GameResult> = gameResults.toList() // returns immutable list copy

    /**
     * Kotlin-idiomatic for:
     * fun deleteGameResult(gameResultId: Long) {
     *     gameResults.removeIf({ gameResult -> gameResult.id == gameResultId })
     * }
     */
    fun deleteGameResult(id: Long) = gameResults.removeIf { it.id == id }
    // In der Datei GameResultService.kt
    fun getLeaderboard(): List<GameResult> {
        return gameResults.sortedWith(
            compareByDescending<GameResult> { it.score } // Höchster Score (z.B. 100 vor 80)
                .thenBy { it.timeInSeconds}                 // Bei 100 vs 100: 30 Sek vor 45 Sek
        )
    }


}
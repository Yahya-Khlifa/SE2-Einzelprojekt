package at.aau.serg.controllers

import at.aau.serg.models.GameResult
import at.aau.serg.services.GameResultService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/leaderboard")
class LeaderboardController(
    private val gameResultService: GameResultService
) {

    @GetMapping
    fun getLeaderboard(
        @RequestParam(required = false) rank: Int?
    ): List<GameResult> {
        // 1. Wir holen die fertig sortierte Liste vom Service (Aufgabe 2.2.1)
        val sortedList = gameResultService.getLeaderboard()

        // 2. Wenn ein 'rank' abgefragt wird, filtern wir (Aufgabe 2.2.2)
        if (rank != null) {
            return if (rank > 0 && rank <= sortedList.size) {
                listOf(sortedList[rank - 1]) // Rank 1 ist Index 0
            } else {
                emptyList()
            }
        }

        // 3. Ohne 'rank' Parameter geben wir die ganze Liste zurück
        return sortedList
    }
}
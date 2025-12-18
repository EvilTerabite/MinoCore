package gg.mino.minocore.ui

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scoreboard.Criteria
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.RenderType

class MinoBoard(val player: Player, title: String, renderType: RenderType = RenderType.INTEGER) {
    private val board = Bukkit.getScoreboardManager().newScoreboard
    private val objective = board.registerNewObjective("mino", Criteria.DUMMY, miniMessage.deserialize(title),
        renderType)

    init {
        objective.displaySlot = DisplaySlot.SIDEBAR
        player.scoreboard = board
    }

    fun updateLines(vararg lines: String) {
        //TODO: Only update lines that were changed
    }
}
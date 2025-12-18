package gg.mino.minocore.player

import gg.mino.minocore.ui.MinoBossBar
import gg.mino.minocore.ui.sendActionMini
import gg.mino.minocore.ui.sendMiniMessage
import org.bukkit.entity.Player
import java.util.UUID

class MinoPlayer(val player: Player) {
    val uuid: UUID = player.uniqueId
    val name: String = player.name

    private val metadata = mutableMapOf<String, Any>()
    private var currentBar: MinoBossBar? = null

    fun msg(message: String) = player.sendMiniMessage(message)
    fun actionBar(message: String) = player.sendActionMini(message)

    fun setMeta(key: String, value: Any) {
        metadata[key] = value
    }

    fun getMeta(key: String): Any? = metadata[key]


    fun setupBar(title: String) {
        currentBar = MinoBossBar(title)
    }

    fun updateBar() {
        //TODO:
    }
}
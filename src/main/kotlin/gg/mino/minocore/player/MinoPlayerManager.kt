package gg.mino.minocore.player

import org.bukkit.entity.Player
import java.util.UUID

object MinoPlayerManager {
    private val cache = mutableMapOf<UUID, MinoPlayer>()

    fun get(player: Player): MinoPlayer {
        return cache.getOrPut(player.uniqueId) { MinoPlayer(player) }
    }

    fun remove(uuid: UUID) {
        cache.remove(uuid)
    }
}
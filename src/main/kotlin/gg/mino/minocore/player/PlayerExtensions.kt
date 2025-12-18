package gg.mino.minocore.player

import org.bukkit.entity.Player

fun Player.mino(): MinoPlayer {
    return MinoPlayerManager.get(this)
}
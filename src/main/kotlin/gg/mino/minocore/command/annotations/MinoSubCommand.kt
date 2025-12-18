package gg.mino.minocore.command.annotations


@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class MinoSubCommand(
    val name: String,
    val permission: String = "",
    val description: String = ""
    )

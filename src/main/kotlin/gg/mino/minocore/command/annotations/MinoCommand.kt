package gg.mino.minocore.command.annotations

/**
 * @param name String
 * @param description String default ""
 * @param usage String default ""
 * @param aliases Array<String> default []
 * @param permission String default ""
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class MinoCommand(val name: String,
                             val description: String = "",
                             val usage: String = "",
                             val aliases: Array<String> = [],
                             val permission: String = ""
)
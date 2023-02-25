package game_of_survival.core

object Constants {
    /**
     * The banner displayed, when the help page is to be displayed.
     */
    const val HELP_BANNER = "Game of survival – a variation of game of life"

    /**
     * The help flag utilised for displaying help.
     */
    const val HELP_FLAG = "--help"

    /**
     * The time of displaying a single frame in a certain state.
     */
    const val SINGLE_FRAME_DURATION_MILLIS = 100;

    /**
     * The characters used for recognising types of cells saved in a text line.
     */
    object CellTypeChars {
        const val HARDLY_REVIVABLE_CELL = 'T'
        const val HIBERNATING_CELL = 'H'
        const val LIMITED_CELL = 'O'
        const val NORMAL_CELL = 'N'
    }

    /**
     * The signings of states for used cells.
     */
    object CellStateSignings {
        object Normal {
            const val ALIVE = "${0x1b.toChar()}[1;31;32m#${0x1b.toChar()}[0m"
            const val DEAD = " "
        }
        object Limited {
            const val ALIVE = "${0x1b.toChar().toChar()}[1;31;32m+${0x1b.toChar().toChar()}[0m"
            const val DEAD = "${0x1b.toChar()}[1;31;31m+${0x1b.toChar()}[0m"
        }
        object Hibernating {
            const val ALIVE = "${0x1b.toChar()}[1;31;32m@${0x1b.toChar()}[0m"
            const val DEAD = "${0x1b.toChar()}[1;31;31m@${0x1b.toChar()}[0m"
            const val HIBERNATED = "${0x1b.toChar()}[1;31;33m@${0x1b.toChar()}[0m"
        }
        object HardlyRevivable {
            const val ALIVE = "${0x1b.toChar()}[1;31;32m*${0x1b.toChar()}[0m"
            const val DEAD = "${0x1b.toChar()}[1;31;31m*${0x1b.toChar()}[0m"
        }
    }
}
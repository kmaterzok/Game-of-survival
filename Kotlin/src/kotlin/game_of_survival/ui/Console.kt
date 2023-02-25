package game_of_survival.ui

/**
 * Helper for handling console related issues.
 */
object ConsoleHelper {
    /**
     * Says if the running operating system is Windows.
     */
    val isWindows = System.getProperty("os.name")
        .uppercase()
        .contains("WINDOWS")

    /**
     * Clears the screen of the console.
     */
    inline fun clear(): Unit {

        if (isWindows) {
            ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor()
        }
        else {
            print("\u001b[H\u001b[2J")
        }
    }

}
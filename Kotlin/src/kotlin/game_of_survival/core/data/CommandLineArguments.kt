package game_of_survival.core.data

/**
 * The class storing all parsed arguments from the command line.
 * @see game_of_survival.core.utilities.ArgumentParser
 */
class CommandLineArguments() {
    /**
     * The input file defining the game plane setting before the first iteration.
     */
    var inputFileName: String = ""

    /**
     * The output file, which the last state of game plane will be saved into.
     */
    var outputFileName: String? = null

    /**
     * The quantity of iteration to commit onto the game plane.
     */
    var iterationQuantity: Int = Int.MAX_VALUE
}

package game_of_survival.ui

import game_of_survival.core.Constants
import game_of_survival.core.data.CommandLineArguments
import game_of_survival.core.exceptions.ParsingException
import game_of_survival.core.models.GamePlane
import game_of_survival.core.utilities.ArgumentParser
import game_of_survival.core.utilities.validateInputFilePath
import game_of_survival.core.utilities.validateIterationCount
import game_of_survival.ui.extensions.print
import game_of_survival.ui.extensions.toFile
import java.time.Duration
import java.time.LocalTime

/**
 * The main method launching the CLI app.
 * @param args The command line parameters passed into this app.
 */
fun main(args: Array<String>) {

    val argumentHolder = CommandLineArguments()
    val parser = ArgumentParser(Constants.HELP_BANNER, fun (m: String) { print(m) }, true)
    parser.argument(
        flag = "--input",
        shortFlag = "-i",
        description = "Input file describing the plane at start",
        assigner = { i: String? -> requireNotNull(i); argumentHolder.inputFileName = i },
        mustBeExplicit = true,
        validation = { validateInputFilePath(it) }
    )
    parser.argument(
        flag = "--output",
        shortFlag = "-o",
        description = "Output file describing the plane at end",
        assigner = { i: String? -> argumentHolder.outputFileName = i },
        validation = null,
    )
    parser.argument(
        flag = "--count",
        shortFlag = "-c",
        description = "Quantity of iterations",
        assigner = { i: Int? -> requireNotNull(i); argumentHolder.iterationQuantity = i },
        defaultValue = Int.MAX_VALUE,
        validation = { validateIterationCount(it) },
    )

    try {
        val parsingResult = parser.parse(args)
        if (!parsingResult) {
            val plane = GamePlane.parseFile(argumentHolder.inputFileName)
            plane.print()
            for (iterationNumber in 2..argumentHolder.iterationQuantity) {
                val beginTime = LocalTime.now()
                plane.nextIteration()
                val finishTime = LocalTime.now()
                val correctedDuration =
                    Constants.SINGLE_FRAME_DURATION_MILLIS - Duration.between(finishTime, beginTime).toMillis()
                Thread.sleep(correctedDuration)
                ConsoleHelper.clear()
                plane.print()
            }
            argumentHolder.outputFileName?.let {
                plane.toFile(it)
            }
        }
    }
    catch (pex: ParsingException) {
        println(pex.message)
    }
    catch (ex: Exception) {
        println("Unexpected error has occurred: ${ex.message}")
        ex.printStackTrace()
    }
}
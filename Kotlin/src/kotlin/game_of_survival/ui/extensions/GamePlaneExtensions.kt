package game_of_survival.ui.extensions

import game_of_survival.ui.exceptions.FileHandlingException
import game_of_survival.core.models.GamePlane
import java.io.File

/**
 * Print all cells onto console output.
 */
fun GamePlane.print() {
    this.forEachCell( { cell ->
        print(cell.getCurrentStateSigning())
    }, {
        println()
    })
}

/**
 * Save file lines into a file.
 * @param path to the written file
 */
fun GamePlane.toFile(path: String) {
    val outputFile = File(path)
    outputFile.createNewFile()
    if (!outputFile.canWrite()) {
        throw FileHandlingException("Cannot write into $path")
    }
    outputFile.bufferedWriter().use {
        this.toFileLines().forEach { line ->
            it.write(line)
            it.write("\n")
        }
    }
}
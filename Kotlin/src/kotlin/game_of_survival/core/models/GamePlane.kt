package game_of_survival.core.models

import game_of_survival.core.abstraction.Cell
import game_of_survival.core.exceptions.ParsingException
import game_of_survival.core.models.factories.CellFactory
import java.io.File
import java.lang.StringBuilder

/**
 * The representation of 2D plane, containing all game cells.
 * @param survivingCells Contains all cells which the simulation will be conducted for.
 */
class GamePlane(val survivingCells: Array<Array<Cell>>) {
    init {
        require(survivingCells.all { it.size == survivingCells.first().size }) {
            "Count of cells in arrays is not distributed equally."
        }
    }
    companion object {
        /**
         * Parse all string lines describing the starting state for GamePlane and create it.
         * @param lines String lines for description
         * @return The game plane for processing cells.
         */
        fun parseFileLines(lines: Iterable<String>): GamePlane {
            var firstRowSize: Int? = null

            val cellsRows = lines.filter { !it.isNullOrEmpty() }.map { line ->

                val cellRow = mutableListOf<Cell>()
                line.split(' ')
                    .filter { !it.isNullOrEmpty() }
                    .forEach { lineGroup ->
                    val cellTypeChar = lineGroup.firstOrNull()
                    val cellState = lineGroup.drop(1).take(1).firstOrNull()
                    if (cellTypeChar == null || cellState == null) {
                        throw ParsingException("There is an invalid cell configuration due to invalid space usage.")
                    }
                    val cellIsAlive = when (cellState) {
                        '0' -> false
                        '1' -> true
                        else -> throw ParsingException("An invalid state `$cellState` is present in the source text.")
                    }
                    cellRow.add(CellFactory.newCell(cellTypeChar, cellIsAlive))
                }
                if (firstRowSize == null) {
                    firstRowSize = cellRow.size
                }
                else if (cellRow.size != firstRowSize) {
                    throw ParsingException("Count of cells in rows is not distributed equally.")
                }
                cellRow.toTypedArray()
            }.toTypedArray()

            var plane = GamePlane(cellsRows)
            plane.bindAllCells();
            return plane;
        }

        /**
         * Parse file containing description of the initial state for Game Plane.
         * @param path Input file path.
         * @return The game plane for processing cells.
         */
        fun parseFile(path: String): GamePlane {
            val sourceLines = File(path).readLines()
            return this.parseFileLines(sourceLines)
        }
    }

    /**
     * Height of the plane.
     */
    val height = survivingCells.size

    /**
     * Width of the plane.
     */
    val width = survivingCells.first().size

    /**
     * Go to the next iteration of the plane.
     */
    fun nextIteration() {
        this.forEachCell { it.stashNextState() }
        this.forEachCell { it.releaseNextState() }
    }

    /**
     * Create string lines describing the game plane state.
     * The lines are ready for saving into a file.
     * @return Corresponding data lines
     */
    fun toFileLines(): List<String> {
        val fileLines = mutableListOf<String>()
        val lineBuilder = StringBuilder()

        this.forEachCell(
            { lineBuilder.append(it.typeChar)
                               .append(if (it.isPerceivedAsAlive()) {1} else {0})
                               .append(' ')
            },
            {
                fileLines.add(lineBuilder.toString())
                lineBuilder.clear()
            }
        )
        return fileLines.toList()
    }

    /**
     * Create bindings between all cells.
     */
    private fun bindAllCells() {
        for (coordinateY in 0 until height) {
            for (coordinateX in 0 until width) {
                survivingCells[coordinateY][coordinateX].setNeighbours(this, coordinateX, coordinateY)
            }
        }
    }

    /**
     * Apply an action for all surviving cells.
     * @param cellAction Action done with a cell
     */
    fun forEachCell(cellAction: (Cell) -> Unit) {
        this.survivingCells.forEach { row ->
            row.forEach { cell ->
                cellAction(cell)
            }
        }
    }
    /**
     * Apply an action for all surviving cells.
     * @param cellAction Action done with a cell
     * @param endRowAction Action done at the end of cell row
     */
    fun forEachCell(cellAction: (Cell) -> Unit, endRowAction: () -> Unit) {
        this.survivingCells.forEach { row ->
            row.forEach { cell ->
                cellAction.invoke(cell)
            }
            endRowAction.invoke()
        }
    }
}
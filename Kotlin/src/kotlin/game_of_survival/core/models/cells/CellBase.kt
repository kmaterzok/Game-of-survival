package game_of_survival.core.models.cells

import game_of_survival.core.abstraction.Cell
import game_of_survival.core.models.GamePlane
import game_of_survival.core.utilities.outOfMinMaxCoalesce

abstract class CellBase : Cell {

    /**
     * X coordinate of the cell in a plane.
     */
    private var x : Int? = null;

    /**
     * Y coordinate of the cell in a plane.
     */
    private var y : Int? = null;

    /**
     * The cells that are neighbouring with this cell and its state
     * will be handled between iterations.
     */
    protected var neighbouringCells: Array<Cell> = emptyArray<Cell>()

    /**
     * Determine if the criteria of not setting a neighbour were met.
     */
    protected open fun ignoreNeighbour(cellX: Int, cellY: Int, addedCellX: Int, addedCellY: Int): Boolean = false

    /**
     * Count all neighbours, that are perceived as alive ones.
     */
    protected open fun countAliveNeighbours() : Int =
        this.neighbouringCells.count { it.isPerceivedAsAlive() }

    override fun setNeighbours(plane: GamePlane, cellX: Int, cellY: Int) {
        x = cellX
        y = cellY
        val cellsToSet = mutableListOf<Cell>()
        for (diffY in -1..1) {
            val neighbourY = outOfMinMaxCoalesce(cellY + diffY, 0, plane.height - 1,
                plane.height - 1, 0)
            for (diffX in -1..1) {
                val neighbourX = outOfMinMaxCoalesce(cellX + diffX, 0, plane.width - 1,
                    plane.width - 1, 0)

                if ((cellX == neighbourX && cellY == neighbourY)
                    || this.ignoreNeighbour(cellX, cellY, diffX, diffY)) {
                    continue
                }
                val neighbour = plane.survivingCells[neighbourY][neighbourX]
                cellsToSet.add(neighbour)
            }
        }
        this.neighbouringCells = cellsToSet.toTypedArray()
    }
    override fun toString() : String = "(${this.x},${this.y})"
}
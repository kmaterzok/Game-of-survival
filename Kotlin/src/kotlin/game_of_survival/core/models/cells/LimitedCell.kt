package game_of_survival.core.models.cells

import game_of_survival.core.Constants

/**
 * Create a new limited cell with half of the neighbours.
 * @param isAliveNow Is the cell alive at the moment.
 */
class LimitedCell(private var isAliveNow: Boolean) : CellBase() {
    private var isAliveNext: Boolean = false
    override val typeChar: Char = Constants.CellTypeChars.LIMITED_CELL
    override fun ignoreNeighbour(cellX: Int, cellY: Int, addedCellX: Int, addedCellY: Int): Boolean =
        cellX != addedCellX && cellY != addedCellY
    override fun isPerceivedAsAlive(): Boolean = this.isAliveNow
    override fun stashNextState() {
        val aliveNeighboursCount = this.countAliveNeighbours();
        this.isAliveNext = if (this.isAliveNow) {
            aliveNeighboursCount == 2 || aliveNeighboursCount == 3;
        } else {
            aliveNeighboursCount == 3;
        }
    }
    override fun releaseNextState() {
        this.isAliveNow = this.isAliveNext
    }
    override fun getCurrentStateSigning(): String =
        if (this.isAliveNow) {
            Constants.CellStateSignings.Limited.ALIVE
        } else {
            Constants.CellStateSignings.Limited.DEAD
        }
}
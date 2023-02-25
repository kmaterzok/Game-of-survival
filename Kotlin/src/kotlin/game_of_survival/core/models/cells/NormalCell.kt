package game_of_survival.core.models.cells

import game_of_survival.core.Constants

/**
 * Create a new normal cell, that behaves as in typical Game of Life.
 * @param isAliveNow Is the cell alive at the moment.
 */
class NormalCell(private var isAliveNow: Boolean) : CellBase() {
    private var isAliveNext: Boolean = false
    override val typeChar: Char = Constants.CellTypeChars.NORMAL_CELL
    override fun isPerceivedAsAlive(): Boolean = this.isAliveNow
    override fun stashNextState() {
        val aliveNeighboursCount = this.countAliveNeighbours()
        this.isAliveNext = if (this.isAliveNow) {
            aliveNeighboursCount == 2 || aliveNeighboursCount == 3
        } else {
            aliveNeighboursCount == 3
        }
    }
    override fun releaseNextState() {
        this.isAliveNow = this.isAliveNext
    }
    override fun getCurrentStateSigning(): String =
        if (this.isAliveNow) {
            Constants.CellStateSignings.Normal.ALIVE
        } else {
            Constants.CellStateSignings.Normal.DEAD
        }
}
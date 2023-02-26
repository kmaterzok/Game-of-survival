package game_of_survival.core.models.cells

import game_of_survival.core.Constants

/**
 * Create a cell, that becomes alive after a prolonged period.
 * @param isAliveNow Is the cell alive at the moment.
 */
class HardlyRevivableCell(private var isAliveNow: Boolean) : CellBase() {
    private var iterationsToWaitNow = 0
    private var iterationsToWaitNext = 0
    private var isAliveNext: Boolean = false
    override val typeChar: Char = Constants.CellTypeChars.HARDLY_REVIVABLE_CELL
    override fun isPerceivedAsAlive(): Boolean = this.isAliveNow
    override fun stashNextState() {
        if (this.isAliveNow || this.iterationsToWaitNow > 0) {
            var aliveNeighboursCount = this.countAliveNeighbours()
            val willBeAlive = aliveNeighboursCount == 2 || aliveNeighboursCount == 3
            if (willBeAlive) {
                this.iterationsToWaitNext = if (this.iterationsToWaitNow == 0) {
                    0
                } else {
                    this.iterationsToWaitNow - 1
                }
            }
            else {
                this.iterationsToWaitNext = 2;
            }
        }
        else {
            this.iterationsToWaitNext = 2;
        }
        this.isAliveNext = this.iterationsToWaitNext == 0
    }
    override fun releaseNextState() {
        this.isAliveNow = this.isAliveNext
        this.iterationsToWaitNow = this.iterationsToWaitNext
    }
    override fun getCurrentStateSigning(): String =
        if (this.isAliveNow) {
            Constants.CellStateSignings.HardlyRevivable.ALIVE
        } else {
            Constants.CellStateSignings.HardlyRevivable.DEAD
        }
}

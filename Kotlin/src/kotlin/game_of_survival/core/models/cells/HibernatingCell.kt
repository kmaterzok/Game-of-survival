package game_of_survival.core.models.cells

import game_of_survival.core.Constants
import game_of_survival.core.models.enums.HibernatingCellState

/**
 * Create a hibernating cell, wakes up after a period.
 * @param isAlive Is the cell alive at the moment.
 */
class HibernatingCell(private val isAlive: Boolean) : CellBase() {
    private var iterationsLeftNow: Int = 0;
    private var iterationsLeftNext: Int = 0;
    private var vitalityNow: HibernatingCellState = if (isAlive) {
        HibernatingCellState.ALIVE
    } else {
        HibernatingCellState.DEAD
    }
    private var vitalityNext: HibernatingCellState = HibernatingCellState.DEAD
    override val typeChar: Char = Constants.CellTypeChars.HIBERNATING_CELL
    override fun isPerceivedAsAlive(): Boolean =
        this.vitalityNow == HibernatingCellState.ALIVE

    override fun stashNextState() {
        val aliveNeighboursCount = this.countAliveNeighbours()
        when (this.vitalityNow) {
            HibernatingCellState.ALIVE -> {
                if (aliveNeighboursCount == 2 || aliveNeighboursCount == 3)
                    this.vitalityNext = HibernatingCellState.ALIVE
                else {
                    // The first iteration (it is not counted)
                    this.vitalityNext = HibernatingCellState.HIBERNATED
                    this.iterationsLeftNext = (0..9).random()
                }
            }
            HibernatingCellState.DEAD -> {
                this.vitalityNext = if (aliveNeighboursCount == 3) {
                    HibernatingCellState.ALIVE
                } else {
                    HibernatingCellState.DEAD
                }
            }
            HibernatingCellState.HIBERNATED -> {
                if (this.iterationsLeftNow == 0) {
                    this.vitalityNext = if(aliveNeighboursCount == 3) {
                        HibernatingCellState.ALIVE
                    } else {
                        HibernatingCellState.DEAD
                    }
                }
                else {
                    this.iterationsLeftNext = this.iterationsLeftNow - 1
                }
            }
        }
    }


    override fun releaseNextState() {
        this.vitalityNow = this.vitalityNext;
        this.iterationsLeftNow = this.iterationsLeftNext;
    }
    override fun getCurrentStateSigning(): String = when (this.vitalityNow) {
        HibernatingCellState.ALIVE -> Constants.CellStateSignings.Hibernating.ALIVE
        HibernatingCellState.DEAD -> Constants.CellStateSignings.Hibernating.DEAD
        HibernatingCellState.HIBERNATED -> Constants.CellStateSignings.Hibernating.HIBERNATED
    }
}
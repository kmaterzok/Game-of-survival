package game_of_survival.core.abstraction

import game_of_survival.core.models.GamePlane

/**
 * The cell used for simulation on a game plane.
 */
interface Cell {
    /**
     * Get the current sign (with possible white characters) for display.
     */
    fun getCurrentStateSigning(): String

    /**
     * The character associated with the cell's type.
     */
    val typeChar: Char

    /**
     * Is the cell seen by neighbouring cells as the alive cell.
     */
    fun isPerceivedAsAlive(): Boolean

    /**
     * Stash next state for the future use.
     */
    fun stashNextState()

    /**
     * Set the stashed state as the current one.
     */
    fun releaseNextState()

    /**
     * Set neighbour cells.
     * @param plane Game plane, which neighbour cells are got from
     * @param cellX X coordinate of the bound cell.
     * @param cellY Y coordinate of the bound cell.
     */
    fun setNeighbours(plane: GamePlane, cellX: Int, cellY: Int)
}
package game_of_survival.core.models.factories

import game_of_survival.core.Constants
import game_of_survival.core.abstraction.Cell
import game_of_survival.core.exceptions.ParsingException
import game_of_survival.core.models.cells.HardlyRevivableCell
import game_of_survival.core.models.cells.HibernatingCell
import game_of_survival.core.models.cells.LimitedCell
import game_of_survival.core.models.cells.NormalCell

object CellFactory {
    fun newCell(type: Char, isAlive: Boolean) : Cell = when (type) {
        Constants.CellTypeChars.NORMAL_CELL -> NormalCell(isAlive)
        Constants.CellTypeChars.LIMITED_CELL -> LimitedCell(isAlive)
        Constants.CellTypeChars.HIBERNATING_CELL -> HibernatingCell(isAlive)
        Constants.CellTypeChars.HARDLY_REVIVABLE_CELL -> HardlyRevivableCell(isAlive)
        else -> throw ParsingException("Cell described with `$type` is not supported")
    }
}
package game_of_survival.core.utilities

import java.io.File

/**
 * Validate, whether the path points to a file.
 * @path Input file path
 * @return Validation comment if eny error occurred or none if the path is valid.
 */
fun validateInputFilePath(path: String?): String? {
    requireNotNull(path)
    return if (File(path).exists()) { null } else { "Input file does not exist." }
}

/**
 * Validate, whether the iteration count is valid.
 * @path Iteration quantity
 * @return Validation comment if eny error occurred or none if the quantity is valid.
 */
fun validateIterationCount(count: Int?) : String? {
    requireNotNull(count)
    return when {
        count < 1 -> "The count must be greater than zero."
        else -> null
    }
}
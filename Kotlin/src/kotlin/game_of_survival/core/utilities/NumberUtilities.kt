package game_of_survival.core.utilities

/**
 * Return the value if it falls within the range or the limit value if exceeded.
 * @param value Checked value
 * @param min Min limit
 * @param max Max limit
 * @param minReplacement The value returned it `min` exceeded
 * @param maxReplacement The value returned it `max` exceeded
 * @return The coalesced value
 */
fun outOfMinMaxCoalesce(value: Int, min: Int, max: Int, minReplacement: Int, maxReplacement: Int): Int {
    return if (value < min) minReplacement
        else if (value > max) maxReplacement
        else value
}
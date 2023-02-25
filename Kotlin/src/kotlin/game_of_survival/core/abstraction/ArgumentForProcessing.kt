package game_of_survival.core.abstraction

/**
 * The interface for argument definition,
 * used for general processing.
 */
interface ArgumentForProcessing {

    /**
     * Short flag for the settings.
     */
    val flag: String?

    /**
     * Long flag equivalent of the short flag.
     */
    val shortFlag: String?

    /**
     * A short commentary description the use of setting.
     */
    val description: String

    /**
     * Was the argument already processed.
     */
    val wasProcessed: Boolean

    /**
     * Utilise the argument, convert it into a specified type,
     * validate and assign it into a desired field.
     * @param argument Passed command line argument
     */
    fun useArgument(argument: String)

    /**
     * Utilise the default value for the setting.
     */
    fun useDefaultArgument()

    /**
     * Get short flag in brackets for use in messages.
     * @return Bracketed short flag.
     */
    fun bracketedShortFlagForMessages(): String =
        if (shortFlag != null) { " (${shortFlag})" } else { "" }
}
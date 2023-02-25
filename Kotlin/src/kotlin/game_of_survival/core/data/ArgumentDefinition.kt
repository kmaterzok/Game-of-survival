package game_of_survival.core.data

import game_of_survival.core.Constants
import game_of_survival.core.abstraction.ArgumentForProcessing
import game_of_survival.core.exceptions.ParsingException

/**
 * A single definition info about command line argument handling.
 * @param flag Full flag for defining behaviour or value, usually beginning with two dashes.
 * @param shortFlag Short flag equivalent of full flag or the full one.
 * @param description A short commentary description the use of setting.
 * @param defaultValue Default value, that will be applied if no flag is defined in the command line.
 * @param mustBeExplicit Is the argument definition obligatory in the command line.
 * @param assigner A function, that utilises the parsed data, e.g. by assigning it to a variable.
 * @param validation A function, that validates the correctness of the parsed argument.
 * @param parseMethod A function, that parses string into a desired setting type.
 * @param TArg A type of object, which the argument will be parsed into.
 */
data class ArgumentDefinition<TArg>(
    override val flag: String?,
    override val shortFlag: String?,
    override val description: String,
    val defaultValue: TArg?,
    val mustBeExplicit: Boolean,
    val assigner: (TArg?) -> Unit,
    val validation: ((TArg) -> String?)?,
    val parseMethod: (String) -> TArg
) : ArgumentForProcessing {

    override var wasProcessed: Boolean = false
        private set


    init {
        require(flag != Constants.HELP_FLAG) {
            "Error: ${Constants.HELP_FLAG} is reserved for help text handling. Use another flag."
        }
        require (flag == null || !flag.contains(' ')) {
            "Flag '$flag' must not contain any spaces."
        }
        require (shortFlag == null || !shortFlag.contains(' ')) {
            "Short flag '$shortFlag' must not contain any spaces."
        }
        require(shortFlag == null || (shortFlag.length == 2 && shortFlag[0] == '-')) {
            "Short flag must begin with dash (-) and contain one character only, e.g. -i, -L, etc. It was $shortFlag."
        }
        require(flag == null || (flag.length > 2 && flag.substring(0, 2) == "--")) {
            "Short flag must begin with dash (-) and contain one character only, e.g. -i, -L, etc. It was $shortFlag."
        }
    }


    override fun useArgument(argument: String) =
        this.useAnyArgument(argument)

    override fun useDefaultArgument() {
        if (this.mustBeExplicit) {
            throw ParsingException("Parameter for flag $flag" +
                    "${this.bracketedShortFlagForMessages()} must be set explicitly.")
        }
        this.useAnyArgument(null)
    }


    private fun useAnyArgument(argument: String?) {
        if (wasProcessed) {
            throw ParsingException("Using argument $argument is not allowed as it's been already processed.")
        }
        val assignedValue: TArg?
        if (argument != null) {
            assignedValue = this.parseMethod.invoke(argument)
            val validationResult = this.validation?.invoke(assignedValue)
            if (validationResult != null) {
                throw ParsingException(validationResult)
            }
        }
        else {
            assignedValue = this.defaultValue
        }
        this.assigner.invoke(assignedValue)
        this.wasProcessed = true
    }
}

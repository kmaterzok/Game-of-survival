package game_of_survival.core.utilities

import game_of_survival.core.Constants
import game_of_survival.core.abstraction.ArgumentForProcessing
import game_of_survival.core.data.ArgumentDefinition
import game_of_survival.core.exceptions.ParsingException
import java.io.InvalidObjectException
import java.lang.StringBuilder

/**
 * The parser checking and loading data from the command line.
 */
class ArgumentParser(
    private val helpBanner: String,
    private val helpTextHandler: (String) -> Unit,
    private val printHelpIfNoFlags: Boolean) {

    private var _definitions = mutableListOf<ArgumentForProcessing>()

    /**
     * Configure handling of argument preceded by a flag. The handled argument targets to be a string.
     * @param flag Full flag for defining behaviour or value, usually beginning with two dashes
     * @param shortFlag Short flag equivalent of full flag or the full one.
     * @param description A short commentary description the use of setting.
     * @param defaultValue Default value, that will be applied if no flag is defined in the command line.
     * @param mustBeExplicit Is the argument definition obligatory in the command line.
     * @param assigner A function, that utilises the parsed data, e.g. by assigning it to a variable.
     * @param validation A function, that validates the correctness of the parsed argument.
     */
    fun argument(flag: String?,
                 shortFlag: String? = null,
                 description: String,
                 defaultValue: String? = null,
                 mustBeExplicit: Boolean = false,
                 assigner: (String?) -> Unit,
                 validation: ((String?) -> String?)? = null) =
        this.argument(
            flag, shortFlag, description, defaultValue, mustBeExplicit,
            assigner, validation, parseMethod = { it }
        )

    /**
     * Configure handling of argument preceded by a flag. The handled argument targets to be an integer.
     * @param flag Full flag for defining behaviour or value, usually beginning with two dashes
     * @param shortFlag Short flag equivalent of full flag or the full one.
     * @param description A short commentary description the use of setting.
     * @param defaultValue Default value, that will be applied if no flag is defined in the command line.
     * @param mustBeExplicit Is the argument definition obligatory in the command line.
     * @param assigner A function, that utilises the parsed data, e.g. by assigning it to a variable.
     * @param validation A function, that validates the correctness of the parsed argument.
     */
    fun argument(flag: String?,
                 shortFlag: String? = null,
                 description: String,
                 defaultValue: Int? = null,
                 mustBeExplicit: Boolean = false,
                 assigner: (Int?) -> Unit,
                 validation: ((Int?) -> String?)? = null) =
        this.argument(
            flag, shortFlag, description, defaultValue, mustBeExplicit,
            assigner, validation, parseMethod = { i: String ->
                try {
                    i.toInt()
                }
                catch (ex: NumberFormatException) {
                    throw ParsingException("The value `${i}` is not an integer.")
                }
            }
        )

    /**
     * Parse data provided in the @param commandLineArgs
     * and pass data into objects pointed during building this parser.
     * @param commandLineArgs All provided command line args
     * @return Help text was utilised (e.g. for printing)
     */
    fun parse(commandLineArgs : Array<String>): Boolean {

        if (utiliseHelpIfArgumentExistsOrNone(commandLineArgs)) {
            return true
        }
        var foundDefinition: ArgumentForProcessing? = null
        for (loadedArgument in commandLineArgs) {

            foundDefinition = if (foundDefinition == null) {
                this._definitions.singleOrNull {
                    loadedArgument == it.flag || loadedArgument == it.shortFlag
                } ?: throw ParsingException("Unknown flag '$loadedArgument'.")
            } else {
                foundDefinition.useArgument(loadedArgument)
                null
            }
        }

        if (foundDefinition != null) {
            throw ParsingException("The parameter '${foundDefinition.flag}" +
                    "${foundDefinition.bracketedShortFlagForMessages()}' is not set the value.")
        }

        this._definitions.filter{ !it.wasProcessed }
                         .forEach { it.useDefaultArgument() }
        return false
    }


    private fun <TArg> argument(flag: String?,
                                shortFlag: String? = null,
                                description: String,
                                defaultValue: TArg? = null,
                                mustBeExplicit: Boolean = false,
                                assigner: (TArg?) -> Unit,
                                validation: ((TArg?) -> String?)? = null,
                                parseMethod: ((String) -> TArg)) {
        val definition = ArgumentDefinition(
            flag, shortFlag, description, defaultValue, mustBeExplicit,
            assigner, validation, parseMethod
        )
        this.ensureNewArgumentDefinitionOk(definition)
        this._definitions.add(definition)
    }

    /**
     * Throw an exception if a new definition fails verification.
     * @param definition Verified argument definition
     * @param TArg A type of object, which the argument will be parsed into.
     */
    private fun <TArg> ensureNewArgumentDefinitionOk(definition: ArgumentDefinition<TArg>) {
        if (this._definitions.any { it.flag == definition.flag || it.shortFlag == definition.flag }) {
            throw InvalidObjectException(
                "The flag ${definition.flag} has been already " +
                        "added into parser definitions. Make sure all settings are well set."
            )
        }
        if (this._definitions.any { it.flag == definition.shortFlag || it.shortFlag == definition.shortFlag }) {
            throw InvalidObjectException(
                "The short flag ${definition.shortFlag} has been already " +
                        "added into parser definitions. Make sure all settings are well set."
            )
        }
    }

    private fun utiliseHelpIfArgumentExistsOrNone(arguments: Array<String>): Boolean = when {
        (this.printHelpIfNoFlags && arguments.isEmpty())
                || (arguments.size == 1 && arguments[0] == Constants.HELP_FLAG) -> {
            this.helpTextHandler.invoke(generateHelpText())
            true
        }
        arguments.size > 1 && arguments.any {it == Constants.HELP_FLAG} ->
            throw InvalidObjectException("${Constants.HELP_FLAG} flag can be set alone only with no arguments more.")
        else -> false
    }

    private fun generateHelpText(): String {

        val help = StringBuilder().appendLine()
                                  .appendLine(this.helpBanner).appendLine()
                                  .appendLine("Arguments:")

        val sortedShortFlagArgs = this._definitions.filter { it.shortFlag != null }.sortedBy { it.shortFlag }
        val      sortedFlagArgs = this._definitions.filter { it.shortFlag == null }.sortedBy { it.flag }


        val allSortedFlags = sortedShortFlagArgs.union(sortedFlagArgs)

        val maxLength = allSortedFlags.maxOf { it.flag?.length ?: 0 }

        for (arg in allSortedFlags) {

            help.append(" ")
                .append(if (arg.shortFlag != null) { "${arg.shortFlag}, " } else { "    " })



            help.append(arg.flag?.padEnd(maxLength) ?: " ".repeat(maxLength))
                .append("   ")
                .append(arg.description)
                .appendLine()
        }
        return help.toString()
    }
}
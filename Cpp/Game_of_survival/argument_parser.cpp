#include "argument_parser.h"

using std::string;
using std::unordered_map;
using Parser::ArgumentType;

Parser::ArgumentType Parser::ArgumentParser::flag_to_argument_type(const std::string& flag)
{
	if ("-i" == flag)
		return ArgumentType::InputFilePath;
	else if ("-c" == flag)
		return ArgumentType::IterationCount;
	else if ("-o" == flag)
		return ArgumentType::OutputFilePath;
	else
		throw Exception::ArgumentException("The flag " + flag + " is not handled by the program.");
}

bool Parser::ArgumentParser::has_all_argument_types(const std::unordered_map<Parser::ArgumentType, std::string> args)
{
	auto args_end = args.end();
	return args.find(ArgumentType::InputFilePath) != args_end &&
		args.find(ArgumentType::IterationCount) != args_end &&
		args.find(ArgumentType::OutputFilePath) != args_end;
}

std::unordered_map<Parser::ArgumentType, std::string> Parser::ArgumentParser::parse_argument_array(const std::vector<std::string>& argument_array)
{
	if (argument_array.empty())
		throw Exception::ArgumentException("No additional argument has been given there.");
	
	unordered_map<ArgumentType, string> parsed_arguments;

	int argsiz = argument_array.size();
	int i=0;
	while (i < argsiz) {

		ArgumentType type;
		try {
			type = flag_to_argument_type(argument_array[i++]);
		}
		catch (Exception::ApplicationException& ex) {
			throw Exception::ParsingException(ex.what());
		}

		if (parsed_arguments.find(type) != parsed_arguments.end())
			throw Exception::ParsingException("One of the flags has been multiplied.");

		if (i >= argsiz)
			throw Exception::ParsingException("There is no value for flag " + argument_array[i-1] +".");

		if (type == ArgumentType::IterationCount && !Helper::StringHelper::is_uint(argument_array[i]))
			throw Exception::ParsingException("Iteration count value is not an unsigned integer.");

		parsed_arguments.emplace(type, argument_array[i++]);
	}

	if (!has_all_argument_types(parsed_arguments))
		throw Exception::ParsingException("There must be 3 flags with their arguments.");
	
	return parsed_arguments;
}

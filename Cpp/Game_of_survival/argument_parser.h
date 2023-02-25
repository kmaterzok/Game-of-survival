#pragma once

#include <unordered_map>
#include <vector>
#include <string>
#include "argument_type.h"
#include "all_exceptions.h"
#include "string_helper.h"

namespace Parser {
	class ArgumentParser
	{
	public:
		static Parser::ArgumentType flag_to_argument_type(const std::string& flag);
		static bool has_all_argument_types(const std::unordered_map<Parser::ArgumentType, std::string> args);
		static std::unordered_map<Parser::ArgumentType, std::string> parse_argument_array(const std::vector<std::string>& argument_array);
	};
}

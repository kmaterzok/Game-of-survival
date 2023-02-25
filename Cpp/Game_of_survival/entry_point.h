#pragma once

#include <iostream>
#include <fstream>
#include <unordered_map>
#include <vector>
#include <thread>
#include <chrono>

#include "dependencies.h"
#include "argument_parser.h"
#include "game_plane.h"

namespace Starter {
	class EntryPoint
	{
	private:
		std::vector<std::string> command_line_arguments;
		void execute_main_process(std::unordered_map<Parser::ArgumentType, std::string>& parsed_args, std::fstream& input_file, std::fstream& output_file);
	public:
		static const int SINGLE_FRAME_DURATION = 100;

		EntryPoint(int argc, char** argv);
		static void display_help();
		void launch_application();
	};
}

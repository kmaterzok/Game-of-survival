#include "entry_point.h"

Starter::EntryPoint::EntryPoint(int argc, char** argv)
{
	for (int i = 1; i < argc; i++)
		command_line_arguments.push_back((std::string)argv[i]);
}

void Starter::EntryPoint::display_help()
{
	std::cerr << "Game of survival" << std::endl <<
		"A variation of game of life" << std::endl << std::endl <<
		"Flags:" << std::endl <<
		"\t-h          Help" << std::endl <<
		"\t-i [PATH]   Input file path" << std::endl <<
		"\t-o [PATH]   Output file path" << std::endl <<
		"\t-c [COUNT]  Iteration count" << std::endl <<
		std::endl;
}

void Starter::EntryPoint::launch_application()
{
	std::unordered_map<Parser::ArgumentType, std::string> parsed_args;
	std::fstream input_file;
	std::fstream output_file;

	if (command_line_arguments.size() == 1 && command_line_arguments.front() == "-h") {
		this->display_help();
		return;
	}


	try {
		execute_main_process(parsed_args, input_file, output_file);
	}
	catch (Exception::ApplicationException& ex) {
		input_file.close();
		output_file.close();
		std::cerr << ex.what() << std::endl << std::endl;
		this->display_help();
		return;
	}
	catch (std::exception & ex) {
		input_file.close();
		output_file.close();
		std::cerr << "An unexpected error has occoured:" <<
			std::endl << ex.what() << std::endl << std::endl;
		this->display_help();
		return;
	}

}


void Starter::EntryPoint::execute_main_process(std::unordered_map<Parser::ArgumentType, std::string>& parsed_args, std::fstream& input_file, std::fstream& output_file)
{
	parsed_args = Parser::ArgumentParser::parse_argument_array(command_line_arguments);

	input_file.open(parsed_args[Parser::ArgumentType::InputFilePath], std::ios_base::in);
	if (!input_file.is_open())
		throw Exception::FileHandleException("The input file could not have been opened.");

	Model::GamePlane plane;
	plane.load(input_file);
	plane.send_to_ostream(std::cout);

	int iteration_count = std::stoi(parsed_args[Parser::ArgumentType::IterationCount]);
	for (int i = 0; i < iteration_count; i++) {

		static auto begin_time = std::chrono::high_resolution_clock::now();
		plane.next_iteration();
		static auto finish_time = std::chrono::high_resolution_clock::now();
		static int corrected_duration = SINGLE_FRAME_DURATION - (int)std::chrono::duration_cast<std::chrono::milliseconds>(finish_time - begin_time).count();

		std::this_thread::sleep_for(std::chrono::milliseconds(corrected_duration));


		Platform::DependentUtilities::clear_terminal();
		std::cout << std::endl << std::endl;
		plane.send_to_ostream(std::cout);
	}
	std::cout << std::endl << std::endl;

	output_file.open(parsed_args[Parser::ArgumentType::OutputFilePath], std::ios_base::out);
	if (output_file.is_open())
		plane.save(output_file);
	else
		throw Exception::FileHandleException("Output file cannot be handled successfully.");

	output_file.close();
}

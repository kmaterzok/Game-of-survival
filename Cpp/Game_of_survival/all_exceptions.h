#pragma once

#include <exception>
#include <string>
#include <cstring>

namespace Exception {

	class ApplicationException : public std::exception {
	public:
		const std::string exception_message;

		ApplicationException(const std::string& msg);
		virtual const char* what() const throw();
	};





	class FileHandleException : public ApplicationException {
	public:
		FileHandleException(const std::string& msg);
	};


	class ArgumentException : public ApplicationException {
	public:
		ArgumentException(const std::string& msg);
	};


	class ParsingException : public ApplicationException {
	public:
		ParsingException(const std::string& msg);
	};

}
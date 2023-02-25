#include "all_exceptions.h"


Exception::ApplicationException::ApplicationException(const std::string& msg) : exception_message(msg) {}

const char* Exception::ApplicationException::what() const throw() {
	return exception_message.c_str();
}


Exception::FileHandleException::FileHandleException(const std::string& msg) : ApplicationException(msg) {}

Exception::ArgumentException::ArgumentException(const std::string& msg) : ApplicationException(msg) {}

Exception::ParsingException::ParsingException(const std::string& msg) : ApplicationException(msg) {}

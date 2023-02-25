#include "string_helper.h"

bool Helper::StringHelper::is_uint(const std::string& text)
{
	auto len = text.length();
	return std::strspn(text.c_str(), "0123456789") == len && len > 0;
}

#pragma once

#include <iostream>

#if defined(unix) || defined(__unix__) || defined(__unix)
#define UNIX_PLATFORM
#elif _WIN32
#define WIN_PLATFORM
#endif


#ifdef UNIX_PLATFORM
#include <unistd.h>
#elif defined(WIN_PLATFORM)
#include <windows.h>
#endif

namespace Platform {
	class DependentUtilities {
	public:
		static void clear_terminal();
	};
}
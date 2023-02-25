#include "dependencies.h"

void Platform::DependentUtilities::clear_terminal()
{
	#ifdef WIN_PLATFORM
	::system("cls");
	#else
	::system("clear");
	#endif
}

#include "entry_point.h"

int main(int argc, char** argv) {
	
	Starter::EntryPoint app_launcher(argc, argv);
	app_launcher.launch_application();
	return 0;
}
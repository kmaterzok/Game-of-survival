#include "random_helper.h"

int Helper::RandomHelper::get_random_from_uniform_distribution(int min, int max)
{
	static auto gen_seed = std::chrono::system_clock::now().time_since_epoch().count();
	static std::default_random_engine generator(gen_seed);

	std::uniform_int_distribution<int> distribution(min, max);

	return distribution(generator);
}

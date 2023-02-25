#pragma once

#include <random>
#include <chrono>

namespace Helper {
	class RandomHelper
	{
	public:
		static int get_random_from_uniform_distribution(int min, int max);
	};
}


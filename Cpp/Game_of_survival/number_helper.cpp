#include "number_helper.h"

int Helper::NumberHelper::out_of_min_max_coalesce(int val, int min, int max, int min_repl, int max_repl)
{
	if (val < min)
		return min_repl;
	else if (val > max)
		return max_repl;
	else
		return val;
}

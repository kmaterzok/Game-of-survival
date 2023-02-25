#pragma once

#include "cell.h"
#include "hardly_revivable_cell.h"
#include "hibernating_cell.h"
#include "limited_cell.h"
#include "normal_cell.h"
#include "all_exceptions.h"

namespace Model {
	class CellFactory
	{
	public:
		static Cell* new_cell(const char type_char, const bool is_alive);
	};
}

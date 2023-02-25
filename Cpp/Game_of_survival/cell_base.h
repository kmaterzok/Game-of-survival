#pragma once

#include <unordered_set>
#include <algorithm>
#include "cell.h"

namespace Model {
	class CellBase : public Cell
	{
	protected:
		CellBase(char declared_type_char);
		~CellBase() = default;
		std::unordered_set<Model::Cell*> neighbouring_cells;
		char current_type_char;
	public:
		virtual void add_neighbour(Cell* cell);
		virtual char get_type_char();
		virtual int count_alive_neighbours();
	};
}

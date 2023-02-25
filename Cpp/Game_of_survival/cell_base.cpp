#include "cell_base.h"

Model::CellBase::CellBase(char declared_type_char) : current_type_char(declared_type_char)
{
}

void Model::CellBase::add_neighbour(Cell* cell) {
	this->neighbouring_cells.emplace(cell);
}

char Model::CellBase::get_type_char() {
	return this->current_type_char;
}

int Model::CellBase::count_alive_neighbours()
{
	return std::count_if(this->neighbouring_cells.begin(), this->neighbouring_cells.end(), [](Cell* cell) {
		return cell->is_perceived_as_alive();
	});
}

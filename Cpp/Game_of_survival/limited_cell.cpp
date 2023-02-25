#include "limited_cell.h"

Model::LimitedCell::LimitedCell(bool is_alive) : is_alive_now(is_alive), CellBase(this->TYPE_CHAR)
{
}

const std::string& Model::LimitedCell::get_alive_state_sign() {
	static std::string obj = "\033[1;31;32m+\033[0m";
	return obj;
}

const std::string& Model::LimitedCell::get_dead_state_sign()
{
	static std::string obj = "\033[1;31;31m+\033[0m";
	return obj;
}

bool Model::LimitedCell::is_perceived_as_alive() {
	return this->is_alive_now;
}

void Model::LimitedCell::stash_next_state()
{
	int alive_neighbours_count = this->count_alive_neighbours();

	if (this->is_alive_now)
		this->is_alive_next = alive_neighbours_count == 2 || alive_neighbours_count == 3;
	else
		this->is_alive_next = alive_neighbours_count == 3;
}

void Model::LimitedCell::release_next_state() {
	this->is_alive_now = this->is_alive_next;
}

const std::string& Model::LimitedCell::get_current_state_sign()
{
	return this->is_alive_now ?
		this->get_alive_state_sign() :
		this->get_dead_state_sign();
}

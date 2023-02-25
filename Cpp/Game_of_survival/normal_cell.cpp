#include "normal_cell.h"

const std::string& Model::NormalCell::get_alive_state_sign() {
	static std::string obj = "\033[1;31;32m#\033[0m";
	return obj;
}

const std::string& Model::NormalCell::get_dead_state_sign() {
	static std::string obj = " ";
	return obj;
}

Model::NormalCell::NormalCell(bool is_alive) :
	is_alive_now(is_alive),
	CellBase(this->TYPE_CHAR)
{
}

bool Model::NormalCell::is_perceived_as_alive() {
	return this->is_alive_now;
}

void Model::NormalCell::stash_next_state()
{
	int alive_neighbours_count = this->count_alive_neighbours();

	if (this->is_alive_now)
		this->is_alive_next = alive_neighbours_count == 2 || alive_neighbours_count == 3;
	else
		this->is_alive_next = alive_neighbours_count == 3;
}

void Model::NormalCell::release_next_state() {
	this->is_alive_now = this->is_alive_next;
}

const std::string& Model::NormalCell::get_current_state_sign()
{
	return this->is_alive_now ?
		this->get_alive_state_sign() :
		this->get_dead_state_sign();
}

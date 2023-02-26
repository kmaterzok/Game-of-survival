#include "hardly_revivable_cell.h"

Model::HardlyRevivableCell::HardlyRevivableCell(bool is_alive) :
	is_alive_now(is_alive),
	CellBase(this->TYPE_CHAR)
{
}

const std::string& Model::HardlyRevivableCell::get_alive_state_sign() {
	static std::string obj = "\033[1;31;32m*\033[0m";
	return obj;
}

const std::string& Model::HardlyRevivableCell::get_dead_state_sign() {
	static std::string obj = "\033[1;31;31m*\033[0m";
	return obj;
}

bool Model::HardlyRevivableCell::is_perceived_as_alive() {
	return this->is_alive_now;
}

void Model::HardlyRevivableCell::stash_next_state()
{
	int alive_neighbours_count = this->count_alive_neighbours();
	bool will_be_alive;

	if (this->is_alive_now || this->iterations_to_wait_now > 0) {
		will_be_alive = alive_neighbours_count == 2 || alive_neighbours_count == 3;

		if (will_be_alive)
			this->iterations_to_wait_next = this->iterations_to_wait_now == 0 ?
			0 : this->iterations_to_wait_now - 1;
		else
			this->iterations_to_wait_next = 2;
	}
	else
		this->iterations_to_wait_next = 2;

	this->is_alive_next = this->iterations_to_wait_next == 0;
}

void Model::HardlyRevivableCell::release_next_state() {
	this->is_alive_now = this->is_alive_next;
	this->iterations_to_wait_now = this->iterations_to_wait_next;
}

const std::string& Model::HardlyRevivableCell::get_current_state_sign()
{
	return this->is_alive_now ?
		this->get_alive_state_sign() :
		this->get_dead_state_sign();
}

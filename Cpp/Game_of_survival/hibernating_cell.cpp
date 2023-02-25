#include "hibernating_cell.h"

Model::HibernatingCell::HibernatingCell(bool is_alive) :
	vitality_now(is_alive ? Model::HibernatingCellState::Alive : Model::HibernatingCellState::Dead),
	CellBase(this->TYPE_CHAR)
{
}

const std::string& Model::HibernatingCell::get_alive_state_sign() {
	static std::string obj = "\033[1;31;32m@\033[0m";
	return obj;
}

const std::string& Model::HibernatingCell::get_dead_state_sign() {
	static std::string obj = "\033[1;31;31m@\033[0m";
	return obj;
}

const std::string& Model::HibernatingCell::get_hibernated_state_sign() {
	static std::string obj = "\033[1;31;33m@\033[0m";
	return obj;
}

bool Model::HibernatingCell::is_perceived_as_alive() {
	return this->vitality_now == Model::HibernatingCellState::Alive;
}

void Model::HibernatingCell::stash_next_state()
{
	int alive_neighbours_count = this->count_alive_neighbours();
	switch (this->vitality_now) {
	
	case Model::HibernatingCellState::Alive: {
		if (alive_neighbours_count == 2 || alive_neighbours_count == 3)
			this->vitality_next = Model::HibernatingCellState::Alive;
		else {
			this->vitality_next = Model::HibernatingCellState::Hibernated; // The first iteration (it is not counted)
			this->iterations_left_next = Helper::RandomHelper::get_random_from_uniform_distribution(0, 9);
		}
		break;
	}

	case Model::HibernatingCellState::Dead: {
		this->vitality_next = alive_neighbours_count == 3 ?
			Model::HibernatingCellState::Alive :
			Model::HibernatingCellState::Dead;
		break;
	}

	case Model::HibernatingCellState::Hibernated: {

		if (this->iterations_left_now == 0)
			this->vitality_next = alive_neighbours_count == 3 ?
				Model::HibernatingCellState::Alive :			
				Model::HibernatingCellState::Dead;
		else
			this->iterations_left_next = this->iterations_left_now - 1;
		break;
	}

	}
}

void Model::HibernatingCell::release_next_state() {
	this->vitality_now = this->vitality_next;
	this->iterations_left_now = this->iterations_left_next;
}

const std::string& Model::HibernatingCell::get_current_state_sign() {
	switch (this->vitality_now) {
	case Model::HibernatingCellState::Alive:
		return this->get_alive_state_sign();
	case Model::HibernatingCellState::Dead:
		return this->get_dead_state_sign();
	case Model::HibernatingCellState::Hibernated:
		return this->get_hibernated_state_sign();
	}
	throw Exception::ApplicationException("Undeclared enum type");
}

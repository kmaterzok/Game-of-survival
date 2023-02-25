#pragma once

#include <string>

namespace Model {
	class Cell
	{
	public:
		virtual char get_type_char() = 0;
		virtual void add_neighbour(Cell* cell) = 0;
		virtual int count_alive_neighbours() = 0;

		virtual bool is_perceived_as_alive() = 0;
		virtual void stash_next_state() = 0;
		virtual void release_next_state() = 0;
		virtual const std::string& get_current_state_sign() = 0;

		virtual ~Cell() = default;
	};
}


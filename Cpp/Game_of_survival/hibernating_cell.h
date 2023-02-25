#pragma once

#include "cell_base.h"
#include "hibernating_cell_state.h"
#include "random_helper.h"
#include "all_exceptions.h"

namespace Model {
	class HibernatingCell : public CellBase
	{
	private:
		Model::HibernatingCellState vitality_now;
		Model::HibernatingCellState vitality_next;
		int iterations_left_now;
		int iterations_left_next;
	public:
		static const char TYPE_CHAR = 'H';
		HibernatingCell(bool is_alive);

		static const std::string& get_alive_state_sign();
		static const std::string& get_dead_state_sign();
		static const std::string& get_hibernated_state_sign();

		virtual bool is_perceived_as_alive();
		virtual void stash_next_state();
		virtual void release_next_state();
		virtual const std::string& get_current_state_sign();
	};
}

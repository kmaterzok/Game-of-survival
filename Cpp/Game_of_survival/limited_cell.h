#pragma once

#include "cell_base.h"

namespace Model {
	class LimitedCell : public CellBase
	{
	private:
		bool is_alive_now;
		bool is_alive_next;
	public:
		static const char TYPE_CHAR = 'O';
		LimitedCell(bool is_alive);

		static const std::string& get_alive_state_sign();
		static const std::string& get_dead_state_sign();

		virtual bool is_perceived_as_alive();
		virtual void stash_next_state();
		virtual void release_next_state();
		virtual const std::string& get_current_state_sign();
	};
}

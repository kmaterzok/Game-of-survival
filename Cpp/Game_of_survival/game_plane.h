#pragma once

#include <iostream>
#include <string>
#include <vector>
#include <memory>
#include <array>

#include "cell.h"
#include "cell_factory.h"
#include "all_exceptions.h"
#include "number_helper.h"

using std::vector;
using std::unique_ptr;
using Helper::NumberHelper;

namespace Model {
	class GamePlane
	{
	private:
		vector<vector<unique_ptr<Model::Cell>>> surviving_cells;

		#pragma region Private methods
		void bind_all_cells();
		void add_neighbouring_cells(const int plane_height, const int plane_width, const int cur_hx, const int cur_wx);
		#pragma endregion
	public:
		static std::array<char, 4U> get_allowed_type_chars();
		int get_width();
		int get_height();

		// Configure the plane based on data from a stream.
		void load(std::istream& filestr);
		// Save the state of the plane into a stream.
		void save(std::ostream& filestr);
		// Load the next iteration of the plane.
		void next_iteration();
		// Send the plane state as a visually presentable lines into a stream.
		void send_to_ostream(std::ostream& str);
	};
}

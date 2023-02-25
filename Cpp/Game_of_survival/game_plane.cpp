#include "game_plane.h"

std::array<char, 4U> Model::GamePlane::get_allowed_type_chars()
{
	return std::array<char, 4U> {
		Model::HardlyRevivableCell::TYPE_CHAR,
		Model::HibernatingCell::TYPE_CHAR,
		Model::LimitedCell::TYPE_CHAR,
		Model::NormalCell::TYPE_CHAR
	};
}

int Model::GamePlane::get_width() {
	return this->surviving_cells.front().size();
}

int Model::GamePlane::get_height() {
	return this->surviving_cells.size();
}

void Model::GamePlane::load(std::istream& filestr)
{
	this->surviving_cells.clear();
	auto accecpted_chars = this->get_allowed_type_chars();
	int line_number = 0;
		
	while (filestr.good()) {
		line_number++;

		std::string single_file_line;
		std::getline(filestr, single_file_line);
		if (single_file_line.empty())
			continue;
		this->surviving_cells.push_back({});
		
		int line_len = single_file_line.length();

		char last_char;
		bool is_type_char_needed_now = true;
		for (int i = 0; i < line_len; i++) {

			if (single_file_line[i] == ' ' || single_file_line[i] == '\r')
				continue;

			if (is_type_char_needed_now) {
				if (std::find(accecpted_chars.begin(), accecpted_chars.end(), single_file_line[i]) == accecpted_chars.end())
					throw Exception::ParsingException("Disallowed character in the input file at location Ln:" + std::to_string(line_number) + " Col: " + std::to_string(i+1) + ".");	
				last_char = single_file_line[i];
			}
			else {
				if (single_file_line[i] != '0' && single_file_line[i] != '1')
					throw Exception::ParsingException("There must be 0 or 1 in the input file at location Ln:" + std::to_string(line_number) + " Col: " + std::to_string(i+1) + ".");
				this->surviving_cells.back().push_back(
					unique_ptr<Cell>(Model::CellFactory::new_cell(last_char, single_file_line[i] == '1'))
				);
			}

			is_type_char_needed_now = !is_type_char_needed_now;
		}

		if (this->get_height() > 1 && this->get_width() != this->surviving_cells.back().size())
			throw Exception::ParsingException("Count of cells in rows is not distributed equally.");
	}
	this->bind_all_cells();
}

void Model::GamePlane::save(std::ostream& filestr)
{
	int plane_height = this->get_height();
	int plane_width = this->get_width();

	for (int hx = 0; hx < plane_height; hx++) {
		for (int wx = 0; wx < plane_width; wx++) {
			auto* cell = this->surviving_cells[hx][wx].get();
			filestr << cell->get_type_char() << " " <<
				(cell->is_perceived_as_alive() ? "1" : "0") << " ";
		}
		filestr << std::endl;
	}
}

void Model::GamePlane::next_iteration()
{
	int plane_height = this->get_height();
	int plane_width = this->get_width();

	for (int hx = 0; hx < plane_height; hx++)
		for (int wx = 0; wx < plane_width; wx++)
			this->surviving_cells[hx][wx]->stash_next_state();

	for (int hx = 0; hx < plane_height; hx++)
		for (int wx = 0; wx < plane_width; wx++)
			this->surviving_cells[hx][wx]->release_next_state();
		
}

void Model::GamePlane::send_to_ostream(std::ostream& str)
{
	int plane_height = this->get_height();
	int plane_width = this->get_width();

	for (int hx = 0; hx < plane_height; hx++) {
		for (int wx = 0; wx < plane_width; wx++)
			str << this->surviving_cells[hx][wx]->get_current_state_sign();
		str << std::endl;
	}
}

void Model::GamePlane::bind_all_cells()
{
	int plane_height = this->get_height();
	int plane_width = this->get_width();

	for (int hx = 0; hx < plane_height; hx++)
		for (int wx = 0; wx < plane_width; wx++)
			this->add_neighbouring_cells(plane_height, plane_width, hx, wx);

}

void Model::GamePlane::add_neighbouring_cells(const int plane_height, const int plane_width, const int hx, const int wx)
{
	auto* cell = this->surviving_cells[hx][wx].get();
	bool is_limited_type = cell->get_type_char() == Model::LimitedCell::TYPE_CHAR;

	for (int ho = -1; ho <= 1; ho++) {
		int checked_hx = NumberHelper::out_of_min_max_coalesce(hx + ho, 0, plane_height - 1, plane_height - 1, 0);

		for (int wo = -1; wo <= 1; wo++) {
			int checked_wx = NumberHelper::out_of_min_max_coalesce(wx + wo, 0, plane_width - 1, plane_width - 1, 0);

			if (hx == checked_hx && wx == checked_wx)
				continue;
			else if (is_limited_type && (hx != checked_hx && wx != checked_wx))
				continue;


			auto* neighbour = this->surviving_cells[checked_hx][checked_wx].get();
			cell->add_neighbour(neighbour);
		}
	}

}
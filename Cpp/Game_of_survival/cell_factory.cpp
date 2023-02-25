#include "cell_factory.h"

Model::Cell* Model::CellFactory::new_cell(const char type_char, const bool is_alive)
{
	switch (type_char) {
	case Model::NormalCell::TYPE_CHAR:
		return (Model::Cell*)new Model::NormalCell(is_alive);

	case Model::LimitedCell::TYPE_CHAR:
		return (Model::Cell*)new Model::LimitedCell(is_alive);

	case Model::HibernatingCell::TYPE_CHAR:
		return (Model::Cell*)new Model::HibernatingCell(is_alive);

	case Model::HardlyRevivableCell::TYPE_CHAR:
		return (Model::Cell*)new Model::HardlyRevivableCell(is_alive);
	}
	throw Exception::ArgumentException("Wrong type_char");
}

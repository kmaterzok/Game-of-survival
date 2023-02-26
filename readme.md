__Game of survival__

_A variation of Game of Life_

# Arguments
```
 -c, --count    Quantity of iterations
 -i, --input    Input file describing the plane at start
 -o, --output   Output file describing the plane at end
```

The precise way of invoking is defined in the help texts for each language.

# Cells
* Normal cell – the cell known from the original Conway's Game of Life, without modifications. Identified in the plane file by ```N``` character.
* Limited cell – the normal cell, which treats only non-diagonal cells as its neighbours. Identified in the plane file by ```O``` character.
* Hibernating cell – the normal cell, which gets hibernated for some iterations (from 0 to 9) instead of being immediately dead. Identified in the plane file by ```H``` character.
* Hardly revivable cell – the cell, that must be surrounded by 2 or 3 alive neighbours for 2 iterations in a row before becoming an alive one. Identified in the plane file by ```T``` character.

# Input file notation
Game plane files describe the beginning state of a simulation (first iteration). As the simulation continues, new states of the cells are established. The plane is implemented as a torus (a doughnut).

The plane file contains settings for each cell. A configuration for a cell is as follows, e.g.:
```
N0 N1 N0 N1
T0 T1 T0 T0
O1 O1 O1 O1
L0 L1 L1 L0
```

Each cell is represented by the character identifying the cell type and a digit meaning if the cell is alive (1) or dead (0). A quantity of cells for every row must be identical.

# Remarks

Every implementation has its own differences. There are some of them below.

## All implementations
* This program must be run under a terminal that recognises escape codes for handling colours of cell states.

## C++
* All command line arguments are required for launch of simulation.
* The only available argument flags are the short ones only.

## Kotlin
* Only the input file is required for launch of simulation.

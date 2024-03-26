# Lattice Boltzman Method - boundary conditions
_(Read information about model [here](/).)_

Examples below show speeds of particle flow in the domain. Left screen is vertical speed, right is horizontal speed.
Both simulations should stabilize in the same state.

### Example 1
Boundary conditions:
* top - symmetric,
* bottom - bounce back,
* left - open, u<sub>x</sub> = linear gradient from 0.25 (top) to 0 (bottom), u<sub>y</sub> = 0,
* right - open, concentration = 1,

concentration = 1 across whole domain,
u = [0, 0] for all cells except boundaries.

[![]](https://github.com/jeremi1111111/Lattice-Boltzmann-Method/assets/130166969/67b2526a-8031-4ebe-899b-baf9f19d1fde)

### Example 2
Boundary conditions:
* top - constant, u<sub>x</sub> = 0.25,
* bottom - constant, u<sub>x</sub> = 0,
* left - constant, u<sub>x</sub> = linear gradient from 0.25 (top) to 0 (bottom),
* right - constant, u<sub>x</sub> = linear gradient from 0.25 (top) to 0 (bottom),

u<sub>y</sub> = 0 for all boundaries,
concentration = 1 across whole domain,
u = [0, 0] for all cells except boundaries.

[![]](https://github.com/jeremi1111111/Lattice-Boltzmann-Method/assets/130166969/abd2f3b9-249a-482a-95f8-1c6fa8cd5919)

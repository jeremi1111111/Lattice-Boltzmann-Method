# Lattice Boltzmann Method
Project made during Discrete Modelling (Modelowanie Dyskretne) course at AGH UST.

_(Examples of how each program operates can be found in corresponding directories.)_

1. [Lattice Gas Automata](LGA)

   Lattice Gas Automata (LGA) is a simulation model based on concept of [Cellular Automata](https://github.com/jeremi1111111/Discrete-Modelling/tree/master/MDLab4).
A single cell stores information about gas particles moving in one of four directions -
left, right, up and down. One iteration is a two-step process consisting of:
   * streaming - particles move between neighbouring cells,
   * collision - head-on colliding particles bounce off of each other
   and their resulting directions are perpendicular to how they entered a cell.
   
   More information about LGA can be found [here](https://en.wikipedia.org/wiki/Lattice_gas_automaton).


2. [LBM Diffusion](LBM-diffusion)

   Lattice Boltzmann Method (LBM) is an evolution of [LGA](LGA).
   In diffusion model, instead of storing information about gas particles,
   a cell stores concentration of gas particles.
   An iteration consists of two steps:
      * calculating concentration based on stream of particles entering cell from neighbouring cells.
      * declaring output stream of particles in certain direction
      that is calculated from concentration and output weight in that direction.
   
   These two steps are analogous to LGA's fundamental processes: streaming and collision.

   LBM can use different neighbourhood models, described as D*n*Q*m*,
   where _n_ - number of dimensions, _m_ - total number of directions.
   Model used here is D2Q4.

   More info about LBM can be found [here](https://en.wikipedia.org/wiki/Lattice_Boltzmann_methods).


3. [LBM Flow](LBM-flow)

   LBM model basing on diffusion alone is not enough to make a "realistic" simulation.
   However, adding particle speed vector to the definition of a cell
   can bring us closer to solving this problem. Now, output stream of a cell
   is also defined by from where the input stream came in.
   This means, if a stream was moving to the right, it will flow in that direction
   in next iteration as well. Impact of diffusion is lower in this model.

   To make things more "realistic", why can't we _expand_ our neighbourhood?
   Flow project uses D2Q9 direction model, which means concentration
   is also impacted by cell's previous state and state of neighbouring cells laying on diagonals.


4. [LBM Boundary Conditions](LBM-bc)

   Finally, my last modification. LBM is a finite model, defined in a closed domain.
   This is a fundamental assumption used in CA, from which LGA, and thus LBM, derive.
   To properly simulate flow, we also need to know how outside world impacts our model.
   That's where boundary conditions come to play.
   
   There are 5 boundary conditions in my program:
   1. Constant - boundary emits unchanging stream of particles in each iteration,
   2. Bounce back - each stream entering boundary of this type is reflected back
      to simulation space, but in perpendicular direction.
      This is the condition used in all previous projects,
   3. Symmetric - this condition assumes stream flows only in direction parallel to boundary surface.
      Perpendicular streams are "absorbed",
   4. Real - this is a combination of 2 previous conditions with influx of friction.
      Behaviour of this boundary is closest to reality, some part of stream is reflected,
      while the rest spreads across wall surface,
   5. Open - lets you set only one of two properties to be constant - speed or concentration.
      The other one is calculated from incoming stream and as well as that constant factor.
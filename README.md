# The walking bus problem

Given a complete weighted directed graph over a set of N points in a 2D grid, we want to find a spanning tree, 
rooted in point 0 such that it complies with the following constraint:<br />
* every path from the root to a node X must not be longer than alpha times the shortest path from the root 
to X, with alpha as a fixed parameter.<br />
Every tree that follows the above constraint is a feasible solution. Among all feasible solutions, we want 
to find the one that has:<br />
* minimum number of leaves (primary objective function) <br />
* minimum overall weight, that is the sum of the weight of all the arcs of the solution (secondary objective function) <br />

The goal is to produce a solver that is able to find a good approximate (or even optimal) solution for the 
problem above, within a maximum computation time of 1 hour.

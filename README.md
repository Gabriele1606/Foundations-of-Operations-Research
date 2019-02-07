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

## Problem solution

### Minimiaztion of nodes number

For this part we decide to use an algorithm that start to analyze the problem from the school (node zero)
to the each leaf.
Our algorithm start from the node zero (school) and try each possible path that respect the condition given
on the exercise text:<br />

<img src="https://github.com/Gabriele1606/Foundations-of-Operations-Research/blob/master/figure_1.png" width="45%">

so the condition is dij+djk>ALPHA*dik <br />

Once that the algorithm reached the leaf , come back to the node zero (school) and the node zero will
decide what is the path with the most number of nodes that can be reached from it and we put these paths
into an ArrayList. <br />

After that on the ArrayList that contains evaluated paths, each time, algorithm take the path that cover
more number of not connected nodes and we eliminate from the ArrayList all paths that intersect with the
path taken and in this way we respect the rule given during the description of the problem (if two paths
merge between them, they cannot divide again), this help us to decrease number of comparison in future
steps. We repeat this process until we cover all nodes of the given graph.

<img src="https://github.com/Gabriele1606/Foundations-of-Operations-Research/blob/master/figure_2.png" width="60%">

### Minimization of dangerous value about chosen path
The algorithm that we applied to minimize the dangerous in the graph works by choosing every time, from
paths with the same number of node, that one containing less danger.
When the choice has been performed, the dangers of that specific path, in the dangerous matrix, are
setted to 0, in this way in the following iterations a paths that follows a path already chosen has the
precedence among the others.

### Execution time of the algorithm
Since there is not time to explore all the possible path in graph from the school to the leaf we decided to
introduce a value called TRESHOLD in our code.
TRESHOLD is a parameter that is proportional with the number of nodes of the graph given and alpha
value, and permit to our code to terminate until 1 hour from the start of program execution. In this way we
found a feasible solutions related to the value of this parameter. For this delivery we chosen a better
compromise between time and solution.

## Executing the solver
It’s possible to run in cmd line jar file, by typing:
```
pedibus.jar <pathToDatFile>
```
example:
```
pedibus.jar C:\pedibus_10.dat
```
 Otherwise you can also use the .exe application by typing in the command line:
 ```
pedibus.exe <pathToDatFile>
```
example:
```
pedibus.exe C:\pedibus_10.dat
```

### Just to save time
It’s possible to run `start.bat` that will execute all the test instances.<br />
It’s possible to run `start_then_checker.bat` that will execute all test instances, and then run the checker with the related solution (checker, solution and exe must be in the same directory).<br />
Solution will be saved with the same filename of the dat file, with extension `.sol` in the same directory.
Will be saved also a log, that just print start time and end time.



 

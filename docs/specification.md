## Specification

The goal of this project is to create simple particle simulator.

The main problem that I want to solve is fast lookup's of particles. For example if the user interface is zoomed at small region, then the program should fetch only particles that are on that region instead of every particle.

Second big problem is that to simulate particles movement you sometimes need to get the particles that are close by. For example collision detection is this kind of problem.

My plan is to have separate interfaces for the models(the data) and the acceleration structures. So the user interface makes changes to the model and the model informs the acceleration structure about the changes.

The data structure that I will definately use is quadtree and the algorithms needed for it. I may or may not implement linked list and hash table for the project.

### The acceleration structures
I have used two variables n and m in the big O notatiotion. The n represents the area of the world and the variable m represents the number of particles.

####Direct
The direct acceleration structure is basically a dummy object that gives data directly from the model.

 * get\_at\_area O(m)
 * get\_at\_point O(m)
 * add/remove\_object O(1)
 * move\_object(1)
 * memory O(m)

#### Quadtree
The quadtree object inserts the particles into tree structure to speed up some of the common methods.

 * get\_at\_area O(log n + m)
 * get\_at\_point O(log n + m)
 * add/remove/move\_object O(log n)
 * memory O(n log n + m)

#### Array
The array interface inserts the particles into nearest array cell's linked list.

 * get\_at\_area O(n + m)
 * get\_at\_point O(m)
 * add/remove/move\_object O(1)
 * memory O(n)

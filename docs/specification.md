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
This structure is fast when there is small ammount of particles in the world. The size of the world dosen't affect to it's performance.

 * get\_at\_area O(m)
 * get\_at\_point O(m)
 * add/remove\_object O(1)
 * move\_object O(1)
 * memory usage O(m)

#### Quadtree
The quadtree class inserts the particles into tree structure to speed up some of the common methods on average.
Each Quadtree node contains at most k particles.
The Quadtree is ideal when the particles are located in varying densities.There can be lots of empty space without increasing memory usage almost at all.

 * get\_at\_area(a = h * w) O(k * a log a)
 * get\_at\_point O(log n + k)
 * add/remove/move\_object O(log n)
 * memory usage O(n log n + m)

#### Array
The array class inserts the particles into nearest array cell's linked list.
This is ideal when the density of the particles is more or less constant and the particles have same size. The biggest issue for the array implementation is that it's memory usage is relative to the world size.

 * get\_at\_area O(n + m)
 * get\_at\_point O(m)
 * add/remove/move\_object O(1)
 * memory usage O(n)

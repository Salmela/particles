## Implementation

This project contains three spatial data structures that are interchangeable. It also has two models that can be used for testing the data structures. The project has also gui, which can be used to visualise the data structures(not yet).

### Direct data sturcture
This data structure exists only for testing rest of the demo. It's implementation is as minimal as possible. It takes particles from the model and then just gives them to the rest of the system.
This data structure is implemented in file DirectStorage.java

### Array data sturcture
The array data structure has two-dimensional array where it puts the particles. The data structure basically splits the world into cells and each cell contains particles that are inside it.
This data structure is implemented in file ArrayStorage.java


### Tree data sturcture
The tree data structure uses tree structure to reduce space neaded for it.
This data structure is implemented in file TreeStorage.java

## Utility data structures

### Particle set
The particle set is implemented as a hash table.
This is used for gathering the particles from the acceleration structures. This is not yet implemented.

### Particle array
The particle array is just dynamically growing array.
This is used by the array data structure.

## Programming Assignment: Kd-Trees

### Write a data type to represent a set of points in the unit square.

 All points have x- and y-coordinates between 0 and 1. Implement data type using a 2d-tree to support efficient range search (find all of the points contained in a query rectangle) and nearest-neighbor search (find a closest point to a query point). 2d-trees have numerous applications, ranging from classifying astronomical objects to computer animation to speeding up neural networks to mining data to image retrieval.

__Performance requirements.__

Your implementation should support insert() and contains() in time proportional to the logarithm of the number of points in the set in the worst case; it should support nearest() and range() in time proportional to the number of points in the set.
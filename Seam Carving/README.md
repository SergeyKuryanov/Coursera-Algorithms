## Programming Assignment 2: Seam Carving

[Assignment Specification](http://coursera.cs.princeton.edu/algs4/assignments/seam.html)

### Seam-carving is a content-aware image resizing technique where the image is reduced in size by one pixel of height (or width) at a time. A vertical seam in an image is a path of pixels connected from the top to the bottom with one pixel in each row.

__Performance requirements.__ The width(), height(), and energy() methods should take constant time in the worst case. All other methods should run in time at most proportional to width × height in the worst case. For faster performance, do not construct explicit DirectedEdge and EdgeWeightedDigraph objects.
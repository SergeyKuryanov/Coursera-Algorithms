## Programming Assignment 1: Percolation

### Write a program to estimate the value of the percolation threshold via Monte Carlo simulation.

Monte Carlo simulation. To estimate the percolation threshold, consider the following computational experiment:

* Initialize all sites to be blocked.
* Repeat the following until the system percolates:
* Choose a site uniformly at random among all blocked sites.
* Open the site.
* The fraction of sites that are opened when the system percolates provides an estimate of the percolation threshold.

__Performance requirements.__
The constructor should take time proportional to n2; all methods should take constant time plus a constant number of calls to the union–find methods union(), find(), connected(), and count().
# Ukkonen Algorithm in Java

This repository contains a Java implementation of Esko Ukkonen's edit distance algorithm, compared against Apache Levenshtein implementation.

## Ukkonen: Approximate String Matching

This project implements Esko Ukkonen's [Approximate String Matching algorithm](https://www.sciencedirect.com/science/article/pii/S0019995885800462), enhanced with concepts from [An Extension of Ukkonen's Enhanced Dynamic Programming ASM Algorithm by Hal Berghel and David Roach](http://berghel.net/publications/asm/asm.pdf).

Ukkonen's algorithm proves to be highly competitive with the traditional [Levenshtein distance](https://en.wikipedia.org/wiki/Levenshtein_distance), especially for longer strings where it demonstrates superior performance.

Moreover, Ukkonen's algorithm offers the advantage of setting a threshold for the distance, further optimizing performance for longer texts beyond the specified threshold.

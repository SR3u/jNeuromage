package org.marasm.neuromage.math;

// A class to represent vector for vectorised math
public interface Vector {

    CalculatedVector calculate();

    Vector copy();

    int size();
}

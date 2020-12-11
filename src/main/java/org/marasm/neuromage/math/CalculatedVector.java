package org.marasm.neuromage.math;

public interface CalculatedVector extends Vector {

    double[] data();

    @Override
    default CalculatedVector calculate() {
        return this;
    }

}

package org.marasm.neuromage.neural.activation;

import org.marasm.neuromage.math.Vector;
import org.marasm.neuromage.math.VectorMath;
import org.marasm.neuromage.neural.ActivationFunction;

public class Sigmoid implements ActivationFunction {
    private static final VectorMath math = VectorMath.get();

    @Override
    public double applyAsDouble(Vector weights, Vector input) {
        return 1 / (1 + Math.exp(math.sum(math.mul(input, weights))));
    }
}

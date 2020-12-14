package org.marasm.neuromage.neural.activation;

import org.marasm.neuromage.math.Vector;
import org.marasm.neuromage.math.VectorMath;
import org.marasm.neuromage.neural.ActivationFunction;

public class Sum implements ActivationFunction {
    private static final VectorMath math = VectorMath.get();

    @Override
    public double applyAsDouble(Vector weights, Vector input) {
        return math.sum(math.mul(input, weights));
    }
}

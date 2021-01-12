package org.marasm.neuromage.neural.activation;

import sr3u.jvec.Vector;
import sr3u.jvec.JMath;
import org.marasm.neuromage.neural.ActivationFunction;

public class Sum implements ActivationFunction {

    private static final JMath math = JMath.get();

    @Override
    public Vector apply(Vector v) {
        return v;
    }

    @Override
    public Vector applyDerivative(Vector v) {
        return math.vec().zeros(v.size());
    }

}

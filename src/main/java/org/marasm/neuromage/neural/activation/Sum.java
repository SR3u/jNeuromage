package org.marasm.neuromage.neural.activation;

import sr3u.jvec.Matrix;
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
        return math.vec().ones(v.size());
    }

    @Override
    public Matrix apply(Matrix v) {
        return v;
    }

    @Override
    public Matrix applyDerivative(Matrix v) {
        return math.matrix(v.size(), 1.0);
    }

}

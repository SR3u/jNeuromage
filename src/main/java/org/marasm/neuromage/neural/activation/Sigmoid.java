package org.marasm.neuromage.neural.activation;

import sr3u.jvec.Matrix;
import sr3u.jvec.Vector;
import sr3u.jvec.JMath;
import org.marasm.neuromage.neural.ActivationFunction;

public class Sigmoid implements ActivationFunction {
    private static final JMath math = JMath.get();

    @Override
    public Vector apply(Vector v) {
        final Vector minusV = math.vec().zeros(v.size()).sub(v);
        final Vector ones = math.vec().ones(v.size());
        final Vector add = ones.add(minusV.exp());
        return ones.div(add);
    }

    @Override
    public Vector applyDerivative(Vector v) {
        final Vector ones = math.vec().ones(v.size());
        final Vector sigma = apply(v);
        return sigma.mul(ones.sub(sigma));
    }

    @Override
    public Matrix apply(Matrix v) {
        final Matrix minusV = math.matrix(v.size(), 0.0).sub(v);
        final Matrix ones = math.matrix(v.size(), 1.0);
        final Matrix add = ones.add(minusV.exp());
        return ones.divScalar(add);
    }

    @Override
    public Matrix applyDerivative(Matrix v) {
        final Matrix ones = math.matrix(v.size(), 1.0);
        final Matrix sigma = apply(v);
        return sigma.mul(ones.sub(sigma).t());
    }

}

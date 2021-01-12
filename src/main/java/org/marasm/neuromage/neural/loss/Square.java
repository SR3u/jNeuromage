package org.marasm.neuromage.neural.loss;

import org.marasm.neuromage.neural.CostFunction;
import sr3u.jvec.JMath;
import sr3u.jvec.Vector;

public class Square implements CostFunction {
    private static final JMath math = JMath.get();

    @Override
    public Vector apply(Vector v, Vector inputs) {
        final Vector sub = v.sub(inputs);
        final Vector subSq = sub.mul(sub);
        return subSq.div(2);
    }

    @Override
    public Vector applyDerivative(Vector v) {
        return null;
    }
}

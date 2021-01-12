package org.marasm.neuromage.neural;

import sr3u.jvec.Vector;

public interface CostFunction {
    Vector apply(Vector v, Vector inputs);

    Vector applyDerivative(Vector v);
}

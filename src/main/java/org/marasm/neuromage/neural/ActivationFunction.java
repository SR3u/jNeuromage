package org.marasm.neuromage.neural;

import sr3u.jvec.Matrix;
import sr3u.jvec.Vector;

import java.io.Serializable;

public interface ActivationFunction extends Serializable {
    Vector apply(Vector v);

    Vector applyDerivative(Vector v);

    Matrix apply(Matrix v);

    Matrix applyDerivative(Matrix v);
}

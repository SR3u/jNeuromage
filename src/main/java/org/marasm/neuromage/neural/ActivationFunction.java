package org.marasm.neuromage.neural;

import org.marasm.neuromage.math.Vector;

import java.io.Serializable;

public interface ActivationFunction extends Serializable {
    double applyAsDouble(Vector weights, Vector input);
}

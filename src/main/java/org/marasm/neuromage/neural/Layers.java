package org.marasm.neuromage.neural;

import org.marasm.neuromage.math.VectorMath;
import org.marasm.neuromage.neural.activation.Sigmoid;
import org.marasm.neuromage.neural.activation.Sum;

import java.io.Serializable;

public class Layers implements Serializable {
    private static final VectorMath math = VectorMath.get();

    public static Layer sigmoid(int size, int inputSize) {
        return new Layer(size, inputSize, new Sigmoid());
    }

    public static Layer sum(int size, int inputSize) {
        return new Layer(size, inputSize, new Sum());
    }
}
package org.marasm.neuromage.neural;

import org.marasm.neuromage.math.VectorMath;

import java.io.Serializable;

public class Layers implements Serializable {
    private static final VectorMath math = VectorMath.get();

    public static Layer sigmoid(int size, int inputSize) {
        return new Layer(size, inputSize, (w, i) -> 1 / (1 + Math.exp(math.sum(math.mul(i, w)))));
    }

    public static Layer sum(int size, int inputSize) {
        return new Layer(size, inputSize, (w, i) -> math.sum(math.mul(i, w)));
    }
}
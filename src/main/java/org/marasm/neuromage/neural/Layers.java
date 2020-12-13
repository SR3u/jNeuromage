package org.marasm.neuromage.neural;

import org.marasm.neuromage.math.VectorMath;

public class Layers {
    private final VectorMath math;

    public Layers(VectorMath math) {
        this.math = math;
    }

    public Layer sigmoid(int size, int inputSize) {
        return new Layer(math, size, inputSize, (n, in) -> 1 / (1 + Math.exp(math.sum(math.mul(in, n)))));
    }

    public Layer sum(int size, int inputSize) {
        return new Layer(math, size, inputSize, (n, in) -> math.sum(math.mul(in, n)));
    }
}

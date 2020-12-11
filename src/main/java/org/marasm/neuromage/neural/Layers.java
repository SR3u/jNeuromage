package org.marasm.neuromage.neural;

import org.marasm.neuromage.math.Vector;
import org.marasm.neuromage.math.VectorMath;

public class Layers<V extends Vector> {
    private final VectorMath<V> math;

    public Layers(VectorMath<V> math) {
        this.math = math;
    }

    public Layer<V> sigmoid(int size, int inputSize) {
        return new Layer<>(math, size, inputSize, (n, in) -> 1 / (1 + Math.exp(math.sum(math.mul(in, n)))));
    }

    public Layer<V> sum(int size, int inputSize) {
        return new Layer<>(math, size, inputSize, (n, in) -> math.sum(math.mul(in, n)));
    }
}

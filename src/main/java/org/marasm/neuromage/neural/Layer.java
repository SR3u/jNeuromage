package org.marasm.neuromage.neural;

import org.marasm.neuromage.math.Matrix;
import org.marasm.neuromage.math.Vector;
import org.marasm.neuromage.math.VectorMath;

import java.io.Serializable;

public class Layer implements Serializable {

    protected final static VectorMath math = VectorMath.get();
    private final int inputSize;
    private final int size;
    protected Matrix neurons;
    private final ActivationFunction activationFunc;

    protected Layer(int size, int inputSize, ActivationFunction activationFunc) {
        this.activationFunc = activationFunc;
        this.size = size;
        this.inputSize = inputSize;
        double[][] weights = new double[size][inputSize];
        for (int i = 0; i < size; i++) {
            double[] e = randomWeights(inputSize).calculate().data();
            weights[i] = e;
        }
        neurons = math.matrix(size, inputSize);
    }

    private Vector randomWeights(int inputSize) {
        double[] weights = new double[inputSize];
        for (int i = 0; i < weights.length; i++) {
            weights[i] = Math.random() * 100;
        }
        return math.vector(weights);
    }

    public VectorMath getMath() {
        return math;
    }

    public Vector output(Vector input) {
        Vector inp = math.convert(input);
        return math.vector(neurons.stream()
                .mapToDouble(n -> activationFunc.applyAsDouble(n, inp)).toArray());
    }

    public long getNeuronsCount() {
        return neurons.rows();
    }

    public LearningLayer learning() {
        return new LearningLayer(size, inputSize, activationFunc, neurons);
    }

}

package org.marasm.neuromage.neural;

import org.marasm.neuromage.math.Matrix;
import org.marasm.neuromage.math.Vector;
import org.marasm.neuromage.math.VectorMath;

import java.util.function.ToDoubleBiFunction;

public class Layer {

    protected final VectorMath math;
    private final int inputSize;
    private final int size;
    protected Matrix neurons;
    private final ToDoubleBiFunction<Vector, Vector> activationFunc;

    protected Layer(VectorMath math, int size, int inputSize, ToDoubleBiFunction<Vector, Vector> activationFunc) {
        this.math = math;
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
        return this.math;
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
        return new LearningLayer(math, size, inputSize, activationFunc);
    }
}

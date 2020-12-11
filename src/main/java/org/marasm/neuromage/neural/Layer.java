package org.marasm.neuromage.neural;

import org.marasm.neuromage.math.Matrix;
import org.marasm.neuromage.math.Vector;
import org.marasm.neuromage.math.VectorMath;

import java.util.function.ToDoubleBiFunction;

public class Layer<V extends Vector> {

    protected final VectorMath<V> math;
    private final int inputSize;
    private final int size;
    protected Matrix<V> neurons;
    private final ToDoubleBiFunction<V, V> activationFunc;

    protected Layer(VectorMath<V> math, int size, int inputSize, ToDoubleBiFunction<V, V> activationFunc) {
        this.math = math;
        this.activationFunc = activationFunc;
        this.size = size;
        this.inputSize = inputSize;
        double[][] weights = new double[size][inputSize];
        for (int i = 0; i < size; i++) {
            double[] e = randomWeights(inputSize).calculate().data();
            weights[i] = e;
        }
        neurons = math.matrix(weights, size, inputSize);
    }

    private V randomWeights(int inputSize) {
        double[] weights = new double[inputSize];
        for (int i = 0; i < weights.length; i++) {
            weights[i] = Math.random() * 100;
        }
        return math.vector(weights);
    }

    public VectorMath<V> getMath() {
        return this.math;
    }

    public V output(V input) {
        return math.vector(neurons.stream()
                .mapToDouble(n -> activationFunc.applyAsDouble(n, input)).toArray());
    }

    public long getNeuronsCount() {
        return neurons.rows();
    }

    public LearningLayer<V> learning() {
        return new LearningLayer<>(math, size, inputSize, activationFunc);
    }
}

package org.marasm.neuromage.neural;

import org.marasm.neuromage.math.Vector;
import org.marasm.neuromage.math.VectorMath;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToDoubleBiFunction;

public class Layer<V extends Vector> {

    protected final VectorMath<V> math;
    protected List<Neuron<V>> neurons = new ArrayList<>();
    private final ToDoubleBiFunction<Neuron<V>, V> activationFunc;

    protected Layer(VectorMath<V> math, int size, int inputSize, ToDoubleBiFunction<Neuron<V>, V> activationFunc) {
        this.math = math;
        this.activationFunc = activationFunc;
        for (int i = 0; i < size; i++) {
            Neuron<V> e = new Neuron<>(randomWeigths(inputSize));
            neurons.add(e);
        }
    }

    private V randomWeigths(int inputSize) {
        double[] weigts = new double[inputSize];
        for (int i = 0; i < weigts.length; i++) {
            weigts[i] = Math.random();
        }
        return math.vector(weigts);
    }

    public VectorMath<V> getMath() {
        return this.math;
    }

    public V output(V input) {
        return math.vector(neurons.stream()
                .mapToDouble(n -> activationFunc.applyAsDouble(n, input)).toArray());
    }

}

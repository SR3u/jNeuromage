package org.marasm.neuromage.neural;

import sr3u.jvec.Matrix;
import sr3u.jvec.Vector;
import sr3u.jvec.JMath;

import java.io.Serializable;

public class Layer implements Serializable {

    protected final static JMath math = JMath.get();
    private final int inputSize;
    private final int size;
    protected Matrix neurons;
    protected final ActivationFunction activationFunc;

    protected Layer(int size, int inputSize, ActivationFunction activationFunc) {
        this.activationFunc = activationFunc;
        this.size = size;
        this.inputSize = inputSize;
        double[] weights = new double[size * inputSize];
        for (int i = 0; i < size; i++) {
            double[] e = randomWeights(inputSize).calculate().data();
            System.arraycopy(e, 0, weights, i * inputSize, e.length);
        }
        neurons = math.matrix(new Matrix.Size(size, inputSize), weights);
    }

    private Vector randomWeights(int inputSize) {
        double[] weights = new double[inputSize];
        for (int i = 0; i < weights.length; i++) {
            weights[i] = Math.random() * 100;
        }
        return math.vector(weights);
    }

    public JMath getMath() {
        return math;
    }

    public Vector output(Vector input) {
        final Matrix mul = neurons.mul(input.asColumn());
        final Vector vec = math.vec(mul.calculate().data());
        return activationFunc.apply(vec);
    }

    public long getNeuronsCount() {
        return neurons.size().rows();
    }

    public LearningLayer learning() {
        return new LearningLayer(size, inputSize, activationFunc, neurons);
    }

    protected ActivationFunction getActivationFunc() {
        return activationFunc;
    }

    protected void setNeurons(Matrix neurons) {
        this.neurons = neurons;
    }

}

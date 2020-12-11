package org.marasm.neuromage.neural;

import org.marasm.neuromage.math.Matrix;
import org.marasm.neuromage.math.Vector;
import org.marasm.neuromage.math.VectorMath;

import java.util.function.ToDoubleBiFunction;

public class LearningLayer<V extends Vector> extends Layer<V> {
    private V output;
    private V delta;
    private V input;

    protected LearningLayer(VectorMath<V> math, int size, int inputSize,
                            ToDoubleBiFunction<V, V> activationFunc) {
        super(math, size, inputSize, activationFunc);
    }

    @Override
    public V output(V input) {
        this.input = input;
        output = super.output(input);
        return output;
    }

    //calculating error for learning using back propagation
    public void calculateError(V expectedOutput) {
        //delta = output * (1 - output) * (expectedOutput - output);
        delta = math.mul(output, math.mul(math.sub(1, output), math.sub(expectedOutput, output)));
    }

    public void calculateError(LearningLayer<V> nextLayer, int currentNeuronNumber) {
        //for calculating the error for hidden neurons use the values of errors of next layer
        //error_i = OUTi*(1 - OUTi)*(sum_children)
        //sum_children = summary (weights(i->j)*error_j), j - all neurons of the next layer
        V sum = nextLayer.getNeurons().transpose().mul(delta).sumRows();
        delta = math.mul(math.mul(output, math.sub(1, output)), sum);
    }

    private Matrix<V> getNeurons() {
        return neurons;
    }


    public V getDelta() {
        return delta;
    }

    public void learn(double rate) {
        //newW_i = oldW_i + LR*error*input_i
        V d = math.mul(math.mul(rate, input), delta);
        neurons = neurons.add(d);
    }

}

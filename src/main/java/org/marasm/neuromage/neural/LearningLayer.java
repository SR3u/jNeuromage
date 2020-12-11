package org.marasm.neuromage.neural;

import org.marasm.neuromage.math.Vector;
import org.marasm.neuromage.math.VectorMath;

import java.util.function.ToDoubleBiFunction;

public class LearningLayer<V extends Vector> extends Layer<V> {
    private V output;
    private V delta;

    protected LearningLayer(VectorMath<V> math, int size, int inputSize,
                            ToDoubleBiFunction<Neuron<V>, V> activationFunc) {
        super(math, size, inputSize, activationFunc);
    }

    //calculating error for learning using back propagation
    public void calculateError(V expectedOutput) {
        //delta = output * (1 - output) * (expectedOutput - output);
        delta = math.mul(output, math.mul(math.sub(1, output), math.sub(expectedOutput, output)))
    }

    public void calculateError(Layer<V> nextLayer, int currentNeuronNumber) {
        /*//for calculating the error for hidden neurons use the values of errors of next layer
        //error_i = OUTi*(1 - OUTi)*(sum_children)
        //sum_children = summary (weights(i->j)*error_j), j - all neurons of the next layer
        double sum = 0;
        //getting all neurons of the next layer
        List<Neuron> neuronList = nextLayer.getNeurons();
        for (Neuron neuron : neuronList) {
            //getting neuron
            //adding weight*delta to the summary
            sum += neuron.getDelta() * neuron.getWeights().get(currentNeuronNumber);
        }
        //calculating error
        delta = output * (1 - output) * sum;
        */
    }
}

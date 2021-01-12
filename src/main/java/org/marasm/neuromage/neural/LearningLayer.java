package org.marasm.neuromage.neural;

import sr3u.jvec.Matrix;
import sr3u.jvec.Vector;

public class LearningLayer extends Layer {
    private Vector output;
    private Vector delta;
    private Vector input;
    private Matrix nablaW;

    protected LearningLayer(int size, int inputSize,
                            ActivationFunction activationFunc, Matrix neurons) {
        super(size, inputSize, activationFunc);
        this.neurons = neurons;
        delta = math.vector(size, 0);
    }

    @Override
    public Vector output(Vector input) {
        this.input = input;
        output = super.output(input);
        return output;
    }

    // calculating error for learning using back propagation
    public void calculateError(Vector expectedOutput) {
        //delta = output * (1 - output) * (expectedOutput - output);
        /* delta = math.mul(output, math.mul(math.sub(1, output), math.sub(expectedOutput, output)));*/
        final Vector dSigmaOut = getActivationFunc().applyDerivative(output);
        delta = output.sub(expectedOutput).mul(dSigmaOut);
        nablaW = delta.asColumn().mul(input.asRow());
    }

    public void calculateError(LearningLayer nextLayer) {
        final Vector dSigmaOut = getActivationFunc().applyDerivative(output);
        final Matrix m = nextLayer.getNeurons().t().mul(nextLayer.getDelta().asColumn()).mul(dSigmaOut.asRow());
        delta = math.vector(m.calculate().data());
        nablaW = delta.asColumn().mul(input.asRow());
    }

    private Matrix getNeurons() {
        return neurons;
    }


    public Vector getDelta() {
        return delta;
    }

    public void learn(double rate) {
        //w-(eta*nw)
        Matrix n = neurons.sub(nablaW.mul(rate));
        setNeurons(n);
    }

}

package org.marasm.neuromage.neural;

import sr3u.jvec.Matrix;
import sr3u.jvec.Vector;

public class LearningLayer extends Layer {
    private Vector output;
    private Vector delta;
    private Vector input;
    private Matrix nablaW;
    private Vector dSigmaOut;

    protected LearningLayer(int size, int inputSize,
                            ActivationFunction activationFunc, Matrix neurons) {
        super(size, activationFunc);
        setInputSize(inputSize);
        this.neurons = neurons;
        delta = math.vector(size, 0);
    }

    @Override
    public Vector output(Vector input) {
        this.input = input;
        output = super.output(input);
        this.dSigmaOut = getActivationFunc().applyDerivative(output);
        return output;
    }

    // calculating error for learning using back propagation
    public void calculateError(Vector expectedOutput) {
        delta = output.sub(expectedOutput).mul(dSigmaOut);
        nablaW = delta.asColumn().mul(input.asRow());
    }

    public void calculateError(LearningLayer nextLayer) {
        final Matrix tmp = (nextLayer.neurons.t().mul(nextLayer.delta.asColumn()));
        final Matrix deltaV = tmp.mulScalar(dSigmaOut.asColumn());
        delta = math.vec(deltaV.calculate().data());
        nablaW = delta.asRow().mul(neurons);
    }

    public void learn(double rate) {
        //w-(eta*nw)
        Matrix n = neurons.sub(nablaW.mul(rate));
        if (!Double.isFinite(n.calculate().data()[0])) {
            throw new IllegalStateException("NaN detected");
        }
        setNeurons(n);
    }

}

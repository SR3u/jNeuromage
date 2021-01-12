package org.marasm.neuromage.neural;

import sr3u.jvec.Vector;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class NeuralNetwork implements Serializable {
    private final List<Layer> layers;
    private int epoch = 0;
    private transient IntBiConsumer handler;

    public void setHandler(IntBiConsumer handler) {
        this.handler = handler;
    }

    public NeuralNetwork(List<Layer> layers) {
        this.layers = layers;
    }

    public NeuralNetwork(Layer... layers) {
        this(Arrays.asList(layers));
    }

    public Vector output(Vector input) {
        for (Layer l : layers) {
            input = l.output(input);
        }
        return input;
    }

    public void learn(double rate, DataSet dataSet) {
        dataSet.forEach((i, t, in, out) -> {
            this.learnIteration(rate, in, out);
            handler.accept(epoch, i, t);
        });
    }

    public void learn(int epochs, double rate, DataSet dataSet) {
        for (int i = 0; i < epochs; i++) {
            epoch = i;
            learn(rate, dataSet);
        }
    }

    private void learnIteration(double rate, Vector in, Vector out) {
        output(in);
        this.calculateErrors(out);
        this.updateWeights(rate);
    }

    private void calculateErrors(Vector out) {
        int start = layers.size() - 1;
        for (int i = start; i >= 0; i--) {
            //get layer
            Layer currentLayer = layers.get(i);
            if (currentLayer instanceof LearningLayer) {
                LearningLayer currentLayer1 = (LearningLayer) currentLayer;
                if (i == start) {
                    //for output layer pass a expected result
                    currentLayer1.calculateError(out);
                } else {
                    Layer nextLayer = layers.get(i + 1);
                    //for another layers - pass a next layer for calculating the error
                    if (nextLayer instanceof LearningLayer) {
                        LearningLayer nextLayer1 = (LearningLayer) nextLayer;
                        currentLayer1.calculateError(nextLayer1);
                    }
                }
            }
        }
    }

    private void updateWeights(double learningRate) {
        //weights update from output layer to input
        for (int i = layers.size() - 1; i >= 0; i--) {
            //getting layer
            Layer currentLayer = layers.get(i);
            if (currentLayer instanceof LearningLayer) {
                LearningLayer currentLayer1 = (LearningLayer) currentLayer;
                currentLayer1.learn(learningRate);
            }
        }
    }


    public NeuralNetworkSummary summary() {
        return NeuralNetworkSummary.builder()
                .layers(layers.size())
                .neurons(layers.stream().mapToLong(Layer::getNeuronsCount).sum())
                .build();
    }
}

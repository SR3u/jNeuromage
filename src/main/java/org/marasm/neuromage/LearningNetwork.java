package org.marasm.neuromage;

import org.marasm.neuromage.neurons.LearningNeuron;
import org.marasm.neuromage.neurons.Neuron;
import org.marasm.neuromage.neurons.RegularNeuron;

import java.util.Date;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class LearningNetwork extends Network {

    private final double learningRate;
    private Consumer<LearningNetwork> showInfoHandler = null;

    public LearningNetwork(Topology topology) {
        super(topology);
        learningRate = topology.getLearningRate();
    }

    @Override
    public Layer createLayer(List<Neuron> neurons) {
        return super.createLayer(neurons.stream()
                .map(n -> {
                    if (n instanceof RegularNeuron) {
                        return new LearningNeuron(n);
                    } else {
                        return n;
                    }
                }).collect(Collectors.toList()));
    }

    public void learnBackPropagation(DataSet dataSet, int numberOfSteps) {
        Date start = new Date(0);
        callHandler();
        for (int i = 0; i < numberOfSteps; i++) {
            //going trough dataset
            for (int j = 0; j < dataSet.getSize(); j++) {
                //getting lists of input signal and expected results from dataset
                List<Double> inputs = dataSet.getEntries().get(j).getInputs();
                List<Double> expectedResults = dataSet.getEntries().get(j).getOutputs();
                //first - calculate result, using special method without scaling inputs,
                //because dataset is already scaled
                //second - calculate errors
                //third - correct weights
                this.output(inputs);
                this.calculateErrors(expectedResults);
                this.updateWeights(learningRate);
                int finalI = i;
                int finalJ = j;
                start = showInfo(start, () -> {
                    double total = numberOfSteps * dataSet.getSize();
                    double current = 1 + finalI * dataSet.getSize() + finalJ;
                    return "Learning: " + (100 * current / total) + "%";
                });
            }
            callHandler();
        }
    }

    private void callHandler() {
        if (showInfoHandler != null) {
            showInfoHandler.accept(this);
        }
    }

    private Date showInfo(Date start, Supplier<String> info) {
        Date now = new Date();
        long diff = (now.getTime() - start.getTime()) / 1000;
        if (diff < 30) {
            return start;
        }
        System.out.println(info.get());
        return now;
    }

    private void calculateErrors(List<Double> expectedResults) {
        //errors calculate from output layer to input
        //because errors in this layer depend on the errors of the next layer
        Layer previousLayer = null;
        for (int i = layers.size() - 1; i >= 0; i--) {
            //get layer
            Layer currentLayer = layers.get(i);
            for (int j = 0; j < currentLayer.getNeurons().size(); j++) {
                //for output layer pass a expected result
                if (previousLayer == null) {
                    currentLayer.getNeurons().get(j).calculateErrorOutput(expectedResults.get(j));
                } else {
                    //for another layers - pass a next layer for calculating the error
                    final int finalJ = j;
                    double sum = previousLayer.getNeurons().stream()
                            .mapToDouble(n -> n.getDelta() * n.getWeights().get(finalJ))
                            .sum();
                    currentLayer.getNeurons().get(j).calculateError(sum);
                }
            }
            previousLayer = currentLayer;
        }
    }

    private void updateWeights(double learningRate) {
        //weights update from output layer to input
        layers.stream()
                .flatMap(l -> l.getNeurons().stream())
                .forEach(n -> n.learnBackPropagation(learningRate));
    }

    public void setShowInfoHandler(Consumer<LearningNetwork> showInfoHandler) {
        this.showInfoHandler = showInfoHandler;
    }
}

package org.marasm.neuromage;

import lombok.Getter;
import org.marasm.neuromage.neurons.InputNeuron;
import org.marasm.neuromage.neurons.Neuron;
import org.marasm.neuromage.neurons.RegularNeuron;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Topology {
    private final int inputs;
    private final int outputs;
    private final List<List<Neuron>> layers;
    protected final double learningRate;

    public Topology(int inputs, int outputs, List<List<Neuron>> layers, double learningRate) {
        this.inputs = inputs;
        this.outputs = outputs;
        this.layers = layers;
        this.learningRate = learningRate;
    }

    public Topology(int inputs, int outputs, List<List<Neuron>> layers) {
        this(inputs, outputs, layers, 0.01);
    }

    public Topology(int inputs, int outputs) {
        this(inputs, outputs, constructLayers(inputs, outputs));
    }

    public Topology(int inputs, int outputs, int... layers) {
        this(inputs, outputs, constructLayers(inputs, outputs, layers));
    }

    public Topology(int inputs, int outputs, double learningRate, int... layers) {
        this(inputs, outputs, constructLayers(inputs, outputs, layers), learningRate);
    }

    private static List<List<Neuron>> constructLayers(int inputs, int outputs, int... layers) {
        List<List<Neuron>> result = new ArrayList<>(layers.length + 2);
        result.add(constructDefaultInputLayer(inputs));
        for (int i = 0; i < layers.length; i++) {
            int n = layers[i];
            int is = 0;
            if (i == 0) {
                is = inputs;
            } else {
                is = layers[i - 1];
            }
            result.add(constructLayer(is, n));
        }
        result.add(constructDefaultOutputLayer(result, outputs));
        return result;
    }

    public Topology(int inputs, int outputs, double learningRate) {
        this(inputs, outputs, constructLayers(inputs, outputs), learningRate);
    }

    private static List<List<Neuron>> constructLayers(int inputs, int outputs) {
        List<List<Neuron>> layers = new ArrayList<>();
        List<Neuron> inputLayer = constructDefaultInputLayer(inputs);
        layers.add(inputLayer);
        int i = inputLayer.size();
        int step = (int) ((inputs - outputs) / 1.5);
        if (step > 0) {
            while (i > outputs + step) {
                List<Neuron> layer = constructDefaultMiddleLayer(layers, step);
                layers.add(layer);
                System.out.println("added layer of size: " + layer.size());
                i -= Math.abs(step);
            }
        } else if (step < 0) {
            while (i < outputs + step) {
                List<Neuron> layer = constructDefaultMiddleLayer(layers, step);
                layers.add(layer);
                System.out.println("added layer of size: " + layer.size());
                i += Math.abs(step);
            }
        }
        layers.add(constructDefaultOutputLayer(layers, outputs));
        return layers;
    }

    private static List<Neuron> constructDefaultOutputLayer(List<List<Neuron>> layers, int outputs) {
        List<Neuron> lastLayer = layers.get(layers.size() - 1);
        int inputs = lastLayer.size();
        List<Neuron> neurons = new ArrayList<>();
        for (int i = 0; i < outputs; i++) {
            Neuron neuron = new RegularNeuron(inputs);
            neurons.add(neuron);
        }
        return neurons;
    }

    private static List<Neuron> constructDefaultInputLayer(int inputs) {
        List<Neuron> inputNeurons = new ArrayList<>();
        for (int i = 0; i < inputs; i++) {
            //input neuron always have 1 input
            Neuron neuron = new InputNeuron(inputs, i);
            inputNeurons.add(neuron);
        }
        return inputNeurons;
    }

    private static List<Neuron> constructDefaultMiddleLayer(List<List<Neuron>> layers, int step) {
        List<Neuron> lastLayer = layers.get(layers.size() - 1);
        int inputs = lastLayer.size();
        int neuronsCount = inputs - step;
        return constructLayer(inputs, neuronsCount);
    }

    private static List<Neuron> constructLayer(int inputs, int neuronsCount) {
        List<Neuron> neurons = new ArrayList<>();
        for (int i = 0; i < neuronsCount; i++) {
            Neuron neuron = new RegularNeuron(inputs);
            neurons.add(neuron);
        }
        return neurons;
    }

}

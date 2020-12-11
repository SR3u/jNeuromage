package org.marasm.neuromage;

import org.marasm.neuromage.neurons.Neuron;

import java.util.List;
import java.util.stream.Collectors;

public class Network {
    protected List<Layer> layers;

    public Network(Topology topology) {
        layers = topology.getLayers().stream()
                .map(this::createLayer)
                .collect(Collectors.toList());
    }

    protected Layer createLayer(List<Neuron> neurons) {
        return new Layer(neurons);
    }

    public List<Double> output(List<Double> input) {
        List<Double> current = input;
        for (Layer l : layers) {
            current = l.output(current);
        }
        return current;
    }

}

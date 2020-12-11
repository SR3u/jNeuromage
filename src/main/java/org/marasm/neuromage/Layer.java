package org.marasm.neuromage;

import lombok.Getter;
import org.marasm.neuromage.neurons.Neuron;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class Layer {
    private final List<Neuron> neurons;

    public Layer(List<Neuron> neurons) {
        this.neurons = neurons;
    }

    public List<Double> output(List<Double> inputs) {
        return neurons.stream()
                .map(n -> n.output(inputs))
                .collect(Collectors.toList());
    }

}

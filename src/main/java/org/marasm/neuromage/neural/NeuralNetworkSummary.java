package org.marasm.neuromage.neural;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class NeuralNetworkSummary {
    int layers;
    long neurons;
}

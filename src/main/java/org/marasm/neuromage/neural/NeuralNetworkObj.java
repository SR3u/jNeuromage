package org.marasm.neuromage.neural;

import java.io.Serializable;
import java.util.List;

public class NeuralNetworkObj implements Serializable {
    private final int epoch;
    private final List<LayerObj> layers;

    public NeuralNetworkObj(int epoch, List<LayerObj> layers) {
        this.epoch = epoch;
        this.layers =layers;
    }

    public List<LayerObj> getLayers() {
        return layers;
    }

    public int getEpoch() {
        return epoch;
    }
}

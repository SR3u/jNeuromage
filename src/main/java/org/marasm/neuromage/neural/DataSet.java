package org.marasm.neuromage.neural;

import lombok.Getter;
import sr3u.jvec.Vector;

import java.util.List;
import java.util.function.BiConsumer;

@Getter
public class DataSet {
    private List<Vector> inputs;
    private List<Vector> outputs;

    protected DataSet(List<Vector> inputs, List<Vector> outputs) {
        this.inputs = inputs;
        this.outputs = outputs;
    }

    public static DataSet of(List<Vector> inputs, List<Vector> outputs) {
        return new DataSet(inputs, outputs);
    }

    void forEach(IterationBiConsumer<Vector, Vector> consumer) {
        for (int i = 0; i < inputs.size(); i++) {
            consumer.accept(i, inputs.size(), inputs.get(i), outputs.get(i));
        }
    }

    void forEach(BiConsumer<Vector, Vector> consumer) {
        forEach((ignored1, ignored2, a, b) -> consumer.accept(a, b));
    }
}

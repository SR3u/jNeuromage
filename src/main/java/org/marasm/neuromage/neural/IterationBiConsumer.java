package org.marasm.neuromage.neural;

import java.io.Serializable;

public interface IterationBiConsumer<A, B> extends Serializable {
    void accept(int i, int total, A a, B b);
}

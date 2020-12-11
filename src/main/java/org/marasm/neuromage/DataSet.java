package org.marasm.neuromage;

import lombok.Value;

import java.util.List;

@Value
public class DataSet {

    List<Entry> entries;

    public int getSize() {
        return entries.size();
    }

    @Value
    public static class Entry {
        List<Double> inputs;
        List<Double> outputs;
    }
}

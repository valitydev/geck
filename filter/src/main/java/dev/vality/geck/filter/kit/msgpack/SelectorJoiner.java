package dev.vality.geck.filter.kit.msgpack;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class SelectorJoiner {
    Map.Entry<Selector, Selector.Context[]> join(List<Map.Entry<Selector, Selector.Context[]>> selectorEntries) {
        List<Selector.Context> contexts = new ArrayList<>(selectorEntries.size());
        List<Map.Entry<Selector, Selector.Context[]>> selectorTracks = selectorEntries.stream().map(e -> new AbstractMap.SimpleEntry<>(e.getKey(), e.getValue())).collect(Collectors.toList());
        for (Map.Entry<Selector, Selector.Context[]> selectorTrack: selectorTracks) {

        }
        return null;
    }
}

package ua.in.soul.university.lsbdecoder;

import lombok.Data;

import java.util.List;
import java.util.stream.Stream;

@Data
public class Triple {
    private String r, g, b;

    public static Triple fromList(List<String> stringList) {
        Triple triple = new Triple();
        triple.setR(stringList.get(0));
        triple.setG(stringList.get(1));
        triple.setB(stringList.get(2));

        return triple;
    }

    public Stream<String> components() {
        return Stream.of(r, g, b);
    }
}
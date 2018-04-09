package ua.in.soul.university.lsbdecoder;

import com.google.common.base.Joiner;
import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class LsbDecoder {

    private static final int SEGMENT_BIT_COUNT = 8;
    private static final int BINARY_RADIX = 2;


    public static String decode(String inputFileName) {
        Matrix tripleMatrix = new Matrix(readFile(inputFileName)    // 1. Read input file line by line
                .map(LsbDecoder::lineToBitValueStream)              // 2. Replace each component with its last bit
                .map(LsbDecoder::splitToTriples)
                .collect(toList()));


        List<String> lastBitValues = bitListFromMatrix(tripleMatrix.inverse()).collect(toList());

        return IntStream.range(0, pageCount(lastBitValues, SEGMENT_BIT_COUNT))      // 4. Determine segment count
                .mapToObj(i -> pageFromList(lastBitValues, i, SEGMENT_BIT_COUNT))   // 5. Get each segment source values
                .map(LsbDecoder::binarySegmentFromList)                             // 6. Convert to segment
                .mapToInt(segmentStr -> Integer.valueOf(segmentStr, BINARY_RADIX))  // 7. Convert segment to value
                .mapToObj(LsbDecoder::symbolWithAsciiCode)                          // 8. Get symbol by ASCII code
                .collect(joining());                                                // 9. Collect them as single string
    }

    @SneakyThrows
    private static Stream<String> readFile(String inputFileName) {
        return Files.lines(Paths.get(Objects.requireNonNull(LsbDecoder.class.getClassLoader().getResource(inputFileName)).toURI()));
    }

    private static String extractLastBit(String string) {
        return string.substring(string.length() - 1);
    }

    private static List<String> lineToBitValueStream(String lsbLine) {
        return Arrays.stream(lsbLine.split(" "))
                .map(Integer::valueOf)
                .map(Integer::toBinaryString)
                .map(LsbDecoder::extractLastBit)
                .collect(toList());
    }

    private static List<Triple> splitToTriples(List<String> data) {
        return IntStream.range(0, pageCount(data, 3))
                .mapToObj(i -> pageFromList(data, i, 3))
                .map(Triple::fromList)
                .collect(toList());
    }

    private static Stream<String> bitListFromMatrix(Triple[][] matrix) {
        return Arrays.stream(matrix)            // 1. Un-box outer array
                .flatMap(Arrays::stream)        // 2. Un-box inner arrays
                .flatMap(Triple::components);   // 3. Un-box triples
    }

    private static List<String> pageFromList(List<String> source, int pageNumber, int pageSize) {
        return source.subList(pageSize * pageNumber, Math.min(pageSize * (pageNumber + 1), source.size()));
    }

    private static int pageCount(List list, int pageSize) {
        int listSize = list.size();
        return (listSize + pageSize - 1) / pageSize;
    }

    private static String binarySegmentFromList(List<String> bits) {
        return Joiner.on("").join(bits);
    }

    private static String symbolWithAsciiCode(int code) {
        return String.valueOf((char) code);
    }
}

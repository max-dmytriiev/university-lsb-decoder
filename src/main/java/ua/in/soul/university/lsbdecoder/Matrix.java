package ua.in.soul.university.lsbdecoder;

import java.util.List;

public class Matrix {
    private int w, h;
    private List<List<Triple>> elements;

    public Matrix(List<List<Triple>> elements) {
        this.elements = elements;
        this.h = elements.size();
        this.w = elements.stream().mapToInt(List::size).max().orElse(0);
    }

    public Triple get(int row, int col) {
        return elements.get(row).get(col);
    }

    public Triple[][] toArray() {
        Triple[][] result = new Triple[h][w];

        for (int i = 0; i < h; i++) {
            List<Triple> element = elements.get(i);
            for (int j = 0; j < element.size(); j++) {
                result[i][j] = element.get(j);
            }
        }

        return result;
    }

    public Triple[][] inverse() {
        Triple[][] result = new Triple[w][h];

        for (int i = 0; i < h; i++) {
            List<Triple> element = elements.get(i);
            for (int j = 0; j < element.size(); j++) {
                result[j][i] = element.get(j);
            }
        }

        return result;
    }
}

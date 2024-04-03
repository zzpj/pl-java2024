package com.example.var;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Student {
    private final Integer[] marks;

    public Student(Integer[] marks) {
        this.marks = marks;
    }

    public Integer getNumberOfMarks() {
        return marks.length;
    }


    public Integer getTotalSumOfMarks() {
        var sum = 0;
        for (var mark : marks) {
            sum += mark;
        }
        return sum;
    }

    public BigDecimal getAverageOfMarks() {
        var sum = getTotalSumOfMarks();
        return new BigDecimal(sum)
                .divide(new BigDecimal(marks.length), 3, RoundingMode.UP);
    }

    public Integer getMaximumMark() {
        var max = Integer.MIN_VALUE;
        for (var mark : marks) {
            if (mark > max) {
                max = mark;
            }
        }
        return max;
    }

    public Integer getMinimumMark() {
        var min = Integer.MAX_VALUE;
        for (var mark : marks) {
            if (mark < min) {
                min = mark;
            }
        }
        return min;
    }

    public List<Integer> getDistinctMarks() {
        var distinctMarks = new ArrayList<Integer>();

        for (var mark : marks) {
            if (!distinctMarks.contains(mark)) {
                distinctMarks.add(mark);
            }
        }
        return distinctMarks;
    }
}

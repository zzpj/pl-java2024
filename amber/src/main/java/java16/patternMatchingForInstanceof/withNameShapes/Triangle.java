package java16.patternMatchingForInstanceof.withNameShapes;

import java16.patternMatchingForInstanceof.Shape;

public class Triangle extends Shape implements Named{
    @Override
    public String getName() {
        return "triangle";
    }
}

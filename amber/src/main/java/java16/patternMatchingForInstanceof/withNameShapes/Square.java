package java16.patternMatchingForInstanceof.withNameShapes;

import java16.patternMatchingForInstanceof.Shape;

public class Square extends Shape implements Named {
    @Override
    public String getName() {
        return "square";
    }
}

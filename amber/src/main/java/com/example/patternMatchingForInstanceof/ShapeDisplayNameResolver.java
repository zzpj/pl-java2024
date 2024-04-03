package com.example.patternMatchingForInstanceof;

import com.example.patternMatchingForInstanceof.withDisplayName.DisplayNamed;
import com.example.patternMatchingForInstanceof.withNameShapes.Named;

public class ShapeDisplayNameResolver {

    public String resolveShape(Shape shape) {
        switch (shape) {
            case DisplayNamed displayNamed -> {
                return displayNamed.getDisplayName();
            }
            case Named named -> {
                return String.format("This is %s name!", named.getName());
            }
            default -> throw new IllegalStateException("Unexpected value: " + shape);
        }
    }
}

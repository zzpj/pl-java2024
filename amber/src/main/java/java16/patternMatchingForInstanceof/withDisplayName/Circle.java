package java16.patternMatchingForInstanceof.withDisplayName;

import java16.patternMatchingForInstanceof.Shape;

public class Circle extends Shape implements DisplayNamed {
    @Override
    public String getDisplayName() {
        return "This is circle name!";
    }
}

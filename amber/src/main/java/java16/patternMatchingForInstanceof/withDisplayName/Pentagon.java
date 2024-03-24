package java16.patternMatchingForInstanceof.withDisplayName;

import java16.patternMatchingForInstanceof.Shape;

public class Pentagon extends Shape implements DisplayNamed {
    @Override
    public String getDisplayName() {
        return "This is pentagon name!";
    }
}

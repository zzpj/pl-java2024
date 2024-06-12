package com.example.record;

record Car(String make, int capacity) {
    Car {
        if (capacity < 0) {
            throw new IllegalArgumentException(String.format("%d capacity less than 0", capacity));
        }
    }
}

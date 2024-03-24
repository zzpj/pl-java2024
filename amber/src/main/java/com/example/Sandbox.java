package com.example;

public class Sandbox {
    public static void main(String[] args) {
        //Person person = new Person(-1L, "Zbyszko");
        //Person person = new Person(-1L);
        Person person = new Person(1L);
        System.out.println(person);
    }

    record Person(Long id, String username)  {

        // custom constructor
        public Person(Long id) {
            this(id, null);
        }

        // compact canonical
        Person {
            if (id < 0) {
                throw new IllegalArgumentException(String.format("%d is less than 0", id));
            }
        }
    }
}
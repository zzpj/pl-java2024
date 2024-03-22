package com.example;

import java.util.*;

public class MyStack<T> {

    private Deque<T> stack;

    public MyStack() {
        this.stack = new ArrayDeque<>();
    }

    public void push(T t) {
        stack.push(t);
    }

    public T pop() {
        return stack.pop();
    }

    public T top() {
        return stack.peek();
    }

    public int size() {
        return stack.size();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

}

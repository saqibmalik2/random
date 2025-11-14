package com.saqib.puzzles;

import java.io.ObjectStreamException;
import java.io.Serializable;

public class LazySingletonRobust implements Serializable {

    private static final long serialVersionUID = 1L;

    // defensive flag — set when constructor runs for the first time
    private static volatile boolean instanceCreated = false;

    private LazySingletonRobust() {
        // Defensive check: if an instance has already been created, fail fast.
        if (instanceCreated) {
            throw new IllegalStateException("Singleton instance already created. Reflection is not allowed.");
        }
        instanceCreated = true;
        System.out.println("RobustLazySingleton constructor called.");
    }

    private static class Holder {
        private static final LazySingletonRobust INSTANCE = new LazySingletonRobust();
    }

    public static LazySingletonRobust getInstance() {
        return Holder.INSTANCE;
    }

    public void doWork() {
        System.out.println("Working: " + this);
    }

    /**
     * Ensure that deserialization returns the singleton instance instead of creating a new one.
     * This method is invoked by the serialization runtime.
     */
    private Object readResolve() throws ObjectStreamException {
        return getInstance();
    }
}


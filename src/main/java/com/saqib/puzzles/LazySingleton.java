package com.saqib.puzzles;


/**
 * 
 * Class loader guarantees singleton.
 * JVM guarantees the Singleton cannot be exposed before fully initialized (i.e. thread safety).
 * 
 * Lazy loaded upon invocation of the getInstance() method.
 * 
 * Problems: vulnerable to serialization and reflection attacks.
 * 
 * Mitigation: use a private static volatile variable as a flag to mark uniqueness (see RobustLazySingleton)
 * 
 */
public class LazySingleton {

    // Private constructor prevents instantiation
    private LazySingleton() {
        System.out.println("Singleton instance created.");
    }

    // Static nested class holds the single instance
    private static class Holder {
        private static final LazySingleton INSTANCE = new LazySingleton();
    }

    // Accessor for the singleton instance
    public static LazySingleton getInstance() {
        return Holder.INSTANCE;
    }

    // Example method
    public void doWork() {
        System.out.println("Doing some work...");
    }
}


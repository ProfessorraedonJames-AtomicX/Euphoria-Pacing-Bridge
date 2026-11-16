package com.atomicx.euphoriapacingbridge.api;

/**
 * Shared state and telemetry communication between Euphoria Pacing (Governor)
 * and Euphoria Pacing Extended (Engine).
 */
public final class BridgeState {
    private BridgeState() {}

    private static volatile boolean pacingLoaded;
    private static volatile boolean extendedLoaded;
    private static volatile boolean sodiumLoaded;

    // Shared governor quality level (0 = normal, 1-6 = progressive reduction)
    private static volatile int governorLevel = 0;

    // Real-time micro-stutter tracking (nanoseconds)
    private static volatile long worstFrameNanos = 0L;
    private static volatile int onePercentLowFps = 60;
    private static volatile int averageFps = 60;

    // Engine dynamic distance scaling factor (1.0 = 100%, 0.7 = 70%)
    private static volatile double engineDistanceScale = 1.0;

    public static boolean isPacingLoaded() {
        return pacingLoaded;
    }

    public static void setPacingLoaded(boolean loaded) {
        pacingLoaded = loaded;
    }

    public static boolean isExtendedLoaded() {
        return extendedLoaded;
    }

    public static void setExtendedLoaded(boolean loaded) {
        extendedLoaded = loaded;
    }

    public static boolean isSodiumLoaded() {
        return sodiumLoaded;
    }

    public static void setSodiumLoaded(boolean loaded) {
        sodiumLoaded = loaded;
    }

    public static int getGovernorLevel() {
        return governorLevel;
    }

    public static void setGovernorLevel(int level) {
        governorLevel = level;
        // Dynamically compute engine distance scaling without reloading chunk meshes
        engineDistanceScale = switch (level) {
            case 0 -> 1.0;
            case 1 -> 0.90;
            case 2 -> 0.80;
            case 3 -> 0.70;
            case 4 -> 0.60;
            case 5 -> 0.50;
            default -> 0.40;
        };
    }

    public static double getEngineDistanceScale() {
        return engineDistanceScale;
    }

    public static void updateMetrics(int avgFps, int lowFps, long worstNanos) {
        averageFps = avgFps;
        onePercentLowFps = lowFps;
        worstFrameNanos = worstNanos;
    }

    public static int getAverageFps() {
        return averageFps;
    }

    public static int getOnePercentLowFps() {
        return onePercentLowFps;
    }

    public static long getWorstFrameNanos() {
        return worstFrameNanos;
    }
}

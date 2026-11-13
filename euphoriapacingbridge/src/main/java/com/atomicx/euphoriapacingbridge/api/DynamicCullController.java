package com.atomicx.euphoriapacingbridge.api;

/**
 * Controller for zero-reload dynamic chunk section distance culling.
 *
 * When the governor signals low performance, instead of modifying vanilla's
 * renderDistance option (which clears all chunk meshes and freezes the screen),
 * this controller dynamically filters distant section draw calls directly
 * during chunk draw-list generation. Chunks remain in memory and restore instantly.
 */
public final class DynamicCullController {
    private DynamicCullController() {}

    /**
     * Determines whether a chunk section should be culled from the current frame's draw list.
     *
     * @param distSquared Squared distance from camera to section center
     * @param baseRenderDistanceChunks Player's configured render distance in chunks
     * @return true if the section is beyond the dynamic budget and should be culled
     */
    public static boolean shouldCullSection(double distSquared, int baseRenderDistanceChunks) {
        double scale = BridgeState.getEngineDistanceScale();
        if (scale >= 1.0) return false;

        double maxBlockDist = (baseRenderDistanceChunks * 16.0) * scale;
        return distSquared > (maxBlockDist * maxBlockDist);
    }
}

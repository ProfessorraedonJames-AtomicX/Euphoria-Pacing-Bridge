# Euphoria Pacing Bridge - by AtomicX
Forge 1.21.11 (61.2.4). Original code. JDK 21.

    ./gradlew runClient
    ./gradlew build        # jar in build/libs

## Purpose
The **Euphoria Pacing Bridge** connects **Euphoria Pacing** (the dynamic governor and culling system) with **Euphoria Pacing Extended (EPE)** (the chunk draw engine and Early-Z pipeline):

- **Real-Time Frame-Pacing Feedback**: Connects EPE's microsecond frame timing engine directly into the Governor so quality adapts to real micro-stutters.
- **Zero-Reload Dynamic View Distance**: When quality drops, instead of reloading all chunk meshes with vanilla's `LevelRenderer.allChanged()`, the Bridge signals EPE to cull distant chunk sections at the draw-list level without any reload freeze.
- **Conflict De-duplication**: Coordinates mixin hooks and options to prevent duplicate timers, conflicting option sliders, or redundant uploads when both mods are installed.
- **Sodium / Embeddium Detection**: Dynamically coordinates hooks when modern OpenGL renderers are present.

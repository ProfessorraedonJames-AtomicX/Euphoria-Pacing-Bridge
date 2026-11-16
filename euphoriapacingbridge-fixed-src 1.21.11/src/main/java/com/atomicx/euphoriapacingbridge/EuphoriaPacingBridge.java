package com.atomicx.euphoriapacingbridge;

import com.atomicx.euphoriapacingbridge.api.BridgeState;
import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.slf4j.Logger;

/**
 * Euphoria Pacing Bridge:
 * Synchronizes Euphoria Pacing (Governor) and Euphoria Pacing Extended (Engine).
 *
 * Provides real-time frame pacing telemetry, eliminates duplicate mixin injections,
 * and enables zero-reload dynamic view distance scaling at the engine draw level.
 */
@Mod(EuphoriaPacingBridge.MODID)
public final class EuphoriaPacingBridge {
    public static final String MODID = "euphoriapacingbridge";
    public static final Logger LOGGER = LogUtils.getLogger();

    public EuphoriaPacingBridge(FMLJavaModLoadingContext context) {
        if (FMLEnvironment.dist == Dist.CLIENT) {
            var modBusGroup = context.getModBusGroup();
            FMLClientSetupEvent.getBus(modBusGroup).addListener(this::clientSetup);
            LOGGER.info("Euphoria Pacing Bridge loaded.");
        }
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        ModList modList = ModList.get();
        boolean hasPacing = modList.isLoaded("euphoriapacing");
        boolean hasExtended = modList.isLoaded("euphoria_pacing_extended");
        boolean hasSodium = modList.isLoaded("sodium") || modList.isLoaded("embeddium") || modList.isLoaded("rubidium");

        BridgeState.setPacingLoaded(hasPacing);
        BridgeState.setExtendedLoaded(hasExtended);
        BridgeState.setSodiumLoaded(hasSodium);

        LOGGER.info("Euphoria Pacing Bridge Status: [Euphoria Pacing: {}, EPE Engine: {}, Sodium/Embeddium: {}]",
                hasPacing ? "Active" : "Not Found",
                hasExtended ? "Active" : "Not Found",
                hasSodium ? "Detected" : "Not Present");
    }
}

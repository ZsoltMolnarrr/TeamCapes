package net.team_capes.fabric;

import net.fabricmc.api.ModInitializer;

import net.team_capes.TeamCapesMod;

public final class TeamCapesModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        TeamCapesMod.init();
    }
}

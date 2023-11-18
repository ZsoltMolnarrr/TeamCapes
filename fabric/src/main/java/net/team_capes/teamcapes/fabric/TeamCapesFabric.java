package net.team_capes.teamcapes.fabric;

import net.team_capes.TeamCapesMod;
import net.fabricmc.api.ModInitializer;

public class TeamCapesFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        TeamCapesMod.init();
    }
}
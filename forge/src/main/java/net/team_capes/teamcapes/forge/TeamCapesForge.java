package net.team_capes.teamcapes.forge;

import net.team_capes.TeamCapesMod;
import net.minecraftforge.fml.common.Mod;

@Mod(TeamCapesMod.NAMESPACE)
public class TeamCapesForge {
    public TeamCapesForge() {
		// Submit our event bus to let architectury register our content on the right time
        // EventBuses.registerModEventBus(TeamCapesMod.NAMESPACE, FMLJavaModLoadingContext.get().getModEventBus());
        TeamCapesMod.init();
    }
}
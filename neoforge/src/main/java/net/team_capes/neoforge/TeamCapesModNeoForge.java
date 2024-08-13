package net.team_capes.neoforge;

import net.neoforged.fml.common.Mod;

import net.team_capes.TeamCapesMod;

@Mod(TeamCapesMod.NAMESPACE)
public final class TeamCapesModNeoForge {
    public TeamCapesModNeoForge() {
        // Run our common setup.
        TeamCapesMod.init();
    }
}

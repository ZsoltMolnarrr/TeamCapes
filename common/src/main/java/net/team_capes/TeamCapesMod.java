package net.team_capes;

import net.team_capes.config.ClientConfig;
import net.tiny_config.ConfigManager;

public final class TeamCapesMod {
    public static final String NAMESPACE = "team_capes";

    public static final ConfigManager<ClientConfig> clientConfig = new ConfigManager<>
            ("client", new ClientConfig())
            .builder()
            .setDirectory(NAMESPACE)
            .sanitize(true)
            .build();
    private static boolean configLoaded = false;

    /**
     * Loads the config on first access. Mixins into other mods' static initializers
     * can run before our entrypoint does, so they must go through here.
     */
    public static synchronized ClientConfig config() {
        if (!configLoaded) {
            clientConfig.refresh();
            configLoaded = true;
        }
        return clientConfig.value;
    }

    public static void init() {
        config();
    }
}

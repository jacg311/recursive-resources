package nl.enjarai.recursiveresources;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.util.Identifier;

public class RecursiveResources implements ClientModInitializer {
    public static final String MOD_ID = "recursiveresources";

    @Override
    public void onInitializeClient() {

    }

    public static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }
}

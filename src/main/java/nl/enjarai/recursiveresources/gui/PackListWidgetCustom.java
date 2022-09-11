package nl.enjarai.recursiveresources.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.pack.PackListWidget;
import nl.enjarai.recursiveresources.mixin.PackListWidgetAccessor;

public class PackListWidgetCustom extends PackListWidget {
    public PackListWidgetCustom(PackListWidget original, int width, int height, int left) {
        super(MinecraftClient.getInstance(), width, height, ((PackListWidgetAccessor) original).recursiveresources$getTitle());
        replaceEntries(original.children());
        setLeftPos(left);
    }
}

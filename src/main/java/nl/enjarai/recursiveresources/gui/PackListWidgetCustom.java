package nl.enjarai.recursiveresources.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.pack.PackListWidget;

// we might not need this, but i wont worry about it for now
public class PackListWidgetCustom extends PackListWidget {
    public PackListWidgetCustom(PackListWidget original, int width, int height, int left) {
        super(MinecraftClient.getInstance(), width, height, original.title);
        replaceEntries(original.children());
        setLeftPos(left);
    }
}

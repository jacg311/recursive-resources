package nl.enjarai.recursiveresources.mixin;

import net.minecraft.client.gui.screen.pack.PackListWidget;
import net.minecraft.client.gui.screen.pack.ResourcePackOrganizer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PackListWidget.ResourcePackEntry.class)
public interface ResourcePackEntryAccessor {
    @Accessor("pack")
    ResourcePackOrganizer.Pack recursiveresources$getPack();
}

package nl.enjarai.recursiveresources.mixin;

import net.minecraft.client.gui.screen.pack.PackListWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PackListWidget.class)
public interface PackListWidgetAccessor {
    @Accessor("title")
    Text recursiveresources$getTitle();
}

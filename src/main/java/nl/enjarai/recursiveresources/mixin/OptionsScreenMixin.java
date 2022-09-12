package nl.enjarai.recursiveresources.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.text.Text;
import nl.enjarai.recursiveresources.gui.CustomResourcePackScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(OptionsScreen.class)
public abstract class OptionsScreenMixin {
    @Shadow
    protected abstract void refreshResourcePacks(ResourcePackManager resourcePackManager);

    /**
     * @author recursiveresources
     * @reason Replace the resource packs screen with a custom one.
     */
    @Overwrite
    private void method_19824(ButtonWidget button) {
        var client = MinecraftClient.getInstance();
        client.setScreen(new CustomResourcePackScreen(
                (OptionsScreen) (Object) this, client.getResourcePackManager(),
                this::refreshResourcePacks, client.getResourcePackDir(),
                Text.translatable("resourcePack.title")
        ));
    }
}

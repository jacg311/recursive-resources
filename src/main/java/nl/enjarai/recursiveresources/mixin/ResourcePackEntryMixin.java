package nl.enjarai.recursiveresources.mixin;

import net.minecraft.client.gui.screen.pack.PackListWidget;
import net.minecraft.client.gui.screen.pack.ResourcePackOrganizer;
import net.minecraft.client.util.math.MatrixStack;
import nl.enjarai.recursiveresources.packs.FolderPack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PackListWidget.ResourcePackEntry.class)
public abstract class ResourcePackEntryMixin {
    @Shadow
    @Final
    public ResourcePackOrganizer.Pack pack;

    @Inject(
            method = "render(Lnet/minecraft/client/util/math/MatrixStack;IIIIIIIZF)V",
            at = @At("HEAD")
    )
    private void recursiveresources$onRender(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta, CallbackInfo ci) {
        if (pack instanceof FolderPack folderPack) {
            folderPack.setHovered(hovered);
        }
    }
}

package nl.enjarai.recursiveresources.mixin;

import net.minecraft.client.resource.Format4ResourcePack;
import net.minecraft.resource.ResourcePack;
import nl.enjarai.recursiveresources.repository.ResourcePackUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Format4ResourcePack.class)
public abstract class ExposeFormat4ResourcePack implements ResourcePackUtils.IExposedResourcePackDelegate {
    @Final
    @Shadow
    private ResourcePack parent;

    @Override
    public ResourcePack getDelegate() {
        return parent;
    }
}

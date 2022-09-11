package nl.enjarai.recursiveresources.mixin;

import net.minecraft.resource.AbstractFileResourcePack;
import nl.enjarai.recursiveresources.repository.ResourcePackUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.io.File;

@Mixin(AbstractFileResourcePack.class)
public abstract class ExposeAbstractFileResourcePack implements ResourcePackUtils.IExposedResourcePack {
    @Final
    @Shadow
    protected File base;

    @Override
    public File getFileOrFolder() {
        return base;
    }
}

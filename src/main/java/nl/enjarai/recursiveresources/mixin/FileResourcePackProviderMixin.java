package nl.enjarai.recursiveresources.mixin;

import net.minecraft.resource.FileResourcePackProvider;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FileResourcePackProvider.class)
public class FileResourcePackProviderMixin {
    @Redirect(method = "getFactory", at = @At(value = "INVOKE", target = "Lorg/slf4j/Logger;info(Ljava/lang/String;Ljava/lang/Object;)V"))
    private static void recursiveresources$silenceLoggerInfo(Logger instance, String s, Object o) {
        // Dont Log the warning. This is expected with this mod.
    }
}

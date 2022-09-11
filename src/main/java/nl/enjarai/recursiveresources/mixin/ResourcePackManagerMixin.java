package nl.enjarai.recursiveresources.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.resource.ResourcePackProvider;
import nl.enjarai.recursiveresources.repository.NestedFolderPackFinder;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mixin(ResourcePackManager.class)
public abstract class ResourcePackManagerMixin {
    @Shadow
    @Final
    @Mutable
    private Set<ResourcePackProvider> providers;

    @Inject(
            method = "<init>(Lnet/minecraft/resource/ResourcePackProfile$Factory;[Lnet/minecraft/resource/ResourcePackProvider;)V",
            at = @At("RETURN")
    )
    private void recursiveresources$onInit(CallbackInfo ci) {
        // Only add our own provider if this is the manager of client
        // resource packs, we wouldn't want to mess with datapacks
        if (providers.stream().anyMatch(provider -> provider == MinecraftClient.getInstance().getResourcePackProvider())) {
            var client = MinecraftClient.getInstance();

            providers = Stream.concat(providers.stream(), Stream.of(new NestedFolderPackFinder(client.getResourcePackDir()))).collect(Collectors.toSet());
        }
    }
}

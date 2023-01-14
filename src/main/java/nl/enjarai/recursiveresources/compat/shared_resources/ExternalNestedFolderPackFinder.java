package nl.enjarai.recursiveresources.compat.shared_resources;

import net.minecraft.resource.ResourcePackProfile;
import nl.enjarai.recursiveresources.repository.NestedFolderPackFinder;

import java.nio.file.Path;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ExternalNestedFolderPackFinder extends NestedFolderPackFinder {
    protected final Supplier<Path> pathSupplier;

    public ExternalNestedFolderPackFinder(Supplier<Path> pathSupplier) {
        super(pathSupplier.get());
        this.pathSupplier = pathSupplier;
    }

    @Override
    public void register(Consumer<ResourcePackProfile> profileAdder) {
        Path path = pathSupplier.get();
        if (path == null) return;
        this.root = path;

        super.register(profileAdder);
    }
}

package nl.enjarai.recursiveresources.repository;

import com.google.common.io.MoreFiles;
import net.minecraft.resource.*;
import net.minecraft.resource.ResourcePackProfile.InsertionPosition;
import net.minecraft.text.Text;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class NestedFolderPackFinder implements ResourcePackProvider {
    protected Path root;

    public NestedFolderPackFinder(Path root) {
        this.root = root;
    }

    @Override
    public void register(Consumer<ResourcePackProfile> profileAdder) {
        try(Stream<Path> folders = Files.list(root)) {
            for (Path folder : folders.filter(ResourcePackUtils::isFolderButNotFolderBasedPack).toList()) {
                processFolder(folder, profileAdder);
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void processFolder(Path folder, Consumer<ResourcePackProfile> profileAdder) {
        if (ResourcePackUtils.isFolderBasedPack(folder)) {
            addPack(folder, profileAdder);
            return;
        }

        try (Stream<Path> elements = Files.list(folder)){
            for (Path element : elements.toList()) {
                if (!Files.isDirectory(element) && element.toString().endsWith(".zip")) {
                    addPack(element, profileAdder);
                    return;
                }

                if (Files.isDirectory(element)) {
                    processFolder(element, profileAdder);
                }
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addPack(Path fileOrFolder, Consumer<ResourcePackProfile> profileAdder) {
//        String displayName = StringUtils.removeStart(fileOrFolder.getAbsolutePath().substring(rootLength).replace('\\', '/'), "/");
        String displayName = MoreFiles.getNameWithoutExtension(fileOrFolder);
        String name = "file/" + displayName;
        ResourcePackProfile info;

        if (Files.isDirectory(fileOrFolder)) {
            info = ResourcePackProfile.create(
                    name, Text.literal(displayName), false,
                    (packName) -> new DirectoryResourcePack(packName, fileOrFolder, true),
                    ResourceType.CLIENT_RESOURCES, InsertionPosition.TOP, ResourcePackSource.NONE
            );
        } else {
            info = ResourcePackProfile.create(
                    name, Text.literal(displayName), false,
                    (packName) -> new ZipResourcePack(packName, fileOrFolder.toFile(), true),
                    ResourceType.CLIENT_RESOURCES, InsertionPosition.TOP, ResourcePackSource.NONE
            );
        }

        if (info != null) {
            profileAdder.accept(info);
        }
    }
}

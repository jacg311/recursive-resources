package nl.enjarai.recursiveresources.repository;

import net.minecraft.resource.DirectoryResourcePack;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ZipResourcePack;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Files;
import java.nio.file.Path;

public class ResourcePackUtils {
    public static boolean isFolderBasedPack(Path folder) {
        return Files.exists(folder.resolve("pack.mcmeta"));
    }

    public static boolean isFolderButNotFolderBasedPack(Path folder) {
        return Files.isDirectory(folder) && !isFolderBasedPack(folder);
    }

    @Nullable
    public static Path determinePackFolder(ResourcePack pack) {
        if (pack instanceof DirectoryResourcePack directoryResourcePack) {
            return directoryResourcePack.root;
        } else if (pack instanceof ZipResourcePack zipResourcePack) {
            return zipResourcePack.backingZipFile.toPath();
        } else {
            return null;
        }
    }

    public static boolean isChildOfFolder(Path folder, ResourcePack pack) {
        Path packFolder = determinePackFolder(pack);
        return packFolder != null && packFolder.toAbsolutePath().startsWith(folder.toAbsolutePath());
    }
}

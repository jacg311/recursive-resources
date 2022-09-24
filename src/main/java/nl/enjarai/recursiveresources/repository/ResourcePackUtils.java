package nl.enjarai.recursiveresources.repository;

import net.minecraft.client.resource.Format3ResourcePack;
import net.minecraft.client.resource.Format4ResourcePack;
import net.minecraft.resource.AbstractFileResourcePack;
import net.minecraft.resource.DirectoryResourcePack;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ZipResourcePack;

import java.io.File;

public class ResourcePackUtils {
    private static final File[] EMPTY_FILE_ARRAY = new File[0];

    public static File[] wrap(File[] filesOrNull) {
        return filesOrNull == null ? EMPTY_FILE_ARRAY : filesOrNull;
    }

    public static boolean isFolderBasedPack(File folder) {
        return new File(folder, "pack.mcmeta").exists();
    }

    public static boolean isFolderButNotFolderBasedPack(File folder) {
        return folder.isDirectory() && !isFolderBasedPack(folder);
    }

    public static File determinePackFolder(ResourcePack pack) {
        Class<? extends ResourcePack> cls = pack.getClass();

        if (cls == ZipResourcePack.class || cls == DirectoryResourcePack.class) {
            return ((AbstractFileResourcePack) pack).base;
        } else if (pack instanceof Format3ResourcePack compatPack) {
            return determinePackFolder(compatPack.parent);
        } else if (pack instanceof Format4ResourcePack compatPack) {
            return determinePackFolder(compatPack.parent);
        } else {
            return null;
        }
    }

    public static boolean isChildOfFolder(File folder, ResourcePack pack) {
        File packFolder = determinePackFolder(pack);
        return packFolder != null && packFolder.getAbsolutePath().startsWith(folder.getAbsolutePath());
    }
}

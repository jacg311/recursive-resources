package nl.enjarai.recursiveresources.repository;

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

    @SuppressWarnings("ConstantConditions")
    public static File determinePackFolder(ResourcePack pack) {
        Class<? extends ResourcePack> cls = pack.getClass();

        if (cls == ZipResourcePack.class || cls == DirectoryResourcePack.class) {
            return ((IExposedResourcePack) pack).getFileOrFolder();
        } else if (pack instanceof IExposedResourcePackDelegate) {
            return determinePackFolder(((IExposedResourcePackDelegate) pack).getDelegate());
        } else {
            return null;
        }
    }

    public static boolean isChildOfFolder(File folder, ResourcePack pack) {
        File packFolder = determinePackFolder(pack);
        return packFolder != null && packFolder.getAbsolutePath().startsWith(folder.getAbsolutePath());
    }

    public interface IExposedResourcePack {
        File getFileOrFolder();
    }

    public interface IExposedResourcePackDelegate {
        ResourcePack getDelegate();
    }
}

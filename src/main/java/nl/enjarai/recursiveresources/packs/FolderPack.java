package nl.enjarai.recursiveresources.packs;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.pack.ResourcePackOrganizer;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.resource.ResourcePackCompatibility;
import net.minecraft.resource.ResourcePackSource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.InputStream;

public class FolderPack implements ResourcePackOrganizer.Pack {
    private static final Identifier folderResource = new Identifier("recursiveresources:textures/gui/folder.png");
    private static final Identifier openFolderResource = new Identifier("recursiveresources:textures/gui/folder_open.png");

    static {
        // for some reason the texture fails to load in the actual game
        loadTexture(folderResource, "/assets/recursiveresources/textures/gui/folder.png");
        loadTexture(openFolderResource, "/assets/recursiveresources/textures/gui/folder_open.png");
    }

    private static void loadTexture(Identifier id, String path) {
        try (InputStream stream = FolderPack.class.getResourceAsStream(path)) {
            MinecraftClient.getInstance().getTextureManager().registerTexture(id, new NativeImageBackedTexture(NativeImage.read(stream)));
        } catch (Exception e) {
            LogManager.getLogger(FolderPack.class).warn("Error loading folder texture:");
            e.printStackTrace();
        }
    }

    private static Identifier loadCustomIcon(File folder) {
        File iconFile = new File(folder, "icon.png");
        if (iconFile.exists()) {
            try (InputStream stream = iconFile.toURI().toURL().openStream()) {
                var relativePath = MinecraftClient.getInstance().getResourcePackDir().toURI().relativize(folder.toURI()).getPath();
                Identifier id = new Identifier("recursiveresources:textures/gui/custom_folders/" + relativePath + ".png");
                MinecraftClient.getInstance().getTextureManager().registerTexture(id, new NativeImageBackedTexture(NativeImage.read(stream)));
                return id;
            } catch (Exception e) {
                LogManager.getLogger(FolderPack.class).warn("Error loading custom folder icon:");
                e.printStackTrace();
            }
        }
        return null;
    }

    private final Text displayName;
    private final Text description;
    @Nullable
    private Identifier icon = null;

    private boolean hovered = false;

    public FolderPack(Text displayName, Text description) {
        this.displayName = displayName;
        this.description = description;
    }

    public FolderPack(Text displayName, Text description, File folder) {
        this(displayName, description);
        icon = loadCustomIcon(folder);
    }

    public void setHovered(boolean hovered) {
        this.hovered = hovered;
    }

    @Override
    public Identifier getIconId() {
        return icon != null ? icon : (hovered ? openFolderResource : folderResource);
    }

    @Override
    public ResourcePackCompatibility getCompatibility() {
        return ResourcePackCompatibility.COMPATIBLE;
    }

    @Override
    public Text getDisplayName() {
        return displayName;
    }

    @Override
    public Text getDescription() {
        return description;
    }

    @Override
    public ResourcePackSource getSource() {
        return ResourcePackSource.PACK_SOURCE_NONE;
    }

    @Override
    public boolean isPinned() {
        return true;
    }

    @Override
    public boolean isAlwaysEnabled() {
        return true;
    }

    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }

    @Override
    public void moveTowardStart() {

    }

    @Override
    public void moveTowardEnd() {

    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public boolean canMoveTowardStart() {
        return false;
    }

    @Override
    public boolean canMoveTowardEnd() {
        return false;
    }
}

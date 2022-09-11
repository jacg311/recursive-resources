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

import java.io.IOException;
import java.io.InputStream;

public class FolderPack implements ResourcePackOrganizer.Pack {
    private static final Identifier folderResource = new Identifier("recursiveresources:textures/gui/folder.png"); // http://www.iconspedia.com/icon/folion-icon-27237.html

    static {
        // for some reason the texture fails to load in the actual game
        try (InputStream stream = FolderPack.class.getResourceAsStream("/assets/recursiveresources/textures/gui/folder.png")) {
            MinecraftClient.getInstance().getTextureManager().registerTexture(folderResource, new NativeImageBackedTexture(NativeImage.read(stream)));
        } catch (IOException e) {
            LogManager.getLogger(FolderPack.class).warn("Error loading folder texture:");
            e.printStackTrace();
        }
    }

    private final Text displayName;
    private final Text description;

    public FolderPack(Text displayName, Text description) {
        this.displayName = displayName;
        this.description = description;
    }

    @Override
    public Identifier getIconId() {
        return folderResource;
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

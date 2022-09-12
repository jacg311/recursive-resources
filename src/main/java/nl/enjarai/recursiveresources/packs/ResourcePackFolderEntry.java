package nl.enjarai.recursiveresources.packs;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.pack.PackListWidget;
import net.minecraft.client.gui.screen.pack.PackListWidget.ResourcePackEntry;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import nl.enjarai.recursiveresources.gui.CustomResourcePackScreen;

import java.io.File;

public class ResourcePackFolderEntry extends ResourcePackEntry {
    public static final String upText = "..";

    private final CustomResourcePackScreen ownerScreen;
    public final File folder;
    public final boolean isUp;

    public ResourcePackFolderEntry(MinecraftClient client, PackListWidget list, CustomResourcePackScreen ownerScreen, File folder, boolean isUp) {
        super(client, list, ownerScreen, new FolderPack(Text.of(isUp ? upText : folder.getName()), Text.of(isUp ? "(Back)" : "(Folder)")));
        this.ownerScreen = ownerScreen;
        this.folder = folder;
        this.isUp = isUp;
    }

    public ResourcePackFolderEntry(MinecraftClient client, PackListWidget list, CustomResourcePackScreen ownerScreen, File folder) {
        this(client, list, ownerScreen, folder, false);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        ownerScreen.moveToFolder(folder);
        return true;
    }

    @Override
    public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        super.render(matrices, index, y, x, entryWidth, entryHeight, mouseX, mouseY, hovered, tickDelta);

        if (hovered) {
            DrawableHelper.fill(matrices, x, y, x + 32, y + 32, 0xa0909090);
        }
    }
}

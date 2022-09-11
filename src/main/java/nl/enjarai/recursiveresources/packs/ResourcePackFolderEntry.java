package nl.enjarai.recursiveresources.packs;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.pack.PackListWidget;
import net.minecraft.client.gui.screen.pack.PackListWidget.ResourcePackEntry;
import net.minecraft.text.LiteralText;
import nl.enjarai.recursiveresources.gui.CustomResourcePackScreen;

import java.io.File;

public class ResourcePackFolderEntry extends ResourcePackEntry {
    public static final String upText = "..";

    private final CustomResourcePackScreen ownerScreen;
    public final File folder;
    public final boolean isUp;

    public ResourcePackFolderEntry(MinecraftClient client, PackListWidget list, CustomResourcePackScreen ownerScreen, File folder, boolean isUp) {
        super(client, list, ownerScreen, new FolderPack(new LiteralText(isUp ? upText : folder.getName()), new LiteralText(isUp ? "(Back)" : "(Folder)")));
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



//    @Override
//    public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
//        ();
//        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
//        DrawableHelper.blit(x, y, 0F, 0F, 32, 32, 32, 32);
//
//        String title = getDisplayName();
//        String desc = getDescription();
//
//        if (client.options.touchscreen || isMouseOver) {
//            DrawableHelper.fill(x, y, x + 32, y + 32, -1601138544);
//            RenderSystem.color4f(1F, 1F, 1F, 1F);
//        }
//
//        TextRenderer fontRenderer = client.textRenderer;
//        int titleWidth = fontRenderer.getWidth(title);
//
//        if (titleWidth > 157) {
//            title = fontRenderer.trimToWidth(title, 157 - fontRenderer.getWidth("...")) + "...";
//        }
//
//        fontRenderer.drawWithShadow(title, x + 32 + 2, y + 1, 16777215);
//        List<String> lines = fontRenderer.wrapLines(desc, 157);
//
//        for (int line = 0; line < 2 && line < lines.size(); ++line) {
//            fontRenderer.drawWithShadow(lines.get(line), x + 32 + 2, y + 12 + 10 * line, 8421504);
//        }
//    }
}

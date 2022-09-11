package nl.enjarai.recursiveresources.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.pack.PackListWidget;
import net.minecraft.client.gui.screen.pack.PackListWidget.ResourcePackEntry;
import net.minecraft.client.gui.screen.pack.PackScreen;
import net.minecraft.client.gui.screen.pack.ResourcePackOrganizer;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import nl.enjarai.recursiveresources.packs.ResourcePackFolderEntry;
import nl.enjarai.recursiveresources.packs.ResourcePackListProcessor;
import nl.enjarai.recursiveresources.repository.ResourcePackUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import static nl.enjarai.recursiveresources.repository.ResourcePackUtils.wrap;

public class CustomResourcePackScreen extends PackScreen {
    private final MinecraftClient client = MinecraftClient.getInstance();

    private final ResourcePackManager packManager;
    private final Consumer<ResourcePackManager> applier;

    private final ResourcePackListProcessor listProcessor = new ResourcePackListProcessor(this::refresh);
    private Comparator<ResourcePackEntry> currentSorter;

    private PackListWidget originalAvailablePacks;
    private PackListWidgetCustom customAvailablePacks;
    private TextFieldWidget searchField;

    private File currentFolder = client.getResourcePackDir();
    private boolean folderView = true;

    public CustomResourcePackScreen(Screen parent, ResourcePackManager packManager, Consumer<ResourcePackManager> applier, File file, Text title) {
        super(parent, packManager, applier, file, title);
        this.packManager = packManager;
        this.applier = applier;
    }

    // Components

    @Override
    protected void init() {
        client.keyboard.setRepeatEvents(true);
        super.init();

        var openFolderText = new TranslatableText("pack.openFolder");
        var doneText = new TranslatableText("gui.done");

        findButton(openFolderText).ifPresent(btn -> {
            btn.x = width / 2 + 25;
            btn.y = height - 48;
        });

        findButton(doneText).ifPresent(btn -> {
            btn.x = width / 2 + 25;
            btn.y = height - 26;
        });

        addDrawableChild(new ButtonWidget(width / 2 - 179, height - 26, 30, 20, Text.of("A-Z"), btn -> {
            listProcessor.setSorter(currentSorter = ResourcePackListProcessor.sortAZ);
        }));

        addDrawableChild(new ButtonWidget(width / 2 - 179 + 34, height - 26, 30, 20, Text.of("Z-A"), btn -> {
            listProcessor.setSorter(currentSorter = ResourcePackListProcessor.sortZA);
        }));

        addDrawableChild(new ButtonWidget(width / 2 - 179 + 68, height - 26, 86, 20, Text.of(folderView ? "Folder View" : "Flat View"), btn -> {
            folderView = !folderView;
            btn.setMessage(Text.of(folderView ? "Folder View" : "Flat View"));

            refresh();
            customAvailablePacks.setScrollAmount(0.0);
        }));

        searchField = addDrawableChild(new TextFieldWidget(
                textRenderer, width / 2 - 179, height - 46, 154, 16, searchField, Text.of("")));
        searchField.setFocusUnlocked(true);
        searchField.setChangedListener(listProcessor::setFilter);
        addDrawableChild(searchField);

        originalAvailablePacks = availablePackList;

        if (originalAvailablePacks == null) {
            client.setScreen(parent);
            return;
        }

        remove(originalAvailablePacks);
        addSelectableChild(customAvailablePacks = new PackListWidgetCustom(originalAvailablePacks, 200, height, width / 2 - 204));

        availablePackList = customAvailablePacks;

        listProcessor.pauseCallback();
        listProcessor.setSorter(currentSorter == null ? (currentSorter = ResourcePackListProcessor.sortAZ) : currentSorter);
        listProcessor.setFilter(searchField.getText());
        listProcessor.resumeCallback();
    }

    private Optional<ClickableWidget> findButton(Text text) {
        return children.stream()
                .filter(ClickableWidget.class::isInstance)
                .map(ClickableWidget.class::cast)
                .filter(btn -> text.equals(btn.getMessage()))
                .findFirst();
    }

    @Override
    public void updatePackLists() {
        super.updatePackLists();
        if (customAvailablePacks != null) {
            onFiltersUpdated();
        }
    }

    // Processing

    private boolean notInRoot() {
        return folderView && !currentFolder.equals(client.getResourcePackDir());
    }

    private void onFiltersUpdated() {
        List<ResourcePackEntry> folders = null;

        if (folderView) {
            folders = new ArrayList<>();

            if (notInRoot()) {
                folders.add(new ResourcePackFolderEntry(client, customAvailablePacks, this, currentFolder.getParentFile(), true));
            }

            for (File folder : wrap(currentFolder.listFiles(ResourcePackUtils::isFolderButNotFolderBasedPack))) {
                folders.add(new ResourcePackFolderEntry(client, customAvailablePacks, this, folder));
            }
        }

        listProcessor.apply(customAvailablePacks.children().stream().toList(), folders, customAvailablePacks.children());

        if (folderView) {
            var filteredPacks = customAvailablePacks.children().stream().filter(entry -> {
                if (entry instanceof ResourcePackFolderEntry folder) {
                    return folder.isUp || currentFolder.equals(folder.folder.getParentFile());
                }

                File file = ResourcePackUtils.determinePackFolder(((ResourcePackOrganizer.AbstractPack) entry.pack).profile.createResourcePack());
                return file == null ? !notInRoot() : currentFolder.equals(file.getParentFile());
            }).toList();

            customAvailablePacks.children().clear();
            customAvailablePacks.children().addAll(filteredPacks);
        }

        customAvailablePacks.setScrollAmount(customAvailablePacks.getScrollAmount());
    }

    public void moveToFolder(File folder) {
        currentFolder = folder;
        refresh();
        customAvailablePacks.setScrollAmount(0.0);
    }

    // UI Overrides

    @Override
    public void tick() {
        super.tick();
        searchField.tick();
    }

    @Override
    public void removed() {
        super.removed();
        client.keyboard.setRepeatEvents(false);
    }
}

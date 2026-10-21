package astolfo.gui.resourcepack;

import astolfo.gui.screen.CustomResourcePacksScreen;
import java.io.File;
import java.util.List;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class FolderPackEntry extends CustomPackEntry {
   private static final ResourceLocation FOLDER_ICON = new ResourceLocation("textures/gui/folder.png");
   private final CustomResourcePacksScreen packsScreen;
   public final File directory;
   public final String displayName;
   public final boolean isBackEntry;

   public FolderPackEntry(CustomResourcePacksScreen customResourcePacksScreen, File file) {
      super(customResourcePacksScreen);
      this.packsScreen = customResourcePacksScreen;
      this.directory = file;
      this.displayName = file.getName();
      this.isBackEntry = false;
   }

   public FolderPackEntry(CustomResourcePacksScreen customResourcePacksScreen, File file, boolean bl) {
      super(customResourcePacksScreen);
      this.packsScreen = customResourcePacksScreen;
      this.directory = file;
      this.displayName = "..";
      this.isBackEntry = bl;
   }

   @Override
   public void func_148313_c() {
      super.mc.getTextureManager().bindTexture(FOLDER_ICON);
   }

   @Override
   public String func_148312_b() {
      return this.displayName;
   }

   @Override
   public String func_148311_a() {
      return this.isBackEntry ? "(Back)" : "(Folder)";
   }

   @Override
   public boolean mousePressed(int slotIndex, int i, int j, int k, int l, int m) {
      this.packsScreen.openDirectory(this.directory);
      return true;
   }

   @Override
   public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected) {
      this.drawFolderEntry(this, x, y, isSelected);
   }

   public void drawFolderEntry(FolderPackEntry folderPackEntry, int i, int j, boolean bl) {
      folderPackEntry.func_148313_c();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.enableBlend();
      GlStateManager.blendFunc(770, 771);
      Gui.drawModalRectWithCustomSizedTexture(i, j, 0.0F, 0.0F, 32, 32, 32.0F, 32.0F);
      GlStateManager.disableBlend();
      boolean flag = super.mc.gameSettings.touchscreen;
      if ((flag || bl) && folderPackEntry.func_148310_d()) {
         Gui.drawRect(i, j, i + 32, j + 32, -1601138544);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      }

      String s = folderPackEntry.func_148312_b();
      int i1 = super.mc.fontRendererObj.getStringWidth(s);
      if (i1 > 157) {
         s = super.mc.fontRendererObj.trimStringToWidth(s, 157 - super.mc.fontRendererObj.getStringWidth("...")) + "...";
      }

      super.mc.fontRendererObj.drawStringWithShadow(s, (float)(i + 32 + 2), (float)(j + 1), 16777215);
      List list = super.mc.fontRendererObj.listFormattedStringToWidth(folderPackEntry.func_148311_a(), 157);

      for (int j1 = 0; j1 < 2 && j1 < list.size(); j1++) {
         super.mc.fontRendererObj.drawStringWithShadow((String)list.get(j1), (float)(i + 32 + 2), (float)(j + 12 + 10 * j1), 8421504);
      }
   }
}

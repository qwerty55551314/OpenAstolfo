package net.minecraft.client.resources;

import com.google.gson.JsonParseException;
import java.io.IOException;
import net.minecraft.client.gui.GuiScreenResourcePacks;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.data.PackMetadataSection;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResourcePackListEntryDefault extends ResourcePackListEntry {
   private static final Logger logger = LogManager.getLogger();
   private final IResourcePack H = this.mc.getResourcePackRepository().rprDefaultResourcePack;
   private final ResourceLocation resourcePackIcon;

   public ResourcePackListEntryDefault(GuiScreenResourcePacks resourcePacksGUIIn) {
      super(resourcePacksGUIIn);

      DynamicTexture dynamictexture;
      try {
         dynamictexture = new DynamicTexture(this.H.getPackImage());
      } catch (IOException ioexception) {
         dynamictexture = TextureUtil.missingTexture;
      }

      this.resourcePackIcon = super.mc.getTextureManager().getDynamicTextureLocation("texturepackicon", dynamictexture);
   }

   @Override
   public int func_183019_a() {
      return 1;
   }

   @Override
   public String func_148311_a() {
      try {
         PackMetadataSection packmetadatasection = this.H.getPackMetadata(super.mc.getResourcePackRepository().rprMetadataSerializer, "pack");
         if (packmetadatasection != null) {
            return packmetadatasection.getPackDescription().getFormattedText();
         }
      } catch (JsonParseException jsonparseexception) {
         logger.error("Couldn't load metadata info", (Throwable)jsonparseexception);
      } catch (IOException ioexception) {
         logger.error("Couldn't load metadata info", (Throwable)ioexception);
      }

      return EnumChatFormatting.RED + "Missing pack.mcmeta :(";
   }

   @Override
   public boolean func_148309_e() {
      return false;
   }

   @Override
   public boolean func_148308_f() {
      return false;
   }

   @Override
   public boolean func_148314_g() {
      return false;
   }

   @Override
   public boolean func_148307_h() {
      return false;
   }

   @Override
   public String func_148312_b() {
      return "Default";
   }

   @Override
   public void func_148313_c() {
      super.mc.getTextureManager().bindTexture(this.resourcePackIcon);
   }

   @Override
   public boolean func_148310_d() {
      return false;
   }
}

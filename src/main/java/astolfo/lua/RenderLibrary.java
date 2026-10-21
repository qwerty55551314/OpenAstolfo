package astolfo.lua;

import astolfo.ClientAccess;
import astolfo.lua.render.Circle;
import astolfo.lua.render.DrawImage;
import astolfo.lua.render.GetStringWidth;
import astolfo.lua.render.Line;
import astolfo.lua.render.LineWidth;
import astolfo.lua.render.Player;
import astolfo.lua.render.PlayerHead;
import astolfo.lua.render.Rect;
import astolfo.lua.render.Scale;
import astolfo.lua.render.ShowGui;
import astolfo.lua.render.StringFunction;
import astolfo.lua.render.StringShadow;
import astolfo.lua.render.WorldToScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.VarArgFunction;

public class RenderLibrary extends TwoArgFunction implements ClientAccess {
   public static final OneArgFunction showGuiFunction = new ShowGui();
   public static final VarArgFunction stringFunction = new StringFunction();
   public static final VarArgFunction worldToScreenFunction = new WorldToScreen();
   public static final VarArgFunction stringShadowFunction = new StringShadow();
   public static final OneArgFunction getStringWidthFunction = new GetStringWidth();
   public static final OneArgFunction scaleFunction = new Scale();
   public static final OneArgFunction lineWidthFunction = new LineWidth();
   public static final VarArgFunction playerFunction = new Player();
   public static final VarArgFunction playerHeadFunction = new PlayerHead();
   public static final VarArgFunction rectFunction = new Rect();
   public static final VarArgFunction drawImageFunction = new DrawImage();
   public static final VarArgFunction lineFunction = new Line();
   public static final VarArgFunction circleFunction = new Circle();
   private static final ResourceLocation FALLBACK_IMAGE_TEXTURE = new ResourceLocation("client/da.png");
   private static String y;

   @Override
   public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
      LuaTable luatable = LuaValue.tableOf();
      luatable.set("rect", rectFunction);
      luatable.set("line", lineFunction);
      luatable.set("player_head", playerHeadFunction);
      luatable.set("circle", circleFunction);
      luatable.set("player", playerFunction);
      luatable.set("get_string_width", getStringWidthFunction);
      luatable.set("string", stringFunction);
      luatable.set("show_gui", showGuiFunction);
      luatable.set("scale", scaleFunction);
      luatable.set("line_width", lineWidthFunction);
      luatable.set("string_shadow", stringShadowFunction);
      luatable.set("world_to_screen", worldToScreenFunction);
      luatable.set("draw_image", drawImageFunction);
      luaValue2.set("render", luatable);
      return luatable;
   }

   public static ResourceLocation getOrDownloadImageTexture(String string, boolean bl) {
      String s = string.replaceAll("[^a-zA-Z0-9]", "");
      ResourceLocation resourcelocation = new ResourceLocation("script_img/" + s);
      TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
      ITextureObject itextureobject = texturemanager.getTexture(resourcelocation);
      if (itextureobject == null) {
         ThreadDownloadImageData threaddownloadimagedata = new ThreadDownloadImageData(null, string, FALLBACK_IMAGE_TEXTURE, new ImageBufferDownload(true));
         texturemanager.loadTexture(resourcelocation, threaddownloadimagedata);
      }

      return resourcelocation;
   }
}

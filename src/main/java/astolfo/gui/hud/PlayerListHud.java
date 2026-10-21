package astolfo.gui.hud;

import astolfo.Astolfo;
import astolfo.gui.component.Component;
import astolfo.module.exploits.Denicker;
import astolfo.module.visuals.PlayerList;
import astolfo.render.RenderUtil;
import java.awt.Color;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;

public class PlayerListHud extends Component {
   private final Map<String, String> playerTags = new LinkedHashMap<>();
   private int backgroundColor = new Color(17, 17, 17, 197).getRGB();

   public PlayerListHud(float f, float g, int i, int j) {
      super("Player list", f, g, i, j);
   }

   @Override
   public void onClick(int i) {
   }

   @Override
   public void render(int i, int j) {
      float f = this.getX();
      float f1 = this.getYPos();
      float f2 = (float)this.getWidth();
      float f3 = (float)this.getHeight();
      RenderUtil.drawRect((double)f, (double)f1, (double)(f + f2), (double)(f1 + f3), new Color(1, 1, 1, 180).getRGB());
      RenderUtil.drawRect((double)f, (double)f1, (double)(f + f2), (double)(f1 + 15.0F), new Color(1, 1, 1, 120).getRGB());
      mc.fontRendererObj.drawStringWithShadow(this.getName(), f + 3.0F, f1 + 3.0F, -1);
      int b0 = 15;
      int i1 = 0;

      for (Entry entry : this.playerTags.entrySet()) {
         boolean flag = false;
         if (!((String)entry.getValue()).contains("kill")
            && !((String)entry.getKey()).equals(PlayerList.RED_TEAM)
            && !((String)entry.getKey()).equals(PlayerList.BLUE_TEAM)
            && !((String)entry.getKey()).equals(PlayerList.GREEN_TEAM)
            && !((String)entry.getKey()).equals(PlayerList.YELLOW_TEAM)) {
            EntityPlayer entityplayer = mc.theWorld.getPlayerEntityByName((String)entry.getKey());
            if (entityplayer == null) {
               continue;
            }
         } else {
            flag = true;
            i1++;
         }

         if (i1 <= 10 || !flag) {
            if (flag) {
               FontRenderer fontrenderer = mc.fontRendererObj;
               StringBuilder stringbuilder = new StringBuilder()
                  .append(
                     i1 == 1 && !((String)entry.getKey()).equals(PlayerList.RED_TEAM) ? EnumChatFormatting.YELLOW + "✦" + EnumChatFormatting.RESET + " " : ""
                  );
               Astolfo astolfo = client;
               fontrenderer.drawStringWithShadow(
                  stringbuilder.append(
                        astolfo.getRelationManager().isEnemyName((String)entry.getKey())
                           ? EnumChatFormatting.RED
                           : (Denicker.nicks.get(entry.getKey()) != null ? EnumChatFormatting.YELLOW : "")
                     )
                     .append((String)entry.getKey())
                     .toString(),
                  f + 3.0F,
                  f1 + 3.0F + (float)b0,
                  -1
               );
               mc.fontRendererObj
                  .drawStringWithShadow(
                     (String)entry.getValue(), f + f2 - (float)mc.fontRendererObj.getStringWidth((String)entry.getValue()) - 3.0F, f1 + 3.0F + (float)b0, -1
                  );
            } else {
               String s = "";
               if (((String)entry.getValue()).equals("nicked")) {
                  s = EnumChatFormatting.YELLOW.toString();
               }

               if (((String)entry.getValue()).equals("enemy")) {
                  s = EnumChatFormatting.RED.toString();
               }

               mc.fontRendererObj.drawStringWithShadow(s + (String)entry.getKey(), f + 3.0F, f1 + 3.0F + (float)b0, -1);
            }

            b0 += 15;
         }
      }

      this.setHeight(b0);
   }

   public Map<String, String> getPlayerTags() {
      return this.playerTags;
   }
}

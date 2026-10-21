package astolfo.module.visuals;

import astolfo.ClientAccess;
import astolfo.event.EventHandler;
import astolfo.event.Listener;
import astolfo.event.render.RenderEvent;
import astolfo.event.render.RenderType;
import astolfo.module.Category;
import astolfo.module.Module;
import astolfo.setting.NumberSetting;
import astolfo.setting.Setting;
import org.lwjgl.opengl.GL11;

public final class ChunkBorders extends Module {
   private final NumberSetting<Integer> height = new NumberSetting<>("Height", "Size", 0, 0, 100);
   private final NumberSetting<Float> width = new NumberSetting<>("Width", "line width", 1.0F, 0.1F, 3.0F);
   @EventHandler(RenderEvent.class)
   private Listener<RenderEvent> onRender = this::onRender;

   public ChunkBorders(Category category) {
      super(category);
      this.addSettings(new Setting[]{this.height, this.width});
      this.setHidden(false);
   }

   private void onRender(RenderEvent renderEvent) {
      if (renderEvent.getType() == RenderType.WORLD) {
         int i = (int)Math.floor(ClientAccess.mc.thePlayer.posX / 16.0) * 16;
         int j = (int)Math.floor(ClientAccess.mc.thePlayer.posZ / 16.0) * 16;
         GL11.glPushMatrix();
         GL11.glTranslated(
            -ClientAccess.mc.getRenderManager().viewerPosX, -ClientAccess.mc.getRenderManager().viewerPosY, -ClientAccess.mc.getRenderManager().viewerPosZ
         );
         GL11.glDisable(3553);
         GL11.glDisable(2896);
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
         GL11.glDisable(2929);
         GL11.glEnable(2848);
         GL11.glLineWidth(this.width.getValue());
         GL11.glColor3f(1.0F, 1.0F, 1.0F);
         int k = this.height.getValue();
         if (k <= 0) {
            k = (int)Math.floor(ClientAccess.mc.getRenderManager().viewerPosY);
         }

         for (int l = -2; l <= 2; l++) {
            for (int i1 = -2; i1 <= 2; i1++) {
               GL11.glBegin(3);
               GL11.glVertex3d((double)(i + l * 16), (double)k, (double)(j + i1 * 16));
               GL11.glVertex3d((double)(i + 16 + l * 16), (double)k, (double)(j + i1 * 16));
               GL11.glVertex3d((double)(i + 16 + l * 16), (double)k, (double)(j + 16 + i1 * 16));
               GL11.glVertex3d((double)(i + l * 16), (double)k, (double)(j + 16 + i1 * 16));
               GL11.glVertex3d((double)(i + l * 16), (double)k, (double)(j + i1 * 16));
               GL11.glEnd();
            }
         }

         GL11.glDisable(2848);
         GL11.glEnable(2929);
         GL11.glEnable(2896);
         GL11.glDisable(3042);
         GL11.glEnable(3553);
         GL11.glPopMatrix();
      }
   }
}

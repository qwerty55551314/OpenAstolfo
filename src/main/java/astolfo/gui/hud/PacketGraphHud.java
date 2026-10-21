package astolfo.gui.hud;

import astolfo.gui.component.Component;
import astolfo.module.visuals.HUD;
import astolfo.render.RenderUtil;
import astolfo.setting.ColorSetting;
import java.awt.Color;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import javax.vecmath.Vector2f;
import org.lwjgl.opengl.GL11;

public class PacketGraphHud extends Component {
   private ArrayBlockingQueue<PacketSnapshot> snapshots = new ArrayBlockingQueue<>(50);
   private boolean showAverage;
   private boolean showIncoming;

   public PacketGraphHud(float f, float g, int i, int j) {
      super("Graph", f, g, i, j);
      this.setDraggable(true);
   }

   @Override
   public void render(int i, int j) {
      try {
         int i1 = this.showIncoming ? this.getMaxIncoming() : this.getMaxOutgoing();
         int j1 = this.showIncoming ? this.getMinIncoming() : this.getMinOutgoing();
         double d0 = ((double)this.getWidth() - 2.0) / (double)(this.snapshots.size() - 1);
         double d1 = ((double)this.getHeight() - 2.0) / (double)(i1 - j1);
         ArrayList arraylist = new ArrayList();
         int k = 0;
         int l = 0;

         for (PacketSnapshot packetsnapshot : this.snapshots) {
            boolean flag = this.showIncoming;
            int i1x = flag ? packetsnapshot.incoming.size() : packetsnapshot.outgoing.size();
            int j1x = (int)((double)k * d0);
            int k1 = (int)((double)(i1 - i1x) * d1);
            arraylist.add(new Vector2f((float)j1x, (float)k1));
            k++;
            l += i1x;
         }

         int j3 = l / this.snapshots.size();
         GL11.glPushMatrix();
         GL11.glScalef(0.5F, 0.5F, 0.5F);
         mc.fontRendererObj
            .drawStringWithShadow(this.showIncoming ? "Incoming packets" : "Outgoing packets", this.getX() * 2.0F, (this.getYPos() - 7.0F) * 2.0F, -1);
         String s1 = Math.round((float)l / (float)this.snapshots.size() * 20.0F) + " packets/sec";
         mc.fontRendererObj
            .drawStringWithShadow(
               s1, (this.getX() + (float)this.getWidth() - (float)mc.fontRendererObj.getStringWidth(s1) / 2.0F) * 2.0F, (this.getYPos() - 7.0F) * 2.0F, -1
            );
         GL11.glPopMatrix();
         int k3 = ((ColorSetting)HUD.graphs.getSetting("Background")).getValue().getRGB();
         int l3 = ((ColorSetting)HUD.graphs.getSetting("Line")).getValue().getRGB();
         RenderUtil.drawRect(
            (double)this.getX(), (double)this.getYPos(), (double)(this.getX() + (float)this.getWidth()), (double)(this.getYPos() + (float)this.getHeight()), k3
         );
         int b0 = 3;

         for (int l1 = 0; l1 < b0 + 1; l1++) {
            int i2 = (int)this.getX();
            int j2 = i2 + this.getWidth();
            int k2 = (int)(this.getYPos() + (float)this.getHeight() - (float)(l1 * this.getHeight() / b0));
            if (this.snapshots.size() > 0) {
               String s = Math.round((double)((int)(((double)j1 + (double)(i1 - j1) * ((double)l1 * 1.0 / (double)b0)) * 100.0)) / 100.0) + "";
               int l2 = mc.fontRendererObj.getStringWidth(s) / 2;
               GL11.glPushMatrix();
               GL11.glScalef(0.5F, 0.5F, 0.5F);
               if (l1 == 3) {
                  k2 = (int)this.getYPos() + 2;
               }

               if (l1 == 0) {
                  k2 = (int)this.getYPos() + this.getHeight() - 3;
               }

               mc.fontRendererObj
                  .drawStringWithShadow(s, (float)((i2 - l2 - 3) * 2), ((float)k2 + (float)mc.fontRendererObj.FONT_HEIGHT / 2.0F - 6.0F) * 2.0F, -1);
               GL11.glPopMatrix();
            }

            RenderUtil.drawLine(1.0F, (float)i2, (float)k2, (float)j2, (float)k2, k3);
         }

         for (int b1 = 0; b1 < this.snapshots.size(); b1 += 2) {
            int i3 = this.snapshots.size();
            if (i3 > 1) {
               int k4 = (int)(this.getX() + (float)(b1 * this.getWidth() / (this.snapshots.size() - 1)));
               int l4 = (int)this.getYPos();
               int i5 = l4 + this.getHeight();
               RenderUtil.drawLine(1.0F, (float)k4, (float)l4, (float)k4, (float)i5, k3);
            }
         }

         for (int i4 = 0; i4 < arraylist.size() - 1; i4++) {
            float f = ((Vector2f)arraylist.get(i4)).x;
            float f1 = ((Vector2f)arraylist.get(i4)).y;
            float f2 = ((Vector2f)arraylist.get(i4 + 1)).x;
            float f3 = ((Vector2f)arraylist.get(i4 + 1)).y;
            RenderUtil.drawLine(1.0F, this.getX() + f, this.getYPos() + f1, this.getX() + f2, this.getYPos() + f3, l3);
         }

         boolean flag1 = this.showAverage;
         if (flag1) {
            int j4 = (int)(this.getYPos() + (float)((int)((double)(i1 - j3) * d1)));
            GL11.glPushMatrix();
            GL11.glScalef(0.5F, 0.5F, 0.5F);
            mc.fontRendererObj.drawStringWithShadow("avg: " + j3, (this.getX() + (float)this.getWidth() + 3.0F) * 2.0F, (float)((j4 - 2) * 2), -1);
            GL11.glPopMatrix();
            RenderUtil.drawLine(1.0F, this.getX(), (float)j4, this.getX() + (float)this.getWidth(), (float)j4, Color.GREEN.getRGB());
         }
      } catch (ArithmeticException arithmeticexception) {
      }
   }

   private int getMaxOutgoing() {
      int i = 0;

      for (PacketSnapshot packetsnapshot : this.snapshots) {
         if (i < packetsnapshot.outgoing.size()) {
            i = packetsnapshot.outgoing.size();
         }
      }

      return i;
   }

   private int getMinOutgoing() {
      int i = 999999;

      for (PacketSnapshot packetsnapshot : this.snapshots) {
         if (i > packetsnapshot.outgoing.size()) {
            i = packetsnapshot.outgoing.size();
         }
      }

      return i;
   }

   private int getMaxIncoming() {
      int i = 0;

      for (PacketSnapshot packetsnapshot : this.snapshots) {
         if (i < packetsnapshot.incoming.size()) {
            i = packetsnapshot.incoming.size();
         }
      }

      return i;
   }

   private int getMinIncoming() {
      int i = 999999;

      for (PacketSnapshot packetsnapshot : this.snapshots) {
         if (i > packetsnapshot.incoming.size()) {
            i = packetsnapshot.incoming.size();
         }
      }

      return i;
   }

   @Override
   public void onClick(int i) {
   }

   public void setShowAverage(boolean bl) {
      this.showAverage = bl;
   }

   public void setShowIncoming(boolean bl) {
      this.showIncoming = bl;
   }

   public void setSnapshots(ArrayBlockingQueue<PacketSnapshot> arrayBlockingQueue) {
      this.snapshots = arrayBlockingQueue;
   }
}

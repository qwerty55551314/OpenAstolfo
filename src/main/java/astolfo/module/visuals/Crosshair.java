package astolfo.module.visuals;

import astolfo.ClientAccess;
import astolfo.event.EventHandler;
import astolfo.event.Listener;
import astolfo.event.render.RenderEvent;
import astolfo.event.render.RenderType;
import astolfo.module.Category;
import astolfo.module.Module;
import astolfo.render.ClientColor;
import astolfo.render.RenderUtil;
import astolfo.setting.BooleanSetting;
import astolfo.setting.ColorSetting;
import astolfo.setting.NumberSetting;
import astolfo.setting.Setting;
import java.awt.Color;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public final class Crosshair extends Module {
   private final NumberSetting<Float> gap = new NumberSetting<>("Gap", "Gap of crosshair", 5.0F, 0.0F, 15.0F);
   private final NumberSetting<Float> width = new NumberSetting<>("Width", "Width of crosshair", 0.5F, 0.1F, 3.0F);
   private final NumberSetting<Float> size = new NumberSetting<>("Size", "Size of crosshair", 7.0F, 1.0F, 15.0F);
   private final ColorSetting color = new ColorSetting("Color", "Color", ClientColor.PURPLE.getColor());
   private final ColorSetting outlineColor = new ColorSetting("Outline-Color", "Color", Color.BLACK);
   private final BooleanSetting dynamic = new BooleanSetting("Dynamic", "with bows n shit u know !!", true);
   private final BooleanSetting dot = new BooleanSetting("Dot", "puts da DOT in da MIDDLE", true);
   @EventHandler(RenderEvent.class)
   private Listener<RenderEvent> onRender = this::onRender;

   public Crosshair(Category category) {
      super(category);
      this.setHidden(false);
      this.addSettings(new Setting[]{this.color, this.outlineColor, this.size, this.gap, this.width, this.dynamic, this.dot});
   }

   private void onRender(RenderEvent renderEvent) {
      if (renderEvent.getType() == RenderType.SCREEN) {
         int i = this.outlineColor.getValue().getRGB();
         int j = renderEvent.getResolution().getScaledWidth() / 2;
         int k = renderEvent.getResolution().getScaledHeight() / 2;
         float f = this.gap.getValue();
         boolean flag = ClientAccess.mc.thePlayer.isSpectator();
         if (!flag && this.dynamic.getValue() && ClientAccess.mc.thePlayer.getHeldItem() != null) {
            ItemStack itemstack = ClientAccess.mc.thePlayer.getHeldItem();
            int l = ClientAccess.mc.thePlayer.getItemInUseCount();
            if (itemstack.getItem() == Items.bow) {
               float f1 = (float)(itemstack.getMaxItemUseDuration() - l) / 20.0F;
               if (l == 0 || f1 > 1.0F) {
                  f1 = 1.0F;
               }

               f = this.gap.getValue() + (float)((int)((1.0F - f1) * (this.gap.getValue() + 5.0F) * 2.0F));
            }
         }

         RenderUtil.drawThinBorderedRect(
            (float)j - this.width.getValue(),
            (float)k - f - this.size.getValue(),
            (float)j + 1.0F + this.width.getValue(),
            (float)k - f + 0.2F,
            this.color.getValue().getRGB(),
            i
         );
         RenderUtil.drawThinBorderedRect(
            (float)j - this.width.getValue(),
            (float)k + f + 1.0F - 0.2F,
            (float)j + 1.0F + this.width.getValue(),
            (float)(k + 1) + f + this.size.getValue() - 0.15F,
            this.color.getValue().getRGB(),
            i
         );
         RenderUtil.drawThinBorderedRect(
            (float)j - f - this.size.getValue() + 0.15F,
            (float)k - this.width.getValue(),
            (float)j - f + 0.2F,
            (float)k + 1.0F + this.width.getValue(),
            this.color.getValue().getRGB(),
            i
         );
         RenderUtil.drawThinBorderedRect(
            (float)(j + 1) + f - 0.2F,
            (float)k - this.width.getValue(),
            (float)j + this.size.getValue() + f + 1.0F,
            (float)k + 1.0F + this.width.getValue(),
            this.color.getValue().getRGB(),
            i
         );
         boolean flag1 = this.dot.getValue();
         if (flag1) {
            RenderUtil.drawThinBorderedRect((float)j - 0.7F, (float)k - 0.7F, (float)j + 1.7F, (float)k + 1.7F, this.color.getValue().getRGB(), i);
         }
      }
   }
}

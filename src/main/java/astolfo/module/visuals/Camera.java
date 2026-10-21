package astolfo.module.visuals;

import astolfo.ClientAccess;
import astolfo.event.EventHandler;
import astolfo.event.Listener;
import astolfo.event.render.RenderItemEvent;
import astolfo.module.Category;
import astolfo.module.Module;
import astolfo.setting.BooleanSetting;
import astolfo.setting.ColorSetting;
import astolfo.setting.MapSetting;
import astolfo.setting.MapSettingBuilder;
import astolfo.setting.NumberSetting;
import astolfo.setting.Setting;
import java.awt.Color;
import net.minecraft.client.renderer.GlStateManager;

public final class Camera extends Module {
   private final NumberSetting<Float> itemSize = new NumberSetting<>("Item-Size", "Size", 0.6F, 0.0F, 2.0F);
   public static final NumberSetting<Float> itemFOV = new NumberSetting<>("Item-FOV", "Item FOV", 1.0F, 1.0F, 2.0F);
   public static final BooleanSetting viewClip = new BooleanSetting("View-Clip", "clips thru stuff in third person", true);
   public static final MapSetting itemPosition = new MapSettingBuilder("Item-Position")
      .ordered()
      .description("")
      .add("X", new NumberSetting<>("X", "X coordinate", 0.0F, -2.0F, 2.0F))
      .add("Y", new NumberSetting<>("Y", "Y coordinate", 0.0F, -2.0F, 2.0F))
      .add("Z", new NumberSetting<>("Z", "Z coordinate", 0.0F, -2.0F, 2.0F))
      .build();
   public static final MapSetting enchant = new MapSettingBuilder("Enchant")
      .ordered()
      .description("enbchant")
      .add("Color", new ColorSetting("Color", "Attacks per second", Color.BLUE))
      .add("Enabled", new BooleanSetting("Enabled", "", false))
      .build();
   @EventHandler(RenderItemEvent.class)
   private Listener<RenderItemEvent> onRenderItem = this::onRenderItem;

   public Camera(Category category) {
      super(category);
      this.addSettings(new Setting[]{enchant, viewClip, this.itemSize, itemFOV, itemPosition});
      this.setHidden(false);
   }

   private void onRenderItem(RenderItemEvent renderItemEvent) {
      if (renderItemEvent.getEntity() == ClientAccess.mc.thePlayer) {
         GlStateManager.scale(this.itemSize.getValue(), this.itemSize.getValue(), this.itemSize.getValue());
      }
   }
}

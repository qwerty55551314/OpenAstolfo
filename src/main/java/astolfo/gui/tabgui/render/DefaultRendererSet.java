package astolfo.gui.tabgui.render;

import astolfo.Astolfo;
import astolfo.gui.TabGui;
import astolfo.gui.tabgui.BooleanSettingItem;
import astolfo.gui.tabgui.CategoryItem;
import astolfo.gui.tabgui.EnumSettingItem;
import astolfo.gui.tabgui.MapSettingItem;
import astolfo.gui.tabgui.ModuleItem;
import astolfo.gui.tabgui.NumberSettingItem;
import astolfo.gui.tabgui.Sized;
import astolfo.render.font.CustomFontRenderer;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;

public class DefaultRendererSet implements RendererSet {
   private final Map<Class<? extends Sized>, ElementRenderer<?>> renderers;
   private static String i;

   @Override
   public CustomFontRenderer getFontRenderer() {
      return Astolfo.INSTANCE.getCustomFontRenderer();
   }

   @Override
   public Map<Class<? extends Sized>, ElementRenderer<?>> getRenderers() {
      return this.renderers;
   }

   public DefaultRendererSet() {
      Object object = null;
      ItemRenderer itemrenderer = new ItemRenderer();
      this.renderers = new Builder<Class<? extends Sized>, ElementRenderer<?>>()
         .put(TabGui.Panel.class, new PanelRenderer())
         .put(CategoryItem.class, itemrenderer)
         .put(ModuleItem.class, itemrenderer)
         .put(BooleanSettingItem.class, itemrenderer)
         .put(NumberSettingItem.class, itemrenderer)
         .put(MapSettingItem.class, itemrenderer)
         .put(EnumSettingItem.class, itemrenderer)
         .build();
   }
}

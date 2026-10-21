package astolfo.gui.clickgui.unused;

import astolfo.gui.component.Container;
import astolfo.module.Module;
import java.awt.Color;

public class ModuleLabel extends Container {
   private Module module;

   public ModuleLabel(Module module, int i, int j, int k, int l) {
      super(module.getName(), (float)i, (float)j, k, l);
      this.module = module;
   }

   @Override
   public void onClick(int i) {
   }

   @Override
   public void render(int i, int j) {
      client.getCustomFontRenderer().drawString(this.module.getName(), this.getX(), this.getYPos(), new Color(97, 95, 97).getRGB());
   }
}

package astolfo.gui.component;

import astolfo.Astolfo;
import astolfo.render.RenderUtil;
import java.awt.Color;
import net.minecraft.client.gui.GuiMainMenu;

public abstract class Button extends Component {
   public Button(String string, float f, float g, int i, int j) {
      super(string, f, g, i, j);
   }

   @Override
   public void onClick(int i) {
      this.onPress();
   }

   public abstract void onPress();

   @Override
   public void render(int i, int j) {
      Astolfo astolfo = client;
      int i1 = astolfo.getSidebarGui().getBaseColor();
      Astolfo astolfo1 = client;
      int j1 = astolfo1.getSidebarGui().getPanelColor();
      Astolfo astolfo2 = client;
      int k = astolfo2.getSidebarGui().getSurfaceColor();
      RenderUtil.drawRect(
         (double)(this.getX() + 1.0F),
         (double)(this.getYPos() + 1.0F),
         (double)(this.getX() + (float)this.getWidth() - 2.0F),
         (double)(this.getYPos() + (float)this.getHeight() - 2.0F),
         i1
      );
      RenderUtil.drawRect(
         (double)(this.getX() + 2.0F),
         (double)(this.getYPos() + 2.0F),
         (double)(this.getX() + (float)this.getWidth() - 3.0F),
         (double)(this.getYPos() + (float)this.getHeight() - 3.0F),
         k
      );
      GuiMainMenu.robotoRegularFont10
         .drawString(
            this.getName(),
            this.getX() + (float)((this.getWidth() - GuiMainMenu.robotoRegularFont10.getStringWidth(this.getName())) / 2) + 1.0F,
            this.getYPos() + (float)(this.getHeight() / 2) - 3.0F,
            Color.WHITE.getRGB()
         );
   }
}

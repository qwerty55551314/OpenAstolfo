package astolfo.gui.clickgui.unused;

import astolfo.Astolfo;
import astolfo.gui.component.Component;
import astolfo.render.RenderUtil;
import java.awt.Color;

public class ListItem extends Component {
   private ListPanel listPanel;

   public ListItem(ListPanel listPanel, String string, int i, int j) {
      super(string, 0.0F, 0.0F, i, j);
      this.listPanel = listPanel;
   }

   @Override
   public void onClick(int i) {
      this.listPanel.setSelectedItem(this);
   }

   @Override
   public void render(int i, int j) {
      if (this.listPanel.getSelectedItem() == this) {
         double d0 = (double)(this.getX() + 2.0F);
         double d1 = (double)(this.getYPos() + 3.0F);
         double d2 = (double)(this.getX() + 3.0F);
         double d3 = (double)(this.getYPos() + (float)this.getHeight() + 3.0F);
         Astolfo astolfo = client;
         RenderUtil.drawRect(d0, d1, d2, d3, astolfo.getColor().getRGB());
      }

      client.getCustomFontRenderer().drawStringWithShadow(this.getName(), this.getX() + 7.0F, this.getYPos() + 2.0F, Color.WHITE.getRGB());
   }
}

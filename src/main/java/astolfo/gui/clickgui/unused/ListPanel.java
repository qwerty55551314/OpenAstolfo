package astolfo.gui.clickgui.unused;

import astolfo.gui.component.Container;
import astolfo.gui.component.Element;
import astolfo.render.RenderUtil;
import java.awt.Color;

public class ListPanel extends Container {
   private static final Color backgroundColor = new Color(34, 33, 32);
   private static final Color borderColor = new Color(26, 26, 26);
   private ListItem selectedItem;
   private static final String e = "Config";

   public ListPanel(String string, int i, int j, int k, int l) {
      super(string, (float)i, (float)j, k, l);
      this.setDraggable(true);
   }

   @Override
   public void onClick(int i) {
      if (i == 1) {
         boolean flag = this.isExpanded();
         this.setExpanded(!flag);
         if (this.isExpanded()) {
            super.expandAnimation.backwards().rewind().start();
         } else {
            super.expandAnimation.forwards().rewind().start();
         }
      }
   }

   @Override
   public void render(int i, int j) {
      int i1 = this.getItemsHeight() + 4;
      RenderUtil.drawRect(
         (double)(this.getX() - 1.5F),
         (double)this.getYPos(),
         (double)(this.getX() + (float)this.getWidth() + 1.5F),
         (double)(this.getYPos() + (float)this.getHeight() + (float)i1 + 3.5F),
         borderColor.getRGB()
      );
      RenderUtil.drawRect(
         (double)this.getX(),
         (double)this.getYPos(),
         (double)(this.getX() + (float)this.getWidth()),
         (double)(this.getYPos() + (float)this.getHeight() + (float)i1 + 2.0F),
         backgroundColor.getRGB()
      );
      RenderUtil.drawRect(
         (double)this.getX(),
         (double)this.getYPos(),
         (double)(this.getX() + (float)this.getWidth()),
         (double)(this.getYPos() + (float)this.getHeight() + 6.0F),
         borderColor.getRGB()
      );
      client.getCustomFontRenderer().drawStringWithShadow(e, this.getX() + 3.0F, this.getYPos() + 2.0F, Color.WHITE.getRGB());
      boolean flag = this.isExpanded();
      if (flag || super.expandAnimation.isRunning()) {
         int j1 = 0;

         for (Element element : this.getChildren()) {
            if (element instanceof ListItem) {
               ListItem listitem = (ListItem)element;
               listitem.setPosition(this.getX(), this.getYPos() + (float)j1 + 15.0F);
               boolean flag1 = this.isExpanded();
               if (flag1) {
                  listitem.render(i, j);
               }

               j1 += 15;
               j1 = (int)((double)j1 - super.expandAnimation.getValue(0.0, (double)j1));
            }
         }
      }
   }

   private int getItemsHeight() {
      int b0 = 0;
      if (this.isExpanded()) {
         for (Element element : this.getChildren()) {
            b0 += 15;
         }
      }

      return b0;
   }

   public ListItem getSelectedItem() {
      return this.selectedItem;
   }

   public void setSelectedItem(ListItem listItem) {
      this.selectedItem = listItem;
   }
}

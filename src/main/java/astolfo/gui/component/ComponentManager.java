package astolfo.gui.component;

import astolfo.gui.clickgui.defaultgui.ConfigsPanel;
import astolfo.gui.clickgui.defaultgui.ScriptsPanel;
import astolfo.manager.Manager;
import java.util.Arrays;

public abstract class ComponentManager extends Manager<Element> {
   private static int T;

   public void updateDragging(int i, int j) {
      for (Element element : this.getList()) {
         if (element instanceof Component) {
            Component component = (Component)element;
            if (component.isDragging()) {
               component.updateDragPosition(i, j);
            }
         }
      }
   }

   public void renderElements(int i, int j) {
      for (Element element : this.getList()) {
         if (!(element instanceof ConfigsPanel) && !(element instanceof ScriptsPanel)) {
            element.render(i, j);
         }
      }
   }

   public void resetDragging(boolean bl) {
      for (Element element : this.getList()) {
         if (element instanceof Component) {
            Component component = (Component)element;
            if (component.isDraggable()) {
               component.setDraggingNoOffset(bl);
            }
         }
      }
   }

   public void addElements(Element... elements) {
      this.getList().addAll(Arrays.asList(elements));
   }

   public static int H() {
      return 0;
   }
}

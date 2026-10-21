package astolfo.gui.clickgui.sidebar;

import astolfo.Astolfo;
import astolfo.gui.animation.Animation;
import astolfo.gui.animation.Easing;
import astolfo.gui.animation.TimedAnimation;
import astolfo.gui.component.Element;
import astolfo.render.RenderUtil;
import astolfo.setting.MapSetting;
import java.awt.Color;

public class MapComponent extends SidebarSettingComponent {
   private Animation arrowAnimation = new TimedAnimation().setDuration(450).setEasing(Easing.LINEAR).forwards().start();
   private final MapSetting mapSetting;
   private boolean mapPanelClosed;
   private int listOffset = 25;
   private boolean arrowForward;

   public MapComponent(ModuleButton moduleButton, MapSetting mapSetting, int i, int j, int k, int l) {
      super(moduleButton, mapSetting, (float)i, (float)j, k, l);
      this.setParent(moduleButton);
      this.mapSetting = mapSetting;
   }

   @Override
   public void onClick(int i) {
      if (i == 0 && this.isVisible()) {
         MapComponent mapcomponent = this.getModuleButton().getSelectedMap();
         if (mapcomponent != null) {
            if (this.getModuleButton().getSelectedMap() != this) {
               this.getModuleButton().setSelectedMap(this);
            } else {
               this.getModuleButton().setSelectedMap(null);
            }
         } else {
            this.getModuleButton().setSelectedMap(this);
         }
      }
   }

   @Override
   public void render(int i, int j) {
      boolean flag = this.arrowAnimation.isRunning();
      if (!flag && (this.isHovered(i, j) || this.getModuleButton().getSelectedMap() == this)) {
         if (this.arrowForward) {
            this.arrowAnimation.forwards().start();
         } else {
            this.arrowAnimation.backwards().start();
         }

         this.arrowForward = !this.arrowForward;
      }

      boolean flag1 = this.isHovered(i, j);
      if (!flag1 && this.getModuleButton().getSelectedMap() != this) {
         this.arrowAnimation.forwards();
      }

      int k = super.fadeAlpha;
      if (k > 40) {
         client.getCustomFontRenderer()
            .drawString(this.mapSetting.getName(), this.getX(), this.getYPos() - 2.0F, new Color(255, 255, 255, super.fadeAlpha).getRGB());
      }

      label110: {
         Astolfo astolfo = Astolfo.INSTANCE;
         int i1 = astolfo.getSidebarGui().getPulseColor();
         RenderUtil.drawArrow(
            (double)(this.getX() + (float)this.getWidth() - 8.0F) + this.arrowAnimation.getValue(-8.0, 0.0),
            (double)this.getYPos(),
            5.0,
            true,
            this.getModuleButton().getSelectedMap() == this ? i1 : new Color(255, 255, 255, super.fadeAlpha).getRGB()
         );
         Sidebar sidebar = this.getModuleButton().getCategoryTab().getSidebar();
         int j1 = this.listOffset;
         if (this.mapPanelClosed || this.getModuleButton().getPreviousMap() != this || this.getModuleButton().getSelectedMap() != null) {
            MapComponent mapcomponent = this.getModuleButton().getPreviousMap();
            if (mapcomponent != null || this.getModuleButton().getSelectedMap() != this) {
               MapComponent mapcomponent1 = this.getModuleButton().getPreviousMap();
               if (mapcomponent1 == this || this.getModuleButton().getSelectedMap() != this) {
                  for (Element element1 : this.getChildren()) {
                     if (element1 instanceof SidebarSettingComponent) {
                        SidebarSettingComponent sidebarsettingcomponent1 = (SidebarSettingComponent)element1;
                        if (!sidebarsettingcomponent1.fadeAnimation.isRunning()) {
                           sidebarsettingcomponent1.fadeAnimation.backwards().start();
                        }

                        sidebarsettingcomponent1.fadeAlpha = (int)sidebarsettingcomponent1.fadeAnimation.getValue(0.0, 255.0);
                     }
                  }
                  break label110;
               }
            }
         }

         for (Element element : this.getChildren()) {
            if (element instanceof SidebarSettingComponent) {
               SidebarSettingComponent sidebarsettingcomponent = (SidebarSettingComponent)element;
               sidebarsettingcomponent.setPosition(this.getX() + 155.0F, sidebar.getYPos() + (float)j1);
               sidebarsettingcomponent.render(i, j);
               if (!sidebarsettingcomponent.fadeAnimation.isRunning()) {
                  sidebarsettingcomponent.fadeAnimation.forwards().start();
               }

               sidebarsettingcomponent.fadeAlpha = (int)sidebarsettingcomponent.fadeAnimation.getValue(0.0, 255.0);
               j1 += 15;
            }
         }
      }

      int l = this.getModuleButton().getCategoryTab().getSidebar().getWidth();
      this.mapPanelClosed = l == 270;
   }

   public MapSetting getMapSetting() {
      return this.mapSetting;
   }
}

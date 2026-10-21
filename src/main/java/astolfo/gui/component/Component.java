package astolfo.gui.component;

import astolfo.gui.animation.Animation;
import astolfo.gui.animation.Easing;
import astolfo.gui.animation.TimedAnimation;

public abstract class Component extends Element {
   private int width;
   private int height;
   private boolean draggable;
   private boolean dragging;
   private float dragOffsetX;
   private float dragOffsetY;
   public Animation stateAnimation = new TimedAnimation().setDuration(100).setEasing(Easing.INERTIA);

   public Component(String string, float f, float g, int i, int j) {
      super(string, f, g);
      this.width = i;
      this.height = j;
   }

   public void updateDragPosition(int i, int j) {
      this.setPosition((float)i - this.dragOffsetX, (float)j - this.dragOffsetY);
   }

   public void onMouseClicked(int i, int j, int k) {
      this.onClick(i);
   }

   public abstract void onClick(int var1);

   public void onKeyTyped(char c, int i) {
   }

   public void onMouseScroll(int i, int j, int k) {
   }

   public boolean isHovered(int i, int j) {
      float f;
      int i1 = (f = (float)i - this.getX()) == 0.0F ? 0 : (f < 0.0F ? -1 : 1);
      if (i1 >= 0) {
         float f1;
         int j1 = (f1 = (float)i - (this.getX() + (float)this.getWidth())) == 0.0F ? 0 : (f1 < 0.0F ? -1 : 1);
         if (j1 <= 0) {
            float f2;
            int k = (f2 = (float)j - this.getYPos()) == 0.0F ? 0 : (f2 < 0.0F ? -1 : 1);
            if (k >= 0) {
               float f3;
               int l = (f3 = (float)j - (this.getYPos() + (float)this.getHeight())) == 0.0F ? 0 : (f3 < 0.0F ? -1 : 1);
               if (l <= 0) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   public boolean isMouseInRect(int i, int j, int k, int l, int m, int n) {
      if (m >= i) {
         int i1 = i + k;
         if (m <= i1 && n >= j && n <= j + l) {
            return true;
         }
      }

      return false;
   }

   public void setIntPosition(int i, int j) {
      this.setX((float)i);
      this.setY((float)j);
   }

   public void setPosition(float f, float g) {
      this.setX(f);
      this.setY(g);
   }

   public int getWidth() {
      return this.width;
   }

   public void setWidth(int i) {
      this.width = i;
   }

   public int getHeight() {
      return this.height;
   }

   public void setHeight(int i) {
      this.height = i;
   }

   public void setDragging(boolean bl, int i, int j) {
      if (bl) {
         this.dragOffsetX = (float)i - this.getX();
         this.dragOffsetY = (float)j - this.getYPos();
      }

      this.dragging = bl;
   }

   public void setDraggingNoOffset(boolean bl) {
      this.dragOffsetX = 0.0F;
      this.dragOffsetY = 0.0F;
      this.dragging = bl;
   }

   public boolean isDragging() {
      return this.dragging;
   }

   public boolean isDraggable() {
      return this.draggable;
   }

   public void setDraggable(boolean bl) {
      this.draggable = bl;
   }
}

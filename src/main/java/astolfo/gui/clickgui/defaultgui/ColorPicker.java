package astolfo.gui.clickgui.defaultgui;

import astolfo.gui.clickgui.SettingContainer;
import astolfo.gui.component.Container;
import astolfo.module.Category;
import astolfo.module.hidden.HypixelPacketHandler;
import astolfo.render.RenderUtil;
import astolfo.setting.ColorMode;
import astolfo.setting.ColorSetting;
import astolfo.setting.Setting;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class ColorPicker extends SettingContainer {
   private final Category category;
   private final Container ownerContainer;
   private final int handleColor = new Color(37, 37, 37, 150).getRGB();
   private int hueOffset;
   private int saturationOffset;
   private int brightnessOffset;
   private int alphaOffset;
   private boolean unusedFlag;
   private boolean edited;
   private static final int HUE_SEGMENT_SIZE = 9;

   public ColorPicker(Category category, Container container, Setting<?> setting, int i, int j) {
      super(container, setting, i, j);
      this.category = category;
      this.ownerContainer = container;
   }

   public int getPickerHeight() {
      return this.getHeight() - 16;
   }

   public float getPickerY() {
      return super.getYPos() + 10.0F;
   }

   @Override
   public void onMouseClicked(int i, int j, int k) {
      switch (i) {
         case 0:
            boolean flag = Keyboard.isKeyDown(29);
            if (flag) {
               DefaultClickGuiScreen.copiedColor = new CopiedColor(
                  this.getColorSetting().mode.ordinal(),
                  this.getColorSetting().getValue(),
                  this.saturationOffset,
                  this.brightnessOffset,
                  this.hueOffset,
                  this.alphaOffset
               );
               return;
            }

            boolean flag1 = Keyboard.isKeyDown(42);
            if (flag1) {
               ColorMode[] acolormode = ColorMode.values();
               int i1 = this.getColorModeIndex();
               i1++;
               if (i1 >= acolormode.length || i1 < 0) {
                  i1 = 0;
               }

               this.getColorSetting().mode = acolormode[i1];
               return;
            }

            if (this.isMouseInRect((int)this.getX(), (int)this.getYPos(), this.getWidth(), 10, j, k)) {
               boolean flag2 = this.isExpanded();
               this.setExpanded(!flag2);
               if (!flag2) {
                  this.seedFromColor(this.getColorSetting().value);
               } else {
                  this.edited = false;
               }
            }
            break;
         case 1:
            if (Keyboard.isKeyDown(29) && DefaultClickGuiScreen.copiedColor != null) {
               this.getColorSetting().setValue(DefaultClickGuiScreen.copiedColor.getColor());
               this.getColorSetting().mode = ColorMode.values()[DefaultClickGuiScreen.copiedColor.getModeOrdinal()];
               this.saturationOffset = DefaultClickGuiScreen.copiedColor.getSaturationOffset();
               this.brightnessOffset = DefaultClickGuiScreen.copiedColor.getBrightnessOffset();
               this.hueOffset = DefaultClickGuiScreen.copiedColor.getHueOffset();
               this.alphaOffset = DefaultClickGuiScreen.copiedColor.getAlphaOffset();
            }
      }
   }

   @Override
   public void onClick(int i) {
   }

   @Override
   public int getHeight() {
      return this.isExpanded() ? super.getHeight() : 10;
   }

   @Override
   public void render(int i, int j) {
      boolean flag1 = this.isExpanded();
      if (!flag1) {
         Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/misc/alpha.png"));
         Gui.drawModalRectWithCustomSizedTexture(
            (int)this.getX() + this.getWidth() - 10,
            (int)this.getYPos(),
            0.0F,
            0.0F,
            10,
            this.getHeight(),
            (float)this.getWidth(),
            (float)(this.getHeight() * 10)
         );
         RenderUtil.drawRect(
            (double)(this.getX() + (float)this.getWidth() - 10.0F),
            (double)this.getYPos(),
            (double)(this.getX() + (float)this.getWidth()),
            (double)(this.getYPos() + (float)this.getHeight()),
            this.getColorSetting().getValue().getRGB()
         );
         GuiMainMenu.latoBoldFont15.drawString(this.getSetting().getName(), this.getX() + 3.0F, this.getYPos(), -1);
      } else {
         GL11.glShadeModel(7425);
         boolean flag = this.getColorSetting().mode == ColorMode.RAINBOW;
         if (flag) {
            this.hueOffset = HypixelPacketHandler.tickCycle;
         }

         Color color = this.drawHueSlider(3);
         Color color1 = this.drawSaturationBrightnessBox(color);
         int i1 = this.drawAlphaSlider(color1, 3);
         Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/misc/alpha.png"));
         Gui.drawModalRectWithCustomSizedTexture(
            (int)this.getX(),
            (int)this.getPickerY() + this.getHeight() - 14,
            0.0F,
            0.0F,
            this.getWidth(),
            5,
            (float)this.getWidth(),
            (float)(this.getHeight() * 2)
         );
         int j1 = color1.getRed();
         if (j1 >= 210) {
            j1 = 255;
         }

         int k = color1.getGreen();
         if (k >= 210) {
            k = 255;
         }

         int l = color1.getBlue();
         if (l >= 210) {
            l = 255;
         }

         Color color2 = this.brightnessOffset > 48 ? new Color(0, 0, 0, i1) : new Color(j1, k, l, i1);
         if (this.edited || flag) {
            this.getColorSetting().setValue(color2);
         }
         double d4 = (double)this.getX();
         double d5 = (double)(this.getPickerY() + (float)this.getHeight() - 14.0F);
         double d6 = (double)(this.getX() + (float)this.getWidth());
         double d3 = (double)(this.getPickerY() + (float)this.getHeight() - 9.0F);
         double d2 = d6;
         double d1 = d5;
         double d0 = d4;
         RenderUtil.drawRect(d0, d1, d2, d3, flag ? color.getRGB() : (this.edited ? color2 : this.getColorSetting().value).getRGB());
         boolean flag2 = Mouse.isButtonDown(0);
         if (flag2) {
            int i1x = (int)this.getX();
            int j1x = (int)this.getPickerY();
            int k1 = j - j1x;
            boolean flag3 = this.isMouseInRect(i1x, j1x, 7, this.getPickerHeight(), i, j);
            if (flag3) {
               this.hueOffset = k1;
               this.edited = true;
            }

            boolean flag4 = this.isMouseInRect(i1x + 11, j1x, this.getWidth() - 26, this.getPickerHeight(), i, j);
            if (flag4) {
               int l1 = i - i1x - 11;
               this.brightnessOffset = k1;
               this.saturationOffset = l1;
               this.edited = true;
            }

            if (this.isMouseInRect(i1x + this.getWidth() - 7, j1x, 7, this.getPickerHeight(), i, j)) {
               this.alphaOffset = k1;
               this.edited = true;
            }
         }
      }
   }

   private void seedFromColor(Color color) {
      this.edited = false;
      int h = this.getPickerHeight();
      int satRange = this.getWidth() - 26;
      if (h <= 0 || satRange <= 0) {
         return;
      }

      int tr = color.getRed();
      int tg = color.getGreen();
      int tb = color.getBlue();
      long best = Long.MAX_VALUE;

      for (int hue = 0; hue < h; hue++) {
         Color hueColor = getHueColor(hue);

         for (int sat = 0; sat < satRange; sat++) {
            Color satColor = this.applySaturation(hueColor, sat, 99 - 7 - 5 * 2 - 7);
            int br = sat < 10 ? 255 : satColor.getRed();
            int bg = sat < 10 ? 255 : satColor.getGreen();
            int bb = sat < 10 ? 255 : satColor.getBlue();

            for (int bright = 0; bright < h; bright++) {
               int r = 0;
               int g = 0;
               int b = 0;
               if (bright <= 48) {
                  r = br - bright * (br / h);
                  g = bg - bright * (bg / h);
                  b = bb - bright * (bb / h);
                  r = r >= 210 ? 255 : r;
                  g = g >= 210 ? 255 : g;
                  b = b >= 210 ? 255 : b;
               }

               long dr = r - tr;
               long dg = g - tg;
               long db = b - tb;
               long d = dr * dr + dg * dg + db * db;
               if (d < best) {
                  best = d;
                  this.hueOffset = hue;
                  this.saturationOffset = sat;
                  this.brightnessOffset = bright;
               }
            }
         }
      }

      int step = Math.max(1, 255 / h);
      this.alphaOffset = Math.max(0, Math.min(h - 1, Math.round((255 - color.getAlpha()) / (float)step)));
   }

   private int getColorModeIndex() {
      ColorMode[] acolormode = ColorMode.values();

      for (int i = 0; i < acolormode.length; i++) {
         if (acolormode[i] == this.getColorSetting().mode) {
            return i;
         }
      }

      return -1;
   }

   public Color drawHueSlider(int i) {
      RenderUtil.drawGradientRect(
         this.getX(),
         this.getPickerY(),
         this.getX() + 7.0F,
         this.getPickerY() + (float)(this.getPickerHeight() / 6),
         new Color(255, 0, 0).getRGB(),
         new Color(255, 0, 255).getRGB()
      );
      RenderUtil.drawGradientRect(
         this.getX(),
         this.getPickerY() + (float)(this.getPickerHeight() / 6),
         this.getX() + 7.0F,
         this.getPickerY() + (float)(this.getPickerHeight() / 6 * 2),
         new Color(255, 0, 255).getRGB(),
         new Color(0, 0, 255).getRGB()
      );
      RenderUtil.drawGradientRect(
         this.getX(),
         this.getPickerY() + (float)(this.getPickerHeight() / 6 * 2),
         this.getX() + 7.0F,
         this.getPickerY() + (float)(this.getPickerHeight() / 6 * 3),
         new Color(0, 0, 255).getRGB(),
         new Color(0, 255, 255).getRGB()
      );
      RenderUtil.drawGradientRect(
         this.getX(),
         this.getPickerY() + (float)(this.getPickerHeight() / 6 * 3),
         this.getX() + 7.0F,
         this.getPickerY() + (float)(this.getPickerHeight() / 6 * 4),
         new Color(0, 255, 255).getRGB(),
         new Color(0, 255, 0).getRGB()
      );
      RenderUtil.drawGradientRect(
         this.getX(),
         this.getPickerY() + (float)(this.getPickerHeight() / 6 * 4),
         this.getX() + 7.0F,
         this.getPickerY() + (float)(this.getPickerHeight() / 6 * 5),
         new Color(0, 255, 0).getRGB(),
         new Color(255, 255, 0).getRGB()
      );
      RenderUtil.drawGradientRect(
         this.getX(),
         this.getPickerY() + (float)(this.getPickerHeight() / 6 * 5),
         this.getX() + 7.0F,
         this.getPickerY() + (float)this.getPickerHeight(),
         new Color(255, 255, 0).getRGB(),
         new Color(255, 0, 0).getRGB()
      );
      double d1 = (double)this.getX();
      float f2 = this.getPickerY() + (float)this.hueOffset + (float)i;
      float f1 = this.getPickerY() + (float)this.getPickerHeight();
      float f = f2;
      double d0 = d1;
      RenderUtil.drawRect(
         d0,
         f > f1 ? (double)(this.getPickerY() + (float)this.getPickerHeight() - (float)i) : (double)(this.getPickerY() + (float)this.hueOffset),
         (double)(this.getX() + 7.0F),
         (double)(this.getPickerY() + (float)this.hueOffset + (float)i),
         this.handleColor
      );
      return getHueColor(this.hueOffset);
   }

   public Color drawSaturationBrightnessBox(Color color) {
      RenderUtil.drawFourColorRect(
         this.getX() + 7.0F + 5.0F,
         this.getPickerY(),
         this.getX() + (float)this.getWidth() - 7.0F - 5.0F,
         this.getPickerY() + (float)this.getPickerHeight(),
         Color.white.getRGB(),
         color.getRGB(),
         Color.white.getRGB(),
         color.getRGB()
      );
      RenderUtil.drawFourColorRect(
         this.getX() + 7.0F + 5.0F,
         this.getPickerY(),
         this.getX() + (float)this.getWidth() - 7.0F - 5.0F,
         this.getPickerY() + (float)this.getPickerHeight(),
         new Color(0, 0, 0, 0).getRGB(),
         new Color(0, 0, 0, 0).getRGB(),
         Color.black.getRGB(),
         Color.black.getRGB()
      );
      RenderUtil.drawRect(
         (double)(this.getX() + 12.0F + (float)this.saturationOffset - 1.0F),
         (double)(this.getPickerY() + (float)this.brightnessOffset - 1.0F),
         (double)(this.getX() + 13.0F + (float)this.saturationOffset),
         (double)(this.getPickerY() + (float)this.brightnessOffset + 1.0F),
         Color.black.getRGB()
      );
      Color color1 = this.applySaturation(color, this.saturationOffset, 99 - 7 - 5 * 2 - 7);
      Color color1x = this.applyBrightness(color1, this.brightnessOffset);
      RenderUtil.drawRect(
         (double)(this.getX() + 12.0F),
         (double)this.getYPos(),
         (double)(this.getX() + (float)this.getWidth() - 12.0F),
         (double)(this.getYPos() + 9.0F),
         new Color(50, 50, 50, 200).getRGB()
      );
      GuiMainMenu.latoBoldFont15
         .drawString(
            "Done",
            this.getX() + (float)(this.getWidth() / 2) - (float)(GuiMainMenu.latoBoldFont15.getStringWidth("Done", true) / 2),
            this.getYPos(),
            new Color(200, 200, 200, 255).getRGB()
         );
      return new Color(color1x.getRed(), color1x.getGreen(), color1x.getBlue());
   }

   public int drawAlphaSlider(Color color, int i) {
      Object object = null;
      Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/misc/alpha.png"));
      Gui.drawModalRectWithCustomSizedTexture(
         (int)this.getX() + this.getWidth() - 7,
         (int)this.getPickerY(),
         0.0F,
         0.0F,
         7,
         this.getPickerHeight(),
         (float)this.getWidth(),
         (float)(this.getPickerHeight() * 2)
      );
      RenderUtil.drawGradientRect(
         this.getX() + (float)this.getWidth() - 7.0F,
         this.getPickerY(),
         this.getX() + (float)this.getWidth(),
         this.getPickerY() + (float)this.getPickerHeight(),
         color.getRGB(),
         new Color(color.getRed(), color.getGreen(), color.getBlue(), 0).getRGB()
      );
      RenderUtil.drawRect(
         (double)(this.getX() + (float)this.getWidth() - 7.0F),
         this.getPickerY() + (float)this.alphaOffset + (float)i > this.getPickerY() + (float)this.getPickerHeight()
            ? (double)(this.getPickerY() + (float)this.getPickerHeight() - (float)i)
            : (double)(this.getPickerY() + (float)this.alphaOffset),
         (double)(this.getX() + (float)this.getWidth()),
         (double)(this.getPickerY() + (float)this.alphaOffset + (float)i),
         this.handleColor
      );
      return this.getAlphaFromOffset(this.alphaOffset);
   }

   private int getAlphaFromOffset(int i) {
      return 255 - i * (255 / this.getPickerHeight());
   }

   private Color applySaturation(Color color, int i, int j) {
      int i1 = j - i;
      int j1 = color.getRed();
      int k = color.getGreen();
      int l = color.getBlue();
      int i1x = (255 - j1) / j;
      int j1x = (255 - k) / j;
      int k1 = (255 - l) / j;
      return new Color(j1 + i1 * i1x, k + i1 * j1x, l + i1 * k1);
   }

   private Color applyBrightness(Color color, int i) {
      int i1 = color.getRed();
      int j = color.getGreen();
      int k = color.getBlue();
      if (this.saturationOffset < 10) {
         k = 255;
         j = 255;
         i1 = 255;
      }

      int l = (255 - (255 - i1)) / this.getPickerHeight();
      int i1x = (255 - (255 - j)) / this.getPickerHeight();
      int j1 = (255 - (255 - k)) / this.getPickerHeight();
      return new Color(i1 - i * l, j - i * i1x, k - i * j1);
   }

   public static Color getHueColor(int i) {
      int i1 = 54 - i;
      Color color = Color.red;
      if (i1 > 0 && i1 <= 9) {
         color = new Color(255, i1 * 28, 0);
      } else if (i1 > 9 && i1 <= 18) {
         int j1 = i1 - 9;
         color = new Color(255 - j1 * 28, 255, 0);
      } else if (i1 > 18 && i1 <= 27) {
         int i1x = i1 - 18;
         color = new Color(0, 255, i1x * 28);
      } else if (i1 > 27 && i1 <= 36) {
         int l = i1 - 27;
         color = new Color(0, 255 - l * 28, 255);
      } else if (i1 > 36 && i1 <= 45) {
         int k = i1 - 36;
         color = new Color(k * 28, 0, 255);
      } else if (i1 > 45 && i1 <= 54) {
         int j = i1 - 45;
         color = new Color(255, 0, 255 - j * 28);
      }

      return color;
   }

   private ColorSetting getColorSetting() {
      return (ColorSetting)this.getSetting();
   }
}

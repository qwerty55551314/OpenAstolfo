package net.minecraft.client.gui;

import astolfo.Astolfo;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.ARBShaderObjects;

public class GuiTextField extends Gui {
   private final int id;
   private final FontRenderer fontRendererInstance;
   public int xPosition;
   public int yPosition;
   private final int width;
   private final int height;
   private String text = "";
   private int maxStringLength = 32;
   private int cursorCounter;
   private boolean enableBackgroundDrawing = true;
   private boolean canLoseFocus = true;
   private boolean isFocused;
   private boolean isEnabled = true;
   private int lineScrollOffset;
   private int cursorPosition;
   private int selectionEnd;
   private int enabledColor = 14737632;
   private int disabledColor = 7368816;
   private boolean visible = true;
   private GuiPageButtonList.GuiResponder field_175210_x;
   private Predicate<String> validator = Predicates.alwaysTrue();
   public boolean useCustomAlpha;
   public int customAlpha;
   private static boolean shaderInitialized;
   private static int outlineShaderProgram;
   private static int colorUniformLocation;
   public float outlineAlpha = 0.1F;

   public GuiTextField(int componentId, FontRenderer fontrendererObj, int x, int y, int par5Width, int par6Height) {
      this.id = componentId;
      this.fontRendererInstance = fontrendererObj;
      this.xPosition = x;
      this.yPosition = y;
      this.width = par5Width;
      this.height = par6Height;
   }

   public void func_175207_a(GuiPageButtonList.GuiResponder guiResponder) {
      this.field_175210_x = guiResponder;
   }

   public void updateCursorCounter() {
      this.cursorCounter++;
   }

   public void setText(String string) {
      if (this.validator.apply(string)) {
         if (string.length() > this.maxStringLength) {
            this.text = string.substring(0, this.maxStringLength);
         } else {
            this.text = string;
         }

         this.setCursorPositionEnd();
      }
   }

   public String getText() {
      return this.text;
   }

   public String getSelectedText() {
      int i = this.cursorPosition < this.selectionEnd ? this.cursorPosition : this.selectionEnd;
      int j = this.cursorPosition < this.selectionEnd ? this.selectionEnd : this.cursorPosition;
      return this.text.substring(i, j);
   }

   public void setValidator(Predicate<String> theValidator) {
      this.validator = theValidator;
   }

   public void writeText(String string) {
      String s = "";
      String s1 = ChatAllowedCharacters.filterAllowedCharacters(string);
      int i = this.cursorPosition < this.selectionEnd ? this.cursorPosition : this.selectionEnd;
      int j = this.cursorPosition < this.selectionEnd ? this.selectionEnd : this.cursorPosition;
      int k = this.maxStringLength - this.text.length() - (i - j);
      int i1 = this.text.length();
      if (i1 > 0) {
         s = s + this.text.substring(0, i);
      }

      int l;
      if (k < s1.length()) {
         s = s + s1.substring(0, k);
         l = k;
      } else {
         s = s + s1;
         l = s1.length();
      }

      int j1 = this.text.length();
      if (j1 > 0 && j < this.text.length()) {
         s = s + this.text.substring(j);
      }

      if (this.validator.apply(s)) {
         this.text = s;
         this.moveCursorBy(i - this.selectionEnd + l);
         GuiPageButtonList.GuiResponder guipagebuttonlist$guiresponder = this.field_175210_x;
         if (guipagebuttonlist$guiresponder != null) {
            this.field_175210_x.func_175319_a(this.id, this.text);
         }
      }
   }

   public void deleteWords(int i) {
      if (this.text.length() != 0) {
         if (this.selectionEnd != this.cursorPosition) {
            this.writeText("");
         } else {
            this.deleteFromCursor(this.getNthWordFromCursor(i) - this.cursorPosition);
         }
      }
   }

   public void deleteFromCursor(int i) {
      int k = this.text.length();
      if (k != 0) {
         int l = this.selectionEnd;
         if (l != this.cursorPosition) {
            this.writeText("");
         } else {
            boolean flag = i < 0;
            int i1 = flag ? this.cursorPosition + i : this.cursorPosition;
            int j = flag ? this.cursorPosition : this.cursorPosition + i;
            String s = "";
            if (i1 >= 0) {
               s = this.text.substring(0, i1);
            }

            if (j < this.text.length()) {
               s = s + this.text.substring(j);
            }

            boolean flag1 = this.validator.apply(s);
            if (flag1) {
               this.text = s;
               if (flag) {
                  this.moveCursorBy(i);
               }

               GuiPageButtonList.GuiResponder guipagebuttonlist$guiresponder = this.field_175210_x;
               if (guipagebuttonlist$guiresponder != null) {
                  this.field_175210_x.func_175319_a(this.id, this.text);
               }
            }
         }
      }
   }

   public int getId() {
      return this.id;
   }

   public int getNthWordFromCursor(int i) {
      return this.getNthWordFromPos(i, this.getCursorPosition());
   }

   public int getNthWordFromPos(int i, int j) {
      return this.func_146197_a(i, j, true);
   }

   public int func_146197_a(int i, int j, boolean bl) {
      int i1 = j;
      boolean flag = i < 0;
      int j1 = Math.abs(i);

      for (int k = 0; k < j1; k++) {
         if (!flag) {
            int l = this.text.length();
            i1 = this.text.indexOf(32, i1);
            if (i1 == -1) {
               i1 = l;
            } else {
               while (bl && i1 < l && this.text.charAt(i1) == ' ') {
                  i1++;
               }
            }
         } else {
            while (bl && i1 > 0 && this.text.charAt(i1 - 1) == ' ') {
               i1--;
            }

            while (i1 > 0 && this.text.charAt(i1 - 1) != ' ') {
               i1--;
            }
         }
      }

      return i1;
   }

   public void moveCursorBy(int i) {
      this.setCursorPosition(this.selectionEnd + i);
   }

   public void setCursorPosition(int i) {
      this.cursorPosition = i;
      int i1 = this.text.length();
      this.cursorPosition = MathHelper.clamp_int(this.cursorPosition, 0, i1);
      this.setSelectionPos(this.cursorPosition);
   }

   public void setCursorPositionZero() {
      this.setCursorPosition(0);
   }

   public void setCursorPositionEnd() {
      this.setCursorPosition(this.text.length());
   }

   public boolean textboxKeyTyped(char c, int i) {
      boolean flag = this.isFocused;
      if (!flag) {
         return false;
      } else {
         boolean flag1 = GuiScreen.isKeyComboCtrlA(i);
         if (flag1) {
            this.setCursorPositionEnd();
            this.setSelectionPos(0);
            return true;
         } else {
            boolean flag2 = GuiScreen.isKeyComboCtrlC(i);
            if (flag2) {
               GuiScreen.setClipboardString(this.getSelectedText());
               return true;
            } else {
               boolean flag3 = GuiScreen.isKeyComboCtrlV(i);
               if (flag3) {
                  if (this.isEnabled) {
                     this.writeText(GuiScreen.getClipboardString());
                  }

                  return true;
               } else {
                  boolean flag4 = GuiScreen.isKeyComboCtrlX(i);
                  if (flag4) {
                     GuiScreen.setClipboardString(this.getSelectedText());
                     if (this.isEnabled) {
                        this.writeText("");
                     }

                     return true;
                  } else {
                     switch (i) {
                        case 14:
                           if (GuiScreen.isCtrlKeyDown()) {
                              if (this.isEnabled) {
                                 this.deleteWords(-1);
                              }
                           } else if (this.isEnabled) {
                              this.deleteFromCursor(-1);
                           }

                           return true;
                        case 199:
                           if (GuiScreen.isShiftKeyDown()) {
                              this.setSelectionPos(0);
                           } else {
                              this.setCursorPositionZero();
                           }

                           return true;
                        case 203:
                           if (GuiScreen.isShiftKeyDown()) {
                              if (GuiScreen.isCtrlKeyDown()) {
                                 this.setSelectionPos(this.getNthWordFromPos(-1, this.getSelectionEnd()));
                              } else {
                                 this.setSelectionPos(this.getSelectionEnd() - 1);
                              }
                           } else if (GuiScreen.isCtrlKeyDown()) {
                              this.setCursorPosition(this.getNthWordFromCursor(-1));
                           } else {
                              this.moveCursorBy(-1);
                           }

                           return true;
                        case 205:
                           if (GuiScreen.isShiftKeyDown()) {
                              if (GuiScreen.isCtrlKeyDown()) {
                                 this.setSelectionPos(this.getNthWordFromPos(1, this.getSelectionEnd()));
                              } else {
                                 this.setSelectionPos(this.getSelectionEnd() + 1);
                              }
                           } else if (GuiScreen.isCtrlKeyDown()) {
                              this.setCursorPosition(this.getNthWordFromCursor(1));
                           } else {
                              this.moveCursorBy(1);
                           }

                           return true;
                        case 207:
                           if (GuiScreen.isShiftKeyDown()) {
                              this.setSelectionPos(this.text.length());
                           } else {
                              this.setCursorPositionEnd();
                           }

                           return true;
                        case 211:
                           if (GuiScreen.isCtrlKeyDown()) {
                              if (this.isEnabled) {
                                 this.deleteWords(1);
                              }
                           } else if (this.isEnabled) {
                              this.deleteFromCursor(1);
                           }

                           return true;
                        default:
                           boolean flag5 = ChatAllowedCharacters.isAllowedCharacter(c);
                           if (flag5) {
                              if (this.isEnabled) {
                                 this.writeText(Character.toString(c));
                              }

                              return true;
                           } else {
                              return false;
                           }
                     }
                  }
               }
            }
         }
      }
   }

   public void mouseClicked(int i, int j, int k) {
      boolean flag3;
      label32: {
         int j1 = this.xPosition;
         if (i >= j1) {
            int k1 = this.xPosition + this.width;
            if (i < k1) {
               int l = this.yPosition;
               if (j >= l && j < this.yPosition + this.height) {
                  flag3 = true;
                  break label32;
               }
            }
         }

         flag3 = false;
      }

      boolean flag = flag3;
      boolean flag1 = this.canLoseFocus;
      if (flag1) {
         this.setFocused(flag);
      }

      boolean flag2 = this.isFocused;
      if (flag2 && flag && k == 0) {
         int i1 = i - this.xPosition;
         if (this.enableBackgroundDrawing) {
            i1 -= 4;
         }

         String s = this.fontRendererInstance.trimStringToWidth(this.text.substring(this.lineScrollOffset), this.getWidth());
         this.setCursorPosition(this.fontRendererInstance.trimStringToWidth(s, i1).length() + this.lineScrollOffset);
      }
   }

   public void drawTextBox() {
      boolean flag2 = this.getVisible();
      if (flag2) {
         boolean flag3 = shaderInitialized;
         if (!flag3) {
            int i = ARBShaderObjects.glCreateShaderObjectARB(35632);
            ARBShaderObjects.glShaderSourceARB(i, "#version 110\nuniform vec4 color;void main() { gl_FragColor = color;}");
            ARBShaderObjects.glCompileShaderARB(i);
            System.err.println(ARBShaderObjects.glGetInfoLogARB(i, 200));
            ARBShaderObjects.glAttachObjectARB(outlineShaderProgram = ARBShaderObjects.glCreateProgramObjectARB(), i);
            ARBShaderObjects.glLinkProgramARB(outlineShaderProgram);
            ARBShaderObjects.glValidateProgramARB(outlineShaderProgram);
            shaderInitialized = true;
         }

         boolean flag4 = this.getEnableBackgroundDrawing();
         if (flag4) {
            float f3 = Minecraft.getMinecraft().theWorld == null ? 0.1F : 0.01F;
            if (this.isFocused() && this.outlineAlpha < 1.0F) {
               this.outlineAlpha += f3;
            } else if (this.outlineAlpha > 0.5F && !this.isFocused) {
               this.outlineAlpha -= f3;
            }

            if (this.outlineAlpha < 0.5F) {
               this.outlineAlpha = 0.5F;
            } else if (this.outlineAlpha > 1.0F) {
               this.outlineAlpha = 1.0F;
            }

            ARBShaderObjects.glUseProgramObjectARB(outlineShaderProgram);
            Astolfo astolfo = Astolfo.INSTANCE;
            float f = (float)astolfo.getColor().getRed() / 255.0F;
            Astolfo astolfo1 = Astolfo.INSTANCE;
            float f1 = (float)astolfo1.getColor().getGreen() / 255.0F;
            Astolfo astolfo2 = Astolfo.INSTANCE;
            float f2 = (float)astolfo2.getColor().getBlue() / 255.0F;
            if (this.isFocused() && this.isEnabled) {
               ARBShaderObjects.glUniform4fARB(colorUniformLocation, f, f1, f2, this.outlineAlpha);
            } else if (this.isEnabled) {
               ARBShaderObjects.glUniform4fARB(colorUniformLocation, 0.5F, 0.5F, 0.5F, this.outlineAlpha);
            } else {
               ARBShaderObjects.glUniform4fARB(colorUniformLocation, 0.1F, 0.1F, 0.1F, this.outlineAlpha);
            }

            colorUniformLocation = ARBShaderObjects.glGetUniformLocationARB(outlineShaderProgram, "color");
            drawRect(
               this.xPosition,
               this.yPosition + this.height - 1,
               this.xPosition + this.width,
               this.yPosition + this.height,
               this.isFocused() ? -65281 : -13421773
            );
            ARBShaderObjects.glUseProgramObjectARB(0);
         }

         boolean flag;
         int k2;
         int l2;
         int i3;
         String s1;
         boolean flag8;
         label123: {
            boolean flag5 = this.useCustomAlpha;
            k2 = flag5 ? new Color(224, 224, 224, this.customAlpha).getRGB() : (this.isEnabled ? this.enabledColor : this.disabledColor);
            l2 = this.cursorPosition - this.lineScrollOffset;
            i3 = this.selectionEnd - this.lineScrollOffset;
            s1 = this.fontRendererInstance.trimStringToWidth(this.text.substring(this.lineScrollOffset), this.getWidth());
            flag = l2 >= 0 && l2 <= s1.length();
            boolean flag6 = this.isFocused;
            if (flag6) {
               int k1 = this.cursorCounter / 6 % 2;
               if (k1 == 0 && flag) {
                  flag8 = true;
                  break label123;
               }
            }

            flag8 = false;
         }

         boolean flag1 = flag8;
         int j = this.enableBackgroundDrawing ? this.xPosition + 4 : this.xPosition;
         int k = this.enableBackgroundDrawing ? this.yPosition + (this.height - 8) / 2 : this.yPosition;
         int l = j;
         if (i3 > s1.length()) {
            i3 = s1.length();
         }

         int l1 = s1.length();
         if (l1 > 0) {
            String s = flag ? s1.substring(0, l2) : s1;
            l = this.fontRendererInstance.drawStringWithShadow(s, (float)j, (float)k, k2);
         }

         int i2 = this.cursorPosition;
         boolean flag7 = i2 < this.text.length() || this.text.length() >= this.getMaxStringLength();
         int i1 = l;
         if (!flag) {
            i1 = l2 > 0 ? j + this.width : j;
         } else if (flag7) {
            i1 = l - 1;
            l--;
         }

         int j2 = s1.length();
         if (j2 > 0 && flag && l2 < s1.length()) {
            l = this.fontRendererInstance.drawStringWithShadow(s1.substring(l2), (float)l, (float)k, k2);
         }

         if (flag1) {
            if (flag7) {
               Gui.drawRect(i1, k - 1, i1 + 1, k + 1 + this.fontRendererInstance.FONT_HEIGHT, -3092272);
            } else {
               this.fontRendererInstance.drawStringWithShadow("_", (float)i1, (float)k, k2);
            }
         }

         if (i3 != l2) {
            int j1 = j + this.fontRendererInstance.getStringWidth(s1.substring(0, i3));
            this.drawCursorVertical(i1, k - 1, j1 - 1, k + 1 + this.fontRendererInstance.FONT_HEIGHT);
         }
      }
   }

   private void drawCursorVertical(int i, int j, int k, int l) {
      if (i < k) {
         int i1 = i;
         i = k;
         k = i1;
      }

      if (j < l) {
         int l1 = j;
         j = l;
         l = l1;
      }

      int j1 = this.xPosition + this.width;
      if (k > j1) {
         k = this.xPosition + this.width;
      }

      int k1 = this.xPosition + this.width;
      if (i > k1) {
         i = this.xPosition + this.width;
      }

      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer worldrenderer = tessellator.getWorldRenderer();
      GlStateManager.color(0.0F, 0.0F, 255.0F, 255.0F);
      GlStateManager.disableTexture2D();
      GlStateManager.enableColorLogic();
      GlStateManager.colorLogicOp(5387);
      worldrenderer.begin(7, DefaultVertexFormats.POSITION);
      worldrenderer.pos((double)i, (double)l, 0.0).endVertex();
      worldrenderer.pos((double)k, (double)l, 0.0).endVertex();
      worldrenderer.pos((double)k, (double)j, 0.0).endVertex();
      worldrenderer.pos((double)i, (double)j, 0.0).endVertex();
      tessellator.draw();
      GlStateManager.disableColorLogic();
      GlStateManager.enableTexture2D();
   }

   public void setMaxStringLength(int i) {
      this.maxStringLength = i;
      if (this.text.length() > i) {
         this.text = this.text.substring(0, i);
      }
   }

   public int getMaxStringLength() {
      return this.maxStringLength;
   }

   public int getCursorPosition() {
      return this.cursorPosition;
   }

   public boolean getEnableBackgroundDrawing() {
      return this.enableBackgroundDrawing;
   }

   public void setEnableBackgroundDrawing(boolean bl) {
      this.enableBackgroundDrawing = bl;
   }

   public void setTextColor(int i) {
      this.enabledColor = i;
   }

   public void setDisabledTextColour(int i) {
      this.disabledColor = i;
   }

   public void setFocused(boolean bl) {
      if (bl && !this.isFocused) {
         this.cursorCounter = 0;
      }

      this.isFocused = bl;
   }

   public boolean isFocused() {
      return this.isFocused;
   }

   public void setEnabled(boolean bl) {
      this.isEnabled = bl;
   }

   public int getSelectionEnd() {
      return this.selectionEnd;
   }

   public int getWidth() {
      return this.getEnableBackgroundDrawing() ? this.width - 8 : this.width;
   }

   public void setSelectionPos(int i) {
      int i1 = this.text.length();
      if (i > i1) {
         i = i1;
      }

      if (i < 0) {
         i = 0;
      }

      this.selectionEnd = i;
      if (this.fontRendererInstance != null) {
         int l = this.lineScrollOffset;
         if (l > i1) {
            this.lineScrollOffset = i1;
         }

         int j = this.getWidth();
         String s = this.fontRendererInstance.trimStringToWidth(this.text.substring(this.lineScrollOffset), j);
         int k = s.length() + this.lineScrollOffset;
         int i1x = this.lineScrollOffset;
         if (i == i1x) {
            this.lineScrollOffset = this.lineScrollOffset - this.fontRendererInstance.trimStringToWidth(this.text, j, true).length();
         }

         if (i > k) {
            this.lineScrollOffset += i - k;
         } else if (i <= this.lineScrollOffset) {
            this.lineScrollOffset = this.lineScrollOffset - (this.lineScrollOffset - i);
         }

         this.lineScrollOffset = MathHelper.clamp_int(this.lineScrollOffset, 0, i1);
      }
   }

   public void setCanLoseFocus(boolean bl) {
      this.canLoseFocus = bl;
   }

   public boolean getVisible() {
      return this.visible;
   }

   public void setVisible(boolean bl) {
      this.visible = bl;
   }
}

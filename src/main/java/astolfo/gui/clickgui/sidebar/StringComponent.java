package astolfo.gui.clickgui.sidebar;

import astolfo.Astolfo;
import astolfo.render.RenderUtil;
import astolfo.setting.Setting;
import astolfo.setting.StringSetting;
import java.awt.Color;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.StringUtils;
import org.lwjgl.input.Mouse;

public class StringComponent extends SidebarSettingComponent {
   private boolean focused;

   public StringComponent(ModuleButton moduleButton, Setting setting, float f, float g, int i, int j) {
      super(moduleButton, setting, f, g, i, j);
   }

   @Override
   public void onClick(int i) {
      if (i == 0 && this.isVisible()) {
         this.focused = true;
      }
   }

   @Override
   public void render(int i, int j) {
      boolean flag = Mouse.isButtonDown(0);
      if (flag && !this.isHovered(i, j)) {
         this.focused = false;
      }

      double d4 = (double)this.getX();
      double d5 = (double)(this.getYPos() + (float)this.getHeight() + 1.0F);
      double d6 = (double)(this.getX() + (float)this.getWidth());
      double d7 = (double)(this.getYPos() + (float)this.getHeight() + 2.0F);
      boolean flag1 = this.focused;
      double d3 = d7;
      double d2 = d6;
      double d1 = d5;
      double d0 = d4;
      int i1;
      if (flag1) {
         Astolfo astolfo = client;
         i1 = astolfo.getSidebarGui().getPulseColor();
      } else {
         i1 = Color.WHITE.getRGB();
      }

      RenderUtil.drawRect(d0, d1, d2, d3, i1);
      client.getCustomFontRenderer().drawStringWithShadow(this.getStringSetting().getValue() + "...", this.getX(), this.getYPos() + 1.0F, -1);
   }

   @Override
   public void onKeyTyped(char c, int i) {
      if (this.focused) {
         StringSetting stringsetting = this.getStringSetting();
         boolean flag = GuiScreen.isKeyComboCtrlV(i);
         if (flag) {
            stringsetting.setValue(stringsetting.getValue() + GuiScreen.getClipboardString());
         } else {
            switch (i) {
               case 14:
                  if (!StringUtils.isNullOrEmpty(stringsetting.getValue())) {
                     stringsetting.setValue(stringsetting.getValue().substring(0, stringsetting.getValue().length() - 1));
                  }
                  break;
               default:
                  if (ChatAllowedCharacters.isAllowedCharacter(c)) {
                     stringsetting.setValue(stringsetting.getValue() + c);
                  }
            }

            super.onKeyTyped(c, i);
         }
      }
   }

   public boolean isFocused() {
      return this.focused;
   }

   public StringSetting getStringSetting() {
      return (StringSetting)this.getSetting();
   }
}

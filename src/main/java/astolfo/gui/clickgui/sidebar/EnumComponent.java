/*
 * Decompiled with CFR 0.152.
 */
package astolfo.gui.clickgui.sidebar;

import astolfo.Astolfo;
import astolfo.gui.animation.Animation;
import astolfo.gui.animation.Easing;
import astolfo.gui.animation.TimedAnimation;
import astolfo.gui.clickgui.sidebar.DropdownComponent;
import astolfo.gui.clickgui.sidebar.ModuleButton;
import astolfo.render.RenderUtil;
import astolfo.setting.EnumSetting;
import astolfo.setting.Setting;
import java.awt.Color;
import org.lwjgl.input.Mouse;

public class EnumComponent
extends DropdownComponent {
    public Animation dropdownAnimation = new TimedAnimation().setDuration(200).setEasing(Easing.LINEAR);
    private float dropdownHeight;
    private int[] optionAlphas = new int[128];

    public EnumComponent(ModuleButton moduleButton, Setting setting, float f, float g, int i, int j2) {
        super(moduleButton, setting, f, g, i, j2);
    }

    @Override
    public void render(int i, int j2) {
        Astolfo astolfo;
        float f = this.getX() + (float)this.getWidth() / 1.7f;
        RenderUtil.drawRect(f - 1.0f, this.getYPos() - 2.0f, this.getX() + (float)this.getWidth() + 1.0f, this.getYPos() + 9.0f, new Color(34, 36, 43, this.fadeAlpha).getRGB());
        RenderUtil.drawRect(f, this.getYPos() - 1.0f, this.getX() + (float)this.getWidth(), this.getYPos() + 8.0f, new Color(29, 31, 37, this.fadeAlpha).getRGB());
        if (this.dropdownHeight > 0.0f && (astolfo = client).getSidebarGui().getOpenDropdown() != this) {
            RenderUtil.drawRect(f - 0.5f, this.getYPos() + 8.0f, this.getX() + (float)this.getWidth() + 0.5f, this.getYPos() + this.dropdownHeight + 8.5f, new Color(34, 36, 43, this.fadeAlpha).getRGB());
            RenderUtil.drawRect(f, this.getYPos() + 8.0f, this.getX() + (float)this.getWidth(), this.getYPos() + this.dropdownHeight + 8.0f, new Color(29, 31, 37, this.fadeAlpha).getRGB());
            this.optionAlphas = new int[128];
            if (!this.dropdownAnimation.isRunning()) {
                this.dropdownAnimation.backwards().start();
            }
            this.dropdownHeight = (float)this.dropdownAnimation.getValue(0.0, this.O_int());
        }
        if (this.fadeAlpha > 40) {
            client.getCustomFontRenderer().drawString(this.getEnumSetting().getName(), this.getX(), this.getYPos() - 2.0f, new Color(255, 255, 255, this.fadeAlpha).getRGB());
            client.getCustomFontRenderer().drawString(((Enum)this.getEnumSetting().getValue()).name(), f + (float)this.getWidth() / 4.5f - (float)client.getCustomFontRenderer().getStringWidth(((Enum)this.getEnumSetting().getValue()).name()) / 2.0f, this.getYPos() - 2.0f, new Color(136, 141, 157, this.fadeAlpha).getRGB());
        }
    }

    @Override
    public void renderDropdown(int i, int j2) {
        float f = this.getX() + (float)this.getWidth() / 1.7f;
        Enum[] enumArray = (Enum[])this.getEnumSetting().getValue().getClass().getEnumConstants();
        RenderUtil.drawRect(f - 1.0f, this.getYPos() + 8.0f, this.getX() + (float)this.getWidth() + 1.0f, this.getYPos() + this.dropdownHeight + 9.0f, new Color(34, 36, 43, this.fadeAlpha).getRGB());
        RenderUtil.drawRect(f, this.getYPos() + 8.0f, this.getX() + (float)this.getWidth(), this.getYPos() + this.dropdownHeight + 8.0f, new Color(29, 31, 37, this.fadeAlpha).getRGB());
        Astolfo astolfo = client;
        int n = astolfo.getSidebarGui().getAnimationStep();
        int n2 = 13;
        for (int k = 0; k < enumArray.length; ++k) {
            Enum enum_ = enumArray[k];
            int n3 = this.optionAlphas[k];
            if (n3 > 30) {
                client.getCustomFontRenderer().drawString(enum_.name(), f + (float)this.getWidth() / 4.5f - (float)client.getCustomFontRenderer().getStringWidth(enum_.name()) / 2.0f, this.getYPos() + (float)n2 - 3.0f, new Color(136, 141, 157, this.optionAlphas[k]).getRGB());
            }
            if (this.isOptionHovered(i, j2, (int)(this.getYPos() + (float)n2)) && Mouse.isButtonDown(0)) {
                this.getEnumSetting().setValue(enum_);
                Astolfo astolfo2 = client;
                astolfo2.getSidebarGui().setOpenDropdown(null);
            }
            if (this.dropdownHeight >= (float)n2 && this.optionAlphas[k] < 255) {
                this.optionAlphas[k] = (int)this.dropdownAnimation.getValue(0.0, 255.0);
            }
            n2 += 13;
        }
        EnumComponent enumComponent = this;
        if (!enumComponent.dropdownAnimation.isRunning()) {
            this.dropdownAnimation.forwards().start();
        }
        this.dropdownHeight = (float)this.dropdownAnimation.getValue(0.0, this.O_int());
        super.renderDropdown(i, j2);
    }

    public boolean isOptionHovered(int i, int j2, int k) {
        int n;
        int n2;
        float f;
        float f2;
        float f3 = (float)i - (this.getX() + (float)this.getWidth() / 1.7f);
        float f4 = f3 == 0.0f ? 0 : (f3 > 0.0f ? 1 : -1);
        return f4 >= 0 && (f2 = (f = (float)i - (this.getX() + (float)this.getWidth())) == 0.0f ? 0 : (f < 0.0f ? -1 : 1)) <= 0 && (n2 = j2) >= (n = k) && j2 <= k + this.getHeight();
    }

    private int O_int() {
        Enum[] enumArray = (Enum[])this.getEnumSetting().getValue().getClass().getEnumConstants();
        int n = 0;
        for (Enum enum_ : enumArray) {
            n += 13;
        }
        return n;
    }

    public EnumSetting getEnumSetting() {
        return (EnumSetting)this.getSetting();
    }
}


/*
 * Decompiled with CFR 0.152.
 */
package astolfo.gui.clickgui.defaultgui;

import astolfo.gui.clickgui.SettingComponent;
import astolfo.gui.component.Container;
import astolfo.setting.EnumSetting;
import astolfo.setting.Setting;
import net.minecraft.client.gui.GuiMainMenu;

public class EnumComponent
extends SettingComponent {
    private static final long d = -7993921108442297140L;

    public EnumComponent(Container container, Setting setting, int i, int j2) {
        super(container, setting, i, j2);
    }

    @Override
    public void render(int i, int j2) {
        GuiMainMenu.latoBoldFont15.drawString(this.getEnumSetting().getName().replace("-", " "), this.getX() + 3.0f, this.getYPos(), -1);
        GuiMainMenu.latoBoldFont15.drawString(String.valueOf(this.getEnumSetting().getValue()), this.getX() + (float)this.getWidth() - (float)GuiMainMenu.latoBoldFont15.getStringWidth(String.valueOf(this.getEnumSetting().getValue()), true), this.getYPos(), (int)d);
    }

    @Override
    public void onClick(int i) {
        EnumComponent enumComponent = this;
        if (enumComponent.getParentContainer().isExpanded()) {
            Enum[] enumArray = (Enum[])this.getEnumSetting().getValue().getClass().getEnumConstants();
            int n = this.getValueIndex();
            int n2 = i;
            int n3 = n;
            n = n3 + (n2 == 0 ? 1 : -1);
            int n4 = n;
            if (n4 >= enumArray.length || n < 0) {
                n = 0;
            }
            this.getEnumSetting().setValue(enumArray[n]);
        }
    }

    private EnumSetting getEnumSetting() {
        return (EnumSetting)this.getSetting();
    }

    private int getValueIndex() {
        Enum[] enumArray = (Enum[])this.getEnumSetting().getValue().getClass().getEnumConstants();
        for (int i = 0; i < enumArray.length; ++i) {
            if (enumArray[i] != this.getEnumSetting().getValue()) continue;
            return i;
        }
        return -1;
    }
}


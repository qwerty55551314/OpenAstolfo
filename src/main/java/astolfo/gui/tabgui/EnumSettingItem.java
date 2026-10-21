/*
 * Decompiled with CFR 0.152.
 */
package astolfo.gui.tabgui;

import astolfo.gui.TabGui;
import astolfo.gui.tabgui.TabGuiItem;
import astolfo.setting.EnumSetting;

public class EnumSettingItem
extends TabGuiItem {
    private final EnumSetting setting;
    private static final String c = ": \u00a77";

    public EnumSettingItem(TabGui.Panel panel, EnumSetting enumSetting) {
        super(panel);
        this.setting = enumSetting;
    }

    @Override
    public String getDisplayText() {
        return this.setting.getName() + c + this.setting.getValue();
    }

    @Override
    public void onPress() {
        Enum[] enumArray = (Enum[])this.setting.getValue().getClass().getEnumConstants();
        int n = this.getCurrentIndex();
        if (++n >= enumArray.length || n < 0) {
            n = 0;
        }
        this.setting.setValue(enumArray[n]);
    }

    @Override
    public void onExpand() {
        this.onPress();
    }

    private int getCurrentIndex() {
        Enum[] enumArray = (Enum[])this.setting.getValue().getClass().getEnumConstants();
        for (int i = 0; i < enumArray.length; ++i) {
            if (enumArray[i] != this.setting.getValue()) continue;
            return i;
        }
        return -1;
    }
}


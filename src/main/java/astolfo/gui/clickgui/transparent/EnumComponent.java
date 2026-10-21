/*
 * Decompiled with CFR 0.152.
 */
package astolfo.gui.clickgui.transparent;

import astolfo.gui.clickgui.SettingComponent;
import astolfo.gui.component.Container;
import astolfo.render.RenderUtil;
import astolfo.setting.EnumSetting;
import astolfo.setting.Setting;
import java.awt.Color;
import net.minecraft.util.EnumChatFormatting;

public class EnumComponent
extends SettingComponent {
    private static final long d = -38810986638999432L;

    public EnumComponent(Container container, Setting setting, int i, int j2) {
        super(container, setting, i, j2);
    }

    @Override
    public void render(int i, int j2) {
        RenderUtil.drawRect(this.getX() + 1.5f, this.getYPos() - 1.0f, this.getX() + (float)this.getWidth() + 0.5f, this.getYPos() + (float)this.getHeight() + 4.0f, new Color(1, 1, 1, (int)d).getRGB());
        client.getCustomFontRenderer().drawStringWithShadow((Object)((Object)EnumChatFormatting.GRAY) + this.getEnumSetting().getName().replace("-", " ") + ":" + (Object)((Object)EnumChatFormatting.WHITE) + " " + ((Enum)this.getEnumSetting().getValue()).name(), this.getX() + 4.0f, this.getYPos(), Color.WHITE.getRGB());
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


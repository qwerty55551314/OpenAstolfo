/*
 * Decompiled with CFR 0.152.
 */
package astolfo.lua;

import astolfo.ClientAccess;
import astolfo.lua.GetSettingFunction4;
import astolfo.lua.modulemanager.IsModuleOn;
import astolfo.lua.modulemanager.Option4;
import astolfo.lua.modulemanager.RegisterBoolean3;
import astolfo.lua.modulemanager.RegisterNumber;
import astolfo.lua.modulemanager.SetOption;
import astolfo.module.Module;
import astolfo.setting.BooleanSetting;
import astolfo.setting.EnumSetting;
import astolfo.setting.NumberSetting;
import astolfo.setting.Setting;
import astolfo.setting.SettingType;
import astolfo.setting.StringSetting;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.ThreeArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.VarArgFunction;

public class ModuleManagerLibrary4
extends TwoArgFunction
implements ClientAccess {
    private Module ownerModule;
    public final TwoArgFunction registerFunction = new ScriptRegisterFunction();
    public static final OneArgFunction getSettingFunction = new GetSettingFunction4();
    public static final VarArgFunction optionFunction = new Option4();
    public static final VarArgFunction setOptionFunction = new SetOption();
    public static final OneArgFunction isModuleOnFunction = new IsModuleOn();
    public static final ThreeArgFunction registerBooleanFunction = new RegisterBoolean3();
    public static final VarArgFunction registerNumberFunction = new RegisterNumber();

    public ModuleManagerLibrary4(Module module) {
        this.ownerModule = module;
    }

    @Override
    public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
        LuaTable luaTable = LuaValue.tableOf();
        luaTable.set("register", (LuaValue)this.registerFunction);
        luaTable.set("register_boolean", (LuaValue)registerBooleanFunction);
        luaTable.set("register_number", (LuaValue)registerNumberFunction);
        luaTable.set("option", (LuaValue)optionFunction);
        luaTable.set("set_option", (LuaValue)setOptionFunction);
        luaTable.set("is_module_on", (LuaValue)isModuleOnFunction);
        luaValue2.set("module_manager", (LuaValue)luaTable);
        return luaTable;
    }

    private static void applyLuaValue(LuaValue luaValue, Setting<?> setting) {
        boolean bl = luaValue.isnumber();
        if (bl) {
            if (setting.getType() == SettingType.NUMBER) {
                NumberSetting numberSetting = (NumberSetting)setting;
                if (numberSetting.valueClass == Integer.class) {
                    numberSetting.setValue(luaValue.toint());
                } else {
                    numberSetting.setValue(Float.valueOf(luaValue.tofloat()));
                }
            } else if (setting.getType() == SettingType.ENUM) {
                EnumSetting enumSetting = (EnumSetting)setting;
                Enum[] enumArray = (Enum[])enumSetting.getValue().getClass().getEnumConstants();
                try {
                    enumSetting.setValue(enumArray[luaValue.toint() + 1]);
                }
                catch (Exception exception) {
                    enumSetting.setValue(enumArray[0]);
                }
            }
        } else {
            Setting<?> setting2;
            boolean bl2 = luaValue.isboolean();
            if (bl2) {
                if (setting.getType() == SettingType.BOOLEAN) {
                    BooleanSetting booleanSetting = (BooleanSetting)setting;
                    booleanSetting.set(luaValue.checkboolean());
                }
            } else if (luaValue.isstring() && (setting2 = setting).getType() == SettingType.STRING) {
                StringSetting stringSetting = (StringSetting)setting;
                stringSetting.setValue(luaValue.checkjstring());
            }
        }
    }

    public static Module getOwnerModule(ModuleManagerLibrary4 moduleManagerLibrary4) {
        return moduleManagerLibrary4.ownerModule;
    }

    public static void accessApplyLuaValue(LuaValue luaValue, Setting setting) {
        ModuleManagerLibrary4.applyLuaValue(luaValue, setting);
    }

    public class ScriptRegisterFunction
    extends TwoArgFunction {
        @Override
        public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
            boolean bl;
            LuaValue luaValue3;
            String string = null;
            LuaTable luaTable = null;
            LuaValue luaValue4 = luaValue;
            if (luaValue4.isstring()) {
                luaValue3 = (LuaString)luaValue;
                string = ((LuaString)luaValue3).checkjstring();
            }
            if (bl = luaValue2.isfunction()) {
                luaValue3 = luaValue2.call();
                if (luaValue3.istable()) {
                    luaTable = (LuaTable)luaValue3.call();
                }
            } else if (luaValue2.istable()) {
                luaTable = (LuaTable)luaValue2;
            }
            if (string != null && luaTable != null) {
                ModuleManagerLibrary4.getOwnerModule((ModuleManagerLibrary4)ModuleManagerLibrary4.this).script = luaTable;
                return ScriptRegisterFunction.valueOf(true);
            }
            return ScriptRegisterFunction.valueOf(false);
        }
    }
}


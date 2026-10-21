package astolfo.gui.clickgui.sidebar;

import astolfo.Astolfo;
import astolfo.gui.component.ComponentManager;
import astolfo.gui.component.Element;
import astolfo.manager.Loadable;
import astolfo.module.Category;
import astolfo.module.Module;
import astolfo.module.ModuleInfo;
import astolfo.module.ModuleManager;
import astolfo.setting.BooleanSetting;
import astolfo.setting.EnumSetting;
import astolfo.setting.MapSetting;
import astolfo.setting.NumberSetting;
import astolfo.setting.Setting;
import astolfo.setting.StringSetting;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class SidebarGui extends ComponentManager implements Loadable {
   public int fadeAlpha;
   private DropdownComponent openDropdown;
   public static final StringSetting config = new StringSetting(
      "Config", "how da fuck are you SEEING THIS, look, lil nigga, ill fucking find you shitty house and burn it, dont mess with zarboss", "default"
   );
   private static int baseColor = new Color(23, 25, 31).getRGB();
   private static int panelColor = new Color(29, 31, 37).getRGB();
   private static int surfaceColor = new Color(34, 36, 43).getRGB();

   @Override
   public void load() {
      Object object = null;
      Sidebar sidebar = new Sidebar(0, 0, 34, 210);

      for (Category category : Category.values()) {
         if (category != Category.HIDDEN && category != Category.SCRIPTS) {
            CategoryTab categorytab = new CategoryTab(sidebar, category, (int)sidebar.getX(), 25, 34, 30);
            if (category == Category.CONFIG) {
               categorytab.addChildren(new Element[]{new StringComponent(null, config, 0.0F, 0.0F, 83, 14)});
               categorytab.addChildren(new Element[]{new LoadConfigButton(0.0F, 0.0F, 83, 14)});
               categorytab.addChildren(new Element[]{new SaveConfigButton(0.0F, 0.0F, 83, 14)});
               sidebar.addChildren(new Element[]{categorytab});
            } else {
               Astolfo astolfo = Astolfo.INSTANCE;
               ModuleManager modulemanager = astolfo.getModuleManager();

               for (ModuleInfo moduleinfo : modulemanager.getModules(category)) {
                  if (moduleinfo instanceof Module) {
                     Module module = (Module)moduleinfo;
                     ModuleButton modulebutton = new ModuleButton(categorytab, module, 0, 0, 70, 14);

                     for (Setting setting : module.getSettings()) {
                        if (setting instanceof BooleanSetting) {
                           BooleanComponent booleancomponent1 = new BooleanComponent(modulebutton, setting, 0.0F, 0.0F, 145, 12);
                           modulebutton.addChildren(new Element[]{booleancomponent1});
                        } else if (setting instanceof NumberSetting) {
                           NumberSlider numberslider = new NumberSlider(modulebutton, setting, 0.0F, 0.0F, 145, 12);
                           modulebutton.addChildren(new Element[]{numberslider});
                        } else if (setting instanceof EnumSetting) {
                           EnumComponent enumcomponent = new EnumComponent(modulebutton, setting, 0.0F, 0.0F, 145, 12);
                           modulebutton.addChildren(new Element[]{enumcomponent});
                        } else if (setting instanceof StringSetting) {
                           StringComponent stringcomponent = new StringComponent(modulebutton, setting, 0.0F, 0.0F, 145, 12);
                           modulebutton.addChildren(new Element[]{stringcomponent});
                        } else if (setting instanceof MapSetting) {
                           MapComponent mapcomponent = new MapComponent(modulebutton, (MapSetting)setting, 0, 0, 145, 12);

                           for (Setting setting1 : ((MapSetting)setting).getSettings().values()) {
                              if (setting1 instanceof BooleanSetting) {
                                 BooleanComponent booleancomponent = new BooleanComponent(modulebutton, setting1, 0.0F, 0.0F, 115, 12);
                                 booleancomponent.setForceVisible(true);
                                 mapcomponent.addChildren(new Element[]{booleancomponent});
                              } else if (setting1 instanceof NumberSetting) {
                                 NumberSlider numberslider1 = new NumberSlider(modulebutton, setting1, 0.0F, 0.0F, 95, 12);
                                 numberslider1.setForceVisible(true);
                                 mapcomponent.addChildren(new Element[]{numberslider1});
                              } else if (setting1 instanceof EnumSetting) {
                                 EnumComponent enumcomponent1 = new EnumComponent(modulebutton, setting1, 0.0F, 0.0F, 115, 12);
                                 enumcomponent1.setForceVisible(true);
                                 mapcomponent.addChildren(new Element[]{enumcomponent1});
                              } else if (setting1 instanceof StringSetting) {
                                 StringComponent stringcomponent1 = new StringComponent(modulebutton, setting1, 0.0F, 0.0F, 115, 12);
                                 stringcomponent1.setForceVisible(true);
                                 mapcomponent.addChildren(new Element[]{stringcomponent1});
                              }
                           }

                           modulebutton.addChildren(new Element[]{mapcomponent});
                        }
                     }

                     categorytab.addChildren(new Element[]{modulebutton});
                  }
               }

               sidebar.addChildren(new Element[]{categorytab});
            }
         }
      }

      this.addElements(new Element[]{sidebar});
   }

   @Override
   public void save() {
   }

   public int getBaseColor() {
      return this.fadeAlpha < 255 ? new Color(23, 25, 31, this.fadeAlpha).getRGB() : baseColor;
   }

   public int getPanelColor() {
      return this.fadeAlpha < 255 ? new Color(29, 31, 37, this.fadeAlpha).getRGB() : panelColor;
   }

   public int getSurfaceColor() {
      return this.fadeAlpha < 255 ? new Color(34, 36, 43, this.fadeAlpha).getRGB() : surfaceColor;
   }

   public int getFadedWhite(int i) {
      return this.fadeAlpha < 255 ? new Color(255, 255, 255, Math.min(this.fadeAlpha, i)).getRGB() : Color.WHITE.getRGB();
   }

   public DropdownComponent getOpenDropdown() {
      return this.openDropdown;
   }

   public void setOpenDropdown(DropdownComponent dropdownComponent) {
      this.openDropdown = dropdownComponent;
   }

   public int getAnimationStep() {
      int i = Minecraft.getDebugFPS();
      if (i == 0) {
         i = 250;
      }

      return i >= 250 ? 1 : 250 / i;
   }

   public int getPulseColor() {
      float f = 1.0F - MathHelper.abs(MathHelper.sin((float)Minecraft.getSystemTime() % 4500.0F / 4500.0F * (float) Math.PI * 2.0F) * 0.6F);
      return new Color(
            MathHelper.clamp_int((int)(255.0F * f), 0, 255), MathHelper.clamp_int((int)(255.0F * f), 0, 255), MathHelper.clamp_int((int)(255.0F * f), 0, 255)
         )
         .getRGB();
   }

   public static void scissor(float f, float g, float h, float f2) {
      ScaledResolution scaledresolution = new ScaledResolution(Minecraft.getMinecraft());
      int i = scaledresolution.getScaleFactor();
      GL11.glScissor(
         (int)(f * (float)i), (int)(((float)scaledresolution.getScaledHeight() - f2) * (float)i), (int)((h - f) * (float)i), (int)((f2 - g) * (float)i)
      );
   }
}

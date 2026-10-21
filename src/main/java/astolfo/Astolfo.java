package astolfo;

import astolfo.account.AltManager;
import astolfo.account.AlteningManager;
import astolfo.chat.ChatManager;
import astolfo.command.CommandManager;
import astolfo.event.bus.EventBus;
import astolfo.font.FontManager;
import astolfo.gui.clickgui.defaultgui.DefaultPanelManager;
import astolfo.gui.clickgui.sidebar.SidebarGui;
import astolfo.gui.clickgui.transparent.TransparentPanelManager;
import astolfo.loader.check.LoaderGuard;
import astolfo.manager.Loadable;
import astolfo.module.ModuleManager;
import astolfo.notification.NotificationManager;
import astolfo.relation.RelationManager;
import astolfo.render.BaseColor;
import astolfo.render.font.CustomFontRenderer;
import astolfo.script.ScriptManager;
import astolfo.server.ServerManager;
import astolfo.setting.BooleanSetting;
import astolfo.setting.ColorSetting;
import astolfo.user.AstolfoUserManager;
import astolfo.via.ViaFabric;
import java.awt.Color;
import java.io.File;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.FontRenderer;

public enum Astolfo implements Loadable {
   INSTANCE;

   private List<Loadable> loadables;
   private LoaderGuard loaderGuard;
   private RelationManager relationManager;
   private EventBus eventBus;
   private ModuleManager moduleManager;
   private AstolfoUserManager userManager;
   private CommandManager commandManager;
   private TransparentPanelManager transparentPanelManager;
   private DefaultPanelManager defaultPanelManager;
   private SidebarGui sidebarGui;
   private CustomFontRenderer customFontRenderer;
   private FontRenderer fontRenderer;
   private AltManager altManager;
   private NotificationManager notificationManager;
   private AlteningManager alteningManager;
   private FontManager fontManager;
   private ChatManager chatManager;
   private ServerManager serverManager;
   private ScriptManager scriptManager;
   private final ColorSetting color = new ColorSetting("Color", "client color", BaseColor.PURPLE.getColor());
   private final BooleanSetting cFont = new BooleanSetting("CFont", "custom font", true);
   private final BooleanSetting sense = new BooleanSetting("Sense", "Advanced technology", true);
   private File directory;
   private boolean unusedFlag = true;
   public static final String VERSION = "261020";
   public static boolean failedAndRunning;
   private String configName = "default";
   private Proxy proxy = Proxy.NO_PROXY;
   private static final Astolfo[] VALUES = new Astolfo[]{INSTANCE};

   Astolfo() {
      this.loadables = new ArrayList<>();
   }

   @Override
   public void load() {
      this.register(121968519176554L, this.getLoaderGuard());
      ViaFabric.INSTANCE.init();
      this.serverManager = new ServerManager();
      Runtime.getRuntime().addShutdownHook(new Thread(this::save));
   }

   @Override
   public void save() {
      this.loadables.forEach(Loadable::save);
   }

   public void register(long l, Loadable loadable) {
      if (!this.loadables.contains(loadable)) {
         this.loadables.add(loadable);
         loadable.load();
      }
   }

   public String getName() {
      return Character.toString('A')
         + Character.toString('s')
         + Character.toString('t')
         + Character.toString('o')
         + Character.toString('l')
         + Character.toString('f')
         + Character.toString('o');
   }

   public String getVersion() {
      return VERSION;
   }

   public CommandManager getCommandManager() {
      if (this.commandManager == null) {
         this.commandManager = new CommandManager();
      }

      return this.commandManager;
   }

   public RelationManager getRelationManager() {
      if (this.relationManager == null) {
         this.relationManager = new RelationManager(this.directory);
      }

      return this.relationManager;
   }

   public ScriptManager getScriptManager() {
      if (this.scriptManager == null) {
         this.scriptManager = new ScriptManager();
      }

      return this.scriptManager;
   }

   public AltManager getAltManager() {
      if (this.altManager == null) {
         this.altManager = new AltManager(this.directory);
      }

      return this.altManager;
   }

   public AlteningManager getAlteningManager() {
      if (this.alteningManager == null) {
         this.alteningManager = new AlteningManager(this.directory);
      }

      return this.alteningManager;
   }

   public FontManager getFontManager() {
      if (this.fontManager == null) {
         this.fontManager = new FontManager(this.directory);
      }

      return this.fontManager;
   }

   public ChatManager getChatManager() {
      if (this.chatManager == null) {
         this.chatManager = new ChatManager(this.directory);
      }

      return this.chatManager;
   }

   public ModuleManager getModuleManager() {
      if (this.moduleManager == null) {
         this.moduleManager = new ModuleManager();
      }

      return this.moduleManager;
   }

   public AstolfoUserManager getUserManager() {
      if (this.userManager == null) {
         this.userManager = new AstolfoUserManager();
      }

      return this.userManager;
   }

   public TransparentPanelManager getTransparentPanelManager() {
      if (this.transparentPanelManager == null) {
         this.transparentPanelManager = new TransparentPanelManager();
      }

      return this.transparentPanelManager;
   }

   public DefaultPanelManager getDefaultPanelManager() {
      if (this.defaultPanelManager == null) {
         this.defaultPanelManager = new DefaultPanelManager();
      }

      return this.defaultPanelManager;
   }

   public SidebarGui getSidebarGui() {
      if (this.sidebarGui == null) {
         this.sidebarGui = new SidebarGui();
      }

      return this.sidebarGui;
   }

   public NotificationManager getNotificationManager() {
      if (this.notificationManager == null && this.eventBus != null) {
         this.notificationManager = new NotificationManager();
      }

      return this.notificationManager;
   }

   public LoaderGuard getLoaderGuard() {
      if (this.loaderGuard == null) {
         this.loaderGuard = new LoaderGuard();
      }

      return this.loaderGuard;
   }

   public ServerManager getServerManager() {
      return this.serverManager;
   }

   public LoaderGuard setLoaderGuard(LoaderGuard loaderGuard) {
      this.loaderGuard = loaderGuard;
      return loaderGuard;
   }

   public Proxy getProxy() {
      return this.proxy;
   }

   public void setProxy(Proxy proxy) {
      this.proxy = proxy;
   }

   public File getDirectory() {
      return this.directory;
   }

   public void setDirectory(File file) {
      this.directory = file;
   }

   public EventBus getEventBus() {
      return this.eventBus;
   }

   public void setEventBus(EventBus eventBus) {
      this.eventBus = eventBus;
   }

   public CustomFontRenderer getCustomFontRenderer() {
      return this.customFontRenderer;
   }

   public void setCustomFontRenderer(CustomFontRenderer customFontRenderer) {
      this.customFontRenderer = customFontRenderer;
   }

   public FontRenderer getFontRenderer() {
      return this.fontRenderer;
   }

   public void setFontRenderer(FontRenderer fontRenderer) {
      this.fontRenderer = fontRenderer;
   }

   public Color getColor() {
      return this.getColorSetting().getValue();
   }

   public BooleanSetting getCFontSetting() {
      return this.cFont;
   }

   public BooleanSetting getSenseSetting() {
      return this.sense;
   }

   public ColorSetting getColorSetting() {
      return this.color;
   }

   public void setUnusedFlag(boolean bl) {
      this.unusedFlag = bl;
   }

   public String getConfigName() {
      return this.configName;
   }

   public void setConfigName(String string) {
      this.configName = string;
   }
}

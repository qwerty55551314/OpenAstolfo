package astolfo.account;

import astolfo.Astolfo;
import astolfo.ClientAccess;
import astolfo.Toasts;
import astolfo.gui.screen.AlteningScreen;
import astolfo.notification.Notification;
import astolfo.notification.NotificationType;
import astolfo.util.FileUtil;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.authlib.Agent;
import com.mojang.authlib.UserAuthentication;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.mojang.authlib.exceptions.UserMigratedException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import java.net.Proxy;
import java.util.UUID;
import net.minecraft.entity.Entity;
import net.minecraft.util.Session;
import net.minecraft.util.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

public enum AltLogin implements ClientAccess {
   AUTH;

   public Alt lastLoggedAlt;
   private boolean mojangWarningShown;
   private static final AltLogin[] VALUES = new AltLogin[]{AUTH};
   private static Entity[] n;

   public String generateClientToken() {
      return UUID.randomUUID().toString();
   }

   public Session.Type getSessionType(String string) {
      return string.contains("@") ? Session.Type.MOJANG : Session.Type.LEGACY;
   }

   public void loginAlt(Alt alt) {
      this.login(alt, true);
   }

   public void authenticateMicrosoft(Alt alt) throws Throwable {
      try {
         MicrosoftAuth.I_void(alt.accessToken);
         Pair pair = MicrosoftAuth.getProfile(alt.accessToken);
         alt.username = (String)pair.getRight();
         alt.clientToken = ((UUID)pair.getLeft()).toString();
      } catch (Throwable throwable1) {
         try {
            Pair pair1 = MicrosoftAuth.refreshMicrosoftToken(alt.refreshToken);
            String s = (String)pair1.getRight();
            String s1 = MicrosoftAuth.authXboxLive((String)pair1.getLeft());
            Pair pair2 = MicrosoftAuth.authXsts(s1);
            String s2 = MicrosoftAuth.authMinecraft((String)pair2.getRight(), (String)pair2.getLeft());
            MicrosoftAuth.I_void(s2);
            Pair pair3 = MicrosoftAuth.getProfile(s2);
            alt.clientToken = ((UUID)pair3.getLeft()).toString();
            alt.username = (String)pair3.getRight();
            alt.accessToken = s2;
            alt.refreshToken = s;
         } catch (Throwable throwable) {
            throwable.addSuppressed(throwable1);
            throw throwable;
         }
      }
   }

   public void login(Alt alt, boolean bl) {
      if (!alt.V_boolean()) {
         if (bl) {
            this.setOfflineSession(alt.displayName);
         }
      } else if (alt.microsoft) {
         FileUtil.executor
            .execute(
               () -> {
                  Astolfo astolfo1 = client;
                  Notification notification1 = astolfo1.getNotificationManager()
                     .createNotification("Microsoft Authentication", "Logging in...", NotificationType.INFO, 10000);
                  notification1.show();

                  try {
                     this.authenticateMicrosoft(alt);
                  } catch (Throwable throwable) {
                     notification1.dismiss();
                     toasts.dangerMessage("Microsoft Account", "Login failed");
                     throwable.printStackTrace();
                     return;
                  }

                  mc.session = new Session(alt.username, alt.clientToken, alt.accessToken, "mojang");
                  Toasts.TOASTS.successMessage("Microsoft Account", "Logged successfully to " + alt.username);
                  notification1.dismiss();
                  this.lastLoggedAlt = alt;
               }
            );
      } else {
         Astolfo astolfo = client;
         Notification notification = astolfo.getNotificationManager().createNotification("Authentication", "Logging in...", NotificationType.INFO, 10000);
         if (bl) {
            notification.show();
         }

         FileUtil.executor
            .execute(
               () -> {
                  if (alt.isAlteningAlt()) {
                     AuthServiceSwitcher authserviceswitcher = AlteningScreen.getAuthServiceSwitcher();
                     if (authserviceswitcher.getAccountType() == AccountType.MOJANG) {
                        try {
                           AlteningScreen.getAuthServiceSwitcher().setAccountType(AccountType.THEALTENING);
                        } catch (IllegalAccessException | NoSuchFieldException nosuchfieldexception1) {
                           nosuchfieldexception1.printStackTrace();
                        }
                     }
                  } else {
                     AuthServiceSwitcher authserviceswitcher1 = AlteningScreen.getAuthServiceSwitcher();
                     if (authserviceswitcher1.getAccountType() == AccountType.THEALTENING) {
                        try {
                           AlteningScreen.getAuthServiceSwitcher().setAccountType(AccountType.MOJANG);
                        } catch (IllegalAccessException | NoSuchFieldException nosuchfieldexception) {
                           nosuchfieldexception.printStackTrace();
                        }
                     }
                  }

                  UserAuthentication userauthentication = new YggdrasilAuthenticationService(Proxy.NO_PROXY, alt.clientToken)
                     .createUserAuthentication(Agent.MINECRAFT);
                  boolean flag1 = false;
                  boolean flag1x = StringUtils.isNullOrEmpty(alt.accessToken);
                  if (!flag1x && alt.sessionType == Session.Type.MOJANG) {
                     ImmutableMap immutablemap = new Builder<String, String>()
                        .put("username", alt.username)
                        .put("displayName", alt.displayName)
                        .put("uuid", alt.clientToken)
                        .put("accessToken", alt.accessToken)
                        .build();
                     userauthentication.loadFromStorage(immutablemap);
                     flag1 = this.tryAuthenticate(userauthentication, "Access Token Login", alt);
                  }

                  if (!flag1) {
                     userauthentication.logOut();
                     userauthentication.setUsername(alt.username);
                     userauthentication.setPassword(alt.isAlteningAlt() ? "cool" : alt.password);
                     flag1 = this.tryAuthenticate(
                        userauthentication,
                        AlteningScreen.getAuthServiceSwitcher().getAccountType() == AccountType.THEALTENING ? "The Altening Login" : "Password Login",
                        alt
                     );
                  }

                  if (flag1) {
                     if (bl) {
                        mc.session = new Session(
                           userauthentication.getSelectedProfile().getName(),
                           userauthentication.getSelectedProfile().getId().toString(),
                           userauthentication.getAuthenticatedToken(),
                           alt.sessionType.toString()
                        );
                     }

                     alt.accessToken = userauthentication.getAuthenticatedToken();
                     alt.clientToken = userauthentication.getSelectedProfile().getId().toString();
                     alt.displayName = userauthentication.getSelectedProfile().getName();
                     alt.failedLogin = false;
                  }

                  notification.dismiss();
               }
            );
      }
   }

   private void setOfflineSession(String string) {
      mc.session = new Session(string, mc.session.getPlayerID(), mc.session.getToken(), Session.Type.LEGACY.toString());
      Astolfo astolfo = client;
      astolfo.getNotificationManager().createNotification("Offline login", "Username set to: " + string, NotificationType.INFO, 5000).show();
   }

   private boolean tryAuthenticate(UserAuthentication userAuthentication, String string, Alt alt) {
      try {
         userAuthentication.logIn();
         Astolfo astolfo = client;
         astolfo.getNotificationManager()
            .createNotification(string, "Logged in to " + userAuthentication.getSelectedProfile().getName(), NotificationType.SUCCESS, 5000)
            .show();
         if (!this.mojangWarningShown) {
            toasts.infoTimed("Mojang Account", "Mojang has closed support for this type of account, they might not work", 15000);
            this.mojangWarningShown = true;
         }

         this.lastLoggedAlt = alt;
         return true;
      } catch (AuthenticationUnavailableException authenticationunavailableexception) {
         Astolfo astolfo1 = client;
         astolfo1.getNotificationManager()
            .createNotification(string, "Failed to connect to Mojang's authentication servers", NotificationType.DANGER, 5000)
            .show();
      } catch (UserMigratedException usermigratedexception) {
         Astolfo astolfo2 = client;
         astolfo2.getNotificationManager().createNotification(string, "User account is migrated", NotificationType.DANGER, 5000).show();
      } catch (AuthenticationException authenticationexception) {
         if (!string.equals("Access Token Login")) {
            alt.failedLogin = true;
         }

         Astolfo astolfo3 = client;
         astolfo3.getNotificationManager().createNotification(string, "Bad login", NotificationType.DANGER, 5000).show();
      }

      return false;
   }

   static {
      b(new Entity[2]);
   }

   public static void b(Entity[] entityArray) {
      n = entityArray;
   }

   public static Entity[] b() {
      return n;
   }
}

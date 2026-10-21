package net.minecraft.server.management;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.io.File;

public class UserListWhitelist extends UserList<GameProfile, UserListWhitelistEntry> {
   public UserListWhitelist(File file) {
      super(file);
   }

   @Override
   public UserListEntry<GameProfile> createEntry(JsonObject entryData) {
      return new UserListWhitelistEntry(entryData);
   }

   @Override
   public String[] getKeys() {
      String[] astring = new String[this.getValues().size()];
      int i = 0;

      for (UserListWhitelistEntry userlistwhitelistentry : this.getValues().values()) {
         astring[i++] = userlistwhitelistentry.getValue().getName();
      }

      return astring;
   }

   public String getObjectKey(GameProfile obj) {
      return obj.getId().toString();
   }

   public GameProfile getBannedProfile(String name) {
      for (UserListWhitelistEntry userlistwhitelistentry : this.getValues().values()) {
         if (name.equalsIgnoreCase(userlistwhitelistentry.getValue().getName())) {
            return userlistwhitelistentry.getValue();
         }
      }

      return null;
   }
}

package net.minecraft.nbt;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import java.util.UUID;
import net.minecraft.util.StringUtils;

public final class NBTUtil {
   public static GameProfile readGameProfileFromNBT(NBTTagCompound compound) {
      String s = null;
      String s1 = null;
      if (compound.hasKey("Name", 8)) {
         s = compound.getString("Name");
      }

      if (compound.hasKey("Id", 8)) {
         s1 = compound.getString("Id");
      }

      if (StringUtils.isNullOrEmpty(s) && StringUtils.isNullOrEmpty(s1)) {
         return null;
      } else {
         UUID uuid;
         try {
            uuid = UUID.fromString(s1);
         } catch (Throwable throwable) {
            uuid = null;
         }

         GameProfile gameprofile = new GameProfile(uuid, s);
         if (compound.hasKey("Properties", 10)) {
            NBTTagCompound nbttagcompound = compound.getCompoundTag("Properties");

            for (String s2 : nbttagcompound.getKeySet()) {
               NBTTagList nbttaglist = nbttagcompound.getTagList(s2, 10);

               for (int i = 0; i < nbttaglist.tagCount(); i++) {
                  NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
                  String s3 = nbttagcompound1.getString("Value");
                  boolean flag = nbttagcompound1.hasKey("Signature", 8);
                  if (flag) {
                     gameprofile.getProperties().put(s2, new Property(s2, s3, nbttagcompound1.getString("Signature")));
                  } else {
                     gameprofile.getProperties().put(s2, new Property(s2, s3));
                  }
               }
            }
         }

         return gameprofile;
      }
   }

   public static NBTTagCompound writeGameProfile(NBTTagCompound tagCompound, GameProfile profile) {
      if (!StringUtils.isNullOrEmpty(profile.getName())) {
         tagCompound.setString("Name", profile.getName());
      }

      if (profile.getId() != null) {
         tagCompound.setString("Id", profile.getId().toString());
      }

      if (!profile.getProperties().isEmpty()) {
         NBTTagCompound nbttagcompound = new NBTTagCompound();

         for (String s : profile.getProperties().keySet()) {
            NBTTagList nbttaglist = new NBTTagList();

            for (Property property : profile.getProperties().get(s)) {
               NBTTagCompound nbttagcompound1 = new NBTTagCompound();
               nbttagcompound1.setString("Value", property.getValue());
               if (property.hasSignature()) {
                  nbttagcompound1.setString("Signature", property.getSignature());
               }

               nbttaglist.appendTag(nbttagcompound1);
            }

            nbttagcompound.setTag(s, nbttaglist);
         }

         tagCompound.setTag("Properties", nbttagcompound);
      }

      return tagCompound;
   }

   public static boolean func_181123_a(NBTBase nbtBase, NBTBase nbtBase2, boolean bl) {
      if (nbtBase == nbtBase2) {
         return true;
      } else if (nbtBase == null) {
         return true;
      } else if (nbtBase2 == null) {
         return false;
      } else {
         boolean flag1 = nbtBase.getClass().equals(nbtBase2.getClass());
         if (!flag1) {
            return false;
         } else {
            boolean flag2 = nbtBase instanceof NBTTagCompound;
            if (flag2) {
               NBTTagCompound nbttagcompound = (NBTTagCompound)nbtBase;
               NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbtBase2;

               for (String s : nbttagcompound.getKeySet()) {
                  NBTBase nbtbase1 = nbttagcompound.getTag(s);
                  if (!func_181123_a(nbtbase1, nbttagcompound1.getTag(s), bl)) {
                     return false;
                  }
               }

               return true;
            } else {
               boolean flag3 = nbtBase instanceof NBTTagList;
               if (flag3 && bl) {
                  NBTTagList nbttaglist = (NBTTagList)nbtBase;
                  NBTTagList nbttaglist1 = (NBTTagList)nbtBase2;
                  if (nbttaglist.tagCount() == 0) {
                     return nbttaglist1.tagCount() == 0;
                  } else {
                     for (int i = 0; i < nbttaglist.tagCount(); i++) {
                        NBTBase nbtbase = nbttaglist.get(i);
                        boolean flag = false;

                        for (int j = 0; j < nbttaglist1.tagCount(); j++) {
                           if (func_181123_a(nbtbase, nbttaglist1.get(j), bl)) {
                              flag = true;
                              break;
                           }
                        }

                        if (!flag) {
                           return false;
                        }
                     }

                     return true;
                  }
               } else {
                  return nbtBase.equals(nbtBase2);
               }
            }
         }
      }
   }
}

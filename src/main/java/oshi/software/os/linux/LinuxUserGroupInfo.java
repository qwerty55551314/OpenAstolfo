package oshi.software.os.linux;

import java.util.HashMap;
import java.util.Map;
import oshi.util.ExecutingCommand;
import oshi.util.tuples.Pair;

public class LinuxUserGroupInfo {
   private final Map<String, Pair<String, String>> usersIdMap = getUserMap();
   private final Map<String, String> groupsIdMap = getGroupMap();
   private static boolean l;

   public Pair<String, String> getUser(String userId) {
      return this.usersIdMap.getOrDefault(userId, new Pair<>(userId, "unknown"));
   }

   public String getGroupName(String groupId) {
      return this.groupsIdMap.getOrDefault(groupId, "unknown");
   }

   private static Map<String, Pair<String, String>> getUserMap() {
      boolean flag = true;
      HashMap hashmap = new HashMap();

      for (String s : ExecutingCommand.runNative("getent passwd")) {
         String[] astring = s.split(":");
         if (astring.length >= 3) {
            String s1 = astring[0];
            String s2 = astring[2];
            hashmap.putIfAbsent(s2, new Pair<>(s2, s1));
         }
      }

      return hashmap;
   }

   private static Map<String, String> getGroupMap() {
      boolean flag = true;
      HashMap hashmap = new HashMap();

      for (String s : ExecutingCommand.runNative("getent group")) {
         String[] astring = s.split(":");
         if (astring.length > 2) {
            String s1 = astring[0];
            String s2 = astring[2];
            hashmap.putIfAbsent(s2, s1);
         }
      }

      return hashmap;
   }

   public static void C(boolean flag) {
      l = flag;
   }

   public static boolean j() {
      return l;
   }

   public static boolean l() {
      return false;
   }

   static {
      C(true);
   }
}

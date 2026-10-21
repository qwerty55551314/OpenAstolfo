package net.minecraft.scoreboard;

import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Map;

public abstract class Team {
   private static String d;

   public boolean isSameTeam(Team other) {
      return other == null ? false : this == other;
   }

   public abstract String getRegisteredName();

   public abstract String formatString(String var1);

   public abstract boolean getSeeFriendlyInvisiblesEnabled();

   public abstract boolean getAllowFriendlyFire();

   public abstract Team.EnumVisible getNameTagVisibility();

   public abstract Collection<String> getMembershipCollection();

   public abstract Team.EnumVisible getDeathMessageVisibility();

   public static enum EnumVisible {
      ALWAYS("always", 0),
      NEVER("never", 1),
      HIDE_FOR_OTHER_TEAMS("hideForOtherTeams", 2),
      HIDE_FOR_OWN_TEAM("hideForOwnTeam", 3);

      private static Map<String, Team.EnumVisible> field_178828_g = Maps.newHashMap();
      public final String internalName;
      public final int id;

      public static String[] func_178825_a() {
         return field_178828_g.keySet().toArray(new String[field_178828_g.size()]);
      }

      public static Team.EnumVisible func_178824_a(String string) {
         return field_178828_g.get(string);
      }

      private EnumVisible(String string2, int i2) {
         this.internalName = string2;
         this.id = i2;
      }

      static {
         for (Team.EnumVisible team$enumvisible : values()) {
            field_178828_g.put(team$enumvisible.internalName, team$enumvisible);
         }
      }
   }
}

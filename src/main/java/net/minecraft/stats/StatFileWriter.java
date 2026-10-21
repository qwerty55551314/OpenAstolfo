package net.minecraft.stats;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IJsonSerializable;
import net.minecraft.util.TupleIntJsonSerializable;

public class StatFileWriter {
   public final Map<StatBase, TupleIntJsonSerializable> statsData = Maps.newConcurrentMap();

   public boolean hasAchievementUnlocked(Achievement achievementIn) {
      return this.readStat(achievementIn) > 0;
   }

   public boolean canUnlockAchievement(Achievement achievementIn) {
      return achievementIn.parentAchievement == null || this.hasAchievementUnlocked(achievementIn.parentAchievement);
   }

   public int func_150874_c(Achievement achievement) {
      boolean flag = this.hasAchievementUnlocked(achievement);
      if (flag) {
         return 0;
      } else {
         int i = 0;

         for (Achievement achievement1 = achievement.parentAchievement; achievement1 != null; i++) {
            boolean flag1 = this.hasAchievementUnlocked(achievement1);
            if (flag1) {
               break;
            }

            achievement1 = achievement1.parentAchievement;
         }

         return i;
      }
   }

   public void increaseStat(EntityPlayer player, StatBase stat, int amount) {
      if (!stat.isAchievement() || this.canUnlockAchievement((Achievement)stat)) {
         this.unlockAchievement(player, stat, this.readStat(stat) + amount);
      }
   }

   public void unlockAchievement(EntityPlayer playerIn, StatBase statIn, int i) {
      TupleIntJsonSerializable tupleintjsonserializable = this.statsData.get(statIn);
      if (tupleintjsonserializable == null) {
         tupleintjsonserializable = new TupleIntJsonSerializable();
         this.statsData.put(statIn, tupleintjsonserializable);
      }

      tupleintjsonserializable.setIntegerValue(i);
   }

   public int readStat(StatBase stat) {
      TupleIntJsonSerializable tupleintjsonserializable = this.statsData.get(stat);
      return tupleintjsonserializable == null ? 0 : tupleintjsonserializable.getIntegerValue();
   }

   public <T extends IJsonSerializable> T func_150870_b(StatBase statBase) {
      TupleIntJsonSerializable tupleintjsonserializable = this.statsData.get(statBase);
      return tupleintjsonserializable != null ? tupleintjsonserializable.getJsonSerializableValue() : null;
   }

   public <T extends IJsonSerializable> T func_150872_a(StatBase statBase, T iJsonSerializable) {
      TupleIntJsonSerializable tupleintjsonserializable = this.statsData.get(statBase);
      if (tupleintjsonserializable == null) {
         tupleintjsonserializable = new TupleIntJsonSerializable();
         this.statsData.put(statBase, tupleintjsonserializable);
      }

      tupleintjsonserializable.setJsonSerializableValue(iJsonSerializable);
      return (T)iJsonSerializable;
   }
}

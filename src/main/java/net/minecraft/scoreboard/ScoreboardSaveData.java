package net.minecraft.scoreboard;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.WorldSavedData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ScoreboardSaveData extends WorldSavedData {
   private static final Logger logger = LogManager.getLogger();
   private Scoreboard theScoreboard;
   private NBTTagCompound delayedInitNbt;

   public ScoreboardSaveData() {
      this("scoreboard");
   }

   public ScoreboardSaveData(String name) {
      super(name);
   }

   public void setScoreboard(Scoreboard scoreboardIn) {
      this.theScoreboard = scoreboardIn;
      if (this.delayedInitNbt != null) {
         this.readFromNBT(this.delayedInitNbt);
      }
   }

   @Override
   public void readFromNBT(NBTTagCompound nbt) {
      if (this.theScoreboard == null) {
         this.delayedInitNbt = nbt;
      } else {
         this.readObjectives(nbt.getTagList("Objectives", 10));
         this.readScores(nbt.getTagList("PlayerScores", 10));
         boolean flag = nbt.hasKey("DisplaySlots", 10);
         if (flag) {
            this.readDisplayConfig(nbt.getCompoundTag("DisplaySlots"));
         }

         if (nbt.hasKey("Teams", 9)) {
            this.readTeams(nbt.getTagList("Teams", 10));
         }
      }
   }

   public void readTeams(NBTTagList nbtTagList) {
      for (int i = 0; i < nbtTagList.tagCount(); i++) {
         NBTTagCompound nbttagcompound = nbtTagList.getCompoundTagAt(i);
         String s = nbttagcompound.getString("Name");
         if (s.length() > 16) {
            s = s.substring(0, 16);
         }

         ScorePlayerTeam scoreplayerteam = this.theScoreboard.createTeam(s);
         String s1 = nbttagcompound.getString("DisplayName");
         int j = s1.length();
         if (j > 32) {
            s1 = s1.substring(0, 32);
         }

         scoreplayerteam.setTeamName(s1);
         boolean flag = nbttagcompound.hasKey("TeamColor", 8);
         if (flag) {
            scoreplayerteam.setChatFormat(EnumChatFormatting.getValueByName(nbttagcompound.getString("TeamColor")));
         }

         scoreplayerteam.setNamePrefix(nbttagcompound.getString("Prefix"));
         scoreplayerteam.setNameSuffix(nbttagcompound.getString("Suffix"));
         boolean flag1 = nbttagcompound.hasKey("AllowFriendlyFire", 99);
         if (flag1) {
            scoreplayerteam.setAllowFriendlyFire(nbttagcompound.getBoolean("AllowFriendlyFire"));
         }

         boolean flag2 = nbttagcompound.hasKey("SeeFriendlyInvisibles", 99);
         if (flag2) {
            scoreplayerteam.setSeeFriendlyInvisiblesEnabled(nbttagcompound.getBoolean("SeeFriendlyInvisibles"));
         }

         boolean flag3 = nbttagcompound.hasKey("NameTagVisibility", 8);
         if (flag3) {
            Team.EnumVisible team$enumvisible = Team.EnumVisible.func_178824_a(nbttagcompound.getString("NameTagVisibility"));
            if (team$enumvisible != null) {
               scoreplayerteam.setNameTagVisibility(team$enumvisible);
            }
         }

         if (nbttagcompound.hasKey("DeathMessageVisibility", 8)) {
            Team.EnumVisible team$enumvisible1 = Team.EnumVisible.func_178824_a(nbttagcompound.getString("DeathMessageVisibility"));
            if (team$enumvisible1 != null) {
               scoreplayerteam.setDeathMessageVisibility(team$enumvisible1);
            }
         }

         this.func_96502_a(scoreplayerteam, nbttagcompound.getTagList("Players", 8));
      }
   }

   public void func_96502_a(ScorePlayerTeam scorePlayerTeam, NBTTagList nbtTagList) {
      for (int i = 0; i < nbtTagList.tagCount(); i++) {
         this.theScoreboard.addPlayerToTeam(nbtTagList.getStringTagAt(i), scorePlayerTeam.getRegisteredName());
      }
   }

   public void readDisplayConfig(NBTTagCompound nbtTagCompound) {
      for (int i = 0; i < 19; i++) {
         if (nbtTagCompound.hasKey("slot_" + i, 8)) {
            String s = nbtTagCompound.getString("slot_" + i);
            ScoreObjective scoreobjective = this.theScoreboard.getObjective(s);
            this.theScoreboard.setObjectiveInDisplaySlot(i, scoreobjective);
         }
      }
   }

   public void readObjectives(NBTTagList nbt) {
      for (int i = 0; i < nbt.tagCount(); i++) {
         NBTTagCompound nbttagcompound = nbt.getCompoundTagAt(i);
         IScoreObjectiveCriteria iscoreobjectivecriteria = IScoreObjectiveCriteria.INSTANCES.get(nbttagcompound.getString("CriteriaName"));
         if (iscoreobjectivecriteria != null) {
            String s = nbttagcompound.getString("Name");
            if (s.length() > 16) {
               s = s.substring(0, 16);
            }

            ScoreObjective scoreobjective = this.theScoreboard.addScoreObjective(s, iscoreobjectivecriteria);
            scoreobjective.setDisplayName(nbttagcompound.getString("DisplayName"));
            scoreobjective.setRenderType(IScoreObjectiveCriteria.EnumRenderType.func_178795_a(nbttagcompound.getString("RenderType")));
         }
      }
   }

   public void readScores(NBTTagList nbt) {
      for (int i = 0; i < nbt.tagCount(); i++) {
         NBTTagCompound nbttagcompound = nbt.getCompoundTagAt(i);
         ScoreObjective scoreobjective = this.theScoreboard.getObjective(nbttagcompound.getString("Objective"));
         String s = nbttagcompound.getString("Name");
         if (s.length() > 40) {
            s = s.substring(0, 40);
         }

         Score score = this.theScoreboard.getValueFromObjective(s, scoreobjective);
         score.setScorePoints(nbttagcompound.getInteger("Score"));
         if (nbttagcompound.hasKey("Locked")) {
            score.setLocked(nbttagcompound.getBoolean("Locked"));
         }
      }
   }

   @Override
   public void writeToNBT(NBTTagCompound nbt) {
      if (this.theScoreboard == null) {
         logger.warn("Tried to save scoreboard without having a scoreboard...");
      } else {
         nbt.setTag("Objectives", this.objectivesToNbt());
         nbt.setTag("PlayerScores", this.scoresToNbt());
         nbt.setTag("Teams", this.func_96496_a());
         this.func_96497_d(nbt);
      }
   }

   public NBTTagList func_96496_a() {
      NBTTagList nbttaglist = new NBTTagList();

      for (ScorePlayerTeam scoreplayerteam : this.theScoreboard.getTeams()) {
         NBTTagCompound nbttagcompound = new NBTTagCompound();
         nbttagcompound.setString("Name", scoreplayerteam.getRegisteredName());
         nbttagcompound.setString("DisplayName", scoreplayerteam.getTeamName());
         if (scoreplayerteam.getChatFormat().getColorIndex() >= 0) {
            nbttagcompound.setString("TeamColor", scoreplayerteam.getChatFormat().getFriendlyName());
         }

         nbttagcompound.setString("Prefix", scoreplayerteam.getColorPrefix());
         nbttagcompound.setString("Suffix", scoreplayerteam.getColorSuffix());
         nbttagcompound.setBoolean("AllowFriendlyFire", scoreplayerteam.getAllowFriendlyFire());
         nbttagcompound.setBoolean("SeeFriendlyInvisibles", scoreplayerteam.getSeeFriendlyInvisiblesEnabled());
         nbttagcompound.setString("NameTagVisibility", scoreplayerteam.getNameTagVisibility().internalName);
         nbttagcompound.setString("DeathMessageVisibility", scoreplayerteam.getDeathMessageVisibility().internalName);
         NBTTagList nbttaglist1 = new NBTTagList();

         for (String s : scoreplayerteam.getMembershipCollection()) {
            nbttaglist1.appendTag(new NBTTagString(s));
         }

         nbttagcompound.setTag("Players", nbttaglist1);
         nbttaglist.appendTag(nbttagcompound);
      }

      return nbttaglist;
   }

   public void func_96497_d(NBTTagCompound nbtTagCompound) {
      Object object = null;
      NBTTagCompound nbttagcompound = new NBTTagCompound();
      boolean flag = false;

      for (int i = 0; i < 19; i++) {
         ScoreObjective scoreobjective = this.theScoreboard.getObjectiveInDisplaySlot(i);
         if (scoreobjective != null) {
            nbttagcompound.setString("slot_" + i, scoreobjective.getName());
            flag = true;
         }
      }

      if (flag) {
         nbtTagCompound.setTag("DisplaySlots", nbttagcompound);
      }
   }

   public NBTTagList objectivesToNbt() {
      Object object = null;
      NBTTagList nbttaglist = new NBTTagList();

      for (ScoreObjective scoreobjective : this.theScoreboard.getScoreObjectives()) {
         if (scoreobjective.getCriteria() != null) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setString("Name", scoreobjective.getName());
            nbttagcompound.setString("CriteriaName", scoreobjective.getCriteria().getName());
            nbttagcompound.setString("DisplayName", scoreobjective.getDisplayName());
            nbttagcompound.setString("RenderType", scoreobjective.getRenderType().func_178796_a());
            nbttaglist.appendTag(nbttagcompound);
         }
      }

      return nbttaglist;
   }

   public NBTTagList scoresToNbt() {
      Object object = null;
      NBTTagList nbttaglist = new NBTTagList();

      for (Score score : this.theScoreboard.getScores()) {
         if (score.getObjective() != null) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setString("Name", score.getPlayerName());
            nbttagcompound.setString("Objective", score.getObjective().getName());
            nbttagcompound.setInteger("Score", score.getScorePoints());
            nbttagcompound.setBoolean("Locked", score.isLocked());
            nbttaglist.appendTag(nbttagcompound);
         }
      }

      return nbttaglist;
   }
}

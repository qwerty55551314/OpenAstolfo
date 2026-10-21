package net.minecraft.util;

import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.server.MinecraftServer;

public class ChatComponentScore extends ChatComponentStyle {
   private final String name;
   private final String objective;
   private String value = "";

   public ChatComponentScore(String nameIn, String objectiveIn) {
      this.name = nameIn;
      this.objective = objectiveIn;
   }

   public String getName() {
      return this.name;
   }

   public String getObjective() {
      return this.objective;
   }

   public void setValue(String valueIn) {
      this.value = valueIn;
   }

   @Override
   public String getUnformattedTextForChat() {
      MinecraftServer minecraftserver = MinecraftServer.getServer();
      if (minecraftserver != null) {
         boolean flag = minecraftserver.isAnvilFileSet();
         if (flag && StringUtils.isNullOrEmpty(this.value)) {
            Scoreboard scoreboard = minecraftserver.worldServerForDimension(0).getScoreboard();
            ScoreObjective scoreobjective = scoreboard.getObjective(this.objective);
            String s = this.name;
            if (scoreboard.entityHasObjective(s, scoreobjective)) {
               Score score = scoreboard.getValueFromObjective(this.name, scoreobjective);
               this.setValue(String.format("%d", score.getScorePoints()));
            } else {
               this.value = "";
            }
         }
      }

      return this.value;
   }

   public ChatComponentScore createCopy() {
      Object object = null;
      ChatComponentScore chatcomponentscore = new ChatComponentScore(this.name, this.objective);
      chatcomponentscore.setValue(this.value);
      chatcomponentscore.setChatStyle(this.getChatStyle().createShallowCopy());

      for (IChatComponent ichatcomponent : this.getSiblings()) {
         chatcomponentscore.appendSibling(ichatcomponent.createCopy());
      }

      return chatcomponentscore;
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else if (!(object instanceof ChatComponentScore)) {
         return false;
      } else {
         ChatComponentScore chatcomponentscore = (ChatComponentScore)object;
         boolean flag = this.name.equals(chatcomponentscore.name);
         if (flag) {
            boolean flag1 = this.objective.equals(chatcomponentscore.objective);
            if (flag1) {
               boolean flag2 = super.equals(object);
               if (flag2) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   @Override
   public String toString() {
      return "ScoreComponent{name='"
         + this.name
         + '\''
         + "objective='"
         + this.objective
         + '\''
         + ", siblings="
         + super.siblings
         + ", style="
         + this.getChatStyle()
         + '}';
   }
}

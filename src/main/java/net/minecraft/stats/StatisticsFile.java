package net.minecraft.stats;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.S37PacketStatistics;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IJsonSerializable;
import net.minecraft.util.TupleIntJsonSerializable;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StatisticsFile extends StatFileWriter {
   private static final Logger logger = LogManager.getLogger();
   private final MinecraftServer mcServer;
   private final File statsFile;
   private final Set<StatBase> field_150888_e = Sets.newHashSet();
   private int field_150885_f = -300;
   private boolean field_150886_g = false;

   public StatisticsFile(MinecraftServer serverIn, File statsFileIn) {
      this.mcServer = serverIn;
      this.statsFile = statsFileIn;
   }

   public void readStatFile() {
      if (this.statsFile.isFile()) {
         try {
            super.statsData.clear();
            super.statsData.putAll(this.parseJson(FileUtils.readFileToString(this.statsFile)));
         } catch (IOException ioexception) {
            logger.error("Couldn't read statistics file " + this.statsFile, (Throwable)ioexception);
         } catch (JsonParseException jsonparseexception) {
            logger.error("Couldn't parse statistics file " + this.statsFile, (Throwable)jsonparseexception);
         }
      }
   }

   public void saveStatFile() {
      try {
         FileUtils.writeStringToFile(this.statsFile, dumpJson(super.statsData));
      } catch (IOException ioexception) {
         logger.error("Couldn't save stats", (Throwable)ioexception);
      }
   }

   @Override
   public void unlockAchievement(EntityPlayer playerIn, StatBase statIn, int i) {
      boolean flag = statIn.isAchievement();
      int i1 = flag ? this.readStat(statIn) : 0;
      super.unlockAchievement(playerIn, statIn, i);
      this.field_150888_e.add(statIn);
      boolean flag1 = statIn.isAchievement();
      if (flag1 && i1 == 0 && i > 0) {
         this.field_150886_g = true;
         if (this.mcServer.isAnnouncingPlayerAchievements()) {
            this.mcServer
               .getConfigurationManager()
               .sendChatMsg(new ChatComponentTranslation("chat.type.achievement", playerIn.getDisplayName(), statIn.createChatComponent()));
         }
      }

      boolean flag2 = statIn.isAchievement();
      if (flag2 && i1 > 0 && i == 0) {
         this.field_150886_g = true;
         if (this.mcServer.isAnnouncingPlayerAchievements()) {
            this.mcServer
               .getConfigurationManager()
               .sendChatMsg(new ChatComponentTranslation("chat.type.achievement.taken", playerIn.getDisplayName(), statIn.createChatComponent()));
         }
      }
   }

   public Set<StatBase> func_150878_c() {
      HashSet hashset = Sets.newHashSet(this.field_150888_e);
      this.field_150888_e.clear();
      this.field_150886_g = false;
      return hashset;
   }

   public Map<StatBase, TupleIntJsonSerializable> parseJson(String string) {
      int b0 = 45;
      JsonElement jsonelement = new JsonParser().parse(string);
      if (!jsonelement.isJsonObject()) {
         return Maps.newHashMap();
      } else {
         JsonObject jsonobject = jsonelement.getAsJsonObject();
         HashMap hashmap = Maps.newHashMap();

         for (Entry entry : jsonobject.entrySet()) {
            StatBase statbase = StatList.getOneShotStat((String)entry.getKey());
            if (statbase != null) {
               TupleIntJsonSerializable tupleintjsonserializable = new TupleIntJsonSerializable();
               boolean flag = ((JsonElement)entry.getValue()).isJsonPrimitive();
               if (flag && ((JsonElement)entry.getValue()).getAsJsonPrimitive().isNumber()) {
                  tupleintjsonserializable.setIntegerValue(((JsonElement)entry.getValue()).getAsInt());
               } else if (((JsonElement)entry.getValue()).isJsonObject()) {
                  JsonObject jsonobject1 = ((JsonElement)entry.getValue()).getAsJsonObject();
                  boolean flag1 = jsonobject1.has("value");
                  if (flag1 && jsonobject1.get("value").isJsonPrimitive() && jsonobject1.get("value").getAsJsonPrimitive().isNumber()) {
                     tupleintjsonserializable.setIntegerValue(jsonobject1.getAsJsonPrimitive("value").getAsInt());
                  }

                  if (jsonobject1.has("progress")) {
                     Class oclass = statbase.func_150954_l();
                     if (oclass != null) {
                        try {
                           Constructor constructor = statbase.func_150954_l().getConstructor();
                           IJsonSerializable ijsonserializable = (IJsonSerializable)constructor.newInstance();
                           ijsonserializable.fromJson(jsonobject1.get("progress"));
                           tupleintjsonserializable.setJsonSerializableValue(ijsonserializable);
                        } catch (Throwable throwable) {
                           logger.warn("Invalid statistic progress in " + this.statsFile, throwable);
                        }
                     }
                  }
               }

               hashmap.put(statbase, tupleintjsonserializable);
            } else {
               logger.warn("Invalid statistic in " + this.statsFile + ": Don't know what " + (String)entry.getKey() + " is");
            }
         }

         return hashmap;
      }
   }

   public static String dumpJson(Map<StatBase, TupleIntJsonSerializable> map) {
      boolean flag = false;
      JsonObject jsonobject = new JsonObject();

      for (Entry entry : map.entrySet()) {
         if (((TupleIntJsonSerializable)entry.getValue()).getJsonSerializableValue() != null) {
            JsonObject jsonobject1 = new JsonObject();
            jsonobject1.addProperty("value", ((TupleIntJsonSerializable)entry.getValue()).getIntegerValue());

            try {
               jsonobject1.add("progress", ((TupleIntJsonSerializable)entry.getValue()).<IJsonSerializable>getJsonSerializableValue().getSerializableElement());
            } catch (Throwable throwable) {
               logger.warn("Couldn't save statistic " + ((StatBase)entry.getKey()).getStatName() + ": error serializing progress", throwable);
            }

            jsonobject.add(((StatBase)entry.getKey()).statId, jsonobject1);
         } else {
            jsonobject.addProperty(((StatBase)entry.getKey()).statId, ((TupleIntJsonSerializable)entry.getValue()).getIntegerValue());
         }
      }

      return jsonobject.toString();
   }

   public void func_150877_d() {
      for (StatBase statbase : super.statsData.keySet()) {
         this.field_150888_e.add(statbase);
      }
   }

   public void func_150876_a(EntityPlayerMP entityPlayerMP) {
      int i = this.mcServer.getTickCounter();
      HashMap hashmap = Maps.newHashMap();
      if (this.field_150886_g || i - this.field_150885_f > 300) {
         this.field_150885_f = i;

         for (StatBase statbase : this.func_150878_c()) {
            hashmap.put(statbase, this.readStat(statbase));
         }
      }

      entityPlayerMP.playerNetServerHandler.sendPacket(new S37PacketStatistics(hashmap));
   }

   public void sendAchievements(EntityPlayerMP player) {
      HashMap hashmap = Maps.newHashMap();

      for (Achievement achievement : AchievementList.achievementList) {
         if (this.hasAchievementUnlocked(achievement)) {
            hashmap.put(achievement, this.readStat(achievement));
            this.field_150888_e.remove(achievement);
         }
      }

      player.playerNetServerHandler.sendPacket(new S37PacketStatistics(hashmap));
   }

   public boolean func_150879_e() {
      return this.field_150886_g;
   }
}

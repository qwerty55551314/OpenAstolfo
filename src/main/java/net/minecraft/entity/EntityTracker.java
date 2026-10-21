package net.minecraft.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.Callable;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.network.Packet;
import net.minecraft.util.IntHashMap;
import net.minecraft.util.ReportedException;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityTracker {
   private static final Logger logger = LogManager.getLogger();
   private final WorldServer theWorld;
   private Set<EntityTrackerEntry> trackedEntities = Sets.newHashSet();
   private IntHashMap<EntityTrackerEntry> trackedEntityHashTable = new IntHashMap<>();
   private int maxTrackingDistanceThreshold;

   public EntityTracker(WorldServer theWorldIn) {
      this.theWorld = theWorldIn;
      this.maxTrackingDistanceThreshold = theWorldIn.getMinecraftServer().getConfigurationManager().getEntityViewDistance();
   }

   public void trackEntity(Entity entityIn) {
      boolean flag = entityIn instanceof EntityPlayerMP;
      if (flag) {
         this.trackEntity(entityIn, 512, 2);
         EntityPlayerMP entityplayermp = (EntityPlayerMP)entityIn;

         for (EntityTrackerEntry entitytrackerentry : this.trackedEntities) {
            if (entitytrackerentry.trackedEntity != entityplayermp) {
               entitytrackerentry.updatePlayerEntity(entityplayermp);
            }
         }
      } else {
         boolean flag1 = entityIn instanceof EntityFishHook;
         if (flag1) {
            this.addEntityToTracker(entityIn, 64, 5, true);
         } else {
            boolean flag2 = entityIn instanceof EntityArrow;
            if (flag2) {
               this.addEntityToTracker(entityIn, 64, 20, false);
            } else {
               boolean flag3 = entityIn instanceof EntitySmallFireball;
               if (flag3) {
                  this.addEntityToTracker(entityIn, 64, 10, false);
               } else {
                  boolean flag4 = entityIn instanceof EntityFireball;
                  if (flag4) {
                     this.addEntityToTracker(entityIn, 64, 10, false);
                  } else {
                     boolean flag5 = entityIn instanceof EntitySnowball;
                     if (flag5) {
                        this.addEntityToTracker(entityIn, 64, 10, true);
                     } else {
                        boolean flag6 = entityIn instanceof EntityEnderPearl;
                        if (flag6) {
                           this.addEntityToTracker(entityIn, 64, 10, true);
                        } else {
                           boolean flag7 = entityIn instanceof EntityEnderEye;
                           if (flag7) {
                              this.addEntityToTracker(entityIn, 64, 4, true);
                           } else {
                              boolean flag8 = entityIn instanceof EntityEgg;
                              if (flag8) {
                                 this.addEntityToTracker(entityIn, 64, 10, true);
                              } else {
                                 boolean flag9 = entityIn instanceof EntityPotion;
                                 if (flag9) {
                                    this.addEntityToTracker(entityIn, 64, 10, true);
                                 } else {
                                    boolean flag10 = entityIn instanceof EntityExpBottle;
                                    if (flag10) {
                                       this.addEntityToTracker(entityIn, 64, 10, true);
                                    } else {
                                       boolean flag11 = entityIn instanceof EntityFireworkRocket;
                                       if (flag11) {
                                          this.addEntityToTracker(entityIn, 64, 10, true);
                                       } else {
                                          boolean flag12 = entityIn instanceof EntityItem;
                                          if (flag12) {
                                             this.addEntityToTracker(entityIn, 64, 20, true);
                                          } else {
                                             boolean flag13 = entityIn instanceof EntityMinecart;
                                             if (flag13) {
                                                this.addEntityToTracker(entityIn, 80, 3, true);
                                             } else {
                                                boolean flag14 = entityIn instanceof EntityBoat;
                                                if (flag14) {
                                                   this.addEntityToTracker(entityIn, 80, 3, true);
                                                } else {
                                                   boolean flag15 = entityIn instanceof EntitySquid;
                                                   if (flag15) {
                                                      this.addEntityToTracker(entityIn, 64, 3, true);
                                                   } else {
                                                      boolean flag16 = entityIn instanceof EntityWither;
                                                      if (flag16) {
                                                         this.addEntityToTracker(entityIn, 80, 3, false);
                                                      } else {
                                                         boolean flag17 = entityIn instanceof EntityBat;
                                                         if (flag17) {
                                                            this.addEntityToTracker(entityIn, 80, 3, false);
                                                         } else {
                                                            boolean flag18 = entityIn instanceof EntityDragon;
                                                            if (flag18) {
                                                               this.addEntityToTracker(entityIn, 160, 3, true);
                                                            } else {
                                                               boolean flag19 = entityIn instanceof IAnimals;
                                                               if (flag19) {
                                                                  this.addEntityToTracker(entityIn, 80, 3, true);
                                                               } else {
                                                                  boolean flag20 = entityIn instanceof EntityTNTPrimed;
                                                                  if (flag20) {
                                                                     this.addEntityToTracker(entityIn, 160, 10, true);
                                                                  } else {
                                                                     boolean flag21 = entityIn instanceof EntityFallingBlock;
                                                                     if (flag21) {
                                                                        this.addEntityToTracker(entityIn, 160, 20, true);
                                                                     } else {
                                                                        boolean flag22 = entityIn instanceof EntityHanging;
                                                                        if (flag22) {
                                                                           this.addEntityToTracker(entityIn, 160, Integer.MAX_VALUE, false);
                                                                        } else {
                                                                           boolean flag23 = entityIn instanceof EntityArmorStand;
                                                                           if (flag23) {
                                                                              this.addEntityToTracker(entityIn, 160, 3, true);
                                                                           } else {
                                                                              boolean flag24 = entityIn instanceof EntityXPOrb;
                                                                              if (flag24) {
                                                                                 this.addEntityToTracker(entityIn, 160, 20, true);
                                                                              } else if (entityIn instanceof EntityEnderCrystal) {
                                                                                 this.addEntityToTracker(entityIn, 256, Integer.MAX_VALUE, false);
                                                                              }
                                                                           }
                                                                        }
                                                                     }
                                                                  }
                                                               }
                                                            }
                                                         }
                                                      }
                                                   }
                                                }
                                             }
                                          }
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public void trackEntity(Entity entityIn, int trackingRange, int updateFrequency) {
      this.addEntityToTracker(entityIn, trackingRange, updateFrequency, false);
   }

   public void addEntityToTracker(Entity entityIn, int trackingRange, final int updateFrequency, boolean sendVelocityUpdates) {
      if (trackingRange > this.maxTrackingDistanceThreshold) {
         trackingRange = this.maxTrackingDistanceThreshold;
      }

      try {
         if (this.trackedEntityHashTable.containsItem(entityIn.getEntityId())) {
            throw new IllegalStateException("Entity is already tracked!");
         }

         EntityTrackerEntry entitytrackerentry = new EntityTrackerEntry(entityIn, trackingRange, updateFrequency, sendVelocityUpdates);
         this.trackedEntities.add(entitytrackerentry);
         this.trackedEntityHashTable.addKey(entityIn.getEntityId(), entitytrackerentry);
         entitytrackerentry.updatePlayerEntities(this.theWorld.playerEntities);
      } catch (Throwable throwable) {
         CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Adding entity to track");
         CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity To Track");
         crashreportcategory.addCrashSection("Tracking range", trackingRange + " blocks");
         crashreportcategory.addCrashSectionCallable("Update interval", new Callable<String>() {
            public String call() throws Exception {
               String s = "Once per " + updateFrequency + " ticks";
               if (updateFrequency == Integer.MAX_VALUE) {
                  s = "Maximum (" + s + ")";
               }

               return s;
            }
         });
         entityIn.addEntityCrashInfo(crashreportcategory);
         CrashReportCategory crashreportcategory1 = crashreport.makeCategory("Entity That Is Already Tracked");
         this.trackedEntityHashTable.lookup(entityIn.getEntityId()).trackedEntity.addEntityCrashInfo(crashreportcategory1);

         try {
            throw new ReportedException(crashreport);
         } catch (ReportedException reportedexception) {
            logger.error("\"Silently\" catching entity tracking error.", (Throwable)reportedexception);
         }
      }
   }

   public void untrackEntity(Entity entityIn) {
      if (entityIn instanceof EntityPlayerMP) {
         EntityPlayerMP entityplayermp = (EntityPlayerMP)entityIn;

         for (EntityTrackerEntry entitytrackerentry : this.trackedEntities) {
            entitytrackerentry.removeFromTrackedPlayers(entityplayermp);
         }
      }

      EntityTrackerEntry entitytrackerentry1 = this.trackedEntityHashTable.removeObject(entityIn.getEntityId());
      if (entitytrackerentry1 != null) {
         this.trackedEntities.remove(entitytrackerentry1);
         entitytrackerentry1.sendDestroyEntityPacketToTrackedPlayers();
      }
   }

   public void updateTrackedEntities() {
      ArrayList arraylist = Lists.newArrayList();

      for (EntityTrackerEntry entitytrackerentry : this.trackedEntities) {
         entitytrackerentry.updatePlayerList(this.theWorld.playerEntities);
         boolean flag = entitytrackerentry.playerEntitiesUpdated;
         if (flag && entitytrackerentry.trackedEntity instanceof EntityPlayerMP) {
            arraylist.add((EntityPlayerMP)entitytrackerentry.trackedEntity);
         }
      }

      for (int i = 0; i < arraylist.size(); i++) {
         EntityPlayerMP entityplayermp = (EntityPlayerMP)arraylist.get(i);

         for (EntityTrackerEntry entitytrackerentry1 : this.trackedEntities) {
            if (entitytrackerentry1.trackedEntity != entityplayermp) {
               entitytrackerentry1.updatePlayerEntity(entityplayermp);
            }
         }
      }
   }

   public void func_180245_a(EntityPlayerMP entityPlayerMP) {
      for (EntityTrackerEntry entitytrackerentry : this.trackedEntities) {
         if (entitytrackerentry.trackedEntity == entityPlayerMP) {
            entitytrackerentry.updatePlayerEntities(this.theWorld.playerEntities);
         } else {
            entitytrackerentry.updatePlayerEntity(entityPlayerMP);
         }
      }
   }

   public void sendToAllTrackingEntity(Entity entityIn, Packet packet) {
      EntityTrackerEntry entitytrackerentry = this.trackedEntityHashTable.lookup(entityIn.getEntityId());
      if (entitytrackerentry != null) {
         entitytrackerentry.sendPacketToTrackedPlayers(packet);
      }
   }

   public void func_151248_b(Entity entityIn, Packet packet) {
      EntityTrackerEntry entitytrackerentry = this.trackedEntityHashTable.lookup(entityIn.getEntityId());
      if (entitytrackerentry != null) {
         entitytrackerentry.func_151261_b(packet);
      }
   }

   public void removePlayerFromTrackers(EntityPlayerMP entityPlayerMP) {
      for (EntityTrackerEntry entitytrackerentry : this.trackedEntities) {
         entitytrackerentry.removeTrackedPlayerSymmetric(entityPlayerMP);
      }
   }

   public void func_85172_a(EntityPlayerMP entityPlayerMP, Chunk chunk) {
      for (EntityTrackerEntry entitytrackerentry : this.trackedEntities) {
         Entity entity = entitytrackerentry.trackedEntity;
         if (entity != entityPlayerMP) {
            int j = chunk.xPosition;
            int i = entitytrackerentry.trackedEntity.chunkCoordX;
            if (i == j && entitytrackerentry.trackedEntity.chunkCoordZ == chunk.zPosition) {
               entitytrackerentry.updatePlayerEntity(entityPlayerMP);
            }
         }
      }
   }
}

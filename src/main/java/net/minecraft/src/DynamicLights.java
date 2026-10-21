package net.minecraft.src;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;

public class DynamicLights {
   private static Map<Integer, DynamicLight> mapDynamicLights = new HashMap<>();
   private static long timeUpdateMs = 0L;
   private static final double MAX_DIST = 7.5;
   private static final double MAX_DIST_SQ = 56.25;
   private static final int LIGHT_LEVEL_MAX = 15;
   private static final int LIGHT_LEVEL_FIRE = 15;
   private static final int LIGHT_LEVEL_BLAZE = 10;
   private static final int LIGHT_LEVEL_MAGMA_CUBE = 8;
   private static final int LIGHT_LEVEL_MAGMA_CUBE_CORE = 13;
   private static final int LIGHT_LEVEL_GLOWSTONE_DUST = 8;
   private static final int LIGHT_LEVEL_PRISMARINE_CRYSTALS = 8;

   public static void entityAdded(Entity entityIn, RenderGlobal renderGlobal) {
   }

   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static void entityRemoved(Entity entityIn, RenderGlobal renderGlobal) {
      boolean flag = true;
      Map map = mapDynamicLights;
      synchronized (mapDynamicLights){} // $VF: monitorenter 

      try {
         DynamicLight dynamiclight = mapDynamicLights.remove(net.minecraft.util.IntegerCache.getInteger(entityIn.getEntityId()));
         if (dynamiclight != null) {
            dynamiclight.updateLitChunks(renderGlobal);
         }

         // $VF: monitorexit
      } finally {
         // $VF: monitorexit
      }
   }

   public static void update(RenderGlobal renderGlobal) {
      long i = System.currentTimeMillis();
      if (i >= timeUpdateMs + 50L) {
         timeUpdateMs = i;
         synchronized (mapDynamicLights) {
            updateMapDynamicLights(renderGlobal);
            if (mapDynamicLights.size() > 0) {
               for (DynamicLight dynamiclight : mapDynamicLights.values()) {
                  dynamiclight.update(renderGlobal);
               }
            }
         }
      }
   }

   private static void updateMapDynamicLights(RenderGlobal renderGlobal) {
      WorldClient worldclient = renderGlobal.getWorld();
      if (worldclient != null) {
         for (Entity entity : worldclient.getLoadedEntityList()) {
            int i = getLightLevel(entity);
            if (i > 0) {
               Integer integer = net.minecraft.util.IntegerCache.getInteger(entity.getEntityId());
               DynamicLight dynamiclight = mapDynamicLights.get(integer);
               if (dynamiclight == null) {
                  dynamiclight = new DynamicLight(entity);
                  mapDynamicLights.put(integer, dynamiclight);
               }
            } else {
               Integer integer1 = net.minecraft.util.IntegerCache.getInteger(entity.getEntityId());
               DynamicLight dynamiclight1 = mapDynamicLights.remove(integer1);
               if (dynamiclight1 != null) {
                  dynamiclight1.updateLitChunks(renderGlobal);
               }
            }
         }
      }
   }

   public static int getCombinedLight(BlockPos pos, int combinedLight) {
      double d0 = getLightLevel(pos);
      return getCombinedLight(d0, combinedLight);
   }

   public static int getCombinedLight(Entity entity, int combinedLight) {
      double d0 = (double)getLightLevel(entity);
      return getCombinedLight(d0, combinedLight);
   }

   public static int getCombinedLight(double lightPlayer, int combinedLight) {
      if (lightPlayer > 0.0) {
         int i = (int)(lightPlayer * 16.0);
         int j = combinedLight & 0xFF;
         if (i > j) {
            combinedLight &= -256;
            combinedLight |= i;
         }
      }

      return combinedLight;
   }

   public static double getLightLevel(BlockPos pos) {
      double d0 = 0.0;
      synchronized (mapDynamicLights) {
         for (DynamicLight dynamiclight : mapDynamicLights.values()) {
            int i = dynamiclight.getLastLightLevel();
            if (i > 0) {
               double d1 = dynamiclight.getLastPosX();
               double d2 = dynamiclight.getLastPosY();
               double d3 = dynamiclight.getLastPosZ();
               double d4 = (double)pos.getX() - d1;
               double d5 = (double)pos.getY() - d2;
               double d6 = (double)pos.getZ() - d3;
               double d7 = d4 * d4 + d5 * d5 + d6 * d6;
               if (dynamiclight.isUnderwater() && !Config.isClearWater()) {
                  i = Config.limit(i - 2, 0, 15);
                  d7 *= 2.0;
               }

               if (d7 <= 56.25) {
                  double d8 = Math.sqrt(d7);
                  double d9 = 1.0 - d8 / 7.5;
                  double d10 = d9 * (double)i;
                  if (d10 > d0) {
                     d0 = d10;
                  }
               }
            }
         }
      }

      return Config.limit(d0, 0.0, 15.0);
   }

   public static int getLightLevel(ItemStack itemStack) {
      if (itemStack == null) {
         return 0;
      } else {
         Item item = itemStack.getItem();
         if (item instanceof ItemBlock) {
            ItemBlock itemblock = (ItemBlock)item;
            Block block = itemblock.getBlock();
            if (block != null) {
               return block.getLightValue();
            }
         }

         Item item1 = Items.lava_bucket;
         int i;
         if (item == item1) {
            i = Blocks.lava.getLightValue();
         } else {
            Item item2 = Items.blaze_rod;
            if (item != item2) {
               Item item3 = Items.blaze_powder;
               if (item != item3) {
                  Item item4 = Items.glowstone_dust;
                  if (item != item4) {
                     Item item5 = Items.prismarine_crystals;
                     if (item == item5) {
                        i = 8;
                     } else {
                        Item item6 = Items.magma_cream;
                        i = item == item6 ? 8 : (item == Items.nether_star ? Blocks.beacon.getLightValue() / 2 : 0);
                     }

                     return i;
                  }

                  return 8;
               }
            }

            i = 10;
         }

         return i;
      }
   }

   public static int getLightLevel(Entity entity) {
      if (entity == Config.getMinecraft().getRenderViewEntity() && !Config.isDynamicHandLight()) {
         return 0;
      } else {
         boolean flag = entity instanceof EntityPlayer;
         if (flag) {
            EntityPlayer entityplayer = (EntityPlayer)entity;
            if (entityplayer.isSpectator()) {
               return 0;
            }
         }

         boolean flag1 = entity.isBurning();
         if (flag1) {
            return 15;
         } else {
            boolean flag2 = entity instanceof EntityFireball;
            if (flag2) {
               return 15;
            } else {
               boolean flag3 = entity instanceof EntityTNTPrimed;
               if (flag3) {
                  return 15;
               } else {
                  boolean flag4 = entity instanceof EntityBlaze;
                  if (flag4) {
                     EntityBlaze entityblaze = (EntityBlaze)entity;
                     return entityblaze.func_70845_n() ? 15 : 10;
                  } else {
                     boolean flag5 = entity instanceof EntityMagmaCube;
                     if (flag5) {
                        EntityMagmaCube entitymagmacube = (EntityMagmaCube)entity;
                        return (double)entitymagmacube.squishFactor > 0.6 ? 13 : 8;
                     } else {
                        boolean flag6 = entity instanceof EntityCreeper;
                        if (flag6) {
                           EntityCreeper entitycreeper = (EntityCreeper)entity;
                           if ((double)entitycreeper.getCreeperFlashIntensity(0.0F) > 0.001) {
                              return 15;
                           }
                        }

                        boolean flag7 = entity instanceof EntityLivingBase;
                        if (flag7) {
                           EntityLivingBase entitylivingbase = (EntityLivingBase)entity;
                           ItemStack itemstack2 = entitylivingbase.getHeldItem();
                           int i = getLightLevel(itemstack2);
                           ItemStack itemstack1 = entitylivingbase.getEquipmentInSlot(4);
                           int j = getLightLevel(itemstack1);
                           return Math.max(i, j);
                        } else {
                           boolean flag8 = entity instanceof EntityItem;
                           if (flag8) {
                              EntityItem entityitem = (EntityItem)entity;
                              ItemStack itemstack = getItemStack(entityitem);
                              return getLightLevel(itemstack);
                           } else {
                              return 0;
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static void removeLights(RenderGlobal renderGlobal) {
      boolean flag = false;
      Map map = mapDynamicLights;
      synchronized (mapDynamicLights){} // $VF: monitorenter 

      try {
         Collection collection = mapDynamicLights.values();
         Iterator iterator = collection.iterator();

         while (iterator.hasNext()) {
            DynamicLight dynamiclight = (DynamicLight)iterator.next();
            iterator.remove();
            dynamiclight.updateLitChunks(renderGlobal);
         }

         // $VF: monitorexit
      } finally {
         // $VF: monitorexit
      }
   }

   public static void clear() {
      synchronized (mapDynamicLights) {
         mapDynamicLights.clear();
      }
   }

   public static int getCount() {
      synchronized (mapDynamicLights) {
         return mapDynamicLights.size();
      }
   }

   public static ItemStack getItemStack(EntityItem entityItem) {
      return entityItem.getDataWatcher().getWatchableObjectItemStack(10);
   }
}

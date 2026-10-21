package net.minecraft.potion;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;

public class Potion {
   public static final Potion[] potionTypes = new Potion[32];
   private static final Map<ResourceLocation, Potion> X = Maps.newHashMap();
   public static final Potion field_180151_b = null;
   public static final Potion moveSpeed = new Potion(1, new ResourceLocation("speed"), false, 8171462)
      .setPotionName("potion.moveSpeed")
      .setIconIndex(0, 0)
      .registerPotionAttributeModifier(SharedMonsterAttributes.movementSpeed, "91AEAA56-376B-4498-935B-2F7F68070635", 0.2F, 2);
   public static final Potion moveSlowdown = new Potion(2, new ResourceLocation("slowness"), true, 5926017)
      .setPotionName("potion.moveSlowdown")
      .setIconIndex(1, 0)
      .registerPotionAttributeModifier(SharedMonsterAttributes.movementSpeed, "7107DE5E-7CE8-4030-940E-514C1F160890", -0.15F, 2);
   public static final Potion digSpeed = new Potion(3, new ResourceLocation("haste"), false, 14270531)
      .setPotionName("potion.digSpeed")
      .setIconIndex(2, 0)
      .setEffectiveness(1.5);
   public static final Potion digSlowdown = new Potion(4, new ResourceLocation("mining_fatigue"), true, 4866583)
      .setPotionName("potion.digSlowDown")
      .setIconIndex(3, 0);
   public static final Potion damageBoost = new PotionAttackDamage(5, new ResourceLocation("strength"), false, 9643043)
      .setPotionName("potion.damageBoost")
      .setIconIndex(4, 0)
      .registerPotionAttributeModifier(SharedMonsterAttributes.attackDamage, "648D7064-6A60-4F59-8ABE-C2C23A6DD7A9", 2.5, 2);
   public static final Potion heal = new PotionHealth(6, new ResourceLocation("instant_health"), false, 16262179).setPotionName("potion.heal");
   public static final Potion harm = new PotionHealth(7, new ResourceLocation("instant_damage"), true, 4393481).setPotionName("potion.harm");
   public static final Potion jump = new Potion(8, new ResourceLocation("jump_boost"), false, 2293580).setPotionName("potion.jump").setIconIndex(2, 1);
   public static final Potion confusion = new Potion(9, new ResourceLocation("nausea"), true, 5578058)
      .setPotionName("potion.confusion")
      .setIconIndex(3, 1)
      .setEffectiveness(0.25);
   public static final Potion regeneration = new Potion(10, new ResourceLocation("regeneration"), false, 13458603)
      .setPotionName("potion.regeneration")
      .setIconIndex(7, 0)
      .setEffectiveness(0.25);
   public static final Potion resistance = new Potion(11, new ResourceLocation("resistance"), false, 10044730)
      .setPotionName("potion.resistance")
      .setIconIndex(6, 1);
   public static final Potion fireResistance = new Potion(12, new ResourceLocation("fire_resistance"), false, 14981690)
      .setPotionName("potion.fireResistance")
      .setIconIndex(7, 1);
   public static final Potion waterBreathing = new Potion(13, new ResourceLocation("water_breathing"), false, 3035801)
      .setPotionName("potion.waterBreathing")
      .setIconIndex(0, 2);
   public static final Potion invisibility = new Potion(14, new ResourceLocation("invisibility"), false, 8356754)
      .setPotionName("potion.invisibility")
      .setIconIndex(0, 1);
   public static final Potion blindness = new Potion(15, new ResourceLocation("blindness"), true, 2039587)
      .setPotionName("potion.blindness")
      .setIconIndex(5, 1)
      .setEffectiveness(0.25);
   public static final Potion nightVision = new Potion(16, new ResourceLocation("night_vision"), false, 2039713)
      .setPotionName("potion.nightVision")
      .setIconIndex(4, 1);
   public static final Potion hunger = new Potion(17, new ResourceLocation("hunger"), true, 5797459).setPotionName("potion.hunger").setIconIndex(1, 1);
   public static final Potion weakness = new PotionAttackDamage(18, new ResourceLocation("weakness"), true, 4738376)
      .setPotionName("potion.weakness")
      .setIconIndex(5, 0)
      .registerPotionAttributeModifier(SharedMonsterAttributes.attackDamage, "22653B89-116E-49DC-9B6B-9971489B5BE5", 2.0, 0);
   public static final Potion poison = new Potion(19, new ResourceLocation("poison"), true, 5149489)
      .setPotionName("potion.poison")
      .setIconIndex(6, 0)
      .setEffectiveness(0.25);
   public static final Potion wither = new Potion(20, new ResourceLocation("wither"), true, 3484199)
      .setPotionName("potion.wither")
      .setIconIndex(1, 2)
      .setEffectiveness(0.25);
   public static final Potion healthBoost = new PotionHealthBoost(21, new ResourceLocation("health_boost"), false, 16284963)
      .setPotionName("potion.healthBoost")
      .setIconIndex(2, 2)
      .registerPotionAttributeModifier(SharedMonsterAttributes.maxHealth, "5D6F0BA2-1186-46AC-B896-C61C5CEE99CC", 4.0, 0);
   public static final Potion absorption = new PotionAbsorption(22, new ResourceLocation("absorption"), false, 2445989)
      .setPotionName("potion.absorption")
      .setIconIndex(2, 2);
   public static final Potion saturation = new PotionHealth(23, new ResourceLocation("saturation"), false, 16262179).setPotionName("potion.saturation");
   public static final Potion field_180153_z = null;
   public static final Potion field_180147_A = null;
   public static final Potion field_180148_B = null;
   public static final Potion field_180149_C = null;
   public static final Potion field_180143_D = null;
   public static final Potion field_180144_E = null;
   public static final Potion field_180145_F = null;
   public static final Potion field_180146_G = null;
   public final int id;
   private final Map<IAttribute, AttributeModifier> attributeModifierMap = Maps.newHashMap();
   private final boolean isBadEffect;
   private final int liquidColor;
   private String name = "";
   private int statusIconIndex = -1;
   private double effectiveness;
   private boolean usable;

   public Potion(int potionID, ResourceLocation location, boolean badEffect, int potionColor) {
      this.id = potionID;
      potionTypes[potionID] = this;
      X.put(location, this);
      this.isBadEffect = badEffect;
      if (badEffect) {
         this.effectiveness = 0.5;
      } else {
         this.effectiveness = 1.0;
      }

      this.liquidColor = potionColor;
   }

   public static Potion getPotionFromResourceLocation(String location) {
      return X.get(new ResourceLocation(location));
   }

   public static Set<ResourceLocation> getPotionLocations() {
      return X.keySet();
   }

   public Potion setIconIndex(int i, int j) {
      this.statusIconIndex = i + j * 8;
      return this;
   }

   public int getId() {
      return this.id;
   }

   public void performEffect(EntityLivingBase entityLivingBaseIn, int i) {
      int j = regeneration.id;
      int i1 = this.id;
      if (i1 == j) {
         if (entityLivingBaseIn.getHealth() < entityLivingBaseIn.getMaxHealth()) {
            entityLivingBaseIn.heal(1.0F);
         }
      } else {
         int l = poison.id;
         int k = this.id;
         if (k == l) {
            if (entityLivingBaseIn.getHealth() > 1.0F) {
               entityLivingBaseIn.attackEntityFrom(DamageSource.magic, 1.0F);
            }
         } else {
            int j1 = wither.id;
            int i1x = this.id;
            if (i1x == j1) {
               entityLivingBaseIn.attackEntityFrom(DamageSource.wither, 1.0F);
            } else {
               int l1 = hunger.id;
               int k1 = this.id;
               if (k1 == l1 && entityLivingBaseIn instanceof EntityPlayer) {
                  ((EntityPlayer)entityLivingBaseIn).addExhaustion(0.025F * (float)(i + 1));
               } else {
                  int j2 = saturation.id;
                  int i2 = this.id;
                  if (i2 != j2 || !(entityLivingBaseIn instanceof EntityPlayer)) {
                     int l2 = heal.id;
                     int k2 = this.id;
                     if (k2 != l2 || entityLivingBaseIn.isEntityUndead()) {
                        int j3 = harm.id;
                        int i3 = this.id;
                        if (i3 != j3 || !entityLivingBaseIn.isEntityUndead()) {
                           int l3 = harm.id;
                           int k3 = this.id;
                           if (k3 != l3 || entityLivingBaseIn.isEntityUndead()) {
                              if (this.id != heal.id) {
                                 return;
                              }

                              boolean flag = entityLivingBaseIn.isEntityUndead();
                              if (!flag) {
                                 return;
                              }
                           }

                           entityLivingBaseIn.attackEntityFrom(DamageSource.magic, (float)(6 << i));
                           return;
                        }
                     }

                     entityLivingBaseIn.heal((float)Math.max(4 << i, 0));
                  } else if (!entityLivingBaseIn.worldObj.isRemote) {
                     ((EntityPlayer)entityLivingBaseIn).getFoodStats().addStats(i + 1, 1.0F);
                  }
               }
            }
         }
      }
   }

   public void affectEntity(Entity entity, Entity entity2, EntityLivingBase entityLivingBaseIn, int i, double d) {
      int k = heal.id;
      int j = this.id;
      if (j != k || entityLivingBaseIn.isEntityUndead()) {
         int i1 = harm.id;
         int l = this.id;
         if (l != i1 || !entityLivingBaseIn.isEntityUndead()) {
            int k1 = harm.id;
            int j1 = this.id;
            if (j1 != k1 || entityLivingBaseIn.isEntityUndead()) {
               if (this.id != heal.id) {
                  return;
               }

               boolean flag = entityLivingBaseIn.isEntityUndead();
               if (!flag) {
                  return;
               }
            }

            int l1 = (int)(d * (double)(6 << i) + 0.5);
            if (entity == null) {
               entityLivingBaseIn.attackEntityFrom(DamageSource.magic, (float)l1);
            } else {
               entityLivingBaseIn.attackEntityFrom(DamageSource.causeIndirectMagicDamage(entity, entity2), (float)l1);
            }

            return;
         }
      }

      int i1 = (int)(d * (double)(4 << i) + 0.5);
      entityLivingBaseIn.heal((float)i1);
   }

   public boolean isInstant() {
      return false;
   }

   public boolean isReady(int i, int j) {
      int k = regeneration.id;
      int j1 = this.id;
      if (j1 == k) {
         int i2 = 50 >> j;
         return i2 > 0 ? i % i2 == 0 : true;
      } else {
         int i1 = poison.id;
         int l = this.id;
         if (l == i1) {
            int l1 = 25 >> j;
            return l1 > 0 ? i % l1 == 0 : true;
         } else {
            int k1 = wither.id;
            int j1x = this.id;
            if (j1x == k1) {
               int i1x = 40 >> j;
               return i1x > 0 ? i % i1x == 0 : true;
            } else {
               return this.id == hunger.id;
            }
         }
      }
   }

   public Potion setPotionName(String nameIn) {
      this.name = nameIn;
      return this;
   }

   public String getName() {
      return this.name;
   }

   public boolean hasStatusIcon() {
      return this.statusIconIndex >= 0;
   }

   public int getStatusIconIndex() {
      return this.statusIconIndex;
   }

   public boolean isBadEffect() {
      return this.isBadEffect;
   }

   public static String getDurationString(PotionEffect effect) {
      if (effect.getIsPotionDurationMax()) {
         return "**:**";
      } else {
         int i = effect.getDuration();
         return StringUtils.ticksToElapsedTime(i);
      }
   }

   public Potion setEffectiveness(double effectivenessIn) {
      this.effectiveness = effectivenessIn;
      return this;
   }

   public double getEffectiveness() {
      return this.effectiveness;
   }

   public boolean isUsable() {
      return this.usable;
   }

   public int getLiquidColor() {
      return this.liquidColor;
   }

   public Potion registerPotionAttributeModifier(IAttribute iAttribute, String string, double d, int i) {
      AttributeModifier attributemodifier = new AttributeModifier(UUID.fromString(string), this.getName(), d, i);
      this.attributeModifierMap.put(iAttribute, attributemodifier);
      return this;
   }

   public Map<IAttribute, AttributeModifier> getAttributeModifierMap() {
      return this.attributeModifierMap;
   }

   public void removeAttributesModifiersFromEntity(EntityLivingBase entityLivingBaseIn, BaseAttributeMap baseAttributeMap, int amplifier) {
      for (Entry entry : this.attributeModifierMap.entrySet()) {
         IAttributeInstance iattributeinstance = baseAttributeMap.getAttributeInstance((IAttribute)entry.getKey());
         if (iattributeinstance != null) {
            iattributeinstance.removeModifier((AttributeModifier)entry.getValue());
         }
      }
   }

   public void applyAttributesModifiersToEntity(EntityLivingBase entityLivingBaseIn, BaseAttributeMap baseAttributeMap, int amplifier) {
      for (Entry entry : this.attributeModifierMap.entrySet()) {
         IAttributeInstance iattributeinstance = baseAttributeMap.getAttributeInstance((IAttribute)entry.getKey());
         if (iattributeinstance != null) {
            AttributeModifier attributemodifier = (AttributeModifier)entry.getValue();
            iattributeinstance.removeModifier(attributemodifier);
            iattributeinstance.applyModifier(
               new AttributeModifier(
                  attributemodifier.getID(),
                  this.getName() + " " + amplifier,
                  this.getAttributeModifierAmount(amplifier, attributemodifier),
                  attributemodifier.getOperation()
               )
            );
         }
      }
   }

   public double getAttributeModifierAmount(int i, AttributeModifier modifier) {
      return modifier.getAmount() * (double)(i + 1);
   }
}

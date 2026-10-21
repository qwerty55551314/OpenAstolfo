package net.hypixel.api.pets;

import net.minecraft.entity.Entity;

public enum PetAttribute {
   HUNGER,
   THIRST,
   EXERCISE;

   private static Entity[] k;

   public int getDecay() {
      return 1;
   }

   static {
      h(new Entity[3]);
   }

   public static void h(Entity[] entityArray) {
      k = entityArray;
   }

   public static Entity[] U() {
      return k;
   }
}

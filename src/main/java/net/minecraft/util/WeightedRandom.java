package net.minecraft.util;

import java.util.Collection;
import java.util.Random;

public class WeightedRandom {
   public static int getTotalWeight(Collection<? extends WeightedRandom.Item> collection) {
      int i = 0;

      for (WeightedRandom.Item weightedrandom$item : collection) {
         i += weightedrandom$item.itemWeight;
      }

      return i;
   }

   public static <T extends WeightedRandom.Item> T getRandomItem(Random randomIn, Collection<T> collection, int totalWeight) {
      if (totalWeight <= 0) {
         throw new IllegalArgumentException();
      } else {
         int i = randomIn.nextInt(totalWeight);
         return getRandomItem(collection, i);
      }
   }

   public static <T extends WeightedRandom.Item> T getRandomItem(Collection<T> collection, int weight) {
      for (WeightedRandom.Item weightedrandom$item : collection) {
         weight -= weightedrandom$item.itemWeight;
         if (weight < 0) {
            return (T)weightedrandom$item;
         }
      }

      return null;
   }

   public static <T extends WeightedRandom.Item> T getRandomItem(Random randomIn, Collection<T> collection) {
      return getRandomItem(randomIn, collection, getTotalWeight(collection));
   }

   public static class Item {
      public int itemWeight;

      public Item(int itemWeightIn) {
         this.itemWeight = itemWeightIn;
      }
   }
}

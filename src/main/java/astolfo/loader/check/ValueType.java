package astolfo.loader.check;

import astolfo.util.collection.ShapedMap;
import net.minecraft.block.Block;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.item.ItemFishFood;
import net.minecraft.src.BetterGrass;
import oshi.util.tuples.Pair;

public enum ValueType {
   A(EntitySkeleton.class),
   B(Block.class),
   C(Integer.class),
   D(NullPointerException.class),
   E(System.class),
   F(Pair.class),
   G(ItemFishFood.class),
   H(Package.class),
   I(BetterGrass.class),
   J(EntityPig.class),
   K(EntitySkeleton.class);

   private final Class<?> type;
   public static final ShapedMap<Class<?>, ValueType> compactHashMap = new ShapedMap<>();
   private static final ValueType[] VALUES = new ValueType[]{A, B, C, D, E, F, G, H, I, J, K};

   private ValueType(Class<?> classValue) {
      this.type = classValue;
   }

   public Class<?> getType() {
      return this.type;
   }

   static {
      for (ValueType valuetype : values()) {
         compactHashMap.put(valuetype.type, valuetype);
      }
   }
}

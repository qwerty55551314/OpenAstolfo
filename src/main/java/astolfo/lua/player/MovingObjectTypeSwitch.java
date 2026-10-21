package astolfo.lua.player;

import net.minecraft.util.MovingObjectPosition;

// $VF: synthetic class
public class MovingObjectTypeSwitch {
   public static final int[] $SwitchMap$net$minecraft$util$MovingObjectPosition$MovingObjectType = new int[MovingObjectPosition.MovingObjectType.values().length];

   static {
      try {
         $SwitchMap$net$minecraft$util$MovingObjectPosition$MovingObjectType[MovingObjectPosition.MovingObjectType.ENTITY.ordinal()] = 1;
      } catch (NoSuchFieldError nosuchfielderror2) {
      }

      try {
         $SwitchMap$net$minecraft$util$MovingObjectPosition$MovingObjectType[MovingObjectPosition.MovingObjectType.MISS.ordinal()] = 2;
      } catch (NoSuchFieldError nosuchfielderror1) {
      }

      try {
         $SwitchMap$net$minecraft$util$MovingObjectPosition$MovingObjectType[MovingObjectPosition.MovingObjectType.BLOCK.ordinal()] = 3;
      } catch (NoSuchFieldError nosuchfielderror) {
      }
   }
}

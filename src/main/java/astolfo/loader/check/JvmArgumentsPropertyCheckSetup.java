package astolfo.loader.check;

import net.minecraft.entity.Entity;

public class JvmArgumentsPropertyCheckSetup {
   private final JvmArgumentsPropertyListOwner listOwner = new JvmArgumentsPropertyListOwner();
   private final StringListHolder stringListHolder = new StringListHolder(this.listOwner.getPatternContainer());
   private final JvmArgumentsPropertyCheck jvmArgumentsPropertyCheck = new JvmArgumentsPropertyCheck(this.stringListHolder);
   private static Entity[] M;

   public StringListHolder getStringListHolder() {
      return this.stringListHolder;
   }

   public JvmArgumentsPropertyCheck getJvmArgumentsPropertyCheck() {
      return this.jvmArgumentsPropertyCheck;
   }

   public static void j(Entity[] entityArray) {
      M = entityArray;
   }

   public static Entity[] J() {
      return M;
   }
}

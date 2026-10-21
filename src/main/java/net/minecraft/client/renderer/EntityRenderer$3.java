package net.minecraft.client.renderer;

import java.util.concurrent.Callable;
import org.lwjgl.input.Mouse;

class EntityRenderer$3 implements Callable {
   final int field_181048_a;
   final int field_181049_b;
   final EntityRenderer field_90028_b;
   private static final String b = "Scaled: (%d, %d). Absolute: (%d, %d)";

   EntityRenderer$3(EntityRenderer entityRenderer, int i, int i2) {
      this.field_90028_b = entityRenderer;
      this.field_181048_a = i;
      this.field_181049_b = i2;
   }

   public String call() throws Exception {
      return String.format(b, this.field_181048_a, this.field_181049_b, Mouse.getX(), Mouse.getY());
   }
}

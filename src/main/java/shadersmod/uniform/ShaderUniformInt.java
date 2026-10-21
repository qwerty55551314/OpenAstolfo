package shadersmod.uniform;

import org.lwjgl.opengl.ARBShaderObjects;
import shadersmod.client.Shaders;

public class ShaderUniformInt extends ShaderUniformBase {
   private int value = -1;

   public ShaderUniformInt(String name) {
      super(name);
   }

   @Override
   public void onProgramChanged() {
      this.value = -1;
   }

   public void setValue(int value) {
      int i = this.getLocation();
      if (i >= 0) {
         int j = this.value;
         if (j != value) {
            ARBShaderObjects.glUniform1iARB(this.getLocation(), value);
            Shaders.checkGLError(this.getName());
            this.value = value;
         }
      }
   }

   public int getValue() {
      return this.value;
   }
}

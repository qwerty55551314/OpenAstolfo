package net.minecraft.client.shader;

import com.google.common.collect.Maps;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.Map;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.util.JsonException;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.BufferUtils;

public class ShaderLoader {
   private final ShaderLoader.ShaderType shaderType;
   private final String shaderFilename;
   private int shader;
   private int shaderAttachCount = 0;

   private ShaderLoader(ShaderLoader.ShaderType typeIn, int shaderId, String filename) {
      this.shaderType = typeIn;
      this.shader = shaderId;
      this.shaderFilename = filename;
   }

   public void attachShader(ShaderManager manager) {
      this.shaderAttachCount++;
      OpenGlHelper.glAttachShader(manager.getProgram(), this.shader);
   }

   public void deleteShader(ShaderManager manager) {
      boolean flag = false;
      this.shaderAttachCount--;
      if (this.shaderAttachCount <= 0) {
         OpenGlHelper.glDeleteShader(this.shader);
         this.shaderType.getLoadedShaders().remove(this.shaderFilename);
      }
   }

   public String getShaderFilename() {
      return this.shaderFilename;
   }

   public static ShaderLoader loadShader(IResourceManager resourceManager, ShaderLoader.ShaderType typeIn, String filename) throws IOException {
      ShaderLoader shaderloader = typeIn.getLoadedShaders().get(filename);
      if (shaderloader == null) {
         ResourceLocation resourcelocation = new ResourceLocation("shaders/program/" + filename + typeIn.getShaderExtension());
         BufferedInputStream bufferedinputstream = new BufferedInputStream(resourceManager.getResource(resourcelocation).getInputStream());
         byte[] abyte = toByteArray(bufferedinputstream);
         ByteBuffer bytebuffer = BufferUtils.createByteBuffer(abyte.length);
         bytebuffer.put(abyte);
         ((Buffer)bytebuffer).position(0);
         int i = OpenGlHelper.glCreateShader(typeIn.getShaderMode());
         OpenGlHelper.glShaderSource(i, bytebuffer);
         OpenGlHelper.glCompileShader(i);
         if (OpenGlHelper.glGetShaderi(i, OpenGlHelper.GL_COMPILE_STATUS) == 0) {
            String s = StringUtils.trim(OpenGlHelper.glGetShaderInfoLog(i, 32768));
            JsonException jsonexception = new JsonException("Couldn't compile " + typeIn.getShaderName() + " program: " + s);
            jsonexception.func_151381_b(resourcelocation.getResourcePath());
            throw jsonexception;
         }

         shaderloader = new ShaderLoader(typeIn, i, filename);
         typeIn.getLoadedShaders().put(filename, shaderloader);
      }

      return shaderloader;
   }

   public static byte[] toByteArray(BufferedInputStream bufferedInputStream) throws IOException {
      byte[] abyte;
      try {
         abyte = IOUtils.toByteArray(bufferedInputStream);
      } finally {
         bufferedInputStream.close();
      }

      return abyte;
   }

   public static enum ShaderType {
      VERTEX("vertex", ".vsh", OpenGlHelper.GL_VERTEX_SHADER),
      FRAGMENT("fragment", ".fsh", OpenGlHelper.GL_FRAGMENT_SHADER);

      private final String shaderName;
      private final String shaderExtension;
      private final int shaderMode;
      private final Map<String, ShaderLoader> loadedShaders = Maps.newHashMap();

      private ShaderType(String string2, String string3, int i2) {
         this.shaderName = string2;
         this.shaderExtension = string3;
         this.shaderMode = i2;
      }

      public String getShaderName() {
         return this.shaderName;
      }

      public String getShaderExtension() {
         return this.shaderExtension;
      }

      public int getShaderMode() {
         return this.shaderMode;
      }

      public Map<String, ShaderLoader> getLoadedShaders() {
         return this.loadedShaders;
      }
   }
}

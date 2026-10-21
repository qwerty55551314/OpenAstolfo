package net.minecraft.client.renderer.vertex;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VertexFormatElement {
   private static final Logger LOGGER = LogManager.getLogger();
   private final VertexFormatElement.EnumType type;
   private final VertexFormatElement.EnumUsage usage;
   private int index;
   private int elementCount;
   private static int i;
   private static final String b = "Multiple vertex elements of the same type other than UVs are not supported. Forcing type to UV.";

   public VertexFormatElement(int indexIn, VertexFormatElement.EnumType typeIn, VertexFormatElement.EnumUsage usageIn, int count) {
      if (!this.func_177372_a(indexIn, usageIn)) {
         LOGGER.warn(b);
         this.usage = VertexFormatElement.EnumUsage.UV;
      } else {
         this.usage = usageIn;
      }

      this.type = typeIn;
      this.index = indexIn;
      this.elementCount = count;
   }

   private final boolean func_177372_a(int i, VertexFormatElement.EnumUsage enumUsage) {
      return i == 0 || enumUsage == VertexFormatElement.EnumUsage.UV;
   }

   public final VertexFormatElement.EnumType getType() {
      return this.type;
   }

   public final VertexFormatElement.EnumUsage getUsage() {
      return this.usage;
   }

   public final int getElementCount() {
      return this.elementCount;
   }

   public final int getIndex() {
      return this.index;
   }

   @Override
   public String toString() {
      return this.elementCount + "," + this.usage.getDisplayName() + "," + this.type.getDisplayName();
   }

   public final int getSize() {
      return this.type.getSize() * this.elementCount;
   }

   public final boolean isPositionElement() {
      return this.usage == VertexFormatElement.EnumUsage.POSITION;
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else {
         if (object != null) {
            Class oclass = this.getClass();
            if (oclass == object.getClass()) {
               VertexFormatElement vertexformatelement = (VertexFormatElement)object;
               int j = vertexformatelement.elementCount;
               int ix = this.elementCount;
               return ix != j
                  ? false
                  : (
                     this.index != vertexformatelement.index
                        ? false
                        : (this.type != vertexformatelement.type ? false : this.usage == vertexformatelement.usage)
                  );
            }
         }

         return false;
      }
   }

   @Override
   public int hashCode() {
      int ix = this.type.hashCode();
      ix = 31 * ix + this.usage.hashCode();
      ix = 31 * ix + this.index;
      return 31 * ix + this.elementCount;
   }

   public static int F() {
      return 48;
   }

   public static enum EnumType {
      FLOAT(4, "Float", 5126),
      UBYTE(1, "Unsigned Byte", 5121),
      BYTE(1, "Byte", 5120),
      USHORT(2, "Unsigned Short", 5123),
      SHORT(2, "Short", 5122),
      UINT(4, "Unsigned Int", 5125),
      INT(4, "Int", 5124);

      private final int size;
      private final String displayName;
      private final int glConstant;

      private EnumType(int i2, String string2, int i3) {
         this.size = i2;
         this.displayName = string2;
         this.glConstant = i3;
      }

      public int getSize() {
         return this.size;
      }

      public String getDisplayName() {
         return this.displayName;
      }

      public int getGlConstant() {
         return this.glConstant;
      }
   }

   public static enum EnumUsage {
      POSITION("Position"),
      NORMAL("Normal"),
      COLOR("Vertex Color"),
      UV("UV"),
      MATRIX("Bone Matrix"),
      BLEND_WEIGHT("Blend Weight"),
      PADDING("Padding");

      private final String displayName;

      private EnumUsage(String string2) {
         this.displayName = string2;
      }

      public String getDisplayName() {
         return this.displayName;
      }
   }
}

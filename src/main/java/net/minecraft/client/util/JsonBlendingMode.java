package net.minecraft.client.util;

import com.google.gson.JsonObject;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.JsonUtils;
import org.lwjgl.opengl.GL14;

public class JsonBlendingMode {
   private static JsonBlendingMode field_148118_a = null;
   private final int field_148116_b;
   private final int field_148117_c;
   private final int field_148114_d;
   private final int field_148115_e;
   private final int field_148112_f;
   private final boolean field_148113_g;
   private final boolean field_148119_h;

   private JsonBlendingMode(boolean bl, boolean bl2, int i, int j, int k, int l, int m) {
      this.field_148113_g = bl;
      this.field_148116_b = i;
      this.field_148114_d = j;
      this.field_148117_c = k;
      this.field_148115_e = l;
      this.field_148119_h = bl2;
      this.field_148112_f = m;
   }

   public JsonBlendingMode() {
      this(false, true, 1, 0, 1, 0, 32774);
   }

   public JsonBlendingMode(int i, int j, int k) {
      this(false, false, i, j, i, j, k);
   }

   public JsonBlendingMode(int i, int j, int k, int l, int m) {
      this(true, false, i, j, k, l, m);
   }

   public void func_148109_a() {
      if (!this.equals(field_148118_a)) {
         JsonBlendingMode jsonblendingmode = field_148118_a;
         if (jsonblendingmode == null || this.field_148119_h != field_148118_a.func_148111_b()) {
            field_148118_a = this;
            if (this.field_148119_h) {
               GlStateManager.disableBlend();
               return;
            }

            GlStateManager.enableBlend();
         }

         GL14.glBlendEquation(this.field_148112_f);
         boolean flag = this.field_148113_g;
         if (flag) {
            GlStateManager.tryBlendFuncSeparate(this.field_148116_b, this.field_148114_d, this.field_148117_c, this.field_148115_e);
         } else {
            GlStateManager.blendFunc(this.field_148116_b, this.field_148114_d);
         }
      }
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else if (!(object instanceof JsonBlendingMode)) {
         return false;
      } else {
         JsonBlendingMode jsonblendingmode = (JsonBlendingMode)object;
         int j = jsonblendingmode.field_148112_f;
         int i = this.field_148112_f;
         boolean flag4;
         if (i != j) {
            flag4 = false;
         } else {
            int l = jsonblendingmode.field_148115_e;
            int k = this.field_148115_e;
            if (k != l) {
               flag4 = false;
            } else {
               int j1 = jsonblendingmode.field_148114_d;
               int i1 = this.field_148114_d;
               if (i1 != j1) {
                  flag4 = false;
               } else {
                  boolean flag1 = jsonblendingmode.field_148119_h;
                  boolean flag = this.field_148119_h;
                  if (flag != flag1) {
                     flag4 = false;
                  } else {
                     boolean flag3 = jsonblendingmode.field_148113_g;
                     boolean flag2 = this.field_148113_g;
                     if (flag2 != flag3) {
                        flag4 = false;
                     } else {
                        int l1 = jsonblendingmode.field_148117_c;
                        int k1 = this.field_148117_c;
                        flag4 = k1 != l1 ? false : this.field_148116_b == jsonblendingmode.field_148116_b;
                     }
                  }
               }
            }
         }

         return flag4;
      }
   }

   @Override
   public int hashCode() {
      int i = this.field_148116_b;
      i = 31 * i + this.field_148117_c;
      i = 31 * i + this.field_148114_d;
      i = 31 * i + this.field_148115_e;
      i = 31 * i + this.field_148112_f;
      int l = 31 * i;
      boolean flag = this.field_148113_g;
      int j = l;
      i = j + (flag ? 1 : 0);
      l = 31 * i;
      boolean flag1 = this.field_148119_h;
      int k = l;
      return k + (flag1 ? 1 : 0);
   }

   public boolean func_148111_b() {
      return this.field_148119_h;
   }

   public static JsonBlendingMode func_148110_a(JsonObject jsonObject) {
      if (jsonObject == null) {
         return new JsonBlendingMode();
      } else {
         int i = 32774;
         int j = 1;
         int k = 0;
         int l = 1;
         int i1 = 0;
         boolean flag = true;
         boolean flag1 = false;
         boolean flag2 = JsonUtils.isString(jsonObject, "func");
         if (flag2) {
            i = func_148108_a(jsonObject.get("func").getAsString());
            if (i != 32774) {
               flag = false;
            }
         }

         boolean flag3 = JsonUtils.isString(jsonObject, "srcrgb");
         if (flag3) {
            j = func_148107_b(jsonObject.get("srcrgb").getAsString());
            if (j != 1) {
               flag = false;
            }
         }

         boolean flag4 = JsonUtils.isString(jsonObject, "dstrgb");
         if (flag4) {
            k = func_148107_b(jsonObject.get("dstrgb").getAsString());
            if (k != 0) {
               flag = false;
            }
         }

         boolean flag5 = JsonUtils.isString(jsonObject, "srcalpha");
         if (flag5) {
            l = func_148107_b(jsonObject.get("srcalpha").getAsString());
            if (l != 1) {
               flag = false;
            }

            flag1 = true;
         }

         boolean flag6 = JsonUtils.isString(jsonObject, "dstalpha");
         if (flag6) {
            i1 = func_148107_b(jsonObject.get("dstalpha").getAsString());
            if (i1 != 0) {
               flag = false;
            }

            flag1 = true;
         }

         return flag ? new JsonBlendingMode() : (flag1 ? new JsonBlendingMode(j, k, l, i1, i) : new JsonBlendingMode(j, k, i));
      }
   }

   private static int func_148108_a(String string) {
      String s = string.trim().toLowerCase();
      boolean flag = s.equals("add");
      int i;
      if (flag) {
         i = 32774;
      } else {
         boolean flag1 = s.equals("subtract");
         if (flag1) {
            i = 32778;
         } else {
            boolean flag2 = s.equals("reversesubtract");
            if (flag2) {
               i = 32779;
            } else {
               boolean flag3 = s.equals("reverse_subtract");
               if (flag3) {
                  i = 32779;
               } else {
                  boolean flag4 = s.equals("min");
                  if (flag4) {
                     i = 32775;
                  } else {
                     boolean flag5 = s.equals("max");
                     i = flag5 ? '耈' : '耆';
                  }
               }
            }
         }
      }

      return i;
   }

   private static int func_148107_b(String string) {
      String s = string.trim().toLowerCase();
      s = s.replaceAll("_", "");
      s = s.replaceAll("one", "1");
      s = s.replaceAll("zero", "0");
      s = s.replaceAll("minus", "-");
      boolean flag = s.equals("0");
      int i;
      if (flag) {
         i = 0;
      } else {
         boolean flag1 = s.equals("1");
         if (flag1) {
            i = 1;
         } else {
            boolean flag2 = s.equals("srccolor");
            if (flag2) {
               i = 768;
            } else {
               boolean flag3 = s.equals("1-srccolor");
               if (flag3) {
                  i = 769;
               } else {
                  boolean flag4 = s.equals("dstcolor");
                  if (flag4) {
                     i = 774;
                  } else {
                     boolean flag5 = s.equals("1-dstcolor");
                     if (flag5) {
                        i = 775;
                     } else {
                        boolean flag6 = s.equals("srcalpha");
                        if (flag6) {
                           i = 770;
                        } else {
                           boolean flag7 = s.equals("1-srcalpha");
                           if (flag7) {
                              i = 771;
                           } else {
                              boolean flag8 = s.equals("dstalpha");
                              if (flag8) {
                                 i = 772;
                              } else {
                                 boolean flag9 = s.equals("1-dstalpha");
                                 i = flag9 ? 773 : -1;
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      return i;
   }
}

package net.minecraft.client.renderer.block.statemap;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.model.ModelResourceLocation;

public abstract class StateMapperBase implements IStateMapper {
   public Map<IBlockState, ModelResourceLocation> mapStateModelLocations = Maps.newLinkedHashMap();
   private static boolean H;
   private static final String d = "normal";

   public String getPropertyString(Map<IProperty, Comparable> map) {
      StringBuilder stringbuilder = new StringBuilder();

      for (Entry entry : map.entrySet()) {
         if (stringbuilder.length() != 0) {
            stringbuilder.append(",");
         }

         IProperty iproperty = (IProperty)entry.getKey();
         Comparable comparable = (Comparable)entry.getValue();
         stringbuilder.append(iproperty.getName());
         stringbuilder.append("=");
         stringbuilder.append(iproperty.getName(comparable));
      }

      if (stringbuilder.length() == 0) {
         stringbuilder.append(d);
      }

      return stringbuilder.toString();
   }

   @Override
   public Map<IBlockState, ModelResourceLocation> putStateModelLocations(Block blockIn) {
      for (IBlockState iblockstate : blockIn.getBlockState().getValidStates()) {
         this.mapStateModelLocations.put(iblockstate, this.getModelResourceLocation(iblockstate));
      }

      return this.mapStateModelLocations;
   }

   public abstract ModelResourceLocation getModelResourceLocation(IBlockState var1);

   public static void n(boolean flag) {
      H = flag;
   }

   public static boolean f() {
      return H;
   }

   public static boolean B() {
      return false;
   }

   static {
      n(true);
   }
}

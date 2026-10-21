package net.minecraft.client.renderer.block.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModelBlock {
   private static final Logger LOGGER = LogManager.getLogger();
   public static final Gson SERIALIZER = new GsonBuilder()
      .registerTypeAdapter(ModelBlock.class, new ModelBlock.Deserializer())
      .registerTypeAdapter(BlockPart.class, new BlockPart.Deserializer())
      .registerTypeAdapter(BlockPartFace.class, new BlockPartFace.Deserializer())
      .registerTypeAdapter(BlockFaceUV.class, new BlockFaceUV.Deserializer())
      .registerTypeAdapter(ItemTransformVec3f.class, new ItemTransformVec3f.Deserializer())
      .registerTypeAdapter(ItemCameraTransforms.class, new ItemCameraTransforms.Deserializer())
      .create();
   private final List<BlockPart> elements;
   private final boolean gui3d;
   private final boolean ambientOcclusion;
   private ItemCameraTransforms cameraTransforms;
   public String name = "";
   public final Map<String, String> textures;
   public ModelBlock parent;
   public ResourceLocation parentLocation;

   public static ModelBlock deserialize(Reader readerIn) {
      return SERIALIZER.fromJson(readerIn, ModelBlock.class);
   }

   public static ModelBlock deserialize(String jsonString) {
      return deserialize(new StringReader(jsonString));
   }

   public ModelBlock(
      List<BlockPart> elementsIn, Map<String, String> texturesIn, boolean ambientOcclusionIn, boolean gui3dIn, ItemCameraTransforms cameraTransformsIn
   ) {
      this((ResourceLocation)null, elementsIn, texturesIn, ambientOcclusionIn, gui3dIn, cameraTransformsIn);
   }

   public ModelBlock(
      ResourceLocation parentLocationIn, Map<String, String> texturesIn, boolean ambientOcclusionIn, boolean gui3dIn, ItemCameraTransforms cameraTransformsIn
   ) {
      this(parentLocationIn, Collections.emptyList(), texturesIn, ambientOcclusionIn, gui3dIn, cameraTransformsIn);
   }

   private ModelBlock(
      ResourceLocation parentLocationIn,
      List<BlockPart> elementsIn,
      Map<String, String> texturesIn,
      boolean ambientOcclusionIn,
      boolean gui3dIn,
      ItemCameraTransforms cameraTransformsIn
   ) {
      this.elements = elementsIn;
      this.ambientOcclusion = ambientOcclusionIn;
      this.gui3d = gui3dIn;
      this.textures = texturesIn;
      this.parentLocation = parentLocationIn;
      this.cameraTransforms = cameraTransformsIn;
   }

   public List<BlockPart> getElements() {
      return this.hasParent() ? this.parent.getElements() : this.elements;
   }

   private boolean hasParent() {
      return this.parent != null;
   }

   public boolean isAmbientOcclusion() {
      return this.hasParent() ? this.parent.isAmbientOcclusion() : this.ambientOcclusion;
   }

   public boolean isGui3d() {
      return this.gui3d;
   }

   public boolean isResolved() {
      if (this.parentLocation != null) {
         ModelBlock modelblock = this.parent;
         if (modelblock != null) {
            boolean flag = this.parent.isResolved();
            if (flag) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }

   public void getParentFromMap(Map<ResourceLocation, ModelBlock> map) {
      if (this.parentLocation != null) {
         this.parent = (ModelBlock)map.get(this.parentLocation);
      }
   }

   public boolean isTexturePresent(String textureName) {
      return !"missingno".equals(this.resolveTextureName(textureName));
   }

   public String resolveTextureName(String textureName) {
      if (!this.startsWithHash(textureName)) {
         textureName = '#' + textureName;
      }

      return this.resolveTextureName(textureName, new ModelBlock.Bookkeep(this, null));
   }

   private String resolveTextureName(String textureName, ModelBlock.Bookkeep bookkeep) {
      if (this.startsWithHash(textureName)) {
         if (this == bookkeep.modelExt) {
            LOGGER.warn("Unable to resolve texture due to upward reference: " + textureName + " in " + this.name);
            return "missingno";
         } else {
            String s = this.textures.get(textureName.substring(1));
            if (s == null && this.hasParent()) {
               s = this.parent.resolveTextureName(textureName, bookkeep);
            }

            bookkeep.modelExt = this;
            if (s != null && this.startsWithHash(s)) {
               s = bookkeep.model.resolveTextureName(s, bookkeep);
            }

            return s != null && !this.startsWithHash(s) ? s : "missingno";
         }
      } else {
         return textureName;
      }
   }

   private boolean startsWithHash(String hash) {
      return hash.charAt(0) == '#';
   }

   public ResourceLocation getParentLocation() {
      return this.parentLocation;
   }

   public ModelBlock getRootModel() {
      return this.hasParent() ? this.parent.getRootModel() : this;
   }

   public ItemCameraTransforms getAllTransforms() {
      ItemTransformVec3f itemtransformvec3f = this.getTransform(ItemCameraTransforms.TransformType.THIRD_PERSON);
      ItemTransformVec3f itemtransformvec3f1 = this.getTransform(ItemCameraTransforms.TransformType.FIRST_PERSON);
      ItemTransformVec3f itemtransformvec3f2 = this.getTransform(ItemCameraTransforms.TransformType.HEAD);
      ItemTransformVec3f itemtransformvec3f3 = this.getTransform(ItemCameraTransforms.TransformType.GUI);
      ItemTransformVec3f itemtransformvec3f4 = this.getTransform(ItemCameraTransforms.TransformType.GROUND);
      ItemTransformVec3f itemtransformvec3f5 = this.getTransform(ItemCameraTransforms.TransformType.FIXED);
      return new ItemCameraTransforms(
         itemtransformvec3f, itemtransformvec3f1, itemtransformvec3f2, itemtransformvec3f3, itemtransformvec3f4, itemtransformvec3f5
      );
   }

   private ItemTransformVec3f getTransform(ItemCameraTransforms.TransformType typeIn) {
      return this.parent != null && !this.cameraTransforms.func_181687_c(typeIn)
         ? this.parent.getTransform(typeIn)
         : this.cameraTransforms.getTransform(typeIn);
   }

   public static void checkModelHierarchy(Map<ResourceLocation, ModelBlock> map) {
      for (ModelBlock modelblock : map.values()) {
         try {
            ModelBlock modelblock1 = modelblock.parent;

            for (ModelBlock modelblock2 = modelblock1.parent; modelblock1 != modelblock2; modelblock2 = modelblock2.parent.parent) {
               modelblock1 = modelblock1.parent;
            }

            throw new ModelBlock.LoopException();
         } catch (NullPointerException nullpointerexception) {
         }
      }
   }

   public static final class Bookkeep {
      public final ModelBlock model;
      public ModelBlock modelExt;

      public Bookkeep(ModelBlock modelBlock) {
         this.model = modelBlock;
      }

      Bookkeep(ModelBlock modelBlock, ModelBlock$1 obj) {
         this(modelBlock);
      }
   }

   public static class Deserializer implements JsonDeserializer<ModelBlock> {
      public ModelBlock deserialize(JsonElement jsonElement, Type type2, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
         JsonObject jsonobject = jsonElement.getAsJsonObject();
         List list = this.getModelElements(jsonDeserializationContext, jsonobject);
         String s = this.getParent(jsonobject);
         boolean flag = StringUtils.isEmpty(s);
         boolean flag1 = list.isEmpty();
         if (flag1 && flag) {
            throw new JsonParseException("BlockModel requires either elements or parent, found neither");
         } else if (!flag && !flag1) {
            throw new JsonParseException("BlockModel requires either elements or parent, found both");
         } else {
            Map map = this.getTextures(jsonobject);
            boolean flag2 = this.getAmbientOcclusionEnabled(jsonobject);
            ItemCameraTransforms itemcameratransforms = ItemCameraTransforms.DEFAULT;
            boolean flag3 = jsonobject.has("display");
            if (flag3) {
               JsonObject jsonobject1 = JsonUtils.getJsonObject(jsonobject, "display");
               itemcameratransforms = jsonDeserializationContext.deserialize(jsonobject1, ItemCameraTransforms.class);
            }

            return flag1
               ? new ModelBlock(new ResourceLocation(s), map, flag2, true, itemcameratransforms)
               : new ModelBlock(list, map, flag2, true, itemcameratransforms);
         }
      }

      private Map<String, String> getTextures(JsonObject jsonObject) {
         HashMap hashmap = Maps.newHashMap();
         if (jsonObject.has("textures")) {
            JsonObject jsonobject = jsonObject.getAsJsonObject("textures");

            for (Entry entry : jsonobject.entrySet()) {
               hashmap.put(entry.getKey(), ((JsonElement)entry.getValue()).getAsString());
            }
         }

         return hashmap;
      }

      private String getParent(JsonObject jsonObject) {
         return JsonUtils.getString(jsonObject, "parent", "");
      }

      public boolean getAmbientOcclusionEnabled(JsonObject jsonObject) {
         return JsonUtils.getBoolean(jsonObject, "ambientocclusion", true);
      }

      public List<BlockPart> getModelElements(JsonDeserializationContext jsonDeserializationContext, JsonObject jsonObject) {
         ArrayList arraylist = Lists.newArrayList();
         if (jsonObject.has("elements")) {
            for (JsonElement jsonelement : JsonUtils.getJsonArray(jsonObject, "elements")) {
               arraylist.add((BlockPart)jsonDeserializationContext.deserialize(jsonelement, BlockPart.class));
            }
         }

         return arraylist;
      }
   }

   public static class LoopException extends RuntimeException {
   }
}

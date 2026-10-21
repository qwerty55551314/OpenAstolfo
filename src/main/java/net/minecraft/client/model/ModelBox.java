package net.minecraft.client.model;

import net.minecraft.client.renderer.WorldRenderer;

public class ModelBox {
   private PositionTextureVertex[] vertexPositions;
   private TexturedQuad[] quadList;
   public final float posX1;
   public final float posY1;
   public final float posZ1;
   public final float posX2;
   public final float posY2;
   public final float posZ2;
   public String boxName;

   public ModelBox(ModelRenderer renderer, int i, int j, float f, float g, float h, int k, int l, int m, float f2) {
      this(renderer, i, j, f, g, h, k, l, m, f2, renderer.mirror);
   }

   public ModelBox(ModelRenderer renderer, int textureX, int textureY, float f, float g, float h, int i, int j, int k, float f2, boolean bl) {
      this.posX1 = f;
      this.posY1 = g;
      this.posZ1 = h;
      this.posX2 = f + (float)i;
      this.posY2 = g + (float)j;
      this.posZ2 = h + (float)k;
      this.vertexPositions = new PositionTextureVertex[8];
      this.quadList = new TexturedQuad[6];
      float f1 = f + (float)i;
      float f1x = g + (float)j;
      float f21 = h + (float)k;
      f -= f2;
      g -= f2;
      h -= f2;
      f1 += f2;
      f1x += f2;
      f21 += f2;
      if (bl) {
         float f3 = f1;
         f1 = f;
         f = f3;
      }

      PositionTextureVertex positiontexturevertex7 = new PositionTextureVertex(f, g, h, 0.0F, 0.0F);
      PositionTextureVertex positiontexturevertex = new PositionTextureVertex(f1, g, h, 0.0F, 8.0F);
      PositionTextureVertex positiontexturevertex1 = new PositionTextureVertex(f1, f1x, h, 8.0F, 8.0F);
      PositionTextureVertex positiontexturevertex2 = new PositionTextureVertex(f, f1x, h, 8.0F, 0.0F);
      PositionTextureVertex positiontexturevertex3 = new PositionTextureVertex(f, g, f21, 0.0F, 0.0F);
      PositionTextureVertex positiontexturevertex4 = new PositionTextureVertex(f1, g, f21, 0.0F, 8.0F);
      PositionTextureVertex positiontexturevertex5 = new PositionTextureVertex(f1, f1x, f21, 8.0F, 8.0F);
      PositionTextureVertex positiontexturevertex6 = new PositionTextureVertex(f, f1x, f21, 8.0F, 0.0F);
      this.vertexPositions[0] = positiontexturevertex7;
      this.vertexPositions[1] = positiontexturevertex;
      this.vertexPositions[2] = positiontexturevertex1;
      this.vertexPositions[3] = positiontexturevertex2;
      this.vertexPositions[4] = positiontexturevertex3;
      this.vertexPositions[5] = positiontexturevertex4;
      this.vertexPositions[6] = positiontexturevertex5;
      this.vertexPositions[7] = positiontexturevertex6;
      this.quadList[0] = new TexturedQuad(
         new PositionTextureVertex[]{positiontexturevertex4, positiontexturevertex, positiontexturevertex1, positiontexturevertex5},
         textureX + k + i,
         textureY + k,
         textureX + k + i + k,
         textureY + k + j,
         renderer.textureWidth,
         renderer.textureHeight
      );
      this.quadList[1] = new TexturedQuad(
         new PositionTextureVertex[]{positiontexturevertex7, positiontexturevertex3, positiontexturevertex6, positiontexturevertex2},
         textureX,
         textureY + k,
         textureX + k,
         textureY + k + j,
         renderer.textureWidth,
         renderer.textureHeight
      );
      this.quadList[2] = new TexturedQuad(
         new PositionTextureVertex[]{positiontexturevertex4, positiontexturevertex3, positiontexturevertex7, positiontexturevertex},
         textureX + k,
         textureY,
         textureX + k + i,
         textureY + k,
         renderer.textureWidth,
         renderer.textureHeight
      );
      this.quadList[3] = new TexturedQuad(
         new PositionTextureVertex[]{positiontexturevertex1, positiontexturevertex2, positiontexturevertex6, positiontexturevertex5},
         textureX + k + i,
         textureY + k,
         textureX + k + i + i,
         textureY,
         renderer.textureWidth,
         renderer.textureHeight
      );
      this.quadList[4] = new TexturedQuad(
         new PositionTextureVertex[]{positiontexturevertex, positiontexturevertex7, positiontexturevertex2, positiontexturevertex1},
         textureX + k,
         textureY + k,
         textureX + k + i,
         textureY + k + j,
         renderer.textureWidth,
         renderer.textureHeight
      );
      this.quadList[5] = new TexturedQuad(
         new PositionTextureVertex[]{positiontexturevertex3, positiontexturevertex4, positiontexturevertex5, positiontexturevertex6},
         textureX + k + i + k,
         textureY + k,
         textureX + k + i + k + i,
         textureY + k + j,
         renderer.textureWidth,
         renderer.textureHeight
      );
      if (bl) {
         for (int i1 = 0; i1 < this.quadList.length; i1++) {
            this.quadList[i1].flipFace();
         }
      }
   }

   public void render(WorldRenderer renderer, float scale) {
      for (int i = 0; i < this.quadList.length; i++) {
         this.quadList[i].draw(renderer, scale);
      }
   }

   public ModelBox setBoxName(String name) {
      this.boxName = name;
      return this;
   }
}

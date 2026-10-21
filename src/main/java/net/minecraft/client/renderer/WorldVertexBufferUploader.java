package net.minecraft.client.renderer;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.List;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.src.Config;
import net.minecraft.src.Reflector;
import org.lwjgl.opengl.GL11;
import shadersmod.client.SVertexBuilder;

public class WorldVertexBufferUploader {
   public void draw(WorldRenderer worldRenderer) {
      if (worldRenderer.getVertexCount() > 0) {
         VertexFormat vertexformat = worldRenderer.getVertexFormat();
         int i = vertexformat.getNextOffset();
         ByteBuffer bytebuffer = worldRenderer.getByteBuffer();
         List list = vertexformat.getElements();
         boolean flag = Reflector.ForgeVertexFormatElementEnumUseage_preDraw.exists();
         boolean flag1 = Reflector.ForgeVertexFormatElementEnumUseage_postDraw.exists();

         for (int j = 0; j < list.size(); j++) {
            VertexFormatElement vertexformatelement = (VertexFormatElement)list.get(j);
            VertexFormatElement.EnumUsage vertexformatelement$enumusage = vertexformatelement.getUsage();
            if (flag) {
               Reflector.callVoid(vertexformatelement$enumusage, Reflector.ForgeVertexFormatElementEnumUseage_preDraw, vertexformat, j, i, bytebuffer);
            } else {
               int k = vertexformatelement.getType().getGlConstant();
               int l = vertexformatelement.getIndex();
               ((Buffer)bytebuffer).position(vertexformat.getOffset(j));
               switch (vertexformatelement$enumusage) {
                  case POSITION:
                     GL11.glVertexPointer(vertexformatelement.getElementCount(), k, i, bytebuffer);
                     GL11.glEnableClientState(32884);
                     break;
                  case UV:
                     OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit + l);
                     GL11.glTexCoordPointer(vertexformatelement.getElementCount(), k, i, bytebuffer);
                     GL11.glEnableClientState(32888);
                     OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
                     break;
                  case COLOR:
                     GL11.glColorPointer(vertexformatelement.getElementCount(), k, i, bytebuffer);
                     GL11.glEnableClientState(32886);
                     break;
                  case NORMAL:
                     GL11.glNormalPointer(k, i, bytebuffer);
                     GL11.glEnableClientState(32885);
               }
            }
         }

         if (worldRenderer.isMultiTexture()) {
            worldRenderer.drawMultiTexture();
         } else if (Config.isShaders()) {
            SVertexBuilder.drawArrays(worldRenderer.getDrawMode(), 0, worldRenderer.getVertexCount(), worldRenderer);
         } else {
            GL11.glDrawArrays(worldRenderer.getDrawMode(), 0, worldRenderer.getVertexCount());
         }

         int i1 = 0;

         for (int j1 = list.size(); i1 < j1; i1++) {
            VertexFormatElement vertexformatelement1 = (VertexFormatElement)list.get(i1);
            VertexFormatElement.EnumUsage vertexformatelement$enumusage1 = vertexformatelement1.getUsage();
            if (flag1) {
               Reflector.callVoid(vertexformatelement$enumusage1, Reflector.ForgeVertexFormatElementEnumUseage_postDraw, vertexformat, i1, i, bytebuffer);
            } else {
               int k1 = vertexformatelement1.getIndex();
               switch (vertexformatelement$enumusage1) {
                  case POSITION:
                     GL11.glDisableClientState(32884);
                     break;
                  case UV:
                     OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit + k1);
                     GL11.glDisableClientState(32888);
                     OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
                     break;
                  case COLOR:
                     GL11.glDisableClientState(32886);
                     GlStateManager.resetColor();
                     break;
                  case NORMAL:
                     GL11.glDisableClientState(32885);
               }
            }
         }
      }

      worldRenderer.reset();
   }
}

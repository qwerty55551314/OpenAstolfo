package net.minecraft.network.play.server;

import astolfo.gui.clickgui.defaultgui.DefaultClickGuiScreen;
import astolfo.gui.clickgui.sidebar.SidebarClickGuiScreen;
import astolfo.gui.clickgui.transparent.TransparentClickGuiScreen;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S2EPacketCloseWindow implements Packet<INetHandlerPlayClient> {
   private int windowId;

   public S2EPacketCloseWindow() {
   }

   public S2EPacketCloseWindow(int windowIdIn) {
      this.windowId = windowIdIn;
   }

   public void processPacket(INetHandlerPlayClient handler) {
      GuiScreen guiscreen = Minecraft.getMinecraft().currentScreen;
      boolean flag = guiscreen instanceof GuiChat;
      if (!flag) {
         boolean flag1 = guiscreen instanceof DefaultClickGuiScreen;
         if (!flag1) {
            boolean flag2 = guiscreen instanceof TransparentClickGuiScreen;
            if (!flag2 && !(guiscreen instanceof SidebarClickGuiScreen)) {
               handler.handleCloseWindow(this);
               return;
            }
         }
      }
   }

   @Override
   public void readPacketData(PacketBuffer buf) throws IOException {
      this.windowId = buf.readUnsignedByte();
   }

   @Override
   public void writePacketData(PacketBuffer buf) throws IOException {
      buf.writeByte(this.windowId);
   }
}

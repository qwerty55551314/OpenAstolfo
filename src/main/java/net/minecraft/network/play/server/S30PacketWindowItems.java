package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S30PacketWindowItems implements Packet<INetHandlerPlayClient> {
   private int windowId;
   private ItemStack[] itemStacks;

   public S30PacketWindowItems() {
   }

   public S30PacketWindowItems(int windowIdIn, List<ItemStack> list) {
      this.windowId = windowIdIn;
      this.itemStacks = new ItemStack[list.size()];

      for (int i = 0; i < this.itemStacks.length; i++) {
         ItemStack itemstack = (ItemStack)list.get(i);
         this.itemStacks[i] = itemstack == null ? null : itemstack.copy();
      }
   }

   @Override
   public void readPacketData(PacketBuffer buf) throws IOException {
      this.windowId = buf.readUnsignedByte();
      short short1 = buf.readShort();
      this.itemStacks = new ItemStack[short1];

      for (int i = 0; i < short1; i++) {
         this.itemStacks[i] = buf.readItemStackFromBuffer();
      }
   }

   @Override
   public void writePacketData(PacketBuffer buf) throws IOException {
      buf.writeByte(this.windowId);
      buf.writeShort(this.itemStacks.length);

      for (ItemStack itemstack : this.itemStacks) {
         buf.writeItemStackToBuffer(itemstack);
      }
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleWindowItems(this);
   }

   public int func_148911_c() {
      return this.windowId;
   }

   public ItemStack[] getItemStacks() {
      return this.itemStacks;
   }
}

package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S39PacketPlayerAbilities implements Packet<INetHandlerPlayClient> {
   private boolean invulnerable;
   private boolean flying;
   private boolean allowFlying;
   private boolean creativeMode;
   private float flySpeed;
   private float walkSpeed;

   public S39PacketPlayerAbilities() {
   }

   public S39PacketPlayerAbilities(PlayerCapabilities capabilities) {
      this.setInvulnerable(capabilities.disableDamage);
      this.setFlying(capabilities.isFlying);
      this.setAllowFlying(capabilities.allowFlying);
      this.setCreativeMode(capabilities.isCreativeMode);
      this.setFlySpeed(capabilities.getFlySpeed());
      this.setWalkSpeed(capabilities.getWalkSpeed());
   }

   @Override
   public void readPacketData(PacketBuffer buf) throws IOException {
      byte b0 = buf.readByte();
      int i = b0 & 1;
      this.setInvulnerable(i > 0);
      int j = b0 & 2;
      this.setFlying(j > 0);
      int k = b0 & 4;
      this.setAllowFlying(k > 0);
      int l = b0 & 8;
      this.setCreativeMode(l > 0);
      this.setFlySpeed(buf.readFloat());
      this.setWalkSpeed(buf.readFloat());
   }

   @Override
   public void writePacketData(PacketBuffer buf) throws IOException {
      int b0 = 0;
      boolean flag = this.isInvulnerable();
      if (flag) {
         b0 = (byte)(b0 | 1);
      }

      boolean flag1 = this.isFlying();
      if (flag1) {
         b0 = (byte)(b0 | 2);
      }

      boolean flag2 = this.isAllowFlying();
      if (flag2) {
         b0 = (byte)(b0 | 4);
      }

      boolean flag3 = this.isCreativeMode();
      if (flag3) {
         b0 = (byte)(b0 | 8);
      }

      buf.writeByte(b0);
      buf.writeFloat(this.flySpeed);
      buf.writeFloat(this.walkSpeed);
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handlePlayerAbilities(this);
   }

   public boolean isInvulnerable() {
      return this.invulnerable;
   }

   public void setInvulnerable(boolean isInvulnerable) {
      this.invulnerable = isInvulnerable;
   }

   public boolean isFlying() {
      return this.flying;
   }

   public void setFlying(boolean isFlying) {
      this.flying = isFlying;
   }

   public boolean isAllowFlying() {
      return this.allowFlying;
   }

   public void setAllowFlying(boolean isAllowFlying) {
      this.allowFlying = isAllowFlying;
   }

   public boolean isCreativeMode() {
      return this.creativeMode;
   }

   public void setCreativeMode(boolean isCreativeMode) {
      this.creativeMode = isCreativeMode;
   }

   public float getFlySpeed() {
      return this.flySpeed;
   }

   public void setFlySpeed(float flySpeedIn) {
      this.flySpeed = flySpeedIn;
   }

   public float getWalkSpeed() {
      return this.walkSpeed;
   }

   public void setWalkSpeed(float walkSpeedIn) {
      this.walkSpeed = walkSpeedIn;
   }
}

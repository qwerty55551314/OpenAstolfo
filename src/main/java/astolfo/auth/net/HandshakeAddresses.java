package astolfo.auth.net;

public final class HandshakeAddresses {
   private final String clientAddress;
   private final String remoteAddress;

   public HandshakeAddresses(String string, String string2) {
      this.clientAddress = string;
      this.remoteAddress = string2;
   }

   public String getClientAddress() {
      return this.clientAddress;
   }

   public String getRemoteAddress() {
      return this.remoteAddress;
   }

   @Override
   public boolean equals(Object object) {
      if (object == this) {
         return true;
      } else if (!(object instanceof HandshakeAddresses)) {
         return false;
      } else {
         HandshakeAddresses handshakeaddresses = (HandshakeAddresses)object;
         boolean flag = handshakeaddresses.clientAddress.equals(this.clientAddress);
         if (flag) {
            boolean flag1 = handshakeaddresses.remoteAddress.equals(this.remoteAddress);
            if (flag1) {
               return true;
            }
         }

         return false;
      }
   }

   @Override
   public int hashCode() {
      return 31 * (31 + this.clientAddress.hashCode()) + this.remoteAddress.hashCode();
   }

   @Override
   public String toString() {
      return this.remoteAddress + '-' + this.clientAddress;
   }
}

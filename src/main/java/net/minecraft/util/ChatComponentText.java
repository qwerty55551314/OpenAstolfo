package net.minecraft.util;

public class ChatComponentText extends ChatComponentStyle {
   private final String text;

   public ChatComponentText(String msg) {
      this.text = msg;
   }

   public String getChatComponentText_TextValue() {
      return this.text;
   }

   @Override
   public String getUnformattedTextForChat() {
      return this.text;
   }

   public ChatComponentText createCopy() {
      ChatComponentText chatcomponenttext = new ChatComponentText(this.text);
      chatcomponenttext.setChatStyle(this.getChatStyle().createShallowCopy());

      for (IChatComponent ichatcomponent : this.getSiblings()) {
         chatcomponenttext.appendSibling(ichatcomponent.createCopy());
      }

      return chatcomponenttext;
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else if (!(object instanceof ChatComponentText)) {
         return false;
      } else {
         ChatComponentText chatcomponenttext = (ChatComponentText)object;
         boolean flag = this.text.equals(chatcomponenttext.getChatComponentText_TextValue());
         if (flag) {
            boolean flag1 = super.equals(object);
            if (flag1) {
               return true;
            }
         }

         return false;
      }
   }

   @Override
   public String toString() {
      return "TextComponent{text='" + this.text + '\'' + ", siblings=" + super.siblings + ", style=" + this.getChatStyle() + '}';
   }
}

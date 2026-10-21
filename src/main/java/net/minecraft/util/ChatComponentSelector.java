package net.minecraft.util;

public class ChatComponentSelector extends ChatComponentStyle {
   private final String selector;

   public ChatComponentSelector(String selectorIn) {
      this.selector = selectorIn;
   }

   public String getSelector() {
      return this.selector;
   }

   @Override
   public String getUnformattedTextForChat() {
      return this.selector;
   }

   public ChatComponentSelector createCopy() {
      Object object = null;
      ChatComponentSelector chatcomponentselector = new ChatComponentSelector(this.selector);
      chatcomponentselector.setChatStyle(this.getChatStyle().createShallowCopy());

      for (IChatComponent ichatcomponent : this.getSiblings()) {
         chatcomponentselector.appendSibling(ichatcomponent.createCopy());
      }

      return chatcomponentselector;
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else if (!(object instanceof ChatComponentSelector)) {
         return false;
      } else {
         ChatComponentSelector chatcomponentselector = (ChatComponentSelector)object;
         boolean flag = this.selector.equals(chatcomponentselector.selector);
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
      return "SelectorComponent{pattern='" + this.selector + '\'' + ", siblings=" + super.siblings + ", style=" + this.getChatStyle() + '}';
   }
}

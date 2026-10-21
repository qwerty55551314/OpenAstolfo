package net.minecraft.util;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.IllegalFormatException;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatComponentTranslation extends ChatComponentStyle {
   private final String key;
   private final Object[] formatArgs;
   private final Object syncLock = new Object();
   private long lastTranslationUpdateTimeInMilliseconds = -1L;
   public List<IChatComponent> children = Lists.newArrayList();
   public static final Pattern stringVariablePattern = Pattern.compile("%(?:(\\d+)\\$)?([A-Za-z%]|$)");

   public ChatComponentTranslation(String translationKey, Object... args) {
      this.key = translationKey;
      this.formatArgs = args;

      for (Object object : args) {
         if (object instanceof IChatComponent) {
            ((IChatComponent)object).getChatStyle().setParentStyle(this.getChatStyle());
         }
      }
   }

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public synchronized void ensureInitialized() {
      Object object1 = null;
      Object object = this.syncLock;
      synchronized (this.syncLock){} // $VF: monitorenter 
      boolean flag = false /* VF: Semaphore variable */;

      try {
         flag = true;
         long i = StatCollector.getLastTranslationUpdateTimeInMilliseconds();
         if (i == this.lastTranslationUpdateTimeInMilliseconds) {
            // $VF: monitorexit
            return;
         }

         this.lastTranslationUpdateTimeInMilliseconds = i;
         this.children.clear();
         // $VF: monitorexit
         flag = false;
      } finally {
         if (flag) {
            // $VF: monitorexit
         }
      }

      try {
         this.initializeFromFormat(StatCollector.translateToLocal(this.key));
      } catch (ChatComponentTranslationFormatException chatcomponenttranslationformatexception1) {
         this.children.clear();

         try {
            this.initializeFromFormat(StatCollector.translateToFallback(this.key));
         } catch (ChatComponentTranslationFormatException chatcomponenttranslationformatexception) {
            throw chatcomponenttranslationformatexception1;
         }
      }
   }

   public void initializeFromFormat(String formatIn) {
      Matcher matcher = stringVariablePattern.matcher(formatIn);
      int i = 0;
      int j = 0;

      try {
         while (matcher.find(j)) {
            int l = matcher.start();
            int k = matcher.end();
            if (l > j) {
               ChatComponentText chatcomponenttext = new ChatComponentText(String.format(formatIn.substring(j, l)));
               chatcomponenttext.getChatStyle().setParentStyle(this.getChatStyle());
               this.children.add(chatcomponenttext);
            }

            String s2 = matcher.group(2);
            String s = formatIn.substring(l, k);
            if ("%".equals(s2) && "%%".equals(s)) {
               ChatComponentText chatcomponenttext2 = new ChatComponentText("%");
               chatcomponenttext2.getChatStyle().setParentStyle(this.getChatStyle());
               this.children.add(chatcomponenttext2);
            } else {
               if (!"s".equals(s2)) {
                  throw new ChatComponentTranslationFormatException(this, "Unsupported format: '" + s + "'");
               }

               String s1 = matcher.group(1);
               int i1 = s1 != null ? Integer.parseInt(s1) - 1 : i++;
               if (i1 < this.formatArgs.length) {
                  this.children.add(this.getFormatArgumentAsComponent(i1));
               }
            }

            j = k;
         }

         if (j < formatIn.length()) {
            ChatComponentText chatcomponenttext1 = new ChatComponentText(String.format(formatIn.substring(j)));
            chatcomponenttext1.getChatStyle().setParentStyle(this.getChatStyle());
            this.children.add(chatcomponenttext1);
         }
      } catch (IllegalFormatException illegalformatexception) {
         throw new ChatComponentTranslationFormatException(this, illegalformatexception);
      }
   }

   private IChatComponent getFormatArgumentAsComponent(int index) {
      if (index >= this.formatArgs.length) {
         throw new ChatComponentTranslationFormatException(this, index);
      } else {
         Object object = this.formatArgs[index];
         Object object1;
         if (object instanceof IChatComponent) {
            object1 = (IChatComponent)object;
         } else {
            object1 = new ChatComponentText(object == null ? "null" : object.toString());
            ((IChatComponent)object1).getChatStyle().setParentStyle(this.getChatStyle());
         }

         return (IChatComponent)object1;
      }
   }

   @Override
   public IChatComponent setChatStyle(ChatStyle style) {
      super.setChatStyle(style);

      for (Object object : this.formatArgs) {
         if (object instanceof IChatComponent) {
            ((IChatComponent)object).getChatStyle().setParentStyle(this.getChatStyle());
         }
      }

      if (this.lastTranslationUpdateTimeInMilliseconds > -1L) {
         for (IChatComponent ichatcomponent : this.children) {
            ichatcomponent.getChatStyle().setParentStyle(style);
         }
      }

      return this;
   }

   @Override
   public Iterator<IChatComponent> iterator() {
      this.ensureInitialized();
      return Iterators.concat(createDeepCopyIterator(this.children), createDeepCopyIterator(super.siblings));
   }

   @Override
   public String getUnformattedTextForChat() {
      this.ensureInitialized();
      StringBuilder stringbuilder = new StringBuilder();

      for (IChatComponent ichatcomponent : this.children) {
         stringbuilder.append(ichatcomponent.getUnformattedTextForChat());
      }

      return stringbuilder.toString();
   }

   public ChatComponentTranslation createCopy() {
      Object[] aobject = new Object[this.formatArgs.length];

      for (int i = 0; i < this.formatArgs.length; i++) {
         if (this.formatArgs[i] instanceof IChatComponent) {
            aobject[i] = ((IChatComponent)this.formatArgs[i]).createCopy();
         } else {
            aobject[i] = this.formatArgs[i];
         }
      }

      ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation(this.key, aobject);
      chatcomponenttranslation.setChatStyle(this.getChatStyle().createShallowCopy());

      for (IChatComponent ichatcomponent : this.getSiblings()) {
         chatcomponenttranslation.appendSibling(ichatcomponent.createCopy());
      }

      return chatcomponenttranslation;
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else if (!(object instanceof ChatComponentTranslation)) {
         return false;
      } else {
         ChatComponentTranslation chatcomponenttranslation = (ChatComponentTranslation)object;
         boolean flag = Arrays.equals(this.formatArgs, chatcomponenttranslation.formatArgs);
         if (flag) {
            boolean flag1 = this.key.equals(chatcomponenttranslation.key);
            if (flag1) {
               boolean flag2 = super.equals(object);
               if (flag2) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   @Override
   public int hashCode() {
      int i = super.hashCode();
      i = 31 * i + this.key.hashCode();
      return 31 * i + Arrays.hashCode(this.formatArgs);
   }

   @Override
   public String toString() {
      return "TranslatableComponent{key='"
         + this.key
         + '\''
         + ", args="
         + Arrays.toString(this.formatArgs)
         + ", siblings="
         + super.siblings
         + ", style="
         + this.getChatStyle()
         + '}';
   }

   public String getKey() {
      return this.key;
   }

   public Object[] getFormatArgs() {
      return this.formatArgs;
   }
}

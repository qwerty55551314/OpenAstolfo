package net.minecraft.util;

import java.util.List;
import net.minecraft.command.CommandException;
import net.minecraft.command.EntityNotFoundException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerSelector;
import net.minecraft.entity.Entity;

public class ChatComponentProcessor {
   public static IChatComponent processComponent(ICommandSender commandSender, IChatComponent component, Entity entityIn) throws CommandException {
      boolean flag = component instanceof ChatComponentScore;
      Object object;
      if (flag) {
         ChatComponentScore chatcomponentscore = (ChatComponentScore)component;
         String s = chatcomponentscore.getName();
         if (PlayerSelector.hasArguments(s)) {
            List list = PlayerSelector.matchEntities(commandSender, s, Entity.class);
            if (list.size() != 1) {
               throw new EntityNotFoundException();
            }

            s = ((Entity)list.get(0)).getName();
         }

         object = entityIn != null && s.equals("*")
            ? new ChatComponentScore(entityIn.getName(), chatcomponentscore.getObjective())
            : new ChatComponentScore(s, chatcomponentscore.getObjective());
         ((ChatComponentScore)object).setValue(chatcomponentscore.getUnformattedTextForChat());
      } else {
         boolean flag1 = component instanceof ChatComponentSelector;
         if (flag1) {
            String s1 = ((ChatComponentSelector)component).getSelector();
            object = PlayerSelector.matchEntitiesToChatComponent(commandSender, s1);
            if (object == null) {
               object = new ChatComponentText("");
            }
         } else {
            boolean flag2 = component instanceof ChatComponentText;
            if (flag2) {
               object = new ChatComponentText(((ChatComponentText)component).getChatComponentText_TextValue());
            } else {
               if (!(component instanceof ChatComponentTranslation)) {
                  return component;
               }

               Object[] aobject = ((ChatComponentTranslation)component).getFormatArgs();

               for (int i = 0; i < aobject.length; i++) {
                  Object object1 = aobject[i];
                  if (object1 instanceof IChatComponent) {
                     aobject[i] = processComponent(commandSender, (IChatComponent)object1, entityIn);
                  }
               }

               object = new ChatComponentTranslation(((ChatComponentTranslation)component).getKey(), aobject);
            }
         }
      }

      ChatStyle chatstyle = component.getChatStyle();
      if (chatstyle != null) {
         ((IChatComponent)object).setChatStyle(chatstyle.createShallowCopy());
      }

      for (IChatComponent ichatcomponent1 : component.getSiblings()) {
         IChatComponent ichatcomponent = ((IChatComponent)object).appendSibling(processComponent(commandSender, ichatcomponent1, entityIn));
      }

      return (IChatComponent)object;
   }
}

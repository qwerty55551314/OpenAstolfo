package astolfo.command;

import astolfo.Astolfo;
import astolfo.ClientAccess;
import astolfo.relation.Relation;
import java.util.Arrays;

public final class FriendsCommand extends Command {
   public FriendsCommand(String string, String string2) {
      super(string, string2);
      this.setAliases(Arrays.asList("f", "friend"));
   }

   @Override
   public void runCommand(String[] strings) {
      int i = strings.length;
      switch (i) {
         case 2:
            boolean flag = strings[1].equalsIgnoreCase("clear");
            if (flag) {
               Astolfo astolfo = ClientAccess.client;
               astolfo.getRelationManager().getRelations().values().removeIf(Relation::isFriend);
               Astolfo astolfo1 = ClientAccess.client;
               astolfo1.getRelationManager().getRelationMap().values().removeIf(Relation::isFriend);
               ClientAccess.chat.print("Cleared friend list");
            }
            break;
         case 3:
         case 4:
            this.handleAddOrRemove(strings);
            break;
         default:
            ClientAccess.chat.print(this.getName() + ": " + this.getUsage());
      }
   }

   private void handleAddOrRemove(String[] strings) {
      String s = strings[2];
      int i = strings.length;
      switch (i) {
         case 3:
            boolean flag = strings[1].equalsIgnoreCase("add");
            if (flag) {
               Astolfo astolfo = ClientAccess.client;
               astolfo.getRelationManager().add(new Relation[]{new Relation(s, s, true)});
               ClientAccess.chat.print(String.format("Added %s as friend", s));
            } else {
               boolean flag1 = strings[1].equalsIgnoreCase("del");
               if (flag1) {
                  Astolfo astolfo1 = ClientAccess.client;
                  if (astolfo1.getRelationManager().isFriendName(s)) {
                     Astolfo astolfo2 = ClientAccess.client;
                     astolfo2.getRelationManager().getRelations().remove(s);
                     Astolfo astolfo3 = ClientAccess.client;
                     astolfo3.getRelationManager().getRelationMap().remove(s);
                     ClientAccess.chat.print(String.format("%s is not a friend anymore", s));
                  } else {
                     ClientAccess.chat.print(String.format("%s is not a friend", s));
                  }
               }
            }
            break;
         case 4:
            String s1 = strings[3];
            if (strings[1].equalsIgnoreCase("add")) {
               Astolfo astolfo4 = ClientAccess.client;
               astolfo4.getRelationManager().add(new Relation[]{new Relation(s, s1, true)});
               ClientAccess.chat.print(String.format("Added %s as friend with alias %s", s, s1));
            }
      }
   }
}

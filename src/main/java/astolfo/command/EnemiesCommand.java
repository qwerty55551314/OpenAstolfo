package astolfo.command;

import astolfo.Astolfo;
import astolfo.ClientAccess;
import astolfo.relation.Relation;
import java.util.Arrays;

public final class EnemiesCommand extends Command {
   public EnemiesCommand(String string, String string2) {
      super(string, string2);
      this.setAliases(Arrays.asList("e", "enemy", "target"));
   }

   @Override
   public void runCommand(String[] strings) {
      int i = strings.length;
      switch (i) {
         case 2:
            boolean flag = strings[1].equalsIgnoreCase("clear");
            if (flag) {
               Astolfo astolfo = ClientAccess.client;
               astolfo.getRelationManager().getRelations().values().removeIf(relation -> !relation.isFriend());
               Astolfo astolfo1 = ClientAccess.client;
               astolfo1.getRelationManager().getRelationMap().values().removeIf(relation -> !relation.isFriend());
               ClientAccess.chat.print("Cleared enemy list");
            }
            break;
         case 3:
         case 4:
            this.addOrRemoveEnemy(strings);
            break;
         default:
            ClientAccess.chat.print(this.getName() + ": " + this.getUsage());
      }
   }

   private void addOrRemoveEnemy(String[] strings) {
      String s = strings[2];
      int i = strings.length;
      switch (i) {
         case 3:
            boolean flag = strings[1].equalsIgnoreCase("add");
            if (flag) {
               Astolfo astolfo = ClientAccess.client;
               astolfo.getRelationManager().add(new Relation[]{new Relation(s, s, false)});
               ClientAccess.chat.print(String.format("Added %s as enemy", s));
            } else {
               boolean flag1 = strings[1].equalsIgnoreCase("del");
               if (flag1) {
                  Astolfo astolfo1 = ClientAccess.client;
                  if (astolfo1.getRelationManager().isEnemyName(s)) {
                     Astolfo astolfo2 = ClientAccess.client;
                     astolfo2.getRelationManager().getRelations().remove(s);
                     Astolfo astolfo3 = ClientAccess.client;
                     astolfo3.getRelationManager().getRelationMap().remove(s);
                     ClientAccess.chat.print(String.format("%s is not an enemy anymore", s));
                  } else {
                     ClientAccess.chat.print(String.format("%s is not an enemy", s));
                  }
               }
            }
            break;
         case 4:
            String s1 = strings[3];
            if (strings[1].equalsIgnoreCase("add")) {
               Astolfo astolfo4 = ClientAccess.client;
               astolfo4.getRelationManager().add(new Relation[]{new Relation(s, s1, false)});
               ClientAccess.chat.print(String.format("Added %s as enemy with alias %s", s, s1));
            }
      }
   }
}

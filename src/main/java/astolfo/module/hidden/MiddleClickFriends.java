package astolfo.module.hidden;

import astolfo.Astolfo;
import astolfo.ClientAccess;
import astolfo.event.EventHandler;
import astolfo.event.EventState;
import astolfo.event.Listener;
import astolfo.event.player.UpdateEvent;
import astolfo.module.HiddenModule;
import astolfo.relation.Relation;
import astolfo.relation.RelationManager;
import astolfo.util.Stopwatch;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.input.Mouse;

public final class MiddleClickFriends extends HiddenModule {
   private Stopwatch clickTimer = new Stopwatch();
   @EventHandler(UpdateEvent.class)
   private Listener<UpdateEvent> onUpdate = this::onUpdate;

   public MiddleClickFriends() {
      ClientAccess.client.getEventBus().subscribe(this);
   }

   private void onUpdate(UpdateEvent updateEvent) {
      if (updateEvent.getState() == EventState.PRE && Mouse.isButtonDown(2)) {
         Minecraft minecraft = ClientAccess.mc;
         if (minecraft.objectMouseOver.entityHit != null) {
            Minecraft minecraft1 = ClientAccess.mc;
            if (minecraft1.gameSettings.thirdPersonView == 0
               && ClientAccess.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY
               && this.clickTimer.hasElapsed(500.0F)) {
               Entity entity = ClientAccess.mc.objectMouseOver.entityHit;
               if (!(entity instanceof EntityPlayer)) {
                  return;
               }

               label22: {
                  EntityPlayer entityplayer = (EntityPlayer)entity;
                  Astolfo astolfo = ClientAccess.client;
                  RelationManager relationmanager = astolfo.getRelationManager();
                  if (!relationmanager.isFriend(entityplayer)) {
                     Astolfo astolfo1 = ClientAccess.client;
                     if (!astolfo1.getRelationManager().isEnemy(entityplayer)) {
                        Astolfo astolfo3 = ClientAccess.client;
                        astolfo3.getRelationManager()
                           .getRelations()
                           .put(
                              entityplayer.getCommandSenderEntity().getName(),
                              new Relation(entityplayer.getCommandSenderEntity().getName(), entityplayer.getCommandSenderEntity().getName(), true)
                           );
                        break label22;
                     }
                  }

                  Astolfo astolfo2 = ClientAccess.client;
                  astolfo2.getRelationManager().getRelations().remove(entityplayer.getCommandSenderEntity().getName());
               }

               this.clickTimer.reset();
            }
         }
      }
   }
}

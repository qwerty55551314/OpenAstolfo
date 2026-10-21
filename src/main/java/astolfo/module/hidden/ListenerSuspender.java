package astolfo.module.hidden;

import astolfo.auth.DisconnectHandler;
import astolfo.event.Event;
import astolfo.event.EventHandler;
import astolfo.event.Listener;
import astolfo.event.bus.AnnotatedEventBus;
import astolfo.event.bus.Subscription;
import astolfo.event.game.TickEvent;
import astolfo.event.render.RenderAfterScreenEvent;
import astolfo.module.HiddenModule;
import astolfo.module.visuals.HUD;
import astolfo.notification.NotificationManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public final class ListenerSuspender extends HiddenModule {
   public static HashMap<Class<? extends Event>, List<Subscription<?, ?>>> suspendedListeners = new HashMap<>();
   @EventHandler(TickEvent.class)
   private Listener<TickEvent> onTick = tickEvent -> {
      AnnotatedEventBus annotatedEventBus = (AnnotatedEventBus)client.getEventBus();
      HashMap hashMap = (HashMap)annotatedEventBus.subscriptionMap;
      if (DisconnectHandler.disconnected) {
         ArrayList arrayList = new ArrayList();

         label79:
         for (Object object : hashMap.entrySet()) {
            Entry entry = (Entry)object;
            for (Object object2 : (List)entry.getValue()) {
               Subscription subscription = (Subscription)object2;
               if (subscription.getOwner().getClass() == this.getClass()
                  || subscription.getOwner().getClass() == HUD.class
                  || subscription.getOwner().getClass() == KeybindHandler.class
                  || subscription.getOwner().getClass() == ChatCommandHandler.class
                  || subscription.getOwner().getClass() == BotDetector.class
                  || subscription.getOwner().getClass() == HypixelPacketHandler.class
                  || subscription.getOwner().getClass() == PlayerListTracker.class
                  || subscription.getOwner().getClass() == InventoryPacketHandler.class
                  || subscription.getOwner().getClass() == MiddleClickFriends.class
                  || subscription.getOwner().getClass() == NotificationManager.class
                  || subscription.getOwner().getClass() == GUI.class) {
                  continue label79;
               }
            }

            arrayList.add(entry.getKey());
            suspendedListeners.put((Class<? extends Event>)entry.getKey(), (List<Subscription<?, ?>>)entry.getValue());
         }

         for (Object object3 : arrayList) {
            Class class2 = (Class)object3;
            if (!class2.equals(RenderAfterScreenEvent.class)) {
               hashMap.remove(class2);
            }
         }
      } else if (!suspendedListeners.isEmpty()) {
         for (Entry entry2 : suspendedListeners.entrySet()) {
            hashMap.put(entry2.getKey(), entry2.getValue());
         }

         suspendedListeners.clear();
      }
   };

   public ListenerSuspender() {
      client.getEventBus().subscribe(this);
   }
}

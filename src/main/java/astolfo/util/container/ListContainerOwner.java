package astolfo.util.container;

import java.util.List;

public class ListContainerOwner<T> extends InitializedContainerOwner<T> {
   private final List<T> initialItems;
   private static boolean a;

   public ListContainerOwner(List<T> list) {
      this.initialItems = list;
      this.onInit();
   }

   @Override
   public void onInit() {
      try {
         this.initialItems.stream().forEach(object -> super.mutator.addAll((T[])(new Object[]{object})));
      } catch (Exception exception) {
      }
   }

   public static void p(boolean flag) {
      a = flag;
   }

   public static boolean T() {
      return a;
   }

   public static boolean G() {
      return false;
   }

   static {
      p(true);
   }
}

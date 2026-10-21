package astolfo.gui.animation;

public class TimedAnimation extends Animation {
   private long lastUpdateTime;

   public TimedAnimation() {
      super.duration = 400;
   }

   @Override
   public Animation start() {
      this.lastUpdateTime = System.currentTimeMillis();
      return super.start();
   }

   @Override
   public double getValue() {
      long i = System.currentTimeMillis();
      this.step((int)(i - this.lastUpdateTime));
      String[] astring = new String[0];
      this.lastUpdateTime = i;
      return this.ease(super.progress);
   }
}

package net.minecraft.client.resources.data;

import com.google.common.collect.Sets;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AnimationMetadataSection implements IMetadataSection {
   private final List<AnimationFrame> animationFrames;
   private final int frameWidth;
   private final int frameHeight;
   private final int frameTime;
   private final boolean interpolate;

   public AnimationMetadataSection(List<AnimationFrame> list, int i, int j, int k, boolean bl) {
      this.animationFrames = list;
      this.frameWidth = i;
      this.frameHeight = j;
      this.frameTime = k;
      this.interpolate = bl;
   }

   public int getFrameHeight() {
      return this.frameHeight;
   }

   public int getFrameWidth() {
      return this.frameWidth;
   }

   public int getFrameCount() {
      return this.animationFrames.size();
   }

   public int getFrameTime() {
      return this.frameTime;
   }

   public boolean isInterpolate() {
      return this.interpolate;
   }

   private AnimationFrame getAnimationFrame(int i) {
      return this.animationFrames.get(i);
   }

   public int getFrameTimeSingle(int i) {
      AnimationFrame animationframe = this.getAnimationFrame(i);
      return animationframe.hasNoTime() ? this.frameTime : animationframe.getFrameTime();
   }

   public boolean frameHasTime(int i) {
      return !this.animationFrames.get(i).hasNoTime();
   }

   public int getFrameIndex(int i) {
      return this.animationFrames.get(i).getFrameIndex();
   }

   public Set<Integer> getFrameIndexSet() {
      HashSet hashset = Sets.newHashSet();

      for (AnimationFrame animationframe : this.animationFrames) {
         hashset.add(animationframe.getFrameIndex());
      }

      return hashset;
   }
}

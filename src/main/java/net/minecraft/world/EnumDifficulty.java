package net.minecraft.world;

public enum EnumDifficulty {
   PEACEFUL(0, "options.difficulty.peaceful"),
   EASY(1, "options.difficulty.easy"),
   NORMAL(2, "options.difficulty.normal"),
   HARD(3, "options.difficulty.hard");

   private static final EnumDifficulty[] difficultyEnums = new EnumDifficulty[values().length];
   private final int difficultyId;
   private final String difficultyResourceKey;

   private EnumDifficulty(int i2, String string2) {
      this.difficultyId = i2;
      this.difficultyResourceKey = string2;
   }

   public int getDifficultyId() {
      return this.difficultyId;
   }

   public static EnumDifficulty getDifficultyEnum(int i) {
      return difficultyEnums[i % difficultyEnums.length];
   }

   public String getDifficultyResourceKey() {
      return this.difficultyResourceKey;
   }

   static {
      for (EnumDifficulty enumdifficulty : values()) {
         difficultyEnums[enumdifficulty.difficultyId] = enumdifficulty;
      }
   }
}

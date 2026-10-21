package astolfo.account;

public enum AccountType {
   MOJANG("mojang"),
   THEALTENING("thealtening");

   public String domain;
   private static final AccountType[] VALUES = new AccountType[]{MOJANG, THEALTENING};

   private AccountType(String string2) {
      this.domain = string2;
   }
}

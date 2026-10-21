package astolfo.event.bus;

public enum MemberKind {
   METHOD,
   FIELD;

   private static final MemberKind[] VALUES = new MemberKind[]{METHOD, FIELD};
   private static String[] I;
}

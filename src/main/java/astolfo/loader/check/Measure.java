package astolfo.loader.check;

public interface Measure {
   String getName();

   void execute() throws MeasureException;
}

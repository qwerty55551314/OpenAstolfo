package astolfo.util;

import java.awt.Component;
import java.awt.HeadlessException;
import java.io.File;
import javax.swing.JDialog;
import javax.swing.JFileChooser;

public final class TopFileChooser extends JFileChooser {
   public TopFileChooser(File file) {
      super(file);
   }

   @Override
   public JDialog createDialog(Component component) throws HeadlessException {
      JDialog jdialog = super.createDialog(component);
      jdialog.setLocationByPlatform(true);
      jdialog.setAlwaysOnTop(true);
      return jdialog;
   }
}

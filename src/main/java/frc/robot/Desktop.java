package frc.robot;


import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Desktop {

   public static void main(String[] args) {
      new Desktop().run();
   }

    public void run() {
        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        NetworkTable table = inst.getTable("datatable");
        NetworkTableEntry xEntry = table.getEntry("Top Left Pixel");
        inst.startClientTeam(2856);
        inst.startDSClient();
        while (true) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            System.out.println("interrupted");
            return;
        }
        double[] x = xEntry.getDoubleArray(new double[]{});
        System.out.println(x[0]);
        }
  }
}

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Utils {

    // TODAS LAS CONSTANTES EN SISTEMA INTERNACIONAL (metro, kilogramo, segundo)

    public static double INIT_SPECIAL_X = -10;
    public static double INIT_SPECIAL_Y = 0;
    public static double VEL_SPECIAL_X = 1;
    public static double VEL_SPECIAL_Y = 0;
    public static double LARGE_X = 105;
    public static double LARGE_Y = 68;
    public static double[] ALPHA_VELOCITY = {3.0 / 20, 251.0 / 360, 1, 11.0 / 18, 1.0 / 6, 1.0 / 60};
    public static double[] ALPHA_POSITION = {3.0 / 16, 251.0 / 360, 1, 11.0 / 18, 1.0 / 6, 1.0 / 60};
    public static double DELTA_TIME = 1.0/240.0;
    public static double Kn = 1.2 * Math.pow(10, 5);
    public static double Kt = 2.4 * Math.pow(10, 5);
    public static double A = 2000;
    public static double B = 0.08;
    public static double MASS = 80;
    public static double RADIUS = 0.6;

    /*
    public static SimulationConfig readConfig(String path){
        Gson gson = new Gson();
        SimulationConfig sConfig = null;
        try (FileReader reader = new FileReader(path)) {
            sConfig = gson.fromJson(reader, SimulationConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sConfig;
    }

    public static void writeStatus(SimulationConfig simulationConfig){
        String statusFile = "Simulation/Output/Status.json";

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("totalTime", simulationConfig.getTotalTime());
            jsonObject.put("deltaT", simulationConfig.getDeltaT());
            jsonObject.put("initialTime", simulationConfig.getInitialTime());
            jsonObject.put("deltaW", simulationConfig.getDeltaW());
            jsonObject.put("alpha", simulationConfig.getAlpha());

            FileWriter writer_status = new FileWriter(statusFile);
            writer_status.write(jsonObject.toString());
            writer_status.close();

        } catch(IOException e){
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }
     */

    public static void readCSV(String path, double[][] data){
        String line = "";
        String CSV_SEPARATOR = ",";
        int i = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            br.readLine();
            br.readLine();
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] csvLine = line.split(CSV_SEPARATOR);
                for(int j = 0; j < csvLine.length; j++){
                    if(csvLine[j].isBlank() || csvLine[j].isEmpty()){
                        data[i][j] = -1;
                    }else{
                        data[i][j] = Float.parseFloat(csvLine[j]);
                    }
                }
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

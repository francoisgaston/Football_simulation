import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Utils {

    // TODAS LAS CONSTANTES EN SISTEMA INTERNACIONAL (metro, kilogramo, segundo)




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

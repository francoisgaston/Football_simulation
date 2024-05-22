import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        // PONER EN UN INPUT
        int inicio_intervalo = 5;
        int fin_intervalo = 15;
        String OutputPath = "Simulation/Output/Salida.csv";


        double[][] Local = new double[141156][33];
        Utils.readCSV("Simulation/Input/TrackingData_Local.csv", Local);
        double[][] Visitante = new double[141156][29];
        Utils.readCSV("Simulation/Input/TrackingData_Visitante.csv", Visitante);

        try {
            FileWriter fw = new FileWriter(OutputPath);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write("Frame,Time,Bx,By,Sx,Sy," +
                    "LP1x,LP1y,LP2x,LP2y,LP3x,LP3y,LP4x,LP4y,LP5x,LP5y,LP6x,LP6y,LP7x,LP7y,LP8x,LP8y,LP9x,LP9y,LP10x,LP10y,LP11x,LP11y,LP12x,LP12y,LP13x,LP13y,LP14x,LP14y" +
                    "VP1x,VP1y,VP2x,VP2y,VP3x,VP3y,VP4x,VP4y,VP5x,VP5y,VP6x,VP6y,VP7x,VP7y,VP8x,VP8y,VP9x,VP9y,VP10x,VP10y,VP11x,VP11y,VP12x,VP12y");
            bw.write("\n");

            SimulationFactory.simulate( inicio_intervalo, fin_intervalo, Local, Visitante, bw);

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
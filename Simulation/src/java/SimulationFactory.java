import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.BiFunction;

public class SimulationFactory {

    public static void simulate(int inicio_intervalo, int fin_intervalo, double[][] Local, double[][] Visitante, BufferedWriter bw) throws IOException {
        for(int i =inicio_intervalo; i<fin_intervalo; i++){

            Double[] SpecialPlayerPosition = calculatePosition();

            bw.write(Local[i][1] + "," + Local[i][2] + "," + Local[i][31] + ',' + Local[i][32] + ',' + SpecialPlayerPosition[0] + ',' + SpecialPlayerPosition[1] + ',' +
                    Local[i][3] + "," + Local[i][4] + "," + Local[i][5] + "," + Local[i][6] + "," + Local[i][7] + "," + Local[i][8] + "," + Local[i][9] + "," + Local[i][10] + "," + Local[i][11] + "," + Local[i][12] + "," + Local[i][13] + "," + Local[i][14] + "," + Local[i][15] + "," + Local[i][16] + "," + Local[i][17] + "," + Local[i][18] + "," + Local[i][19] + "," + Local[i][20] + "," + Local[i][21] + "," + Local[i][22] + "," + Local[i][23] + "," + Local[i][24] + "," + Local[i][25] + "," + Local[i][26] + "," + Local[i][27] + "," + Local[i][28] + "," + Local[i][29] + "," + Local[i][30] + "," +
                    Visitante[i][3] + "," + Visitante[i][4] + "," + Visitante[i][5] + "," + Visitante[i][6] + "," + Visitante[i][7] + "," + Visitante[i][8] + "," + Visitante[i][9] + "," + Visitante[i][10] + "," + Visitante[i][11] + "," + Visitante[i][12] + "," + Visitante[i][13] + "," + Visitante[i][14] + "," + Visitante[i][15] + "," + Visitante[i][16] + "," + Visitante[i][17] + "," + Visitante[i][18] + "," + Visitante[i][19] + "," + Visitante[i][20] + "," + Visitante[i][21] + "," + Visitante[i][22] + "," + Visitante[i][23] + "," + Visitante[i][24] + "," + Visitante[i][25] + "," + Visitante[i][26] + "," + Visitante[i][27]);
            bw.write("\n");
        }
    }

    public static Double[] calculatePosition(){
        return new Double[]{0.0, 0.0};
    }
}

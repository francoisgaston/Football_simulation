import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.BiFunction;

public class SimulationFactory {

    public static void simulate(int inicio_intervalo, int fin_intervalo, double v_deseada, double tao ,double[][][] data, BufferedWriter bw) throws IOException {
        double[] SpecialPlayerPosition = new double[]{
            data[0][2][inicio_intervalo] + Utils.INIT_SPECIAL_X,
            data[0][3][inicio_intervalo] +  Utils.INIT_SPECIAL_Y,
            Utils.VEL_SPECIAL_X * v_deseada,
            Utils.VEL_SPECIAL_Y * v_deseada};

        for(int i =inicio_intervalo; i<fin_intervalo; i++){

            SpecialPlayerPosition = calculatePosition(SpecialPlayerPosition, v_deseada, tao, data, i);

            bw.write(data[0][0][i] + "," + data[0][1][i] + "," + data[0][2][i] + ',' + data[0][3][i] + ',' + SpecialPlayerPosition[0] + ',' + SpecialPlayerPosition[1] + ',' +
                    data[1][0][i] + "," + data[2][0][i] + "," + data[1][1][i] + "," + data[2][1][i] + "," + data[1][2][i] + "," + data[2][2][i] + "," + data[1][3][i] + "," + data[2][3][i] + "," + data[1][4][i] + "," + data[2][4][i] + "," + data[1][5][i] + "," + data[2][5][i] + "," + data[1][6][i] + "," + data[2][6][i] + "," + data[1][7][i] + "," + data[2][7][i] + "," + data[1][8][i] + "," + data[2][8][i] + "," + data[1][9][i] + "," + data[2][9][i] + "," + data[1][10][i] + "," + data[2][10][i] + "," + data[1][11][i] + "," + data[2][11][i] + "," +
                    data[3][0][i] + "," + data[4][0][i] + "," + data[3][1][i] + "," + data[4][1][i] + "," + data[3][2][i] + "," + data[4][2][i] + "," + data[3][3][i] + "," + data[4][3][i] + "," + data[3][4][i] + "," + data[4][4][i] + "," + data[3][5][i] + "," + data[4][5][i] + "," + data[3][6][i] + "," + data[4][6][i] + "," + data[3][7][i] + "," + data[4][7][i] + "," + data[3][8][i] + "," + data[4][8][i] + "," + data[3][9][i] + "," + data[4][9][i] + "," + data[3][10][i] + "," + data[4][10][i] + "," + data[3][11][i] + "," + data[4][11][i]);
            bw.write("\n");
        }
    }

    public static double[] calculatePosition(double[] SpecialPlayerPosition, double v_deseada, double tao, double[][][] data, int frame){
        BiFunction<Double, Double, Double> acelerationXFuction =
                (posX, velX) -> {
                    double d = Math.pow(Math.pow(data[0][2][frame] - posX, 2) + Math.pow(data[0][3][frame] - SpecialPlayerPosition[1], 2), 0.5);
                    double acelX = (Utils.MASS / tao) * ( (v_deseada * (data[0][2][frame] - posX)  / d) - (velX) );
                    return acelX;
                };

        BiFunction<Double, Double, Double> acelerationYFuction =
                (posY, velY) -> {
                    double d = Math.pow(Math.pow(data[0][2][frame] - SpecialPlayerPosition[0], 2) + Math.pow(data[0][3][frame] - posY, 2), 0.5);
                    double acelY = (Utils.MASS / tao) * ( (v_deseada * (data[0][3][frame] - posY)  / d) - (velY) );
                    return acelY;
                };

        double[] rx;
        double[] ry;

        for(int i=0; i<10; i++){
            rx = GearMethod(SpecialPlayerPosition[0], SpecialPlayerPosition[2], Utils.DELTA_TIME, acelerationXFuction, Utils.ALPHA_VELOCITY);
            ry = GearMethod(SpecialPlayerPosition[1], SpecialPlayerPosition[3], Utils.DELTA_TIME, acelerationYFuction, Utils.ALPHA_VELOCITY);
            SpecialPlayerPosition[0] = rx[0];
            SpecialPlayerPosition[1] = ry[0];
            SpecialPlayerPosition[2] = rx[1];
            SpecialPlayerPosition[3] = ry[1];
        }
        return SpecialPlayerPosition;
    }


    public static double[] GearMethod(double pos, double vel, double deltaTime, BiFunction<Double, Double, Double> acelerationFuction, double[] alpha){
        double dr2 = acelerationFuction.apply(pos, vel);
        double dr3 = acelerationFuction.apply(vel, dr2);
        double dr4 = acelerationFuction.apply(dr2, dr3);
        double dr5 = acelerationFuction.apply(dr3, dr4);

        double r5p = dr5;
        double r4p = dr4 + dr5 * deltaTime;
        double r3p = dr3 + dr4 * deltaTime + dr5 * Math.pow(deltaTime, 2) / 2;
        double r2p = dr2 + dr3 * deltaTime + dr4 * Math.pow(deltaTime, 2) / 2 + dr5 * Math.pow(deltaTime, 3) / 6;
        double r1p = vel + dr2 * deltaTime + dr3 * Math.pow(deltaTime, 2) / 2 + dr4 * Math.pow(deltaTime, 3) / 6 + dr5 * Math.pow(deltaTime, 4) / 24;
        double r0p = pos + vel * deltaTime + dr2 * Math.pow(deltaTime, 2) / 2 + dr3 * Math.pow(deltaTime, 3) / 6 + dr4 * Math.pow(deltaTime, 4) / 24 + dr5 * Math.pow(deltaTime, 5) / 120;

        double r2c = acelerationFuction.apply(r0p, r1p);
        double DR2 = (r2c - r2p) * Math.pow(deltaTime, 2) / 2;

        double r0 = r0p + alpha[0] * DR2 * 1 / Math.pow(deltaTime, 0);
        double r1 = r1p + alpha[1] * DR2 * 1 / Math.pow(deltaTime, 1);
        double r2 = r2p + alpha[2] * DR2 * 2 / Math.pow(deltaTime, 2);
        double r3 = r3p + alpha[3] * DR2 * 6 / Math.pow(deltaTime, 3);
        double r4 = r4p + alpha[4] * DR2 * 24 / Math.pow(deltaTime, 4);
        double r5 = r5p + alpha[5] * DR2 * 120 / Math.pow(deltaTime, 5);

        return new double[]{r0, r1};
    }
}

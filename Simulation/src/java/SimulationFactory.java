import java.io.BufferedWriter;
import java.io.IOException;
import java.util.function.BiFunction;

public class SimulationFactory {

    public static void simulate(double v_deseada, double tao ,double[][][] data, BufferedWriter bw) throws IOException {
        double[] SpecialPlayerPosition = new double[]{
            data[0][1][0] + Utils.INIT_SPECIAL_X, // X = 0
            data[0][1][1] + Utils.INIT_SPECIAL_Y, // Y = 1
            Utils.VEL_SPECIAL_X * v_deseada, // Vx = 2
            Utils.VEL_SPECIAL_Y * v_deseada}; // Vy = 3

        for(double[][] frame : data){
            // [ [frame, time], [Bx,By], [P1x, P2y], [P2x, P2y], ... ]
            bw.write(frame[0][0] + "," + frame[0][1] + "," + frame[1][0] + ',' + frame[1][1] + ',' + SpecialPlayerPosition[0] + ',' + SpecialPlayerPosition[1] + ',' +
                    frame[2][0] + "," + frame[2][1] + "," + frame[3][0] + "," + frame[3][1] + "," + frame[4][0] + "," + frame[4][1] + "," + frame[5][0] + "," + frame[5][1] + "," + frame[6][0] + "," + frame[6][1] + "," + frame[7][0] + "," + frame[7][1] + "," + frame[8][0] + "," + frame[8][1] + "," + frame[9][0] + "," + frame[9][1] + "," + frame[10][0] + "," + frame[10][1] + "," + frame[11][0] + "," + frame[11][1] + "," + frame[12][0] + "," + frame[12][1] + "," +
                    frame[13][0] + "," + frame[13][1] + "," + frame[14][0] + "," + frame[14][1] + "," + frame[15][0] + "," + frame[15][1] + "," + frame[16][0] + "," + frame[16][1] + "," + frame[17][0] + "," + frame[17][1] + "," + frame[18][0] + "," + frame[18][1] + "," + frame[19][0] + "," + frame[19][1] + "," + frame[20][0] + "," + frame[20][1] + "," + frame[21][0] + "," + frame[21][1] + "," + frame[22][0] + "," + frame[22][1] + "," + frame[23][0] + "," + frame[23][1]);
            bw.write("\n");

            calculatePosition(SpecialPlayerPosition, v_deseada, tao, frame);
        }
    }

    public static void calculatePosition(double[] SpecialPlayerPosition, double v_deseada, double tao, double[][] frame){
        BiFunction<Double, Double, Double> acelerationXFuction = (posX, velX) -> {
                    double granular_N = 0, social = 0;

                    double d_ball = Math.pow(Math.pow(frame[1][0] - posX, 2) + Math.pow(frame[1][1] - SpecialPlayerPosition[1], 2), 0.5);
                    double deseo = Utils.MASS * ( (v_deseada * (frame[1][0] - posX)  / d_ball) - velX ) / tao;

                    for(int player = 2; player<frame.length; player++){
                        double d_player = Math.pow(Math.pow(frame[player][0] - posX, 2) + Math.pow(frame[player][1] - SpecialPlayerPosition[1], 2), 0.5);
                        double E = d_player - (Utils.RADIUS * 2);

                        if(E < 0){
                            granular_N += - Utils.Kn * E * (frame[player][0] - posX)/d_player;;
                        }

                        social += Utils.A * Math.exp(-E/Utils.B) * (frame[player][0] - posX)/d_player;
                    }
                    return (deseo + granular_N + social) / Utils.MASS;
                };

        BiFunction<Double, Double, Double> acelerationYFuction = (posY, velY) -> {
                    double granular_N = 0, social = 0;

                    double d_ball = Math.pow(Math.pow(frame[1][0] - SpecialPlayerPosition[0], 2) + Math.pow(frame[1][1] - posY, 2), 0.5);
                    double deseo = (Utils.MASS / tao) * ( (v_deseada * (frame[1][1] - posY)  / d_ball) - (velY) );

                    for(int player = 2; player<frame.length; player++){
                        double d_player = Math.pow(Math.pow(frame[player][0] - SpecialPlayerPosition[0], 2) + Math.pow(frame[player][1] - posY, 2), 0.5);
                        double E = d_player - (Utils.RADIUS * 2);

                        if(E < 0){
                            granular_N += - Utils.Kn * E * (frame[player][1] - posY)/d_player;
                        }

                        social += Utils.A * Math.exp(-E/Utils.B) * (frame[player][1] - posY)/d_player;
                    }
                    return (deseo + granular_N + social) / Utils.MASS;
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
    }


    public static double[][][] clearData(double[][] Local, double[][] Visitante, int inicio_intervalo, int fin_intervalo){
        double[][][] data = new double[fin_intervalo - inicio_intervalo][2 + 11 + 11][2];

        for(int i=0; i<fin_intervalo - inicio_intervalo; i++){
            //[frame, time]
            data[i][0][0] = Local[inicio_intervalo + i][1];
            data[i][0][1] = Local[inicio_intervalo + i][2];
            //[Bx, By]
            data[i][1][0] = Local[inicio_intervalo + i][31] * Utils.LARGE_X;
            data[i][1][1] = Local[inicio_intervalo + i][32] * Utils.LARGE_Y;
            for(int j=0; j<11; j++){
                data[i][2+j][0] = Local[inicio_intervalo + i][3+j*2] * Utils.LARGE_X;
                data[i][2+j][1] = Local[inicio_intervalo + i][3+j*2+1] * Utils.LARGE_Y;
                data[i][2+j+11][0] = Visitante[inicio_intervalo + i][3+j*2] * Utils.LARGE_X;
                data[i][2+j+11][1] = Visitante[inicio_intervalo + i][3+j*2+1] * Utils.LARGE_Y;
            }
        }
        return data;
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

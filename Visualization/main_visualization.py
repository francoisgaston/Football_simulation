import csv
import pandas as pd
import numpy as np
import json
import math
import sys
import cv2

# ---------------------------------------------------
# File stuff
# ---------------------------------------------------
OUTPUT_PATH = '../Simulation/'
DEFAULT_INPUT_PATH = '../Simulation/input/'
AVG_PATH = '/animation_output/'
#PARTICLES_COORDINATES_FILE2 = ('../../Simulation/output/SimulationData_' + str(N) + '_' + str(FAKE_L) +
#                               '_' + str(SPEED) + '.csv')
PLAYERS_COORDINATES_FILE = '../Simulation/Output/Salida_todo.csv'
#SIMULATION_INPUT_JSON = ('../../Simulation/output/' + 'StateData_' + str(N) + '_' + str(FAKE_L) +
#                         '_' + str(SPEED) + '.json')
CONFIG_FILE = '../../Simulation/input/input.json'
#OPENCV_OUTPUT_FILENAME = '../output/visualization_' + str(N) + '_' + str(FAKE_L) + str(SPEED)
OUTPUT_FILENAME = 'match_animation'
# ---------------------------------------------------
# Simulation stuff
# ---------------------------------------------------
HEIGHT = 68
WIDTH = 105
RADIUS_PLAYERS = 0.3 * 2
RADIUS_BALL = 0.3 * 2
# ---------------------------------------------------
# Video stuff
# ---------------------------------------------------
MP4_FORMAT = 'mp4'
SCALE_FACTOR = 10
BALL_COLOR = (255, 255, 255)
LOCO_COLOR = (0, 0, 0)
LOCAL_COLOR = (0, 100, 255)
VISITING_COLOR = (255, 0, 0)
CIRCLE_COLOR = (0, 0, 255)
PITCH_COLOR = (0, 255, 0)
FPS = 30.0
VIDEO_RES = 2000


def complete_visualization_opencv(players_coords):
    fourcc = cv2.VideoWriter_fourcc(*'mp4v')

    SCALED_HEIGHT = int(HEIGHT * SCALE_FACTOR)
    SCALED_WIDTH = int(WIDTH * SCALE_FACTOR)

    background_img = cv2.imread('./plots/cancha_de_futbol2.jpg')
    background_img = cv2.resize(background_img, (SCALED_WIDTH, SCALED_HEIGHT))

    video_writer = cv2.VideoWriter(OUTPUT_FILENAME + '.' + MP4_FORMAT, fourcc, FPS, (SCALED_WIDTH, SCALED_HEIGHT))
    for index, row in players_coords.iterrows():
        frame = np.full((SCALED_HEIGHT, SCALED_WIDTH, 3), PITCH_COLOR, dtype=np.uint8)

        frame = cv2.addWeighted(frame, 0, background_img, 1, 0)

        # El loco
        if math.isnan(row['Sx']) is False:
            loco_pos = [int(row['Sx'] * SCALE_FACTOR), int(row['Sy'] * SCALE_FACTOR)]
            cv2.circle(frame, tuple(loco_pos), int(RADIUS_PLAYERS * SCALE_FACTOR), LOCO_COLOR, -1)
            #cv2.circle(frame, tuple(loco_pos), int(RADIUS_PLAYERS * SCALE_FACTOR * 5), CIRCLE_COLOR, 3)

    # Local players
        for i in range(1,12): # [ 1, 12 )
            local_player = [int(row['LP' + str(i) +'x'] * SCALE_FACTOR), int(row['LP' + str(i) +'y'] * SCALE_FACTOR)]
            cv2.circle(frame, tuple(local_player), int(RADIUS_PLAYERS * SCALE_FACTOR), LOCAL_COLOR, -1)
            if i == 13:
                cv2.circle(frame, tuple(local_player), int(RADIUS_PLAYERS * SCALE_FACTOR * 5), CIRCLE_COLOR, 3)

        # Visiting players
        for i in range(1,12): # [ 1, 12 )
            visiting_player = [int(row['VP' + str(i) +'x'] * SCALE_FACTOR), int(row['VP' + str(i) +'y'] * SCALE_FACTOR)]
            cv2.circle(frame, tuple(visiting_player), int(RADIUS_PLAYERS * SCALE_FACTOR), VISITING_COLOR, -1)
            if i == 13:
                cv2.circle(frame, tuple(visiting_player), int(RADIUS_PLAYERS * SCALE_FACTOR * 5), CIRCLE_COLOR, 3)

        # Ball
        if math.isnan(row['Bx']) is False:
            ball_pos = [int(row['Bx'] * SCALE_FACTOR), int(row['By'] * SCALE_FACTOR)]
            cv2.circle(frame, tuple(ball_pos), int(RADIUS_BALL * SCALE_FACTOR), BALL_COLOR, -1)

        video_writer.write(frame)

    video_writer.release()
    cv2.destroyAllWindows()


def read_config_file(file_path):
    with open(file_path, 'r') as file:
        config_data = json.load(file)
    return config_data


def main():
    #config = read_config_file(SIMULATION_INPUT_JSON)
    players_coords = pd.read_csv(PLAYERS_COORDINATES_FILE, index_col=False)

    print('Drawing particles with opencv...')
    complete_visualization_opencv(players_coords)
    print('DONE!')


if __name__ == '__main__':
    main()
import pandas as pd
import numpy as np
import seaborn as sns
import matplotlib.pyplot as plt
from scipy.stats import gaussian_kde


# ---------------------------------------------------
# CAMBIAR
# ---------------------------------------------------
CELDA = 4
PLAYER_1 = 1
PLAYER_2 = 9
PLAYER_3 = 3
PLAYER_ESPECIAL = 0
# ---------------------------------------------------

# Leer los datos desde el archivo CSV
data = pd.read_csv('../Simulation/Output/Salida_todo.csv')
num_rows, num_cols = data.shape

player_numbers = [4 + PLAYER_1*2, 4 + PLAYER_2*2, 4 + PLAYER_3*2, 4 + PLAYER_ESPECIAL*2]

plt.figure(figsize=(10, 6))

for player_number in player_numbers:
    velocities = []
    for i in range(num_rows -1 ):
        j = player_number
        posX = data.iloc[i, j]
        next_posX = data.iloc[i+1, j]
        posY = data.iloc[i, j+1]
        next_posY = data.iloc[i+1, j+1]
        velocity = np.sqrt((posX - next_posX)**2 + (posY - next_posY)**2) / (1/24.0)
        if velocity < 6:
            velocities.append(velocity)

    counts, bin_edges = np.histogram(velocities, bins='sturges', density=True)
    bin_centers = (bin_edges[:-1] + bin_edges[1:]) / 2

    label = ''
    if player_number == 4 + PLAYER_ESPECIAL*2:
        label = 'Jugador'
        velocities.append(0)
    if player_number == 4 + PLAYER_1*2:
        label = 'Arquero'
    if player_number == 4 + PLAYER_2*2:
        label = 'Lateral'
    if player_number == 4 + PLAYER_3*2:
        label = 'Delantero'

    plt.plot(bin_centers, counts, linestyle='-', marker='o', label=label)
    #plt.hist(velocities, bins='sturges', density=True, histtype='step')

plt.xlabel("Velocidad (m/s)", fontsize=16)
plt.ylabel("PDF (s/m)", fontsize=16)
plt.legend(bbox_to_anchor=(0.5, 1.1), loc='upper center', borderaxespad=0, fontsize=12, ncol=4)
plt.show()

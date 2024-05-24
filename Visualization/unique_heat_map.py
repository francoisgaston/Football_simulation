import pandas as pd
import numpy as np
import seaborn as sns
import matplotlib.pyplot as plt


# ---------------------------------------------------
# CAMBIAR
# ---------------------------------------------------
CELDA = 4
PLAYER = 1
VISITANTE = False
# PLAYER ESPECIAL = 0
# ---------------------------------------------------

# Leer los datos desde el archivo CSV
data = pd.read_csv('../Simulation/Output/Salida_0.1_tao.csv')
num_rows, num_cols = data.shape

rows = 68 // CELDA # Redondear hacia abajo
cols = 105 // CELDA  # Redondear hacia abajo

# Crear la matriz de ceros
field = np.zeros((rows, cols))

player_number = 4 + PLAYER * 2
if VISITANTE:
    player_number += 22

for i in range(num_rows):
    j = player_number
    posX = data.iloc[i, j]
    posY = data.iloc[i, j+1]
    iX = int(posX // CELDA)
    iY = int(posY // CELDA)
    field[iY, iX] += 1

# Crear el mapa de calor
plt.figure(figsize=(10, 6))
sns.heatmap(field, cmap='RdYlGn_r', annot=False, cbar=True, xticklabels=False, yticklabels=False)

# Mostrar el gráfico
plt.show()






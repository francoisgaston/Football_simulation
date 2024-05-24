import pandas as pd
import numpy as np
import seaborn as sns
import matplotlib.pyplot as plt


# ---------------------------------------------------
# CAMBIAR
# ---------------------------------------------------
CELDA = 3
# ---------------------------------------------------

# Leer los datos desde el archivo CSV
data = pd.read_csv('../Simulation/Output/Salida.csv')
num_rows, num_cols = data.shape

rows = 68 // CELDA # Redondear hacia abajo
cols = 105 // CELDA  # Redondear hacia abajo

# Crear la matriz de ceros
field = np.zeros((rows, cols))

for i in range(num_rows):
    for j in range(4, num_cols, 2):
        posX = data.iloc[i, j]
        posY = data.iloc[i, j+1]
        iX = int(posX // CELDA)
        iY = int(posY // CELDA)
        if(iX >= cols):
            iX = cols - 1
        if(iY >= rows):
            iY = rows - 1
        if(iX < 0):
            iX = 0
        if(iY < 0):
            iY = 0
        field[iY, iX] += 1

# Crear el mapa de calor
plt.figure(figsize=(10, 6))
sns.heatmap(field, cmap='RdYlGn_r', annot=False, cbar=True, xticklabels=False, yticklabels=False)

# Mostrar el gráfico
plt.show()






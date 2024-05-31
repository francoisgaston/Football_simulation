import pandas as pd
import numpy as np
import seaborn as sns
import matplotlib.pyplot as plt

# ---------------------------------------------------
# CAMBIAR
# ---------------------------------------------------
CELDA = 1
# ---------------------------------------------------

# Leer los datos desde el archivo CSV
data = pd.read_csv('../Simulation/Output/Salida_todo.csv')
num_rows, num_cols = data.shape

rows = 68 // CELDA  # Redondear hacia abajo
cols = 105 // CELDA  # Redondear hacia abajo

# Crear la matriz de ceros
field_1 = np.zeros((rows, cols))
field_2 = np.zeros((rows, cols))

for i in range(num_rows):
    for j in range(4, num_cols, 2):
        #print(i, j)
        posX = data.iloc[i, j]
        posY = data.iloc[i, j+1]
        iX = int(posX // CELDA)
        iY = int(posY // CELDA)
        if iX >= cols:
            iX = cols - 1
        if iY >= rows:
            iY = rows - 1
        if iX < 0:
            iX = 0
        if iY < 0:
            iY = 0
        if j != 6 and j != 6 + 11 * 2:
            if 67942 > data.iloc[i, 0]:
                field_1[iY, iX] += 1
            else:
                field_2[iY, iX] += 1

# Crear el mapa de calor
plt.figure(figsize=(10, 6))
sns.heatmap(field_1/data.iloc[num_rows-1, 1], cmap='RdYlGn_r', annot=False, cbar=True, cbar_kws={'label': 'Visitas por segundo'})

# Ajustar las etiquetas de los ejes para mostrar 4 marcas
x_ticks = [0, cols // 4, cols // 2, 3 * cols // 4,  cols]
y_ticks = [0, rows // 4, rows // 2, 3* rows // 4, rows]

plt.xticks(ticks=x_ticks, labels=[int(tick * CELDA) for tick in x_ticks])
plt.yticks(ticks=y_ticks, labels=[int(tick * CELDA) for tick in y_ticks])

# Invertir el eje y para que el origen esté en la parte inferior izquierda
plt.gca().invert_yaxis()

# Mostrar el gráfico
plt.show()

# Crear el mapa de calor
plt.figure(figsize=(10, 6))
sns.heatmap(field_2/data.iloc[num_rows-1, 1], cmap='RdYlGn_r', annot=False, cbar=True, cbar_kws={'label': 'Visitas por segundo'})

# Ajustar las etiquetas de los ejes para mostrar 4 marcas
x_ticks = [0, cols // 4, cols // 2, 3 * cols // 4,  cols]
y_ticks = [0, rows // 4, rows // 2, 3* rows // 4, rows]

plt.xticks(ticks=x_ticks, labels=[int(tick * CELDA) for tick in x_ticks])
plt.yticks(ticks=y_ticks, labels=[int(tick * CELDA) for tick in y_ticks])

# Invertir el eje y para que el origen esté en la parte inferior izquierda
plt.gca().invert_yaxis()

# Mostrar el gráfico
plt.show()

plt.figure(figsize=(10, 6))

plt.imshow(field/data.iloc[num_rows-1, 1], cmap='RdYlGn_r', interpolation='bicubic')
cbar = plt.colorbar()
cbar.set_label('Visitas por tiempo')
# Ajustar las etiquetas de los ejes para mostrar 4 marcas

x_ticks = [0, cols // 4, cols // 2, 3 * cols // 4,  cols]
y_ticks = [0, rows // 4, rows // 2, 3* rows // 4, rows]

plt.xticks(ticks=x_ticks, labels=[int(tick * CELDA) for tick in x_ticks])
plt.yticks(ticks=y_ticks, labels=[int(tick * CELDA) for tick in y_ticks])

# Invertir el eje y para que el origen esté en la parte inferior izquierda
plt.gca().invert_yaxis()

# Mostrar el gráfico
plt.show()
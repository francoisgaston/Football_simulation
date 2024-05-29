import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import os
import re

# Directorio donde están tus archivos CSV
data_directory = "../Simulation/Output"

regex = r'Salida_(\d+\.\d+)_tao\.csv'

# Lista para guardar las distancias mínimas
total_distances = []

plt.figure(figsize=(10, 6))

# Recorre cada archivo CSV en el directorio
for filename in os.listdir(data_directory):
    if filename.endswith("tao.csv"):
        # Lee el archivo CSV
        file_path = os.path.join(data_directory, filename)
        df = pd.read_csv(file_path)

        # Calcula la distancia entre (spX, spY) y (mpX, mpY)
        distances = np.sqrt((df['Sx'] - df['Bx'])**2 + (df['Sy'] - df['By'])**2)

        if filename == "Salida_0.1_tao.csv":
            plt.plot(df['Time'], distances, label='τ=' + str(0.1) + ' s')
        if filename == "Salida_0.65_tao.csv":
            plt.plot(df['Time'], distances, label='τ=' + str(0.65) + ' s')
        if filename == "Salida_0.95_tao.csv":
            plt.plot(df['Time'], distances, label='τ=' + str(0.95) + ' s')


        # Encuentra la distancia mínima
        distance_prom = distances.mean()
        distance_std = distances.std() / np.sqrt(len(distances))

        # Guarda el nombre del archivo y la distancia mínima
        total_distances.append((float(re.search(regex, filename).group(1)), distance_prom, distance_std))

plt.ylabel("Distancia (m)", fontsize=16)
plt.xlabel("Tiempo (s)", fontsize=16)
plt.legend(bbox_to_anchor=(0.5, 1.1), loc='upper center', borderaxespad=0, fontsize=12, ncol=3)
plt.grid(False)
plt.show()


# Convierte a DataFrame para facilitar el uso
distances_df = pd.DataFrame(total_distances, columns=["Filename", "Distance", "Error"])

# Ordena por nombre del archivo (si es necesario)
distances_df.sort_values(by="Filename", inplace=True)

# Crea el gráfico de distancias mínimas
plt.figure(figsize=(10, 6))
plt.errorbar(distances_df['Filename'], distances_df["Distance"], yerr=distances_df['Error'], fmt='o-', label='Min Distance')
plt.xlabel("Tiempo de relajación (s)", fontsize=16)
plt.ylabel("Distancia (m)", fontsize=16)
plt.ticklabel_format(axis="y", style="sci", useMathText=True)
plt.grid(False)
plt.show()
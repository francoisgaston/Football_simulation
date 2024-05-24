import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import os
import re

# Directorio donde están tus archivos CSV
data_directory = "../Simulation/Output"

regex = r'Salida_(\d+\.\d+)_vel\.csv'

plt.figure(figsize=(10, 6))

# Lista para guardar las distancias mínimas
total_distances = []

# Recorre cada archivo CSV en el directorio
for filename in os.listdir(data_directory):
    if filename.endswith("vel.csv"):
        # Lee el archivo CSV
        file_path = os.path.join(data_directory, filename)
        df = pd.read_csv(file_path)

        # Calcula la distancia entre (spX, spY) y (mpX, mpY)
        distances = np.sqrt((df['Sx'] - df['Bx'])**2 + (df['Sy'] - df['By'])**2)

        # Encuentra la distancia mínima
        distance_prom = distances.mean()
        distance_std = distances.std()

        # Guarda el nombre del archivo y la distancia mínima
        total_distances.append((float(re.search(regex, filename).group(1)), distance_prom, distance_std))

        if filename == "Salida_0.1_vel.csv":
            plt.plot(df['Time'], distances, label='V=' + str(0.1) + 'm/s' )
        if filename == "Salida_5.1_vel.csv":
            plt.plot(df['Time'], distances, label='V=' + str(5.1) + 'm/s' )
        if filename == "Salida_12.6_vel.csv":
            plt.plot(df['Time'], distances, label='V=' + str(12.6) + 'm/s' )

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
plt.xlabel("Velocidad deseada (m/s)", fontsize=16)
plt.ylabel("Distancia (m)", fontsize=16)
plt.ticklabel_format(axis="y", style="sci", useMathText=True)
plt.grid(False)
plt.show()
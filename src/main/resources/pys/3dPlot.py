# -*- coding: utf-8 -*-
"""
Created on Sat Jul  4 20:35:33 2020

@author: SERGI
"""
import matplotlib.pyplot as plt
from json import load
import numpy as np
import sys
from mpl_toolkits import mplot3d
from mpl_toolkits.mplot3d import Axes3D


class Plotting3D:

    def __init__(self, rootPath, champions, title):

        self.rootPath = rootPath
        self.title = title
        self.champions = champions
        self.champions_dict = {}
        self.jsonPath = self.rootPath + "target/json/"
        self.readJson()

    def readJson(self):
        with open(self.jsonPath + self.title+".json", "r") as file:
            self.champions_dict = load(file)

    def generate3Dplot(self, colors):
        # es una pena que "no" se puedan guardar las animaciones de plt :(
        fig = plt.figure()
        ax = plt.axes(projection="3d")
# =============================================================================
#         para poner la imagen de fondo.. consume muchos recursos..xd
#         pero entender esto
#         fn = cbook.get_sample_data(os.getcwd()[:len(os.getcwd())-13]+
#            "visualization\\resources\\inicial.png", asfileobj=False)
#         img = read_png(fn)
#         x, y = np.ogrid[0:img.shape[0], 0:img.shape[1]]
#         ax.plot_surface(x, y, np.zeros((len(x),len(y))) , rstride=1,
#             cstride=1, facecolors=img)
# =============================================================================
        # pos_pre=self.champions_dict[self.champions[0]][0]
        for idx, champion_name in enumerate(self.champions):
            x = []
            y = []
            z = []
            time = 0

            for i in range(1, len(self.champions_dict[champion_name]) - 1):

                if (self.champions_dict[champion_name][i] != None):
                    x.append(self.champions_dict[champion_name][i][0])
                    y.append(self.champions_dict[champion_name][i][1])
                    z.append(time)

                time += 1
            ax.plot3D(x, y, z, color=colors[idx])
            ax.scatter3D(x, y, z, c=z, cmap='hsv')

            ax.plot3D(x, y, np.zeros(len(z)), color=colors[idx])

        plt.savefig(self.rootPath + "target/plots/" + self.title + "3D.png")
        plt.show()


if __name__ == "__main__":
    
    print("hello from python", flush=True)
    rootPath = str(sys.argv[1])
    champis = sys.argv[2].split("#")
    champions = [champ for champ in champis if champ != ""]
    title = str(sys.argv[3])
    colors = ["blue", "red", "green", "yellow", "black"]
    
# =============================================================================
#     rootPath = "put your root path here"
#     champions = ["amumu","caitlyn","darius","fizz","morgana"]
#     title = "threshold-0.33_Si-100"
#     colors = ["blue", "red", "green", "yellow", "black"]
# =============================================================================
    
    plotting = Plotting3D(rootPath, champions, title)
    plotting.generate3Dplot(colors)
    print("finnish", flush=True)
    

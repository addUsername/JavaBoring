# -*- coding: utf-8 -*-
"""
Created on Sat Jul  4 18:05:19 2020

@author: SERGI
"""


import matplotlib.pyplot as plt
from matplotlib import cbook
from json import load
import sys


class Plotting:

    def __init__(self, rootPath, title):

        self.rootPath = rootPath
        self.title = title
        self.champions_dict = {}
        self.jsonPath = self.rootPath + "target/json/"
        self.readJson()
        self.champions = [name for name in self.champions_dict.keys() if "0" not in name]

    def readJson(self):
        with open(self.jsonPath + self.title+".json", "r") as file:
            self.champions_dict = load(file)

    def generatePlot(self):

        imageFile = cbook.get_sample_data(self.rootPath + "src/main/resources/img/board.png")
        image = plt.imread(imageFile)
        plt.imshow(image)
        plt.suptitle(self.title, fontsize=14, fontweight='bold')

        print("primer champ", flush=True)
        x = []
        y = []

        for i in range(1, len(self.champions_dict[self.champions[0]])-1):
            if (self.champions_dict[self.champions[0]][i] != None):
                x.append(self.champions_dict[self.champions[0]][i][0])
                y.append(self.champions_dict[self.champions[0]][i][1])
            else:
                if(x != None):
                    plt.plot(x, y, 'b-')
                x = []
                y = []
        plt.plot(x, y, 'b-', label=self.champions[0])

        print("leyendo ", self.champions[1], flush=True)
        x = []
        y = []

        for i in range(1, len(self.champions_dict[self.champions[1]]) - 1):
            if (self.champions_dict[self.champions[1]][i] != None):
                x.append(self.champions_dict[self.champions[1]][i][0])
                y.append(self.champions_dict[self.champions[1]][i][1])
            else:
                if(x != None):
                    plt.plot(x, y, 'r-')
                x = []
                y = []
        plt.plot(x, y, 'r-', label=self.champions[1])

        print("leyendo ", self.champions[2], flush=True)
        x = []
        y = []
        for i in range(1, len(self.champions_dict[self.champions[2]]) - 1):
            if (self.champions_dict[self.champions[2]][i] != None):
                x.append(self.champions_dict[self.champions[2]][i][0])
                y.append(self.champions_dict[self.champions[2]][i][1])
            else:
                if(x != None):
                    plt.plot(x, y, 'g-')
                x = []
                y = []
        plt.plot(x, y, "g-", label=self.champions[2])

        print("leyendo ", self.champions[3], flush=True)
        x = []
        y = []
        for i in range(1, len(self.champions_dict[self.champions[3]]) - 1):
            if (self.champions_dict[self.champions[3]][i] != None):
                x.append(self.champions_dict[self.champions[3]][i][0])
                y.append(self.champions_dict[self.champions[3]][i][1])
            else:
                if(x != None):
                    plt.plot(x, y, 'y-')
                x = []
                y = []

        plt.plot(x, y, 'y-', label=self.champions[3])

        print("leyendo ", self.champions[4], flush=True)
        for i in range(1, len(self.champions_dict[self.champions[4]]) - 1):
            if (self.champions_dict[self.champions[4]][i] != None):
                x.append(self.champions_dict[self.champions[4]][i][0])
                y.append(self.champions_dict[self.champions[4]][i][1])
            else:
                if(x != None):
                    plt.plot(x, y, 'k-')
                x = []
                y = []

        plt.plot(x, y, 'k-', label=self.champions[4])

        plt.legend(bbox_to_anchor=(1.05, 1), loc='upper left', borderaxespad=0.)
        print ("guardando grafica en ", self.rootPath + "target/plots/")
        plt.savefig(self.rootPath + "target/plots/" + self.title + ".png")
        plt.savefig(self.rootPath + "target/plots/" + self.title + ".pdf")
        plt.show()

if __name__ == "__main__":

    print("hello from python")
    rootPath = str(sys.argv[1])
    title = str(sys.argv[2])
    
# =============================================================================
#    rootPath = "../prueba/"
#    title = "threshold-0.33_Si-300"
# =============================================================================
    
    plotting = Plotting(rootPath, title)
    plotting.generatePlot()
    print("finnish", flush=True)
    
    
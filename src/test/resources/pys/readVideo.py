# -*- coding: utf-8 -*-
"""
Created on Wed Jul  1 12:52:05 2020

@author: SERGI
"""
#importar solo metodos necesarios
import cv2
from json import dump
import sys
from pathlib import Path


def getChampions(champions):

    toReturn = []
    for champi in champions:
        #os.getcwd()[:len(os.getcwd())-13]+"visualization\\resources\\"+champi+".jpg"
        # TODO better path magnament
        champ = cv2.imread("C:/Users/SERGI/eclipse-workspace/prueba/src/main/resources/img/" + champi + ".jpg")
        dim = resizeChampion(champ.shape)
        row = dim[0]
        col = dim[1]
        champ = cv2.resize(champ, (col, row), interpolation=cv2.INTER_NEAREST)
        champ = cv2.GaussianBlur(champ, (5, 5), 0)
        champ = cv2.cvtColor(champ, cv2.COLOR_BGR2GRAY)
        champ = cv2.Canny(champ, 50, 100, True)
        toReturn.append(champ)

    return toReturn

def resizeChampion(shape):

    scale_percent = 52
    return [int(shape[1] * scale_percent / 100), int(shape[0] * scale_percent / 100)]

def writeJSON(json_path, title, champions_dict):

    print("writting json")
    #os.getcwd()[:len(os.getcwd())-13]+"visualization\\outputs\\
    with open(json_path + title + ".json", "w") as file:
        dump(champions_dict, file)

def readVideo(champions, path, threshold=0.33, second_inicial=10, frame_step=1, frame_stop=600, json_path = ""):

    title = "threshold-" + str(threshold) + "_Si-" + str(second_inicial)
    pathJSON = path.split()
    champions_img = getChampions(champions)
    progress = int ((frame_stop - (second_inicial * 30))/10)
    print(progress)
    champions_dict = {}
    for name in champions:
        champions_dict[name] = []

    h_champ, w_champ = champions_img[0].shape

    video_rgb = cv2.VideoCapture(path)
    video_rgb.set(cv2.CAP_PROP_POS_FRAMES, int((second_inicial*30)))

    # select portion image
    _, frame = video_rgb.read()
    h = frame.shape[0]
    w = frame.shape[1]
    h1 = int(h-h/4)
    w1 = int(w - w/7)

    fram_pos = 0
    while(video_rgb.isOpened()):

        ret, frame = video_rgb.read()

        if ret:
            if (fram_pos % frame_step == 0):
                if (fram_pos < frame_stop):
                    if(fram_pos % progress == 0):
                        print(str(fram_pos / progress), " % leido", flush=True)

                    # crop map area
                    frame = frame[h1:h, w1:w]

                    # frame transformation
                    frame = cv2.GaussianBlur(frame, (5, 5), 0)
                    frame = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
                    frame = cv2.Canny(frame, 50, 100, True)

                    i = 0
                    for champ in (champions_img):
                        # frame_compare = cv2.subtract(frame_canny,start)
                        res = cv2.matchTemplate(frame, champ, cv2.TM_CCOEFF_NORMED)

                        _, max_val, _, max_loc = cv2.minMaxLoc(res)
                        if(max_val > threshold):
                            champions_dict[champions[i]].append(
                                [max_loc[0] + int(w_champ/2),
                                 max_loc[1]+int(h_champ/2)]
                                )
                        else:
                            champions_dict[champions[i]].append(None)
                        i += 1
                    i = 0
                elif (fram_pos > frame_stop):
                    champions_dict["0frameStep"] = frame_step
                    champions_dict["0seg,f_step,f_stop"] = [
                        second_inicial, frame_step, frame_stop
                        ]
                    writeJSON(json_path, title, champions_dict)
                    return 1

            fram_pos += 1
        else:
            break
    champions_dict["0frameStep"] = frame_step
    champions_dict["0seg,f_step,f_stop"] = [second_inicial, frame_step, frame_stop]
    writeJSON(json_path, title, champions_dict)


if __name__ == "__main__":

    print("hello from python")
    path = str(sys.argv[1])
    print(path)
    champis = sys.argv[2].split("#")
    champions = [champ for champ in champis if champ != ""]
    print(champions)
    threshold = float(sys.argv[3])
    print(threshold)
    second_inicial = int(sys.argv[4])
    print(second_inicial)
    frame_step = int(sys.argv[5])
    print(frame_step)
    frame_stop = int(sys.argv[6])
    print(frame_stop)
    json_path = str(sys.argv[7])
    print(json_path)
    # champions = [champ for champ in "#amumu#caitlyn#darius#fizz#morgana".split("#") if champ != ""]
    # threshold = 0.33
    # second_inicial = 100
    # frame_step = 1
    # frame_stop = 3500   
    # print("Directory Path:", Path().absolute())  
    print("leyendo video")
    print(champions, path, threshold, second_inicial, frame_step, frame_stop)
    readVideo(champions, path, threshold, second_inicial, frame_step, frame_stop, json_path)
    print("the end")
    
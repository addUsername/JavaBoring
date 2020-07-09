# -*- coding: utf-8 -*-
"""
Created on Sun Jul  5 13:52:17 2020

@author: SERGI
"""

# -*- coding: utf-8 -*-
import cv2
import sys
from json import load
import time

class Writting:

    def __init__(self, rootPath, videoPath, title):

        self.rootPath = rootPath
        self.video_path = videoPath
        self.title = title
        self.champions_dict = {}
        self.output_path = self.rootPath + "target/video/"
        self.jsonPath = self.rootPath + "target/json/"
        self.readJson()

    def readJson(self):
        with open(self.jsonPath + self.title+".json", "r") as file:
            self.champions_dict = load(file)

    def writeVideo(self):
        '''
            Ok this is not pretty, its too big and complexity n^inf at least
        '''
        self.readJson()
        self.champions = [name for name in self.champions_dict.keys() if "0" not in name]
        # red,green,blue,yellow
        colors = [(255, 0, 0), (0, 0, 255), (0, 255, 0),
                  (0, 255, 255), (255, 255, 0)]

        fram_pos = 0
        frame_stop = self.champions_dict["0seg,f_step,f_stop"][2] * 30
        progress = int (frame_stop / 10)
        
        ult_valid_position = [tuple((0, 1)) for i in range(5)]

        video_rgb = cv2.VideoCapture(self.video_path)
        video_rgb.set(cv2.CAP_PROP_POS_FRAMES,
                      int((self.champions_dict["0seg,f_step,f_stop"][0] * 30))
                      )
        # video_rgb.set(cv2.CAP_PROP_POS_FRAMES, 2000)

        _, frame = video_rgb.read()

        h = frame.shape[0]
        w = frame.shape[1]
        h1 = int(h - h/4)
        w1 = int(w - w/7)
        frame = frame[h1:h, w1:w]
        print("escribiendo video")

        print(self.output_path + self.title+'.avi')
        out = cv2.VideoWriter(self.output_path + self.title+'.avi',
                              cv2.VideoWriter_fourcc(*'DIVX'),
                              30,
                              (frame.shape[1], frame.shape[0])
                              )

        while(video_rgb.isOpened()):

            ret, frame = video_rgb.read()
            if ((ret) and (fram_pos < frame_stop)):
                if(fram_pos % progress == 0):
                    print(str((fram_pos/progress)*10),"% procesado", flush=True)
                frame = frame[h1:h, w1:w]
                if(fram_pos % self.champions_dict["0seg,f_step,f_stop"][1] == 0):
                    i = 0
                    for champ in (self.champions):
                       position  = self.champions_dict[self.champions[i]][fram_pos]
                       if(position != None):
                           ult_valid_position[i] = [position[0], position[1]]
                           cv2.circle(frame,tuple((ult_valid_position[i])), 11, colors[i], 1)
                       else:
                           cv2.circle(frame,tuple((ult_valid_position[i])), 11, colors[i], 1)
                       i+=1

                else:
                    i=0
                    for champ in (self.champions):
                        cv2.circle(frame,tuple((ult_valid_position[i])), 11, colors[i], 1)
                        i += 1
                fram_pos += 1
                
                out.write(frame)
            else:
                break

        out.release()
        print("video escrito")

if __name__ == "__main__":

    print("hello from python")
    
    rootPath = str(sys.argv[1])
    videoPath = str(sys.argv[2])
    jsonName = str(sys.argv[3])
    
# =============================================================================
#     start = time.time()
#     rootPath = "/prueba/"
#     videoPath = "/prueba/videoHD.mp4"
#     jsonName = "threshold-0.33_Si-1200.0"
# =============================================================================
    print("leyendo video")
    writting = Writting(rootPath, videoPath, jsonName)
    writting.writeVideo()
    end = time.time()
    print(end - start)
    print("the end")

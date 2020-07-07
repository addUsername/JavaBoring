# -*- coding: utf-8 -*-
"""
Created on Tue Jul  7 20:14:22 2020

Simple script to join json files

@author: SERGI
"""

import json
import sys
import os

def readJson(path):
    with open(path, "r") as file:
        return json.load(file)
    
def writeJson(path, dicc):
    with open(path, "w") as file:
        json.dump(dicc, file)

if __name__ == "__main__":
    
    print("hello from python", flush=True)
    jsonPath = str(sys.argv[1])
# =============================================================================
#     jsonPath = "../eclipse-workspace/prueba/target/json/"
# =============================================================================
    jsonPathTemp = jsonPath+"temp/"
    
    arr = os.listdir(jsonPathTemp)
    arr.sort()
    print(arr)
    
    dict_to_json = {}
    
    dict_0 = readJson(jsonPathTemp + arr[0])
    dict_1 = readJson(jsonPathTemp + arr[1])
    dict_2 = readJson(jsonPathTemp + arr[2])
    dict_3 = readJson(jsonPathTemp + arr[3])
    
    keys = [name for name in dict_0.keys() if "0" not in name]
    
    for key in keys:
        dict_to_json[key] = dict_0[key] + dict_1[key] + dict_2[key] + dict_3[key]
    
    #0seg,f_step,f_stop
    seg =  dict_0['0seg,f_step,f_stop'][0]
    step = dict_0['0seg,f_step,f_stop'][1]
    stop = dict_3['0seg,f_step,f_stop'][2]
    
    dict_to_json['0seg,f_step,f_stop'] = [seg, step, stop]
    print("Escribiendo json: ", jsonPath+arr[0], flush=True)
    writeJson(jsonPath+arr[0], dict_to_json)
    
    print("finish", flush=True)
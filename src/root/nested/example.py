'''
Created on 22 feb. 2017

@author: Per Eriksson
'''

### IMPORTS ###
import csv
import matplotlib.pyplot as plt
from datetime import datetime

### FUNCTIONS ##############################
def unpack_CSV(csv_list):
    dates = []
    high = []
    low = []
    close = []
    
    for line in my_list[1:]:
        row = line[0].split(";")
        rowDate = row[0].split('-')
        dates.append(datetime(year=int(rowDate[0]), month=int(rowDate[1]), day=int(rowDate[2])))
        high.append(row[1])
        low.append(row[2])
        close.append(row[3])
        
    return dates,high,low,close

def calc_SMA(data, time):
    if time<0 or len(data)==0: 
        return -1
    
    sma=[]
    for index in enumerate(data):
        if index < time:
            sma.append(0)
        else:
            sma.append(sum(data[index-time:index])/time)
    
    return sma

### END FUNCTION BLOCK ######################


with open('050608-170217.csv', 'r') as f:
    reader = csv.reader(f)
    my_list = list(reader)
    
#print(len(my_list))
#print(my_list[1])

dates,high,low,close = unpack_CSV(my_list)

#print(len(dates))
#print(dates[2])
#plt.plot(dates,close)
#plt.show()

plt.plot(dates,close)
plt.show()


    
'''
Created on 22 feb. 2017

@author: Per Eriksson
'''

### IMPORTS ###
import csv
import matplotlib.pyplot as plt
from datetime import datetime
from math import floor
transactionCostMult = 0.998

### FUNCTIONS ##############################
def unpackCSV(csv_list):
    dates = []
    high = []
    low = []
    close = []
    
    for line in my_list[1:]:
        row = line[0].split(";")
        rowDate = row[0].split('-')
        dates.append(datetime(year=int(rowDate[0]), month=int(rowDate[1]), day=int(rowDate[2])))
        high.append(float(row[1]))
        low.append(float(row[2]))
        close.append(float(row[3]))
        
    return dates,high,low,close

def calcSMA(data, time):
    if time<0 or len(data)==0: 
        return -1
    
    sma=[]
    for (index,value) in enumerate(data):
        if index < time:
            sma.append(0)
        else:
            sma.append(sum(data[index-time:index])/time)
    
    return sma

def calcMomentum(data,time):
    return -1

def calcVolatility(data,time):
    return -1

def calcPrice(data,volatility,momentum,sma1,sma2):
    return -1
    
def doBuy(capital,price):
    noStocks = floor(capital/price)
    returnCap=capital - price*noStocks
    return returnCap,noStocks

def doSell(capital,price,noStocks):
    retSum = capital + price*noStocks*transactionCostMult
    number = 0
    return retSum,number

def daterange(start_date,end_date):
    for n in range(int ((end_date - start_date).days)):
        yield start_date + timedelta(n)
    
### END FUNCTION BLOCK ######################


with open('050608-170217.csv', 'r') as f:
    reader = csv.reader(f)
    my_list = list(reader)
    
#print(len(my_list))
#print(my_list[1])

dates,high,low,close = unpackCSV(my_list)

#print(len(dates))
#print(dates[2])
#plt.plot(dates,close)
#plt.show()

sma20=calcSMA(close, 20)
sma50=calcSMA(close, 50)
sma200=calcSMA(close, 200)

#plt.plot(dates,close, 'k')
#plt.plot(dates,sma50, 'r')
#plt.plot(dates,sma200, 'b')
#plt.show()

startCap = 10000 #10k SEK

trainDateStart = datetime(year=2006, month=3, day=20)
trainDateEnd = datetime(year=2011, month=2, day=4)

idxStart = dates.index(trainDateStart)
idxEnd = dates.index(trainDateEnd)
for day in close[idxStart:idxEnd]:
    print(day)
    
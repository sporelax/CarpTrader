'''
Created on 22 feb. 2017

@author: Per Eriksson
'''

import csv
import plotly as py
import plotly.graph_objs as go
from datetime import datetime

with open('050608-170217.csv',  'r') as f:
    reader = csv.reader(f)
    my_list = list(reader)
    
print(len(my_list))
print(my_list[1])

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

print(len(dates))
print(dates[2])

# Create traces
trace0 = go.Scatter(
    x = dates,
    y = high,
    mode = 'lines',
    name = 'lines'
)
trace1 = go.Scatter(
    x = dates,
    y = low,
    mode = 'lines+markers',
    name = 'lines+markers'
)
trace2 = go.Scatter(
    x = dates,
    y = close,
    mode = 'markers',
    name = 'markers'
)
data = [trace0, trace1, trace2]

py.offline.iplot(data, filename='line-mode')
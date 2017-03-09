'''
Created on 9 mars 2017

@author: EriksPer
'''


import time
import base64
from Crypto.Cipher import PKCS1_OAEP
from Crypto.PublicKey import RSA
import urllib.parse
import httplib2
import json

def print_json(j,prefix=''):
    for key, value in j.items():
        if isinstance (value,dict):
            print("%s%s", prefix,key)
            print_json(value, prefix+'  ')
        else:
            print("%s%s:%s", prefix,key,value)

username = 'perik911'
password = 'free4all'
service  = 'NEXTAPI'
URL='api.test.nordnet.se'
API_VERSION='2'

timestamp = int(round(time.time()*1000))
timestamp = str(timestamp)
buf = str.encode(username+':'+password+':'+timestamp)

rsakey=RSA.importKey(open('NEXTAPI_TEST_public.pem').read())
cipher=PKCS1_OAEP.new(rsakey)
cipher_text = cipher.encrypt(buf)
encrypted_hash = base64.b64encode(cipher_text)

headers = {"Accept": "application/json"}
conn = httplib2.HTTPSConnectionWithTimeout(URL)

# GET server status
response, content = conn.request('/next/'+API_VERSION + '/','GET', headers=headers)
j = json.loads(content)
print_json(j)

# POST login
params = urllib.parse.urlencode({'service': 'NEXTAPI', 'auth': encrypted_hash})
response, content = conn.request('/next/'+API_VERSION+'/login', 'POST',headers=headers, body=params)
j = json.loads(content)
print_json(j)
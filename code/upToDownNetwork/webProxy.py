#coding:utf-8
import time
from socket import *

serverPort = 8899
serverSocket = socket(AF_INET,SOCK_STREAM)
serverSocket.bind(('',serverPort))
serverSocket.listen(5)#欢迎之门

while True:
    print('ProxyServer Ready to server...')
    proxySocket,addr = serverSocket.accept() #等待连接
    message = proxySocket.recv(4096).decode() #获得客户端请求
    filename = message.split()[1].partition("//")[2] #提取出文件名
    print(filename)
    fileExist = "false"
    try: #看文件是否存在于代理服务器当中
        f = open(filename,"r")
        outputData = f.readlines()
        fileExist = "true"
        print('File Exists')
        for i in range(0,len(outputData)):
            proxySocket.send(outputData.encode())
    except IOError: #若不存在
        print("File no exist in proxyServer")
        if fileExist=="false":#此时参照TCP客户端
            mainSocket = socket(AF_INET,SOCK_DGRAM) #新建套接字，准备连接远程主机
            hostn = message.split()[1].partition("//")[2].partition("/")[0] #提取出远程主机的主机名
            print("HostName:",hostn)
            try:#尝试连接远程主机
                mainSocket.connect((hostn,80))
                print('connect to remote server port 80...')

                mainSocket.sendall(message.encode())
                buff = mainSocket.recv(4096)
                proxySocket.sendall(buff)

                tmpFile = open("/"+filename,"w") 
                tmpFile.writelines(bytes.decode(buff).replace('\r\n','\n')) #将文件存入代理服务器
                tmpFile.close()
            except:
                print("Illegal request")
        else:
            print("File Not Found...")
    proxySocket.close()
serverSocket.close()
             

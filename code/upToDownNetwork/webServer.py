from socket import *
serverPort = 12000
serverSocket = socket(AF_INET, SOCK_STREAM)
serverSocket.bind(('',serverPort))
serverSocket.listen(1)

while True:
    print('Ready to serve...')
    connectionSocket, addr = serverSocket.accept()
    try:
        message = connectionSocket.recv(1024)
        filename = message.split()[1]
        f = open(bytes.decode(filename[1:]))#此处需要从字节流转换成UTF-8
        outputData = f.read()
        #header就是http的报头格式
        header = ' HTTP/1.1 200 OK\nConnecion:close\nContent-Length:%d\nContent-Type:text/html\n\n'%(len(outputData))
        connectionSocket.send(str.encode(header))
        
        for i in range(0,len(outputData)):
            connectionSocket.send(str.encode(outputData[i]))
        connectionSocket.close()
    except IOError:
        header = ' HTTP/1.1 404 Found'
        connectionSocket.send(str.encode(header))
        connectionSocket.close()

serverSocket.close()

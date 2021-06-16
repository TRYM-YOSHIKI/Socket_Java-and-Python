# ソケットを使うためにsocketをimportする。
import socket

def Send_command(command):
  # 127.0.0.1のIPはローカルの意味だ。
  HOST = '127.0.0.1'
  # ポートはサーバーで設定した9998に接続する。
  PORT = 9998
  # ソケットを生成する。
  client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

  try:
    # connect関数でサーバーに接続する。
    client_socket.connect((HOST, PORT))

    # 10回のループでsend、receiveをする。
    #for i in range(1,10):
      # メッセージはjava hello messageで送信
      #msg = 'java hello message'
    # メッセージをバイナリ(byte)タイプに変換する。
    data = str(command).encode()
    # メッセージのサイズを計算する。
    length = len(data)
    # データサイズをlittleエンディアンタイプに変換してサーバーに送信する。
    client_socket.sendall(length.to_bytes(4, byteorder="little"))
    # データを送信する。
    client_socket.sendall(data)

    # サーバーからデータサイズを受信する。
    #data = client_socket.recv(4)
    # データ長さはlittleエンディアンでintを変換する。
    #length = int.from_bytes(data, "big")
    # データの長さを受信する。
    #data = client_socket.recv(length)
    # データを受信する。
    #msg = data.decode()
    # データをコンソールで出力する。
    #print('Received from : ', msg)
    # ソケットリソースを返却する。
    client_socket.close()
    return True
  except:
    # ソケットリソースを返却する。
    client_socket.close()
    return False

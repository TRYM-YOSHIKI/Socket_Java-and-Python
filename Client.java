package simplerace.x;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
 
public class Client {
  // 実行関数
  public static boolean Send(double send_data[]) {
    // ソケット宣言
    try (Socket client = new Socket()) {
      // ソケットに接続するため、接続情報を設定する。
      InetSocketAddress ipep = new InetSocketAddress("127.0.0.1", 9999);
      // ソケット接続
      client.connect(ipep);
      // ソケット接続が完了すればinputstreamとoutputstreamを受け取る。
      try (OutputStream sender = client.getOutputStream(); InputStream receiver = client.getInputStream();) {
        // メッセージはfor文を通って10回にメッセージを送信する。
        for (int i = 0; i < 14; i++) {
          // 送信するメッセージを作成する。
          String msg = String.valueOf(send_data[i]);
          // stringをbyte配列に変換する。
          byte[] data = msg.getBytes();
          // ByteBufferを通ってデータサイズをbyteタイプに変換する。
          ByteBuffer b = ByteBuffer.allocate(4);
          // byteフォマートはlittleエンディアンだ。
          b.order(ByteOrder.LITTLE_ENDIAN);
          b.putInt(data.length);
          // データ長さを送信
          sender.write(b.array(), 0, 4);
          // データ送信
          sender.write(data);

          data = new byte[4];
          // データを長さを受信
          receiver.read(data, 0, 4);
          // ByteBufferを通ってlittleエンディアンで変換してデータサイズを受け取る。
          b = ByteBuffer.wrap(data);
          b.order(ByteOrder.LITTLE_ENDIAN);
          int length = b.getInt();
          // データサイズほど、バッファーを設定する。
          data = new byte[length];
          // データを受け取る。
          receiver.read(data, 0, length);

          // byteタイプの데이터をstringタイプに変換する。
          msg = new String(data, "UTF-8");
          // コンソールに出力する。
          //System.out.println(msg);
        }
      }
      return true;
    } catch (Throwable e) {
      // エラーが発生する時コンソールに出力する。
      //e.printStackTrace();
      return false;
    }
  }
}
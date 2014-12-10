package example.jeromq_example;
import android.os.Handler;

import org.zeromq.ZMQ;
 
public class ZeroMQServer implements Runnable {
    private final Handler uiThreadHandler;
 
    public ZeroMQServer(Handler uiThreadHandler) {
        this.uiThreadHandler = uiThreadHandler;
    }
 
    @Override
    public void run() {
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket socket = context.socket(ZMQ.REP);
        socket.bind("tcp://127.0.0.1:5556");
 
        while(!Thread.currentThread().isInterrupted()) {
            byte[] msg = socket.recv(0);
            uiThreadHandler.sendMessage(
                    Util.bundledMessage(uiThreadHandler, new String(msg)));
            socket.send(new String(Util.reverseInPlace(msg)), 0);
        }
        socket.close();
        context.term();
    }
}

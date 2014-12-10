package example.jeromq_example;

import android.os.AsyncTask;
import android.os.Handler;
 
import org.zeromq.ZMQ;
 
public class ZeroMQMessageTask extends AsyncTask<String, Void, String> {
    private final Handler uiThreadHandler;
 
    public ZeroMQMessageTask(Handler uiThreadHandler) {
        this.uiThreadHandler = uiThreadHandler;
    }
 
    @Override
    protected String doInBackground(String... params) {
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket socket = context.socket(ZMQ.REQ);
        socket.connect("tcp://127.0.0.1:5556");
 
        socket.send(params[0].getBytes(), 0);
        String result = new String(socket.recv(0));
 
        socket.close();
        context.term();
 
        return result;
    }
 
    @Override
    protected void onPostExecute(String result) {
        uiThreadHandler.sendMessage(Util.bundledMessage(uiThreadHandler, result));
    }
}

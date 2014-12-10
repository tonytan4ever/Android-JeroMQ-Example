package example.jeromq_example;

import android.os.Handler;
import android.os.Bundle;
import android.os.Message;
 
public class Util {
	public static final String MESSAGE_PAYLOAD_KEY = "jeromq-service-payload";
	
	public static char[] reverseInPlace(byte[] input){
		
		char[] string = new char[input.length];
		for(int i=0;i < input.length;i++){
			string[i] = (char)input[i];
		};
		
		for(int i = 0, j = string.length - 1; i < string.length / 2; i++, j--) {
		    char c = string[i];
		    string[i] = string[j];
		    string[j] = c;
		}
		return string;
	};
	
	public static Message bundledMessage(Handler uiThreadHandler, String msg) {
		Message m = uiThreadHandler.obtainMessage();
	    prepareMessage(m, msg);
	    return m;
	};
	
	public static void prepareMessage(Message m, String msg){
	      Bundle b = new Bundle();
	      b.putString(MESSAGE_PAYLOAD_KEY, msg);
	      m.setData(b);
	      return ;      
	}
}

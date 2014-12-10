package example.jeromq_example;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends ActionBarActivity {
	private TextView textView;
    private EditText editText;
 
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");
    
    private static String getTimeString() {
        return DATE_FORMAT.format(new Date());
    }
 
    private void serverMessageReceived(String messageBody) {
        textView.append(getTimeString() + " - server received: " + messageBody + "\n");
    }
 
    private void clientMessageReceived(String messageBody) {
        textView.append(getTimeString() + " - client received: " + messageBody + "\n");
    }
	
    private final MessageListenerHandler serverMessageHandler = new MessageListenerHandler(
            new IMessageListener() {
                @Override
                public void messageReceived(String messageBody) {
                    serverMessageReceived(messageBody);
                }
            },
            Util.MESSAGE_PAYLOAD_KEY);
 
    private final MessageListenerHandler clientMessageHandler = new MessageListenerHandler(
            new IMessageListener() {
                @Override
                public void messageReceived(String messageBody) {
                    clientMessageReceived(messageBody);
                }
            },
            Util.MESSAGE_PAYLOAD_KEY);
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		textView = (TextView)findViewById(R.id.text_console);
        editText = (EditText)findViewById(R.id.text_message);
        
        new Thread(new ZeroMQServer(serverMessageHandler)).start();
        
        findViewById(R.id.button_send_message).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new ZeroMQMessageTask(clientMessageHandler).execute(getTaskInput());
                    }
     
                    protected String getTaskInput() {
                        return editText.getText().toString();
                    }
         });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		if (id == R.id.Exit) {
			this.finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

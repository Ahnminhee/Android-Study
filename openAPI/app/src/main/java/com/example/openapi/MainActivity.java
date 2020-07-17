package com.example.openapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    TextView textView;
    XmlPullParser xpp;
    XmlPullParserFactory factory;
    private StringBuffer buffer = new StringBuffer();
    
    String data;
    String tag;

    private static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);
        textView = (TextView) findViewById(R.id.result);

    }

    public void mOnClick(View v) {
        switch(v.getId()) {
            case R.id.button:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        data= getXmlData();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textView.setText(data);
                            }
                        });
                    }
                }).start();
                break;
        }
    }

    String getXmlData() {
        String queryUrl = "http://apis.data.go.kr/1741000/DisasterMsg2"
                + "&pageNo=1&numOfRows=10&ServiceKey=" + key;

        try{
            URL url = new URL(queryUrl);
            InputStream is = url.openStream();

            factory = XmlPullParserFactory.newInstance();
            xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8"));

            xpp.next();
            int eventType = xpp.getEventType();

            while(eventType != XmlPullParser.END_DOCUMENT) {
                buffer.append("시작");
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        buffer.append("파싱 시작");
                        break;

                    case XmlPullParser.START_TAG:
                        tag = xpp.getName();

                        if(tag.equals("item"));
                        else if (tag.equals("BUS_NODE_ID")) {
                            buffer.append("정류소ID: ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }

                        break;
                }
            }


        } catch (Exception e) {
            textView.setText("에러가 났습니다.");
        }

        buffer.append("파싱 끝 ^.^");

        return buffer.toString();
    }
}

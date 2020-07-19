package com.example.openapi;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private final String key = "l640r40BkQ%2Bmi6ZO9AvHYmCZZk%2BBVEBg%2BSG%2BKnR4Mn5MSa6Yv03XrQ2nlHVQST94XYFxPAn3rG2nExAqrm%2F2dg%3D%3D";
    private final String endPoint = "http://openapitraffic.daejeon.go.kr/api/rest/busposinfo/getBusPosByRtid";

    private EditText busNum_edit;
    private EditText station_edit;
    private TextView showInfo_text;

    private URL url;
    private InputStream is;
    private XmlPullParserFactory factory;
    private XmlPullParser xpp;
    private String tag;
    private int eventType;

    private String busNum;
    private String station = ""; //출발 정류장 arsNo
    private StringBuffer buffer;

    private String busNumId;
    private String stationId;

    private String car1 = null;
    private String min1 = null;
    private String station1 = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getXmlId();
        buffer = new StringBuffer();
    }

    public void mOnClick(View v) {
        busNum = busNum_edit.getText().toString();
        station = station_edit.getText().toString();

        buffer = null;
        buffer = new StringBuffer();
        showInfo_text.setText(" ");

        if (exminData()) {
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                getBusId(busNum);

                getStationId(station);

                userWant(busNumId, stationId);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (car1 == null) {
                            buffer.append("도착 정보가 없습니다.");
                        } else {
                            buffer.append(" 차량 도착 정보");
                            buffer.append("\n차량 번호: " + car1);
                            buffer.append("\n남은 시간: " + min1 + "분");
                            buffer.append("\n남은 구간: " + station1 + "정거장");
                        }

                        showInfo_text.setText(buffer.toString());
                    }
                });
            }
        }).start();
    }

    public void getStationId(String station) {
        String stationUrl = endPoint + "?serviceKey=" + key + "&busRouteId=" + station +"&";


        try {
            setUrlNParser(stationUrl);

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        tag = xpp.getName();

                        if (tag.equals("BUs_NODE_ID")){
                            xpp.next();
                            buffer.append(xpp.getText());

                        }
                        else if (tag.equals("BUS_STOP_ID")) {
                            xpp.next();
                            buffer.append(xpp.getText());
                        } else if (tag.equals("bstopArsno")) ;
                        else if (tag.equals("bstopArsno")) ;
                        else if (tag.equals("bstopNm")) ;
                        else if (tag.equals("gpsX")) ;
                        else if (tag.equals("gpsY")) ;
                        else if (tag.equals("stoptype")) ;
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag = xpp.getName();
                        if (tag.equals("item")) ;
                        break;

                }
            }
        } catch (Exception e) {
            buffer.append("get station error\n");
            e.printStackTrace();
        }
    }

    public void getBusId(String busNum) {
        /*String busNumUrl = endPoint + "?serviceKey=" + key + "&busRouteId=30300001&";*/

        String busNumUrl = "http://openapitraffic.daejeon.go.kr/api/rest/busposinfo/getBusPosByRtid?serviceKey=l640r40BkQ%2Bmi6ZO9AvHYmCZZk%2BBVEBg%2BSG%2BKnR4Mn5MSa6Yv03XrQ2nlHVQST94XYFxPAn3rG2nExAqrm%2F2dg%3D%3D&busRouteId=30300001&";

        try {
            setUrlNParser(busNumUrl);

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        tag = xpp.getName();

                        if (tag.equals("item")) ; //첫번째 검색 결과
                        else if (tag.equals("lineId")) {
                            xpp.next();
                            busNumId = xpp.getText();
                        } else if (tag.equals("buslinenum")) ;
                        else if (tag.equals("bustype")) ;
                        else if (tag.equals("companyid")) ;
                        else if (tag.equals("endpoint")) ;
                        else if (tag.equals("stoptype")) ;
                        else if (tag.equals("firsttime")) ;
                        else if (tag.equals("endtime")) ;
                        else if (tag.equals("headway")) ;
                        else if (tag.equals("headwayNorm")) ;
                        else if (tag.equals("headwayPeak")) ;
                        else if (tag.equals("headwayHoli")) ;
                        break;
                    case XmlPullParser.TEXT:
                        break;
                    case XmlPullParser.END_TAG:
                        tag = xpp.getName();
                        if (tag.equals("item")) ;
                        break;
                }
                eventType = xpp.next();
            }
        } catch (Exception e) {
            buffer.append("getBusId error\n");
            e.printStackTrace();
        }
    }

    public void userWant(String busNumId, String stationId) {
        /*String dataUrl = endPoint + "?serviceKey=" + key + "&busRouteId=30300001&";*/

        String dataUrl = "http://openapitraffic.daejeon.go.kr/api/rest/busposinfo/getBusPosByRtid?serviceKey=l640r40BkQ%2Bmi6ZO9AvHYmCZZk%2BBVEBg%2BSG%2BKnR4Mn5MSa6Yv03XrQ2nlHVQST94XYFxPAn3rG2nExAqrm%2F2dg%3D%3D&busRouteId=30300001&";


        try {
            setUrlNParser(dataUrl);

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        tag = xpp.getName();

                        if (tag.equals("item")) ;
                        else if (tag.equals("carNo1")) {
                            xpp.next();
                        } else if (tag.equals("min1")) {
                            xpp.next();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;
                    case XmlPullParser.END_TAG:
                        tag = xpp.getName();
                        if (tag.equals("item")) ;
                        break;
                }
            }
        } catch (Exception e) {
            buffer.append("userwant error\n");
            e.printStackTrace();
        }
    }

    public boolean exminData() {
        /*if (busNum.equals("") || station.equals("")) {
            Toast.makeText(this, "값을 입력해주세요", Toast.LENGTH_SHORT).show();
            return true;
        }
*/
        String regExp = "([0-9])";
        Pattern pattern_symbol = Pattern.compile(regExp);

        Matcher matcher_busNum = pattern_symbol.matcher(busNum);
        /*if (matcher_busNum.find() == false) {
            Toast.makeText(this, "버스 번호를 다시 입력해주세요", Toast.LENGTH_SHORT).show();
            return true;
        }*/
        Matcher matcher_station = pattern_symbol.matcher(station);
        if (matcher_station.find() == false) {
            Toast.makeText(this, "정류장 번호를 다시 입력해주세여", Toast.LENGTH_SHORT).show();
            return true;
        }

        return false;
    }

    public void setUrlNParser(String quary) {
        try {
            url = new URL(quary);
            is = url.openStream();

            factory = XmlPullParserFactory.newInstance();
            xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8"));

            xpp.next();
            eventType = xpp.getEventType();

            buffer.append("성공");
        } catch (Exception e) {
            Toast.makeText(this,"가져오기 실패", Toast.LENGTH_SHORT).show();
            buffer.append("url error\n");
        }

    }

    public void getXmlId() {
        busNum_edit = (EditText) findViewById(R.id.busNum_edit);
        station_edit = (EditText) findViewById(R.id.busNum_edit);
        showInfo_text = (TextView) findViewById(R.id.showInfo_text);

    }

}

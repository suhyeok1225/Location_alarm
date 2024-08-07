package honeyimleaving.toyproject.honeyimleaving.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.telephony.SmsManager;
import android.app.Activity;
import android.app.PendingIntent;

import com.toyproject.honeyimleaving.db.MyDBHandlerForSentHistory;
import com.toyproject.honeyimleaving.model.PlaceTask;
import com.toyproject.honeyimleaving.myutil.Dlog;

import java.util.ArrayList;

/*이는 SMS를 전송하는 기능을 제공하는 핸들러 클래스입니다.
        SendSmsHandler 클래스는 Handler 클래스를 상속받아 handleMessage() 메소드를 오버라이드합니다.
        handleMessage() 메소드에서는 메시지 객체를 받아와서 해당 객체에 저장된 PlaceTask 정보를 이용하여 SMS를 전송합니다.
        sendSMS() 메소드에서는 전화번호와 메시지를 입력받아 SMS를 전송합니다. 이 때, PendingIntent를 이용하여 SMS 전송 결과를 받아옵니다.
        MySmsBroadcastReceiver 클래스는 BroadcastReceiver 클래스를 상속받아 SMS 전송 결과를 받아 처리합니다.
        이 때, 전송 결과를 MyDBHandlerForSentHistory 클래스를 이용하여 데이터베이스에 저장합니다.
        즉, 이 코드는 SMS를 전송하고, 전송 결과를 처리하여 데이터베이스에 저장하는 기능을 제공합니다.

 */

public class SendSmsHandler extends Handler {

    private Context mContext;
    private PlaceTask placeTask;
    public SendSmsHandler(Looper looper, Context context) {
        super(looper);
        this.mContext = context;


    }

    @Override
    public void handleMessage(Message msg) {
        if(msg.what == 0) {
            placeTask = (PlaceTask) msg.obj;
            if (placeTask != null) {
                try {
                    Dlog.e("Send Msg is 0");
                    sendSMS(placeTask.getTaskID(), placeTask.getMobileNumbersList(), placeTask.getPlaceAlert().getSmsContents());
                } catch (Exception e) {
                    Dlog.e("Exception :" + e.getMessage());
                    if (placeTask == null) return;
                    MyDBHandlerForSentHistory dbSentHistory = new MyDBHandlerForSentHistory(mContext);
                    dbSentHistory.insertSendHistory(placeTask, -9999);
                    dbSentHistory.close();
                }
            }
        }
    }

    private void sendSMS(int id, ArrayList<String> mobile, String message) {
        if(mContext == null) return;

        for(int i = 0 ; i < mobile.size() ; i++) {
            sendSMS(id, mobile.get(i), message);
        }
    }

    private void sendSMS(int id, String phoneNumber, String message) {
        if(mContext == null) return;

        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(mContext, id, new Intent(SENT), PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(mContext, id, new Intent(DELIVERED), PendingIntent.FLAG_UPDATE_CURRENT);

        mContext.registerReceiver(new MySmsBroadcastReceiver(), new IntentFilter(SENT));
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
        Dlog.d("Send Message: " + phoneNumber + ", " +  message);
    }

    public class MySmsBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Dlog.d("getResultCode : " + getResultCode());
            if (placeTask == null) return;
            MyDBHandlerForSentHistory dbSentHistory = new MyDBHandlerForSentHistory(mContext);
            dbSentHistory.insertSendHistory(placeTask, getResultCode());
            dbSentHistory.close();
        }
    }
}

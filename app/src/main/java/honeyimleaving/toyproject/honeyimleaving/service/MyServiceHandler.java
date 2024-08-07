package honeyimleaving.toyproject.honeyimleaving.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.app.ActivityManager;
import android.support.annotation.NonNull;
import android.app.ActivityManager.*;

import com.toyproject.honeyimleaving.model.PlaceTask;
import com.toyproject.honeyimleaving.myutil.Dlog;

/*MyServiceHandler 클래스는 PlaceTaskGPSCheckService와 연동하기 위한 클래스입니다.
        ServiceConnection 인터페이스를 구현하여 PlaceTaskGPSCheckService와 연결하고, 서비스와의 통신을 위한 메소드들을 제공합니다.
        onServiceConnected() 메소드에서는 PlaceTaskGPSCheckService와 연결되었을 때 호출되며, LocalBinder 클래스를 사용하여 PlaceTaskGPSCheckService 객체를 가져옵니다.
        onServiceDisconnected() 메소드에서는 PlaceTaskGPSCheckService와 연결이 끊어졌을 때 호출되며, mService와 mBound 변수를 초기화합니다.
        onStop() 메소드에서는 PlaceTaskGPSCheckService와의 연결을 해제합니다.
        BindService() 메소드에서는 PlaceTaskGPSCheckService와 연결을 시도합니다.
        isServiceRunningCheck() 메소드에서는 PlaceTaskGPSCheckService가 실행 중인지 확인합니다.
        isEmptyWaitingTaskQueue() 메소드에서는 PlaceTaskGPSCheckService의 대기열이 비어있는지 확인합니다.
        addTask() 메소드에서는 PlaceTaskGPSCheckService의 대기열에 PlaceTask를 추가합니다.
        removeTask() 메소드에서는 PlaceTaskGPSCheckService의 대기열에서 PlaceTask를 제거합니다.
        startService() 메소드에서는 PlaceTaskGPSCheckService에서 위치 정보를 확인하도록 요청하는 메소드를 호출합니다.

 */

public class MyServiceHandler {
    private PlaceTaskGPSCheckService mService = null;
    private boolean mBound = false;
    private Context mContext;

    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Dlog.d("onServiceConnected 실행됨.");
            PlaceTaskGPSCheckService.LocalBinder binder = (PlaceTaskGPSCheckService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Dlog.d("onServiceDisconnected 실행됨.");
            mService = null;
            mBound = false;
        }
    };

    public MyServiceHandler(@NonNull Context context) {
        mContext = context;
    }


    public ServiceConnection getServiceConnection() {
        return mServiceConnection;
    }

    public void onStop() {
        if (mBound) {
            mContext.unbindService(mServiceConnection);
            mBound = false;
            Dlog.d("unbinding 함");
        }
    }

    public boolean BindService(@NonNull Intent intent,@NonNull int mode) {
        if (mServiceConnection == null) return false;
        return mContext.bindService(intent, mServiceConnection, mode);
    }

    public boolean isServiceRunningCheck() {
        final String SERVICE_NAME_FOR_CHECKING = "com.toyproject.honeyimleaving.service.PlaceTaskGPSCheckService";
        ActivityManager manager =  (ActivityManager)mContext.getSystemService(Context.ACTIVITY_SERVICE);

        for(RunningServiceInfo runningServiceInfo : manager.getRunningServices(Integer.MAX_VALUE)) {
            Dlog.d("getClassName() : " + runningServiceInfo.service.getClassName());
            if (SERVICE_NAME_FOR_CHECKING.equals(runningServiceInfo.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public boolean isEmptyWaitingTaskQueue() {
        return mService.isEmptyWaitingTaskQueue();
    }

    public void addTask(PlaceTask placeTask) {
        mService.addPlaceTask(placeTask);
    }

    public void removeTask(PlaceTask placeTask) {
        mService.removePlaceTask(Integer.valueOf(placeTask.getTaskID()));
    }
    public void startService() {
        if(mService == null) {
            Dlog.d("mService가 null 입니다.");
            return;
        }
        Dlog.d("mService.requestLocationUpdates(); 실행함");
        mService.requestLocationUpdates();
        //mService.startForground(); 이거 활성화 할지 조금만 생각해보자.
    }

}
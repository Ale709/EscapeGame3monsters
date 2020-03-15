package com.ale059.escapegame3monsters;

import android.os.Handler;
import android.os.Message;

public class ATimer {
    protected int mTimePeriod = 1000;
    protected int mTimerTicksCounter = 0;

    protected static final int ON_TIMER = app.uid();

    protected events mCallback = null;

    private boolean mThreadOnTimer_StopFlag = true;
    protected Thread mThreadOnTimer = null;


    interface events {
        void onTimerEvent(ATimer poTimer);
    }

    protected void StartTimer() {
        if (mThreadOnTimer_StopFlag == true) {
            mThreadOnTimer_StopFlag = false;

            boolean bCreateNewThread = false;
            if (mThreadOnTimer == null)
                bCreateNewThread = true;
            else if (mThreadOnTimer.getState() != Thread.State.RUNNABLE)
                bCreateNewThread = true;

            if (bCreateNewThread) {
                mThreadOnTimer = new Thread() {
                    @Override
                    public void run() {
                        while (mThreadOnTimer_StopFlag == false) {
                            try {
                                sleep(mTimePeriod);
                                if (mThreadOnTimer_StopFlag == false)
                                    if (mHandler4Timer != null)
                                        mHandler4Timer.sendMessage( mHandler4Timer.obtainMessage(ON_TIMER) );
                                mTimerTicksCounter++;
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        mThreadOnTimer_StopFlag = true;
                    }
                };
            }

            mThreadOnTimer.start();


//			try
//			{
//				mThreadOnTimer.start();
//
//
//			} catch (Exception e)
//			{
//				log.v("[EX] "+e.getLocalizedMessage()+"   "+String.valueOf(mThreadOnTimer.getState()));
//				// TODO: handle exception
//			}
        }
    }

    public void StartTimer(int pnTimePeriod, events ppCallback) {
        mTimePeriod = pnTimePeriod;
        mCallback = ppCallback;
        StartTimer();
    }

    public void StopTimer() {
        if (mThreadOnTimer_StopFlag == false) {
            mThreadOnTimer_StopFlag = true;
//	    	while (mThreadOnTimer_StopFlag == true)
//	    	{
//	    		//sleep(250);
//	    	}
        }
    }

 /*   @Override
    protected void onDestroy() {
        StopTimer();

        super.onDestroy();

    }
*/
    protected Handler mHandler4Timer = new Handler()
            //private Handler handlerOnTimer = new Handler()
    {
        public void handleMessage(Message msg) {
            if (msg.what == ON_TIMER) {
                //onTimer();
                if (mCallback != null)
                    mCallback.onTimerEvent(ATimer.this);
            }
        }

        ;
    };

    public void onTimer() {
    }
}

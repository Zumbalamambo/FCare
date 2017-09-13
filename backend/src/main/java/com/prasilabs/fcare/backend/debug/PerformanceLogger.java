package com.prasilabs.fcare.backend.debug;

/**
 * Created by prasi on 30/1/17.
 */

public class PerformanceLogger
{
    public static void logTimeTaken(String TAG, long startTime, long endTime)
    {
        long timeDiff = endTime - startTime;

        if(timeDiff > 5000)
        {
            ConsoleLog.w(TAG, "time taken is : " + timeDiff);
        }
        else
        {
            ConsoleLog.i(TAG, "time taken is : " + timeDiff);
        }
    }
}

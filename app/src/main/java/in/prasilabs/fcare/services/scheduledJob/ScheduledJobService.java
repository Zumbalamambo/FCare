package in.prasilabs.fcare.services.scheduledJob;

import android.app.AlarmManager;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.prasilabs.droidwizardlib.debug.ConsoleLog;

import in.prasilabs.fcare.modelEngines.FirebaseStatusUploadModelEngine;

/**
 * Created by prasi on 23/1/17.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class ScheduledJobService extends JobService
{
    private static final int STATUS_UPDATE = 7;

    private static final String TAG = ScheduledJobService.class.getSimpleName();


    public static void scheduleStatusUpdate(Context context)
    {
        long interval = AlarmManager.INTERVAL_FIFTEEN_MINUTES;

        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);

        JobInfo job = new JobInfo.Builder(STATUS_UPDATE, new ComponentName(context.getPackageName(), ScheduledJobService.class.getName()))
                .setPersisted(true)
                .setPeriodic(interval)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .build();

        try
        {
            jobScheduler.schedule(job);
        }catch (Exception e)
        {
            ConsoleLog.e(e);
        }
    }

    @Override
    public boolean onStartJob(JobParameters jobParameters)
    {
        ConsoleLog.i(TAG, "start job called");
        if(jobParameters != null) {
            if (jobParameters.getJobId() == STATUS_UPDATE) {

                FirebaseStatusUploadModelEngine.getInstance().uploadSanity(this);
            }
        }

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}

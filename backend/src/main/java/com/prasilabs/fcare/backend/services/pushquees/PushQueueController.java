package com.prasilabs.fcare.backend.services.pushquees;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

/**
 * Created by prasi on 2/6/16.
 */
public class PushQueueController
{
    public static final String OPERATION_TYPE = "OPERATION_TYPE";
    public static final String SEND_WELCOME_EMAIL_OPER = "SEND_WELCOME_EMAIL";
    public static final String SEND_BEATEN_NOTIF_OPER = "SEND_BEATEN_NOTIF";
    public static final String SET_SRC_DEST_NAME_OPER = "SET_SRC_DEST_NAME";
    public static final String SET_TOP_SPEED_EVENT_OPER = "SET_TOP_SPEED_EVENT";
    public static final String SEND_LIKE_ON_FEED_OPER = "SEND_LIKE_ON_FEED";
    public static final String SEND_COMMENT_ON_FEED_OPER = "SEND_COMMENT_ON_FEED";
    public static final String UPDATE_USER_STATS_OPER = "UPDATE_USER_STATS";
    public static final String ADD_FACEBOOK_FRIENDS_OPER = "ADD_FACEBOOK_FRIENDS";
    public static final String FRIEND_REQ_NOTIF_OPER = "FRIEND_REQ_NOTIF";
    public static final String COMPARE_TOP_SPEED_NOTIFY_OPER = "COMPARE_TOP_SPEED_NOTIFY";

    public static final String ID_KEY = "ID";

    public static void sendWelcomeEmail(long userId)
    {
        Queue queue = QueueFactory.getDefaultQueue();
        TaskOptions taskOptions = getTaskOption();
        taskOptions.param(OPERATION_TYPE, SEND_WELCOME_EMAIL_OPER);
        taskOptions.param(ID_KEY, String.valueOf(userId));
        queue.add(taskOptions);
    }

    public static void analyzeForBeatenAndEventBotification(long cloudTripId)
    {
        Queue queue = QueueFactory.getDefaultQueue();
        TaskOptions taskOptions = getTaskOption();
        taskOptions.param(OPERATION_TYPE, SEND_BEATEN_NOTIF_OPER);
        taskOptions.param(ID_KEY, String.valueOf(cloudTripId));
        queue.add(taskOptions);
    }

    public static void setSourceDestName(long cloudTripId)
    {
        Queue queue = QueueFactory.getDefaultQueue();
        TaskOptions taskOptions = getTaskOption();
        taskOptions.param(OPERATION_TYPE, SET_SRC_DEST_NAME_OPER);
        taskOptions.param(ID_KEY, String.valueOf(cloudTripId));
        queue.add(taskOptions);
    }

    private static TaskOptions getTaskOption()
    {
        return TaskOptions.Builder.withUrl("/PushQ");
    }

    public static void addTopSpeedEventToSql(long tripId)
    {
        Queue queue = QueueFactory.getDefaultQueue();
        TaskOptions taskOptions = getTaskOption();
        taskOptions.param(OPERATION_TYPE, SET_TOP_SPEED_EVENT_OPER);
        taskOptions.param(ID_KEY, String.valueOf(tripId));
        queue.add(taskOptions);
    }

    public static void sendLikeOnFeedNotification(long userId, long feedId)
    {
        Queue queue = QueueFactory.getDefaultQueue();
        TaskOptions taskOptions = getTaskOption();
        taskOptions.param(OPERATION_TYPE, SEND_LIKE_ON_FEED_OPER);
        taskOptions.param("USER_ID", String.valueOf(userId));
        taskOptions.param("FEED_ID", String.valueOf(feedId));
        queue.add(taskOptions);
    }

    public static void sendCommentOnFeedNotification(long userId, long feedId, String commentMessage)
    {
        Queue queue = QueueFactory.getDefaultQueue();
        TaskOptions taskOptions = getTaskOption();
        taskOptions.param(OPERATION_TYPE, SEND_COMMENT_ON_FEED_OPER);
        taskOptions.param("USER_ID", String.valueOf(userId));
        taskOptions.param("FEED_ID", String.valueOf(feedId));
        taskOptions.param("COMMENT", commentMessage);
        queue.add(taskOptions);
    }

    public static void updateUserStats(long tripId)
    {
        Queue queue = QueueFactory.getDefaultQueue();
        TaskOptions taskOptions = getTaskOption();
        taskOptions.param(OPERATION_TYPE, UPDATE_USER_STATS_OPER);
        taskOptions.param("TRIP_ID", String.valueOf(tripId));

        queue.add(taskOptions);
    }

    public static void sendFriendRequestNotification(long myUserId, long otherUserId)
    {
        Queue queue = QueueFactory.getDefaultQueue();
        TaskOptions taskOptions = getTaskOption();
        taskOptions.param(OPERATION_TYPE, FRIEND_REQ_NOTIF_OPER);
        taskOptions.param("USER_ID1", String.valueOf(myUserId));
        taskOptions.param("USER_ID2", String.valueOf(otherUserId));

        queue.add(taskOptions);
    }

    public static void compareRankAndNotifiOthers(long tripId) {
        Queue queue = QueueFactory.getDefaultQueue();
        TaskOptions taskOptions = getTaskOption();
        taskOptions.param(OPERATION_TYPE, COMPARE_TOP_SPEED_NOTIFY_OPER);
        taskOptions.param(ID_KEY, String.valueOf(tripId));

        queue.add(taskOptions);
    }
}

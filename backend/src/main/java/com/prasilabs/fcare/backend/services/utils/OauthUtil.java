package com.prasilabs.fcare.backend.services.utils;

import com.prasilabs.fcare.backend.debug.ConsoleLog;
import com.prasilabs.utils.DataUtil;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;

/**
 * Created by prasi on 19/4/16.
 * admin util to check wether fcare object exist. admin or not
 */
public class OauthUtil
{
    public static boolean  checkUser(User user)
    {
        boolean isUserExist = false;

        try
        {
            isUserExist = checkAndThrow(user);
        } catch (OAuthRequestException e) {
            e.printStackTrace();
        }

        return isUserExist;
    }

    public static long getUserId(User user)
    {
        return DataUtil.stringToLong(user.getUserId());
    }

    public static boolean checkAndThrow(User user) throws OAuthRequestException
    {
        boolean isUserExist;
        if(user == null || user.getUserId() == null)
        {
            OAuthRequestException oauthException = new OAuthRequestException("oauth fcare is not found.");
            ConsoleLog.e(oauthException);
            throw oauthException;
        }
        else
        {
            isUserExist = true;
        }

        return isUserExist;
    }
}

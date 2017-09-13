package com.prasilabs.fcare.backend.services.pushquees;

import com.prasilabs.fcare.backend.debug.ConsoleLog;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by prasi on 2/6/16.
 */
public class PushQueeServlet extends HttpServlet
{
    private static final String TAG = PushQueeServlet.class.getSimpleName();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        ConsoleLog.l(TAG, "post called");

        try
        {
            String operationType = req.getParameter(PushQueueController.OPERATION_TYPE);
            String sid = req.getParameter(PushQueueController.ID_KEY);

            ConsoleLog.i(TAG, "operation type is : " + operationType);

            if (operationType.equals(PushQueueController.SEND_WELCOME_EMAIL_OPER))
            {

            }
            else
            {
                ConsoleLog.w(TAG, "illegal call to pushQ");
            }
        }
        catch (Exception e)
        {
            ConsoleLog.e(e);
        }
    }
}

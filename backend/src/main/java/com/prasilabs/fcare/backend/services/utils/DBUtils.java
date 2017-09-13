package com.prasilabs.fcare.backend.services.utils;


import com.prasilabs.fcare.backend.debug.ConsoleLog;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by prasi on 30/1/17.
 */

public class DBUtils
{
    public static boolean Close(ResultSet resultSet)
    {
        boolean status = false;

        try
        {
            resultSet.close();
            status = true;
        }
        catch (Exception e)
        {
            ConsoleLog.e(e);
        }

        return status;
    }

    public static boolean Close(Connection connection)
    {
        boolean status = false;

        try
        {
            connection.close();
            status = true;
        }
        catch (Exception e)
        {
            ConsoleLog.e(e);
        }

        return status;
    }

    public static boolean Close(Statement statement)
    {
        boolean status = false;

        try
        {
            statement.close();
            status = true;
        }
        catch (Exception e)
        {
            ConsoleLog.e(e);
        }

        return status;
    }

    public static boolean Close(PreparedStatement preparedStatement)
    {
        boolean status = false;

        try
        {
            preparedStatement.close();
            status = true;
        }
        catch (Exception e)
        {
            ConsoleLog.e(e);
        }

        return status;
    }

    public static boolean Close(CallableStatement callableStatement)
    {
        boolean status = false;

        try
        {
            callableStatement.close();
            status = true;
        }
        catch (Exception e)
        {
            ConsoleLog.e(e);
        }

        return status;
    }
}

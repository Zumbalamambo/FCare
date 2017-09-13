package com.prasilabs.fcare.backend.core;

import com.prasilabs.fcare.backend.debug.ConsoleLog;
import com.prasilabs.fcare.backend.debug.PerformanceLogger;
import com.prasilabs.fcare.backend.services.utils.DBUtils;
import com.google.appengine.api.memcache.ErrorHandlers;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by prasi on 11/3/16.
 * Core logic engine. every one should extend this guy
 */
public abstract class CoreLogicEngine
{
    private static final String TAG = CoreLogicEngine.class.getSimpleName();

    //should not be 0. 0  is default. default key not in the list

    private static MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();

    private static Connection connection;

    protected static <T> List<T> getListDataFromCache(String cacheKey)
    {
        ConsoleLog.i(TAG, "Getting data from cache : key : " + cacheKey);

        syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
        byte[] cacheValue = (byte[]) syncCache.get(cacheKey);

        if(cacheValue != null)
        {
            return getListFromByteArray(cacheValue);
        }
        else
        {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    protected static <T> T getDataFromCache(String cacheKey)
    {
        ConsoleLog.s(TAG, "Getting data from cache : key : " + cacheKey);

        syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
        byte[] cacheValue = (byte[]) syncCache.get(cacheKey);

        if(cacheValue != null)
        {
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(cacheValue));
                Object data = objectInputStream.readObject();
                return (T) data;
            }
            catch (Exception e)
            {
                if(clearCache(cacheKey))
                {
                    ConsoleLog.w(TAG, "exception: cleared cache");
                }
                else
                {
                    ConsoleLog.s(TAG, "exception class cast unable to clear cache also");
                }
                ConsoleLog.e(e);
            }
            return null;
        }
        else
        {
            return null;
        }
    }

    protected static boolean clearCache(int parentID)
    {
        boolean isCleared = false;

        try
        {
            /*List<ECacheKeyValue> cacheKeyList = OfyService.ofy().load().type(ECacheKeyValue.class).filter("parentKey", parentID).list();

            for (ECacheKeyValue cacheKey : cacheKeyList) {
                syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
                if (syncCache.contains(cacheKey.getCacheKey())) {
                    syncCache.delete(cacheKey.getCacheKey());
                }
            }

            OfyService.ofy().delete().entities(cacheKeyList);*/

            isCleared = true;
        }catch (Exception e)
        {
            ConsoleLog.e(e);
            isCleared = false;
        }

        return isCleared;
    }

    protected synchronized static boolean clearCache(String key)
    {
        boolean isSuccess = false;
        try
        {
            syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
            if (syncCache.contains(key))
            {
                isSuccess = syncCache.delete(key);

                /*ECacheKeyValue cacheKey = OfyService.ofy().load().type(ECacheKeyValue.class).filter("cacheKey", key).first().now();
                if (cacheKey != null)
                {
                    OfyService.ofy().delete().type(ECacheKeyValue.class).id(cacheKey.getId()).now();
                }*/
            }
        }
        catch (Exception e)
        {
            ConsoleLog.e(e);
            isSuccess = false;
        }

        return isSuccess;
    }

    protected synchronized static <T> void storeListToCache(String cacheKey, List<T> list)
    {
        storeListToCache(cacheKey, list, 0);
    }

    protected synchronized static <T> void storeListToCache(String cacheKey, List<T> list, int parentKey)
    {
        try
        {
            ConsoleLog.s(TAG, "storing data to cache : key : " + cacheKey + " list size is : " + list.size());
            syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
            syncCache.put(cacheKey, getByteArrayFromList(list));

            if(parentKey != 0)
            {
                /*ECacheKeyValue existCacheKeyVal = OfyService.ofy().load().type(ECacheKeyValue.class).filter(ECacheKeyValue.PARENT_KEY_STR, parentKey).filter(ECacheKeyValue.CACHE_KEY_STR, cacheKey).first().now();

                ECacheKeyValue cacheKeyObj = new ECacheKeyValue();
                if(existCacheKeyVal != null)
                {
                    cacheKeyObj.setId(existCacheKeyVal.getId());
                    cacheKeyObj.setModified(new Date(System.currentTimeMillis()));
                }
                else
                {
                    cacheKeyObj.setCreated(new Date(System.currentTimeMillis()));
                }
                cacheKeyObj.setParentId(parentKey);
                cacheKeyObj.setCacheKey(cacheKey);
                OfyService.ofy().save().entity(cacheKeyObj).now();*/

            }
        }
        catch (Exception e)
        {
            ConsoleLog.e(e);
        }

    }

    protected synchronized static <T> void storeToCache(String cacheKey, T data)
    {
        storeToCache(cacheKey, data, 0);
    }

    protected synchronized static <T> void storeToCache(String cacheKey, T data, int parentKey)
    {
        try
        {
            ConsoleLog.s(TAG, "storing data to cache : key : " + cacheKey);
            syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
            syncCache.put(cacheKey, getByteArrayFromData(data));

            if(parentKey != 0)
            {
                /*ECacheKeyValue existCacheKeyVal = OfyService.ofy().load().type(ECacheKeyValue.class).filter(ECacheKeyValue.PARENT_KEY_STR, parentKey).filter(ECacheKeyValue.CACHE_KEY_STR, cacheKey).first().now();

                ECacheKeyValue cacheKeyObj = new ECacheKeyValue();
                if(existCacheKeyVal != null)
                {
                    cacheKeyObj.setId(existCacheKeyVal.getId());
                    cacheKeyObj.setModified(new Date(System.currentTimeMillis()));
                }
                else
                {
                    cacheKeyObj.setCreated(new Date(System.currentTimeMillis()));
                }
                cacheKeyObj.setParentId(parentKey);
                cacheKeyObj.setCacheKey(cacheKey);
                OfyService.ofy().save().entity(cacheKeyObj).now();*/

            }
        }catch (Exception e)
        {
            ConsoleLog.e(e);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> List<T> getListFromByteArray(byte[] listBytes)
    {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new ByteArrayInputStream(listBytes));

            Object data = ois.readObject();

            List<T> list = null;
            if(data instanceof List)
            {
                list = (List<T>) data;
            }

            ois.close();
            return  list;
        }
        catch (Exception e)
        {
            ConsoleLog.e(e);
        }
        return null;
    }

    private static <T> byte[] getByteArrayFromList(List<T> list)
    {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(list);//mArrayList is the array to convert
            return bos.toByteArray();
        }
        catch (IOException e)
        {
            ConsoleLog.e(e);
        }
        return  null;
    }

    private static <T> byte[] getByteArrayFromData(T data)
    {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(data);
            return bos.toByteArray();
        }
        catch (IOException e)
        {
            ConsoleLog.e(e);
        }
        return  null;
    }

    private static final String DBConnectionDev = "jdbc:google:mysql://ff-location:dev/dev";
    private static final String DBConnectionProd = "jdbc:google:mysql://foodie-dev:pokerbuddiesdb/foodie_prod_db";

    private Statement getStatement()
    {
        Statement statement = null;
        Connection connection = getDBConnection();

        if(connection != null)
        {
            try
            {
                statement = connection.createStatement();
            }
            catch (SQLException e)
            {
                ConsoleLog.e(e);
            }
        }

        return statement;
    }

    protected ResultSet executeQuery(String sql)
    {
        ResultSet resultSet = null;
        Statement statement = getStatement();

        if(statement != null)
        {
            try
            {
                resultSet = statement.executeQuery(sql);
            }
            catch (SQLException e)
            {
                ConsoleLog.e(e);
            }
        }

        return resultSet;
    }

    protected int insertOrUpdateOrDeleteQuery(String sql)
    {
        int result = 0;
        Statement statement = getStatement();

        if(statement != null)
        {
            try
            {
                result = statement.executeUpdate(sql);
            }
            catch (SQLException e)
            {
                ConsoleLog.e(e);
            }
        }

        return result;
    }

    private static Connection getDBConnection()
    {
        try {

            if(connection == null ||  connection.isClosed() || !connection.isValid(0))
            {
                Class.forName("com.mysql.jdbc.Driver");

                if(CoreController.isDebug) {
                    return DriverManager.getConnection(DBConnectionDev, "root", "prasi123");
                }
                else
                {
                    return DriverManager.getConnection(DBConnectionProd, "root", "prasi123");
                }
            }
        }
        catch (Exception e)
        {
            ConsoleLog.e(e);
        }
        return connection;
    }

    protected <T> T dbOperation(DbOperationCallBack dbOperationCallBack)
    {
        T lastInsertKey = null;

        String sqlQuery = dbOperationCallBack.setSql();

        PreparedStatement statement = null;

        long startTime = System.currentTimeMillis();

        try
        {

            connection = getDBConnection();

            if(connection != null)
            {
                statement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);

                dbOperationCallBack.setData(statement);

                int val = statement.executeUpdate();

                ResultSet rs = statement.getGeneratedKeys();

                lastInsertKey = dbOperationCallBack.getResultKey(rs, val);
            }
            else
            {
                ConsoleLog.w(TAG, "connection is null");
            }
        }
        catch (Exception e)
        {
            ConsoleLog.e(e);
        }
        finally
        {
            DBUtils.Close(statement);
            //DBUtils.Close(connection);

            PerformanceLogger.logTimeTaken("sql insert", startTime, System.currentTimeMillis());
        }

        return lastInsertKey;
    }

    protected  <T> T dbQueryOpearation(DbQueryCallBack dbQueryCallBack)
    {
        T t = null;
        String sqlQuery = dbQueryCallBack.setSql();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        long startTime = System.currentTimeMillis();

        try
        {
            connection = getDBConnection();
            if(connection != null)
            {
                statement = connection.prepareStatement(sqlQuery);
                dbQueryCallBack.setData(statement);
                resultSet = statement.executeQuery();
                t = dbQueryCallBack.getResulSet(resultSet);
            }
            else
            {
                ConsoleLog.w(TAG, "connection is null");
            }
        }
        catch (Exception e)
        {
            ConsoleLog.e(e);
        }
        finally
        {
            DBUtils.Close(resultSet);
            DBUtils.Close(statement);
            //DBUtils.Close(connection);

            PerformanceLogger.logTimeTaken("sql call", startTime, System.currentTimeMillis());
        }

        return t;
    }


    protected  <T> T dbProcedureOpearation(DbCallOperCallBack dbCallOperCallBack)
    {
        T t = null;
        long startTime = System.currentTimeMillis();

        CallableStatement callableStatement = null;

        try
        {
            connection = getDBConnection();
            if(connection != null)
            {
                callableStatement = connection.prepareCall(dbCallOperCallBack.open());
                t = dbCallOperCallBack.run(callableStatement);
            }
            else
            {
                ConsoleLog.w(TAG,"connection is null");
            }
        }
        catch (Exception e)
        {
            ConsoleLog.e(e);

            t = dbCallOperCallBack.failed(e.getMessage());
        }
        finally
        {
            DBUtils.Close(callableStatement);
            //DBUtils.Close(connection);

            PerformanceLogger.logTimeTaken("sql call", startTime, System.currentTimeMillis());
        }

        return t;
    }

    public interface DbCallOperCallBack
    {
        String open() throws Exception;

        <T> T run(CallableStatement callableStatement) throws Exception;

        <T> T failed(String message);
    }

    public interface DbQueryCallBack
    {
        String setSql();

        void setData(PreparedStatement statement) throws Exception;

        <T> T getResulSet(ResultSet resultSet) throws Exception;

        <T> T failed(String message);
    }

    public interface DbOperationCallBack
    {
        String setSql();

        void setData(PreparedStatement preparedStatement) throws Exception;

        <T> T getResultKey(ResultSet resultSet, int resultInt) throws Exception;
    }
}

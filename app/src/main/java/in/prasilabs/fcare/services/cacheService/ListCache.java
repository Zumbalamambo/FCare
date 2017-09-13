package in.prasilabs.fcare.services.cacheService;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;

import com.prasilabs.CacheData;
import com.prasilabs.droidwizardlib.utils.JsonUtil;
import com.prasilabs.droidwizardlib.utils.LocalPreference;
import com.prasilabs.pojos.responsePojos.UserDataVO;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Contus team on 6/9/17.
 */

public class ListCache<T extends CacheData> {

    private Map<String, T> mCacheMap = new ArrayMap<>();

    @NonNull
    public List<T> getCacheList() {
        return new ArrayList<>(mCacheMap.values());
    }

    @NonNull
    public List<T> getCacheList(Context context, String key) {

        if(!mCacheMap.isEmpty()) {
            return new ArrayList<>(mCacheMap.values());
        } else {
            String cacheData = LocalPreference.getUserDataFromShared(context, key, null);

            if(!TextUtils.isEmpty(cacheData)) {
                Class<ListData<T>> cls = generify(List.class);
                ListData<T> listData = JsonUtil.getObjectFromJson(cacheData, cls);

                setCacheList(listData.gettList());

                return listData.gettList();
            }
        }

        return new ArrayList<>();
    }

    public void setCacheList(List<T> cacheList) {
        mCacheMap.clear();
        if(cacheList != null) {
            for(T t : cacheList) {
                mCacheMap.put(t.getKey(), t);
            }
        }
    }

    public void updateCacheList(T t) {
        if(t != null) {
            mCacheMap.put(t.getKey(), t);
        }
    }

    public void updateCacheList(List<T> cacheList) {
        if(cacheList != null) {
            for(T t : cacheList) {
                mCacheMap.put(t.getKey(), t);
            }
        }
    }

    public void removeFromCache(List<T> cacheList) {
        if(cacheList != null) {
            for(T t : cacheList) {
                mCacheMap.remove(t.getKey());
            }
        }
    }

    public void removeFromCache(T t) {
        if(t != null) {
            mCacheMap.remove(t.getKey());
        }
    }

    public boolean isCacheDataPresent() {
        return !mCacheMap.isEmpty();
    }

    public void commit(Context context, String key) {
        ListData<T> listData = new ListData<>();
        listData.settList(getCacheList());

        JSONObject jsonObject = JsonUtil.getJsonFromClass(listData);

        LocalPreference.saveUserDataInShared(context, key, String.valueOf(jsonObject));
    }

    public boolean isCacheDataPresent(Context context, String key) {

        if(!mCacheMap.isEmpty()) {
            return false;
        } else {
            return !TextUtils.isEmpty(LocalPreference.getUserDataFromShared(context, key, null));
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> Class<T> generify(Class<?> cls) {
        return (Class<T>)cls;
    }
}

package in.prasilabs.fcare.services.cacheService;

import java.util.List;

/**
 * Created by Contus team on 7/9/17.
 */

public class ListData<T> {

    private List<T> tList;

    public List<T> gettList() {
        return tList;
    }

    public void settList(List<T> tList) {
        this.tList = tList;
    }
}

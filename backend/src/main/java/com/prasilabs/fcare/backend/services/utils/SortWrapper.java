package com.prasilabs.fcare.backend.services.utils;

import static com.google.apphosting.datastore.DatastoreV4.PropertyOrder.Direction;

public class SortWrapper
{
    public static String getSorting(String sortKey, Direction sortOrder)
    {
        if(sortKey != null)
        {
            String sort = "";
            if(sortOrder == Direction.DESCENDING)
            {
                sort += "-";
            }

            sort += sortKey;

            return sort;
        }

        return null;
    }
}
package in.prasilabs.fcare.services.api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.prasilabs.pojos.responsePojos.UserDataVO;
import com.prasilabs.pojos.responsePojos.UserRelationShipVO;
import com.prasilabs.pojos.responsePojos.UserRelationShipVOCollection;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Contus team on 29/8/17.
 */

public interface UserRelationRequest {

    @GET("userApi/v1/userrelationshipvocollection")
    Call<UserRelationShipVOCollection> getRelations();

    @POST("userApi/v1/addUserRelation/{number}")
    Call<UserRelationShipVO> addRelation(@NonNull @Path("number") String number, @Nullable @Query("name") String name);

    @PUT("userApi/v1/confirmUserRelation/{user_id}/{is_accept}")
    Call<UserRelationShipVO> acceptRelation(@Path("user_id") long userId, @Path("is_accept") boolean isAccept);
}

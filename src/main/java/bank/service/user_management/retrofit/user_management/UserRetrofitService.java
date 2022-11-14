package bank.service.user_management.retrofit.user_management;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import java.util.List;

public interface UserRetrofitService {
    @POST("fetch_all_users")
    Call<List<UserRetrofitModel>> fetchAllUsers();


    @FormUrlEncoded
    @POST("fetch_a_user_roles")
    Call<List<UserRolesRetrofitModel>> fetchAUserRolesByUserId(@Field("userId") int userId);

    @FormUrlEncoded
    @POST("fetch_a_user_specific_role")
    Call<UserRolesRetrofitModel> fetchAUserSpecificRolesByUserIdAndRoleId(@Field("userId") int userId, @Field("roleId") int roleId);
}

package com.rajah.retroapp.interfaces;

import com.rajah.retroapp.Constant;
import com.rajah.retroapp.models.Album;
import com.rajah.retroapp.models.Comment;
import com.rajah.retroapp.models.Post;
import com.rajah.retroapp.models.Todo;
import com.rajah.retroapp.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Rajah on 8/25/2017.
 */

public interface ApiInterface {

    @GET(Constant.ALLUSERSREQUEST)
    Call<List<User>> getAllUser();

//    getting Users by id
    @GET(Constant.ALLUSERSREQUEST)
    Call<User> getUserById(@Query(Constant.QUERY_ID) String user_id);

//    getting post by id
    @GET(Constant.ALLPOSTSREQUEST)
    Call<List<Post>> getPostById(@Query(Constant.USER_ID) String user_id);

//    Getting Comment by Post_id
    @GET(Constant.ALLCOMMENTSREQUEST)
   Call<List<Comment>> getCommentById(@Query(Constant.POST_ID) String post_id);

//    getting to do by Id
    @GET(Constant.ALLTODOREQUEST)
    Call<List<Todo>> getTodoById(@Query(Constant.USER_ID) String user_id);

//    getting albumby Id
    @GET(Constant.ALLALBUMREQUEST)
    Call<List<Album>> getAlbumById(@Query(Constant.USER_ID) String user_id);
}

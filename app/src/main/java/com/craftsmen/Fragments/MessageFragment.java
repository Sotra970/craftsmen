package com.craftsmen.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.craftsmen.Adapter.Messages_Adapter;
import com.craftsmen.AppManger.Config;
import com.craftsmen.Models.Comements_model;
import com.craftsmen.Models.Message_model;
import com.craftsmen.R;

import java.util.ArrayList;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.craftsmen.AppManger.AppController;
import com.craftsmen.Models.User_model;
import com.craftsmen.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2/22/2017.
 */

public class MessageFragment extends Fragment {

    RecyclerView recyclerView ;
    public Messages_Adapter adapter;
    ArrayList<Message_model> item_model=new ArrayList<>();
    View layout , layoutmsg;
    String url = "http://192.168.56.1/craftsmen/login.php" ;
    TextView textView ;
    View noMessages ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(layout==null) {
            layout = inflater.inflate(R.layout.fragment_message, container, false);
            recyclerView = (RecyclerView) layout.findViewById(R.id.msg_view);
            adapter = new Messages_Adapter(getActivity(), item_model);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            noMessages = layout.findViewById(R.id.no_mess);
        }
        geMessages();
        return layout;
    }
    void geMessages(){
        StringRequest user_req = new StringRequest(Request.Method.POST, Config.BASE_URL+"outbox.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("mess response" , response) ;
                try {
                    noMessages.setVisibility(View.GONE);

                    if (response.equals("Empty")){
                    }else {
                        noMessages.setVisibility(View.GONE);
                        JSONArray fedarr = new JSONArray(response) ;
                        for (int i= (0); i<fedarr.length() ; i++){
                            noMessages.setVisibility(View.GONE);
                            JSONObject temp = fedarr.getJSONObject(i) ;

                            Message_model message_model = new Message_model() ;
                            message_model.setMessage(temp.getString("message"));
                            message_model.setDate(temp.getString("date"));

                            User_model user_model = new User_model() ;
                            user_model.setU_Id(temp.getString("sender"));
                            user_model.setU_Pic(temp.getString("user_image"));
                            user_model.setU_name(temp.getString("name"));
                            user_model.setU_p_num(temp.getString("phone"));
                            user_model.setU_gender(temp.getString("gender"));
                            user_model.setU_city(temp.getString("city"));
                            user_model.setLocation(temp.getString("location"));
                            user_model.setU_address(temp.getString("address"));
                            user_model.setU_service(temp.getString("job"));
                            message_model.setUser_model(user_model);


                            item_model.add(message_model);
                        }

                        adapter.notifyDataSetChanged();

                    }
                }catch (Exception e) {
                    Log.e("login response  err" , e.toString()) ;
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("login err" , error.toString()) ;
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String , String> parmas = new HashMap<>() ;
                parmas.put("user_id" , AppController.getInstance().getPrefManager().getUser().getU_Id()) ;
                Log.e("params" , parmas.toString());
                return parmas;
            }

        };

        AppController.getInstance().getRequestQueue().add(user_req) ;

    }
}

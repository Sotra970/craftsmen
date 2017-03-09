package com.craftsmen.AppManger;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.craftsmen.Models.User_model;



public class MyPreferenceManager {

    private String TAG = MyPreferenceManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "craftsmen";

    // All Shared Preferences Keys
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_USER_EMAIL = "user_email";
    private static final String KEY_USER_PHONE = "phone";
    private static final String KEY_USER_TYPE = "user_type";
    private static final String KEY_USER_City = "city";
    private static final String KEY_USER_loc = "location";
    private static final String KEY_USER_adress = "adress";
    private static final String KEY_USER_service_ = "service";
    private static final String KEY_USER_pic = "pic";
    private static final String KEY_USER_gender = "gender";


    // Constructor
    public MyPreferenceManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void storeUser(User_model user) {
        editor.clear();
        editor.commit();
        editor.putString(KEY_USER_ID, user.getU_Id());
        editor.putString(KEY_USER_NAME, user.getU_name());
        editor.putString(KEY_USER_TYPE , user.getU_type());
        editor.putString(KEY_USER_PHONE , user.getU_p_num());
        editor.putString(KEY_USER_EMAIL , user.getU_email());
        editor.putString(KEY_USER_City , user.getU_city());
        editor.putString(KEY_USER_loc , user.getLocation());
        editor.putString(KEY_USER_adress , user.getU_address());
        editor.putString(KEY_USER_service_ , user.getU_service());
        editor.putString(KEY_USER_pic , user.getU_Pic());
        editor.putString(KEY_USER_gender , user.getU_gender());
        editor.commit();


        Log.e(TAG, "User is stored in shared preferences. " + user.getU_name() + " ," + user.getU_city() );
    }

    public User_model getUser() {
        if (pref.getString(KEY_USER_ID, null) != null) {
            String id, name,type,phone ,email, gender , adress , location , service , pic ,city ;
            id = pref.getString(KEY_USER_ID, null);
            name = pref.getString(KEY_USER_NAME, null);
            phone = pref.getString(KEY_USER_PHONE, null);
            type = pref.getString(KEY_USER_TYPE, null);
            email = pref.getString(KEY_USER_EMAIL, null);
            city = pref.getString(KEY_USER_City, null);
            location = pref.getString(KEY_USER_loc, null);
            adress = pref.getString(KEY_USER_adress, null);
            service = pref.getString(KEY_USER_service_, null);
            pic = pref.getString(KEY_USER_pic, null);
            gender = pref.getString(KEY_USER_gender, null);


            User_model user_model = new User_model() ;
            user_model.setU_Id(id);
            user_model.setU_name(name);
            user_model.setU_p_num(phone);
            user_model.setU_type(type) ;
            user_model.setU_service(service); ;
            user_model.setU_email(email); ;
            user_model.setU_address(adress); ;
            user_model.setLocation(location); ;
            user_model.setU_city(city); ;
            user_model.setU_Pic(pic); ;
            user_model.setU_gender(gender); ;
            return user_model;
        }
        return null;
    }

    public void clear() {
        editor.clear();
        editor.commit();
    }
}

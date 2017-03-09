package com.craftsmen.Fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.craftsmen.AppManger.AndroidMultiPartEntity;
import com.craftsmen.AppManger.AppController;
import com.craftsmen.AppManger.Config;
import com.craftsmen.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2/22/2017.
 */

public class DashboardFragment_User extends Fragment {
    View carpenter ,mechanic,plumber,electricity,n2ash,tblit;
    View layout_container  ;
    View dialog , add_img ;
    View send , cancel;
    private String uploaded_img_name="";
    TextView txtPercentage;
    ProgressBar progressBar ;
    View progressBarView ;
    TextInputLayout desc_layout ;
    EditText description ;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout_container = inflater.inflate(R.layout.fragment_dashboard_user, container, false);
        if (savedInstanceState == null) no_storage_permission();


        desc_layout = (TextInputLayout) layout_container.findViewById(R.id.desc_layout);
        description = (EditText) layout_container.findViewById(R.id.desc_input);

        txtPercentage = (TextView) layout_container.findViewById(R.id.txtPercentage);
        progressBar = (ProgressBar) layout_container.findViewById(R.id.progressBar);
        progressBarView = layout_container.findViewById(R.id.progressBarView);


        carpenter =  layout_container.findViewById(R.id.user_cardview_carpenter);
        mechanic = layout_container.findViewById(R.id.user_cardview_mech);
        plumber = layout_container.findViewById(R.id.user_cardview_plumber);
        electricity = layout_container.findViewById(R.id.user_cardview_electricity);
        n2ash = layout_container.findViewById(R.id.user_cardview_n2ash);
        tblit = layout_container.findViewById(R.id.user_cardview_tblit);
        dialog = layout_container.findViewById(R.id.layout_dashboard);
        send = layout_container.findViewById(R.id.send_btn);
        cancel = layout_container.findViewById(R.id.cancel_btn);

        add_img = layout_container.findViewById(R.id.add_img);


        carpenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opendialog("نجارة");

            }
        });

        mechanic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opendialog("ميكانيكا");

            }
        });

        plumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opendialog("سباكة");

            }
        });

        electricity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opendialog("كهرباء");

            }
        });

        n2ash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opendialog("نقاشة");

            }
        });

        tblit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opendialog("تبليط");

            }
        });
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_img();
            }
        });

        return layout_container;

    }
    public void opendialog(final String type){
        dialog.setVisibility(View.VISIBLE);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    add_request(type);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setVisibility(View.GONE);
            }
        });
    }



    public class ResizeImage extends AsyncTask<String, Integer, String> {
        protected String  filePath,fileName ;
        long totalSize = 0;
        Boolean ok = false ;
        Bitmap resized_bitmap ;


        @Override
        protected void onPreExecute() {
            // setting progress bar to zero
            progressBar.setProgress(0);
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Making progress bar visible
            progressBar.setVisibility(View.VISIBLE);
            progressBarView.setVisibility(View.VISIBLE);
            // updating progress bar value
            progressBar.setProgress(progress[0]);

            // updating percentage value
            txtPercentage.setText(String.valueOf(progress[0]) + "%");
        }

        // Decode image in background.
        @Override
        protected String doInBackground(String... params) {
            filePath = params[0];
            File uploadFile = null ;
            String response = null;
            try {
                resized_bitmap =  decodeSampledBitmapFromPath(filePath, 1080, 1920) ;

                uploadFile = bitmaptofile(resized_bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }

            if (uploadFile == null){
                Log.e("category","file"+"null");
                uploadFile = new File(filePath);
            }
            fileName = uploadFile.getName();
            response = uploadFile(uploadFile);


            return    response ;
        }

        // Once complete, see if ImageView is still around and set bitmap.
        @Override
        protected void onPostExecute(String result) {
            showAlert(result);
            progressBarView.setVisibility(View.GONE);
        }

        protected File  bitmaptofile(Bitmap bitmap) throws IOException {
            File outputDir = getActivity().getCacheDir(); // context being the Activity pointer
            File outputFile = File.createTempFile(currentDateFormat(),".jpg" , outputDir);
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(outputFile);

                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return outputFile;
        }
        /**
         * Method to show alert dialog
         * */
        protected void showAlert(String message) {
            if (ok){
                String filePathName = new File(filePath).getName();
                Log.e("category","filename  "+fileName +"file path   " + filePathName);
                uploaded_img_name = fileName;
                validateImg();
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(message).setTitle("Response from Servers")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // do nothing
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
            alert.getWindow().getDecorView().setBackgroundColor(ContextCompat.getColor(getContext(),R.color.colorPrimary));



        }

        @SuppressWarnings("deprecation")
        protected String uploadFile(File sourceFile) {
            String responseString = null;

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(Config.BASE_URL+"upload_img.php");
            Log.e("category","url"+httppost.getURI());
            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                        new AndroidMultiPartEntity.ProgressListener() {

                            @Override
                            public void transferred(long num) {
                                Log.e("transferd" , num +"");
                                int precentage = (int) ((num / (float) totalSize) * 100);
                                if (precentage <=99){
                                    publishProgress(precentage);
                                }else publishProgress(99);
                            }
                        });


                // Adding file data to http body
                entity.addPart("image", new FileBody(sourceFile));


                totalSize = entity.getContentLength();
                httppost.setEntity(entity);

                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                    ok=true ;
                } else {
                    responseString = "Error occurred! Http Status Code: "
                            + statusCode;
                }

            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }

            return responseString;

        }

    }

    public static String currentDateFormat(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        String  currentTimeStamp = dateFormat.format(new Date());
        return currentTimeStamp;
    }
    public static Bitmap decodeSampledBitmapFromPath( String FilePath,
                                                      int reqWidth, int reqHeight) {


        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile( FilePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile( FilePath, options);
    }
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        Log.e("sapleSize" , inSampleSize + "");
        return inSampleSize;
    }
    protected boolean validateImg() {
        if ( uploaded_img_name.equals("null") || uploaded_img_name.isEmpty() ){
            layout_container.findViewById(R.id.img_err).setVisibility(View.VISIBLE);
            return false ;
        }else {
            layout_container.findViewById(R.id.img_err).setVisibility(View.GONE);
        }
        return true ;
    }

    void get_img(){
        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, Config.RESULT_LOAD_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        /// select from gallery section
        if (requestCode == Config.RESULT_LOAD_IMAGE && resultCode == getActivity().RESULT_OK && data!=null){

            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Log.e("Uri",selectedImage + "");
            Log.e("filePathColumn",MediaStore.Images.Media.DATA + "");

            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);

            cursor.close();

            Log.e("picturePath",picturePath + "");
            new ResizeImage().execute(picturePath);

        }
    }


    void add_request (final String type) {

        if (!validate_description())
            return;

        if (!validateImg()) {
            return;
        }
        layout_container.findViewById(R.id.loadingSpinner).setVisibility(View.VISIBLE);

        StringRequest requesturl = new StringRequest(Request.Method.POST, Config.BASE_URL+"add_request.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                layout_container.findViewById(R.id.loadingSpinner).setVisibility(View.GONE);
                dialog.setVisibility(View.GONE);

                Log.e("login response  " , response.toString()) ;


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("login response  err" , error.toString()) ;
            }
        }
        ) {

            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", AppController.getInstance().getPrefManager().getUser().getU_Id());
                params.put("service_type", type);
                params.put("description", description.getText().toString().trim());
                params.put("city", AppController.getInstance().getPrefManager().getUser().getU_city());
                params.put("location", AppController.getInstance().getPrefManager().getUser().getLocation());
                params.put("image",uploaded_img_name);
                Log.e("request prams  err" , params.toString()) ;
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(requesturl);

    }

    boolean validate_description (){
        if (description.getText().toString().trim().equals(null) || description.getText().toString().trim().isEmpty()){
            desc_layout.setError("يجب ادخال المدينة");
            return false ;
        }
        desc_layout.setErrorEnabled(false);
        return true ;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case Config.MY_PERMISSIONS_REQUEST_STORAGE: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    //  no_gps_permition();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getActivity(), "Permission needed to run your app", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    private void no_storage_permission() {
        // Here, thisActivity is the current activity
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {



            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Config.MY_PERMISSIONS_REQUEST_STORAGE);


        }
    }


}

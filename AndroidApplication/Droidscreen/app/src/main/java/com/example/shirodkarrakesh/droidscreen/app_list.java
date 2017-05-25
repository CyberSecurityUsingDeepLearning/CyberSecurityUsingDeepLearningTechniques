package com.example.shirodkarrakesh.droidscreen;

import android.Manifest;
import android.nfc.Tag;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;


import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.internal.http.multipart.MultipartEntity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

//package app.file_upload;


import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static android.R.attr.data;
import static android.R.attr.tag;
import static android.R.string.ok;
import static com.example.shirodkarrakesh.droidscreen.DatabaseHelper.CreatTable1;
import static com.example.shirodkarrakesh.droidscreen.DatabaseHelper.CreatTable2;
import static com.example.shirodkarrakesh.droidscreen.R.id.email;

public class app_list extends AppCompatActivity {


    String filePath;

    DatabaseHelper ScDb;

    String Result;

    String result="Application not Scanned";

    String file_name;

    String application_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ScDb = new DatabaseHelper(this);

        setContentView(R.layout.activity_app_list);

        ArrayList<String> arrayWord = new ArrayList<String>();


        final PackageManager pm = getPackageManager();

        //get a list of installed apps.
        List<ApplicationInfo> packages =  pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo packageInfo : packages) {

           if((packageInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0)
            {

                //it's a system app, not interested
            } else if ((packageInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0)
            {
                //Discard this one

                //in this case, it should be a user-installed app
            }
            else

            {
                arrayWord.add(pm.getApplicationLabel(packageInfo) +"\n"+ packageInfo.sourceDir);
                // arrayWord.add(packageInfo.sourceDir);

            }

            //arrayWord.add(packageInfo.sourceDir);

            //filePath=packageInfo.sourceDir; pm.getApplicationLabel(packageInfo) +"\n"+
            //"Installed package :" + packageInfo.packageName);
            //"Apk file path:" + packageInfo.sourceDir);
        }

        final ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayWord);
        ListView listView = (ListView) findViewById(R.id.activity_app_list);
        listView.setAdapter(itemsAdapter);



        //listener on listveiw
        listView.setOnItemClickListener (new ListView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                String FullInfo = itemsAdapter.getItem(position);

                filePath = FullInfo.substring(FullInfo.indexOf("\n")+1);

                application_name=FullInfo.substring(0, FullInfo.indexOf("\n"));

                Toast.makeText(getApplicationContext(), "sending file", Toast.LENGTH_LONG).show();


                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        File f  = new File(filePath);

                        String content_type  = getMimeType(f.getPath());

                        String file_path = f.getAbsolutePath();

                        file_name = file_path.substring(file_path.lastIndexOf("/")+1);

                        boolean is_db_app=false;


                        OkHttpClient client = new OkHttpClient.Builder()
                                .connectTimeout(30, TimeUnit.SECONDS)
                                .writeTimeout(30, TimeUnit.SECONDS)
                                .readTimeout(30, TimeUnit.SECONDS)
                                .build();


                        RequestBody file_body = RequestBody.create(MediaType.parse(content_type),f);

                        RequestBody request_body = new MultipartBody.Builder()
                                .setType(MultipartBody.FORM)
                                .addFormDataPart("type",content_type)
                                .addFormDataPart("sent_file",file_path.substring(file_path.lastIndexOf("/")+1), file_body)
                                .build();

                        Request request = new Request.Builder()
                                .url("http://130.65.159.84:8080/~msprj_security/send_file.php")
                                //.url("http://10.0.0.15/send_file.php")
                                .post(request_body)
                                .build();


                        try {

                            Response response = client.newCall(request).execute();

                            Result = response.body().string();

                            System.out.print("******************************\n");

                            System.out.println(Result);

                            System.out.print("******************************\n");

                            response.body().close();

                            //extract data to update in to table

                                if (Result.length() != 0) {

                                        int n = Result.indexOf(":");

                                        if (n != 0) {
                                            n = n + 2;

                                        }
                                        //System.out.print(Result.charAt(n));
                                        if (Result.charAt(n) == '0') {
                                            result = "Benign Application";
                                        } else if (Result.charAt(n) == '1'){
                                            result = "Malicious Application";
                                        }
                                        else {
                                            result = "Application not Scanned";
                                        }
                                }
                                else
                                        {
                                            result = "Application not Scanned";
                                        }


                            //System.out.println(result);
                            // check if user already has this application
                            try{

                                // getting the Password from database for the provided username

                                is_db_app = ScDb.getapp(application_name);

                                //System.out.print("applocation is_db_app!!\n");

                                //System.out.println(is_db_app);

                                /*if (is_db_app == true)
                                {

                                    //System.out.print("application already present!!\n");
                                }
                                else
                                {

                                    //System.out.print("application not present!!\n");
                                }*/


                            }
                            catch (Exception e)
                            {
                                System.out.println("Error while validating app name in to database\n" + e.getMessage());
                                e.printStackTrace();
                            }


                            //inserting or updating data to table
                            try{
                                if (!is_db_app) {
                                    //inserting values to scan table
                                    boolean ScanInsertResult = ScDb.ScanInsert(application_name, result, Result);

                                    if (ScanInsertResult == true) {

                                       // System.out.print("Data inserted for application result!!\n");
                                    } else {

                                        System.out.print("Error while inserting Data for application result!!\n");
                                    }
                                }
                                else
                                {
                                    boolean ScanUpdateResult = ScDb.ScanUpdate(application_name, result, Result);

                                    if (ScanUpdateResult == true) {

                                        //System.out.print("Data updated for application result!!\n");
                                    } else {

                                        System.out.print("Error while updating Data for application result!!\n");
                                    }
                                }


                            }
                            catch (Exception e)
                            {
                                System.out.println("Error inserting/updating in to database\n" + e.getMessage());
                                e.printStackTrace();
                            }


                            if(!response.isSuccessful()){

                                response.body().close();

                                throw new IOException("Error message while sending: "+response);

                            }


                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }


                    }
                });

                t.start();

                //end of file sending


                //to go to previous screen
                finish();

            }

            private String getMimeType(String path) {

                String extension = MimeTypeMap.getFileExtensionFromUrl(path);

                return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
                }


        });

            }


}

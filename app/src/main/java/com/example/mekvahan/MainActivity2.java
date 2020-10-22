package com.example.mekvahan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;




public class MainActivity2 extends AppCompatActivity {
        Button button;
        EditText phone ,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        button=findViewById(R.id.button2);
        phone=findViewById(R.id.ephone);
        password=findViewById(R.id.tpass);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    loginButton();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void loginButton() throws JSONException {
       if (phone.getText().toString().trim().length()!=10){
            phone.setError("Please Enter Valid Mobile Number");
       }else{
            callApi(phone.getText().toString(),password.getText().toString());
       }

    }
        public void callApi(String phone,String pass) throws JSONException {
            JSONObject addrObj = new JSONObject();
           addrObj.put("mobile",""+phone);
           addrObj.put("password",""+pass);
                String url = "https://mekvahan.com/api/android_intern_task";
                Log.e("json",""+addrObj);
            final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, addrObj, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Gson gson=new Gson();
                    PojoData pojoData=gson.fromJson(response.toString(),PojoData.class);
                    if(pojoData.getStatus()!=null){
                        Toast.makeText(MainActivity2.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity2.this,MainActivity3.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(MainActivity2.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity2.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
            RequestQueue queue= Volley.newRequestQueue(this);
            queue.add(request);

        }
}
package com.example.tejas.androidphpmysql;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextEmail, editTextUsername, editTextPassword;
    private Button buttonRegister;
    private ProgressDialog progressDialog;
    private TextView loginTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            return;
        }

        editTextEmail = (EditText) findViewById(R.id.edit_text_email);
        editTextPassword = (EditText) findViewById(R.id.edit_text_password);
        editTextUsername = (EditText) findViewById(R.id.edit_text_username);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        loginTextView = (TextView)findViewById(R.id.text_view_login);
        progressDialog = new ProgressDialog(this);

        buttonRegister.setOnClickListener(this);
        loginTextView.setOnClickListener(this);
    }

    private void registerUser() {
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        final String username = editTextUsername.getText().toString().trim();

        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getApplicationContext(), jsonObject.getString("error"), Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", password);
                params.put("email", email);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onClick(View view) {
        if(view == buttonRegister){
            registerUser();
        }
        if(view == loginTextView){
            startActivity(new Intent(this, LoginActivity.class));
        }
    }


}

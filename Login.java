package com.dosindonesia.www.dosindoreport;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final Button bLogin = (Button) findViewById(R.id.bLogin);

        final EditText etfromDB = (EditText) findViewById(R.id.etLevel1);


        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v)
            {
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");


                           if (success)
                            {

                                Toast.makeText(v.getContext(),"Login Sukses", Toast.LENGTH_SHORT).show();
                                String first_name = jsonResponse.getString("first_name");
                                String last_name = jsonResponse.getString("last_name");
                                String email = jsonResponse.getString("email");
                                String phone = jsonResponse.getString("phone");
                                String code = jsonResponse.getString("code");
                                String akses = jsonResponse.getString("akses");
                                etfromDB.setText(akses);

                                if (akses.equals("administrator"))
                                {
                                    Toast.makeText(v.getContext(),"Admin Login", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Login.this, Admin.class);
                                    intent.putExtra("first_name", first_name);
                                    intent.putExtra("last_name", last_name);
                                    intent.putExtra("email", email);
                                    intent.putExtra("phone", phone);
                                    intent.putExtra("code", code);
                                    intent.putExtra("akses", akses);
                                    intent.putExtra("username", username);
                                    etUsername.setText("");
                                    etPassword.setText("");
                                    Login.this.startActivity(intent);
                                }

                                else if(akses.equals("pc"))
                                {
                                    Toast.makeText(v.getContext(), "PC Login", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Login.this, MainActivity.class);
                                    intent.putExtra("first_name", first_name);
                                    intent.putExtra("last_name", last_name);
                                    intent.putExtra("email", email);
                                    intent.putExtra("phone", phone);
                                    intent.putExtra("code", code);
                                    intent.putExtra("akses", akses);
                                    intent.putExtra("username", username);
                                    etUsername.setText("");
                                    etPassword.setText("");
                                    Login.this.startActivity(intent);
                                }

                                else if(akses.equals("management"))
                                {
                                    Toast.makeText(v.getContext(), "Management Login", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Login.this, Mangement.class);
                                    intent.putExtra("first_name", first_name);
                                    intent.putExtra("last_name", last_name);
                                    intent.putExtra("email", email);
                                    intent.putExtra("phone", phone);
                                    intent.putExtra("code", code);
                                    intent.putExtra("akses", akses);
                                    intent.putExtra("username", username);
                                    etUsername.setText("");
                                    etPassword.setText("");
                                    Login.this.startActivity(intent);
                                }
                                else {Toast.makeText(v.getContext(),"eror Get level", Toast.LENGTH_SHORT).show();}

                            }


                            else
                            {

                                AlertDialog.Builder builder = new  AlertDialog.Builder(Login.this);
                                builder.setTitle("Login Failed")
                                        .setMessage("Cek User & Pwd")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                                etUsername.setText("");
                                etPassword.setText("");
                            }



                        } catch (JSONException e) {e.printStackTrace();}
                    }
                };
                LoginRequest loginRequest = new LoginRequest(username, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Login.this);
                queue.add(loginRequest);

            }
        });

    }
}

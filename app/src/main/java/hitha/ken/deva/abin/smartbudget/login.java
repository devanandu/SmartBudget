package hitha.ken.deva.abin.smartbudget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.AuthConfig;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsAuthButton;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.models.User;

import io.fabric.sdk.android.Fabric;

public class login extends AppCompatActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "z2pCqVB1v1GRk4SKHwKa16qtL";
    private static final String TWITTER_SECRET = "cq66zAjMZZS1NCDa7AVOZJymOLdUPP0OOWgiqzIdPwdGOby4rg";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Digits.Builder digitsBuilder = new Digits.Builder().withTheme(R.style.CustomDigitsTheme);

        mAuth = FirebaseAuth.getInstance();
        Fabric.with(this, new TwitterCore(authConfig), digitsBuilder.build());
        setContentView(R.layout.activity_login);

        DigitsAuthButton digitsButton = (DigitsAuthButton) findViewById(R.id.auth_button);
        digitsButton.setCallback(new AuthCallback() {
            @Override

            public void success(DigitsSession session, String phoneNumber) {
                // TODO: associate the session userID with your user model
                SharedPreferences mPreferences = getApplicationContext().getSharedPreferences("MyPref", 0);
                SharedPreferences.Editor editor = mPreferences.edit();
                editor.putString("loginid", phoneNumber);
                editor.commit();
                signin(phoneNumber);
                Intent i = new Intent(login.this,userDetails.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(i);
                Toast.makeText(getApplicationContext(), "Authentication successful for "
                        + phoneNumber, Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(DigitsException exception) {
                Log.d("Digits", "Sign in with Digits failure", exception);
            }

        });

    }
    void signin(String phno)
    {
      mAuth.createUserWithEmailAndPassword(phno+"@bilancio.pythonanywhere.com",phno);
        userupload();
    }
    public void userupload()
    {
        FirebaseUser firebaseUser=mAuth.getCurrentUser();
        if(firebaseUser==null)
            Toast.makeText(getApplicationContext(), "error uploading", Toast.LENGTH_SHORT).show();
        else
        {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            myRef = database.getReference("USERS");
            Toast.makeText(getApplicationContext(),firebaseUser.getEmail() , Toast.LENGTH_SHORT).show();
            user user = new user(firebaseUser.getUid(),firebaseUser.getEmail());
            myRef.child(firebaseUser.getUid())
                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        // successfully added user
                        Toast.makeText(getApplicationContext(), "added", Toast.LENGTH_SHORT).show();
                    } else {
                        // failed to add user
                        Toast.makeText(getApplicationContext(), "error adding", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


}


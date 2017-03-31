package hitha.ken.deva.abin.smartbudget;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        first_time_check();
        setContentView(R.layout.activity_main);
    }
    private boolean first_time_check() {
        SharedPreferences mPreferences= getSharedPreferences("MyPref", MODE_PRIVATE);
        String first = mPreferences.getString("loginid", null);
        if((first == null)){
            Intent i = new Intent(MainActivity.this,login.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }
        return false;
    }
    public void addexp(View v)
    {
        Intent i=new Intent(this,addexpense.class);
        startActivity(i);
    }
    public void pay(View v)
    {
        Intent i=new Intent(this,pay.class);
        startActivity(i);
    }
    public void view(View v)
    {
        Intent i=new Intent(this,list.class);
        startActivity(i);
    }
}


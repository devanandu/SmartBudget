package hitha.ken.deva.abin.smartbudget;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class addexpense extends AppCompatActivity {

    RadioGroup rg;
    Spinner spin;
    ArrayAdapter<CharSequence> adapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addexpense);
        rg=(RadioGroup)findViewById(R.id.rg);

        spin = (Spinner) findViewById(R.id.spinner);
        if (rg.getCheckedRadioButtonId() == R.id.expense) {
            adapter = ArrayAdapter.createFromResource(this, R.array.expense, android.R.layout.simple_spinner_item);
            spin.setAdapter(adapter);
        }
        if (rg.getCheckedRadioButtonId() == R.id.income) {
            adapter = ArrayAdapter.createFromResource(this, R.array.income, android.R.layout.simple_spinner_item);
            spin.setAdapter(adapter);
        }
    }
       public void catlist(View v){

        if(rg.getCheckedRadioButtonId()==R.id.expense) {
            adapter = ArrayAdapter.createFromResource(this, R.array.expense, android.R.layout.simple_spinner_item);
            spin.setAdapter(adapter);
        }
        if(rg.getCheckedRadioButtonId()==R.id.income) {
            adapter = ArrayAdapter.createFromResource(this, R.array.income, android.R.layout.simple_spinner_item);
            spin.setAdapter(adapter);
    }
    }
    public void savetransact(View v) {
        transactDB db = new transactDB(this);
        String type;
        TextView amt = (TextView) findViewById(R.id.amount);
        TextView notes = (TextView) findViewById(R.id.notes);
        if (rg.getCheckedRadioButtonId() == R.id.expense)
            type = "Expense";
        else
            type = "Income";
        if (db.addtransact(type, spin.getSelectedItem().toString(), amt.getText().toString(), notes.getText().toString()))
            Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
    }
}

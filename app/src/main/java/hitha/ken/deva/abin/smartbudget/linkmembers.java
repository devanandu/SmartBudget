package hitha.ken.deva.abin.smartbudget;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class linkmembers extends AppCompatActivity {
    private ArrayList<Map<String, String>> mPeopleList;
    private SimpleAdapter mAdapter;
    private AutoCompleteTextView mTxtPhoneNo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linkmembers);

        mPeopleList = new ArrayList<Map<String, String>>();
        PopulatePeopleList();
        mTxtPhoneNo = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);

        mAdapter = new SimpleAdapter(this, mPeopleList, R.layout.contactlist,
                new String[]{"Name", "Phone"}, new int[]{
                R.id.ccontName, R.id.ccontNo,});
        mTxtPhoneNo.setAdapter(mAdapter);
        mTxtPhoneNo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, String> map = (Map<String, String>) parent.getItemAtPosition(position);
                String name = map.get("Name");
                String number = map.get("Phone");
                mTxtPhoneNo.setText("" + name + "<" + number + ">");
            }
        });

    }

    public void PopulatePeopleList() {
        mPeopleList.clear();
        Cursor people = getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        while (people.moveToNext()) {
            String contactName = people.getString(people
                    .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            String contactId = people.getString(people
                    .getColumnIndex(ContactsContract.Contacts._ID));
            String hasPhone = people
                    .getString(people
                            .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

            if ((Integer.parseInt(hasPhone) > 0)) {
                // You know have the number so now query it like this
                Cursor phones = getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                int count = 0;
                while (phones.moveToNext()) {
                    //store numbers and display a dialog letting the user select which.
                    count++;
                    String phoneNumber = phones.getString(
                            phones.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER));
                    Map<String, String> NamePhoneType = new HashMap<String, String>();
                    NamePhoneType.put("Name", contactName);
                    NamePhoneType.put("Phone", phoneNumber);
                    mPeopleList.add(NamePhoneType);
                    if (count == 2)
                        break;
                }
                phones.close();
            }
        }
        people.close();
        startManagingCursor(people);
    }

    public void onItemClick(AdapterView<?> av, View v, int index, long arg) {
        Map<String, String> map = (Map<String, String>) av.getItemAtPosition(index);
        //Iterator<String> myVeryOwnIterator = map.keySet().iterator();
        String name = map.get("Name");
        String number = map.get("Phone");
        mTxtPhoneNo.setText("" + name + "<" + number + ">");

    }


}
package hitha.ken.deva.abin.smartbudget;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class Add_product extends Activity {
    EditText editName,editDate,editExpiry,editService,editFrom;
    Button bt_sel;
    productdb myDb;
    public ImageView imageView;
    Bitmap photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        myDb=new productdb(this);
        //imageView.setImageResource(R.mipmap.camera);
        imageView =(ImageView) findViewById(R.id.cam);
        editName=(EditText)findViewById(R.id.editText);
        editDate=(EditText)findViewById(R.id.editText2);
        editExpiry=(EditText)findViewById(R.id.editText3);
        editService=(EditText)findViewById(R.id.editText4);
        editFrom=(EditText)findViewById(R.id.editText5);
        bt_sel=(Button) findViewById(R.id.button);

    }
    public void insert(View view)
    {
        boolean inserted=myDb.addProduct(editName.getText().toString(),
                editDate.getText().toString(),
                editExpiry.getText().toString(),
                editService.getText().toString(),
                editFrom.getText().toString(),photo);
        if(inserted == true)
            Toast.makeText(Add_product.this,"Data inserted",Toast.LENGTH_LONG).show();
        else
            Toast.makeText(Add_product.this,"Data not inserted",Toast.LENGTH_LONG).show();
        reset();
    }
    public void camera(View v)
    {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent,0);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
            ByteArrayOutputStream out=new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG,100,out);
            byte ByteArr[]=out.toByteArray();
        }}
void reset()
{
    editDate.setText("");
    editExpiry.setText("");
    editFrom.setText("");
    editName.setText("");
    editService.setText("");
    imageView.setImageResource(R.mipmap.camera);
}
}

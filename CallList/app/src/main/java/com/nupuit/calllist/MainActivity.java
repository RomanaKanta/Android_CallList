package com.nupuit.calllist;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    Cursor cursor ;
    String name, phonenumber ;
    public  static final int RequestPermissionCode  = 0010 ;
    ArrayList<ModelClass> StoreContacts  =  new ArrayList<ModelClass>();
    DatabaseHandler db;
    ListAdapter adapter;
    int count = 10;

    @Bind(R.id.por_image)
    ImageView profileImage;

    @Bind(R.id.btn_more)
    Button btnMore;

    @OnClick(R.id.btn_more)
    public void setMore(){

        count = count+ 10;

        if(count>StoreContacts.size()){
            count = StoreContacts.size();
            btnMore.setText("No more results to show");
        }


        ArrayList<ModelClass> sublist = new ArrayList<ModelClass>(StoreContacts.subList(0,count));
//        adapter = new ListAdapter(this,sublist);
//        recyclerView.setAdapter(adapter);
        adapter.setData(sublist);
        adapter.notifyDataSetChanged();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        db = new DatabaseHandler(this);

        Bitmap bm = BitmapFactory.decodeResource(getResources(),
                R.drawable.photo);
        profileImage.setImageBitmap(getCircleBitmap(bm));

        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        adapter = new ListAdapter(this,StoreContacts);
        recyclerView.setAdapter(adapter);

        EnableRuntimePermission();

    }

    public void GetContactsIntoArrayList(){

        cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null, null, null);

        while (cursor.moveToNext()) {

            name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

            phonenumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            db.addData(new ModelClass(name,phonenumber));

        }

        cursor.close();


        String queryall = "SELECT DISTINCT * FROM "+ Constant.TABLE_NAME;

        StoreContacts = db.getAllData(queryall);

        ArrayList<ModelClass> sublist = new ArrayList<ModelClass>(StoreContacts.subList(0,count));
        adapter = new ListAdapter(this,sublist);
        recyclerView.setAdapter(adapter);


    }


    public void EnableRuntimePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(
                MainActivity.this,
                Manifest.permission.READ_CONTACTS))
        {

            Toast.makeText(MainActivity.this,"CONTACTS permission allows us to Access CONTACTS app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                    Manifest.permission.READ_CONTACTS}, RequestPermissionCode);

        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {

        switch (RC) {

            case RequestPermissionCode:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(MainActivity.this,"Permission Granted, Now your application can access CONTACTS.", Toast.LENGTH_LONG).show();

                    GetContactsIntoArrayList();

                } else {

                    Toast.makeText(MainActivity.this,"Permission Canceled, Now your application cannot access CONTACTS.", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }

    private Bitmap getCircleBitmap(Bitmap bitmap) {
        final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);

        final int color = Color.RED;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        bitmap.recycle();

        return output;
    }

}

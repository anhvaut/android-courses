package com.example.huynhle.tim_tro.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.huynhle.tim_tro.R;
import com.example.huynhle.tim_tro.adapter.adapter_laydulieu;
import com.example.huynhle.tim_tro.classLaydulieu;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class laygiulieubase extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    private ImageView imvanhnguoidung;
    private Button btndangtin;

    String anhnguoidung;
    String name;

    ListView lvlaydulieu;
    LinearLayout detailRoom;
    ArrayList<classLaydulieu> arrLaydulieu;
    adapter_laydulieu adapter=null;


    DatabaseReference mData;

//    View footerview;
//    boolean isLoading = false;
//    mHandler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_laygiulieubase );



        mData = FirebaseDatabase.getInstance().getReference();
        AnhXa();

        btndangtin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MHChoThue.class));
            }
        });

        adapter = new adapter_laydulieu( this, R.layout.dong_list_laydulieu, arrLaydulieu );
        lvlaydulieu.setAdapter( adapter );
        LoadData();

        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lvlaydulieu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(laygiulieubase.this, "Da nhan item", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(laygiulieubase.this, chi_tiet_room.class));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void LoadData() {
        mData.child( "ThongTinTro" ).addChildEventListener( new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                Toast.makeText( laygiulieubase.this, dataSnapshot.getValue().toString()+"\n", Toast.LENGTH_LONG ).show();
                classLaydulieu upload = dataSnapshot.getValue(classLaydulieu.class);
                arrLaydulieu.add( new classLaydulieu(upload.getTennguoidung(), upload.getAnhnguoidung(),
                        upload.getQuanHuyen()+"-Đà Nẵng", upload.getGiaPhong()+"  VND", upload.getDienTich()+" m²",
                        upload.getGiodang(), upload.getLinkAnhTro()) );
//                adapter.notifyDataSetChanged();
                adapter = new adapter_laydulieu( laygiulieubase.this, R.layout.dong_list_laydulieu, arrLaydulieu );
                lvlaydulieu.setAdapter( adapter );
//                arrLaydulieu.add(new classLaydulieu("Le Van Ha", "http://nhatrodanang.com/mp-up/2017/11/23130489_1619574658101948_5801269117031255651_n.jpg",
//                        "Nguyen Luong Bang", "1000000", "30 met",
//                        "1 gio truoc", "http://nhatrodanang.com/mp-up/2017/11/23130489_1619574658101948_5801269117031255651_n.jpg"));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        } );
    }


    private void AnhXa() {
        lvlaydulieu = (ListView) findViewById( R.id.lvlaydulieu );
        imvanhnguoidung = findViewById(R.id.imvanhnguoidung);
//        txtvten = findViewById(R.id.txtvten);
//        txtvmail = findViewById(R.id.txtvmail);
        mDrawerLayout = findViewById(R.id.mDrawerLayout);
        arrLaydulieu = new ArrayList<classLaydulieu>(  );
        btndangtin = findViewById(R.id.btndangtin);


//        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
//        footerview = layoutInflater.inflate(R.layout.footer_view, null);
//        mHandler = new mHandler();


//        arrLaydulieu.add( new classLaydulieu("Le Van Huynh", "http://nhatrodanang.com/mp-up/2017/11/23130489_1619574658101948_5801269117031255651_n.jpg",
//                "Nguyen Luong Bang", "1000000", "30 met",
//                "1 gio truoc", "http://nhatrodanang.com/mp-up/2017/11/23130489_1619574658101948_5801269117031255651_n.jpg") );
//        arrLaydulieu.add( new classLaydulieu("Le Van Huynh", "http://nhatrodanang.com/mp-up/2017/11/23130489_1619574658101948_5801269117031255651_n.jpg",
//                "Nguyen Luong Bang", "1000000", "30 met",
//                "1 gio truoc", "http://nhatrodanang.com/mp-up/2017/11/23130489_1619574658101948_5801269117031255651_n.jpg") );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_app, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.menuSearch).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(laygiulieubase.this, "no", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText.trim());
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


//    public class mHandler extends Handler{
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what){
//                case 0:
//                    lvlaydulieu.addFooterView(footerview);
//                    break;
//                case 1:
//                    lvlaydulieu.removeFooterView(footerview);
//                    adapter.AddListItemAdapter((ArrayList<classLaydulieu>) msg.obj);
//                    isLoading = false;
//                    break;
//            }
//        }
//    }
//    public class ThreaData extends Thread{
//        @Override
//        public void run() {
////            mHandler.sendEmptyMessage(0);
////            ArrayList<classLaydulieu> mangdata = getMoreData();
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
////            Message message = mHandler.obtainMessage(1, mangdata);
////            mHandler.sendMessage(message);
//
//        }
//    }
////
//    public ArrayList<classLaydulieu> getMoreData(){
//        ArrayList<classLaydulieu> list = new ArrayList<>();
//        return list;
//    }

}


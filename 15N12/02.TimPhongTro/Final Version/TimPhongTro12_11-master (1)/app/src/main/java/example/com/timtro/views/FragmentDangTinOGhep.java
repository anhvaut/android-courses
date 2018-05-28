package example.com.timtro.views;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

import example.com.timtro.interfaces.Click;
import example.com.timtro.R;
import example.com.timtro.models.TimNguoiOGhep;

/**
 * Created by User on 11/25/2017.
 */

public class FragmentDangTinOGhep extends Fragment {
    private EditText ten,tuoi,sdtlh,diachi,giatien,thongtinkhac;
    private ImageView imv;
    private Button btchontep,btluu;
    private Spinner spinnerGioiTinh,spinnerGiaTien;
    private CheckBox cb1,cb2,cb3,cb4,cb5,cb6;
    private String[] stringGioiTinh,stringGiaTien;
    private ArrayAdapter<String> spinnerAddapterGioiTinh,spinnerAddapterGiaTien;
    private static final int PICK_IMAGE = 100;
    private String imagePath,bien1,bien2;
    private int column_index;
    private DatabaseReference databaseReference;
    private DatabaseReference mDatabase;
    private Click click;
    private static final int RESULT_OK = -1;
    private static int RESULT_LOAD_IMAGE = 1;
    Bitmap image;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dangtinoghep, container, false);
        mDatabase =  FirebaseDatabase.getInstance().getReference("thongtin");
        databaseReference = mDatabase.child("nguoioghep");
        connectView(view);
        connectspinner();
        btluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickluu();
            }
        });
        btchontep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
        connectView(view);
        connectspinner();
        return view;
    }



    private void connectView(View view) {
        //Ket noi voi edit text
        ten=(EditText) view.findViewById(R.id.ten) ;
        tuoi=(EditText) view.findViewById(R.id.tuoi) ;
        sdtlh=(EditText) view.findViewById(R.id.sdtlh) ;
        diachi=(EditText) view.findViewById(R.id.diachi) ;
        giatien=(EditText) view.findViewById(R.id.giatien) ;
        imv = (ImageView) view.findViewById(R.id.imv) ;
        btluu = (Button) view.findViewById(R.id.btluu);
        btchontep = (Button) view.findViewById(R.id.btchontep) ;
        cb1=(CheckBox) view.findViewById(R.id.checkBox1) ;
        cb2=(CheckBox) view.findViewById(R.id.checkBox2) ;
        cb3=(CheckBox) view.findViewById(R.id.checkBox3) ;
        cb4=(CheckBox) view.findViewById(R.id.checkBox4) ;
        cb5=(CheckBox) view.findViewById(R.id.checkBox5) ;
        cb6=(CheckBox) view.findViewById(R.id.checkBox6) ;
        thongtinkhac=(EditText) view.findViewById(R.id.ttkhac) ;
        spinnerGioiTinh = (Spinner) view.findViewById(R.id.spiner1);
        spinnerGiaTien = (Spinner) view.findViewById(R.id.spiner2);
        // set on click

    }

    private void connectspinner() {

        stringGioiTinh = getResources().getStringArray(R.array.gioi_tinh);
        spinnerAddapterGioiTinh = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,stringGioiTinh);
        spinnerGioiTinh.setAdapter(spinnerAddapterGioiTinh);
        spinnerGioiTinh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println(R.string.selected+stringGioiTinh[i]);
                bien1=stringGioiTinh[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        stringGiaTien = getResources().getStringArray(R.array.gia_tien);
        spinnerAddapterGiaTien = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,stringGiaTien);
        spinnerGiaTien.setAdapter(spinnerAddapterGiaTien);
        spinnerGiaTien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println(R.string.selected+stringGiaTien[i]);
                bien2=stringGiaTien[i];
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //GETTING IMAGE FROM GALLERY
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = this.getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            imv.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            imv.setImageURI(selectedImage);

        }
    }

    public void onClickluu(){

        String strten = ten.getText().toString();
        String strtuoi = tuoi.getText().toString();
        String strsdt = sdtlh.getText().toString();
        String strdiachi = diachi.getText().toString();
        String strgiatien = giatien.getText().toString();

        if(strten.length()==0){
            Toast.makeText(getActivity(),R.string.nhap_ten,Toast.LENGTH_LONG).show();

        }
        else{
            if(strtuoi.length()==0){
                Toast.makeText(getActivity(),R.string.nhap_tuoi,Toast.LENGTH_LONG).show();
            }
            else {
                if(strtuoi.length()>2){
                    Toast.makeText(getActivity(), R.string.tuoi_khong_hop_le,Toast.LENGTH_LONG).show();
                }
                else {
                    if(strsdt.length()==0){
                        Toast.makeText(getActivity(), R.string.nhap_sdt,Toast.LENGTH_LONG).show();
                    }
                    else {
                        if(strsdt.length()<10){
                            Toast.makeText(getActivity(), R.string.sdt_khong_hơp_le,Toast.LENGTH_LONG).show();
                        }
                        else {
                            if(strdiachi.length()==0){
                                Toast.makeText(getActivity(), R.string.nhap_dia_chi,Toast.LENGTH_LONG).show();
                            }
                            else {
                                if(strgiatien.length()==0){
                                    Toast.makeText(getActivity(), R.string.nhap_gia_tien,Toast.LENGTH_LONG).show();
                                }
                                else{
                                    addDataToFirebase();
                                    Toast.makeText(getActivity(), R.string.dang_tin_thanh_cong,Toast.LENGTH_LONG).show();
                                    Thread bamgio = new Thread() {
                                        public void run() {
                                            try {
                                                sleep(2000);
                                            } catch (Exception e) {

                                            } finally {
                                                click = (Click) getActivity();
                                                click.onClick();

                                            }
                                        }
                                    };
                                    bamgio.start();
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    private void addDataToFirebase() {
        TimNguoiOGhep timNguoiOGhep = new TimNguoiOGhep();
        String tien=giatien.getText().toString()+bien2;
        timNguoiOGhep.setGiatien(tien);
        timNguoiOGhep.setGioiTinh(bien1);
        timNguoiOGhep.setDiachi(diachi.getText().toString());
        timNguoiOGhep.setSdtlienhe(sdtlh.getText().toString());
        timNguoiOGhep.setTen(ten.getText().toString());
        int age=Integer.parseInt(tuoi.getText().toString());
        timNguoiOGhep.setTuoi(age);
        String thongtintro ="";
        if(cb1.isChecked()==true) thongtintro+=cb1.getText().toString()+"\n";
        if(cb2.isChecked()==true) thongtintro+=cb2.getText().toString()+"\n";
        if(cb3.isChecked()==true) thongtintro+=cb3.getText().toString()+"\n";
        if(cb4.isChecked()==true) thongtintro+=cb4.getText().toString()+"\n";
        if(cb5.isChecked()==true) thongtintro+=cb5.getText().toString()+"\n";
        if(cb6.isChecked()==true) thongtintro+=cb6.getText().toString()+"\n";
        thongtintro+=thongtinkhac.getText().toString();
        timNguoiOGhep.setChitiet(thongtintro);
        String key = databaseReference.push().getKey();
        timNguoiOGhep.setId(key);
        databaseReference.push().setValue(timNguoiOGhep);
    }
}

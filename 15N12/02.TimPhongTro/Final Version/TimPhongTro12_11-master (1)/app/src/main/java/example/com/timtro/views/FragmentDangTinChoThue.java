package example.com.timtro.views;

import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import example.com.timtro.interfaces.Click;
import example.com.timtro.R;
import example.com.timtro.models.PhongTro;

/**
 * Created by User on 11/25/2017.
 */

public class FragmentDangTinChoThue extends Fragment {
    private EditText diachi, gia, dientich, sdtlh, thongtinkhac;
    private Spinner spinnerGiaTien, spinnerDienTich;
    private ImageView imv;
    private Button btchontep, btluu;
    private String[] GiaTien, DienTich;
    private ArrayAdapter<String> spinnerAddapterGiaTien, spinnerAddapterDienTich;
    int column_index;
    String imagePath;
    private DatabaseReference databaseReference;
    private DatabaseReference mDatabase;
    private CheckBox cb1, cb2, cb3, cb4, cb5, cb6;
    private String bien1, bien2;
    private Click click;
    private static final int RESULT_OK = -1;
    private static int RESULT_LOAD_IMAGE = 1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dangtinchothue, container, false);
        mDatabase =  FirebaseDatabase.getInstance().getReference("thongtin");
        databaseReference = mDatabase.child("phongchothue");
        connectView(view);
        connectspinner();
        btluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDuLieu();
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
        return view;
    }

    private void connectView(View view) {
        //Ket noi voi edit text
        diachi = (EditText) view.findViewById(R.id.diachi);
        gia = (EditText) view.findViewById(R.id.giaphong);
        dientich = (EditText) view.findViewById(R.id.dientich);
        sdtlh = (EditText) view.findViewById(R.id.sdtlh);
        imv = (ImageView) view.findViewById(R.id.imv);
        btluu = (Button) view.findViewById(R.id.btluu);
        btchontep = (Button) view.findViewById(R.id.btchontep);
        cb1 = (CheckBox) view.findViewById(R.id.checkBox1);
        cb2 = (CheckBox) view.findViewById(R.id.checkBox2);
        cb3 = (CheckBox) view.findViewById(R.id.checkBox3);
        cb4 = (CheckBox) view.findViewById(R.id.checkBox4);
        cb5 = (CheckBox) view.findViewById(R.id.checkBox5);
        cb6 = (CheckBox) view.findViewById(R.id.checkBox6);
        thongtinkhac = (EditText) view.findViewById(R.id.ttkhac);
        spinnerGiaTien = (Spinner) view.findViewById(R.id.spiner1);
        spinnerDienTich = (Spinner) view.findViewById(R.id.spiner2);
        // set on click
    }

    private void connectspinner() {
        GiaTien = getResources().getStringArray(R.array.gia_tien);
        spinnerAddapterGiaTien = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, GiaTien);
        spinnerGiaTien.setAdapter(spinnerAddapterGiaTien);
        spinnerGiaTien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println(R.string.selected + GiaTien[i]);
                bien1 = GiaTien[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        DienTich = getResources().getStringArray(R.array.dientich);
        spinnerAddapterDienTich = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, DienTich);
        spinnerDienTich.setAdapter(spinnerAddapterDienTich);
        spinnerDienTich.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println(R.string.selected + DienTich[i]);
                bien2 = DienTich[i];
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
            Toast.makeText(getActivity(),String.valueOf(imv),Toast.LENGTH_LONG).show();

        }
    }

    public void addDuLieu() {

        String strdiachi = diachi.getText().toString();
        String strgiatien = gia.getText().toString();
        String strdientich = dientich.getText().toString();
        String strsdt = sdtlh.getText().toString();
        if (strdiachi.length() == 0) {
            Toast.makeText(getActivity(), R.string.nhap_dia_chi, Toast.LENGTH_LONG).show();

        } else {
            if (strgiatien.length() == 0) {
                Toast.makeText(getActivity(), R.string.nhap_gia_tien, Toast.LENGTH_LONG).show();
            } else {
                if (strdientich.length() == 0) {
                    Toast.makeText(getActivity(),R.string.nhap_dien_tich, Toast.LENGTH_LONG).show();
                } else {
                    if (strsdt.length() == 0) {
                        Toast.makeText(getActivity(), R.string.nhap_sdt, Toast.LENGTH_LONG).show();
                    } else {
                        if (strsdt.length() < 10 || strsdt.length() > 11) {
                            Toast.makeText(getActivity(), R.string.sdt_khong_hơp_le, Toast.LENGTH_LONG).show();
                        } else {
                            addDataToFirebase();
                            Toast.makeText(getActivity(), R.string.dang_tin_thanh_cong, Toast.LENGTH_LONG).show();
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

    private void addDataToFirebase() {
        PhongTro phongTro = new PhongTro();
        phongTro.setDiachi(diachi.getText().toString());
        String dt = dientich.getText().toString() + bien2;
        phongTro.setDientich(dt);
        String tien = gia.getText().toString() + bien1;
        phongTro.setGiatien(tien);
        phongTro.setSdtlienhe(sdtlh.getText().toString());
        String thongtintro = "";
        if (cb1.isChecked() == true) thongtintro += cb1.getText().toString() + "\n";
        if (cb2.isChecked() == true) thongtintro += cb2.getText().toString() + "\n";
        if (cb3.isChecked() == true) thongtintro += cb3.getText().toString() + "\n";
        if (cb4.isChecked() == true) thongtintro += cb4.getText().toString() + "\n";
        if (cb5.isChecked() == true) thongtintro += cb5.getText().toString() + "\n";
        if (cb6.isChecked() == true) thongtintro += cb6.getText().toString() + "\n";
        thongtintro += thongtinkhac.getText().toString();
        phongTro.setChitiet(thongtintro);
        String key = databaseReference.push().getKey();
        phongTro.setId(key);
        databaseReference.push().setValue(phongTro);
    }
}

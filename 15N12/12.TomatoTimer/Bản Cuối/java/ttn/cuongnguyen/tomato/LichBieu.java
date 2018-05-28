package ttn.cuongnguyen.tomato;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Cuongnguyen on 9/29/2017.
 */

public class LichBieu extends AppCompatActivity {
    TextView txtNgay, txtGio;
    Button btnNhap;
    EditText edtCongViec;
    ImageButton imgNgay, imgGio;
    Calendar calendar= Calendar.getInstance();
    SimpleDateFormat sdf1= new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat sdf2= new SimpleDateFormat("HH:mm");
    ListView lvCongViec;
    ArrayList<CongViec> dsCongViec;
    CongViecAdapter congViecAdapter;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);

        addContent();
        addEvent();
    }

    private void addContent() {
        txtGio= (TextView) findViewById(R.id.txtGio);
        txtNgay= (TextView) findViewById(R.id.txtNgay);
        imgGio= (ImageButton) findViewById(R.id.imgGio);
        imgNgay= (ImageButton) findViewById(R.id.imgNgay);
        btnNhap= (Button) findViewById(R.id.btnNhap);
        calendar=Calendar.getInstance();
        edtCongViec= (EditText) findViewById(R.id.edtCongViec);
        txtNgay.setText(sdf1.format(calendar.getTime()));
        txtGio.setText(sdf2.format(calendar.getTime()));



    }

    private void addEvent() {
        btnNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lvCongViec= (ListView) findViewById(R.id.lvDanhSach);
                dsCongViec= new ArrayList<>();
                dsCongViec.add(new CongViec(edtCongViec.getText().toString(), (String) txtGio.getText(), (String) txtNgay.getText()));
                congViecAdapter = new CongViecAdapter(LichBieu.this, R.layout.item, dsCongViec);
                lvCongViec.setAdapter(congViecAdapter);

            }
        });
        imgNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thayDoiNgay();
            }

            private void thayDoiNgay() {
                DatePickerDialog.OnDateSetListener callBack = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        txtNgay.setText(sdf1.format(calendar.getTime()));

                    }
                };
                DatePickerDialog datePickerDialog = new DatePickerDialog(LichBieu.this, callBack, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
        imgGio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thayDoiGio();
            }

            private void thayDoiGio() {
                final TimePickerDialog.OnTimeSetListener callBack = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        txtGio.setText(sdf2.format(calendar.getTime()));
                    }
                };
                TimePickerDialog timePickerDialog = new TimePickerDialog(LichBieu.this, callBack, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
            timePickerDialog.show();
            }
        });
    }
}

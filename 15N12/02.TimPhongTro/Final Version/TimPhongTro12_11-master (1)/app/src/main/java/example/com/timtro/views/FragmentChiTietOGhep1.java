package example.com.timtro.views;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import example.com.timtro.activity.ChiTietOGhepActivity;
import example.com.timtro.R;
import example.com.timtro.models.TimNguoiOGhep;

public class FragmentChiTietOGhep1 extends Fragment {
    private TextView texttelephone, texttien,textdiachi,textname,textage,textgt;
    private Button btcall;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1_chitietoghep,container,false);
        getview(view);
        nhandulieu();
        btcall=(Button) view.findViewById(R.id.call) ;
        btcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse(R.string.tel + texttelephone.getText().toString()));//change the number
                startActivity(callIntent);
            }
        });
        return view;
    }

    private void getview(View view){
        texttelephone = (TextView) view.findViewById(R.id.texttelephone);
        texttien = (TextView) view.findViewById(R.id.texttien);
        textdiachi = (TextView) view.findViewById(R.id.textdiachi);
        textname = (TextView) view.findViewById(R.id.textname);
        textage = (TextView) view.findViewById(R.id.textage);
        textgt = (TextView) view.findViewById(R.id.textgt);
    }
    private void nhandulieu(){
        ChiTietOGhepActivity activityChiTietOGhep = (ChiTietOGhepActivity) getActivity();
        TimNguoiOGhep timNguoiOGhep = activityChiTietOGhep.timNguoiOGhep;
        texttelephone.setText(timNguoiOGhep.getSdtlienhe().toString());
        textdiachi.setText(timNguoiOGhep.getDiachi().toString());
        String tuoi= String.valueOf(timNguoiOGhep.getTuoi());
        textage.setText(tuoi);
        texttien.setText(timNguoiOGhep.getGiatien().toString());
        textgt.setText(timNguoiOGhep.getGioiTinh().toString());
        textname.setText(timNguoiOGhep.getTen().toString());
    }
}

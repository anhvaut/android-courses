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
import example.com.timtro.activity.ChiTietTroActivity;
import example.com.timtro.R;
import example.com.timtro.models.PhongTro;

public class FragmentChiTietTro1 extends Fragment {
    private TextView texttelephone,giatien,diachi,dientich;
    private Button btcall;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1_chitiettro,container,false);
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
        giatien = (TextView) view.findViewById(R.id.texttien);
        diachi = (TextView) view.findViewById(R.id.textdiachi);
        dientich = (TextView) view.findViewById(R.id.textdt);
    }
    private void nhandulieu(){
        ChiTietTroActivity activityChiTietTro = (ChiTietTroActivity) getActivity();
        PhongTro phongTro = activityChiTietTro.phongTro;
        texttelephone.setText(phongTro.getSdtlienhe().toString());
        giatien.setText(phongTro.getGiatien().toString());
        diachi.setText(phongTro.getDiachi().toString());
        dientich.setText(phongTro.getDientich().toString());
    }
}

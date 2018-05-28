package example.com.timtro.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import example.com.timtro.activity.ChiTietTroActivity;
import example.com.timtro.R;
import example.com.timtro.models.PhongTro;

public class FragmentChiTietTro2 extends Fragment {
    private TextView chitet;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2_chitiettro,container,false);
        chitet = (TextView) view.findViewById(R.id.chitiet);
        ChiTietTroActivity activityChiTietTro = (ChiTietTroActivity) getActivity();
        PhongTro phongTro = activityChiTietTro.phongTro;
        chitet.setText(phongTro.getChitiet().toString());
        return view;
    }
}

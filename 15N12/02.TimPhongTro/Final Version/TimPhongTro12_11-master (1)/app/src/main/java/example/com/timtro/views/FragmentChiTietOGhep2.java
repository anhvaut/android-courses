package example.com.timtro.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import example.com.timtro.activity.ChiTietOGhepActivity;
import example.com.timtro.R;
import example.com.timtro.models.TimNguoiOGhep;

public class FragmentChiTietOGhep2 extends Fragment {
    private TextView textchitiet;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2_chitietoghep,container,false);
        textchitiet=(TextView) view.findViewById(R.id.textchitiet);
        ChiTietOGhepActivity activityChiTietOGhep = (ChiTietOGhepActivity) getActivity();
        TimNguoiOGhep timNguoiOGhep = activityChiTietOGhep.timNguoiOGhep;
        textchitiet.setText(timNguoiOGhep.getChitiet().toString());
        return view;
    }
}

package example.com.timtro.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import example.com.timtro.activity.SuCoActivity;
import example.com.timtro.R;

public class FragmentSuCo extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_suco, container,false);
        ListView listView =(ListView) view.findViewById(R.id.lvSuco);
        final String arr[] = {"Tìm Phòng Trọ", "Tìm người ở ghép", "Đăng tin cho thuê", "Đăng tin ở ghép"};
        ArrayAdapter<String> adapter  = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, arr);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity() , SuCoActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}

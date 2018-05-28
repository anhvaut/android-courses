package ttn.cuongnguyen.tomato;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


/**
 * Created by Cuongnguyen on 22/11/2017.
 */

public class CongViecAdapter extends ArrayAdapter<CongViec> {
    Context context;
    int resource;
    List<CongViec> objects;
    public CongViecAdapter(@NonNull Context context, int resource, @NonNull List<CongViec> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row=inflater.inflate(resource, parent, false);
        TextView txtCongViec = (TextView) row.findViewById(R.id.txtlvcongviec);
        TextView txtthoiGianGio = (TextView) row.findViewById(R.id.txtlvgio);
        TextView txtthoiGianNgay = (TextView) row.findViewById(R.id.txtlvngay);
        CongViec congViec = this.objects.get(position);
        txtCongViec.setText((CharSequence) congViec.getTenCongViec());
        txtthoiGianGio.setText(congViec.getThoiGianGio());
        txtthoiGianNgay.setText(congViec.getThoiGianNgay());

        return row;
    }
}

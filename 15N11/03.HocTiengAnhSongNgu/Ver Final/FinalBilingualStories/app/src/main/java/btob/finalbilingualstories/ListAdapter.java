package btob.finalbilingualstories;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import static btob.finalbilingualstories.R.id.imageView;

/**
 * Created by qs270 on 11/22/2017.
 */

public class ListAdapter extends ArrayAdapter<Story> {

    public ListAdapter(Context context, int resource, List<Story> items) {
        super(context, resource, items);
    }

    public class ViewHolder {
        TextView txtEngTitle, txtVieTitle;
        ImageView imgv;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view =  inflater.inflate(R.layout.custom_layout_list, null);
            holder = new ViewHolder();

            holder.txtEngTitle = (TextView) view.findViewById(R.id.tvEng);
            holder.txtVieTitle = (TextView) view.findViewById(R.id.tvVie);
            holder.imgv = (ImageView) view.findViewById(R.id.image);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        Story p = getItem(position);
        if(p!=null) {
            // Anh xa + Gan gia tri
            holder.txtEngTitle.setText(p.EngTitle);
            holder.txtVieTitle.setText(p.VieTitle);
            //Nếu là Integer thì String.valueOf(p.SoLuong)
            Picasso.with(getContext()).load(p.Image).into(holder.imgv);
        }
        return view;
    }
}
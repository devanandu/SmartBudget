package hitha.ken.deva.abin.smartbudget;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by deva on 18/04/17.
 */

public class req_adapter extends RecyclerView.Adapter<req_adapter.Viewholder> {
    private List<String> array;
    public class Viewholder extends RecyclerView.ViewHolder{
        public TextView n;
        public Viewholder(View itemView) {
            super(itemView);
            n=(TextView)itemView.findViewById(R.id.lnk_member_name);
        }
    }
    public req_adapter.Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.memlist_layout, parent, false);
        Viewholder vh=new Viewholder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(Viewholder holder, int position) {
        holder.n.setText(array.get(position));
        holder.n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

   public req_adapter(List<String> s){array=s;}

}

package hitha.ken.deva.abin.smartbudget;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by deva on 16/04/17.
 */

public class memberlist_adapter extends RecyclerView.Adapter<memberlist_adapter.ViewHolder>{
    private List<members> array;
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView n,s;
        public ViewHolder(View t)
        {
            super(t);
            n=(TextView) t.findViewById(R.id.lnk_member_name);
            s=(TextView) t.findViewById(R.id.request_status);
        }
    }
    public memberlist_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.memlist_layout, parent, false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(array.get(position).status.equals("false"))
        {
            holder.s.setTextColor(Color.RED);
            holder.s.setText("------ Request Send...");
        }
        else
        {
            holder.s.setTextColor(Color.GREEN);
            holder.s.setText("------ Linked");
        }
        Log.e("db",array.get(position).phno);
        String s="  * "+array.get(position).name+"("+array.get(position).phno+")";
        holder.n.setText(s);
    }


    @Override
    public int getItemCount() {

        return array.size();
    }


    public memberlist_adapter(List<members>s)
    {
        array=s;
    }
}


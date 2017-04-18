package hitha.ken.deva.abin.smartbudget;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

/**
 * Created by deva on 16/04/17.
 */

public class memberlist_adapter extends RecyclerView.Adapter<memberlist_adapter.ViewHolder>{
    private List<members> array;
    public class ViewHolder extends RecyclerView.ViewHolder {
        public CheckBox check;
        public ViewHolder(View t)
        {
            super(t);
            check=(CheckBox) t.findViewById(R.id.checkBox);

        }
    }
    public memberlist_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.linked_members_rec_layout, parent, false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String s=array.get(position).name+"("+array.get(position).phno+")";
        holder.check.setText(s);
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


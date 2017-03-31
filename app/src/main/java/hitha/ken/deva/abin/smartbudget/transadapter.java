package hitha.ken.deva.abin.smartbudget;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by deva on 11/03/17.
 */

public class transadapter extends RecyclerView.Adapter<transadapter.ViewHolder>{
    private List<String> array;
    static int count=1;
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txt;

        public ViewHolder(View t)
        {
            super(t);
            txt=(TextView)t.findViewById(R.id.data);
            if(count%2==0)
            txt.setTextColor(Color.CYAN);
            else
                txt.setTextColor(Color.YELLOW);
            count++;
        }
    }
    public transadapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.translist, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(transadapter.ViewHolder holder, int position) {

        holder.txt.setText(array.get(position));

    }

    @Override
    public int getItemCount() {

        return array.size();
    }


    public transadapter(List<String> s)
    {array=s;}
}

package techkids.vn.customviewskills.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import techkids.vn.customviewskills.R;
import techkids.vn.customviewskills.models.ModelListCustom;

public class RecyclerListCustomAdapter extends RecyclerView.Adapter<RecyclerListCustomAdapter.ViewHolder> {
    private Context context;
    private static final String TAG = RecyclerListCustomAdapter.class.toString();
    private List<ModelListCustom> listCustoms;

    public RecyclerListCustomAdapter(Context context, List<ModelListCustom> listCustoms) {
        this.context = context;
        this.listCustoms = listCustoms;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        for(ModelListCustom model: listCustoms){
            Log.d(TAG, String.format("onCreateViewHolder: %s - %s", model.getDescription(), model.getTitle()));
        }
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_rl_list_custom, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.tvDescription.setText(listCustoms.get(position).getDescription());
        holder.tvTitle.setText(listCustoms.get(position).getTitle());

        //bat su kien click vao item
        holder.cvItemListCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: cac");
                if (onItemClickedListener != null) {
                    //chuyen title sang main activity
                    onItemClickedListener.onItemClick(holder.tvTitle.getText().toString());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listCustoms.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvDescription;
        CardView cvItemListCustom;

        ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDescription = itemView.findViewById(R.id.tv_description);
            cvItemListCustom = itemView.findViewById(R.id.cv_item_list_custom);
        }
    }

    //khai bao interface su dung o ham main
    public interface OnItemClickedListener {
        void onItemClick(String title);
    }

    private OnItemClickedListener onItemClickedListener;

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }
}

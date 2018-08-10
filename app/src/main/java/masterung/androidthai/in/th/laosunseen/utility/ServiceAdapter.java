package masterung.androidthai.in.th.laosunseen.utility;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import masterung.androidthai.in.th.laosunseen.R;

public class ServiceAdapter extends  RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder>{

    private Context context;
    private ArrayList<String> phoStringArrayList, nmeStringArrayList, postStringArrayList;
    private LayoutInflater layoutInflater;

    public ServiceAdapter(Context context, ArrayList<String> phoStringArrayList, ArrayList<String> nmeStringArrayList, ArrayList<String> postStringArrayList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.phoStringArrayList = phoStringArrayList;
        this.nmeStringArrayList = nmeStringArrayList;
        this.postStringArrayList = postStringArrayList;
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.recycler_view_service, parent, false);

        ServiceViewHolder serviceViewHolder = new ServiceViewHolder(view);
        return serviceViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {

        String urlpathString = phoStringArrayList.get(position);
        String nameString = nmeStringArrayList.get(position);
        String postString = postStringArrayList.get(position);

        holder.namTextView.setText(nameString);
        holder.posTextView.setText(postString);
        Picasso.get().load(urlpathString)
                .resize(150, 150)
                .into(holder.circleImageView);


    }

    @Override
    public int getItemCount() {
        return nmeStringArrayList.size();
    }

    public class ServiceViewHolder extends RecyclerView.ViewHolder{

        private CircleImageView circleImageView;
        private TextView namTextView, posTextView;


        public ServiceViewHolder(View itemView) {
            super(itemView);

            circleImageView = itemView.findViewById(R.id.photocricle);
            namTextView = itemView.findViewById(R.id.txtName);
            posTextView = itemView.findViewById(R.id.txtPost);


        }
    }//Service Class

}//Mine Class

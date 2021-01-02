package mik.maev.audiorecorder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import java.io.File;

public class AudioListAdapter extends RecyclerView.Adapter<AudioListAdapter.AudioViewHolder> {

    private File[] allFiles;
    private TimeAgo timeAgo;

    private View view;

    private onItemListClick onItemListClick;

    public AudioListAdapter(File[] allFiles, onItemListClick onItemListClick) {
        this.allFiles = allFiles;
        this.onItemListClick = onItemListClick;
    }

    @NonNull
    @Override
    public AudioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_list_item, parent, false);
        timeAgo = new TimeAgo();
        return new AudioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AudioViewHolder holder, int position) {
        holder.list_title.setText(allFiles[position].getName());
        holder.list_date.setText(timeAgo.getTimeAgo(allFiles[position].lastModified()));
    }

    @Override
    public int getItemCount() {
        return allFiles.length;
    }

    public class AudioViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView list_image;
        private TextView list_title;
        private TextView list_date;
        private ImageView list_delete;

        public AudioViewHolder(@NonNull View itemView) {
            super(itemView);

            list_image = itemView.findViewById(R.id.list_image_view);
            list_title = itemView.findViewById(R.id.list_title);
            list_date = itemView.findViewById(R.id.list_date);
            list_delete = itemView.findViewById(R.id.delete);

            list_title.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onItemListClick.onClickListener(allFiles[getAdapterPosition()], getAdapterPosition());

            list_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String filePath = "/storage/emulated/0/Android/data/com.tvacstudio.audiorecorder/files";
                    String file = list_title.getText().toString();
                    File obj = new File (filePath + "/" +  file);
                    if (obj.delete()){
                        list_title.setText("Successfully deleted");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        list_delete.setClickable(false);
                    }
                    else {
                        list_title.setText("Error");
                    }
                }
            });
        }
    }

    public interface onItemListClick {
        void onClickListener(File file, int position);
    }

}

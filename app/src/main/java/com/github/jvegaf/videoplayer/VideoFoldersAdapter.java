package com.github.jvegaf.videoplayer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VideoFoldersAdapter extends RecyclerView.Adapter<VideoFoldersAdapter.ViewHolder> {

    private ArrayList<MediaFiles> mediaFiles;
    private ArrayList<String> folderPath;
    private Context context;

    public VideoFoldersAdapter(ArrayList<MediaFiles> mediaFiles, ArrayList<String> folderPath, Context context) {
        this.mediaFiles = mediaFiles;
        this.folderPath = folderPath;
        this.context = context;
    }

    @NonNull
    @Override
    public VideoFoldersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.folder_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoFoldersAdapter.ViewHolder holder, int position) {

        // /storage/Media/Videos
        int indexPath = folderPath.get(position).lastIndexOf("/");
        String fName = folderPath.get(position).substring(indexPath+1);
        holder.folderName.setText(fName);
        holder.folderPath.setText(folderPath.get(position));
        holder.noOfFiles.setText("5 Videos");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VideoFilesActivity.class);
                intent.putExtra("folderName", fName);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return folderPath.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView folderName, folderPath, noOfFiles;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            folderName = itemView.findViewById(R.id.folderName);
            folderPath = itemView.findViewById(R.id.folderPath);
            noOfFiles = itemView.findViewById(R.id.noOfFiles);
        }
    }
}

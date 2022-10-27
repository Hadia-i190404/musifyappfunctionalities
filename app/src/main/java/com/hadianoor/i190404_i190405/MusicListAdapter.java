package com.hadianoor.i190404_i190405;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.ViewHolder>{


    ArrayList<AudioModel> songsList;
    Context context;

    public MusicListAdapter(ArrayList<AudioModel> songsList, Context context) {
        this.songsList = songsList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.recycler_item,parent,false);
        return new MusicListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MusicListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position)
    {

        AudioModel songData = songsList.get(position);
        holder.title.setText(songData.getTitle());
       // holder.title1.setText(songData.getTitle());
       // holder.title2.setText(songData.getTitle());

        if(MyMediaPlayer.currentIndex==position)
        {
            holder.title.setTextColor(Color.parseColor("#FF0000"));
        }
        else
        {
            holder.title.setTextColor(Color.parseColor("#000000"));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MyMediaPlayer.getInstance().reset();
                MyMediaPlayer.currentIndex=position;
                Intent intent= new Intent(context,MusicPlayerActivity.class);
                intent.putExtra("List",songsList);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return songsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        Button title,title1,title2;
        ImageView icon;
        public ViewHolder(View itemview)
        {
            super(itemview);
            title=itemview.findViewById(R.id.music_title_text);
            icon=itemview.findViewById(R.id.icon_view);
            title1=itemview.findViewById(R.id.music_title_text1);
            title2=itemview.findViewById(R.id.music_title_text2);
        }

    }

}

package com.example.keep_notes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Context context;
    private ArrayList titles,descriptions,times;

    public MyAdapter(Context context, ArrayList titles, ArrayList descriptions, ArrayList times) {
        this.context = context;
        this.titles = titles;
        this.descriptions = descriptions;
        this.times = times;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_view,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        int num = position;
        holder.titleOutput.setText(String.valueOf(titles.get(position)));
        holder.descriptionoutput.setText(String.valueOf(descriptions.get(position)));
//        Toast.makeText(context, ""+createTimeLable((Integer) times.get(position)), Toast.LENGTH_SHORT).show();
//        long createdtime = System.currentTimeMillis();
//        String formattime = DateFormat.getDateTimeInstance().format(times.get(position));
//        String formattime = DateFormat.getDateInstance().format(times.get(position));
//        Toast.makeText(context, formattime, Toast.LENGTH_SHORT).show();
        holder.timeoutput.setText(String.valueOf(times.get(position)));
//        holder.timeoutput.setText(formattime);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,updateactivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("utitle",String.valueOf(titles.get(num)));
                bundle.putString("udescription",String.valueOf(descriptions.get(num)));
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                PopupMenu menu = new PopupMenu(context,view);
                menu.getMenu().add("DELETE");
//                menu.getMenu().add("UPDATE");
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if(menuItem.getTitle().equals("DELETE")){
                            // deletebthe note
                            DBHelper DB = new DBHelper(context);
                            String nameTXT = String.valueOf(titles.get(num));

                            Boolean checkdeletedata  = DB.deleteuserdata(nameTXT);
                            if(checkdeletedata==true)
                            {
                                Toast.makeText(context, "Note Deleted", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(context,MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(context, "Entry Not Deleted", Toast.LENGTH_SHORT).show();
                            }
                        }
                        return true;
                    }
                });
                menu.show();
                return true;
            }
        });
    }


    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView titleOutput;
        TextView descriptionoutput;
        TextView timeoutput;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titleOutput = itemView.findViewById(R.id.titleoutput);
            descriptionoutput = itemView.findViewById(R.id.descriptionoutput);
            timeoutput = itemView.findViewById(R.id.timeoutput);
        }
    }

    public String createTimeLable(int duration){
        String timerLable = "";
        int min = duration/1000/60;
        int sec = duration/1000%60;

        if(min <10) timerLable += "0";


        if(sec<10){
            timerLable += min + ":" + "0" + sec;
        }else{
            timerLable += min + ":" + sec;
        }

        return timerLable;

    }

}


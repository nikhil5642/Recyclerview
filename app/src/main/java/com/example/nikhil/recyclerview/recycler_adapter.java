package com.example.nikhil.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v4.database.DatabaseUtilsCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static com.example.nikhil.recyclerview.R.id.parent;

/**
 * Created by nikhil on 11/3/17.
 */

public class recycler_adapter extends RecyclerView.Adapter<recycler_adapter.ViewHolder> {
    public ArrayList<object> mdatas;
    ViewGroup x;
    Context context;

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }

    public recycler_adapter(ArrayList<object> myDataset) {
        mdatas = myDataset;
    }
   public TextView c;
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
      private MonthActivity monthActivity;
        public TextView txth;
        public TextView txtf,txtt,txtd,txtr;
        private MyClickListener myClickListener;

        public ViewHolder(View itemView) {

            super(itemView);
            txth=(TextView)itemView.findViewById(R.id.detail_textview);
            txtf=(TextView)itemView.findViewById(R.id.location_textview);
            txtt=(TextView)itemView.findViewById(R.id.time_textview);
            txtd=(TextView)itemView.findViewById(R.id.date_textview);
            txtr=(TextView)itemView.findViewById(R.id.range_textview);
            c=txtr;
            itemView.setOnClickListener(this);

            context=itemView.getContext();
        }

        @Override
        public void onClick(View view) {
            myClickListener.onItemClick(getLayoutPosition(), view);
        }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;

    }


    }

   @Override
    public recycler_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       x=parent;
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.object,parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(recycler_adapter.ViewHolder holder, int position) {
       final object o1=mdatas.get(position);
        GradientDrawable circle =(GradientDrawable)c.getBackground();
        circle.setColor(getMagnitudeColor(Double.parseDouble(o1.getRange())));
        holder.txtr.setText(o1.getRange());
        if(o1.getLocation().contains("of")) {
            String[] part1 = o1.getLocation().split("of");

            holder.txtf.setText(part1[1]);
            holder.txth.setText(part1[0] + "of");
        }
        else {
            holder.txth.setText("");
            holder.txtf.setText(o1.getLocation());
            }
            Date date=new Date(o1.getTime());
            holder.txtd.setText(formatdate(date));
            holder.txtt.setText(formattime(date));

        holder.setOnItemClickListener(new MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Uri EarthquakeUri=Uri.parse(o1.getUrl());
                Intent web=new Intent(Intent.ACTION_VIEW,EarthquakeUri);
                context.startActivity(web);

            }
        });
    }


    public String formatdate(Date object){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("LLL dd, yyyy");
        return simpleDateFormat.format(object);
    }
    public String formattime(Date object){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("h:mm a");
        return simpleDateFormat.format(object);
    }

    @Override
    public int getItemCount() {
        return mdatas.size();
    }

    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
                return ContextCompat.getColor(x.getContext(),magnitudeColorResourceId);
    }

   }


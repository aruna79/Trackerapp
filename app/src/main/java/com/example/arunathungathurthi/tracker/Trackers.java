package com.example.arunathungathurthi.tracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Trackers extends AppCompatActivity {
    ArrayList<AdapterListItems> listnewData = new ArrayList<AdapterListItems>();
    CustomAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);
        listnewData.add(new AdapterListItems("Aruna","4257708280"));
    myAdapter=new CustomAdapter(listnewData);
        ListView lv=(ListView)findViewById(R.id.listView);
        lv.setAdapter(myAdapter);
    }

    void Refresh(){

    }

    private class CustomAdapter extends BaseAdapter{
        public ArrayList<AdapterListItems> listnewDataAdapter;
        public CustomAdapter(ArrayList<AdapterListItems> listnewDataAdapter){
            this.listnewDataAdapter=listnewDataAdapter;
        }

        @Override
        public int getCount() {
            return listnewDataAdapter.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertview, ViewGroup parent) {
            LayoutInflater Inflater = getLayoutInflater();
            View myview = Inflater.inflate(R.layout.contact,null);
            final AdapterListItems items = listnewDataAdapter.get(position);
            TextView tv_user_name=(TextView)myview.findViewById(R.id.tv_user_name);
            tv_user_name.setText(items.PhoneNumber);
            TextView tv_phone =(TextView)myview.findViewById(R.id.tv_phone);
            tv_phone.setText(items.PhoneNumber);

            return myview;

        }
    }
}

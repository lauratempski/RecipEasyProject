package com.example.recipeasy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class ShoppigList extends AppCompatActivity {

    private static ListView listView;
    private static ArrayList<String> items;
    private static ListViewAdapter adapter;
    private static Context context;

    private Toast t;

    private EditText input;
    private ImageView add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppig_list);

        listView = (ListView) findViewById(R.id.listView);
        items = new ArrayList<>();
        adapter = new ListViewAdapter(ShoppigList.this, items);
        listView.setAdapter(adapter);

        input = (EditText) findViewById(R.id.input);
        add = (ImageView) findViewById(R.id.addItem);
        context = getApplicationContext();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = input.getText().toString();
                if(text == null || text.length() == 0){
                    makeAToast("Enter an item");
                }else{
                    addItem(text);
                    input.setText("");
                    makeAToast("added " + text);
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = items.get(i);
                makeAToast(name);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                makeAToast("Long Press "+ items.get(i));
                return false;
            }
        });

        loadContent();
    }

    @Override
    protected void onDestroy() {
        File path = getApplicationContext().getFilesDir();
        try {
            FileOutputStream writer = new FileOutputStream(new File(path, "list.txt"));
            writer.write(items.toString().getBytes());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    public void loadContent(){
        File path = ShoppigList.this.getFilesDir();
        File readFrom =new File(path, "list.txt");
        byte[] content = new byte[(int) readFrom.length()];

        FileInputStream stream = null;
        try {
            stream = new FileInputStream(readFrom);
            stream.read(content);

            String s = new String(content);
            s = s.substring(1, s.length() - 1);
            String split[] = s.split(", ");

            if(split.length == 1 && split[0].isEmpty())
                items = new ArrayList<>();
            else items = new ArrayList<>(Arrays.asList(split));

            adapter = new ListViewAdapter(this, items);
            listView.setAdapter(adapter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void removeItem( int remove ){
        items.remove(remove);
        listView.setAdapter(adapter);
    }

    public static void addItem(String item){
        items.add(item);
        listView.setAdapter(adapter);
    }

    private void makeAToast(String s){
        if ( t != null) t.cancel();
        t = Toast.makeText(ShoppigList.this, s, Toast.LENGTH_SHORT);
        t.show();
    }
}
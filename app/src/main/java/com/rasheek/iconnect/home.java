package com.rasheek.iconnect;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.rasheek.iconnect.Model.Items;

import java.util.ArrayList;
import java.util.List;

public class home extends AppCompatActivity {

    GridView gridView;
    CustomAdapter customAdapter;
    int images[]= {R.drawable.apple12, R.drawable.apple11, R.drawable.applex, R.drawable.apple8, R.drawable.apple7, R.drawable.applese};
    String names[] = {"Iphone 12", "Iphone 11", "Iphone X", "Iphone 8", "Iphone 7", "Iphone SE"};
    String desc[] = {"LKR 245,000", "LKR 175,000", "LKR 132,000", "LKR 80,000", "LKR 65,000", "LKR 40,000"};
    Button news, tickets, logOut;

    List<Items> itemsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    gridView = findViewById(R.id.gridLayout);
        logOut = findViewById(R.id.logout_Btn);
        news = findViewById(R.id.news_Btn);
        tickets = findViewById(R.id.ticketsBtn);

    for(int i = 0; i < names.length; i++){
        Items itemModel = new Items(names[i], desc[i], images[i]);
        itemsList.add(itemModel);
    }

    customAdapter = new CustomAdapter(itemsList , this);
    gridView.setAdapter(customAdapter);


        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
                Intent loginIntent = new Intent(home.this, login.class);
                startActivity(loginIntent);
            }
        });

        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newsIntent = new Intent(home.this, news.class);
                startActivity(newsIntent);
            }
        });

    tickets.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent ticketIntent = new Intent(home.this, tickets.class);
            startActivity(ticketIntent);
        }
    });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.searchView);
        SearchView searchView =  (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                customAdapter.getFilter().filter(s);
                return true ;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.searchView){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void signOut() {
        // [START auth_fui_signout]
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
        // [END auth_fui_signout]
    }
}







class CustomAdapter extends BaseAdapter implements Filterable {

    private  List<Items> itemsList;
    private  List<Items> itemsListFiltered;
    private Context context;

    public CustomAdapter(List<Items> itemsList, Context context) {
        this.itemsList = itemsList;
        this.itemsListFiltered = itemsList ;
        this.context = context;
    }

    @Override
    public int getCount() {
        return itemsListFiltered.size() ;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_items, null);
        ImageView imageView = view.findViewById(R.id.imageView);
        TextView tvName = view.findViewById(R.id.tvname);
        TextView tvDesc = view.findViewById(R.id.tvdesc);

        imageView.setImageResource(itemsListFiltered.get(i).getImage());
        tvName.setText(itemsListFiltered.get(i).getName());
        tvDesc.setText(itemsListFiltered.get(i).getDesc());


        return view;
    }

    @Override
    public Filter getFilter() {
         Filter filter = new Filter() {
             @Override
             protected FilterResults performFiltering(CharSequence charSequence) {
                 FilterResults filterResults = new FilterResults();
                 if(charSequence == null || charSequence.length() == 0 ){
                     filterResults.count = itemsList.size();
                     filterResults.values = itemsList;

                 }else{
                     String searchStr = charSequence.toString().toLowerCase();
                      List<Items> resultData = new ArrayList<>();
                      for(Items items : itemsList){
                          if(items.getName().contains(searchStr)){
                              resultData.add(items);
                          }
                          filterResults.count = resultData.size();
                          filterResults.values = resultData;
                      }
                 }
                 return  filterResults ;

             }

             @Override
             protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
    itemsListFiltered = (List<Items>) filterResults.values;
    notifyDataSetChanged();

             }
         };
         return  filter;
    }


}
package com.example.haletest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    ListView list;
    ListViewAdapter adapter;
    SearchView editsearch;
    Businesses[] businesses;
    ArrayList<Businesses> arrayList = new ArrayList<Businesses>();
    public static final String BUS_DETAIL_KEY = "business";
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        firebaseAuth = FirebaseAuth.getInstance();

        businesses = new Businesses[3];
        businesses[0] = new Businesses("Hawaii Doggie Bakery",
                "We are Hawaii’s original bakery for dogs, founded in 1998, handcrafting " +
                        "fresh baked healthy innovative treat for dogs using quality local Hawaiian " +
                        "ingredients!\n", "https://i.imgur.com/YfOW14C.jpg", "2961 E Manoa Rd, Honolulu, HI 96822");
        businesses[1] = new Businesses("Purve Donut Shop",
                "Life Changing Donuts Made Fresh To Order!", "https://i.imgur.com/Jr15ebj.jpg",
                "1234 Kona St, Honolulu, HI 96814");
        businesses[2] = new Businesses("Lanikai Bath & Body",
                "Made fresh and all natural, Lanikai Bath and Body reflects the Hawaii of today, beautiful, light-hearted and cosmopolitan.",
                "https://i.imgur.com/QOIfPvJ.png",
                "600 Kailua Road. No. 119 Kailua, Hawaii 96734");

        list = (ListView) findViewById(R.id.listview);

        arrayList.add(businesses[0]);
        arrayList.add(businesses[1]);
        arrayList.add(businesses[2]);


        // Pass results to com.example.haletest.ListViewAdapter Class
        adapter = new ListViewAdapter(this, arrayList);
        adapter.checkIsFavorite(firebaseAuth.getUid());

        // Binds the Adapter to the ListView
        list.setAdapter(adapter);


        // Locate the EditText in listview_main.xml
        editsearch = (SearchView) findViewById(R.id.search);
        editsearch.setOnQueryTextListener(this);

        setupBusinessSelectedListener();

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.nav_person);

        // Perform item selected listener
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_home:
                        startActivity(new Intent(FavoriteActivity.this, HomeActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.nav_search:
                        startActivity(new Intent(FavoriteActivity.this, SearchActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.nav_person:
                        startActivity(new Intent(FavoriteActivity.this, ProfileActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

    }

    @Override
    public boolean onQueryTextSubmit (String query){

        return false;
    }

    @Override
    public boolean onQueryTextChange (String newText){
        String text = newText;
        adapter.filter(text);
        return false;
    }

    public void setupBusinessSelectedListener() {
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Launch the detail view passing book as an extra
                Intent intent = new Intent(FavoriteActivity.this, BusinessInfoPageActivity.class);
                intent.putExtra(BUS_DETAIL_KEY, adapter.getItem(position));
                startActivity(intent);
            }
        });
    }
}

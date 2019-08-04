package com.example.yummypoint;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yummypoint.Common.Common;
import com.example.yummypoint.Interface.ItemClickListener;
import com.example.yummypoint.Model.Category;
import com.example.yummypoint.Model.User;
import com.example.yummypoint.ViewHolder.MenuViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseDatabase database;
    DatabaseReference category;

    TextView txtFullName;
    TextView textView;

    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter <Category,MenuViewHolder> adapter;
    String isGuest = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);

        //Initialize Firebase

        database = FirebaseDatabase.getInstance();
        category = database.getReference("Category");


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                  //
                //     .setAction("Action", null).show();
            Intent intent = new Intent(Home.this , AddCart.class);
            startActivity(intent);

            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Set Name for User

        String UserName = getIntent().getStringExtra("UserName");

        if(UserName.equals("User")) {
            View headerView = navigationView.getHeaderView(0);
            txtFullName = headerView.findViewById(R.id.txtFullName);
            txtFullName.setText(Common.currentUser.getName());
            textView = headerView.findViewById(R.id.textView);
            textView.setText(Common.currentUser.getEmail());
        }else {
            View headerView = navigationView.getHeaderView(0);
            txtFullName = headerView.findViewById(R.id.txtFullName);
            txtFullName.setText(getIntent().getStringExtra("UserName"));
            isGuest = getIntent().getStringExtra("UserName");
            textView = headerView.findViewById(R.id.textView);
            textView.setText("");

        }
        //Load Menu
        recycler_menu = findViewById(R.id.recycler_view);
        recycler_menu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(layoutManager);


        loadMenu();


    }
private void loadMenu(){
        adapter= new FirebaseRecyclerAdapter<Category, MenuViewHolder>(Category.class,R.layout.menu_item,MenuViewHolder.class,category) {
            @Override
            protected void populateViewHolder(MenuViewHolder viewHolder, Category model, int position) {
           viewHolder.txtMenuName.setText(model.getName());
           Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.iv_items);
           //String image = model.getImage();
           final Category clickItem = model;
           viewHolder.setItemClickListener(new ItemClickListener() {
               @Override
               public void onClick(View view, int position, boolean isLongClick) {
                   //Get CategoryId
                   Intent item = new Intent(Home.this, ItemList.class);
                   item.putExtra("CategoryId", adapter.getRef(position).getKey());
                   startActivity(item);

               }
           });
            }
        };
        recycler_menu.setAdapter(adapter);



}
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_Account) {
            // Handle the camera action

            if(isGuest.equals("Guest")){
                Toast.makeText(this, "Please SignIn", Toast.LENGTH_SHORT).show();
            }else{
                startActivity(new Intent(Home.this, MyAccount.class));
            }

        } else if (id == R.id.nav_Order) {

        } else if (id == R.id.nav_logout) {
            startActivity(new Intent(Home.this, MainActivity.class));
        } else if (id == R.id.nav_rateApp) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps"));
            startActivity(intent);
        } else if (id == R.id.nav_shareApp) {
            Intent shareIntent =   new Intent(android.content.Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT,"Insert Subject here");
            String app_url = " https://play.google.com/store/apps/details?id=my.example.javatpoint";
            shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,app_url);
            startActivity(Intent.createChooser(shareIntent, "Share via"));
        } else if (id == R.id.nav_terms) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.yummypointbakery.com/services.php"));
            startActivity(intent);
        } else if (id == R.id.nav_about) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.yummypointbakery.com/about.php"));
            startActivity(intent);
        } else if(id == R.id.nav_Api){
            Intent intent = new Intent(Home.this, Users.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

package com.fire.scorecounter2;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.fire.scorecounter2.Utils.Constants;
import com.fire.scorecounter2.model.Challenge;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView mScoreRecycler;
    private FirebaseRecyclerAdapter<Challenge, ChallengeHolder> mScoreAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Challenge tempChallenge = new Challenge("bramch10@gmail,com", "Some Challenge", System.currentTimeMillis());
                FirebaseDatabase.getInstance().getReference(Constants.SCORES_KEY)
                        .setValue(tempChallenge);
            }
        });

        LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this);
        mScoreRecycler = (RecyclerView) findViewById(R.id.score_recycler);
        mScoreRecycler.setLayoutManager(manager);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    //Click 'CTRL' + 'o' to bring up a list of override methods

    //OnStart gets called after onCreate
    @Override
    protected void onStart() {
        super.onStart();
        setupAdapter();
    }

    private void setupAdapter() {
        mScoreAdapter = new FirebaseRecyclerAdapter<Challenge, ChallengeHolder>(
                Challenge.class,
                R.layout.item_layout_score,
                ChallengeHolder.class,
                FirebaseDatabase.getInstance().getReference(Constants.SCORES_KEY)
        ) {
            @Override
            protected void populateViewHolder(ChallengeHolder viewHolder, Challenge model, int position) {
                viewHolder.setTimeView(model.getTime());
                viewHolder.setChallenge(model.getChallenge());
            }
        };

        mScoreRecycler.setAdapter(mScoreAdapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * In this section, you will get(instantiate your views for each item
     * and set their values
     */
    public static class ChallengeHolder extends RecyclerView.ViewHolder {
        //Instantiating views
        CircleImageView userDisplay;
        TextView username;
        TextView timeView;
        TextView challengeView;

        public ChallengeHolder(View itemView) {
            super(itemView);

            userDisplay = (CircleImageView) itemView.findViewById(R.id.display_image);
            username = (TextView) itemView.findViewById(R.id.username);
            timeView = (TextView) itemView.findViewById(R.id.time_text);
            challengeView = (TextView) itemView.findViewById(R.id.challenge_text);
        }

        public void setUsername(String user) {
            username.setText(user);
        }

        public void setTimeView(long time) {
            timeView.setText(DateUtils.getRelativeTimeSpanString(time));
        }

        public void setChallenge(String challenge) {
            challengeView.setText(challenge);
        }
    }
}

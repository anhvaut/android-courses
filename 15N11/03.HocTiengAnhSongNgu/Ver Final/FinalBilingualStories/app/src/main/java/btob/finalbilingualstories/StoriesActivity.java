package btob.finalbilingualstories;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

import static btob.finalbilingualstories.R.id.lv;

public class StoriesActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    ListView listView;
    ArrayList<Story> mang, mangi;
//    ArrayList<HashMap> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stories2);

        listView = (ListView) findViewById(lv);

        mang = new ArrayList<>();
        mangi = new ArrayList<>();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new docJSON().execute("https://bilingualstories-fa7cb.firebaseio.com/.json");
            }
        });

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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        switch (id) {

            case R.id.nav_home: {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
            case R.id.nav_stories: {
                Intent intent2 = new Intent(getApplicationContext(), StoriesActivity.class);
                startActivity(intent2);
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return super.onOptionsItemSelected(item);
    }

    class docJSON extends AsyncTask<String, Integer, String>{

        @Override
        protected String doInBackground(String... strings) {
            return readContentFromURL(strings[0]);
        }

        @Override
        // Biến s là nội dung của JSON
        protected void onPostExecute(String s) {
            //Đọc JSON ở đây
//            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
            try {
                JSONArray mangjSON = new JSONArray(s);
                for(int i=0; i<mangjSON.length(); i++){
//                    HashMap<String, String> Hashmap = new HashMap<String, String>();
                    JSONObject sp = mangjSON.getJSONObject(i);

                    String iEngTitle = sp.getString("EngTitle");
                    String iVieTitle = sp.getString("VieTitle");
                    String iImage    = sp.getString("Image");
                    String iMP3      = sp.getString("MP3");
                    String iEngStory = sp.getString("EngStory");
                    String iVieStory = sp.getString("VieStory");

                    mang.add(new Story(iEngTitle,iVieTitle,iImage));
                    mangi.add(new Story(iEngTitle,iVieTitle,iImage,iMP3,iEngStory,iVieStory));

                }

                ListAdapter adapter = new ListAdapter(
                    getApplicationContext(),
                    R.layout.custom_layout_list,
                    mang
                );
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(StoriesActivity.this,DetailActivity.class);

                    Bundle bundle = new Bundle();

                    bundle.putString("engtitle",mangi.get(i).getEngTitle());
                    bundle.putString("vietitle",mangi.get(i).getVieTitle());
                    bundle.putString("image",mangi.get(i).getImage());
                    bundle.putString("mp3",mangi.get(i).getMP3());
                    bundle.putString("engstory",mangi.get(i).getEngStory());
                    bundle.putString("viestory",mangi.get(i).getVieStory());

                    intent.putExtra("Key", bundle);

                    startActivity(intent);
                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private String readContentFromURL(String theUrl){
        StringBuilder content = new StringBuilder();
        try    {
            // create a url object
            URL url = new URL(theUrl);

            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null){
                content.append(line + "\n");
            }
            bufferedReader.close();
        }
        catch(Exception e)    {
            e.printStackTrace();
        }
        return content.toString();
    }
}

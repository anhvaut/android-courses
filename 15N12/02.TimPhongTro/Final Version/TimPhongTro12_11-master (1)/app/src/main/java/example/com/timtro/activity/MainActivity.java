package example.com.timtro.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import example.com.timtro.R;

/**
 * Created by Administrator on 11/09/2017.
 */

public class MainActivity extends AppCompatActivity {

    private LoginButton loginButton;
    private CallbackManager callbackManager;
    String jsonData;
    private Button btnnext;
    public static  final String NAME="name";
    public static  final String PICTURE="picture";
    public static  final String CHECK="check";
    private SharedPreferences.Editor edit;
    SharedPreferences pre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_main);
        btnnext=(Button) findViewById(R.id.btnnext) ;
        loginfacebook();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private String message(Profile profile) {
        StringBuilder stringBuffer = new StringBuilder();
        if (profile != null) {
            stringBuffer.append("").append(profile.getName());
        }
        return stringBuffer.toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.search_view).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void loginfacebook(){
        pre = getSharedPreferences("my_data",MODE_PRIVATE);
        edit = pre.edit();
        boolean a=pre.getBoolean(CHECK,false);
        loginButton = (LoginButton) findViewById(R.id.btn_login_facebook);
        if(a==true)
        {
            loginButton.setVisibility(View.GONE);
            btnnext.setVisibility(View.GONE);
            Thread bamgio=new Thread(){
                public void run()
                {
                    try {
                        sleep(3000);
                    } catch (Exception e) {

                    }
                    finally
                    {
                        Intent intent = new Intent(MainActivity.this, MapActivity.class);
                        startActivity(intent);
                    }
                }
            };
            bamgio.start();
        }
        else
        {
            loginButton.setReadPermissions("user_friends");
            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(final LoginResult loginResult) {
                    GraphRequestAsyncTask graphRequestAsyncTask = new GraphRequest(loginResult.getAccessToken(),
                            "me/taggable_friends", null, HttpMethod.GET, new GraphRequest.Callback() {
                        @Override
                        public void onCompleted(GraphResponse response) {
                            try {
                                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                                String name = message(Profile.getCurrentProfile());
                                String urlProfile = "https://graph.facebook.com/" + loginResult.getAccessToken().getUserId() + "/picture?type=large";
                                edit.putString(NAME, name);
                                edit.putString(PICTURE, urlProfile);
                                edit.putBoolean(CHECK,true);
                                edit.commit();
                                startActivity(intent);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).executeAsync();
                }
                @Override
                public void onCancel() {
                    Toast.makeText(MainActivity.this,String.valueOf(R.string.login_cacel) , Toast.LENGTH_LONG).show();
                    String name = "Đăng nhập";
                    String urlProfile = "https://stocknews.com/wp-content/uploads/2017/06/facebook-fb-groups.png";
                    edit.putString(NAME, name);
                    edit.putString(PICTURE, urlProfile);
                    edit.putBoolean(CHECK,false);
                    edit.commit();
                   /* Intent intent = new Intent(MainActivity.this, ActivityMap.class);
                    startActivity(intent);*/
                }

                @Override
                public void onError(FacebookException e) {
                    Toast.makeText(MainActivity.this, String.valueOf(R.string.login_fail) +e, Toast.LENGTH_LONG).show();
                      Intent intent = new Intent(MainActivity.this, MapActivity.class);
                    String name = "Đăng nhập";
                    String urlProfile = "https://stocknews.com/wp-content/uploads/2017/06/facebook-fb-groups.png";
                    edit.putString(NAME, name);
                    edit.putString(PICTURE, urlProfile);
                    edit.putBoolean(CHECK,false);
                    edit.commit();
                     startActivity(intent);
                }
            });
        }
    }

    public void nextactivity(View view)
    {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setMessage(R.string.canh_bao_khong_dang_nhap);

        b.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                edit.putBoolean(CHECK,false);
                edit.putString(NAME, "Đăng nhập");
                edit.putString(PICTURE,"https://stocknews.com/wp-content/uploads/2017/06/facebook-fb-groups.png");
                edit.commit();
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });
/*        b.setPositiveButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });*/
        AlertDialog alertDialog = b.create();
        alertDialog.show();
    }
}


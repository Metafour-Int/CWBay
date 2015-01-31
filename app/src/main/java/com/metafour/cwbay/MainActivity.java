package com.metafour.cwbay;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.metafour.cwbay.remote.WebConnection;
import com.metafour.cwbay.ui.CategoryActivity;
import com.metafour.cwbay.ui.ProductDetailsActivity;
import com.metafour.cwbay.ui.ProductImagesActivity;
import com.metafour.cwbay.ui.ProfileUpdateActivity;
import com.metafour.cwbay.ui.SignInActivity;
import com.metafour.cwbay.util.Constants;


public class MainActivity extends AbstractCWBayActivity implements WebConnection.Callback {

    private Button btnSignIn;
    private Button btnSignUp;
    private Button btnCat;
    private Button btnCatG;
    private Button btnProfileUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
//        WebConnection.getInstance().connect(this, "http://192.168.20.102:8000");
        initialiseToolbar();

        btnSignIn = (Button)findViewById(R.id.mainBSignIn);
        btnSignUp = (Button)findViewById(R.id.mainBSignUp);
        btnCat = (Button)findViewById(R.id.mainBCat);
        btnCatG = (Button)findViewById(R.id.mainBCatG);
        btnProfileUpdate = (Button)findViewById(R.id.mainBProfileUpdate);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignInPage(v);
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignUpPage(v);
            }
        });
        btnCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCategoryPage(v);
            }
        });
        btnCatG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCategoryGridPage(v);
            }
        });
        btnProfileUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProfileUpdate(v);
            }
        });
    }

    @Override
    public void onResponse(WebConnection.Response response) {
        Log.i(Constants.ACTIVITY_LOG_TAG, response.getStatus() + " " + response.getContent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void openSignInPage(View v) {
        Log.i(Constants.ACTIVITY_LOG_TAG, "Going to open sign in page");
        openNextActivity(SignInActivity.class);
    }


    public void openSignUpPage(View v) {
        Log.i(Constants.ACTIVITY_LOG_TAG, "Going to open sign up page");
        openNextActivity(ProductImagesActivity.class);
    }

    public void openCategoryPage(View v) {
        Log.i(Constants.ACTIVITY_LOG_TAG, "Going to open category page");
        CategoryActivity.idToShow = 0;
        openNextActivity(CategoryActivity.class);
    }

    public void openCategoryGridPage(View v) {
        Log.i(Constants.ACTIVITY_LOG_TAG, "Going to open category grid page");
        openNextActivity(ProductDetailsActivity.class);
    }

    public void openLogOutPage(View v) {
        Log.i(Constants.ACTIVITY_LOG_TAG, "Going to logout from the app");
        openNextActivity(MainActivity.class);
    }

    public void openProfileUpdate(View v){
        Log.i(Constants.ACTIVITY_LOG_TAG, "Going to profile update");
        openNextActivity(ProfileUpdateActivity.class);
    }
}

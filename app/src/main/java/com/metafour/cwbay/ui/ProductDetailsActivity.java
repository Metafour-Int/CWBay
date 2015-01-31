package com.metafour.cwbay.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.metafour.cwbay.AbstractCWBayActivity;
import com.metafour.cwbay.R;
import com.metafour.cwbay.model.Ad;
import com.metafour.cwbay.remote.WebAPI;
import com.metafour.cwbay.util.Constants;
import com.metafour.cwbay.util.Utility;

import java.util.Map;

/**
 * Created by Noor on 1/31/2015.
 */
public class ProductDetailsActivity extends AbstractCWBayActivity {
    private String prodId = "test";
    private LinearLayout myGallery;
    private TextView detLoc;
    private TextView detPrice;
    private TextView detTitle;
    private TextView detDesc;
    private GridView detTypes;
    private Ad ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_details);

        initialiseToolbar();

        myGallery = (LinearLayout)findViewById(R.id.mygallery);
        detLoc = (TextView) findViewById(R.id.detLoc);
        detPrice = (TextView) findViewById(R.id.detPrice);
        detTitle = (TextView) findViewById(R.id.detTitle);
        detDesc = (TextView) findViewById(R.id.detDesc);
        detTypes = (GridView) findViewById(R.id.detTypes);
        if (getIntent().getExtras() != null) {
            prodId = getIntent().getExtras().getString("prod_id");

        }

        WebAPI.adDetails(this, new WebAPI.Callback<Ad>() {
            @Override
            public void onFailed(String reason) {
                Log.i(Constants.ACTIVITY_LOG_TAG, "Reason = " + reason);
                Utility.showShortLengthToast(ProductDetailsActivity.this, reason);
            }

            @Override
            public void onSuccess(Ad ad1) {
                Log.i(Constants.ACTIVITY_LOG_TAG, "Category list received successfully. " + ad1.toString());
                showAdDetails(ad1);
                ad = ad1;
            }
        }, prodId);
        myGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProductDetailsActivity.this, ProductImagesActivity.class);
                String[] images = new String[ad.getImages().size()];
                ad.getImages().toArray(images);
                i.putExtra("images_array", images);
                ProductDetailsActivity.this.startActivity(i);
                Utility.nextWithAnimation(ProductDetailsActivity.this);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Utility.backWithAnimation(this);
    }

    void showAdDetails(Ad ad) {
        for(String path : ad.getImages()) {
            myGallery.addView(Utility.getLayoutForImage(this, path, 220, 0));
        }
        detLoc.setText(ad.getPlace());
        detPrice.setText(String.format(Constants.PRICE_FORMAT, ad.getPrice()));
        detTitle.setText(ad.getTitle());
        detDesc.setText(ad.getDescription());
        if (ad.hasProperty()) {

            detTypes.setAdapter(new ArrayAdapter<Object>(this, android.R.layout.simple_list_item_1, android.R.id.text1, ad.getProperties().entrySet().toArray()) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                    Map.Entry<String, String> entry = (Map.Entry<String, String>) getItem(position);
                    text1.setText(entry.getKey() + ": " + entry.getValue());
                    return view;
                }
            });
        }
    }

    /*private Button shareBtn;

    shareBtn = (Button)findViewById(R.id.share_button);

    shareBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId()==R.id.share_button) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "http://dhakacity.olx.com.bd/yoyota-ist-model-04-rege-09-cc-1500-serial-25-colour-white-iid-779196215");
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Check out this site!");
                startActivity(Intent.createChooser(intent, "Share"));
            }
        }
    });*/
}

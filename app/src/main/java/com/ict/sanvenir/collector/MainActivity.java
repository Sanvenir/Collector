package com.ict.sanvenir.collector;

import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "Collector Activity";

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    private List<InformationContent> contentList = new ArrayList<>();
    protected ArrayAdapter<InformationContent> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button buttonToggle = findViewById(R.id.buttonToggle);
        final Switch switchPermission = findViewById(R.id.switchPermission);
        final ListView listInformation = findViewById(R.id.listInformation);

        buttonToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(switchPermission.isChecked()) {
                    // TODO: Grant permission from system
                }
                int successCount = collectInformation();
                Log.d(TAG, "Total success: " + successCount);

                setAdapter();
                listInformation.setAdapter(mAdapter);
                listInformation.setOnItemClickListener(itemClickListener);
            }
        });
    }

    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            showInformation(contentList.get(i));
        }
    };

    private void showInformation(InformationContent content) {
        Toast.makeText(this, content.getContent(), Toast.LENGTH_LONG).show();
        // TODO: Change this function to show information
    }

    private void setAdapter() {
        mAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, contentList);
        Log.d(TAG, contentList.toString());
    }

    /**
     * Collecting information of the device.
     * @return success time of collecting
     */
    private int collectInformation() {
        int successCount = 0;
        if(collectLocationProviders()) {
            successCount++;
        }
        return successCount;
    }

    /**
     * Collecting Location Provider of the device
     * @return true if success
     */
    private boolean collectLocationProviders() {
        InformationContent informationContent = new InformationContent("Location Provider");
        contentList.add(informationContent);
        LocationManager locationManager =
                (LocationManager) getSystemService(LOCATION_SERVICE);
        if(locationManager == null) {
            return false;
        }
        List<String> providers = locationManager.getProviders(true);
        if(providers.isEmpty()) {
            informationContent.setContent("No provider available");
            return true;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for(String provider : providers) {
            stringBuilder.append(provider);
            stringBuilder.append("\n");
        }
        informationContent.setContent(stringBuilder.toString());
        return true;
    }
}

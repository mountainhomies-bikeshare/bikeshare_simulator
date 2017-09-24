package com.example.praveer.simulator;

import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.hypertrack.lib.HyperTrack;
import com.hypertrack.lib.callbacks.HyperTrackCallback;
import com.hypertrack.lib.models.ErrorResponse;
import com.hypertrack.lib.models.SuccessResponse;
import com.hypertrack.lib.models.User;
import com.hypertrack.lib.models.UserParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    double currLong = 0;
    double currLat = 0;
    String userId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        HyperTrack.initialize(this.getApplicationContext(), "pk_54f2e023cd179babf0400dd029cbfff1463d4730");

        // To request Location Permissions
        HyperTrack.requestPermissions(this);

        // To enable Location Services
        HyperTrack.requestLocationServices(this);

        HyperTrack.enableMockLocations(true);

        final Button createUser = (Button) findViewById(R.id.createUser);
        createUser.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                UserParams userParams = new UserParams().setName("TheFinalChengHang6")
                        .setPhone("12345678")
                        .setLookupId("81dcf18f-e476-467e-8af7-5a6c315b439g");

                // This API will create a new user only if none exists already for the given lookup_id
                HyperTrack.getOrCreateUser(userParams, new HyperTrackCallback() {
                    @Override
                    public void onSuccess(@NonNull SuccessResponse successResponse) {
                        if (successResponse.getResponseObject() != null) {
                            User user = (User) successResponse.getResponseObject();
                            // Handle user_id, if needed
                            userId = user.getId();

                            System.out.println("User created successfully with UserId: " + userId);
                        }
                    }

                    @Override
                    public void onError(@NonNull ErrorResponse errorResponse) {
                        // Handle createUser error here
                        Toast.makeText(MainActivity.this, errorResponse.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        final Button startButton = (Button) findViewById(R.id.stop1);
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

//                try {
//                    getCurrentLocation();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }


                final LatLng startLatLng;

//                if (currLat == 0 && currLong == 0) {
                    startLatLng = new LatLng(37.3880037, -122.1425205);
//                } else {
//                    startLatLng = new LatLng(currLat, currLong);
//                }

                LatLng destLatLng = new LatLng(37.3825111,-122.1335267);

                HyperTrack.startMockTracking(startLatLng, destLatLng, new HyperTrackCallback() {
                    @Override
                    public void onSuccess(@NonNull SuccessResponse successResponse) {

                    }

                    @Override
                    public void onError(@NonNull ErrorResponse errorResponse) {
                        Toast.makeText(MainActivity.this, errorResponse.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

        final Button startButton2 = (Button) findViewById(R.id.stop2);
        startButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final LatLng startLatLng;
                startLatLng = new LatLng(37.3825111, -122.1335267);


                    LatLng destLatLng = new LatLng(37.3871733,-122.1324181);

                HyperTrack.startMockTracking(startLatLng, destLatLng, new HyperTrackCallback() {
                    @Override
                    public void onSuccess(@NonNull SuccessResponse successResponse) {

                    }

                    @Override
                    public void onError(@NonNull ErrorResponse errorResponse) {
                        Toast.makeText(MainActivity.this, errorResponse.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

        final Button startButton3 = (Button) findViewById(R.id.stop3);
        startButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final LatLng startLatLng;
                startLatLng = new LatLng(37.3871733, -122.1324181);


                LatLng destLatLng = new LatLng(37.3880037,-122.1425205);

                HyperTrack.startMockTracking(startLatLng, destLatLng, new HyperTrackCallback() {
                    @Override
                    public void onSuccess(@NonNull SuccessResponse successResponse) {

                    }

                    @Override
                    public void onError(@NonNull ErrorResponse errorResponse) {
                        Toast.makeText(MainActivity.this, errorResponse.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

        Button stopButton = (Button) findViewById(R.id.stopTracking);
        stopButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                HyperTrack.stopMockTracking();
                HyperTrack.stopTracking();
            }
        });


    }

    private void getCurrentLocation() throws IOException, JSONException {
        String url = "https://api.hypertrack.com/api/v1/users/" + userId + "/";

        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        con.setRequestProperty("Authorization", "token sk_0eeeb6548add587a5338ced6eaf6fab2b9df5364");

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        JSONObject json = new JSONObject(response.toString());

        //print result

        currLong = Double.parseDouble(json.getJSONObject("last_location").getJSONObject("geojson").getJSONArray("coordinates").get(0).toString());
        currLat = Double.parseDouble(json.getJSONObject("last_location").getJSONObject("geojson").getJSONArray("coordinates").get(1).toString());
    }


}

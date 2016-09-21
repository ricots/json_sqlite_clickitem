package com.appified.jsonparsingexample;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    ListView listView;
    KampusAdapter adapter;
    //ArrayList<City> cityArrayList;
    DBHandler handler;
    ImageView logo;
    private List<Kampus> kampusList;
    //    SharedPreferences sp;
//    SharedPreferences.Editor spe;
//    String url = "http://ricots.hol.es/copy_jurusan.php?id_kampus=";
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new DBHandler(this);
        mProgressDialog = new ProgressDialog(this);

        listView = (ListView) findViewById(R.id.listview);
        logo = (ImageView) findViewById(R.id.logo);


//        sp = getSharedPreferences("parameter", 0);
//        spe = sp.edit();


        if (isLoadAsync()) {
            new DataFetcherTask().execute();
            Log.d(getClass().getSimpleName(), "eksekusi Asyntask");
        } else {
            kampusList = handler.getAllCity();
            adapter = new KampusAdapter(this, kampusList);
            Log.d(getClass().getSimpleName(), "mengambil data dari DB");
            listView.setAdapter(adapter);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent in = new Intent(MainActivity.this, DetailKampus.class);
                //     in.putExtra("id", )
                //       Toast.makeText(MainActivity.this, position, Toast.LENGTH_SHORT).show();

                Log.d("onClick", kampusList.get((int) id).getName());
                String idKampus = kampusList.get((int) id).getName();
                String namaKampus = kampusList.get((int) id).getState();

                in.putExtra("namaKampus", namaKampus);
                in.putExtra("id", idKampus);

//                spe.putString("data", cityList.get((int) id).getName());
//                spe.apply();
                startActivity(in);
            }
        });
    }

    public boolean isLoadAsync() {
        NetworkUtils utils = new NetworkUtils(this);
        return handler.getCityCount() == 0 && utils.isConnectingToInternet();
    }

    class DataFetcherTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            mProgressDialog.setMessage("downloading data..");
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            JSONParser jsonParser = new JSONParser();
            String url = "http://ricots.hol.es/spiner.php";
            kampusList = new ArrayList<>();
            try {
                JSONObject jsonObject = jsonParser.makeHttpRequest(url);
                JSONArray jsonArray = jsonObject.getJSONArray("hasil");
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObjectCity = jsonArray.getJSONObject(i);
                    String cityName = jsonObjectCity.getString("id_kampus");
                    String cityState = jsonObjectCity.getString("nama_kampus");
                    String cityDescription = jsonObjectCity.getString("alamat_kampus");
                    String logonya = jsonObjectCity.getString("logo");

                    Kampus kampus = new Kampus();
                    kampus.setName(cityName);
                    kampus.setState(cityState);
                    kampus.setDescription(cityDescription);
                    kampus.setLogo(logonya);

                    kampusList.add(kampus);
                    handler.addCity(kampus);// Inserting into DB
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Json Parsing code end
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mProgressDialog.dismiss();

            adapter = new KampusAdapter(MainActivity.this, kampusList);
            listView.setAdapter(adapter);
        }
    }
}

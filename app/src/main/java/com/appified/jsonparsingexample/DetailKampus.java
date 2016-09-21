package com.appified.jsonparsingexample;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetailKampus extends AppCompatActivity {

    TextView title, jurusan;
    DBHandler handler;
    AdapterJurusan adapter_jur;
    ListView listView;
    ArrayList<Jurusan> jurList;
    String url = "http://ricots.hol.es/copy_jurusan.php?id_kampus=";
    String idKampus;
    String namaKampus;
    private ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kampus);
        mProgressDialog = new ProgressDialog(this);
        handler = new DBHandler(this);

        idKampus = getIntent().getStringExtra("id"); //menangkap data intent yang dikirim dari MainActivity
        namaKampus = getIntent().getStringExtra("namaKampus");

        title = (TextView) findViewById(R.id.tamp);
        listView = (ListView) findViewById(R.id.list_jurusan);

//        sp = this.getSharedPreferences("parameter", 0);
        title.setText(namaKampus);


        if (isLoadAsync()) {
            new DataFetcherTask().execute();
        } else {
            jurList = handler.getAlljur();
            adapter_jur = new AdapterJurusan(this, jurList);
            listView.setAdapter(adapter_jur);
            adapter_jur.notifyDataSetChanged();
            //listView.setAdapter(null);
        }
    }

    public boolean isLoadAsync() {
        NetworkUtils utils = new NetworkUtils(this);
        return handler.getjur() == 0 && utils.isConnectingToInternet();
    }

    class DataFetcherTask extends AsyncTask<Void, Void, Void> {
        @Override
        public void onPreExecute() {
            mProgressDialog.setMessage("downloading data..");
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();

        }

        @Override
        public Void doInBackground(Void... params) {
            JSONParser jsonParser = new JSONParser();

            try {
                jurList = new ArrayList<>();
                JSONObject jsonObject = jsonParser.makeHttpRequest(url+idKampus);
                JSONArray jsonArray = jsonObject.getJSONArray("jurusan");
                for (int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject jsonObjectCity = jsonArray.getJSONObject(i);
                    String jurusan = jsonObjectCity.getString("jurusan");
                    String id_kampusnya = jsonObjectCity.getString("id_kampus");
                    String namaKampus = jsonObjectCity.getString("nama_kampus");
                    Jurusan mJurusan = new Jurusan();
                    mJurusan.setJurusan(jurusan);
                    mJurusan.setIdkampus(id_kampusnya);
                    mJurusan.setNamaKampus(namaKampus);
                    jurList.add(mJurusan);
//                    handler.addjur(mJurusan);// Inserting into DB
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mProgressDialog.dismiss();

            adapter_jur = new AdapterJurusan(DetailKampus.this, jurList);
            listView.setAdapter(adapter_jur);
            adapter_jur.notifyDataSetChanged();
        }
    }
}
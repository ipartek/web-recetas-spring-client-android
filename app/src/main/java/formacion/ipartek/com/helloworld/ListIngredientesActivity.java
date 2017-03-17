package formacion.ipartek.com.helloworld;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.Exchanger;

public class ListIngredientesActivity extends AppCompatActivity {

    private static final String TAG = "ListIngredientesAct";
    Context context;
    ArrayList<Ingrediente> ingredientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_ingredientes);

        context = getApplicationContext();
        ingredientes = new ArrayList<Ingrediente>();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        getIngredientes();

    }
    //onCreate


    private void getIngredientes(){

        String url = MySingleton.END_POINT + "ingrediente/";
        Log.i(TAG, "GET: " + url);

        JsonArrayRequest request = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i(TAG,"Response: " + response.toString());
                        try{
                            parseJsonToArray(response);
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.toString() );

                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(request);


    }
    //getIngredientes


    private void parseJsonToArray(JSONArray response){

        JSONObject jsonObject;
        Ingrediente ingrediente;
        for ( int i=0; i < response.length(); i++ ){
            jsonObject = new JSONObject();
            ingrediente = new Ingrediente();
            try {
                jsonObject = response.getJSONObject(i);
                ingrediente.setId( jsonObject.getLong("id"));
                ingrediente.setNombre(jsonObject.getString("nombre"));
                ingrediente.setCantidad(jsonObject.getString("cantidad"));
                ingrediente.setGluten(jsonObject.getBoolean("gluten"));

                ingredientes.add(ingrediente);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        Log.i(TAG, "recuperados " + ingredientes.size() + " ingredientes");
        //for
    }
    //parseJsonToArray

}

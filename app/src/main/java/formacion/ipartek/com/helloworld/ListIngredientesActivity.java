package formacion.ipartek.com.helloworld;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
    ArrayList<String> listadoNombresIngredintes;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_ingredientes);

        context = getApplicationContext();

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
                            setTitle("Ingredientes: " + ingredientes.size() );
                            populateList();

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
        listadoNombresIngredintes = new ArrayList<String>();
        ingredientes = new ArrayList<Ingrediente>();

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
                listadoNombresIngredintes.add( "["+jsonObject.getLong("id")+"] " + jsonObject.getString("nombre"));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        Log.i(TAG, "recuperados " + ingredientes.size() + " ingredientes");
        //for
    }
    //parseJsonToArray


    private void populateList(){
        Log.i(TAG, "rellenando ListView");
        list = (ListView)findViewById(R.id.list_ingredientes);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                listadoNombresIngredintes
        );

        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String ingredientePulsado = listadoNombresIngredintes.get(position);
                Snackbar.make(view, ingredientePulsado , Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                //Llamar otra Activity
                Intent intent = new Intent( context , MainActivity.class );
                startActivity(intent);

            }
        });

    }
    //populateList

}

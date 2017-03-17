package formacion.ipartek.com.helloworld;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    Context context;
    //views
    TextView tvHello;
    EditText etNombre;
    Button boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        tvHello = (TextView)findViewById(R.id.tv_hello);
        etNombre = (EditText)findViewById(R.id.et_main);

        boton = (Button)findViewById(R.id.btn_main);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idIngrediente = etNombre.getText().toString();
                if ( "".equals(idIngrediente)){

                    CharSequence text = "No puedo consultar si no escribes";
                    Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
                    toast.show();

                }else{
                    tvHello.setText("Realizando consulta.....");
                    getIngredienteDetalle(idIngrediente);
                }
            }
        });
        //setOnClickListener

    }
    //onClick


    private void getIngredienteDetalle(String idIngrediente){

        String url = MySingleton.END_POINT + "ingrediente/" + idIngrediente + "/";
        Log.i(TAG, "GET: " + url);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG,"Response: " + response.toString());
                        try{
                            tvHello.setText( response.getString("nombre") );

                            //consultar si tiene gluten
                            boolean gluten = response.getBoolean("gluten");
                            ImageView imvGluten = (ImageView)findViewById(R.id.imv_gluten);
                            if ( !gluten ){
                                imvGluten.setImageResource(R.drawable.gluten_free);
                            }else{
                                imvGluten.setImageResource(R.drawable.gluten);
                            }


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
        MySingleton.getInstance(this).addToRequestQueue(jsObjRequest);


    }
    //getIngredienteDetalle

}

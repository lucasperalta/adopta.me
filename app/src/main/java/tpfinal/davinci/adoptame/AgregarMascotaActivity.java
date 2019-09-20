package tpfinal.davinci.adoptame;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.net.HttpURLConnection;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.EditText;
import android.net.Uri;
import java.io.InputStreamReader;
import java.io.OutputStream;
import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedWriter;
import java.util.Map;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.io.OutputStreamWriter;
import java.net.URL;
import android.provider.MediaStore;
import java.io.BufferedReader;
import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;
import android.util.Base64;

import androidx.appcompat.app.AppCompatActivity;

public class AgregarMascotaActivity extends AppCompatActivity {

    Button selectImageFromGallery,UploadImageToServer;

    ImageView ImageViewHolder;

    EditText imageName;

    ProgressDialog progressDialog ;

    private SharedPreferences sharedPreferences;



    Bitmap bitmap;

    boolean check = true;

    String GetImageNameFromEditText;

    String ImageNameFieldOnServer = "image_name" ;

    String ImagePathFieldOnServer = "image_path" ;

    String ImageUploadPathOnSever ="http://10.0.2.2/uploadImages/capture_img_upload_to_server.php" ;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_mascota);
        context=this.getApplicationContext();
        selectImageFromGallery = (Button)findViewById(R.id.buttonCapture);
        ImageViewHolder = (ImageView)findViewById(R.id.imageView);
        UploadImageToServer = (Button) findViewById(R.id.button2);
        imageName = (EditText)findViewById(R.id.editText);


        selectImageFromGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
            // Muestra solo imagens, sin videos ni nada
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                // Siempre muestra el selector  (si es que hay multiples selecciones)
                startActivityForResult(Intent.createChooser(intent, "Seleccione foto"), 7);

            }
        });

        //Cuando hacemos click en
        UploadImageToServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //obtenemos el nombre de la imagen
                GetImageNameFromEditText = imageName.getText().toString();
                ImageUploadToServerFunction();

            }
        });
    }

    // Una vez elegida la imagen la actividad vuelve a este metodo con el resultado (la iamgen seleccionada)
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //El request code es un codigo que ponemos para identificar la accion que hicimos,puede ser cualquier numero
        if (requestCode == 7 && resultCode == RESULT_OK  ) {

         Uri uri = data.getData();

        // la imagen seleccionada obtenemos el bitmap

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }

                // la mostramos en la pantalla
                ImageViewHolder.setImageBitmap(bitmap);

        }

    }



    // Subimos la imagen al servidor
    public void ImageUploadToServerFunction(){

        ByteArrayOutputStream byteArrayOutputStreamObject ;

        byteArrayOutputStreamObject = new ByteArrayOutputStream();

        // Convertimos la imagen a JPG, para que sean todas del mismo formato
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStreamObject);
        //la transformamos en un array de bytes para subirlas al servidor
        byte[] byteArrayVar = byteArrayOutputStreamObject.toByteArray();

        final String ConvertImage = Base64.encodeToString(byteArrayVar, Base64.DEFAULT);

        class AsyncTaskUploadClass extends AsyncTask<Void,Void,String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();

                // muestra un spinner
                progressDialog = ProgressDialog.show(AgregarMascotaActivity.this,"Subiendo imagen","Espere por favor",false,false);
            }

            //despues que subio la imagen
            @Override
            protected void onPostExecute(String string1) {

                super.onPostExecute(string1);

                // cerramos el spinner cuando termina de subir
                progressDialog.dismiss();

                // imprimismo el mensaje de exito que recibimos del servicor
                Toast.makeText(AgregarMascotaActivity.this,string1,Toast.LENGTH_LONG).show();

                // Setting image as transparent after done uploading.
                ImageViewHolder.setImageResource(android.R.color.transparent);
                imageName.setText("");
                Intent mismascotas = new Intent(context, ListarMisMascotasActivity.class);
                startActivity(mismascotas);


            }

            @Override
            protected String doInBackground(Void... params) {

                ImageProcessClass imageProcessClass = new ImageProcessClass();

                HashMap<String,String> HashMapParams = new HashMap<String,String>();
                //seteamos los parametros nombre de la imagen
                HashMapParams.put(ImageNameFieldOnServer, GetImageNameFromEditText);
                //ruta de la imagen, con la imagen convertida a un string
                HashMapParams.put(ImagePathFieldOnServer, ConvertImage);

                String FinalData = imageProcessClass.ImageHttpRequest(ImageUploadPathOnSever, HashMapParams);

                return FinalData;
            }
        }
        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();

        AsyncTaskUploadClassOBJ.execute();
    }

    //clase que procesa la imagen y la sube al servidor via httpConnection
    public class ImageProcessClass{

        public String ImageHttpRequest(String requestURL,HashMap<String, String> PData) {

            StringBuilder stringBuilder = new StringBuilder();

            try {

                URL url;
                HttpURLConnection httpURLConnectionObject ;
                OutputStream OutPutStream;
                BufferedWriter bufferedWriterObject ;
                BufferedReader bufferedReaderObject ;
                int RC ;

                url = new URL(requestURL);

                httpURLConnectionObject = (HttpURLConnection) url.openConnection();

                httpURLConnectionObject.setReadTimeout(19000);

                httpURLConnectionObject.setConnectTimeout(19000);

                httpURLConnectionObject.setRequestMethod("POST");

                httpURLConnectionObject.setDoInput(true);

                httpURLConnectionObject.setDoOutput(true);

                OutPutStream = httpURLConnectionObject.getOutputStream();

                bufferedWriterObject = new BufferedWriter(

                        new OutputStreamWriter(OutPutStream, "UTF-8"));

                bufferedWriterObject.write(bufferedWriterDataFN(PData));

                bufferedWriterObject.flush();

                bufferedWriterObject.close();

                OutPutStream.close();

                RC = httpURLConnectionObject.getResponseCode();

                if (RC == HttpsURLConnection.HTTP_OK) {

                    bufferedReaderObject = new BufferedReader(new InputStreamReader(httpURLConnectionObject.getInputStream()));

                    stringBuilder = new StringBuilder();

                    String RC2;

                    while ((RC2 = bufferedReaderObject.readLine()) != null){

                        stringBuilder.append(RC2);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        private String bufferedWriterDataFN(HashMap<String, String> HashMapParams) throws UnsupportedEncodingException {

            StringBuilder stringBuilderObject;

            stringBuilderObject = new StringBuilder();

            for (Map.Entry<String, String> KEY : HashMapParams.entrySet()) {

                if (check)

                    check = false;
                else
                    stringBuilderObject.append("&");

                stringBuilderObject.append(URLEncoder.encode(KEY.getKey(), "UTF-8"));

                stringBuilderObject.append("=");

                stringBuilderObject.append(URLEncoder.encode(KEY.getValue(), "UTF-8"));
            }

            return stringBuilderObject.toString();
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.actionFiltros:
                Intent filtros = new Intent(this, FiltrosActivity.class);
                startActivity(filtros);
                break;
            // action with ID action_settings was selected
            case R.id.actionMisMascotas:

                Intent mismascotas = new Intent(this, ListarMisMascotasActivity.class);
                startActivity(mismascotas);
                break;

            case R.id.actionAgregarMascotas:

                Toast.makeText(this, "Ya estas en agregar Mascotas", Toast.LENGTH_SHORT)
                        .show();
                break;
            case R.id.actionLogout:
                //persistencia resuelta con SharedPreferences
                sharedPreferences = this.getSharedPreferences(getResources().getString(R.string.app_name), MODE_PRIVATE);
                //Guardo asincronicamente las credenciales de logueo
                sharedPreferences.edit()
                        .putString("usuario", "")
                        .putString("password", "")
                        .apply();                Intent login = new Intent(this, MainActivity.class);
                startActivity(login);
                finish();
                break;

            default:
                break;
        }

        return true;
    }

}
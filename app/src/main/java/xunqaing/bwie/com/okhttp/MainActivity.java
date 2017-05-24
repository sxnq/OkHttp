package xunqaing.bwie.com.okhttp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends Activity {

    //线程池
    private ExecutorService executorService = Executors.newFixedThreadPool(3);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bt1 = (Button) findViewById(R.id.bt_get_sy);
        Button bt2 = (Button) findViewById(R.id.bt_get_asy);
        Button bt3 = (Button) findViewById(R.id.bt_post_sy);
        Button bt4 = (Button) findViewById(R.id.bt_post_asy);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Get_Synchronization();
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Get_Asynchronous();
            }
        });

        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Post_Synchronization();
            }
        });

        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Post_Asynchronous();
            }
        });
    }

    //get 同步
    public void Get_Synchronization() {

        executorService.execute(new Runnable() {
            @Override
            public void run() {

                try {

                    OkHttpClient client = new OkHttpClient();

                    Request requst = new Request.Builder().url("http://publicobject.com/helloworld.txt").build();

                    Response response = client.newCall(requst).execute();

                    System.out.println(response.body().string());

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    //get 异步
    public void Get_Asynchronous() {

        executorService.execute(new Runnable() {
            @Override
            public void run() {

                OkHttpClient client = new OkHttpClient();

                Request requst = new Request.Builder().url("http://publicobject.com/helloworld.txt").build();

                client.newCall(requst).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        System.out.println(response.body().string());
                    }
                });
            }
        });
    }

    String url = "http://qhb.2dyt.com/Bwei/login";

    //post 同步
    public void Post_Synchronization() {

        executorService.execute(new Runnable() {
            @Override
            public void run() {

                try {

                    OkHttpClient client = new OkHttpClient();

                    RequestBody requestBody = new FormBody.Builder().add("username", "123456789").add("password", "123456").add("postkey", "1503d").build();

                    Request requst = new Request.Builder().url("http://qhb.2dyt.com/Bwei/login").post(requestBody).build();

                    Response response = client.newCall(requst).execute();

                    System.out.println("=======================" + response.body().string());


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    //post 异步
    public void Post_Asynchronous() {

        executorService.execute(new Runnable() {
            @Override
            public void run() {

                OkHttpClient okHttpClient = new OkHttpClient();

                RequestBody requestBody = new FormBody.Builder().add("username", "123456789").add("password", "123456").add("postkey", "1503d").build();

                Request request = new Request.Builder().url(url).post(requestBody).build();

                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        System.out.println("=====" + response.body().string());

                    }
                });


            }
        });


    }

}

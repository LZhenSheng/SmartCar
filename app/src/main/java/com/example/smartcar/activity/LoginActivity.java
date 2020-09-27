package com.example.smartcar.activity;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;

import com.example.smartcar.activity.base.BaseActivity;
import com.example.smartcar.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    private Handler mMainHandler;

    // Socket变量
    private Socket socket;

    // 线程池
    // 为了方便展示,此处直接采用线程池进行线程管理,而没有一个个开线程
    private ExecutorService mThreadPool;

    /**
     * 接收服务器消息 变量
     */
    // 输入流对象
    InputStream is;

    // 输入流读取器对象
    InputStreamReader isr ;
    BufferedReader br ;

    // 接收服务器发送过来的消息
    String response;


    /**
     * 发送消息到服务器 变量
     */
    // 输出流对象
    OutputStream outputStream;

    /**
     * 按钮 变量
     */

    // 连接 断开连接 发送数据到服务器 的按钮变量
    private Button btnConnect, btnDisconnect, btnSend;

    @BindView(R.id.login)
    Button btn_login;

    @BindView(R.id.account)
    EditText account;
    @BindView(R.id.password)
    EditText password;

    @OnClick(R.id.login)
    public void OnClick(){
        if(!TextUtils.isEmpty(account.getText().toString())&&!TextUtils.isEmpty(password.getText().toString())){
            mMainHandler=new Handler(){
                @Override
                public void handleMessage(@NonNull Message msg) {
                    switch (msg.what){
                        case 0:
                            System.out.println(111);
                            Toast.makeText(LoginActivity.this, "1", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            };
            mThreadPool= Executors.newCachedThreadPool();
            mThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try{
                        socket=new Socket("120.79.58.77",5567);
                        System.out.println(socket.isConnected());
                        outputStream=socket.getOutputStream();
                        String result=getResult(2,account.getText().toString().trim()+password.getText().toString().trim(),0);
                        System.out.println(result);
                        System.out.println(result.getBytes().toString());
                        outputStream.write(result.getBytes("UTF-8"));
                        socket.shutdownOutput();
                        is=socket.getInputStream();
                        int len;
                        StringBuilder sb=new StringBuilder();
                        byte[] bytes=new byte[1024];
                        while((len=is.read(bytes))!=-1){
                            sb.append(new String(bytes,0,len,"UTF-8"));
                        }
                        System.out.println(sb+" 23");
                        is.close();
                        outputStream.close();
                        socket.close();
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }
            });
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }


    /*第一个参数为命令类型
     * 第二个参数为用户名+密码    第三个参数为数据区长度
     */
    public static String getResult(int a, String b, int c) {

        String res = a + b + c;
        int resultAsc = stringToAscii(res) % 256;

        String result1 = String.valueOf(resultAsc);
        String result2 = asciiToString(result1);
        String Result = "0x230x0" + res + result2 + "0x24"; // 拼接最后的结果
        return Result;

    }

    /**
     * 字符串转换为Ascii
     *
     * @param value
     */
    public static int stringToAscii(String value) {
        int result = 0;
        StringBuffer sbu = new StringBuffer();
        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (i != chars.length - 1) {
                sbu.append((int) chars[i]).append(" ");
                int y = (int) chars[i];
                result += y;
            } else {
                sbu.append((int) chars[i]);
                int x = (int) chars[i];
                result += x;
            }
        }
        return result;

    }

    /**
     * Ascii转换为字符串
     *
     * @param value
     */
    public static String asciiToString(String value) {
        StringBuffer sbu = new StringBuffer();
        String[] chars = value.split(",");
        for (int i = 0; i < chars.length; i++) {
            sbu.append((char) Integer.parseInt(chars[i]));
        }
        return sbu.toString();
    }
}

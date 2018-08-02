package app.robo.com.roboapp;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.blockly.android.AbstractBlocklyActivity;
import com.google.blockly.android.codegen.CodeGenerationRequest;
import com.google.blockly.model.DefaultBlocks;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import static java.lang.System.in;

/**
 * Simplest implementation of AbstractBlocklyActivity.
 */
public class MainActivity extends AbstractBlocklyActivity {

    //JsEvaluator jsEvaluator = new JsEvaluator(this);

    private static final String TAG = "SimpleActivity";

    //WIFI Code start from here:
    private Socket socket;

    private static final int SERVERPORT = 8888;
    private static final String SERVER_IP = "192.168.4.1";

    private static final String SAVE_FILENAME = "simple_workspace.xml";
    private static final String AUTOSAVE_FILENAME = "simple_workspace_temp.xml";


    String read = "Status code = 1";
    String str;
    String send;

    int num;
    String[] ad;
    PrintWriter out;
    // Add custom blocks to this list.
    private static final List<String> BLOCK_DEFINITIONS =Arrays.asList(
            DefaultBlocks.LOGIC_BLOCKS_PATH,
            "blocks/block_definition.json"
    );
    private static final List<String> JAVASCRIPT_GENERATORS = Arrays.asList(
            // Custom block generators go here. Default blocks are already included.

            "blocks/code_generator.js"

    );
   // private final Handler mHandler = new Handler();





    // Client thread to get IP and Port No. of server
    class ClientThread implements Runnable {

        public void run() {
            try

            {

                    InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
                    socket = new Socket(serverAddr, SERVERPORT);




            } catch (
                    UnknownHostException e1)

            {
                e1.printStackTrace();
            } catch (
                    IOException e1)

            {
                e1.printStackTrace();
            }

        }
    }

    private final CodeGenerationRequest.CodeGeneratorCallback mCodeGeneratorCallback =
            new CodeGenerationRequest.CodeGeneratorCallback() {
                @Override
                public void onFinishCodeGeneration(final String generatedCode) {
                    //Toast.makeText(getApplicationContext(), generatedCode,Toast.LENGTH_SHORT).show();


                    try{


                            str = generatedCode.toString();

                            //Toast.makeText(MainActivity.this, str, Toast.LENGTH_LONG).show();
                       out = new PrintWriter(new BufferedWriter(
                                    new OutputStreamWriter(socket.getOutputStream())),
                                    true);
                            ad = str.split("=");
                      num = 1;
                            if(ad[0].contains("func 6 ,")){

                                 String[] nad = ad[0].split(",");
                                String mysz2 = nad[1].replaceAll("\\s","");
                                 num = Integer.parseInt(mysz2);
                            }

                        Thread thread = new Thread() {

                            @Override
                            public void run() {
                                try {
                                    if(read == "Status code = 1" ) {

                                        //read = "";
                                        //Toast.makeText(getApplicationContext(), String.valueOf(send), Toast.LENGTH_SHORT).show();
                                        for (int j=1;j<=num;j++)
                                        {
                                            for(int i=0;i<ad.length;i++)
                                            {
                                                read ="";
                                                if(ad[i].contains("func 6 ,")){
                                                    continue;
                                                }
                                                send = ad[i];
                                                out.println(send);
                                                new Thread(new MainActivity.ReceiveThread()).start();
                                                String aa= read;
                                                sleep(10000);
                                                if(!read.equals("Status code = 1")){
                                                    break;
                                                }

                                                //sleep(10000);
                                            }

                                        }
                                    }


                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        };

                        thread.start();
                        read = "Status code = 1";

                        /*int ind0 = 0;
                        int ind1 = str.indexOf('=');

                        send = str.substring(ind0,ind1);
                        Toast.makeText(getApplicationContext(),send,Toast.LENGTH_LONG).show();
                        while(read == "status code = 1" ) {

                            read = "";
                            //Toast.makeText(getApplicationContext(), String.valueOf(send), Toast.LENGTH_SHORT).show();
                            out.println(send);
                            new Thread(new ReceiveThread()).start();
                            String aa= read;
                                ind0 = ind1+1;
                                ind1= str.indexOf('=',ind0);
                                Log.d("key",ind1+"----");
                                send = str.substring(ind0,ind1);
                        }*/





                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }

 /*                 mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            jsEvaluator.evaluate(generatedCode, new JsCallback() {
                                @Override
                                public void onResult(String result) {
                                    // Process result here.
                                    // This method is called in the UI thread.
                                   // Toast.makeText(getApplicationContext(),result.toString(),Toast.LENGTH_LONG).show();




                                }

                                @Override
                                public void onError(String errorMessage) {
                                    // Process JavaScript error here.
                                    // This method is called in the UI thread.
                                    Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    });
*/
                }
            };


    /*CodeGenerationRequest.CodeGeneratorCallback mCodeGeneratorCallback =
            new LoggingCodeGeneratorCallback(this, TAG);*/




    class ReceiveThread implements Runnable{


        public void run() {
            try{

                Looper.prepare();

                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));


                read = String.valueOf(in.readLine());

                Toast.makeText(getApplicationContext(), String.valueOf(read), Toast.LENGTH_LONG).show();
                System.out.print(read);
              //  socket.close();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    @NonNull
    @Override
    protected List<String> getBlockDefinitionsJsonPaths() {
        return BLOCK_DEFINITIONS;
    }

    @NonNull
    @Override
    protected String getToolboxContentsXmlPath() {
        // Replace with a toolbox that includes application specific blocks.
        new Thread(new ClientThread()).start();

        return "blocks/toolbox.xml";
    }

    @NonNull
    @Override
    protected List<String> getGeneratorsJsPaths() {
        return JAVASCRIPT_GENERATORS;
    }

    @NonNull
    @Override
    protected CodeGenerationRequest.CodeGeneratorCallback getCodeGenerationCallback() {
        // Uses the same callback for every generation call.
        return mCodeGeneratorCallback;
    }

    /**
     * Optional override of the save path, since this demo Activity has multiple Blockly
     * configurations.
     * @return Workspace save path used by SimpleActivity and SimpleFragment.
     */
    @Override
    @NonNull
    protected String getWorkspaceSavePath() {

        return SAVE_FILENAME;
    }

    /**
     * Optional override of the auto-save path, since this demo Activity has multiple Blockly
     * configurations.
     * @return Workspace auto-save path used by SimpleActivity and SimpleFragment.
     */
    @Override
    @NonNull
    protected String getWorkspaceAutosavePath() {
        return AUTOSAVE_FILENAME;
    }
}

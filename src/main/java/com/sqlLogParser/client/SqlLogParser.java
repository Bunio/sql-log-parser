package com.sqlLogParser.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.sqlLogParser.client.rpc.MyService;
import com.sqlLogParser.client.rpc.MyServiceAsync;
import com.sqlLogParser.client.rpc.fileReader.FileReaderService;
import com.sqlLogParser.client.rpc.fileReader.FileReaderServiceAsync;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class SqlLogParser implements EntryPoint {

    /**
     * This is the entry point method.
     */


    public void onModuleLoad()
    {

        TextBox filenameTf = new TextBox();
        TextBox result = new TextBox();
        Button loadFileBt = new Button("load file");


        loadFileBt.addClickHandler(e ->
        {
            FileReaderServiceAsync fileReader = GWT.create(FileReaderService.class);

            fileReader.fileToString(filenameTf.toString(), new AsyncCallback<String>()
            {
                @Override
                public void onFailure(Throwable throwable)
                {
                    Window.alert("FAILURE");
                }

                @Override
                public void onSuccess(String s)
                {

                    Window.alert("SUCESS. RESULT: " + s);
                }
            });

        });

        RootPanel.get("slot1").add(filenameTf);
        RootPanel.get("slot2").add(loadFileBt);

    }

}

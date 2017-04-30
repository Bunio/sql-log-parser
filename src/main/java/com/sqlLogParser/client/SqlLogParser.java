package com.sqlLogParser.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.sqlLogParser.client.rpc.fileReader.FileReaderService;
import com.sqlLogParser.client.rpc.fileReader.FileReaderServiceAsync;
import com.sqlLogParser.shared.logs.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class SqlLogParser implements EntryPoint {

    /**
     * This is the entry point method.
     */

    private static Logger logger = Logger.getLogger("FILE READER");

    public void onModuleLoad()
    {

        TextBox filenameTf = new TextBox();
        TextBox result = new TextBox();
        Button loadFileBt = new Button("load file");


        loadFileBt.addClickHandler(e ->
        {
            FileReaderServiceAsync fileReader = GWT.create(FileReaderService.class);

            fileReader.getLogsFromFile(filenameTf.getText(), new AsyncCallback<List<Log>>()
            {
                @Override
                public void onFailure(Throwable throwable)
                {
                    Window.alert("FAILURE");
                }

                @Override
                public void onSuccess(List<Log> logs)
                {
                }
            });

        });

        RootPanel.get("slot1").add(filenameTf);
        RootPanel.get("slot2").add(loadFileBt);

    }

}

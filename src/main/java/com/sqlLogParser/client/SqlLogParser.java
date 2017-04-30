package com.sqlLogParser.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.SingleSelectionModel;
import com.sqlLogParser.client.rpc.fileReader.FileReaderService;
import com.sqlLogParser.client.rpc.fileReader.FileReaderServiceAsync;
import com.sqlLogParser.shared.logs.Log;
import com.sqlLogParser.server.rpc.logs.LogParser;

import java.util.List;
import java.util.logging.Logger;


public class SqlLogParser implements EntryPoint {

    private static Logger logger = Logger.getLogger("FILE READER");

    private Log selectedLog;
    private TextBox filenameTf;
    private Button loadFileBt;


    public void onModuleLoad()
    {
        addTextfields();
        addButtons();
    }

    private void addTextfields()
    {
        filenameTf = new TextBox();
        RootPanel.get("slot1").add(filenameTf);
    }

    private void addButtons()
    {
        loadFileBt = new Button("load file");

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
                    displayLogTable(logs);
                }
            });

        });

        RootPanel.get("slot2").add(loadFileBt);
    }

    public void displayLogTable(List<Log> logs)
    {
        CellTable<Log> cellTable = new CellTable<>();
        cellTable.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.ENABLED);

        TextColumn<Log> indexColumn = new TextColumn<Log>() {
            @Override
            public String getValue(Log log) {
                return Long.toString(log.getId());
            }
        };

        TextColumn<Log> logColumn = new TextColumn<Log>() {
            @Override
            public String getValue(Log log) {
                return log.getContent();
            }
        };

        cellTable.addColumn(indexColumn, "id");
        cellTable.addColumn(logColumn, "log");

        SingleSelectionModel<Log> selectionModel = new SingleSelectionModel<>();
        cellTable.setSelectionModel(selectionModel);

        selectionModel.addSelectionChangeHandler(e ->
        {
            selectedLog = selectionModel.getSelectedObject();

            if(selectedLog != null)
            {
                displayParsedLog(selectedLog);
            }

        });

        cellTable.setRowCount(logs.size());
        cellTable.setRowData(logs);

        VerticalPanel panel = new VerticalPanel();
        panel.setBorderWidth(1);
        panel.setWidth("100%");
        panel.add(cellTable);

        RootPanel.get().add(panel);
    }

    private void displayParsedLog(Log selectedLog)
    {
        FileReaderServiceAsync fileReader = GWT.create(FileReaderService.class);
        fileReader.parseLog(selectedLog, new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert("COULD NOT PARSE THIS LOG");
            }

            @Override
            public void onSuccess(String s)
            {
                Window.alert(s);
            }
        });
    }

}

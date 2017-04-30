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
import java.util.List;
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
                       Log selected = selectionModel.getSelectedObject();

                       if(selected != null)
                       {
                           Window.alert("LOG " + selected.getContent());
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
            });

        });

        RootPanel.get("slot1").add(filenameTf);
        RootPanel.get("slot2").add(loadFileBt);

    }

}

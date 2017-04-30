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
import com.sqlLogParser.client.widgets.LogDialog;
import com.sqlLogParser.shared.logs.Log;

import java.util.List;
import java.util.logging.Logger;


public class SqlLogParser implements EntryPoint {

    private static Logger logger = Logger.getLogger("FILE READER");

    private Log selectedLog;
    private TextBox fileNameTextBox;
    private Button loadFileButton;
    private Button parseLogButton;
    private CellTable<Log> logTable;
    private VerticalPanel menuPanel;
    private VerticalPanel logPanel;


    public void onModuleLoad()
    {
        createMenu();

    }

    private void createMenu()
    {
        addMenuPanel();
        addLogPanel();
        addTextfields();
        addButtons();
    }

    private void addMenuPanel()
    {
        menuPanel = new VerticalPanel();
        menuPanel.setWidth("100%");
        menuPanel.setSpacing(10);
        menuPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

        RootPanel.get().add(menuPanel);
    }

    private void addLogPanel()
    {
        logPanel = new VerticalPanel();
        logPanel.setWidth("100%");
        logPanel.setBorderWidth(1);

        RootPanel.get().add(logPanel);
    }


    private void addTextfields()
    {
        fileNameTextBox = new TextBox();
        menuPanel.add(fileNameTextBox);
    }

    private void addButtons()
    {
        loadFileButton = new Button("Load file");
        parseLogButton = new Button("Parse");

        parseLogButton.addClickHandler(e ->
        {
           displayParsedLog();
        });

        loadFileButton.addClickHandler(e ->
        {
            FileReaderServiceAsync fileReader = GWT.create(FileReaderService.class);
            fileReader.getLogsFromFile(fileNameTextBox.getText(), new AsyncCallback<List<Log>>()
            {
                @Override
                public void onFailure(Throwable throwable)
                {
                    Window.alert("FAILURE");
                }

                @Override
                public void onSuccess(List<Log> logs)
                {
                    if(logs.size() > 0)
                    {
                        displayLogTable(logs);
                    }
                    else
                    {
                        Window.alert("I could not load any logs. Wrong file perhaps?");
                    }

                }
            });

        });

        menuPanel.add(loadFileButton);
        menuPanel.add(parseLogButton);
    }

    public void displayLogTable(List<Log> logs)
    {

        if(logTable != null && logPanel.getWidgetIndex(logTable) >= 0)
        {
            logPanel.remove(logTable);
        }

        logTable = new CellTable<>();
        logTable.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.ENABLED);

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

        logTable.addColumn(indexColumn, "id");
        logTable.addColumn(logColumn, "log");

        SingleSelectionModel<Log> selectionModel = new SingleSelectionModel<>();
        logTable.setSelectionModel(selectionModel);

        selectionModel.addSelectionChangeHandler(e ->
                selectedLog = selectionModel.getSelectedObject()
        );

        logTable.setRowCount(logs.size());
        logTable.setRowData(logs);

        logPanel.add(logTable);
    }

    private void displayParsedLog()
    {

        if(selectedLog == null)
        {
            Window.alert("No log selected!");
        }

        else
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

                    LogDialog myDialog = new LogDialog(s);

                    int left = Window.getClientWidth()/ 2;
                    int top = Window.getClientHeight()/ 2;
                    myDialog.setPopupPosition(left, top);

                    myDialog.show();
                }
            });
        }

    }

}

package com.mySampleApplication.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;


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
        Button loadFileBt = new Button("load file");

        loadFileBt.addClickHandler(e ->
        {
            MyServiceAsync myServiceAsync = (MyServiceAsync) GWT.create(MyService.class);

            myServiceAsync.myMethod("Jacob", new AsyncCallback<String>() {
                @Override
                public void onFailure(Throwable throwable) {
                    Window.alert("Whoops");
                }

                @Override
                public void onSuccess(String s) {
                    filenameTf.setText(s);
                }
            });




        });





        RootPanel.get("slot1").add(filenameTf);
        RootPanel.get("slot2").add(loadFileBt);

    }

}

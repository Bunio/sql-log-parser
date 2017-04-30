package com.sqlLogParser.client.widgets;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;

/**
 * Created by Jakub on 30.04.2017.
 */
public class LogDialog extends DialogBox
{
    public LogDialog(String log)
    {

        setText("LOG");
        setAnimationEnabled(true);
        //setGlassEnabled(true);

        Button okButton = new Button("OK");

        okButton.addClickHandler(handler ->  hide());

        Label label = new Label(log);

        VerticalPanel panel = new VerticalPanel();
            panel.setHeight("100");
            panel.setWidth("600");
            panel.setSpacing(10);
            panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_JUSTIFY);
            panel.add(label);
            panel.add(okButton);

        setWidget(panel);
    }
}

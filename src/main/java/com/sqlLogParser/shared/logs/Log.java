package com.sqlLogParser.shared.logs;

import com.google.gwt.user.client.rpc.IsSerializable;
/**
 * Created by Jakub on 30.04.2017.
 */
public class Log implements IsSerializable
{
    private long Id;
    private String content;


    public Log() {}

    public Log(long id, String content) {
        Id = id;
        this.content = content;
    }


    public long getId() {
        return Id;
    }
    public String getContent() {
        return content;
    }
}

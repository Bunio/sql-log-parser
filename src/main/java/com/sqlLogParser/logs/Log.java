package com.sqlLogParser.logs;

/**
 * Created by Jakub on 30.04.2017.
 */
public class Log
{
    private long Id;
    private String content;

    public Log(long id, String content) {
        Id = id;
        this.content = content;
    }
}

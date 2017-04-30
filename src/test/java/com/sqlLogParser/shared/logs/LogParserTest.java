package com.sqlLogParser.shared.logs;

import com.sqlLogParser.server.rpc.logs.LogParser;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by Jakub on 30.04.2017.
 */

public class LogParserTest
{

    @Test
    public void getParamsFrom() throws Exception
    {

        Log log1 = new Log(
                0,
                "289968  TRACE  [btpool0-0] kodo.jdbc.SQL blablabla ? [params=(long) 1300053803]"
        );

        List<String> log1paramsExpected = Arrays.asList("1300053803");
        List<String> log1paramsActual = LogParser.getParamsFrom(log1);

        Assert.assertTrue(log1paramsActual.size() == log1paramsExpected.size());
        Assert.assertTrue(log1paramsActual.containsAll(log1paramsExpected));



        Log log2 = new Log(
                0,
                "DELETE FROM TESTSOFT.TB_RESOURCE WHERE ID = ? AND LAST_UPDATE = ? [params=(long) 1328126930, (int) 0]"
        );

        List<String> log2paramsExpected = Arrays.asList("1328126930", "0");
        List<String> log2paramsActual = LogParser.getParamsFrom(log2);

        Assert.assertTrue(log2paramsActual.size() == log2paramsExpected.size());
        Assert.assertTrue(log2paramsActual.containsAll(log2paramsExpected));

    }

    @Test
    public void cutParamsFrom() throws Exception
    {

        String log;
        String expected;
        String actual;


        log = "DELETE FROM TESTSOFT.TB_RESOURCE WHERE ID = ? AND LAST_UPDATE = ? [params=(long) 1328126930, (int) 0]";
        expected = "DELETE FROM TESTSOFT.TB_RESOURCE WHERE ID = ? AND LAST_UPDATE = ? ";
        actual = LogParser.cutParamsFrom(log);
        Assert.assertEquals(expected,actual);

        log = "? [params=(long) 1300053803]\"";
        expected = "? ";
        actual = LogParser.cutParamsFrom(log);
        Assert.assertEquals(expected,actual);

        log = "?? what a nice test";
        expected = "?? what a nice test";
        actual = LogParser.cutParamsFrom(log);
        Assert.assertEquals(expected,actual);

    }

    @Test
    public void trimToQuery() throws Exception
    {

        String log;
        String expected;
        String actual;


        log = "bla bla bla DELETE FROM TESTSOFT.TB_RESOURCE WHERE ID = ? AND LAST_UPDATE = ? [params=(long) 1328126930, (int) 0]";
        expected = "DELETE FROM TESTSOFT.TB_RESOURCE WHERE ID = ? AND LAST_UPDATE = ? [params=(long) 1328126930, (int) 0]";
        actual = LogParser.trimToQuery(log);
        Assert.assertEquals(expected, actual);


        log = "290062  TRACE  [btpool0-0] kodo.jdbc.SQL - <t 10272075, conn 18292272> [28 ms] executing prepstmnt 29574501 SELECT t0.A FROM TESTSOFT.TB_NUMBERING_RANGE t0 WHERE t0.ID = ? [params=(long) 1300053803]";
        expected = "SELECT t0.A FROM TESTSOFT.TB_NUMBERING_RANGE t0 WHERE t0.ID = ? [params=(long) 1300053803]";
        actual = LogParser.trimToQuery(log);
        Assert.assertEquals(expected, actual);

        log = "293698  TRACE  [btpool0-0] kodo.jdbc.SQL - <t 10272075, conn 18292272> [0 ms] batching prepstmnt 6104070 UPDATE TESTSOFT.TB_SERVICES SET NUMBER_ID = ? WHERE NUMBER_ID = ? [params=(null) null, (long) 1328126939]";
        expected = "UPDATE TESTSOFT.TB_SERVICES SET NUMBER_ID = ? WHERE NUMBER_ID = ? [params=(null) null, (long) 1328126939]";
        actual = LogParser.trimToQuery(log);
        Assert.assertEquals(expected, actual);

        log = "Random text 13123123";
        expected = "Random text 13123123";
        actual = LogParser.trimToQuery(log);
        Assert.assertEquals(expected, actual);

    }

    @Test
    public void parseQuery() throws Exception
    {
        Log log;
        String logBody;
        String expected;
        String actual;


        logBody = "290062  TRACE  [btpool0-0] kodo.jdbc.SQL - <t 10272075, conn 18292272> [28 ms] executing prepstmnt 29574501 SELECT t0.A FROM TESTSOFT.TB_NUMBERING_RANGE t0 WHERE t0.ID = ? [params=(long) 1300053803]";
        expected = "SELECT t0.A FROM TESTSOFT.TB_NUMBERING_RANGE t0 WHERE t0.ID = '1300053803'";
        actual = LogParser.parseQuery(new Log(0, logBody));

        Assert.assertEquals(expected, actual);



    }

    @Test
    public void replacePlaceholdersWithParams() throws Exception
    {
        Log log1 = new Log(
            0,
            " ? [params=(long) 1300053803]"
        );


        List<String> log1params= LogParser.getParamsFrom(log1);

        Assert.assertEquals(
                " '1300053803' [params=(long) 1300053803]",
                LogParser.replacePlaceholdersWithParams(log1.getContent(), log1params));



        Log log2 = new Log(
                0,
                "DELETE FROM TESTSOFT.TB_RESOURCE WHERE ID = ? AND LAST_UPDATE = ? [params=(long) 1328126930, (int) 0]"
        );

        List<String> log2params= LogParser.getParamsFrom(log2);

        Assert.assertEquals(
                "DELETE FROM TESTSOFT.TB_RESOURCE WHERE ID = '1328126930' AND LAST_UPDATE = '0' [params=(long) 1328126930, (int) 0]",
                LogParser.replacePlaceholdersWithParams(log2.getContent(), log2params));


    }




}
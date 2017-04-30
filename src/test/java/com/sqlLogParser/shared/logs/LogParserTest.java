package com.sqlLogParser.shared.logs;

import com.sqlLogParser.server.rpc.logs.LogParser;
import com.sqlLogParser.shared.params.Parameter;
import com.sqlLogParser.shared.params.ParameterType;
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
    public void parseQuery() throws Exception
    {
        Log log;
        String logBody;
        String expected;
        String actual;


        logBody = "290062  TRACE  [btpool0-0] kodo.jdbc.SQL - <t 10272075, conn 18292272> [28 ms] executing prepstmnt 29574501 SELECT t0.A FROM TESTSOFT.TB_NUMBERING_RANGE t0 WHERE t0.ID = ? [params=(long) 1300053803]";
        expected = "SELECT t0.A FROM TESTSOFT.TB_NUMBERING_RANGE t0 WHERE t0.ID = 1300053803";
        actual = LogParser.parse(new Log(0, logBody));

        logBody = "290000 TRACE [btpool0-0] kodo.jdbc.SQL - [30 ms] executing prepstmnt 25221994 SELECT t0.ID, t0.NUMBER_RANGE_TYPE, t0.LAST_UPDATE, t0.C, t0.CREATED_BY, t0.CREATED_ON, t0.D, t0.IDENTIFIER_PERSISTENT, t0.LAST_MODIFIED_BY, t0.LAST_MODIFIED_ON, t0.M, t0.P, t0.Q, t0.S, t0.SECONDARY_DESTINATION_NUM, t0.U, t0.A, t0.B, t0.DESCRIPTION, t0.DESCENT_NUM, t0.IDENTIFIER_AB_PERSISTENT, t0.IS_MOVED, t0.NUMBERING_STATUS FROM TESTSOFT.TB_NUMBERING_RANGE t0 WHERE (LOWER(t0.A) = ? AND LOWER(t0.B) = ? AND LOWER(t0.S) = ? AND LOWER(t0.P) = ? AND LOWER(t0.Q) = ? AND t0.ID <> ?) AND t0.NUMBER_RANGE_TYPE = ? [params=(String) 1, (String) 5, (String) 8, (String) 7, (String) 6, (long) 1300053803, (int) 2]";
        expected = "SELECT t0.ID, t0.NUMBER_RANGE_TYPE, t0.LAST_UPDATE, t0.C, t0.CREATED_BY, t0.CREATED_ON, t0.D, t0.IDENTIFIER_PERSISTENT, t0.LAST_MODIFIED_BY, t0.LAST_MODIFIED_ON, t0.M, t0.P, t0.Q, t0.S, t0.SECONDARY_DESTINATION_NUM, t0.U, t0.A, t0.B, t0.DESCRIPTION, t0.DESCENT_NUM, t0.IDENTIFIER_AB_PERSISTENT, t0.IS_MOVED, t0.NUMBERING_STATUS FROM TESTSOFT.TB_NUMBERING_RANGE t0 WHERE (LOWER(t0.A) = '1' AND LOWER(t0.B) = '5' AND LOWER(t0.S) = '8' AND LOWER(t0.P) = '7' AND LOWER(t0.Q) = '6' AND t0.ID <> 1300053803) AND t0.NUMBER_RANGE_TYPE = 2";
        actual = LogParser.parse(new Log(0, logBody));

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getParamValuesFrom() throws Exception
    {
        Log log;
        String logBody;
        List<String> paramTypesExpected;
        List<String> paramTypesActual;

        logBody = "[14.06.2013|09:48:40:715] [btpool0-0] [validator.ValidatorResources] [getForm] [439] : Form 'java.lang.Object' not found for locale 'pl_PL'";
        paramTypesExpected = new ArrayList<>();
        paramTypesActual = LogParser.getParamValuesFrom(new Log(0, logBody));
        Assert.assertTrue(paramTypesActual.size() == paramTypesExpected.size());
        Assert.assertTrue(paramTypesActual.containsAll(paramTypesExpected));


        logBody = "289968  TRACE  [btpool0-0] kodo.jdbc.SQL blablabla ? [params=(long) 1300053803]";
        paramTypesExpected = Arrays.asList("1300053803");
        paramTypesActual = LogParser.getParamValuesFrom(new Log(0, logBody));
        Assert.assertTrue(paramTypesActual.size() == paramTypesExpected.size());
        Assert.assertTrue(paramTypesActual.containsAll(paramTypesExpected));


        logBody = "DELETE FROM TESTSOFT.TB_RESOURCE WHERE ID = ? AND LAST_UPDATE = ? [params=(long) 1328126930, (int) 0]";
        paramTypesExpected = Arrays.asList("1328126930", "0");
        paramTypesActual = LogParser.getParamValuesFrom(new Log(0, logBody));
        Assert.assertTrue(paramTypesActual.size() == paramTypesExpected.size());
        Assert.assertTrue(paramTypesActual.containsAll(paramTypesExpected));
    }


    @Test
    public void getParamTypesFrom() throws Exception
    {
        Log log;
        String logBody;
        List<String> paramTypesExpected;
        List<String> paramTypesActual;


        logBody = "289968  TRACE  [btpool0-0] kodo.jdbc.SQL blablabla ? [params=(long) 1300053803]";
        paramTypesExpected = Arrays.asList("long");
        paramTypesActual = LogParser.getParamTypesFrom(new Log(0, logBody));

        Assert.assertNotNull(paramTypesActual);
        Assert.assertEquals("long", paramTypesActual.get(0));
        Assert.assertTrue(paramTypesExpected.size() == paramTypesActual.size());
        Assert.assertTrue(paramTypesExpected.containsAll(paramTypesActual));


        logBody = "DELETE FROM TESTSOFT.TB_RESOURCE WHERE ID = ? AND LAST_UPDATE = ? [params=(long) 1328126930, (int) 0]";
        paramTypesExpected = Arrays.asList("long", "int");
        paramTypesActual = LogParser.getParamTypesFrom(new Log(0, logBody));

        Assert.assertNotNull(paramTypesActual);
        Assert.assertEquals("long", paramTypesActual.get(0));
        Assert.assertEquals("int", paramTypesActual.get(1));
        Assert.assertTrue(paramTypesExpected.size() == paramTypesActual.size());
        Assert.assertTrue(paramTypesExpected.containsAll(paramTypesActual));

        logBody = "SELECT * FROM USER WHERE ID = ? AND NAME = ? [params=(long) 1328126930, (String) jacob]";
        paramTypesExpected = Arrays.asList("long", "String");
        paramTypesActual = LogParser.getParamTypesFrom(new Log(0, logBody));

        Assert.assertNotNull(paramTypesActual);
        Assert.assertEquals("long", paramTypesActual.get(0));
        Assert.assertEquals("String", paramTypesActual.get(1));
        Assert.assertTrue(paramTypesExpected.size() == paramTypesActual.size());
        Assert.assertTrue(paramTypesExpected.containsAll(paramTypesActual));

    }

    @Test
    public void getParamsFrom() throws Exception
    {

        Log log;
        String logBody;
        List<Parameter> paramsExpected;
        List<Parameter> paramsActual;

        logBody = "[14.06.2013|09:48:40:715] [btpool0-0] [validator.ValidatorResources] [getForm] [439] : Form 'java.lang.Object' not found for locale 'pl_PL'";
        paramsExpected = new ArrayList<>();
        paramsActual = LogParser.getParamsFrom(new Log(0, logBody));
        Assert.assertNotNull(paramsActual);
        Assert.assertTrue(paramsActual.size() == paramsExpected.size());


        logBody = "289968  TRACE  [btpool0-0] kodo.jdbc.SQL blablabla ? [params=(long) 1300053803]";
        paramsExpected = new ArrayList<>();
        paramsExpected.add(new Parameter("1300053803", ParameterType.OTHER));
        paramsActual = LogParser.getParamsFrom(new Log(0, logBody));
        Assert.assertNotNull(paramsActual);
        Assert.assertTrue(paramsExpected.get(0).equals(paramsActual.get(0)));


        logBody ="DELETE FROM TESTSOFT.TB_RESOURCE WHERE ID = ? AND LAST_UPDATE = ? [params=(long) 1328126930, (int) 0]";
        paramsExpected = new ArrayList<>();
        paramsExpected.add(new Parameter("1328126930", ParameterType.OTHER));
        paramsExpected.add(new Parameter("0", ParameterType.OTHER));
        paramsActual = LogParser.getParamsFrom(new Log(0, logBody));
        Assert.assertNotNull(paramsActual);
        Assert.assertTrue(paramsExpected.get(0).equals(paramsActual.get(0)));
        Assert.assertTrue(paramsExpected.get(1).equals(paramsActual.get(1)));

        logBody ="SELECT * FROM USER WHERE ID = ? AND NAME = ? [params=(long) 1328126930, (String) jacob]";
        paramsExpected = new ArrayList<>();
        paramsExpected.add(new Parameter("1328126930", ParameterType.OTHER));
        paramsExpected.add(new Parameter("jacob", ParameterType.STRING));
        paramsActual = LogParser.getParamsFrom(new Log(0, logBody));
        Assert.assertNotNull(paramsActual);
        Assert.assertTrue(paramsExpected.get(0).equals(paramsActual.get(0)));
        Assert.assertTrue(paramsExpected.get(1).equals(paramsActual.get(1)));
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
    public void replacePlaceholdersWithParams() throws Exception
    {

        Log log;
        String logBody;
        String expectedResoult;
        List<Parameter> parameters;


        logBody = " ? [params=(long) 1300053803]";
        expectedResoult = " 1300053803 [params=(long) 1300053803]";
        parameters = LogParser.getParamsFrom(new Log(0, logBody));
        Assert.assertEquals(expectedResoult, LogParser.replacePlaceholdersWithParams(logBody, parameters));

        logBody = "DELETE FROM TESTSOFT.TB_RESOURCE WHERE ID = ? AND LAST_UPDATE = ? [params=(long) 1328126930, (int) 0]";
        expectedResoult = "DELETE FROM TESTSOFT.TB_RESOURCE WHERE ID = 1328126930 AND LAST_UPDATE = 0 [params=(long) 1328126930, (int) 0]";
        parameters = LogParser.getParamsFrom(new Log(0, logBody));
        Assert.assertEquals(expectedResoult, LogParser.replacePlaceholdersWithParams(logBody, parameters));

        logBody = "SELECT * FROM USER WHERE ID = ? AND NAME = ? [params=(long) 1328126930, (String) jacob]";
        expectedResoult = "SELECT * FROM USER WHERE ID = 1328126930 AND NAME = 'jacob' [params=(long) 1328126930, (String) jacob]";
        parameters = LogParser.getParamsFrom(new Log(0, logBody));
        Assert.assertEquals(expectedResoult, LogParser.replacePlaceholdersWithParams(logBody, parameters));

    }


}
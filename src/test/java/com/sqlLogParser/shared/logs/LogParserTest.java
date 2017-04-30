package com.sqlLogParser.shared.logs;

import com.sqlLogParser.server.rpc.logs.LogParser;
import org.junit.Assert;
import org.junit.Test;


/**
 * Created by Jakub on 30.04.2017.
 */

public class LogParserTest
{

    @Test
    public void getParamsFrom() throws Exception
    {
        Log log = new Log(
                0,
                "289968  TRACE  [btpool0-0] kodo.jdbc.SQL - <t 10272075, conn 18292272> [29 ms] executing prepstmnt 11854753 SELECT t0.NUMBER_RANGE_TYPE, t0.LAST_UPDATE FROM TESTSOFT.TB_NUMBERING_RANGE t0 WHERE t0.ID = ? [params=(long) 1300053803]"
        );


        Log log2 = new Log(
                0,
                "294517  TRACE  [btpool0-0] kodo.jdbc.SQL - <t 10272075, conn 18292272> [0 ms] batching prepstmnt 15649338 DELETE FROM TESTSOFT.TB_RESOURCE WHERE ID = ? AND LAST_UPDATE = ? [params=(long) 1328126930, (int) 0]"
        );

        Assert.assertEquals("1300053803", LogParser.getParamsFrom(log));
        Assert.assertEquals("1328126930, 0",LogParser.getParamsFrom(log2));


    }


    @Test
    public void parseLog() throws Exception
    {

        Log log = new Log(
                0,
                "289968  TRACE  [btpool0-0] kodo.jdbc.SQL - <t 10272075, conn 18292272> [29 ms] executing prepstmnt 11854753 SELECT t0.NUMBER_RANGE_TYPE, t0.LAST_UPDATE FROM TESTSOFT.TB_NUMBERING_RANGE t0 WHERE t0.ID = ? [params=(long) 1300053803]");

        Assert.assertEquals(
                "SELECT t0.NUMBER_RANGE_TYPE, t0.LAST_UPDATE FROM TESTSOFT.TB_NUMBERING_RANGE t0 WHERE t0.ID = '1300053803'",
                LogParser.parseLog(log));
    }

}
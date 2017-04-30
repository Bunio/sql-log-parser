package com.sqlLogParser.server.rpc.logs;

import com.sqlLogParser.shared.logs.Log;
import com.sqlLogParser.shared.params.Parameter;
import com.sqlLogParser.shared.params.ParameterType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jakub on 30.04.2017.
 */

public class LogParser
{
    private static Logger logger = Logger.getLogger("LOG PARSER");

    public static String parseQuery(Log log)
    {

        String query = log.getContent();

        // Get param list
        List<String> params = getParamValuesFrom(log);

        // Remove everything before SELECT/UPDATE ETC
        query = trimToQuery(query);

        // Remove params
        query = cutParamsFrom(query);

        // Replace question marks with params
        query = replacePlaceholdersWithParams(query, params);

        return query.trim();
    }

    public static String trimToQuery(String log)
    {
        return log.replaceFirst(".*\\s+(?=DELETE |UPDATE |SELECT |INSERT )", "");
    }


    public static List<Parameter> getParamsFrom(Log log)
    {
        List<String> paramValues = getParamValuesFrom(log);
        List<String> paramTypes = getParamTypesFrom(log);
        List<Parameter> params = new ArrayList<>();
        Parameter parameter;

        for(int i=0; i< paramValues.size(); i++)
        {
            parameter = new Parameter();
            parameter.setValue(paramValues.get(i));

            if(paramTypes.get(i).equals("String"))
            {
                parameter.setType(ParameterType.STRING);
            }

            else
            {
                parameter.setType(ParameterType.OTHER);
            }

            params.add(parameter);
        }

        return params;
    }


    public static List<String> getParamValuesFrom(Log log)
    {
        String params = log.getContent()
                .replaceFirst("(.*)params=", "") // Remove Everything before params=
                .replaceAll("\\[", "")  // Remove all left square brackets
                .replaceAll("]", "")    // Remove all right square brackets
                .replaceAll("\\(.*?\\)", "") // Remove everything inside brackets
                .replaceAll("\\s+", "") // Remove all whitespace characters
                .trim();

        return Arrays.asList(params.split(","));
    }

    public static List<String> getParamTypesFrom(Log log)
    {
        String params = log.getContent()
                .replaceFirst("(.*)params=", "") // Remove Everything before params=
                .replaceAll("\\[", "")  // Remove all left square brackets
                .replaceAll("]", "")    // Remove all right square brackets
                .replaceAll("\\s+", "") // Remove all whitespace characters
                .trim();

        Pattern pattern = Pattern.compile("\\((.*?)\\)");
        Matcher matcher = pattern.matcher(params);

        List<String> paramTypes = new ArrayList<>();

        while(matcher.find())
        {
            paramTypes.add(matcher.group(1));
        }

        return paramTypes;
    }

    public static String cutParamsFrom(String log)
    {
        return  log.replaceFirst("\\[params=(.*)", "");
    }

    public static String replacePlaceholdersWithParams(String log, List<String> params)
    {
        for(String param : params)
        {
            log = log.replaceFirst("\\?", "'" + param + "'");
        }

        return log;
    }

}

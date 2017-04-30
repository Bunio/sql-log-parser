package com.sqlLogParser.shared.params;

/**
 * Created by Jakub on 30.04.2017.
 */
public class Parameter
{
    private String parameter;
    private ParameterType type;


    public Parameter() {}

    public Parameter(String parameter, ParameterType type) {
        this.parameter = parameter;
        this.type = type;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public ParameterType getType() {
        return type;
    }

    public void setType(ParameterType type) {
        this.type = type;
    }
}

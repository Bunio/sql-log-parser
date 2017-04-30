package com.sqlLogParser.shared.params;

/**
 * Created by Jakub on 30.04.2017.
 */
public class Parameter
{
    private String value;
    private ParameterType type;


    public Parameter() {}

    public Parameter(String parameter, ParameterType type) {
        this.value = parameter;
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ParameterType getType() {
        return type;
    }

    public void setType(ParameterType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Parameter parameter = (Parameter) o;

        if (!value.equals(parameter.value)) return false;
        return type == parameter.type;
    }

    @Override
    public int hashCode() {
        int result = value.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }
}

package kr.teamcocoa.freefight.messages;

import java.util.ArrayList;
import java.util.List;

public class Arguments {

    public static Arguments empty() {
        return new Arguments(0);
    }

    private List<Object> arguments;

    public Arguments(int amount) {
        this.arguments = new ArrayList<>(amount);
    }

    public void addArgument(Object object) {
        arguments.add(object);
    }

    public Object[] getArguments() {
        return arguments.toArray();
    }

}

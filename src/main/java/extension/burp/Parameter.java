package extension.burp;

import burp.IParameter;

/**
 *
 * @author isayan
 */
public class Parameter implements IParameter  {

    public Parameter(IParameter parameter) {
        this.type = parameter.getType();
        this.name = parameter.getName();
        this.value = parameter.getValue();
        this.nameStart = parameter.getNameStart();
        this.nameEnd = parameter.getNameEnd();
        this.valueStart = parameter.getValueStart();
        this.valueEnd = parameter.getValueEnd();
    }
    
    private byte type;
    
    @Override
    public byte getType() {
        return type;
    }

    private String name;
    
    @Override
    public String getName() {
        return name;
    }

    private String value;
    
    @Override
    public String getValue() {
        return value;
    }

    private int nameStart;
    
    @Override
    public int getNameStart() {
        return nameStart;
    }

    private int nameEnd;
    
    @Override
    public int getNameEnd() {
        return nameEnd;
    }

    private int valueStart;
    
    @Override
    public int getValueStart() {
        return valueStart;
    }

    private int valueEnd;
    
    @Override
    public int getValueEnd() {
        return valueEnd;
    }
    
}

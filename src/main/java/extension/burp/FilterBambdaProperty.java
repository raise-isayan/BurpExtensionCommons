package extension.burp;

/**
 *
 * @author isayan
 */
public interface FilterBambdaProperty {

    public FilterProperty.FilterMode getFilterMode();

    public void setFilterMode(FilterProperty.FilterMode filterMode);
    
    public String getBambdaQuery();
    
}

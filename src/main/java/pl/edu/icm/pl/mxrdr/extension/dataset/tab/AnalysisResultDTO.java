package pl.edu.icm.pl.mxrdr.extension.dataset.tab;

public class AnalysisResultDTO {

    String fieldLabel;
    String primaryValue;
    String secondaryValue;
    
    public String getFieldLabel() {
        return fieldLabel;
    }
    public void setFieldLabel(String fieldLabel) {
        this.fieldLabel = fieldLabel;
    }
    public String getPrimaryValue() {
        return primaryValue;
    }
    public void setPrimaryValue(String primaryValue) {
        this.primaryValue = primaryValue;
    }
    public String getSecondaryValue() {
        return secondaryValue;
    }
    public void setSecondaryValue(String secondaryValue) {
        this.secondaryValue = secondaryValue;
    }
}

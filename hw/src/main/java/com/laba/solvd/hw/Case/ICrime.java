package com.laba.solvd.hw.Case;
public interface ICrime {
    String getDescription();
    
    public enum Severity {
        LOW(1),
        MEDIUM(2),
        HIGH(3);
        
        private int value;
        
        Severity(int value) {
            this.value = value;
        }
        
        public int getValue() {
            return value;
        }
    }
    
    void setSeverity(Severity severity);
    Severity getSeverity();
}
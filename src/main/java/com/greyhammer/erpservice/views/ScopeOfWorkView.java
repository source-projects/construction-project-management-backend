package com.greyhammer.erpservice.views;

public class ScopeOfWorkView {
    public interface L1View {}
    public interface L2View extends L1View {}
    public interface L3View extends L2View {}
}

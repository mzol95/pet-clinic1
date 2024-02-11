package pl.zoltowskimarcin.java.app.mapper;

import org.modelmapper.ModelMapper;

public class ModelMapperManager {

    private final static ModelMapper instance;

    static {
        instance = new ModelMapper();
    }

    private ModelMapperManager() {
    }

    public static ModelMapper getInstance() {
        return instance;
    }
}

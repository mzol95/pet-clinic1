package pl.zoltowskimarcin.java.app.mapper;

import org.modelmapper.ModelMapper;

public class ModelMapperManager {

    private final static ModelMapper modelMapper;

    static {
        modelMapper = new ModelMapper();
    }

    private ModelMapperManager() {
    }

    public static ModelMapper getModelMapper() {
        return modelMapper;
    }
}

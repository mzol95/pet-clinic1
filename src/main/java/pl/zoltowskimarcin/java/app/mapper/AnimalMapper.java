package pl.zoltowskimarcin.java.app.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.zoltowskimarcin.java.app.repository.entity.AnimalEntity;
import pl.zoltowskimarcin.java.app.web.model.Animal;

@Component
public class AnimalMapper {

    private static ModelMapper modelMapper = new ModelMapper();

    private AnimalMapper() {
    }

    public static Animal mapToModel(AnimalEntity animalEntity) {
        return modelMapper.map(animalEntity, Animal.class);
    }

    public static AnimalEntity mapToEntity(Animal animal) {
        return modelMapper.map(animal, AnimalEntity.class);
    }

}

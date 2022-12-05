package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dto.ImportPictureDto;
import softuni.exam.instagraphlite.models.entities.Picture;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.service.PictureService;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.Double.SIZE;
import static softuni.exam.instagraphlite.constant.Messages.INVALID_PICTURE;
import static softuni.exam.instagraphlite.constant.Messages.SUCCESSFULLY_IMPORTED_PICTURE;
import static softuni.exam.instagraphlite.constant.Path.PICTURES_JSON_PATH;

@Service
public class PictureServiceImpl implements PictureService {

    private final PictureRepository pictureRepository;

    private final Gson gson;
    private final Validator validator;
    private final ModelMapper modelMapper;

    @Autowired
    public PictureServiceImpl(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;

        this.gson = new GsonBuilder().create();

        this.validator = Validation
                .buildDefaultValidatorFactory()
                .getValidator();

        this.modelMapper = new ModelMapper();
    }

    @Override
    public boolean areImported() {
        return this.pictureRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        Path path = Path.of(PICTURES_JSON_PATH);

        return Files.readString(path);
    }

    @Override
    public String importPictures() throws IOException {
        String json = this.readFromFileContent();

        ImportPictureDto[] pictureDtos = this.gson.fromJson(json, ImportPictureDto[].class);

        List<String> result = new ArrayList<>();

        for (ImportPictureDto picture : pictureDtos) {
            Set<ConstraintViolation<ImportPictureDto>> errors = this.validator.validate(picture);
            if (errors.isEmpty()){

                Optional<Picture> optPicture = this.pictureRepository.findByPath(picture.getPath());

                if (optPicture.isEmpty()){
                    Picture pictureToMap = this.modelMapper.map(picture, Picture.class);
                    this.pictureRepository.save(pictureToMap);

                    String msg = String.format(SUCCESSFULLY_IMPORTED_PICTURE, pictureToMap.getSize());
                    result.add(msg);
                } else {
                    result.add(INVALID_PICTURE);
                }
            } else {
                result.add(INVALID_PICTURE);
            }
        }
        return String.join("\n", result);
    }

    @Override
    public String exportPictures() {

        double size = 30000.00;

       return this.pictureRepository.findAllBySizeGreaterThanOrderBySizeAsc(size).stream().map(Picture::toString).collect(Collectors.joining(System.lineSeparator()));
    }
}

package productsShop.constant;

import org.modelmapper.ModelMapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public enum Utils {
    ;
    public static final ModelMapper MODEL_MAPPER = new ModelMapper();

    public static <T> void writeXMLToFile(T data, Path filePath) throws IOException, JAXBException {

        File file = filePath.toFile();

        final JAXBContext context = JAXBContext.newInstance(data.getClass());
        final Marshaller marshaller = context.createMarshaller();

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        marshaller.marshal(data, file);
    }

    public static void writeJsonToFile(Object object, Path filePath) throws IOException {
        final FileWriter fileWriter = new FileWriter(filePath.toFile());

        // GSON.toJson(object, fileWriter);

        fileWriter.flush();
        fileWriter.close();
    }

}

package com.comunidadmayor.servicio;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.apache.commons.io.FilenameUtils;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

@SpringBootTest
public class AlmacenServicioImplTest {
    @Autowired
    AlmacenServicioImpl almacenservicioimpl = new AlmacenServicioImpl();
        
    @Test
    void testAlmacenarArchivo() throws IOException {
        File filemf = new File("audiolibro01.png");
        BufferedImage bImage = ImageIO.read(filemf);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bImage,FilenameUtils.getExtension(filemf.getAbsolutePath()), bos);

        MockMultipartFile multipartFile = new MockMultipartFile("image", "audiolibro01.png", MediaType.IMAGE_JPEG_VALUE, bos.toByteArray());
        final String archivo = almacenservicioimpl.almacenarArchivo(multipartFile);
        assertNotNull(archivo);
    }

    @Test
    void testEliminarArchivo() {
        String fileDelete = "audiolibro01.png";
        almacenservicioimpl.eliminarArchivo(fileDelete);
    }
}

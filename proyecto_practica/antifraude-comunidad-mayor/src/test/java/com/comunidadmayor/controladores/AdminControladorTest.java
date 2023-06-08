package com.comunidadmayor.controladores;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import org.springframework.web.servlet.ModelAndView;

import com.comunidadmayor.modelo.Genero;
import com.comunidadmayor.modelo.Libro;

@SpringBootTest
public class AdminControladorTest {
    @Autowired
    AdminControlador admincontrolador = new AdminControlador();


    @Test
    void testRegistrarLibro() throws IOException {
        List<Genero> listsGenero = new ArrayList<>();
        listsGenero.add(new Genero(2));
        File filemf = new File("laptop-g6dfff0680_640.jpg");
        BufferedImage bImage = ImageIO.read(filemf);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bImage,FilenameUtils.getExtension(filemf.getAbsolutePath()), bos);

        MockMultipartFile multipartFile = new MockMultipartFile("image", filemf.getName(), MediaType.IMAGE_JPEG_VALUE, bos.toByteArray());
        
        Libro libro = new Libro("test-chica", "signopsis de test chica con lentes", 
        			LocalDate.of(2023,6,6), "ok_ss2293", "", listsGenero, multipartFile);
        final ModelAndView model = admincontrolador.registrarLibro(libro, null);
        assertNotNull(model);
        System.out.println(model.toString());

    }
}

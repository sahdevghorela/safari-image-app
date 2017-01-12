package com.learn.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Controller
public class HomeController {

    private static final String BASE_PATH = "/images";
    private static final String FILENAME = "{filename:.+}";
    private ImageService imageService;

    @Autowired
    public HomeController(ImageService imageService) {
        this.imageService = imageService;
    }


    @RequestMapping(method = RequestMethod.GET,value = BASE_PATH+"/"+FILENAME+"/"+"raw")
    @ResponseBody
    public ResponseEntity<?> findRawImage(@PathVariable String fileName) {

        try {
            Resource file = imageService.findOneImage(fileName);
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .contentLength(file.contentLength())
                    .body(new InputStreamResource(file.getInputStream()));
        } catch (IOException e) {
            return ResponseEntity
                    .badRequest()
                    .body("Not found "+fileName+e.getMessage());
        }

    }


    @RequestMapping(method = RequestMethod.POST,value=BASE_PATH)
    @ResponseBody
    public ResponseEntity createFile(@RequestParam("file")MultipartFile file, HttpServletRequest httpRequest){
        try {
            imageService.createImage(file);
            URI uri = new URI(httpRequest.getRequestURL().toString()+"/").resolve(file.getOriginalFilename()+"/raw");
            return ResponseEntity.created(uri).body("successfully uploaded "+file.getOriginalFilename());
        } catch (IOException | URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Can not upload "+file.getOriginalFilename());
        }
    }

    @RequestMapping(method = RequestMethod.DELETE,value = BASE_PATH+"/"+FILENAME )
    @ResponseBody
    public ResponseEntity deleteFile(@PathVariable String fileName){
        try {
            imageService.deleteImage(fileName);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("successfully deleted "+fileName);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete "+fileName);
        }
    }

}

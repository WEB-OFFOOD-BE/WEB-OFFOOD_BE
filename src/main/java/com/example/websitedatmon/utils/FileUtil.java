package com.example.websitedatmon.utils;

import com.example.websitedatmon.constans.CommonConstants;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

public class FileUtil {
    /**
     * TODO: Method description
     *
     * @param multipartFile
     * @param request
     * @return
     * @throws IOException
     * @throws IllegalStateException
     */
    public static String upload(MultipartFile multipartFile, HttpServletRequest request) throws IllegalStateException, IOException {
        String webPath = request.getServletContext().getRealPath("");
        String dirPath = webPath + CommonConstants.DIR_UPLOAD;
        if (new File(dirPath).exists()) {
            new File(dirPath).mkdir();
        }

        String fileName = String.valueOf(System.currentTimeMillis()) + ".jpg";

        String pathFile = dirPath + File.separator + fileName;
        multipartFile.transferTo(new File(pathFile));
        return fileName;
    }

    /**
     * TODO: Method description
     *
     * @param nameFile
     * @param request
     * @throws IOException
     * @throws IllegalStateException
     */
    public static void delete(String nameFile, HttpServletRequest request) throws IllegalStateException, IOException {
        String dirFile = request.getServletContext().getRealPath("") + CommonConstants.DIR_UPLOAD;
        File file = new File(dirFile + File.separator + nameFile);
        if (file.exists()) {
            file.delete();
        }
    }
    public static String uploadPdf(HttpServletRequest request,MultipartFile image){
        if(image.isEmpty()) {
            return "null";
        }
        String dirname = request.getServletContext().getRealPath(CommonConstants.DIR_UPLOAD);

        Path path = Paths.get(dirname);
        try {
            InputStream inputStream = image.getInputStream();

            String name = String.valueOf(new Date().getTime()+image.getOriginalFilename().toLowerCase());
            Files.copy(inputStream, path.resolve(name), StandardCopyOption.REPLACE_EXISTING);
            return name;

        } catch (Exception e) {
            // TODO: handle exceptione.p
            e.printStackTrace();
            return null;
        }

    }
}

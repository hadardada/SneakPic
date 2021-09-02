package UsersManagement.LoadImageServiece;

import Entities.Image.MyImage;
import Repositories.AlbumRepository.AlbumRepository;
import Repositories.MyImageRepository.MyImageRepository;
import Repositories.UserRepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Service
public class LoadImageService {

    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MyImageRepository myImageRepository;

    private static String absolutePath = "C:\\Users\\dhnhd\\Desktop\\SneakPic\\SneakPic\\Server\\src\\main\\resources\\static\\Albums\\";

    public void saveImage(MultipartFile image, Long albumId) throws IOException {
        System.out.println("here");
        try {
            if (image.isEmpty()) // if no img was sent
                return; // do nothing

            //save to data base
            MyImage myImage = new MyImage();
            myImage.setAlbumId(albumId);
            MyImage currImg= myImageRepository.save(myImage);

            //save uploaded img
            String fileName = currImg.getId().toString()+"."+image.getContentType().replaceFirst("image/", "");
            String uploadDir = this.absolutePath + albumId;
            FileUploadUtil.saveFile(uploadDir, fileName, image);

            //get "marked" dir
            String path = uploadDir+ "/" +fileName; //original image path
            String[] details = path.split("/");
            String outDir = path.replace(details[details.length -1], "marked");

            File sourceImageFile = new File(path);
            BufferedImage sourceImage = ImageIO.read(sourceImageFile);
            String sourceName = details[details.length -1].split("\\.")[0];
            BufferedImage copy = deepCopy(sourceImage,outDir, sourceName);

            String outPath = outDir + "/" +sourceName +"Copy.jpeg" ;
            File outImageFile = new File(outPath);

            Graphics2D g2d = (Graphics2D) copy.getGraphics();
            String waterMark = "SneakPic";
            // initializes necessary graphic properties
            AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
            g2d.setComposite(alphaChannel);
            g2d.setColor(Color.BLUE);
            Font font = new Font("Arial", Font.BOLD, 150);
            g2d.setFont(font);
            FontMetrics fontMetrics = g2d.getFontMetrics();
            Rectangle2D rect = fontMetrics.getStringBounds(waterMark, g2d);

            // calculates the coordinate where the String is painted
            int x = (copy.getWidth() - (int) rect.getWidth()) / 4;
            int y = copy.getHeight() / 4;
            g2d.drawString(waterMark, x, y);

            int centerX = (copy.getWidth() - (int) rect.getWidth()) / 2;
            int centerY = copy.getHeight() / 2;
            g2d.drawString(waterMark, centerX, centerY);

            int x3 = (copy.getWidth() - (int) rect.getWidth()) / 6;
            int y3 = copy.getHeight() / 6;
            g2d.drawString(waterMark, x3, y3);

            int x2 = (copy.getWidth() - (int) rect.getWidth()) ;
            int y2 = copy.getHeight();
            g2d.drawString(waterMark, x2, y2);

            int x5 = (copy.getWidth() ) ;
            int y5 = copy.getHeight();
            g2d.drawString(waterMark, x5, y5);

            int x4 = (copy.getWidth() - (int) rect.getWidth()) /3;
            int y4 = copy.getHeight() /3;
            g2d.drawString(waterMark, x4, y4);

            ImageIO.write(copy, "jpeg", outImageFile);
            g2d.dispose();

            System.out.println("The tex watermark is added to the image.");
        } catch (IOException ex) {
            System.err.println(ex);
        } finally {

        }

    }

    static BufferedImage deepCopy(BufferedImage bi, String outPath, String imageSourceName) throws IOException {
        String saveAs = imageSourceName +"Copy.jpeg";
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        BufferedImage cImg = new BufferedImage(cm, raster, isAlphaPremultiplied, null);
        File saveImage = new File(outPath, saveAs);
        ImageIO.write(cImg, "jpeg", saveImage);
        return cImg;
    }
//
//        public static void main(String[] args) throws IOException {
//            BufferedImage cp, img;
//            img = ImageIO.read(file);
//            cp = deepCopy(img);
//        }
//    }

    public static class FileUploadUtil {

        public static void saveFile(String uploadDir, String fileName,
                                    MultipartFile multipartFile) throws IOException {
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            try (InputStream inputStream = multipartFile.getInputStream()) {
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ioe) {
                throw new IOException("Could not save image file: " + fileName, ioe);
            }
        }
    }
}

package UsersManagement.LoadImageServiece;

import Entities.Image.MyImage;
import Repositories.AlbumRepository.AlbumRepository;
import Repositories.MyImageRepository.MyImageRepository;
import Repositories.UserRepository.UserRepository;
import S3Config.AmazonConfig;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;


@Service
public class LoadImageService {
    @Autowired
    private AmazonConfig amazonConfig;

    @Autowired
    private MyImageRepository myImageRepository;

    private static String bucketName = "sneakpicbucket";
    private static String markedBucket = "sneakpicbucketmarked";

    public void saveImage(MultipartFile image, Long albumId) throws IOException {
        if (image.isEmpty()) // if no img was sent
            return; // do nothing

        //save img details to data base
        MyImage myImage = new MyImage();
        myImage.setAlbumId(albumId);
        MyImage currImg= myImageRepository.save(myImage);

        // upload img to S3
        String fileName = currImg.getId().toString()+"."+image.getContentType().replaceFirst("image/", "");
        String uploadDir =albumId+"/"+fileName;
        InputStream input =image.getInputStream();
        ObjectMetadata metadata=new ObjectMetadata();
        metadata.setContentLength(image.getSize());
        PutObjectResult res = amazonConfig.s3().putObject(new PutObjectRequest(bucketName,uploadDir, input,  metadata));

        // create & upload watermarked img to S3
        BufferedImage copy = ImageIO.read(new ByteArrayInputStream(image.getBytes()));
        Graphics2D g2d = (Graphics2D) copy.getGraphics();
        AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
        g2d.setComposite(alphaChannel);
        BufferedImage watermarkImage;
        if ((copy.getWidth()<500) || (copy.getHeight()<500)){ // if it's a small pic - watermark shouldn't be big
            watermarkImage= ImageIO.read(getClass().getResource("/static/img/watermark.png"));
            int topLeftX = (copy.getWidth() - watermarkImage.getWidth()) / 2;
            int topLeftY = (copy.getHeight() - watermarkImage.getHeight()) / 2;
            g2d.drawImage(watermarkImage, topLeftX, topLeftY, null);
        }
        else { // if img is big, cover pic with multiple watermarks
            watermarkImage = ImageIO.read(getClass().getResource("/static/img/watermarks.png"));
            int i = 0;
            while (watermarkImage.getHeight()*i< copy.getHeight()){
                int topLeftX = 0;
                int topLeftY = watermarkImage.getHeight()*i;
                g2d.drawImage(watermarkImage, topLeftX, topLeftY, null);
                i++;
            }
        }
        String uploadDirMarked = albumId+ "/" +fileName; //original image path
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(copy, "jpeg", os);
        byte[] buffer = os.toByteArray();
        InputStream inputMarked = new ByteArrayInputStream(buffer);
        metadata.setContentLength(buffer.length);
         amazonConfig.s3().putObject(new PutObjectRequest(markedBucket,uploadDirMarked, inputMarked,  metadata));
        g2d.dispose();
    }

}

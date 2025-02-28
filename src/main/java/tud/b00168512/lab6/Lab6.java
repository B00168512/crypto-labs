package tud.b00168512.lab6;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import org.apache.commons.imaging.formats.bmp.BmpImageParser;
import org.apache.commons.imaging.formats.bmp.BmpImagingParameters;

public class Lab6 {

    public static void main(String[] args) throws Exception {
        craftBmp();
    }

    private static void craftBmp() throws Exception {
        var encryptedBmpFile = Paths.get("F:/Docs/tud/labs/lab6(bmp)/aes.bmp.enc");
        var allBytes = Files.readAllBytes(encryptedBmpFile);
        var encPixels = Arrays.copyOfRange(allBytes, 55, allBytes.length);

        try (var dos = new ByteArrayOutputStream()) {
            // Header
            dos.write("BM".getBytes());
            dos.write(ByteBuffer.allocate(4).putInt(Integer.reverseBytes(900 * 1024)).array()); // file size
            dos.write(new byte[]{0, 0});
            dos.write(new byte[]{0, 0});
            dos.write(ByteBuffer.allocate(4).putInt(Integer.reverseBytes(14 + 40)).array()); // offset

            // DIB header
            dos.write(ByteBuffer.allocate(4).putInt(Integer.reverseBytes(40)).array()); // the size of this header, in bytes (40)

            var width = 480; // 640, 768, depends on the bpp; '480' is for 32 bits
            var height = 470;

            dos.write(ByteBuffer.allocate(4).putInt(Integer.reverseBytes(width)).array()); // width
            dos.write(ByteBuffer.allocate(4).putInt(Integer.reverseBytes(height)).array()); // height

            dos.write(ByteBuffer.allocate(2).put(new byte[]{1, 0}).array()); // the number of color planes (must be 1)
            dos.write(ByteBuffer.allocate(2).put(new byte[]{32, 0}).array()); // bpp
            dos.write(ByteBuffer.allocate(4).putInt(Integer.reverseBytes(0)).array()); // compression
            dos.write(ByteBuffer.allocate(4).putInt(Integer.reverseBytes(0)).array()); // dummy raw size
            dos.write(ByteBuffer.allocate(4).putInt(Integer.reverseBytes(0)).array()); // horizontal res
            dos.write(ByteBuffer.allocate(4).putInt(Integer.reverseBytes(0)).array()); // vertical res
            dos.write(ByteBuffer.allocate(4).putInt(0).array()); // the number of colors in the color palette
            dos.write(ByteBuffer.allocate(4).putInt(0).array()); // the number of important colors, all

            dos.write(encPixels);

            var generatedBmp = dos.toByteArray();

            // If it does not throw an exception it means the image is perfectly valid with bmp specification
            try {
                var bufferedImage = new BmpImageParser().getBufferedImage(generatedBmp, new BmpImagingParameters());
            } catch (Exception e) {
                System.err.println(e.getMessage());
            } finally {
                // Keep saving the result to file regardless any errors thrown, this could help to debug.
                var p = Paths.get("trolld.bmp");
                if (Files.exists(p)) {
                    Files.delete(p);
                } else {
                    Files.createFile(p);
                }
                Files.write(p, generatedBmp);
            }
        }
    }
}

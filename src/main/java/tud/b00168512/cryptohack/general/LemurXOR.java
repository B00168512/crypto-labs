package tud.b00168512.cryptohack.general;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.imaging.bytesource.ByteSource;
import org.apache.commons.imaging.formats.png.PngImageParser;
import org.apache.commons.imaging.formats.png.PngImagingParameters;

public class LemurXOR {

    public static <List> void main(String[] args) throws Exception {
        var img1Path = Paths.get("C:\\Users\\bemon\\Downloads\\flag_7ae18c704272532658c10b5faad06d74.png");
        var img2Path = Paths.get("C:\\Users\\bemon\\Downloads\\lemur_ed66878c338e662d3473f0d98eedbd0d.png");
        var redPixelPngPath = Paths.get("C:\\Users\\bemon\\Downloads\\red_pixe.png");

        try (var dis = new DataInputStream(new FileInputStream(img1Path.toFile()))) {
            var header = new byte[8];

            dis.readFully(header);

            var headerHex = Hex.encodeHexString(header);
            var hexArray = new ArrayList<String>();
            for (int i = 0; i < headerHex.length(); i = i + 2) {
                if (i + 1 < headerHex.length()) {
                    hexArray.add("" + headerHex.charAt(i) + "" + headerHex.charAt(i + 1));
                }
            }

            var chunkLengthArr = new byte[4];
            var chunkType = new byte[4];


            dis.read(chunkLengthArr);
            dis.read(chunkType);

            var chunkLength = ByteBuffer.wrap(chunkLengthArr).getInt();
            var chunkData = new byte[chunkLength];
            var chunkCRC = new byte[4];
            dis.read(chunkData);
            dis.read(chunkCRC);

            System.out.println("Header:" + headerHex);
            System.out.println("Header: " + Arrays.toString(hexArray.toArray()));
            System.out.println("Chunk length: " + chunkLength);
            System.out.println("Chunk type: " + new String(chunkType, StandardCharsets.US_ASCII));

            System.out.println("\nIHDR: \n");

            System.out.println("Width: " + ByteBuffer.wrap(Arrays.copyOf(chunkData, 4)).getInt());
            System.out.println("Height: " + ByteBuffer.wrap(Arrays.copyOfRange(chunkData, 4, 8)).getInt());
            System.out.println("Bit depth: " + chunkData[8]);
            System.out.println("Color type: " + chunkData[9]);
            System.out.println("Compression method: " + chunkData[10]);
            System.out.println("Filter method: " + chunkData[11]);
            System.out.println("Interlace method: " + chunkData[12]);

            var nextChunkLengthArr = new byte[4];
            var nextChunkType = new byte[4];


            dis.read(nextChunkLengthArr);

            var nextChunkLength = ByteBuffer.wrap(nextChunkLengthArr).getInt();

            dis.read(nextChunkType);

            var nextChunkData = new byte[nextChunkLength];
            dis.read(nextChunkData);
            var nextChunkCRC = new byte[4];
            dis.read(nextChunkCRC);

            System.out.println("RGB: " + new BigInteger(nextChunkData).toString(16));

            System.out.println("\nNext chunk: \n");

            System.out.println("Next chunk length: " + nextChunkLength);
            System.out.println("Next chunk type: " + new String(nextChunkType, StandardCharsets.US_ASCII));

//            System.out.println("Next chunk DEFLATE COMPRESSION: " + ByteBuffer.wrap(Arrays.copyOfRange(nextChunkData, 4, 8)).getInt());
            var chunk3LengthArr = new byte[4];
            var chunk3Type = new byte[4];
            dis.read(chunk3LengthArr);
            dis.read(chunk3Type);

            System.out.println("chunk 3 length: " + ByteBuffer.wrap(chunk3LengthArr).getInt());
            System.out.println("chunk 3 chunk type: " + new String(chunk3Type, StandardCharsets.US_ASCII));


            var chunks = new PngImageParser().getImageInfo(img1Path.toFile(), new PngImagingParameters());
//            System.out.println(chunks);
//            var buffImage = new PngImageParser().getBufferedImage(ByteSource.file(redPixelPngPath.toFile()), new PngImagingParameters());
//            System.out.println(buffImage);
            System.out.println(chunks);

        }
    }
}

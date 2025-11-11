import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.channels.Channels;
import java.nio.charset.Charset;
import java.nio.file.Files;

public class Nio {
    public static void main(String[] args) throws IOException {
        File file = new File("/Users/mshe/Desktop/1.txt");
        Writer writer = Channels.newWriter(Files.newByteChannel(file.toPath()), Charset.defaultCharset());
        writer.write("hello world");
        writer.close();

    }
}

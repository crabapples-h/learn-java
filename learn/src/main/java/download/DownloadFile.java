package download;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

public class DownloadFile {
    public static void main(String[] args) {
        String url = "http://172.16.8.1:9001/api/v1/download-shared-object/aHR0cDovLzEyNy4wLjAuMTo5MDAwL29iai9taW5lLXBpY3R1cmUvMjAxNjAyMDgyMzU5LkpQRz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPTAzODZPNVJPOVJQVDhWRkZUUTNYJTJGMjAyNTAxMTUlMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjUwMTE1VDAxNDQwMVomWC1BbXotRXhwaXJlcz00MzE5OSZYLUFtei1TZWN1cml0eS1Ub2tlbj1leUpoYkdjaU9pSklVelV4TWlJc0luUjVjQ0k2SWtwWFZDSjkuZXlKaFkyTmxjM05MWlhraU9pSXdNemcyVHpWU1R6bFNVRlE0VmtaR1ZGRXpXQ0lzSW1WNGNDSTZNVGN6TmprME9EVXlPU3dpY0dGeVpXNTBJam9pWTNKaFltRndjR3hsY3lKOS5RRnc5ZFFnT0dzT1JETktmMEhSNjE1UXQxR3ZUUnhZYzlyS2gyT2xLdG50ZTVWZ3FjMmNuOXJZbFRYbzBYVW5pLXhhUnhHTkt0MW9ndmRpLU1UNHZRZyZYLUFtei1TaWduZWRIZWFkZXJzPWhvc3QmdmVyc2lvbklkPW51bGwmWC1BbXotU2lnbmF0dXJlPWY2OTJmN2Q5Nzc4MDQ0NTU2N2IzMmMxYzc3NmFlODI4ODZiNzIxMzIyMjY0YTY5NzBjNTQ1YjlmOGFmZGJmMWI";
        try {
//            File file = new File("/Users/mshe/Downloads/1.jpg");
//            if (file.exists()) {
//                return;
//            }
//            FileOutputStream outputStream = new FileOutputStream(file);
            URLConnection connection = new URL(url).openConnection();
            long fileSize = connection.getContentLengthLong();
            RandomAccessFile fileOutputStream = new RandomAccessFile("/Users/mshe/Downloads/1.jpg", "rw");
            fileOutputStream.seek(fileSize);
//            InputStream inputStream = urlConnection.getInputStream();
//            byte[] data = new byte[1024];
//            for (int i = inputStream.read(data); i != -1; i = inputStream.read(data)) {
//                outputStream.write(data, 0, i);
//            }
//            outputStream.flush();
//            outputStream.close();
//            inputStream.close();
            System.err.println(fileSize);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

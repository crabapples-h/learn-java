package demo.net;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * TODO java连接linux
 *
 * @author Mr.He
 * 2019/7/4 16:24
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */
public class LinuxSessionDemo {
    public static void main(String[] args) throws IOException {
        Connection connection = new Connection("192.168.56.101",22);
        connection.connect();
        connection.authenticateWithPassword("root","0705");
        Session session = connection.openSession();
        session.execCommand("ls");
        InputStream inputStream1 = session.getStdout();
        InputStream inputStream = session.getStderr();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        System.err.println(bufferedReader.readLine());
        BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(inputStream1));
        System.err.println(bufferedReader1.readLine());
    }

}

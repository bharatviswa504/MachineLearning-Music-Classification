package edu.umkc.network;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;


public class Server {

    /**
     * Runs the server.
     */
    public static void main(String[] args) throws IOException {
        ServerSocket listener = new ServerSocket(9090);
        try {
            while (true) {
                Socket socket = listener.accept();
                try {
                    PrintWriter out =
                        new PrintWriter(socket.getOutputStream(), true);
                    OutputStream os = socket.getOutputStream();
                   
                    File file = new File("data\\genres\\testing\\pop\\pop.00009.au");
                    
                    byte[] data = new byte[(int) file.length()];
                    System.out.println(data.length);
                    System.out.println("File length is:" +file.length());
                    out.println(file.length());
                    out.println("Sent File Length");
                   
                    socket.close();
                    
                    
                    FileInputStream fis = new FileInputStream(file);
                    fis.read(data);
                    fis.close();

                 	Socket s = listener.accept();
                 	System.out.println("Second connection sending data");
                 	OutputStream o = s.getOutputStream();
                 	o.write(data, 0, data.length);
                 	o.flush(); 
                 	o.close();
                 	s.close();
                 	
                 	
   /*
                os.write(data,0,data.length);
                os.flush();
                socket.close();  */
                   
                   FileOutputStream fos = new FileOutputStream("sent.au");
                   fos.write(data);
                   
                   fos.close();
                   System.out.println("File transfer complete");
       
                    
                } finally {
                    socket.close();
                }
            }
        }
        finally {
            listener.close();
        }
    }
}
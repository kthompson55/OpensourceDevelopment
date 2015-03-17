package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import service.ItemService;
import service.ServerItemService;
import threads.ServerThread;

public class ItemServer {

	public static void main(String[] args) throws IOException
	{
		ItemService itemHandler = new ServerItemService();
		ExecutorService service = Executors.newFixedThreadPool(10000);
		try(ServerSocket ss = new ServerSocket(8080))
		{		
			while(true)
			{
				try
				{
					Socket s = ss.accept();
					if(s.isConnected())
					{
						service.execute(new ServerThread(s,itemHandler));
					}
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		service.shutdown();
	}

}

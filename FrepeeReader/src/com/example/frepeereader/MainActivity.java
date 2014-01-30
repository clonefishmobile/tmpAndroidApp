package com.example.frepeereader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	TextView reader;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		reader = (TextView) findViewById(R.id.readerView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void OnOpenFileClick(View view) {
		OpenFileDialog fileDialog = new OpenFileDialog(this)
        .setFilter(".*\\.txt")
        .setOpenDialogListener(new OpenFileDialog.OpenDialogListener() {
            @Override
            public void OnSelectedFile(String fileName) {
                Toast.makeText(getApplicationContext(), fileName, Toast.LENGTH_LONG).show();
                showText(fileName);
            }
        });
		fileDialog.show();
	}

	private void showText(String fileName)
	{
		reader.setText("");
		try {
	        FileReader fr = new FileReader(fileName);
	        BufferedReader br = new BufferedReader(fr);
	        String line = null;
	        try {
	        	while((line = br.readLine()) != null)
	        	{
	        	    reader.append(line);
	        	    reader.append("\n");
	        	}
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    } catch (FileNotFoundException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	}
}

package com.hw;

import android.os.Bundle;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;
import android.os.SerialPortServiceManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Queue;
import java.util.LinkedList;

@SuppressLint("HandlerLeak")
public class MainDisplay extends Activity  implements OnClickListener{
	// ����
	public static final String TAG = "Serialport-APP";
	public static final int DISPLAY_READ_HEX  = 0x000;
	public static final int DISPLAY_READ_ASC  = 0x001;
	public static final int DISPLAY_READ_HEX_F  = 0x002;
	public static final int DISPLAY_READ_ASC_F  = 0x003;
	public static final int DISPLAY_WRITE_HEX = 0x010;
	public static final int DISPLAY_WRITE_ASC = 0x011;
	private static final int ACTION_SCREEN_ON  = 0x100;
	private static final int ACTION_SCREEN_OFF = 0x101;
	private static final int ACTION_MESSAGE_ERROR = 0xfff;
	public static final int Message_display = 0x010;
	
	// cmd index
	public static final int cmd_input = 0x2;
	public static final int cmd_out   = 0x3;
	public static final int gps_power_off    = 0x100;
	public static final int gps_power_on   = 0x101;
	
	// ���յ�����ʱclean��fastģʽʱ��
	public static final int rx_max = 12000;
	
	// ���� ��
	private static Object rxlock = new Object();
	
	// SD��·��
	private static String sd_dir = "/mnt/sdcard/external_sdcard/SerialportLog/";
	private static File   file ;
	private static int    sd_state_flag = 0;
	private static boolean enableMKDir = false;
	
    // д�����
	public static byte[]  part_serialPortNode = null;
	public static int     part_baud = 0;
	public static int     part_data_size = 8;
	public static int     part_stop_bit = 1;
	public static int     part_display_hex_asc = DISPLAY_READ_ASC;
	private Handler mHandler = null;

	// �ؼ�
	private Spinner 		baud_spinner = null;
	private Spinner 		node_spinner = null;
	private Spinner 		disp_spinner = null;
	private TextView       	curDisplay = null;
	private TextView        rxDisplay = null;
	private TextView        txDisplay = null;
	private EditText        sendText = null;
	private CheckBox        send_hex = null;
	
	// �Ի���ؼ�
	private  LayoutInflater dg_li = null;
	private  LayoutInflater dg_power = null;
	private  View dg_view = null;
	private Spinner     dg_spinner_gp  = null;
	private Spinner     dg_spinner_num = null;
	private Spinner     dg_spinner_st  = null;
	
	private Button      dg_gps_button_on  = null;
	private Button      dg_gps_button_off = null;
	private DialogInterface.OnClickListener dialog_wirte;
	private int         dg_gpio_group = 0;
	private int         dg_gpio_num = 0;
	private int         dg_gpio_st = 0;
	private byte[] cmd_buffer = new byte[10];
	private int          dg_ret;
	
	// �㲥������
	private MreceiveBC     curMreceiveBC    = new MreceiveBC();
	private IntentFilter   cur_IntentFilter = new IntentFilter(Intent.ACTION_MEDIA_EJECT);
	private IntentFilter   cur_Screen_Rece  = new IntentFilter(Intent.ACTION_SCREEN_OFF);
	private static    int  broadf = 0;
	
	private String[]  baud_string;
	private String[]  node_string;
	private String[]  disp_string;
	// ��ť
	private Button    node_open;
	private Button    node_close;
	private Button    node_clean;
	private Button    node_send;
	private Button    node_save;
	// ������
	private ScrollView mScrollView;
	private int       sum_rx = 0;
	private int       sum_tx = 0;
	
	
	private SerialPortServiceManager   mSeriport;
	private read_thread   mreadTh;
	
	private byte[] read_buffer = new byte[1024];
	private byte[] write_buffer= new byte[1024];
	
	private int ret = 0;
	private int i = 0;
	private int open_state_before = 0;
	
	// ʱ�������
	private static SimpleDateFormat sDateFormat = new  SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");
	
	// ʮ�����ƹ���
	private static final String regEx = "[0-9a-fA-F]*";
	
	Queue<String> rx_queue = new LinkedList<String>();
	private String head_queue = null;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_display);
		
		// ------------------> set ui
		Resources res =getResources();
		baud_string = res.getStringArray(R.array.baud);
		node_string = res.getStringArray(R.array.index);
		disp_string = res.getStringArray(R.array.display);

		node_spinner = (Spinner)findViewById(R.id.spinner_node);
		baud_spinner = (Spinner)findViewById(R.id.spinner_baud);
		disp_spinner = (Spinner)findViewById(R.id.spinner_display);
		
		node_open  = (Button)findViewById(R.id.button_open);
		node_send  = (Button)findViewById(R.id.button_send);
		node_save  = (Button)findViewById(R.id.button_save);
		node_close = (Button)findViewById(R.id.button_close);
		node_clean = (Button)findViewById(R.id.button_clean);
		
		curDisplay = (TextView)findViewById(R.id.display_rx);
		rxDisplay  = (TextView)findViewById(R.id.text_st_rx);
		txDisplay  = (TextView)findViewById(R.id.text_st_tx);
		sendText   = (EditText)findViewById(R.id.text_send);
		send_hex   = (CheckBox)this.findViewById(R.id.cb_send_hex);
		mScrollView = (ScrollView) findViewById(R.id.sv_show);
	
		
		// hex ����¼�
		send_hex.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				sendText.setText("");
			}
		});
		
		// ---------------------> ����������
		node_spinner.setOnItemSelectedListener(cur_Item_select);
		baud_spinner.setOnItemSelectedListener(cur_Item_select);
		disp_spinner.setOnItemSelectedListener(cur_Item_select);
		node_spinner.setPrompt("ѡ���豸�ڵ�");
		baud_spinner.setPrompt("ѡ������");
		disp_spinner.setPrompt("ѡ����ʾ��ʽ");
		node_spinner.setSelection(0, true);
		baud_spinner.setSelection(5, true);
		disp_spinner.setSelection(1, true);
		
		
		// �����ļ��� 
		Log.d(TAG,"--->start to open file!");
		file = new File(sd_dir); 
		if(!file.exists()) {
			enableMKDir = file.mkdirs();
			if(enableMKDir == false) {
				Log.d(TAG,"--->log dir mkdir fail!");
			}
		} else {
			enableMKDir = true;
			Log.d(TAG,"--->log dir is exists!");
		}

		// �㲥�������ã�
		if(broadf == 0) {
			cur_Screen_Rece.addAction(Intent.ACTION_SCREEN_ON);
			cur_IntentFilter.addAction(Intent.ACTION_MEDIA_MOUNTED);
			cur_IntentFilter.addDataScheme("file");
			registerReceiver(curMreceiveBC, cur_IntentFilter);
			registerReceiver(curMreceiveBC, cur_Screen_Rece);
			broadf = 1;
			Log.d(TAG,"-------->set broadcast receiver!");
		}
		
	    // ��ť�¼���
		node_open.setOnClickListener(this);
		node_send.setOnClickListener(this);
		node_save.setOnClickListener(this);
		node_close.setOnClickListener(this);
		node_clean.setOnClickListener(this);
		
		node_close.setClickable(false);
		node_send.setClickable(false);
		
		// ȡ�ô��ڷ���
		mSeriport = new SerialPortServiceManager(0);//0--->colse log  1---->open log
		
		if(mSeriport == null) {
			Log.d(TAG, "------------------------>SerialPortServiceManager star fail!");
			node_open.setClickable(false);
		}
		
		// ------------------------------------------------>�Ի���ť�¼�
		dialog_wirte = new DialogInterface.OnClickListener() {
 
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				if(which == DialogInterface.BUTTON_POSITIVE) {
					Log.d(TAG, "------------>" + dg_gpio_group + "  " + dg_gpio_num + "  " + dg_gpio_st);
					if(gpio.gpio_check(dg_gpio_group, dg_gpio_num) == 0) {
						cmd_buffer[0] = (byte)gpio.get_gpio_num(dg_gpio_group, dg_gpio_num);
						cmd_buffer[1] = (byte)dg_gpio_st;
						dg_ret = mSeriport.cmd(cmd_out, cmd_buffer);
						if(dg_ret == 0) {
							curDisplay.append("\n\n\nGPIO�ܽŲ����ɹ�\n\n\n");
						} else if(dg_ret < 0) {
							curDisplay.append("\n\n\nGPIO�ܽŲ���ʧ��\n\n\n");
						}
					} else {
						curDisplay.append("\n\n\n�����ڴ�GPIO�ܽ�\n\n\n");
					}
					mScrollView.scrollTo(0, curDisplay.getHeight());
				} else if(which == DialogInterface.BUTTON_NEGATIVE) {
					Log.d(TAG, "------------>dg_cancel");
				} else if(which == DialogInterface.BUTTON_NEUTRAL) {
					Log.d(TAG, "------------>dg_noknow");
				}
			}
		};
		
		// �߳���Ϣ���մ���------------------------------------------------>
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch(msg.what) 
				{
					case Message_display:
						 int k = 0;
						 do {
							 synchronized (rxlock) {
								 head_queue = rx_queue.poll();
							 }
							 if(head_queue != null) {
								 if(part_display_hex_asc == DISPLAY_READ_HEX || part_display_hex_asc == DISPLAY_READ_HEX_F) {
									 
									 // curDisplay.append(help.toString(head_queue.getBytes(), 0, head_queue.length()));
									try {
										curDisplay.append(help.toString(head_queue.getBytes("iso-8859-1"), 0, head_queue.length()));
									} catch (UnsupportedEncodingException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								 } else if(part_display_hex_asc == DISPLAY_READ_ASC || part_display_hex_asc == DISPLAY_READ_ASC_F) {
									 curDisplay.append(head_queue);
								 }
								 rxDisplay.setText("RX:" + sum_rx);
								 mScrollView.scrollTo(0, curDisplay.getHeight());
								 if(part_display_hex_asc >1 && sum_rx > rx_max) {
									 node_clean.performClick();
								 }
							 } else {
								 break;
							 }
						 } while(k++ < 4);
						break;
					case ACTION_SCREEN_ON:
						if(open_state_before == 1) {
							node_open.performClick();
						}
						open_state_before = 0;
						Log.d(TAG,"----------->Hand GET ACTION_SCREEN_ON!");
						break;
					case ACTION_SCREEN_OFF:
						if(open_state_before == 1) {
							node_close.performClick();
						}
						Log.d(TAG,"----------->Hand GET ACTION_SCREEN_OFF!");
						break;
					default:
						Log.d(TAG,"----------->message error:"+msg.what);
				}
			}
		};
		// ����һ���߳�
		mreadTh = new read_thread();
		mreadTh.start();
	}
	
	public OnItemSelectedListener cur_Item_select = new OnItemSelectedListener()
	{
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1,
				int arg2, long arg3) {
			// TODO Auto-generated method stub
			if(arg0 == node_spinner) {
				part_serialPortNode = new String("/dev/" + node_string[arg2] + "\0").getBytes();  // һ��Ҫ��\0��β��
			} else if (arg0 == baud_spinner){
				part_baud = Integer.parseInt(baud_string[arg2]);
			} else if (arg0 == disp_spinner){
				part_display_hex_asc = arg2;
			} else if (arg0 == dg_spinner_gp){
				dg_gpio_group = arg2;
			} else if(arg0 == dg_spinner_num) {
				dg_gpio_num = arg2;
			} else if(arg0 == dg_spinner_st) {
				dg_gpio_st = arg2;
			} else {
				Log.d(TAG, "---------------->spinner select error");
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	// �˵���-------------------------------------------------->
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		 getMenuInflater().inflate(R.menu.main_display, menu);
		 menu.add(Menu.NONE, Menu.FIRST + 1, 1, "GPIO����");
		 menu.add(Menu.NONE, Menu.FIRST + 2, 2, "GPS/BD��Դ");
		 menu.add(Menu.NONE, Menu.FIRST + 3, 3, "δ����");
		 menu.add(Menu.NONE, Menu.FIRST + 4, 4, "δ����");
		 menu.add(Menu.NONE, Menu.FIRST + 5, 5, "δ����");	
		 
		return true;
	}
	
	 public boolean onOptionsItemSelected(MenuItem item) {
	        switch (item.getItemId()) {

	        case Menu.FIRST + 1: 
	        	// if(sendText.getText().toString().equals("emdoor")) {
	        		// ----------------------------------------->GPIO���öԻ���
	        		dg_li = LayoutInflater.from(this);
	            	dg_view = dg_li.inflate(R.layout.menu_gpio_operate, null);
	            	dg_spinner_gp  = (Spinner)dg_view.findViewById(R.id.dg_gpio_group);
	        		dg_spinner_num = (Spinner)dg_view.findViewById(R.id.dg_gpio_num);
	        		dg_spinner_st  = (Spinner)dg_view.findViewById(R.id.dg_gpio_operate);
	        		// GPIO�Ի�����������¼�
	        		dg_spinner_gp.setOnItemSelectedListener(cur_Item_select);
	        		dg_spinner_num.setOnItemSelectedListener(cur_Item_select);
	        		dg_spinner_st.setOnItemSelectedListener(cur_Item_select);
	        		// ��Ĭ��ֵ
	        		dg_spinner_gp.setSelection(dg_gpio_group, true);
	        		dg_spinner_num.setSelection(dg_gpio_num, true);
	        		dg_spinner_st.setSelection(dg_gpio_st, true);
	        		
	        		AlertDialog.Builder db_builder = new AlertDialog.Builder(this);
	        		db_builder.setTitle("GPIO�������");
	        		db_builder.setView(dg_view);
	        		db_builder.setPositiveButton("ȷ��",dialog_wirte);
	        		db_builder.setNegativeButton("ȡ��",dialog_wirte);
	        		db_builder.create();
	        		db_builder.show();
	            break;
	        case Menu.FIRST + 2:// ---------------------------------------------------->GPS power ���ƣ�
	      
	        	dg_power = LayoutInflater.from(this);
	        
        		dg_view = dg_power.inflate(R.layout.gps_power, null);
        		 
        		dg_gps_button_on  = (Button)dg_view.findViewById(R.id.dg_gps_power_on);
        		dg_gps_button_off = (Button)dg_view.findViewById(R.id.dg_gps_power_off);
       		
	    		AlertDialog.Builder db_builder_power = new AlertDialog.Builder(this);
	    		db_builder_power.setTitle("GPS/BD ��Դ����");
	    		db_builder_power.setView(dg_view);
	    		 //db_builder_power.setPositiveButton("����",null);
	    		db_builder_power.setNegativeButton("ȡ��",null);
	    		db_builder_power.create();
	    		db_builder_power.show();
	            break;
	        case Menu.FIRST + 3:
	            Toast.makeText(this, "δ����", Toast.LENGTH_LONG).show();
	            break;
	        case Menu.FIRST + 4:
	            Toast.makeText(this, "δ����", Toast.LENGTH_LONG).show();
	            break;
	        case Menu.FIRST + 5:
	            Toast.makeText(this, "δ����", Toast.LENGTH_LONG).show();
	            break;
	        default :
	        	Toast.makeText(this, "δ����", Toast.LENGTH_LONG).show();
	        }
	        return false;
	    }
	
	
	// ���ݶ�ȡ�߳� ------------------------------------------------------->
	// read serialport data thread 
	public class read_thread extends Thread {
		private int       ret_receive = 0;
		
		@Override
		public void run() {
		 while (!Thread.currentThread().isInterrupted()) {  

			 if(mSeriport.isReady() > 0) {
				 ret_receive = mSeriport.wait_data();
				 if(ret_receive > 0){
					 ret_receive = mSeriport.read(read_buffer, 100, 10);// 10ms read
				 	 if (ret_receive > 0) { 
				 		String insert_data = null;
						try {
							insert_data = new String(read_buffer, 0, ret_receive, "ISO-8859-1");
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				 	
				 		synchronized (rxlock) {
				 			rx_queue.offer(insert_data);
				 			sum_rx += ret_receive;
				 		}
				 		Message msg = new Message();  
				 		msg.what = Message_display;  
				 		mHandler.sendMessage(msg);	
				 	}
			 	}
			 }
	        try {  
	            Thread.sleep(2);  
	        } catch (InterruptedException e) {  
	            e.printStackTrace();
	        }
		 }  
		}
	}
	// ���񰴼���----------------------------------------------------------------------------->
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if(keyCode == KeyEvent.KEYCODE_BACK)
		{
			node_close.performClick();//return true;
			
			if(broadf == 1) {
				unregisterReceiver(curMreceiveBC);
				broadf = 0;
			}
		}
		return super.onKeyDown(keyCode,event);
	}
	
	protected void onDestroy() {
		super.onDestroy();
		
		node_close.performClick();
		
		if(broadf == 1) {
			unregisterReceiver(curMreceiveBC);
			broadf = 0;
		}
		
		Log.d(TAG, "----->start onDestroy");
	}
	
	protected void onStop() 
	{
		node_close.performClick();
		Log.d(TAG, "------------>onStop");
		
		if(broadf == 1) {
			unregisterReceiver(curMreceiveBC);
			broadf = 0;
		}
		super.onStop();
	}
	
	protected void onResume()
	{
		if(broadf == 0) {
			cur_Screen_Rece.addAction(Intent.ACTION_SCREEN_ON);
			cur_IntentFilter.addAction(Intent.ACTION_MEDIA_MOUNTED);
			cur_IntentFilter.addDataScheme("file");
			registerReceiver(curMreceiveBC, cur_IntentFilter);
			registerReceiver(curMreceiveBC, cur_Screen_Rece);
			broadf = 1;
			Log.d(TAG,"onResume-------->set broadcast receiver!");
		}
		Log.d(TAG,"-------->onResume!");
		super.onResume();
	}
	
	// XML��İ�ť�����¼���------------------------------------------------------------>
	public void GpsPower(View src) 
	{
		if (src.equals(dg_gps_button_on)) {
			
			dg_ret = mSeriport.cmd(gps_power_on, "nothing".getBytes()); // �򿪱���
			
			if(dg_ret != 0)
				Toast.makeText(this, "ִ��ʧ�ܣ�, ret:" + dg_ret, Toast.LENGTH_LONG).show();
			else
				Toast.makeText(this, "�ϵ���ִ�У�, ret:" + dg_ret, Toast.LENGTH_LONG).show();
		} else if (src.equals(dg_gps_button_off)) {
			
			dg_ret = mSeriport.cmd(gps_power_off, "nothing".getBytes()); // �رձ���
			if(dg_ret != 0)
				Toast.makeText(this, "ִ��ʧ�ܣ���ret:" + dg_ret, Toast.LENGTH_LONG).show();
			else
				Toast.makeText(this, "�µ���ִ�У�, ret:" + dg_ret, Toast.LENGTH_LONG).show();
		} else {
			Log.d(TAG, "no button to do.....");
			Toast.makeText(this, "no button to do.....", Toast.LENGTH_LONG).show();
		}
			
		return;
	}

	// ����ť�¼�------------------------------------------------------------------------------>
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.equals(node_open)) {
			ret = mSeriport.open(part_serialPortNode, part_baud, part_data_size, part_stop_bit); // ��һ�����ڣ�
			Log.d(TAG, new String(part_serialPortNode) +"   baud:" + part_baud + "  "+"Button open rturn:"+ret);
			if(ret > 0) {
				node_close.setClickable(true);
				node_send.setClickable(true);
				node_open.setText("�Ѵ�");
				node_close.setText("�ر�");
				node_close.setEnabled(true);
				node_open.setEnabled(false);
				
				node_spinner.setEnabled(false);
				baud_spinner.setEnabled(false);
			} else {
				AlertDialog.Builder builder = new Builder(this); 
				builder.setTitle("����"); 
				builder.setPositiveButton("ȷ��", null); 
				// builder.setIcon(android.R.drawable.xx); 
				builder.setMessage("�豸���쳣" + "(" + ret + ")"); 
				builder.show(); 
			}
		} else if (v.equals(node_close)) {
			mSeriport.close();
			node_spinner.setEnabled(true);
			baud_spinner.setEnabled(true);
			node_send.setClickable(false);
			node_close.setEnabled(false);
			node_close.setText("�ѹر�");
			node_open.setText("��");
			node_open.setEnabled(true);
			mScrollView.scrollTo(0, curDisplay.getHeight());
		} else if (v.equals(node_save)) {
			if(sum_rx == 0) {
				return;
			}
			if(android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
				if(enableMKDir == true) {
					String   file_name = sd_dir + sDateFormat.format(new java.util.Date()) + ".log";
					File file = new File(file_name);
					try {
						file.createNewFile();
						Log.d(TAG, "new a log file!");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if(file.exists()) {
						FileOutputStream out = null;
						try {
							out = new FileOutputStream(file, true);
							out.write(curDisplay.getText().toString().getBytes());
							out.flush();
							out.close();
							
							AlertDialog.Builder builder = new Builder(this);
							builder.setTitle("��ʾ"); 
							builder.setPositiveButton("ȷ��", null);
							builder.setMessage("���ݱ���ɹ�\nPath:"+file_name); 
							builder.show(); 
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							Log.d(TAG,"Write Log file faid!");
						}
					} else {
						Log.d(TAG,"MK Log file faid!");
					}
					Log.d(TAG,"Log file name:" + file_name);
				} else {
					Log.d(TAG,"there is not log dir.");
				}
			} else {
				AlertDialog.Builder builder = new Builder(this); 
				builder.setTitle("��ʾ"); 
				builder.setPositiveButton("ȷ��", null); 
				// builder.setIcon(android.R.drawable.xx); 
				builder.setMessage("�����SD�����Ա�������"); 
				builder.show(); 
			}
		} else if (v.equals(node_send)) {
			Log.d(TAG, "Button send");
			String write_string = sendText.getText().toString();
			if(write_string.length() == 0)
			{
				Log.d(TAG, "Send data is null!");
				return;
			}
			if(send_hex.isChecked()) {
				write_string = write_string.replace(" ", "");
				if(write_string.matches(regEx)) {
					ret = mSeriport.write(help.toByteArray(write_string), write_string.length()/2);
				} else {
					AlertDialog.Builder builder = new Builder(this); 
					builder.setTitle("����"); 
					builder.setPositiveButton("ȷ��", null); 
					// builder.setIcon(android.R.drawable.xx); 
					builder.setMessage("��ȷ�����������Ϊʮ������"); 
					builder.show(); 
				}
			} else {
				write_buffer = write_string.getBytes();
				ret = mSeriport.write(write_buffer, write_buffer.length);
			}
			
			if(ret > 0) {
				sum_tx += ret;
				txDisplay.setText("TX:" + sum_tx);
			}
			Log.d(TAG, "Write:" + write_string);
		} else if (v.equals(node_clean)) {
			curDisplay.setText("");
			rxDisplay.setText("RX:0");
			txDisplay.setText("TX:0");
			sum_rx = 0;
			sum_tx = 0;
		} else if (v.equals(send_hex)){
			Log.d(TAG, "Button HEX");
		} else {
			Log.d(TAG, "Button select error");
		}
	}
	
	// �㲥����ע����--------------------------------------->
	public class MreceiveBC extends BroadcastReceiver{
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			final String action = intent.getAction();
			int   flag_send = 0;
			
			Message msg = Message.obtain();
			Log.d(TAG, "Receive broadcast:" + action);
			if (Intent.ACTION_SCREEN_ON.equals(action)) {
				msg.what = ACTION_SCREEN_ON;
				flag_send =  1;
	        } else if(Intent.ACTION_SCREEN_OFF.equals(action)){
	            msg.what = ACTION_SCREEN_OFF;
	            flag_send = 1;
	            if(!node_open.isEnabled()) {
	            	open_state_before = 1;
	            }
	        } else if(Intent.ACTION_MEDIA_MOUNTED.equals(action)){
	        	flag_send = 0;
	        	// ������־Ŀ¼����SD���ϣ�
	        	if(!file.exists()) {
	    			enableMKDir = file.mkdirs();
	    			if(enableMKDir == false) {
	    				Log.d(TAG,"---->log dir mkdir fail!");
	    			}
	    		} else {
	    			enableMKDir = true;
	    			Log.d(TAG,"---->log dir is exists!");
	    		}
	        } else if(Intent.ACTION_MEDIA_EJECT.equals(action)){
	        	flag_send = 0;
	        	// ������־Ŀ¼����SD���ϣ�
	        	if(!file.exists()) {
	    			enableMKDir = file.mkdirs();
	    			if(enableMKDir == false) {
	    				Log.d(TAG,"---->log dir mkdir fail!");
	    			}
	    		} else {
	    			enableMKDir = true;
	    			Log.d(TAG,"---->log dir is exists!");
	    		}
	        } else {
	        	msg.what = ACTION_MESSAGE_ERROR;
	        }
			if (mHandler != null && flag_send == 1)
				mHandler.sendMessage(msg);
		}
	}
}


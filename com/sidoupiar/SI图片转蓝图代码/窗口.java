package com.sidoupiar.SI图片转蓝图代码;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;

public final class 窗口 extends JFrame implements 输出器 , ActionListener , MouseListener , MouseMotionListener
{
	private static final long serialVersionUID = -6433101588930175699L;
	
	
	
	private JLabel 标题标签;
	private JButton 关闭按钮;
	
	private JLabel 图片位置标签;
	private JLabel 蓝图位置标签;
	private JTextField 图片位置文本条;
	private JTextField 蓝图位置文本条;
	private JButton 图片位置按钮;
	private JButton 蓝图位置按钮;
	
	private JButton 开始转换按钮;
	private JTextArea 输出文本区;
	private JScrollPane 输出滚动;
	
	private 操作 操作;
	
	private boolean 控制鼠标输入;
	private Point 鼠标位置;
	
	public 窗口()
	{
		super( 常数.标题 );
		
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int width = 常数.窗口宽度;
		int height = 常数.窗口高度;
		
		this.setBounds( (d.width-width)/2 , (d.height-height)/2 , width , height );
		this.setFocusable( false );
		this.setUndecorated( true );
		this.setLayout( null );
		
		this.addMouseListener( this );
		this.addMouseMotionListener( this );
		
		this.初始化();
		this.调整布局( width , height );
	}
	
	private final void add( JLabel c )
	{
		super.add( c );
		c.setFocusable( false );
	}
	
	private final void add( JButton c )
	{
		super.add( c );
		c.setFocusable( false );
		c.setContentAreaFilled( false );
		c.addActionListener( this );
	}
	
	private final void add( JTextField c )
	{
		super.add( c );
	}
	
	
	
	private void 初始化()
	{
		this.标题标签 = new JLabel( 常数.标题 , JLabel.CENTER );
		this.关闭按钮 = new JButton( 常数.关闭 );
		this.add( this.标题标签 );
		this.add( this.关闭按钮 );
		this.关闭按钮.setBorderPainted( false );
		this.关闭按钮.setForeground( Color.RED );
		
		this.图片位置标签 = new JLabel( 常数.图片位置 , JLabel.RIGHT );
		this.蓝图位置标签 = new JLabel( 常数.蓝图位置 , JLabel.RIGHT );
		this.图片位置文本条 = new JTextField();
		this.蓝图位置文本条 = new JTextField();
		this.图片位置按钮 = new JButton( 常数.选择 );
		this.蓝图位置按钮 = new JButton( 常数.选择 );
		this.add( this.图片位置标签 );
		this.add( this.蓝图位置标签 );
		this.add( this.图片位置文本条 );
		this.add( this.蓝图位置文本条 );
		this.add( this.图片位置按钮 );
		this.add( this.蓝图位置按钮 );
		
		this.开始转换按钮 = new JButton( 常数.开始转换按钮 );
		this.输出文本区 = new JTextArea();
		this.输出滚动 = new JScrollPane( this.输出文本区 );
		this.add( this.开始转换按钮 );
		this.add( this.输出滚动 );
	}
	
	private void 调整布局( int width , int height )
	{
		if( this.标题标签 != null ) this.标题标签.setBounds( 0 , 0 , width-40 , 20 );
		if( this.关闭按钮 != null ) this.关闭按钮.setBounds( width-40 , 0 , 50 , 20 );
		
		if( this.图片位置标签 != null ) this.图片位置标签.setBounds( 20 , 45 , 80 , 20 );
		if( this.蓝图位置标签 != null ) this.蓝图位置标签.setBounds( 20 , 70 , 80 , 20 );
		if( this.图片位置文本条 != null ) this.图片位置文本条.setBounds( 105 , 45 , width-210 , 20 );
		if( this.蓝图位置文本条 != null ) this.蓝图位置文本条.setBounds( 105 , 70 , width-210 , 20 );
		if( this.图片位置按钮 != null ) this.图片位置按钮.setBounds( width-100 , 45 , 80 , 20 );
		if( this.蓝图位置按钮 != null ) this.蓝图位置按钮.setBounds( width-100 , 70 , 80 , 20 );
		
		if( this.开始转换按钮 != null ) this.开始转换按钮.setBounds( width/2-100 , 95 , 200 , 45 );
		if( this.输出滚动 != null ) this.输出滚动.setBounds( 20 , 145 , width-40 , height-165 );
	}
	
	
	
	@Override
	public final void 清空()
	{
		this.输出文本区.setText( "" );
	}
	
	@Override
	public final void 输出( String 输出 )
	{
		this.输出文本区.setText( 输出+"\n"+this.输出文本区.getText() );
	}
	
	@Override
	public final void actionPerformed( ActionEvent e )
	{
		Object button = e.getSource();
		if( button == this.关闭按钮 ) System.exit( 0 );
		else if( button == this.图片位置按钮 ) this.弹出位置选择窗口( JFileChooser.OPEN_DIALOG , this.图片位置文本条 );
		else if( button == this.蓝图位置按钮 ) this.弹出位置选择窗口( JFileChooser.SAVE_DIALOG , this.蓝图位置文本条 );
		else if( button == this.开始转换按钮 )
		{
			if( this.操作 == null || this.操作.已完成() )
			{
				this.清空();
				
				String 图片位置 = this.图片位置文本条.getText();
				String 蓝图位置 = this.蓝图位置文本条.getText();
				
				if( 图片位置 == null || "".equals( 图片位置 ) )
				{
					this.输出( 常数.无图片位置 );
					return ;
				}
				if( 蓝图位置 == null || "".equals( 蓝图位置 ) )
				{
					this.输出( 常数.无蓝图位置 );
					return ;
				}
				if( ! new File( 图片位置 ).exists() )
				{
					this.输出( 常数.无图片文件 );
					return ;
				}
				
				this.操作 = new 操作( this , 图片位置 , 蓝图位置 );
				new Thread( this.操作 ).start();
			}
			else this.输出( 常数.请等待 );
		}
	}
	
	@Override
	public void mouseClicked( MouseEvent e )
	{
		
	}
	
	@Override
	public void mousePressed( MouseEvent e )
	{
		int button = e.getButton();
		if( button == MouseEvent.BUTTON1 || button == MouseEvent.BUTTON3 )
		{
			this.控制鼠标输入 = true;
			this.鼠标位置 = e.getPoint();
		}
	}
	
	@Override
	public void mouseReleased( MouseEvent e )
	{
		this.控制鼠标输入 = false;
	}
	
	@Override
	public void mouseEntered( MouseEvent e )
	{
		
	}
	
	@Override
	public void mouseExited( MouseEvent e )
	{
		
	}
	
	@Override
	public void mouseDragged( MouseEvent e )
	{
		if( this.控制鼠标输入 )
		{
			Point 窗口 = this.getLocation();
			this.setLocation( 窗口.x+e.getX()-this.鼠标位置.x , 窗口.y+e.getY()-this.鼠标位置.y );
		}
	}
	
	@Override
	public void mouseMoved( MouseEvent e )
	{
		
	}
	
	
	
	private void 弹出位置选择窗口( int 类型 , JTextField 文本条 )
	{
		JFileChooser 选择器 = new JFileChooser();
		选择器.setCurrentDirectory( new File( FileSystemView.getFileSystemView().getHomeDirectory().getPath().replace( "\\" , "/" )+"/" ) );
		选择器.setFileSelectionMode( JFileChooser.FILES_ONLY );
		选择器.setDialogType( 类型 );
		int 结果 = 选择器.showOpenDialog( this );
		if( 结果 == JFileChooser.APPROVE_OPTION )
		{
			String 位置 = 选择器.getSelectedFile().getAbsolutePath();
			if( 位置 == null || "".equals( 位置 ) ) this.输出( 常数.无位置 );
			else
			{
				文本条.setText( 位置 );
				this.输出( 常数.选择位置+位置 );
			}
		}
		else this.输出( 常数.取消选择位置 );
	}
}
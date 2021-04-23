package com.sidoupiar.SI图片转蓝图代码;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.zip.Deflater;

import javax.imageio.ImageIO;

public final class 操作 implements Runnable
{
	private 输出器 输出器;
	private String 图片位置;
	private String 蓝图位置;
	
	private boolean 已完成;
	
	public 操作( 输出器 输出器 , String 图片位置 , String 蓝图位置 )
	{
		this.输出器 = 输出器;
		this.图片位置 = 图片位置;
		this.蓝图位置 = 蓝图位置;
		
		this.已完成 = false;
	}
	
	
	
	public final boolean 已完成()
	{
		return this.已完成;
	}
	
	
	
	@Override
	public final void run()
	{
		try
		{
			this.输出器.输出( "开始操作" );
			
			File 文件 = new File( this.蓝图位置 );
			if( ! 文件.getParentFile().exists() ) 文件.getParentFile().mkdirs();
			
			String 头部 = "{\"blueprint\":{\"icons\":[{\"signal\":{\"type\":\"item\",\"name\":\"sicw-item-像素块母版\"},\"index\":1}],\"entities\":[";
			String 尾部 = "],\"item\":\"blueprint\",\"version\":"+System.currentTimeMillis()+"}}";
			
			BufferedImage 图片 = ImageIO.read( new File( this.图片位置 ) );
			BufferedWriter 写入器 = new BufferedWriter( new OutputStreamWriter( new FileOutputStream( this.蓝图位置 ) , "utf-8" ) );
			double 横坐标偏移量 = ( double ) 图片.getWidth() / 2D + 0.5D;
			double 纵坐标偏移量 = ( double ) 图片.getHeight() / 2D + 0.5D;
			
			StringBuffer 原始代码 = new StringBuffer( 头部 );
			int 编号 = 0;
			Color 颜色;
			int 红 , 绿 , 蓝;
			for( int 横坐标 = 0 , 横坐标最大值 = 图片.getWidth() ; 横坐标 < 横坐标最大值 ; 横坐标 ++ )
			{
				for( int 纵坐标 = 0 , 纵坐标最大值 = 图片.getHeight() ; 纵坐标 < 纵坐标最大值 ; 纵坐标 ++ )
				{
					编号 ++;
					颜色 = new Color( 图片.getRGB( 横坐标 , 纵坐标 ) );
					红 = ( int ) Math.floor( (double)颜色.getRed()/32D+0.5D ) * 32;
					绿 = ( int ) Math.floor( (double)颜色.getGreen()/32D+0.5D ) * 32;
					蓝 = ( int ) Math.floor( (double)颜色.getBlue()/32D+0.5D ) * 32;
					原始代码.append( "{\"entity_number\":"+编号+",\"name\":\"sicw-simple-像素块-"+红+"-"+绿+"-"+蓝+"\",\"position\":{\"x\":"+((double)横坐标-横坐标偏移量)+",\"y\":"+((double)纵坐标-纵坐标偏移量)+"},\"variation\":1}" );
					if( 横坐标 < 横坐标最大值-1 || 纵坐标 < 纵坐标最大值-1 ) 原始代码.append( "," );
				}
			}
			原始代码.append( 尾部 );
			this.输出器.输出( "生成结果长度："+原始代码.length() );
			
			this.output( 写入器 , this.zip( 原始代码.toString() ) );
			写入器.close();
			
			this.输出器.输出( "操作完毕" );
		}
		catch( IOException e )
		{
			e.printStackTrace();
			this.输出器.输出( "执行操作失败：文件流错误" );
		}
		
		this.已完成 = true;
	}
	
	private ByteArrayOutputStream zip( String 原始代码 )
	{
		int 缓冲大小 = 1024;
		
		byte[] 原始数据 = 原始代码.getBytes();
		this.输出器.输出( "流长度："+原始数据.length );
		Deflater 压缩器 = new Deflater();
		
		压缩器.reset();
		压缩器.setInput( 原始数据 , 0 , 原始数据.length );
		压缩器.finish();
		
		ByteArrayOutputStream 字节流 = new ByteArrayOutputStream();
		byte[] 缓冲数据 = new byte[缓冲大小];
		int length;
		while( ! 压缩器.finished() )
		{
			length = 压缩器.deflate( 缓冲数据 , 0 , 缓冲大小 );
			字节流.write( 缓冲数据 , 0 , length );
		}
		压缩器.end();
		
		return 字节流;
	}
	
	private void output( BufferedWriter 写入器 , ByteArrayOutputStream 字节流 ) throws IOException
	{
		Encoder 编码器 = Base64.getEncoder();
		String 最终输出 = "0" + 编码器.encodeToString( 字节流.toByteArray() );
		
		this.输出器.输出( "输出结果长度："+最终输出.length() );
		写入器.write( 最终输出 );
		写入器.flush();
	}
}
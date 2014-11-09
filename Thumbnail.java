package cn.com.cheney;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;

import javax.imageio.ImageIO;
public class Thumbnail {
	/**
	 * 生成缩略图 
	 * fromFileStr:原图片路径
	 *  saveToFileStr:缩略图路径 
	 *  width:缩略图的宽 
	 *  height:缩略图的高
	 */
	public static void saveImageAsJpg(String fromFileStr, String saveToFileStr,
			int width, int height,boolean equalProportion) throws Exception {
		BufferedImage srcImage;
        String imgType="JPEG";
        if(fromFileStr.toLowerCase().endsWith(".png")){
        	imgType="PNG";
        } else if(fromFileStr.toLowerCase().endsWith(".gif")){
        	imgType="GIF";
        }
        File fromFile=new File(fromFileStr);
        File saveFile=new File(saveToFileStr);
        srcImage=ImageIO.read(fromFile);
        
        if(width>0||height>0){
        	srcImage=resize(srcImage,width,height,equalProportion);
        }
        ImageIO.write(srcImage,imgType,saveFile);
	}
    
	/**
	 * 将原图片的BufferedImage对象生成缩略图
	 * source：原图片的BufferedImage对象
	 * targetW:缩略图的宽
	 * targetH:缩略图的高
	 */
	public static BufferedImage resize(BufferedImage source,int targetW,int targetH,boolean equalProportion){
		int type=source.getType();
		BufferedImage target=null;
		double sx=(double)targetW/source.getWidth();
		double sy=(double)targetH/source.getHeight();
		//这里想实现在targetW，targetH范围内实现等比例的缩放
		  //如果不需要等比例的缩放则下面的if else语句注释调即可
		if(equalProportion){
			if(sx>sy){
				sx=sy;
				targetW=(int)(sx*source.getWidth());
			}else{
				sy=sx;
				targetH=(int)(sx*source.getHeight());
			}
		}
		if(type==BufferedImage.TYPE_CUSTOM){
			ColorModel cm=source.getColorModel();
			WritableRaster raster=cm.createCompatibleWritableRaster(targetW,targetH);
		    boolean alphaPremultiplied=cm.isAlphaPremultiplied();
		    target=new BufferedImage(cm,raster,alphaPremultiplied,null);
		}else{
			target=new BufferedImage(targetW,targetH,type);
			Graphics2D g=target.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
			g.drawRenderedImage(source,AffineTransform.getScaleInstance(sx,sy));
			g.dispose();
		}
		return target;
	}
	
public static void getGifImage(File srcImg, File destImg, int width,
			int height, boolean smooth) {
		try {
			GifImage gifImage = GifDecoder.decode(srcImg);// 创建一个GifImage对象
			GifImage resizedGifImage2 = GifTransformer.scale(gifImage, 0.3,
					0.3, smooth);// 1.缩放重新更改大小.
			GifEncoder.encode(resizedGifImage2, destImg, true);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static void main(String[] args){
		try{
			Thumbnail.saveImageAsJpg("/Users/cheney/Downloads/WL002765_4201612.gif", "/Users/cheney/Downloads/SCAL-1-WL002765_4201612.gif", 400, 300,true);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}


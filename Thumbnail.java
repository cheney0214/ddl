package cn.com.cheney.image;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;

import javax.imageio.ImageIO;

import com.gif4j.GifDecoder;
import com.gif4j.GifEncoder;
import com.gif4j.GifImage;
import com.gif4j.GifTransformer;

public class ImageUtils {
	
	/**
	 * 
	 * @param srcImage
	 * @param destImage
	 * @param width
	 * @param height
	 * @param equalProportion
	 * @throws Exception
	 */
	public static void scaleImage(String srcImage, String destImage, int width,
			int height, boolean equalProportion) throws Exception {
		String imgType = null;
		
		if (srcImage.toLowerCase().endsWith(".png")) {
			imgType = "PNG";
		} else if (srcImage.toLowerCase().endsWith(".jpg")) {
			imgType = "JPEG";
		} else if (srcImage.toLowerCase().endsWith(".gif")) {
			scaleGifImage(srcImage, destImage, width, height, equalProportion);

			return;
		} else {
			throw new UnsupportedOperationException("image type not support.");
		}

		BufferedImage source = ImageIO.read(new File(srcImage));
		source = resize(source, width, height, equalProportion);
		ImageIO.write(source, imgType, new File(destImage));
	}
	
	/**
	 * 
	 * @param source
	 * @param width
	 * @param height
	 * @param equalProportion
	 * @return
	 */
	private static BufferedImage resize(BufferedImage source, int width,
			int height, boolean equalProportion) {
		double w = (double) width / source.getWidth();
		double h = (double) height / source.getHeight();

		if (equalProportion) {
			if (w > h) {
				w = h;
				width = (int) (w * source.getWidth());
			} else {
				h = w;
				height = (int) (w * source.getHeight());
			}
		}

		BufferedImage target = null;
		int type = source.getType();
		if (type == BufferedImage.TYPE_CUSTOM) {
			ColorModel cm = source.getColorModel();
			WritableRaster raster = cm.createCompatibleWritableRaster(width,
					height);
			boolean alphaPremultiplied = cm.isAlphaPremultiplied();
			target = new BufferedImage(cm, raster, alphaPremultiplied, null);
		} else {
			target = new BufferedImage(width, height, type);
			Graphics2D g = target.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_RENDERING,
					RenderingHints.VALUE_RENDER_QUALITY);
			g.drawRenderedImage(source, AffineTransform.getScaleInstance(w, h));
			g.dispose();
		}

		return target;
	}
	
	/**
	 * 
	 * @param srcImg
	 * @param destImg
	 * @param width
	 * @param height
	 * @param equalProportion
	 * @throws Exception
	 */
	private static void scaleGifImage(String srcImg, String destImg, int width,
			int height, boolean equalProportion) throws Exception {
		BufferedImage source = ImageIO.read(new File(srcImg));
		double w = (double) width / source.getWidth();
		double h = (double) height / source.getHeight();

		if (equalProportion) {
			if (w > h) {
				w = h;
				width = (int) (w * source.getWidth());
			} else {
				h = w;
				height = (int) (w * source.getHeight());
			}
		}

		GifImage gifImage = GifDecoder.decode(new File(srcImg));
		GifImage resizedGifImage2 = GifTransformer.resize(gifImage, width,
				height, true);
		GifEncoder.encode(resizedGifImage2, new File(destImg), true);

	}
}

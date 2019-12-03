package de.elbosso.tools.svgrasterizer;

/**
 * ***************************************************************************
 * Copyright (C) The Apache Software Foundation. All rights reserved. *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the Apache Software License *
 * version 1.1, a copy of which has been included with this distribution in *
 * the LICENSE file. *
 *
 ****************************************************************************
 */

import de.elbosso.util.Utilities;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.TranscodingHints;
import org.apache.batik.transcoder.image.ImageTranscoder;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.svg.SVGDocument;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;

/**
 * This class provides a simple and method based API for converting a SVG
 * document fragment to a <tt>BufferedImage</tt>.
 *
 * @author <a href="mailto:Thierry.Kormann@sophia.inria.fr">Thierry Kormann</a>
 * @version $Id$
 */
public class SVGRasterizer
{
	private final static org.apache.log4j.Logger CLASS_LOGGER = org.apache.log4j.Logger.getLogger(SVGRasterizer.class);
	private final static java.lang.String format = "png";

	private static javax.swing.JPanel panel;
	private static javax.swing.JScrollPane scroller;
	/**
	 * The transcoder input.
	 */
	protected TranscoderInput input;

	/**
	 * The transcoder hints.
	 */
	protected TranscodingHints hints = new TranscodingHints();

	/**
	 * The image that represents the SVG document.
	 */
	protected BufferedImage img;

	/**
	 * Constructs a new SVGRasterizer.
	 *
	 * @param uri the uri of the document to rasterize
	 */
	public SVGRasterizer(String uri)
	{
		this.input = new TranscoderInput(uri);
	}

	/**
	 * Constructs a new SVGRasterizer.
	 *
	 * @param url the URL of the document to rasterize
	 */
	public SVGRasterizer(URL url)
	{
		this.input = new TranscoderInput(url.toString());
	}

	/**
	 * Constructs a new SVGRasterizer converter.
	 *
	 * @param istream the input stream that represents the SVG document to
	 * rasterize
	 */
	public SVGRasterizer(InputStream istream)
	{
		this.input = new TranscoderInput(istream);
	}

	/**
	 * Constructs a new SVGRasterizer converter.
	 *
	 * @param reader the reader that represents the SVG document to rasterize
	 */
	public SVGRasterizer(Reader reader)
	{
		this.input = new TranscoderInput(reader);
	}

	/**
	 * Constructs a new SVGRasterizer converter.
	 *
	 * @param document the SVG document to rasterize
	 */
	public SVGRasterizer(SVGDocument document)
	{
		this.input = new TranscoderInput(document);
	}

	/**
	 * Returns the image that represents the SVG document.
	 */
	public BufferedImage createBufferedImage() throws TranscoderException
	{
		Rasterizer r = new Rasterizer();
		r.setTranscodingHints((Map) hints);
		r.transcode(input, null);
		return img;
	}

	/**
	 * Sets the width of the image to rasterize.
	 *
	 * @param width the image width
	 */
	public void setImageWidth(float width)
	{
		hints.put(ImageTranscoder.KEY_WIDTH, Float.valueOf(width));
	}

	/**
	 * Sets the height of the image to rasterize.
	 *
	 * @param height the image height
	 */
	public void setImageHeight(float height)
	{
		hints.put(ImageTranscoder.KEY_HEIGHT, Float.valueOf(height));
	}

	/**
	 * Sets the preferred language to use. SVG documents can provide text in
	 * multiple languages, this method lets you control which language to use if
	 * possible. e.g. "en" for english or "fr" for french.
	 *
	 * @param language the preferred language to use
	 */
	public void setLanguages(String language)
	{
		hints.put(ImageTranscoder.KEY_LANGUAGE, language);
	}

	/**
	 * Sets the unit conversion factor to the specified value. This method lets
	 * you choose how units such as 'em' are converted. e.g. 0.26458 is 96dpi
	 * (the default) or 0.3528 is 72dpi.
	 *
	 * @param px2mm the pixel to millimeter convertion factor.
	 */
	public void setPixelToMMFactor(float px2mm)
	{
		hints.put(ImageTranscoder.KEY_PIXEL_UNIT_TO_MILLIMETER, Float.valueOf(px2mm));
	}

	/**
	 * Sets the uri of the user stylesheet. The user stylesheet can be used to
	 * override styles.
	 *
	 * @param uri the uri of the user stylesheet
	 */
	public void setUserStyleSheetURI(String uri)
	{
		hints.put(ImageTranscoder.KEY_USER_STYLESHEET_URI, uri);
	}

	/**
	 * Sets whether or not the XML parser used to parse SVG document should be
	 * validating or not, depending on the specified parameter. For futher
	 * details about how media work, see the
	 * <a href="http://www.w3.org/TR/CSS2/media.html">Media types in the CSS2
	 * specification</a>.
	 *
	 * @param b true means the XML parser will validate its input
	 */
	public void setXMLParserValidating(boolean b)
	{
		hints.put(ImageTranscoder.KEY_XML_PARSER_VALIDATING,
				(b ? Boolean.TRUE : Boolean.FALSE));
	}

	/**
	 * Sets the media to rasterize. The medium should be separated by comma.
	 * e.g. "screen", "print" or "screen, print"
	 *
	 * @param media the media to use
	 */
	public void setMedia(String media)
	{
		hints.put(ImageTranscoder.KEY_MEDIA, media);
	}

	/**
	 * Sets the alternate stylesheet to use. For futher details, you can have a
	 * look at the <a href="http://www.w3.org/TR/xml-stylesheet/">Associating
	 * Style Sheets with XML documents</a>.
	 *
	 * @param alternateStylesheet the alternate stylesheet to use if possible
	 */
	public void setAlternateStylesheet(String alternateStylesheet)
	{
		hints.put(ImageTranscoder.KEY_ALTERNATE_STYLESHEET,
				alternateStylesheet);
	}

	/**
	 * Sets the Paint to use for the background of the image.
	 *
	 * @param p the paint to use for the background
	 */
	public void setBackgroundColor(Paint p)
	{
		hints.put(ImageTranscoder.KEY_BACKGROUND_COLOR, p);
	}

	/**
	 * An image transcoder that stores the resulting image.
	 */
	protected class Rasterizer extends ImageTranscoder
	{

		public BufferedImage createImage(int w, int h)
		{
			return new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		}

		public void writeImage(BufferedImage img, TranscoderOutput output)
				throws TranscoderException
		{
			SVGRasterizer.this.img = img;
		}
	}

	public static java.util.List<java.io.File> workOnFile(final java.io.File svg, Configuration conf, javax.imageio.ImageWriter writer, javax.imageio.ImageWriteParam iwp,MultiResolutionHelper multiResolutionHelper, java.util.Collection<java.lang.Integer> customSizes) throws MalformedURLException, ParserConfigurationException, SAXException, IOException, XPathExpressionException, TransformerConfigurationException, TransformerException, TranscoderException
	{
		java.io.FileInputStream fis=new java.io.FileInputStream(svg);
		java.lang.String svgs= Utilities.readIntoString(fis);
		fis.close();

		java.util.regex.Pattern patt=java.util.regex.Pattern.compile(".*?(inkscape:label=\"\\!(.*?)\".*?style=\"display:none\").*?",java.util.regex.Pattern.MULTILINE|java.util.regex.Pattern.DOTALL);
		java.util.regex.Matcher match=patt.matcher(svgs);
		boolean hasSymbols=false;
		java.util.List<java.io.File> rv=new java.util.LinkedList();
		while(match.find())
		{
			java.io.File dir=svg.getParentFile();
			java.io.File svgdir=new java.io.File(dir,"symbols");
			if(svgdir.exists()==false)
				svgdir.mkdirs();
			hasSymbols=true;
			if(CLASS_LOGGER.isDebugEnabled())CLASS_LOGGER.debug(match.groupCount()+" "+match.group(2));
			java.lang.String name=svg.getName().substring(0,svg.getName().toUpperCase().indexOf(".SVG"))+"_"+match.group(2)+".svg";
			java.io.File result=new java.io.File(svgdir,name);
			if(CLASS_LOGGER.isDebugEnabled())CLASS_LOGGER.debug(name);
			java.io.PrintWriter pw=new java.io.PrintWriter(result);
			java.lang.String o=match.group(1);
			java.lang.String n=match.group(1).replace("display:none","display:inline");
			java.lang.String svgm=svgs.replace(o, n);//.("(<g.*?inkscape:label=\"!"+match.group(1)+"\".*?style=\"display:)(none)(\")","$1visible$3");
			pw.print(svgm);
			pw.close();
			rv.addAll(workOnFileImpl(result, conf, writer, iwp,multiResolutionHelper, customSizes));
//			break;
		}
//		if(hasSymbols==false)
			rv.addAll(workOnFileImpl(svg, conf, writer, iwp,multiResolutionHelper, customSizes));
		return rv;
	}
	public static java.util.List<java.io.File> workOnFileImpl(final java.io.File svg, Configuration conf, javax.imageio.ImageWriter writer, javax.imageio.ImageWriteParam iwp,MultiResolutionHelper multiResolutionHelper, java.util.Collection<java.lang.Integer> customSizes) throws MalformedURLException, ParserConfigurationException, SAXException, IOException, XPathExpressionException, TransformerConfigurationException, TransformerException, TranscoderException
	{
		java.util.List rv = new java.util.LinkedList();
		java.net.URL turl = svg.toURI().toURL();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document = db.parse(turl.openStream());	//					de.elbosso.util.Utilities.sopln(turl);
//						XPathAPI xpathAPI = new XPathAPI();
//			org.w3c.dom.NodeList nl = xpathAPI.selectNodeList(document.getDocumentElement(), "//@style");
//			for()
		XPath xpath = XPathFactory.newInstance().newXPath();

		NodeList list = (NodeList) xpath.evaluate("//@style", document, XPathConstants.NODESET);
		java.util.regex.Pattern pat = java.util.regex.Pattern.compile(".*stroke-width:(\\d*?\\.\\d*?);.*");
		for (int i = 0; i < list.getLength(); i++)
		{
			Node node = list.item(i);
			String title = (String) xpath.evaluate(".", node, XPathConstants.STRING);
			java.util.regex.Matcher m = pat.matcher(title);
			if (m.matches())
			{

				double d = java.lang.Double.parseDouble(m.group(1));
				if (d < 2)
				{

					Attr attr = (Attr) node;
					attr.getOwnerElement().setAttribute("style", title.replaceAll("stroke-width:(\\d*?\\.\\d*?);", "stroke-width:2.0;"));
				}
			}

		}
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer trans = factory.newTransformer();
		trans.setOutputProperties(new java.util.Properties());
		Source src = new DOMSource(document.getDocumentElement());
		java.io.File tfile = null;
		try
		{
			tfile = java.io.File.createTempFile(System.getProperty("user.name") + "iconrasterizer", ".xml");
		}
		catch (java.lang.Throwable t)
		{
			tfile = java.io.File.createTempFile("iconrasterizer", ".xml");
		}

		tfile.deleteOnExit();
		java.io.OutputStream os = new java.io.FileOutputStream(tfile);
		Result res = new StreamResult(os);
		trans.transform(src, res);
		os.close();
//        SVGRasterizer r = new SVGRasterizer(new SSHFile(args[0]).toURI().toURL());
		String parser = org.apache.batik.util.XMLResourceDescriptor.getXMLParserClassName();
		org.apache.batik.anim.dom.SAXSVGDocumentFactory fact = new org.apache.batik.anim.dom.SAXSVGDocumentFactory(parser);

		final java.io.File dir = new java.io.File(svg.getParentFile(), "bitmap");
		dir.mkdirs();
		final java.io.File smalldir = new java.io.File(dir, "small");
		smalldir.mkdirs();
		final java.io.File meddir = new java.io.File(dir, "medium");
		meddir.mkdirs();
		final java.io.File pamdir = new java.io.File(dir, "pam");
		pamdir.mkdirs();
		final java.io.File androiddir = new java.io.File(dir, "android");
		androiddir.mkdirs();
		SVGRasterizer r = new SVGRasterizer((SVGDocument) (fact.createDocument((conf.isModifyStrokeWidths()?tfile:svg).toURI().toURL().toString())));
//        r.setBackgroundColor(java.awt.Color.white);
//		r.setImageWidth(48);
//		r.setImageHeight(48);
		BufferedImage img = r.createBufferedImage();
		java.io.File f = new java.io.File(dir, svg.getName().substring(0, svg.getName().length() - 4) + "_orig." + format);
		javax.imageio.ImageIO.write(img, format, f);
		rv.add(f);
		f = new java.io.File(meddir, svg.getName().substring(0, svg.getName().length() - 4) + "." + format);
		javax.imageio.ImageIO.write(img, format, f);
		rv.add(f);
		int w = 128;
		int h = 128;
		if (img.getWidth() < img.getHeight())
		{
			w = img.getWidth() * 128 / img.getHeight();
		}
		else
		{
			h = img.getHeight() * 128 / img.getWidth();
		}
		r.setImageWidth(w);
		r.setImageHeight(h);
		img = r.createBufferedImage();
		f = new java.io.File(smalldir, svg.getName().substring(0, svg.getName().length() - 4) + "." + format);
		javax.imageio.ImageIO.write(img, format, f);
		rv.add(f);
//        javax.swing.JFrame f = new javax.swing.JFrame();
//		javax.swing.JPanel p=new javax.swing.JPanel(new java.awt.GridLayout(1, 0));
//		javax.swing.JLabel l=new javax.swing.JLabel(new javax.swing.ImageIcon(img));
//		l.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
//        p.add(l, java.awt.BorderLayout.CENTER);

		java.io.File ppf=null;
		if(conf.isPam())
		{
			if (writer != null)
			{
				r.setImageWidth(48);
				r.setImageHeight(48);
				img = r.createBufferedImage();
				java.awt.image.BufferedImage muster = img;
				int colors = 255;
				if ((iwp != null) && (iwp.getCompressionQuality() == 1))
				{
					colors = 65535;
				}
				{
	//				int pixels[][] = getPixels(img);
					int pixels[][] = getPixels(img, 5, 5, 5);
					int palette[] = de.netsysit.ui.image.Quantize.quantizeImage(pixels, colors);
					pixels = de.netsysit.ui.image.FloydSteinbergDither.floydSteinbergDither(getRGBTriples(img), palette);
					if(CLASS_LOGGER.isTraceEnabled())CLASS_LOGGER.trace(pixels.length + " " + pixels[0].length);
					img = de.netsysit.ui.image.Quantize.convertToBufferedImage(palette, pixels, muster);
				}
				f = new java.io.File(smalldir, svg.getName().substring(0, svg.getName().length() - 4) + "d." + format);
				javax.imageio.ImageIO.write(img, format, f);
				java.io.File pamfile = new java.io.File(pamdir, svg.getName().substring(0, svg.getName().length() - 4) + "_48." + "pam");
				javax.imageio.stream.ImageOutputStream output = javax.imageio.ImageIO.createImageOutputStream(pamfile);
				writer.setOutput(output);
				javax.imageio.IIOImage image = new javax.imageio.IIOImage(img, null, null);
				writer.write(null, image, iwp);
				writer.dispose();
				output.close();

				ppf=pamfile;
				rv.add(pamfile);
			}
			else
			{
				ppf=f;
			}
		}
		else
			ppf=f;
		final java.io.File pf=ppf;
		java.lang.Runnable runnable=new java.lang.Runnable()
		{

			public void run()
			{
				if (panel != null)
				{
					try
					{
						javax.swing.JLabel l=new javax.swing.JLabel(new javax.swing.ImageIcon(javax.imageio.ImageIO.read(pf)));
						l.setText(svg.getName().substring(0,svg.getName().length()-4));
						l.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
						l.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
						l.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(), javax.swing.BorderFactory.createEmptyBorder(0, 4, 0, 4)));
						panel.add(l);
						l.setMaximumSize(new java.awt.Dimension(1920,l.getMaximumSize().height));
						scroller.invalidate();
						scroller.validate();
						scroller.doLayout();
						scroller.repaint();
						if(CLASS_LOGGER.isTraceEnabled())CLASS_LOGGER.trace(l.getBounds());
						if(CLASS_LOGGER.isTraceEnabled())CLASS_LOGGER.trace(l.getMaximumSize());
						l.scrollRectToVisible(l.getBounds());
						l.addMouseListener(new java.awt.event.MouseAdapter()
						{

							@Override
							public void mouseClicked(MouseEvent e)
							{
								super.mouseClicked(e); //To change body of generated methods, choose Tools | Templates.
								try
								{
									if(CLASS_LOGGER.isTraceEnabled())CLASS_LOGGER.trace(pf.getCanonicalPath());
									if(CLASS_LOGGER.isTraceEnabled())CLASS_LOGGER.trace(svg.getName());
									final java.lang.String stem=svg.getName().substring(0,svg.getName().length()-4);
									final java.lang.String suffix=".png";
									java.io.FilenameFilter fnf=new java.io.FilenameFilter()
									{

										public boolean accept(File dir, String name)
										{

											return  ((name.startsWith(stem))&&(name.endsWith(suffix)));
										}
									};
									java.io.File[] dirs=new java.io.File[]{dir,smalldir,meddir,pamdir,androiddir};
									java.util.List<java.io.File> l=new java.util.LinkedList();
									for (File file : dirs)
									{
										java.io.File[] files=file.listFiles(fnf);
										for (File file1 : files)
										{
											if(CLASS_LOGGER.isTraceEnabled())CLASS_LOGGER.trace(file1);
										}
										l.addAll(Arrays.asList(files));
//										de.netsysit.ui.components.ImageGallery imageGallery=new de.netsysit.ui.components.ImageGallery(files);
//										javax.swing.JOptionPane.showMessageDialog(panel, imageGallery);
									}
									if(CLASS_LOGGER.isTraceEnabled())CLASS_LOGGER.trace(l);
									java.lang.String s=l.toString();
									s=s.substring(1, s.length()-1);
									if(CLASS_LOGGER.isTraceEnabled())CLASS_LOGGER.trace(s);
									//l.addFirst("gthumb");
									java.util.List<java.lang.String> ll=new java.util.LinkedList();
									ll.add("gthumb");
									for (java.io.File file : l)
									{
										ll.add(file.toString());
									}
									Runtime.getRuntime().exec(ll.toArray(new java.lang.String[0]));
								}
								catch(java.lang.Throwable t)
								{
									t.printStackTrace();
								}
							}

						});
					}
					catch (IOException ex)
					{
					}
				}
			}
		};
		if(javax.swing.SwingUtilities.isEventDispatchThread())
			runnable.run();
		else
			javax.swing.SwingUtilities.invokeLater(runnable);
		if(conf.isAndroid())
		{
			int multiplicators[]=new int[]{2,3,4,6,8};
			java.lang.String resourceQualifier[]=new java.lang.String[]{"base","ldpi","mdpi","hdpi","xhdpi"};
			int basew=conf.getFreeAndroidDimensions().width/2;
			int baseh=conf.getFreeAndroidDimensions().height/2;
			for(int i=0;i<resourceQualifier.length;++i)
			{
				new java.io.File(androiddir,resourceQualifier[i]).mkdirs();
			}
			for(int i=0;i<multiplicators.length;++i)
			{
				w=basew*multiplicators[i];
				h=baseh*multiplicators[i];
				factory = javax.xml.transform.TransformerFactory.newInstance();
				java.net.URL xsltUrl=de.netsysit.util.ResourceLoader.getResource("de/elbosso/ressources/data/svgrasterizer/filter.xsl");
				java.io.InputStream xslts=xsltUrl.openStream();
				javax.xml.transform.Source xslt = new javax.xml.transform.stream.StreamSource(xslts);
				javax.xml.transform.Transformer transformer = factory.newTransformer(xslt);
				transformer.setParameter("searchTerm", "$not"+w+"$");
				javax.xml.transform.Source text = new javax.xml.transform.stream.StreamSource(svg);
				java.io.File tmpFile=java.io.File.createTempFile(svg.getName().substring(0,svg.getName().lastIndexOf("."))+"abc", ".svg");

				tmpFile.deleteOnExit();
				transformer.transform(text, new javax.xml.transform.stream.StreamResult(tmpFile.getCanonicalPath()));
				xslts.close();


				turl = tmpFile.toURI().toURL();
				r = new SVGRasterizer((SVGDocument) (fact.createDocument(turl.toString())));
				r.setImageWidth(w);
				r.setImageHeight(h);
				img = r.createBufferedImage();
				f = new java.io.File(androiddir, resourceQualifier[i]+"/"+svg.getName().substring(0, svg.getName().length() - 4) + "." + format);
				javax.imageio.ImageIO.write(img, format, f);
				rv.add(f);
			}
		}
		if(conf.isGooglePlay())
		{
				w=512;
				h=512;
				factory = javax.xml.transform.TransformerFactory.newInstance();
				java.net.URL xsltUrl=de.netsysit.util.ResourceLoader.getResource("de/elbosso/ressources/data/svgrasterizer/filter.xsl");
				java.io.InputStream xslts=xsltUrl.openStream();
				javax.xml.transform.Source xslt = new javax.xml.transform.stream.StreamSource(xslts);
				javax.xml.transform.Transformer transformer = factory.newTransformer(xslt);
				transformer.setParameter("searchTerm", "$not"+w+"$");
				javax.xml.transform.Source text = new javax.xml.transform.stream.StreamSource(svg);
				java.io.File tmpFile=java.io.File.createTempFile(svg.getName().substring(0,svg.getName().lastIndexOf("."))+"abc", ".svg");

				tmpFile.deleteOnExit();
				transformer.transform(text, new javax.xml.transform.stream.StreamResult(tmpFile.getCanonicalPath()));
				xslts.close();


				turl = tmpFile.toURI().toURL();
				r = new SVGRasterizer((SVGDocument) (fact.createDocument(turl.toString())));
				r.setImageWidth(w);
				r.setImageHeight(h);
				img = r.createBufferedImage();
				f = new java.io.File(androiddir, svg.getName().substring(0, svg.getName().length() - 4) + "." + format);
				javax.imageio.ImageIO.write(img, format, f);
				rv.add(f);
		}
		if(conf.isJava())
		{
			int[] sizes = new int[4 + customSizes.size()+(conf.getAdditionalSizes()!=null?conf.getAdditionalSizes().length:0)];
			sizes[0] = 48;
			sizes[1] = 32;
			sizes[2] = 24;
			sizes[3] = 64;
			int counter = 4;
			for (java.lang.Integer i : customSizes)
			{
				sizes[counter] = i.intValue();
				++counter;
			}
			if(conf.getAdditionalSizes()!=null)
			{
				for (int loop=0;loop<conf.getAdditionalSizes().length;++loop)
				{
					sizes[counter] = conf.getAdditionalSizes(loop);
					++counter;
				}
			}
			for (int i : sizes)
			{
				if(i>0)
				{
					factory = javax.xml.transform.TransformerFactory.newInstance();
					java.net.URL xsltUrl=de.netsysit.util.ResourceLoader.getResource("de/elbosso/ressources/data/svgrasterizer/filter.xsl");
					java.io.InputStream xslts=xsltUrl.openStream();
					javax.xml.transform.Source xslt = new javax.xml.transform.stream.StreamSource(xslts);
					javax.xml.transform.Transformer transformer = factory.newTransformer(xslt);
					transformer.setParameter("searchTerm", "$not"+i+"$");
					javax.xml.transform.Source text = new javax.xml.transform.stream.StreamSource(svg);
					java.io.File tmpFile=java.io.File.createTempFile(svg.getName().substring(0,svg.getName().lastIndexOf("."))+"abc", ".svg");

					tmpFile.deleteOnExit();
					transformer.transform(text, new javax.xml.transform.stream.StreamResult(tmpFile.getCanonicalPath()));
					xslts.close();


					turl = tmpFile.toURI().toURL();
					r = new SVGRasterizer((SVGDocument) (fact.createDocument(turl.toString())));
					r.setImageWidth(i);
					r.setImageHeight(i);
					img = r.createBufferedImage();
					f = new java.io.File(dir, svg.getName().substring(0, svg.getName().length() - 4) + "_" + i + "." + format);
					javax.imageio.ImageIO.write(img, format, f);
					rv.add(f);
				}
			}
			if(svg.getName().toUpperCase().endsWith("SYMBOL.SVG"))
			{
	//			if(i==48)
				{
					factory = javax.xml.transform.TransformerFactory.newInstance();
					java.net.URL xsltUrl=de.netsysit.util.ResourceLoader.getResource("de/elbosso/ressources/data/svgrasterizer/filter.xsl");
					java.io.InputStream xslts=xsltUrl.openStream();
					javax.xml.transform.Source xslt = new javax.xml.transform.stream.StreamSource(xslts);
					javax.xml.transform.Transformer transformer = factory.newTransformer(xslt);
					transformer.setParameter("searchTerm", "$not16$");
					javax.xml.transform.Source text = new javax.xml.transform.stream.StreamSource(svg);
					java.io.File tmpFile=java.io.File.createTempFile(svg.getName().substring(0,svg.getName().lastIndexOf("."))+"abc", ".svg");

					tmpFile.deleteOnExit();
					transformer.transform(text, new javax.xml.transform.stream.StreamResult(tmpFile.getCanonicalPath()));
					xslts.close();


					turl = tmpFile.toURI().toURL();
				}
	//			else
	//				turl=tfile.toURI().toURL();
				r = new SVGRasterizer((SVGDocument) (fact.createDocument(turl.toString())));
				r.setImageWidth(16);
				r.setImageHeight(16);
				img = r.createBufferedImage();
				f = new java.io.File(dir, svg.getName().substring(0, svg.getName().length() - 4) + "_" + 16 + "." + format);
				javax.imageio.ImageIO.write(img, format, f);
				rv.add(f);
			}
		}
//		l=new javax.swing.JLabel(new javax.swing.ImageIcon(img));
//		l.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
//        p.add(l, java.awt.BorderLayout.CENTER);
//		f.setContentPane(p);
//        f.pack();
//        f.setVisible(true);
		if(CLASS_LOGGER.isTraceEnabled())CLASS_LOGGER.trace(" ok!");
		return rv;
	}
	/*
	 * @(#)TestQuantize.java    0.90 9/19/00 Adam Doppelt
	 *
	 * @version 0.90 19 Sep 2000
	 * @author <a href="http://www.gurge.com/amd/">Adam Doppelt</a>
	 */

	static int[][] getPixels(java.awt.image.BufferedImage image) throws IOException
	{
		int w = image.getWidth(null);
		int h = image.getHeight(null);
		int pix[] = new int[w * h];
		PixelGrabber grabber = new PixelGrabber(image, 0, 0, w, h, pix, 0, w);

		try
		{
			if (grabber.grabPixels() != true)
			{
				throw new IOException("Grabber returned false: "
						+ grabber.status());
			}
		}
		catch (InterruptedException e)
		{
			Utilities.handleException(null, e);
		}

		int pixels[][] = new int[w][h];
		for (int x = w; x-- > 0;)
		{
			for (int y = h; y-- > 0;)
			{
				pixels[x][y] = pix[y * w + x];
			}
		}

		return pixels;
	}

	static int[][] getPixels(java.awt.image.BufferedImage image, int redbits, int greenbits, int bluebits) throws IOException
	{
		int w = image.getWidth(null);
		int h = image.getHeight(null);
		int pix[] = new int[w * h];
		PixelGrabber grabber = new PixelGrabber(image, 0, 0, w, h, pix, 0, w);

		try
		{
			if (grabber.grabPixels() != true)
			{
				throw new IOException("Grabber returned false: "
						+ grabber.status());
			}
		}
		catch (InterruptedException e)
		{
			Utilities.handleException(null, e);
		}

		int pixels[][] = new int[w][h];
		for (int x = w; x-- > 0;)
		{
			for (int y = h; y-- > 0;)
			{
				int compressed = ((pix[y * w + x] & 0xff0000) >> (8 - redbits)) << (8 - redbits);
				compressed |= ((pix[y * w + x] & 0xff00) >> (8 - greenbits)) << (8 - greenbits);
				compressed |= ((pix[y * w + x] & 0xff) >> (8 - bluebits)) << (8 - bluebits);
				pixels[x][y] = compressed;
			}
		}

		return pixels;
	}

	static de.netsysit.ui.image.RGBTriple[][] getRGBTriples(java.awt.image.BufferedImage image) throws IOException
	{
		int w = image.getWidth(null);
		int h = image.getHeight(null);
		int pix[] = new int[w * h];
		PixelGrabber grabber = new PixelGrabber(image, 0, 0, w, h, pix, 0, w);

		try
		{
			if (grabber.grabPixels() != true)
			{
				throw new IOException("Grabber returned false: "
						+ grabber.status());
			}
		}
		catch (InterruptedException e)
		{
			Utilities.handleException(null, e);
		}

		de.netsysit.ui.image.RGBTriple pixels[][] = new de.netsysit.ui.image.RGBTriple[w][h];
		for (int x = w; x-- > 0;)
		{
			for (int y = h; y-- > 0;)
			{
				pixels[x][y] = new de.netsysit.ui.image.RGBTriple(pix[y * w + x]);
			}
		}

		return pixels;
	}

	// debug
	public static void main(final String[] args) throws Exception
	{
//		de.elbosso.util.Utilities.configureBasicStdoutLogging(Level.ALL);
		try
		{
			java.util.Properties iconFallbacks = new java.util.Properties();
			java.io.InputStream is=de.netsysit.util.ResourceLoader.getResource("de/elbosso/ressources/data/icon_trans_material.properties").openStream();
			iconFallbacks.load(is);
			is.close();
			de.netsysit.util.ResourceLoader.configure(iconFallbacks);
		}
		catch(java.io.IOException ioexp)
		{
			ioexp.printStackTrace();
		}
		de.netsysit.util.ResourceLoader.setSize(false ? de.netsysit.util.ResourceLoader.IconSize.medium : de.netsysit.util.ResourceLoader.IconSize.small);
//		javax.swing.JFileChooser fc = new javax.swing.JFileChooser();
//		fc.setMultiSelectionEnabled(false);
//		fc.setDialogType(fc.SAVE_DIALOG);
//		fc.setFileSelectionMode(fc.DIRECTORIES_ONLY);
//		try
//		{
//			fc.setSelectedFile(new java.io.File("/tmp/Untitled Folder"));
//		}
//		catch(java.lang.Throwable t)
//		{
//			t.printStackTrace();
//		}
//		fc.showSaveDialog(null);
		Configuration confi=null;
		java.io.File configFile=new java.io.File(new java.io.File(System.getProperty("user.home")),".svgRasterizer.conf");
		try
		{
			java.io.InputStream is=new java.io.FileInputStream(configFile);
			java.beans.XMLDecoder decoder=new java.beans.XMLDecoder(is);
			confi=(Configuration)decoder.readObject();
			decoder.close();
			is.close();
		}
		catch(java.lang.Throwable t)
		{
			confi=new Configuration();
		}
		final javax.swing.JFrame frame = new javax.swing.JFrame(SVGRasterizer.class.getSimpleName());
		frame.setIconImage(de.netsysit.util.ResourceLoader.getIcon("de/elbosso/ressources/gfx/eb/rasterizer_48.png").getImage());//de/netsysit/db/prg/windowicon.png")).getImage());
		panel = new javax.swing.JPanel();
		BoxLayout bl=new BoxLayout(panel, BoxLayout.PAGE_AXIS);
		panel.setLayout(bl);
		scroller=new javax.swing.JScrollPane(panel);
		scroller.setMinimumSize(new java.awt.Dimension(640,480));
		scroller.setPreferredSize(new java.awt.Dimension(640,480));
		frame.setContentPane(scroller);
		frame.pack();
		frame.setVisible(true);
//		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter()
		{

			@Override
			public void windowClosing(WindowEvent e)
			{
				super.windowClosed(e); //To change body of generated methods, choose Tools | Templates.
				try
				{
					frame.setVisible(false);
					frame.dispose();
					SVGRasterizer.main(args);
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
					System.exit(0);
				}
			}

});
		de.netsysit.util.beans.InterfaceFactory ifactory=new de.netsysit.util.beans.InterfaceFactory();
		if(javax.swing.JOptionPane.OK_OPTION==javax.swing.JOptionPane.showConfirmDialog(frame,ifactory.fetchInterfaceForBean(confi, "Konfiguration"),SVGRasterizer.class.getSimpleName(),javax.swing.JOptionPane.OK_CANCEL_OPTION))
		{
			final Configuration conf=confi;
			try
			{
				java.io.OutputStream is=new java.io.FileOutputStream(configFile);
				java.beans.XMLEncoder encoder=new java.beans.XMLEncoder(is);
				encoder.setPersistenceDelegate(java.io.File.class, new java.beans.DefaultPersistenceDelegate(new java.lang.String[]{"canonicalPath"}));
				encoder.writeObject(conf);
				encoder.close();
				is.close();
			}
			catch(java.lang.Throwable t)
			{
				de.elbosso.util.Utilities.handleException(null, t);
			}
			if (conf.getDirectory() != null)
			{
				final java.io.File[] svgs = conf.getDirectory().listFiles(new java.io.FileFilter()
				{

					@Override
					public boolean accept(File f)
					{
						return f.getName().toUpperCase().endsWith(".SVG");
					}

				});
				new java.lang.Thread(new java.lang.Runnable()
				{
					public void run()
					{
						if (svgs != null)
						{
							javax.imageio.ImageWriter writer = null;
							java.util.Iterator<javax.imageio.ImageWriter> writers = javax.imageio.ImageIO.getImageWritersByFormatName("pam");
							while (writers.hasNext())
							{
								writer = writers.next();
								if(CLASS_LOGGER.isTraceEnabled())CLASS_LOGGER.trace(writer.getClass());
								if (writer.getOriginatingProvider().getVendorName().equals("NetSys.IT"))
								{
									break;
								}
								writer = null;
							}
							javax.imageio.ImageWriteParam iwp = null;
							if (writer != null)
							{
								iwp = writer.getDefaultWriteParam();
								iwp.setCompressionMode(javax.imageio.ImageWriteParam.MODE_EXPLICIT);
								iwp.setCompressionQuality(1);// an integer: 0(compresed 256 colors), 0.5(uncompresed 256 colors) or 1(uncompresed 65536 colors)
							}
							else
							{
								if(CLASS_LOGGER.isTraceEnabled())CLASS_LOGGER.trace(System.getProperty("java.home"));
							}
							MultiResolutionHelper multiResolutionHelper=new MultiResolutionHelper(svgs);
							for(java.io.File file:multiResolutionHelper)
							{
								try
								{

									workOnFile(file, conf, writer, iwp,multiResolutionHelper, java.util.Collections.EMPTY_LIST);
								}
								catch (java.lang.Throwable t)
								{
									if(CLASS_LOGGER.isTraceEnabled())CLASS_LOGGER.trace(" failed! " + t.getMessage());
									Utilities.handleException(null, t);
								}
							}
							javax.swing.SwingUtilities.invokeLater(new java.lang.Runnable()
							{

								public void run()
								{
									javax.swing.JOptionPane.showMessageDialog(panel, "Finished!");
								}
							});
						}
					}
				}).start();
			}
		}
		else
			System.exit(0);
	}
}

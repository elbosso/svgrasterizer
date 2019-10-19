package de.elbosso.tools.svgrasterizer;

import de.elbosso.util.Utilities;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class SVGRasterizerServlet extends HttpServlet
{
	private final static org.apache.log4j.Logger CLASS_LOGGER = org.apache.log4j.Logger.getLogger(SVGRasterizerServlet.class);
	private final java.io.File tempDir;

	public SVGRasterizerServlet() throws IOException
	{
		super();
		java.io.File f = java.io.File.createTempFile("probe", "fordir");
		tempDir = new java.io.File(f.getParentFile(), "svgrasterizerservlet");
		if (tempDir.exists() == false)
		{
			tempDir.mkdirs();
		}
		f.delete();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		if(request.getServletPath().endsWith("/bitmap")==false)
		{
		response.setContentType("text/html");
		long hd = System.currentTimeMillis();
		response.addDateHeader("Date", hd);
		response.addDateHeader("Expires", hd);
		response.addDateHeader("Last-Modified", hd);
		response.addHeader("Cache-Control", "no-cache, no-store, s-maxage=0, max-age=0, must-revalidate");
		response.getWriter().println("<html><body>");
		response.getWriter().println(request.getContextPath());
		response.getWriter().println(request.getPathInfo());
		response.getWriter().println(request.getPathTranslated());
		response.getWriter().println(request.getRequestURI());
		response.getWriter().println(request.getServletPath());
		response.getWriter().println("<FORM ENCTYPE='multipart/form-data' method='POST' action='"+request.getRequestURI()+"'>");
		response.getWriter().println("<INPUT TYPE='file' NAME='svgfile'>");
		response.getWriter().println("<INPUT TYPE='text' NAME='customSize'>");
		response.getWriter().println("<INPUT TYPE='submit' VALUE='transform'>");
		response.getWriter().println("</FORM>");
		response.getWriter().println("</body></html>");
		response.getWriter().flush();
		response.getWriter().close();
		}
		else
		{
			response.setContentType("image/png");
			long hd = System.currentTimeMillis();
			response.addDateHeader("Date", hd);
			response.addDateHeader("Expires", hd);
			response.addDateHeader("Last-Modified", hd);
			response.addHeader("Cache-Control", "no-cache, no-store, s-maxage=0, max-age=0, must-revalidate");
			java.io.File f=new java.io.File(tempDir,"bitmap");
			if(CLASS_LOGGER.isTraceEnabled())CLASS_LOGGER.trace(request.getPathInfo());
			if(CLASS_LOGGER.isTraceEnabled())CLASS_LOGGER.trace(URLDecoder.decode(request.getPathInfo(),java.nio.charset.StandardCharsets.UTF_8.name()));
			if(CLASS_LOGGER.isTraceEnabled())CLASS_LOGGER.trace(URLDecoder.decode(request.getPathInfo(),"UTF-16"));
			f=new java.io.File(f, URLDecoder.decode(request.getPathInfo(),java.nio.charset.StandardCharsets.UTF_8.name()));
			java.io.FileInputStream fis=new java.io.FileInputStream(f);
			Utilities.copyBetweenStreams(fis, response.getOutputStream(), true);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html");
		long hd = System.currentTimeMillis();
		response.addDateHeader("Date", hd);
		response.addDateHeader("Expires", hd);
		response.addDateHeader("Last-Modified", hd);
		response.addHeader("Cache-Control", "no-cache, no-store, s-maxage=0, max-age=0, must-revalidate");
		response.getWriter().println("<html><body>");
		java.util.Map<java.lang.String,java.lang.String> formFields=new java.util.HashMap();

		try
		{
			java.util.List<org.apache.commons.fileupload.FileItem> items = new org.apache.commons.fileupload.servlet.ServletFileUpload(new org.apache.commons.fileupload.disk.DiskFileItemFactory()).parseRequest(request);
			for (org.apache.commons.fileupload.FileItem item : items)
			{
				if (item.isFormField())
				{
					// Process regular form field (input type="text|radio|checkbox|etc", select, etc).
					String fieldname = item.getFieldName();
					String fieldvalue = item.getString();
					formFields.put(fieldname,fieldvalue);
					// ... (do your job here)
				}
			}
			for (org.apache.commons.fileupload.FileItem item : items)
			{
				if (item.isFormField())
				{

				}
				else
				{
					// Process form file field (input type="file").
					String fieldname = item.getFieldName();

					String filename = org.apache.commons.io.FilenameUtils.getName(item.getName());

					java.io.InputStream filecontent = item.getInputStream();
					java.io.File f = new java.io.File(tempDir, filename);

					java.io.FileOutputStream fos = new java.io.FileOutputStream(f);
					Utilities.copyBetweenStreams(filecontent, fos, true);
					response.getWriter().println("<FORM ENCTYPE='multipart/form-data' method='POST' action='"+request.getRequestURI()+"'>");
					response.getWriter().println("<INPUT TYPE='file' NAME='svgfile'>");
					response.getWriter().println("<INPUT TYPE='text' NAME='customSize'"+(formFields.containsKey("customSize")?(" VALUE='"+formFields.get("customSize")+"'"):"")+">");
					response.getWriter().println("<INPUT TYPE='submit' VALUE='transform'>");
					response.getWriter().println("</FORM>");
					java.util.List<java.lang.Integer> customSizes=new java.util.LinkedList();
					if(formFields.containsKey("customSize"))
					try
					{
						customSizes.add(java.lang.Integer.valueOf(formFields.get("customSize")));
					}
					catch(java.lang.Throwable t){}
					Configuration conf=new Configuration();
					java.util.List<java.io.File> bitmaps = de.elbosso.tools.svgrasterizer.SVGRasterizer.workOnFile(f, conf, null, null,customSizes);
					java.util.List<java.lang.String> names=new java.util.LinkedList();
					java.util.List<java.lang.String> namesWithSubdirs=new java.util.LinkedList();
					java.lang.String origName=null;
					for (File file : bitmaps)
					{
						//response.getWriter().println(file.getCanonicalPath());
						java.lang.String i=file.getCanonicalPath();
						i=i.substring(i.indexOf(java.io.File.separator+"bitmap"+java.io.File.separator)+(java.io.File.separator+"bitmap"+java.io.File.separator).length());
						if(i.endsWith("_orig.png"))
							origName=i;
						else
						{
							if(i.contains(java.io.File.separator))
								namesWithSubdirs.add(i);
							else
								names.add(i);
						}
					}
					java.util.Collections.sort(names);
					java.util.Collections.sort(namesWithSubdirs);
					response.getWriter().println("<h1>"+filename+"</h1>");
					if(origName!=null)
						response.getWriter().println("<a href=\"bitmap/" + URLEncoder.encode(origName,"ISO-8859-1") + "\"><img src=\"bitmap/" + URLEncoder.encode(origName,"ISO-8859-1") + "\" alt=\""+origName+"\" title=\""+origName+"\"></a><br>");
					for (java.lang.String name:names)
					{
						//response.getWriter().println(file.getCanonicalPath());
						response.getWriter().println("<a href=\"bitmap/" + URLEncoder.encode(name,"ISO-8859-1") + "\"><img src=\"bitmap/" + URLEncoder.encode(name,"ISO-8859-1") + "\" alt=\""+name+"\" title=\""+name+"\"></a>");

					}
					java.lang.String heading="";
					for (java.lang.String name:namesWithSubdirs)
					{
						java.lang.String h=name.substring(0, name.indexOf(java.io.File.separator));
						if(h.equals(heading)==false)
						{
							heading=h;
							response.getWriter().println("<h2>"+heading+"</h2>");
						}
						java.lang.String mname=name.replace(java.io.File.separatorChar, '/');

						mname= URLEncoder.encode(mname,"ISO-8859-1");
						mname=mname.replace("%2F", "/");

//						response.getWriter().println("#"+mname);
						response.getWriter().println("<a href=\"bitmap/" + mname + "\"><img src=\"bitmap/" + mname + "\" alt=\""+name+"\" title=\""+name+"\"></a>");
					}
				}
			}
		}
		catch (Exception e)
		{
			throw new ServletException("Cannot parse multipart request.", e);
		}
		response.getWriter().println("</body></html>");
	}
}

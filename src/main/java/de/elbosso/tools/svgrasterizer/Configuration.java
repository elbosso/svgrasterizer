/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.elbosso.tools.svgrasterizer;

import java.awt.*;
import java.io.File;

/**
 *
 * @author elbosso
 */
public class Configuration extends java.lang.Object
{
	private java.awt.Dimension freeAndroidDimensions;
	private boolean java;
	private boolean android;
	private boolean iOs;
	private boolean pam;
	private boolean modifyStrokeWidths;
	private java.io.File directory;
	private AndroidIcon androidIcon;
	private boolean googlePlay;
	private int[] additionalSizes;

	public Configuration()
	{
		super();
//		freeAndroidDimensions=new java.awt.Dimension(48,48);
		java=true;
		directory=new java.io.File(System.getProperty("user.home"));
//		androidIcon=AndroidIcon.free;
		setAndroidIcon(AndroidIcon.launcher);
	}


	public Dimension getFreeAndroidDimensions()
	{
		return freeAndroidDimensions;
	}

	public void setFreeAndroidDimensions(Dimension freeAndroidDimensions)
	{
		this.freeAndroidDimensions = freeAndroidDimensions;
	}

	public boolean isJava()
	{
		return java;
	}

	public void setJava(boolean java)
	{
		this.java = java;
	}

	public boolean isAndroid()
	{
		return android;
	}

	public void setAndroid(boolean android)
	{
		this.android = android;
	}

	public boolean isIOs()
	{
		return iOs;
	}

	public void setIOs(boolean iOs)
	{
		this.iOs = iOs;
	}

	public File getDirectory()
	{
		return directory;
	}

	public void setDirectory(File directory)
	{
		this.directory = directory;
	}

	public boolean isPam()
	{
		return pam;
	}

	public void setPam(boolean pam)
	{
		this.pam = pam;
	}

	public boolean isModifyStrokeWidths()
	{
		return modifyStrokeWidths;
	}

	public void setModifyStrokeWidths(boolean modifyStrokeWidths)
	{
		this.modifyStrokeWidths = modifyStrokeWidths;
	}

	public AndroidIcon getAndroidIcon()
	{
		return androidIcon;
	}

	public void setAndroidIcon(AndroidIcon androidIcon)
	{
		this.androidIcon = androidIcon;
		switch(androidIcon)
		{
			case actionbar:
			{
				setFreeAndroidDimensions(new java.awt.Dimension(32,32));
				break;
			}
			case contextual:
			{
				setFreeAndroidDimensions(new java.awt.Dimension(16,16));
				break;
			}
//			case free:
//			{
//				break;
//			}
			case launcher:
			{
				setFreeAndroidDimensions(new java.awt.Dimension(48,48));
				break;
			}
			case notification:
			{
				setFreeAndroidDimensions(new java.awt.Dimension(24,24));
				break;
			}
		}
	}

	public boolean isGooglePlay()
	{
		return googlePlay;
	}

	public void setGooglePlay(boolean googlePlay)
	{
		this.googlePlay = googlePlay;
	}


	public int[] getAdditionalSizes()
	{
		return additionalSizes;
	}

	public void setAdditionalSizes(int[] additionalSizes)
	{
		int[] old = getAdditionalSizes();
		this.additionalSizes = additionalSizes;
//		send("additionalSizes", old, getAdditionalSizes());
	}

	public int getAdditionalSizes(int index)
	{
		return additionalSizes[index];
	}

	public void setAdditionalSizes(int index,int additionalSize)
	{
		int[] old = getAdditionalSizes();
		this.additionalSizes[index] = additionalSize;
//		send("additionalSizes", old, getAdditionalSizes());
	}


}

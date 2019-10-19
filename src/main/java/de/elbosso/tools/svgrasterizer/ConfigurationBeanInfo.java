/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.elbosso.tools.svgrasterizer;

import java.beans.*;

/**
 *
 * @author elbosso
 */
public class ConfigurationBeanInfo extends SimpleBeanInfo
{
	private final static java.util.ResourceBundle i18n=java.util.ResourceBundle.getBundle("de.elbosso.tools.i18n",java.util.Locale.getDefault());

    // Bean descriptor//GEN-FIRST:BeanDescriptor
    /*lazy BeanDescriptor*/
    private static BeanDescriptor getBdescriptor(){
        BeanDescriptor beanDescriptor = new BeanDescriptor( de.elbosso.tools.svgrasterizer.Configuration.class , null ); // NOI18N//GEN-HEADEREND:BeanDescriptor
    // Here you can add code for customizing the BeanDescriptor.

        return beanDescriptor;     }//GEN-LAST:BeanDescriptor


    // Property identifiers//GEN-FIRST:Properties
    private static final int PROPERTY_android = 0;
    private static final int PROPERTY_androidIcon = 1;
    private static final int PROPERTY_directory = 2;
    private static final int PROPERTY_freeAndroidDimensions = 3;
    private static final int PROPERTY_googlePlay = 4;
    private static final int PROPERTY_IOs = 5;
    private static final int PROPERTY_java = 6;
    private static final int PROPERTY_modifyStrokeWidths = 7;
	private static final int PROPERTY_pam = 8;
	private static final int PROPERTY_additionalSizes = 9;

    // Property array 
    /*lazy PropertyDescriptor*/
    private static PropertyDescriptor[] getPdescriptor(){
        PropertyDescriptor[] properties = new PropertyDescriptor[10];
    
        try {
            properties[PROPERTY_android] = new PropertyDescriptor( "android", de.elbosso.tools.svgrasterizer.Configuration.class, "isAndroid", "setAndroid" ); // NOI18N
            properties[PROPERTY_android].setDisplayName ( i18n.getString("ConfigurationBeanInfo.android.displayName") );
            properties[PROPERTY_android].setShortDescription ( i18n.getString("ConfigurationBeanInfo.android.shortDescription") );
            properties[PROPERTY_androidIcon] = new PropertyDescriptor( "androidIcon", de.elbosso.tools.svgrasterizer.Configuration.class, "getAndroidIcon", "setAndroidIcon" ); // NOI18N
            properties[PROPERTY_androidIcon].setDisplayName ( i18n.getString("ConfigurationBeanInfo.androidIcon.displayName") );
            properties[PROPERTY_androidIcon].setShortDescription ( i18n.getString("ConfigurationBeanInfo.androidIcon.shortDescription") );
            properties[PROPERTY_directory] = new PropertyDescriptor( "directory", de.elbosso.tools.svgrasterizer.Configuration.class, "getDirectory", "setDirectory" ); // NOI18N
            properties[PROPERTY_directory].setDisplayName ( i18n.getString("ConfigurationBeanInfo.directory.displayName") );
            properties[PROPERTY_directory].setShortDescription ( i18n.getString("ConfigurationBeanInfo.directory.shortDescription") );
            properties[PROPERTY_freeAndroidDimensions] = new PropertyDescriptor( "freeAndroidDimensions", de.elbosso.tools.svgrasterizer.Configuration.class, "getFreeAndroidDimensions", "setFreeAndroidDimensions" ); // NOI18N
            properties[PROPERTY_freeAndroidDimensions].setPreferred ( true );
            properties[PROPERTY_freeAndroidDimensions].setDisplayName ( i18n.getString("ConfigurationBeanInfo.freeAndroidDimensions.displayName") );
            properties[PROPERTY_freeAndroidDimensions].setShortDescription ( i18n.getString("ConfigurationBeanInfo.freeAndroidDimensions.shortDescription") );
            properties[PROPERTY_googlePlay] = new PropertyDescriptor( "googlePlay", de.elbosso.tools.svgrasterizer.Configuration.class, "isGooglePlay", "setGooglePlay" ); // NOI18N
            properties[PROPERTY_googlePlay].setDisplayName ( i18n.getString("ConfigurationBeanInfo.googlePlay.displayName") );
            properties[PROPERTY_googlePlay].setShortDescription ( i18n.getString("ConfigurationBeanInfo.googlePlay.shortDescription") );
            properties[PROPERTY_IOs] = new PropertyDescriptor( "IOs", de.elbosso.tools.svgrasterizer.Configuration.class, "isIOs", "setIOs" ); // NOI18N
            properties[PROPERTY_IOs].setHidden ( true );
            properties[PROPERTY_IOs].setDisplayName ( i18n.getString("ConfigurationBeanInfo.iOS.displayName") );
            properties[PROPERTY_IOs].setShortDescription ( i18n.getString("ConfigurationBeanInfo.iOS.shortDescription") );
            properties[PROPERTY_java] = new PropertyDescriptor( "java", de.elbosso.tools.svgrasterizer.Configuration.class, "isJava", "setJava" ); // NOI18N
            properties[PROPERTY_java].setDisplayName ( i18n.getString("ConfigurationBeanInfo.java.displayName") );
            properties[PROPERTY_java].setShortDescription ( i18n.getString("ConfigurationBeanInfo.java.shortDescription") );
            properties[PROPERTY_modifyStrokeWidths] = new PropertyDescriptor( "modifyStrokeWidths", de.elbosso.tools.svgrasterizer.Configuration.class, "isModifyStrokeWidths", "setModifyStrokeWidths" ); // NOI18N
            properties[PROPERTY_modifyStrokeWidths].setDisplayName ( i18n.getString("ConfigurationBeanInfo.modifyStrokeWidths.displayName") );
            properties[PROPERTY_modifyStrokeWidths].setShortDescription ( i18n.getString("ConfigurationBeanInfo.modifyStrokeWidths.shortDescription") );
            properties[PROPERTY_pam] = new PropertyDescriptor( "pam", de.elbosso.tools.svgrasterizer.Configuration.class, "isPam", "setPam" ); // NOI18N
            properties[PROPERTY_pam].setPreferred ( true );
            properties[PROPERTY_pam].setDisplayName ( i18n.getString("ConfigurationBeanInfo.pam.displayName") );
            properties[PROPERTY_pam].setShortDescription ( i18n.getString("ConfigurationBeanInfo.pam.shortDescription") );
        }
        catch(IntrospectionException e) {
            e.printStackTrace();
        }//GEN-HEADEREND:Properties
    // Here you can add code for customizing the properties array.
        try {
            properties[PROPERTY_additionalSizes] = new IndexedPropertyDescriptor( "additionalSizes", de.elbosso.tools.svgrasterizer.Configuration.class, "getAdditionalSizes", "setAdditionalSizes","getAdditionalSizes", "setAdditionalSizes" ); // NOI18N
            properties[PROPERTY_additionalSizes].setPreferred ( true );
            properties[PROPERTY_additionalSizes].setDisplayName ( i18n.getString("ConfigurationBeanInfo.additionalSizes.displayName") );
            properties[PROPERTY_additionalSizes].setShortDescription ( i18n.getString("ConfigurationBeanInfo.additionalSizes.shortDescription") );
        }
        catch(IntrospectionException e) {
            e.printStackTrace();
        }
		properties[PROPERTY_directory].setValue(de.netsysit.ui.beans.customizerwidgets.java.io.FileCustomizer.FILESELECTIONMODE, de.netsysit.ui.beans.customizerwidgets.java.io.FileCustomizer.DIRECTORIES_ONLY);
        return properties;     }//GEN-LAST:Properties

    // EventSet identifiers//GEN-FIRST:Events

    // EventSet array
    /*lazy EventSetDescriptor*/
    private static EventSetDescriptor[] getEdescriptor(){
        EventSetDescriptor[] eventSets = new EventSetDescriptor[0];//GEN-HEADEREND:Events
    // Here you can add code for customizing the event sets array.

        return eventSets;     }//GEN-LAST:Events

    // Method identifiers//GEN-FIRST:Methods

    // Method array 
    /*lazy MethodDescriptor*/
    private static MethodDescriptor[] getMdescriptor(){
        MethodDescriptor[] methods = new MethodDescriptor[0];//GEN-HEADEREND:Methods
    // Here you can add code for customizing the methods array.

        return methods;     }//GEN-LAST:Methods

    private static java.awt.Image iconColor16 = null;//GEN-BEGIN:IconsDef
    private static java.awt.Image iconColor32 = null;
    private static java.awt.Image iconMono16 = null;
    private static java.awt.Image iconMono32 = null;//GEN-END:IconsDef
    private static String iconNameC16 = null;//GEN-BEGIN:Icons
    private static String iconNameC32 = null;
    private static String iconNameM16 = null;
    private static String iconNameM32 = null;//GEN-END:Icons

    private static final int defaultPropertyIndex = -1;//GEN-BEGIN:Idx
    private static final int defaultEventIndex = -1;//GEN-END:Idx


//GEN-FIRST:Superclass
    // Here you can add code for customizing the Superclass BeanInfo.

//GEN-LAST:Superclass
	/**
	 * Gets the bean's <code>BeanDescriptor</code>s.
	 *
	 * @return BeanDescriptor describing the editable properties of this bean.
	 * May return null if the information should be obtained by automatic
	 * analysis.
	 */
	@Override
	public BeanDescriptor getBeanDescriptor()
	{
		return getBdescriptor();
	}

	/**
	 * Gets the bean's <code>PropertyDescriptor</code>s.
	 *
	 * @return An array of PropertyDescriptors describing the editable
	 * properties supported by this bean. May return null if the information
	 * should be obtained by automatic analysis.
	 * <p>
	 * If a property is indexed, then its entry in the result array will belong
	 * to the IndexedPropertyDescriptor subclass of PropertyDescriptor. A client
	 * of getPropertyDescriptors can use "instanceof" to check if a given
	 * PropertyDescriptor is an IndexedPropertyDescriptor.
	 */
	@Override
	public PropertyDescriptor[] getPropertyDescriptors()
	{
		return getPdescriptor();
	}

	/**
	 * Gets the bean's <code>EventSetDescriptor</code>s.
	 *
	 * @return An array of EventSetDescriptors describing the kinds of events
	 * fired by this bean. May return null if the information should be obtained
	 * by automatic analysis.
	 */
	@Override
	public EventSetDescriptor[] getEventSetDescriptors()
	{
		return getEdescriptor();
	}

	/**
	 * Gets the bean's <code>MethodDescriptor</code>s.
	 *
	 * @return An array of MethodDescriptors describing the methods implemented
	 * by this bean. May return null if the information should be obtained by
	 * automatic analysis.
	 */
	@Override
	public MethodDescriptor[] getMethodDescriptors()
	{
		return getMdescriptor();
	}

	/**
	 * A bean may have a "default" property that is the property that will
	 * mostly commonly be initially chosen for update by human's who are
	 * customizing the bean.
	 *
	 * @return Index of default property in the PropertyDescriptor array
	 * returned by getPropertyDescriptors.
	 * <P>
	 * Returns -1 if there is no default property.
	 */
	@Override
	public int getDefaultPropertyIndex()
	{
		return defaultPropertyIndex;
	}

	/**
	 * A bean may have a "default" event that is the event that will mostly
	 * commonly be used by human's when using the bean.
	 *
	 * @return Index of default event in the EventSetDescriptor array returned
	 * by getEventSetDescriptors.
	 * <P>
	 * Returns -1 if there is no default event.
	 */
	@Override
	public int getDefaultEventIndex()
	{
		return defaultEventIndex;
	}

	/**
	 * This method returns an image object that can be used to represent the
	 * bean in toolboxes, toolbars, etc. Icon images will typically be GIFs, but
	 * may in future include other formats.
	 * <p>
	 * Beans aren't required to provide icons and may return null from this
	 * method.
	 * <p>
	 * There are four possible flavors of icons (16x16 color, 32x32 color, 16x16
	 * mono, 32x32 mono). If a bean choses to only support a single icon we
	 * recommend supporting 16x16 color.
	 * <p>
	 * We recommend that icons have a "transparent" background so they can be
	 * rendered onto an existing background.
	 *
	 * @param iconKind The kind of icon requested. This should be one of the
	 * constant values ICON_COLOR_16x16, ICON_COLOR_32x32, ICON_MONO_16x16, or
	 * ICON_MONO_32x32.
	 * @return An image object representing the requested icon. May return null
	 * if no suitable icon is available.
	 */
	@Override
	public java.awt.Image getIcon(int iconKind)
	{
		switch (iconKind)
		{
			case ICON_COLOR_16x16:
				if (iconNameC16 == null)
				{
					return null;
				}
				else
				{
					if (iconColor16 == null)
					{
						iconColor16 = loadImage(iconNameC16);
					}
					return iconColor16;
				}
			case ICON_COLOR_32x32:
				if (iconNameC32 == null)
				{
					return null;
				}
				else
				{
					if (iconColor32 == null)
					{
						iconColor32 = loadImage(iconNameC32);
					}
					return iconColor32;
				}
			case ICON_MONO_16x16:
				if (iconNameM16 == null)
				{
					return null;
				}
				else
				{
					if (iconMono16 == null)
					{
						iconMono16 = loadImage(iconNameM16);
					}
					return iconMono16;
				}
			case ICON_MONO_32x32:
				if (iconNameM32 == null)
				{
					return null;
				}
				else
				{
					if (iconMono32 == null)
					{
						iconMono32 = loadImage(iconNameM32);
					}
					return iconMono32;
				}
			default:
				return null;
		}
	}
	
}

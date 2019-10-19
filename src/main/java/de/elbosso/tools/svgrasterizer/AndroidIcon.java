/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.elbosso.tools.svgrasterizer;

/**
 *
 * @author elbosso
 */
public enum AndroidIcon
{
	/*free("Frei"),*/launcher("Launcher 48x48"),actionbar("Action Bar 32x32"),contextual("Klein (Kontextbezogen) 16x16"),notification("NotificationImpl 24x24");

	private AndroidIcon(String desc)
	{
		this.desc = desc;
	}

	@Override
	public String toString()
	{
		return desc;
	}

	
	
	private java.lang.String desc;
}

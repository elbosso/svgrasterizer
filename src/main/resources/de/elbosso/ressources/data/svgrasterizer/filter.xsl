<?xml version="1.0" encoding="UTF-8"?>

<!--
	Document   : filter.xsl
	Created on : November 1, 2013, 9:02 PM
	Author     : elbosso
	Description:
		Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
				   xmlns:svg="http://www.w3.org/2000/svg"
version="1.0">
<!--xsl:stylesheet version="1.0"
 xmlns:xsl="http://www.w3.org/1999/XSL/Transform"-->
 <!--xsl:output omit-xml-declaration="yes" indent="yes"/-->

 <xsl:param name="searchTerm"/>

 <xsl:template match="node()[not(svg:desc)]|node()[not(contains(svg:desc, $searchTerm))]|@*">
  <xsl:copy>
   <xsl:apply-templates select="node()[not(svg:desc)]|node()[not(contains(svg:desc, $searchTerm))]|@*"/>
  </xsl:copy>
 </xsl:template>
</xsl:stylesheet>

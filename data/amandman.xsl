<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
 	xmlns:ama="http://www.ftn.uns.ac.rs/amandman"
	xmlns:fo="http://www.w3.org/1999/XSL/Format" version="2.0">
	
	<xsl:template match="/">
        <fo:root font-family="Arial">
            <fo:layout-master-set>
                <fo:simple-page-master master-name="bookstore-page">
                    <fo:region-body margin="1px"/>
                </fo:simple-page-master>
            </fo:layout-master-set>
            <fo:page-sequence master-reference="bookstore-page">
                <fo:flow flow-name="xsl-region-body">
                	<fo:block text-align="center" font-size="20px" font-weight="bold" font-family="Arial" padding-top="50px">
                       <xsl:value-of select="ama:Amandman/ama:oznaka"/>
                    </fo:block>
                </fo:flow>
            </fo:page-sequence>
         </fo:root>
     </xsl:template>
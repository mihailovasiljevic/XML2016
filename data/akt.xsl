<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
 	xmlns:akt="http://www.ftn.uns.ac.rs/pravniAkt"
	xmlns:fo="http://www.w3.org/1999/XSL/Format" version="2.0">

    <xsl:template match="/">
        <fo:root>
            <fo:layout-master-set>
                <fo:simple-page-master master-name="bookstore-page">
                    <fo:region-body margin="1px"/>
                </fo:simple-page-master>
            </fo:layout-master-set>
            <fo:page-sequence master-reference="bookstore-page">
                <fo:flow flow-name="xsl-region-body">
                	<fo:block text-align="center" font-size="20px" font-weight="bold" font-family="Arial" padding-top="50px">
                       <xsl:value-of select="akt:propis/akt:naziv"/>
                    </fo:block>
                	<fo:block text-align="center" font-size="16px" font-weight="bold" font-family="Arial" padding-top="15px">
                       <xsl:value-of select="akt:propis/akt:deo/akt:simbolickiNaziv"/>
                    </fo:block>
                   <fo:block text-align="center" font-size="16px" font-weight="bold" font-family="Arial" padding-top="4px">
                       <xsl:value-of select="akt:propis/akt:deo/akt:naziv"/>
                    </fo:block>
                    
                    <xsl:for-each select="akt:propis/akt:deo/akt:clan">
	                   <fo:block text-align="center" font-size="13px" font-weight="bold" font-family="Arial" padding-top="12px">
	                       <xsl:value-of select="akt:simbolickiNaziv"/>
	                    </fo:block>
	                    <fo:block text-align="center" font-size="13px" font-weight="bold" font-family="Arial" padding-top="2px">
	                       <xsl:value-of select="akt:clan/akt:naziv"/>
	                    </fo:block>
	                   	<fo:block text-align="justify" font-size="12px" font-family="Arial" padding-top="2px" margin-left="50px" margin-right="50px">
	                       	<xsl:value-of select="akt:stav/akt:tekst"/>
	                    </fo:block>
          	            <fo:block text-align="justify" font-size="12px" font-family="Arial" padding-top="2px" margin-left="50px" margin-right="50px">
	                       	<xsl:value-of select="akt:tekst"/>
	                    </fo:block>
                    </xsl:for-each>
                    
                    <xsl:for-each select="akt:propis/akt:deo/akt:glava">
	                	<fo:block text-align="center" font-size="15px" font-weight="bold" font-family="Arial" padding-top="16px">
	                       <xsl:value-of select="akt:simbolickiNaziv"/>
	                    </fo:block>
	                   <fo:block text-align="center" font-size="15px" font-weight="bold" font-family="Arial" padding-top="4px">
	                       <xsl:value-of select="akt:naziv"/>
	                    </fo:block>

	                    <xsl:for-each select="akt:clan">
		                   <fo:block text-align="center" font-size="13px" font-weight="bold" font-family="Arial" padding-top="12px">
		                       <xsl:value-of select="akt:simbolickiNaziv"/>
		                    </fo:block>
		                    <fo:block text-align="center" font-size="13px" font-weight="bold" font-family="Arial" padding-top="2px">
		                       <xsl:value-of select="akt:naziv"/>
		                    </fo:block>
		                   	<fo:block text-align="justify" font-size="12px" font-family="Arial" padding-top="2px" margin-left="50px" margin-right="50px">
		                       	<xsl:value-of select="akt:stav/akt:tekst"/>
		                    </fo:block>
	          	            <fo:block text-align="justify" font-size="12px" font-family="Arial" padding-top="2px" margin-left="50px" margin-right="50px">
		                       	<xsl:value-of select="akt:tekst"/>
		                    </fo:block>
	                    </xsl:for-each>
	                    
	                    <xsl:for-each select="akt:odeljak">
	                    	<fo:block text-align="center" font-size="13px" font-weight="bold" font-family="Arial" padding-top="12px">
		                       <xsl:value-of select="akt:simbolickiNaziv"/>
		                    </fo:block>
		                    <fo:block text-align="center" font-size="13px" font-weight="bold" font-family="Arial" padding-top="2px">
		                       <xsl:value-of select="akt:naziv"/>
		                    </fo:block>
		                    
		                   <xsl:for-each select="akt:clan">
			                   <fo:block text-align="center" font-size="13px" font-weight="bold" font-family="Arial" padding-top="12px">
			                       <xsl:value-of select="akt:simbolickiNaziv"/>
			                    </fo:block>
			                    <fo:block text-align="center" font-size="13px" font-weight="bold" font-family="Arial" padding-top="2px">
			                       <xsl:value-of select="akt:naziv"/>
			                    </fo:block>
			                   	<fo:block text-align="justify" font-size="12px" font-family="Arial" padding-top="2px" margin-left="50px" margin-right="50px">
			                       	<xsl:value-of select="akt:stav/akt:tekst"/>
			                    </fo:block>
		          	            <fo:block text-align="justify" font-size="12px" font-family="Arial" padding-top="2px" margin-left="50px" margin-right="50px">
			                       	<xsl:value-of select="akt:tekst"/>
			                    </fo:block>
	                    	</xsl:for-each>
	                    	
	                    	<xsl:for-each select="akt:pododeljak">
	                    		<fo:block text-align="center" font-size="13px" font-weight="bold" font-family="Arial" padding-top="2px">
			                       <xsl:value-of select="akt:rednoSlovo"/>)
			                       <xsl:value-of select="akt:naziv"/>
			                    </fo:block>
			                    
				                <xsl:for-each select="akt:clan">
				                   <fo:block text-align="center" font-size="13px" font-weight="bold" font-family="Arial" padding-top="12px">
				                       <xsl:value-of select="akt:simbolickiNaziv"/>
				                    </fo:block>
				                    <fo:block text-align="center" font-size="13px" font-weight="bold" font-family="Arial" padding-top="2px">
				                       <xsl:value-of select="akt:naziv"/>
				                    </fo:block>
				                   	<fo:block text-align="justify" font-size="12px" font-family="Arial" padding-top="2px" margin-left="50px" margin-right="50px">
				                       	<xsl:value-of select="akt:stav/akt:tekst"/>
				                    </fo:block>
			          	            <fo:block text-align="justify" font-size="12px" font-family="Arial" padding-top="2px" margin-left="50px" margin-right="50px">
				                       	<xsl:value-of select="akt:tekst"/>
				                    </fo:block>
		                    	</xsl:for-each>
			                    
	                    	</xsl:for-each>
	                    	
	                    </xsl:for-each>

                    </xsl:for-each>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
	</xsl:template>
</xsl:stylesheet>
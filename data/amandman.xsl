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
                	<fo:block text-align="center" font-size="33px" font-weight="bold" font-family="Arial" padding-top="50px">
                       <xsl:value-of select="ama:Amandman/ama:zaglavlje/ama:drzava"/>
                    </fo:block>
                    
                    <fo:block text-align="center" font-size="24px" font-weight="bold" font-family="Arial" padding-top="20px">
                       <xsl:value-of select="ama:Amandman/ama:zaglavlje/ama:organ"/>
                    </fo:block>
                    
                    <fo:block text-align="center" font-size="18px" font-family="Arial" padding-top="12px">
                       Predlaze <xsl:value-of select="ama:Amandman/ama:zaglavlje/ama:predlagac"/> u 
                       <xsl:value-of select="ama:Amandman/ama:zaglavlje/ama:mesto"/> dana
                       <xsl:value-of select="ama:Amandman/ama:zaglavlje/ama:datum"/>
                    </fo:block>
                    
                   <fo:block text-align="center" font-size="21px" font-weight="bold" font-family="Arial" padding-top="25px">
                       <xsl:value-of select="ama:Amandman/ama:uvod/ama:naslov"/> 
                   </fo:block>

                   <fo:block text-align="justify" font-size="15px" font-family="Arial" padding-top="12px" margin-left="60px" margin-right="60px">
                       <xsl:value-of select="ama:Amandman/ama:uvod/ama:tekst"/> 
                   </fo:block>
                   
                   <fo:block text-align="center" font-size="24px" font-weight="bold" font-family="Arial" padding-top="30px">
                       <xsl:value-of select="ama:Amandman/ama:glavniDeo/ama:naslov"/> 
                   </fo:block>
                    
                   <fo:block text-align="justify" font-size="14px" font-family="Arial" padding-top="12px" margin-left="60px" margin-right="60px">
                       <xsl:value-of select="ama:Amandman/ama:glavniDeo/ama:clan/ama:tekst"/> 
                   </fo:block>
                    
                  <fo:block text-align="justify" font-size="14px" font-family="Arial" padding-top="12px" margin-left="60px" margin-right="60px">
                       <xsl:for-each select="ama:Amandman/ama:glavniDeo/ama:clan/ama:stav">
                       		<fo:block text-align="justify" font-size="12px" font-family="Arial" padding-top="6px" margin-left="10px">
                       			<xsl:value-of select="ama:tekst"/> 
                       		</fo:block>
                       		<xsl:for-each select="ama:tacka">
                       			<fo:block text-align="justify" font-size="12px" font-family="Arial" padding-top="6px" margin-left="15px">
                       				<xsl:value-of select="ama:tekst"/> 
                       			</fo:block>
                       		</xsl:for-each>
                       </xsl:for-each>
                  </fo:block>
                  
                   <fo:block text-align="center" font-size="24px" font-weight="bold" font-family="Arial" padding-top="40px">
                       OBRAZLOZENJE
                   </fo:block>
                  
                   <fo:block text-align="center" font-size="20px" font-weight="bold" font-family="Arial" padding-top="20px">
                       <xsl:value-of select="ama:Amandman/ama:obrazlozenje/ama:naslov"/> 
                   </fo:block>
                   
                   <fo:block text-align="justify" font-size="15px" font-weight="bold" font-family="Arial" padding-top="15px" margin-left="60px" margin-right="60px">
                       <xsl:value-of select="ama:Amandman/ama:obrazlozenje/ama:tekst"/> 
                   </fo:block>
                   
                   
                   <fo:block text-align="right" font-size="11px" font-weight="bold" font-family="Arial" padding-top="30px" margin-right="30px">
                       Funckija: <xsl:value-of select="ama:Amandman/ama:potpis/ama:funkcija"/> 
                   </fo:block>
                   
                   <fo:block text-align="right" font-size="11px" font-weight="bold" font-family="Arial" padding-top="18px" margin-right="30px">
                       Ime i Prezime: <xsl:value-of select="ama:Amandman/ama:potpis/ama:imeIPrezime"/> 
                   </fo:block>
                    
                </fo:flow>
            </fo:page-sequence>
         </fo:root>
     </xsl:template>
</xsl:stylesheet>
<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
 	xmlns:ama="http://www.ftn.uns.ac.rs/amandman"
	xmlns:fo="http://www.w3.org/1999/XSL/Format" version="2.0">
	
	<xsl:template match="/">
		<html>
			<body>
                	<h1 align="center" font-weight="bold" font-family="Arial" padding-top="50px">
                       <xsl:value-of select="ama:Amandman/ama:zaglavlje/ama:drzava"/>
                    </h1>
                    
                    <h2 align="center" font-weight="bold" font-family="Arial" padding-top="20px">
                       <xsl:value-of select="ama:Amandman/ama:zaglavlje/ama:organ"/>
                    </h2>
                    
                    <h4 align="center" font-family="Arial" padding-top="12px">
                       Predlaze <xsl:value-of select="ama:Amandman/ama:zaglavlje/ama:predlagac"/> u 
                       <xsl:value-of select="ama:Amandman/ama:zaglavlje/ama:mesto"/> dana
                       <xsl:value-of select="ama:Amandman/ama:zaglavlje/ama:datum"/>
                    </h4>
                    
                   <h3 align="center" font-weight="bold" font-family="Arial" padding-top="25px">
                       <xsl:value-of select="ama:Amandman/ama:uvod/ama:naslov"/> 
                   </h3>

                   <h4 align="justify" font-family="Arial" padding-top="12px" style="marign-left:60px; maring-right:60px">
                       <xsl:value-of select="ama:Amandman/ama:uvod/ama:tekst"/> 
                   </h4>
                   
                   <h3 align="center" font-weight="bold" font-family="Arial" padding-top="30px">
                       <xsl:value-of select="ama:Amandman/ama:glavniDeo/ama:naslov"/> 
                   </h3>
                    
                   <h4 align="justify" font-family="Arial" padding-top="12px" style="marign-left:60px; maring-right:60px">
                       <xsl:value-of select="ama:Amandman/ama:glavniDeo/ama:clan/ama:tekst"/> 
                   </h4>
                    
                 
                   <xsl:for-each select="ama:Amandman/ama:glavniDeo/ama:clan/ama:stav">
                   		<h5 align="justify" font-size="12px" font-family="Arial" padding-top="6px" style="marign-left:10px;">
                   			<xsl:value-of select="ama:tekst"/> 
                   		</h5>
                   		<xsl:for-each select="ama:tacka">
                   			<h6 align="justify" font-size="12px" font-family="Arial" padding-top="6px" style="marign-left:15px;">
                   				<xsl:value-of select="ama:tekst"/> 
                   			</h6>
                   		</xsl:for-each>
                   </xsl:for-each>
                  
                   <h2 align="center" font-weight="bold" font-family="Arial" padding-top="40px">
                       OBRAZLOZENJE
                   </h2>
                  
                   <h3 align="center" font-weight="bold" font-family="Arial" padding-top="20px">
                       <xsl:value-of select="ama:Amandman/ama:obrazlozenje/ama:naslov"/> 
                   </h3>
                   
                   <h4 align="justify" font-weight="bold" font-family="Arial" padding-top="15px" style="marign-left:60px; maring-right:60px">
                       <xsl:value-of select="ama:Amandman/ama:obrazlozenje/ama:tekst"/> 
                   </h4>
                   
                   
                   <h5 align="right" font-weight="bold" font-family="Arial" padding-top="30px" style="marign-right:30px;">
                       Funckija: <xsl:value-of select="ama:Amandman/ama:potpis/ama:funkcija"/> 
                   </h5>
                   
                   <h5 align="right" font-weight="bold" font-family="Arial" padding-top="18px" style="maring-right:30px">
                       Ime i Prezime: <xsl:value-of select="ama:Amandman/ama:potpis/ama:imeIPrezime"/> 
                   </h5>
                    
			</body>
		</html>
     </xsl:template>
</xsl:stylesheet>
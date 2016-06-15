<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
 	xmlns:akt="http://www.ftn.uns.ac.rs/pravniAkt"
	xmlns:fo="http://www.w3.org/1999/XSL/Format" version="2.0">

    <xsl:template match="/">
	<html>
	<head>
		<meta charset="UTF-8"/>
	</head>
		<body>
       	<h1 align="center" font-size="20px" font-weight="bold" font-family="Arial" padding-top="50px">
              <xsl:value-of select="akt:propis/akt:naziv"/>
       	</h1>
       	<h2 align="center" font-size="16px" font-weight="bold" font-family="Arial" padding-top="15px">
              <xsl:value-of select="akt:propis/akt:deo/akt:simbolickiNaziv"/>
        </h2>
          <h3 align="center" font-size="16px" font-weight="bold" font-family="Arial" padding-top="4px">
              <xsl:value-of select="akt:propis/akt:deo/akt:naziv"/>
          </h3>
           
           <xsl:for-each select="akt:propis/akt:deo/akt:clan">
           <h4 align="center" font-size="13px" font-weight="bold" font-family="Arial" padding-top="12px">
               <xsl:value-of select="akt:simbolickiNaziv"/>
            </h4>
            <h4 align="center" font-size="13px" font-weight="bold" font-family="Arial" padding-top="2px">
               <xsl:value-of select="akt:clan/akt:naziv"/>
            </h4>
           	<h5 align="justify" font-size="12px" font-family="Arial" padding-top="2px" style="padding-left: 95px; padding-right:95px;">
               	<xsl:value-of select="akt:stav/akt:tekst"/>
            </h5>
	        <h5 align="justify" font-size="12px" font-family="Arial" padding-top="2px" style="padding-left: 95px; padding-right:95px;">
               	<xsl:value-of select="akt:tekst"/>
            </h5>
           </xsl:for-each>
           
           <xsl:for-each select="akt:propis/akt:deo/akt:glava">
        	<h3 align="center" font-size="15px" font-weight="bold" font-family="Arial" padding-top="16px">
               <xsl:value-of select="akt:simbolickiNaziv"/>
            </h3>
           <h3 align="center" font-size="15px" font-weight="bold" font-family="Arial" padding-top="4px">
               <xsl:value-of select="akt:naziv"/>
           </h3>

            <xsl:for-each select="akt:clan">
            <h4 align="center" font-size="13px" font-weight="bold" font-family="Arial" padding-top="12px">
                <xsl:value-of select="akt:simbolickiNaziv"/>
             </h4>
             <h4 align="center" font-size="13px" font-weight="bold" font-family="Arial" padding-top="2px">
                <xsl:value-of select="akt:naziv"/>
             </h4>
             <h5 align="justify" font-size="12px" font-family="Arial" padding-top="2px" style="padding-left: 95px; padding-right:95px;">
                	<xsl:value-of select="akt:stav/akt:tekst"/>
             </h5>
  	         <h5 align="justify" font-size="12px" font-family="Arial" padding-top="2px" style="padding-left: 95px; padding-right:95px;">
                	<xsl:value-of select="akt:tekst"/>
             </h5>
            </xsl:for-each>
            
            <xsl:for-each select="akt:odeljak">
           	 <h3 align="center" font-size="13px" font-weight="bold" font-family="Arial" padding-top="12px">
                <xsl:value-of select="akt:simbolickiNaziv"/>
             </h3>
             <h3 align="center" font-size="13px" font-weight="bold" font-family="Arial" padding-top="2px">
                <xsl:value-of select="akt:naziv"/>
             </h3>
             
            <xsl:for-each select="akt:clan">
             <h4 align="center" font-size="13px" font-weight="bold" font-family="Arial" padding-top="12px">
                 <xsl:value-of select="akt:simbolickiNaziv"/>
             </h4>
             <h4 align="center" font-size="13px" font-weight="bold" font-family="Arial" padding-top="2px">
                 <xsl:value-of select="akt:naziv"/>
             </h4>
              <h5 align="justify" font-size="12px" font-family="Arial" padding-top="2px" style="padding-left: 95px; padding-right:95px;">
                 	<xsl:value-of select="akt:stav/akt:tekst"/>
              </h5>
   	          <h5 align="justify" font-size="12px" font-family="Arial" padding-top="2px" style="padding-left: 95px; padding-right:95px;">
                 	<xsl:value-of select="akt:tekst"/>
              </h5>
             </xsl:for-each>
            	
            	<xsl:for-each select="akt:pododeljak">
          			<h4 align="center" font-size="13px" font-weight="bold" font-family="Arial" padding-top="2px">
                		<xsl:value-of select="akt:rednoSlovo"/>)
                 		<xsl:value-of select="akt:naziv"/>
                 		
            		</h4>
              
		           <xsl:for-each select="akt:clan">
		              <h4 align="center" font-size="13px" font-weight="bold" font-family="Arial" padding-top="12px">
		                  <xsl:value-of select="akt:simbolickiNaziv"/>
		               </h4>
		               <h4 align="center" font-size="13px" font-weight="bold" font-family="Arial" padding-top="2px">
		                  <xsl:value-of select="akt:naziv"/>
		               </h4>
		              	<h5 align="justify" font-size="12px" font-family="Arial" padding-top="2px" style="padding-left: 95px; padding-right:95px;">
		                  	<xsl:value-of select="akt:stav/akt:tekst"/>
		               </h5>
	    	            <h5 align="justify" font-size="12px" font-family="Arial" padding-top="2px" style="padding-left: 95px; padding-right:95px;">
		                  	<xsl:value-of select="akt:tekst"/>
		               </h5>
		             	</xsl:for-each>
              
            	</xsl:for-each>
            	
            </xsl:for-each>

           </xsl:for-each>
           </body>
    </html>
	</xsl:template>
</xsl:stylesheet>
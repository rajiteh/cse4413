<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:template match="/">
    <html>
    <head>
      <title>SIS Report</title>
    </head>
    <body>
	<xsl:apply-templates/>
    </body>
    </html>
    </xsl:template>

    <xsl:template match="sisReport">
	<h1>SIS Report</h1>
	<h2>Scope</h2>
	<p>Last Name Prefix: <xsl:value-of select="@namePrefix"/></p>
	<p>Minimum GPA: <xsl:value-of select="@minGPA"/></p>
	<p><i>Sorted By: <xsl:value-of select="@orderBy"/></i></p>
	  <table border="1" cellpadding="5">
	<xsl:apply-templates/>
	</table>
    </xsl:template>

    <xsl:template match="student">
	<tr>
	  <td><xsl:value-of select="./name"/></td>
	  <td><xsl:value-of select="./major"/></td>
	  <td><xsl:value-of select="./courses"/></td>
	  <td><xsl:value-of select="./gpa"/></td>
	</tr>
    </xsl:template>

</xsl:stylesheet>

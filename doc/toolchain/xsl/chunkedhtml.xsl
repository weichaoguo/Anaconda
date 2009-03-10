<?xml version='1.0'?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
 xmlns:date="http://exslt.org/dates-and-times"
 exclude-result-prefixes="date" version="1.0">


 <!-- Load the "several html chunks" style sheet -->
 <xsl:import href="../docbook-xsl/html/chunk.xsl" />
 <xsl:import href="html.xsl" />

 <!-- Use chapter ids for html filenames -->
 <xsl:param name="use.id.as.filename">1</xsl:param>
 <!-- We want to see title in navigation bars -->
 <xsl:param name="navig.showtitles">1</xsl:param>
 <xsl:param name="suppress.navigation">0</xsl:param>
 <xsl:param name="suppress.header.navigation">0</xsl:param>
 <!-- Only make new files for new chapters and parts, not for sections -->
 <xsl:param name="chunk.section.depth">0</xsl:param>
 <!-- Please put list of tables/examples/figures on separate page from full table of contents -->
 <xsl:param name="chunk.separate.lots">1</xsl:param>
 <!--  Indent html output -->
 <xsl:param name="chunker.output.indent">yes</xsl:param>
 <!-- Make the legal notice appear, but only as a link - no need for everyone to see it -->
 <xsl:param name="generate.legalnotice.link">1</xsl:param>

 <!-- Replace the standard home button (bottom line, middle) by 'Table of Content' -->
 <xsl:param name="local.l10n.xml" select="document('')" />
 <l:i18n xmlns:l="http://docbook.sourceforge.net/xmlns/l10n/1.0">
  <l:l10n language="en">
   <l:gentext key="nav-home" text="Table of Contents" />
  </l:l10n>
 </l:i18n>

<!--- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - --> 
<!-- Customizes the navigation bar (at the top) contain part title, then chapter title -->
<!--- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - --> 
 <xsl:template name="header.navigation">
  <xsl:param name="prev" select="Prev" /><!--Values here are those if param not specified-->
  <xsl:param name="next" select="Next" /><!--Values here are those if param not specified-->
  <xsl:param name="nav.context" /><!-- just states you can pass a "nav.context" parameter -->


  <xsl:variable name="home" select="/*[1]" />
  <xsl:variable name="up" select="parent::*" />

  <xsl:variable name="row1" select="$navig.showtitles != 0" />
  <xsl:variable name="row2" select="count($prev) > 0 or (count($up) > 0 and generate-id($up) != generate-id($home) and $navig.showtitles != 0) or count($next) > 0" />

  <xsl:if
   test="$suppress.navigation = '0' and $suppress.header.navigation = '0'">
   <div class="navheader">
    <table width="100%" summary="Navigation header">
     <tr>
      <!-- prev button -->
      <td width="15%" align="left">
       <xsl:if test="count($prev)>0">
        <a accesskey="p">
         <xsl:attribute name="href">
          <xsl:call-template
           name="href.target">
           <xsl:with-param
            name="object" select="$prev" />
          </xsl:call-template>
         </xsl:attribute>
         <xsl:call-template
          name="navig.content">
          <xsl:with-param name="direction"
           select="'prev'" />
         </xsl:call-template>
        </a>
       </xsl:if>
       <xsl:text>&#160;</xsl:text>
      </td>
      <!--  part and chapter lines -->
      <td width="50%" align="center">
  <xsl:choose>
  <!-- Normal chapter: just put part and chapter lines -->
  <xsl:when test="count($up) > 0  and generate-id($up) != generate-id($home) and $navig.showtitles != 0">
       <a accesskey="p">
          <xsl:attribute name="href">
           <xsl:call-template name="href.target">
            <xsl:with-param name="object" select="$up" />
           </xsl:call-template>
          </xsl:attribute>
          <xsl:apply-templates   select="$up" mode="object.title.markup" />
         </a>
         <br />
         <!-- Insert the 'local' string, ie the Chapter title -->
         <!--  No need for link, we're already at the top of the page!  -->
         <xsl:apply-templates select="." mode="object.title.markup" />
  </xsl:when>
  <xsl:otherwise>
         <!--  Just insert the local string -->
         <xsl:apply-templates select="." mode="object.title.markup" />
         <!--  Add link to PA Manual Start only if not at start already.  -->
         <xsl:if test="generate-id(.) != generate-id($home)">
         <br/>
         <a>
          <xsl:attribute name="href">
           <xsl:call-template name="href.target">
            <xsl:with-param name="object" select="$up" />
           </xsl:call-template>
          </xsl:attribute>
          <xsl:text>ProActive manual Table Of Contents</xsl:text>
         </a>
         </xsl:if>
  </xsl:otherwise> 
  </xsl:choose> 
       </td>
      <!--  ProActive Logo -->
      <td width="20%" align="center">
       <a href="http://ProActive.ObjectWeb.org">
        <img>
         <xsl:attribute name="src">
          <xsl:copy-of
           select="$header.image.filename" />
         </xsl:attribute>
         <xsl:attribute name="alt">
          Back to the ProActive Home Page
         </xsl:attribute>
         <xsl:attribute name="title">
          Back to the ProActive Home Page
         </xsl:attribute>
        </img>
       </a>
      </td>
      <!-- next button -->
      <td width="15%" align="right">
       <xsl:text>&#160;</xsl:text>
       <xsl:if test="count($next)>0">
        <a accesskey="n">
         <xsl:attribute name="href">
          <xsl:call-template
           name="href.target">
           <xsl:with-param
            name="object" select="$next" />
          </xsl:call-template>
         </xsl:attribute>
         <xsl:call-template
          name="navig.content">
          <xsl:with-param name="direction"
           select="'next'" />
         </xsl:call-template>
        </a>
       </xsl:if>
      </td>
     </tr>
    </table>
    <xsl:if test="$header.rule != 0">
     <hr />
    </xsl:if>
   </div>
  </xsl:if>
 </xsl:template>


<!--- - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  --> 
<!-- Have list of figures, examples... referenced before the TOC -->
<!--- - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  --> 
<xsl:template name="make.lots">
  <xsl:param name="toc.params" select="''"/>
  <xsl:param name="toc"/>

  <xsl:variable name="lots">
    <xsl:if test="contains($toc.params, 'figure')">
      <xsl:choose>
        <xsl:when test="$chunk.separate.lots != '0'">
          <xsl:call-template name="make.lot.chunk">
            <xsl:with-param name="type" select="'figure'"/>
            <xsl:with-param name="lot">
              <xsl:call-template name="list.of.titles">
                <xsl:with-param name="titles" select="'figure'"/>
                <xsl:with-param name="nodes" select=".//figure"/>
              </xsl:call-template>
            </xsl:with-param>
          </xsl:call-template>
        </xsl:when>
        <xsl:otherwise>
          <xsl:call-template name="list.of.titles">
            <xsl:with-param name="titles" select="'figure'"/>
            <xsl:with-param name="nodes" select=".//figure"/>
          </xsl:call-template>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:if>

    <xsl:if test="contains($toc.params, 'table')">
      <xsl:choose>
        <xsl:when test="$chunk.separate.lots != '0'">
          <xsl:call-template name="make.lot.chunk">
            <xsl:with-param name="type" select="'table'"/>
            <xsl:with-param name="lot">
              <xsl:call-template name="list.of.titles">
                <xsl:with-param name="titles" select="'table'"/>
                <xsl:with-param name="nodes" select=".//table"/>
              </xsl:call-template>
            </xsl:with-param>
          </xsl:call-template>
        </xsl:when>
        <xsl:otherwise>
          <xsl:call-template name="list.of.titles">
            <xsl:with-param name="titles" select="'table'"/>
            <xsl:with-param name="nodes" select=".//table"/>
          </xsl:call-template>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:if>

    <xsl:if test="contains($toc.params, 'example')">
      <xsl:choose>
        <xsl:when test="$chunk.separate.lots != '0'">
          <xsl:call-template name="make.lot.chunk">
            <xsl:with-param name="type" select="'example'"/>
            <xsl:with-param name="lot">
              <xsl:call-template name="list.of.titles">
                <xsl:with-param name="titles" select="'example'"/>
                <xsl:with-param name="nodes" select=".//example"/>
              </xsl:call-template>
            </xsl:with-param>
          </xsl:call-template>
        </xsl:when>
        <xsl:otherwise>
          <xsl:call-template name="list.of.titles">
            <xsl:with-param name="titles" select="'example'"/>
            <xsl:with-param name="nodes" select=".//example"/>
          </xsl:call-template>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:if>

    <xsl:if test="contains($toc.params, 'equation')">
      <xsl:choose>
        <xsl:when test="$chunk.separate.lots != '0'">
          <xsl:call-template name="make.lot.chunk">
            <xsl:with-param name="type" select="'equation'"/>
            <xsl:with-param name="lot">
              <xsl:call-template name="list.of.titles">
                <xsl:with-param name="titles" select="'equation'"/>
                <xsl:with-param name="nodes" select=".//equation"/>
              </xsl:call-template>
            </xsl:with-param>
          </xsl:call-template>
        </xsl:when>
        <xsl:otherwise>
          <xsl:call-template name="list.of.titles">
            <xsl:with-param name="titles" select="'equation'"/>
            <xsl:with-param name="nodes" select=".//equation"/>
          </xsl:call-template>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:if>

    <xsl:if test="contains($toc.params, 'procedure')">
      <xsl:choose>
        <xsl:when test="$chunk.separate.lots != '0'">
          <xsl:call-template name="make.lot.chunk">
            <xsl:with-param name="type" select="'procedure'"/>
            <xsl:with-param name="lot">
              <xsl:call-template name="list.of.titles">
                <xsl:with-param name="titles" select="'procedure'"/>
                <xsl:with-param name="nodes" select=".//procedure[title]"/>
              </xsl:call-template>
            </xsl:with-param>
          </xsl:call-template>
        </xsl:when>
        <xsl:otherwise>
          <xsl:call-template name="list.of.titles">
            <xsl:with-param name="titles" select="'procedure'"/>
            <xsl:with-param name="nodes" select=".//procedure[title]"/>
          </xsl:call-template>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:if>

    <xsl:if test="contains($toc.params, 'toc')">
      <xsl:copy-of select="$toc"/>
    </xsl:if>

  </xsl:variable>

  <xsl:if test="string($lots) != ''">

    <hr/>
    <xsl:choose>
      <xsl:when test="$chunk.tocs.and.lots != 0 and not(parent::*)">
        <xsl:call-template name="write.chunk">
          <xsl:with-param name="filename">
            <xsl:call-template name="make-relative-filename">
              <xsl:with-param name="base.dir" select="$base.dir"/>
              <xsl:with-param name="base.name">
                <xsl:call-template name="dbhtml-dir"/>
                <xsl:apply-templates select="." mode="recursive-chunk-filename">
                  <xsl:with-param name="recursive" select="true()"/>
                </xsl:apply-templates>
                <xsl:text>-toc</xsl:text>
                <xsl:value-of select="$html.ext"/>
              </xsl:with-param>
            </xsl:call-template>
          </xsl:with-param>
          <xsl:with-param name="content">
            <xsl:call-template name="chunk-element-content">
              <xsl:with-param name="prev" select="/foo"/>
              <xsl:with-param name="next" select="/foo"/>
              <xsl:with-param name="nav.context" select="'toc'"/>
              <xsl:with-param name="content">
                <h1>
                  <xsl:apply-templates select="." mode="object.title.markup"/>
                </h1>
                <xsl:copy-of select="$lots"/>
              </xsl:with-param>
            </xsl:call-template>
          </xsl:with-param>
          <xsl:with-param name="quiet" select="$chunk.quietly"/>
        </xsl:call-template>
      </xsl:when>
      <xsl:otherwise>
        <xsl:copy-of select="$lots"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:if>
</xsl:template>

</xsl:stylesheet>



<?xml version="1.0" encoding="UTF-8"?>
<schema xmlns="http://purl.oclc.org/dsdl/schematron" queryBinding="xslt2">
  
  <pattern>
    <rule context="/*">
      <assert test="@toto">
        Root element mus have a toto attribute
      </assert>
    </rule>
  </pattern>
</schema>
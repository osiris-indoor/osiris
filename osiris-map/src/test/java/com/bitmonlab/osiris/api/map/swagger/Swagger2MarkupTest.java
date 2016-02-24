package com.bitmonlab.osiris.api.map.swagger;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import io.github.robwin.markup.builder.MarkupLanguage;
import io.github.robwin.swagger2markup.Swagger2MarkupConverter;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;


import static org.junit.Assert.assertTrue;


@RunWith(MockitoJUnitRunner.class)
public class Swagger2MarkupTest {

    @Test
    @Ignore public void convertRemoteSwaggerToAsciiDoc() throws IOException {
        // Remote Swagger source
        // Default is AsciiDoc
        Swagger2MarkupConverter.from("http://localhost:4024/api-docs").build().intoFolder("src/docs/asciidoc/generated");    

        // Then validate that three AsciiDoc files have been created
        String[] files = new File("src/docs/asciidoc/generated").list();
        
        assertTrue(Arrays.asList(files).contains("definitions.adoc"));
        assertTrue(Arrays.asList(files).contains("overview.adoc"));
        assertTrue(Arrays.asList(files).contains("paths.adoc"));       
     
    }

    @Test
    @Ignore public void convertRemoteSwaggerToMarkdown() throws IOException {
        // Remote Swagger source
        // Markdown
        Swagger2MarkupConverter.from("http://localhost:4024/api-docs").withMarkupLanguage(MarkupLanguage.MARKDOWN).build().intoFolder("src/docs/markdown/generated");

        // Then validate that three Markdown files have been created
        String[] files = new File("src/docs/markdown/generated").list();
        
        assertTrue(Arrays.asList(files).contains("definitions.md"));
        assertTrue(Arrays.asList(files).contains("overview.md"));
        assertTrue(Arrays.asList(files).contains("paths.md"));        
        
    }
}

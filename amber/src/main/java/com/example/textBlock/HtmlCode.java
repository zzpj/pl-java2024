package com.example.textBlock;

public class HtmlCode {
    String getHtml() {
        return """
                <!DOCTYPE html>
                <html>
                  <head>
                    <meta charset="utf-8">
                    <title>My test page</title>
                  </head>
                  <body>
                    <img src="images/firefox-icon.png" alt="My test image">
                  </body>
                </html>
                """;
    }
}

package demo;

import org.rendersnake.HtmlCanvas;

import java.io.IOException;

public class RenderHtmlDemo {
    public static void main(String[] args) throws IOException {
        HtmlCanvas canvas = new HtmlCanvas();
        canvas.html()
                .body()
                .h1().write("RenderSnake")._h1()
                ._body()
                ._html();
        System.err.println(canvas.toHtml());
    }
}

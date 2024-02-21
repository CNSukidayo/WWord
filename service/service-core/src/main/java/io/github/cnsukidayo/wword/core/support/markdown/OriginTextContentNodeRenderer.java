package io.github.cnsukidayo.wword.core.support.markdown;

import org.commonmark.internal.renderer.text.BulletListHolder;
import org.commonmark.internal.renderer.text.ListHolder;
import org.commonmark.internal.renderer.text.OrderedListHolder;
import org.commonmark.node.*;
import org.commonmark.renderer.NodeRenderer;
import org.commonmark.renderer.text.TextContentNodeRendererContext;
import org.commonmark.renderer.text.TextContentWriter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * The node renderer that renders all the core nodes (comes last in the order of node renderers).
 */
public class OriginTextContentNodeRenderer extends AbstractVisitor implements NodeRenderer {

    protected final TextContentNodeRendererContext context;
    private final TextContentWriter textContent;

    private ListHolder listHolder;

    public OriginTextContentNodeRenderer(TextContentNodeRendererContext context) {
        this.context = context;
        this.textContent = context.getWriter();
    }

    private int separateLength = 0;

    @Override
    public Set<Class<? extends Node>> getNodeTypes() {
        return new HashSet<>(Arrays.asList(
            Document.class,
            Heading.class,
            Paragraph.class,
            BlockQuote.class,
            BulletList.class,
            FencedCodeBlock.class,
            HtmlBlock.class,
            ThematicBreak.class,
            IndentedCodeBlock.class,
            Link.class,
            ListItem.class,
            OrderedList.class,
            Image.class,
            Emphasis.class,
            StrongEmphasis.class,
            Text.class,
            Code.class,
            HtmlInline.class,
            SoftLineBreak.class,
            HardLineBreak.class
        ));
    }

    @Override
    public void render(Node node) {
        node.accept(this);
    }

    @Override
    public void visit(Document document) {
        // No rendering itself
        visitChildren(document);
    }


    @Override
    public void visit(Heading heading) {
        // 标题
        for (int i = 0; i < heading.getLevel(); i++) {
            textContent.write('#');
        }
        textContent.whitespace();
        visitChildren(heading);
        writeEndOfLineIfNeeded(heading, ':');
    }

    @Override
    public void visit(ThematicBreak thematicBreak) {
        // 分割线
        if (!context.stripNewlines()) {
            textContent.write("***");
        }
        writeEndOfLineIfNeeded(thematicBreak, null);
    }

    @Override
    public void visit(HtmlInline htmlInline) {
        writeText(htmlInline.getLiteral());
    }

    @Override
    public void visit(HtmlBlock htmlBlock) {
        writeText(htmlBlock.getLiteral());
    }


    @Override
    public void visit(BlockQuote blockQuote) {
        // 块引用,就是> 语法
        separateLength++;
        writeEndOfLine();
        visitChildren(blockQuote);
        textContent.write("\n");
        separateLength--;
    }

    @Override
    public void visit(Paragraph paragraph) {
        if (separateLength > 0) {
            for (int i = 0; i < separateLength; i++) {
                textContent.write("> ");
            }
        }
        if (!(paragraph.getParent() instanceof ListItem)) {
            textContent.write("\n");
        }
        if (separateLength > 0) {
            for (int i = 0; i < separateLength; i++) {
                textContent.write("> ");
            }
        }
        visitChildren(paragraph);
        // Add "end of line" only if its "root paragraph.
        if (paragraph.getParent() == null || paragraph.getParent() instanceof Document) {
            writeEndOfLineIfNeeded(paragraph, null);
        }
    }

    @Override
    public void visit(SoftLineBreak softLineBreak) {
        // 软换行,即文字末尾回车键
        writeEndOfLineIfNeeded(softLineBreak, null);
        for (int i = 0; i < separateLength; i++) {
            textContent.write("> ");
        }

    }

    @Override
    public void visit(FencedCodeBlock fencedCodeBlock) {
        // 代码块
        if (context.stripNewlines()) {
            textContent.writeStripped(fencedCodeBlock.getLiteral());
            writeEndOfLineIfNeeded(fencedCodeBlock, null);
        } else {
            writeEndOfLineIfNeeded(fencedCodeBlock, null);
            for (int i = 0; i < fencedCodeBlock.getFenceLength(); i++) {
                textContent.write(fencedCodeBlock.getFenceChar());
            }
            textContent.write(fencedCodeBlock.getInfo());
            writeEndOfLine();
            textContent.write(fencedCodeBlock.getLiteral());
            for (int i = 0; i < fencedCodeBlock.getFenceLength(); i++) {
                textContent.write(fencedCodeBlock.getFenceChar());
            }
            writeEndOfLineIfNeeded(fencedCodeBlock, null);
        }
    }

    @Override
    public void visit(IndentedCodeBlock indentedCodeBlock) {
        // 代码块
        if (context.stripNewlines()) {
            textContent.writeStripped(indentedCodeBlock.getLiteral());
            writeEndOfLineIfNeeded(indentedCodeBlock, null);
        } else {
            textContent.write(indentedCodeBlock.getLiteral());
        }
    }

    @Override
    public void visit(Code code) {
        // 行内代码
        textContent.write('`');
        textContent.write(code.getLiteral());
        textContent.write('`');
    }


    @Override
    public void visit(Image image) {
        // 图片
        writeLink(image, image.getTitle(), image.getDestination());
    }

    @Override
    public void visit(Link link) {
        // 链接
        writeLink(link, link.getTitle(), link.getDestination());
    }

    @Override
    public void visit(ListItem listItem) {
        // 列表
        if (listHolder != null && listHolder instanceof OrderedListHolder) {
            OrderedListHolder orderedListHolder = (OrderedListHolder) listHolder;
            String indent = context.stripNewlines() ? "" : orderedListHolder.getIndent();
            textContent.write(indent + orderedListHolder.getCounter() + orderedListHolder.getDelimiter() + " ");
            visitChildren(listItem);
            writeEndOfLineIfNeeded(listItem, null);
            orderedListHolder.increaseCounter();
        } else if (listHolder != null && listHolder instanceof BulletListHolder) {
            BulletListHolder bulletListHolder = (BulletListHolder) listHolder;
            if (!context.stripNewlines()) {
                textContent.write(bulletListHolder.getIndent() + bulletListHolder.getMarker() + " ");
            }
            visitChildren(listItem);
            writeEndOfLineIfNeeded(listItem, null);
        }
    }

    @Override
    public void visit(BulletList bulletList) {
        // 任务列表
        if (listHolder != null) {
            writeEndOfLine();
        }
        listHolder = new BulletListHolder(listHolder, bulletList);
        visitChildren(bulletList);
        writeEndOfLineIfNeeded(bulletList, null);
        if (listHolder.getParent() != null) {
            listHolder = listHolder.getParent();
        } else {
            listHolder = null;
        }
    }

    @Override
    public void visit(OrderedList orderedList) {
        // 有序列表
        if (listHolder != null) {
            writeEndOfLine();
        }
        listHolder = new OrderedListHolder(listHolder, orderedList);
        visitChildren(orderedList);
        writeEndOfLineIfNeeded(orderedList, null);
        if (listHolder.getParent() != null) {
            listHolder = listHolder.getParent();
        } else {
            listHolder = null;
        }
    }


    @Override
    public void visit(HardLineBreak hardLineBreak) {
        // 硬换行,即文字结尾两个空格+回车
        writeText("  ");
        writeEndOfLineIfNeeded(hardLineBreak, null);
        for (int i = 0; i < separateLength; i++) {
            textContent.write("> ");
        }
    }


    @Override
    public void visit(Text text) {
        // 普通的文本渲染
        writeText(text.getLiteral());
    }

    @Override
    public void visit(Emphasis emphasis) {
        // 斜体渲染
        writeText(emphasis.getClosingDelimiter());
        visitChildren(emphasis);
        writeText(emphasis.getOpeningDelimiter());
    }

    @Override
    public void visit(StrongEmphasis strongEmphasis) {
        // 粗体渲染
        writeText(strongEmphasis.getClosingDelimiter());
        visitChildren(strongEmphasis);
        writeText(strongEmphasis.getOpeningDelimiter());
    }

    @Override
    protected void visitChildren(Node parent) {
        // 如果当前还处在块引用的层级中需要加上该前缀
        Node node = parent.getFirstChild();
        while (node != null) {
            Node next = node.getNext();
            context.render(node);
            node = next;
        }
    }

    private void writeText(String text) {
        if (context.stripNewlines()) {
            textContent.writeStripped(text);
        } else {
            textContent.write(text);
        }
    }

    private void writeLink(Node node, String title, String destination) {
        boolean hasChild = node.getFirstChild() != null;
        boolean hasTitle = title != null && !title.equals(destination);
        boolean hasDestination = destination != null && !destination.equals("");

        if (hasChild) {
            if (node instanceof Image) {
                textContent.write('!');
            }
            textContent.write('[');
            visitChildren(node);
            textContent.write(']');
            if (hasTitle || hasDestination) {
                textContent.write('(');
            }
        }

        if (hasDestination) {
            textContent.write(destination);
        }

        if (hasTitle) {
            if (hasDestination) {
                textContent.whitespace();
            }
            textContent.write('"');
            textContent.write(title);
            textContent.write('"');
        }

        if (hasChild && (hasTitle || hasDestination)) {
            textContent.write(')');
        }
    }

    private void writeEndOfLineIfNeeded(Node node, Character c) {
        if (context.stripNewlines()) {
            if (c != null) {
                textContent.write(c);
            }
            if (node.getNext() != null) {
                textContent.whitespace();
            }
        } else {
            if (node.getNext() != null) {
                textContent.line();
            }
        }
    }

    private void writeEndOfLine() {
        if (context.stripNewlines()) {
            textContent.whitespace();
        } else {
            textContent.line();
        }
    }
}

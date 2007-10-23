/*
 * Copyright (c) 2007 Henri Sivonen
 *
 * Permission is hereby granted, free of charge, to any person obtaining a 
 * copy of this software and associated documentation files (the "Software"), 
 * to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Software, and to permit persons to whom the 
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL 
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING 
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER 
 * DEALINGS IN THE SOFTWARE.
 */

package nu.validator.saxtree;

import org.xml.sax.Locator;

public abstract class ParentNode extends Node {

    protected Locator endLocator;
    
    private Node firstChild = null;
    
    private Node lastChild = null;
    
    ParentNode(Locator locator) {
        super(locator);
    }

    /**
     * Sets the endLocator.
     * 
     * @param endLocator the endLocator to set
     */
    public void setEndLocator(Locator endLocator) {
        this.endLocator = new LocatorImpl(endLocator);
    }

    /**
     * Copies the endLocator from another node.
     * 
     * @param another the another node
     */
    public void copyEndLocator(ParentNode another) {
        this.endLocator = another.endLocator;
    }
    
    /**
     * Returns the firstChild.
     * 
     * @return the firstChild
     */
    public final Node getFirstChild() {
        return firstChild;
    }

    /**
     * Returns the lastChild.
     * 
     * @return the lastChild
     */
    public final Node getLastChild() {
        return lastChild;
    }

    public Node insertBefore(Node child, Node sibling) {
        assert sibling == null || this == sibling.getParentNode();
        if (sibling == null) {
            return appendChild(child);
        }
        child.detach();
        child.setParentNode(this);
        if (firstChild == sibling) {
            child.setNextSibling(sibling);
            firstChild = child;
        } else {
            Node prev = firstChild;
            Node next = firstChild.getNextSibling();
            while (next != sibling) {
                prev = next;
                next = next.getNextSibling();
            }
            prev.setNextSibling(child);
            child.setNextSibling(next);
        }
        return child;
    }
    
    public Node appendChild(Node child) {
        child.detach();
        child.setParentNode(this);
        if (firstChild == null) {
            firstChild = child;
        } else {
            lastChild.setNextSibling(child);
        }
        lastChild = child;
        return child;
    }
    
    public void appendChildren(Node parent) {
        Node child = parent.getFirstChild();
        if (child == null) {
            return;
        }
        ParentNode another = (ParentNode) parent;
        if (firstChild == null) {
            firstChild = child;
        } else {
            lastChild.setNextSibling(child);
        }
        lastChild = another.lastChild;
        do {
            child.setParentNode(this);
        } while ((child = child.getNextSibling()) != null);
        another.firstChild = null;
        another.lastChild = null;
    }

    void removeChild(Node node) {
        assert this == node.getParentNode();
        if (firstChild == node) {
            firstChild = node.getNextSibling();
            if (lastChild == node) {
                lastChild = null;
            }
        } else {
            Node prev = firstChild;
            Node next = firstChild.getNextSibling();
            while (next != node) {
                prev = next;
                next = next.getNextSibling();
            }
            prev.setNextSibling(node.getNextSibling());
            if (lastChild == node) {
                lastChild = prev;
            }            
        }
    }
}

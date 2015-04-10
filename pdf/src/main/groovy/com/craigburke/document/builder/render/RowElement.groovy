package com.craigburke.document.builder.render

import com.craigburke.document.core.Cell
import com.craigburke.document.core.Row
import com.craigburke.document.core.Table

/**
 * Rendering element for the Row node
 * @author Craig Burke
 */
class RowElement {

    float startY
    float startX
    boolean spansMultiplePages = false

    Row node
    List<CellElement> cellElements = []

    RowElement(Row row, int startX) {
        this.node = row
        this.startX = startX

        Table table = row.parent
        int cellX = this.startX + table.border.size
        row.children.each { Cell cell ->
            cellElements << new CellElement(cell, cellX)
            cellX += cell.width + table.border.size
        }
    }

    void parseCellsUntilHeight(float height) {
        cellElements*.parseUntilHeight(height)
    }

    float getTotalHeight() {
        cellElements.max { it.totalHeight }.totalHeight
    }

    float getCurrentHeight() {
        cellElements.max { it.currentHeight }.currentHeight
    }

    boolean isFullyRendered() {
        cellElements.every { it.fullyRendered }
    }

    boolean isFirstRow() {
        (node == node.parent.children.first())
    }

}

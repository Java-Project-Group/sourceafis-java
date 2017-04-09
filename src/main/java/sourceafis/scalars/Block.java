package sourceafis.scalars;

import java.util.*;

public class Block implements Iterable<Cell> {
	public final int x;
	public final int y;
	public final int width;
	public final int height;
	public Block(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	public Block(Cell size) {
		this(0, 0, size.x, size.y);
	}
	public int left() {
		return x;
	}
	public int bottom() {
		return y;
	}
	public int right() {
		return x + width;
	}
	public int top() {
		return y + height;
	}
	public int area() {
		return width * height;
	}
	public static Block between(Cell start, Cell end) {
		return new Block(start.x, start.y, end.x - start.x, end.y - start.y);
	}
	public Cell center() {
		return new Cell((right() + left()) / 2, (bottom() + top()) / 2);
	}
	public Block intersect(Block other) {
		return between(
			new Cell(Math.max(left(), other.left()), Math.max(bottom(), other.bottom())),
			new Cell(Math.min(right(), other.right()), Math.min(top(), other.top())));
	}
	@Override public Iterator<Cell> iterator() {
		return new BlockIterator();
	}
	private class BlockIterator implements Iterator<Cell> {
		int atX;
		int atY;
		@Override public boolean hasNext() {
			return atY < height && atX < width;
		}
		@Override public Cell next() {
			if (!hasNext())
				throw new NoSuchElementException();
			Cell result = new Cell(x + atX, y + atY);
			++atX;
			if (atX >= width) {
				atX = 0;
				++atY;
			}
			return result;
		}
		@Override public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}
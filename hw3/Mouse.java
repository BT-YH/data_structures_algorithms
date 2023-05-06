import java.util.Stack;

public class Mouse {
	
	private static final char[] DIRECTIONS = {'n', 'e', 's', 'w'};
	
	private Maze maze;
	private Stack<Position> myStack;
	private Position currPosition;
	private Position exit;
	
	public Mouse(Maze maze, int r, int c) {
		this.maze = maze;
		exit = maze.getExit();
		currPosition = new Position(r, c);
		myStack = new Stack<Position>();
		
	}
	
	public char findOpenDir() {
		for (char dir : DIRECTIONS) {
			if (maze.isOpen(currPosition, dir)) {
				return dir;
			}
		}
		return 'x';
	}
	
	public Position getPosition() {
		return currPosition;
	}
	
	public boolean isExit() {
		return currPosition.equals(exit);
	}
	
	public Stack<Position> getStack() {
		return myStack;
	}
	
	public boolean makeMove() {
		char move = findOpenDir();
		if (move != 'x') {
			maze.mark(currPosition, Maze.MAYBE);
			myStack.push(currPosition);
			currPosition = currPosition.getAdjacent(move);
			return true;
		} else if (move == 'x' && !myStack.isEmpty()) {
			maze.mark(currPosition, Maze.TRIED_BAD);
			currPosition = myStack.pop();
			return true;
		} 

		return false;
	}
	
}
